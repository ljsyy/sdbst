package com.unifs.sdbst.app.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.annotation.FormCommit;
import com.unifs.sdbst.app.annotation.RequestLimit;
import com.unifs.sdbst.app.annotation.ServiceLog;
import com.unifs.sdbst.app.base.LogEntity;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.service.LogService;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @version V1.0
 * @title: DetailLogAspect
 * @projectName ExcetptionAndValid
 * @description: 切面
 * @author： 张恭雨
 * @date 2019/4/29 11:59
 */
@Component
@Aspect
public class SystemAspect {
    @Autowired
    private LogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserService userService;

    private HashMap<String, Integer> ipAccessRecords;

    private Date startTime;

    private Logger log = LoggerFactory.getLogger(SystemAspect.class);

    /* 控制层切点
     这里定义下切点的位置,也就是刚才我们自定义的注解.
     这里的切点为注解的方式，我们也使用直接切入到service或control
     eg：@Pointcut("execution(public * com.unifs.baseframe.controller..*.*(..))")
    */
    @Pointcut("@annotation(com.unifs.sdbst.app.annotation.ControlLog)")
    public void controlPointcut() {
    }

    /*service层切点*/
    @Pointcut("@annotation(com.unifs.sdbst.app.annotation.ServiceLog)")
    public void servicePointcut() {
    }

    //IP限制访问切点
    @Pointcut("@annotation(com.unifs.sdbst.app.annotation.RequestLimit)")
    public void requestLimit() {
    }


    @Around("execution(public * *(..)) && @annotation(com.unifs.sdbst.app.annotation.RequestLimit)")
    public Object requestLimitHandel(ProceedingJoinPoint pjp) {
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();
        RequestLimit limit = method.getAnnotation(RequestLimit.class);

        System.out.println("限制切入点开始工作！");
        try {
            Object[] args = pjp.getArgs();
            HttpServletRequest request = null;
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof HttpServletRequest) {
                    request = (HttpServletRequest) args[i];
                    break;
                }
            }
            if (request == null) {
                Resp resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("方法中缺失HttpServletRequest参数");
                return resp;
            }
            // 获取限制IP存储器：存储被限制的IP信息
//            HashMap<String, Long> limitedIpMap = (HashMap<String, Long>) request.getServletContext().getAttribute("limitedIpMap");
            HashMap<Object, Object> limitedIpMap = (HashMap<Object, Object>) redisUtil.hgetall("limitedIpMap");

            if (limitedIpMap == null) {
                limitedIpMap = new HashMap<>();
            }

            if (ipAccessRecords == null) {
                ipAccessRecords = new HashMap<>();
            }

            //过滤受限的IP，剔除已经到期的限制IP
            filterLimitedIpMap(limitedIpMap);

            String ip = HttpUtil.getClientIp(request);
            String url = request.getRequestURL().toString();
            String key = "req_limit_".concat(url).concat(ip);

            /*验证该IP是否为限制IP*/
            if (isLimitedIP(limitedIpMap, ip)) {
                String context = "用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数,为限制IP！";
                log.info(context);
                Resp resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("IP访问次数超过限制");
                return resp;
            }

            if (ipAccessRecords.get(key) == null || Integer.parseInt(ipAccessRecords.get(key).toString()) == 0) {
                ipAccessRecords.put(key, 1);
            } else {
                ipAccessRecords.put(key, ipAccessRecords.get(key) + 1);
            }

            int count = Integer.parseInt(ipAccessRecords.get(key).toString());
            if (count > 0) {
                //创建一个定时器
                Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        ipAccessRecords.remove(key);
                    }
                };
                //这个定时器设定在time规定的时间之后会执行上面的remove方法，也就是说在这个时间后它可以重新访问
                timer.schedule(timerTask, limit.time());
            }
            if (count > limit.count()) {
                String context = "用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.count() + "]";
                log.info(context);
                /*将该用户IP加入限制IP中*/
                //设置该Ip地址的限制访问时间
                limitedIpMap.put(ip, String.valueOf(System.currentTimeMillis() + limit.limitTime()));
                //将新的ip黑名单更新到redis中
                redisUtil.hsetAll("limitedIpMap", limitedIpMap);
                Resp resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("IP访问次数超过限制");
                return resp;
            }

        } catch (Exception e) {
            log.error("发生异常", e);
            throw new RuntimeException("服务器异常");
        }

        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
//            throwable.printStackTrace();
            throw new MyException(throwable.getMessage(), 0);
        }
    }

    //before，在切点之前触发，
    @Before("controlPointcut()")
    public void controlBeforeLog(JoinPoint joinPoint) throws Exception {
        /*//初始化日志信息
        LogEntity logEntity = initLog(joinPoint);
        //记录日志
        logService.saveLog(logEntity);*/
        startTime = new Date();


    }


    //消息通知 @AfterReturning,在切点方法运行之后触发returning 为目标函数返回值
    @AfterReturning(returning = "resp", value = "controlPointcut()")
    public void controlAfterLog(JoinPoint joinPoint, Resp resp) {
        LogEntity logEntity = initLog(joinPoint);
        String status;
        if (resp != null) {
            // 获取操作状态
            if (resp.getCode() == 200) {
                status = "成功";
            } else {
                status = "失败";
            }
            //设置访问结果状态
            logEntity.setStatus(status);
            //设置详细信息
            logEntity.setDetail(resp.getMsg());
            //记录日志
            try {
                logService.saveLog(logEntity);
            } catch (Exception e) {
                //日志插入错误，继续执行，保证接口访问不受影响
                log.error(e.getMessage());
                return;
            }
        }

    }


    //异常通知
    @AfterThrowing(value = "controlPointcut()", throwing = "e")
    public void controlAfterThrowing(JoinPoint joinPoint, Throwable e) {
        //初始化日志信息
        LogEntity logEntity = initLog(joinPoint);
        logEntity.setStatus("失败！");
        logEntity.setDetail(e.getMessage());
        //记录日志
        logService.saveLog(logEntity);

    }

    //服务层，访问之前记录
    @Before(value = "servicePointcut()")
    public void serviceBeforeLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        String context = "";       //  定义操作内容

        // 获取注解中的操作方式
        if (method != null && !"".equals(method)) {
            // 获取自定义注解操作
            ServiceLog serviceLog = method.getAnnotation(ServiceLog.class);
            // 获取用户操作内容
            context = serviceLog.context();

        }
        StringBuffer interfacePath = new StringBuffer();
        // 获取请求的类名
        interfacePath.append(joinPoint.getTarget().getClass().getName());
        // 获取请求的方法名
        interfacePath.append("." + method.getName());

        // 获取请求的ip地址
        String ip = HttpUtil.getClientIp(request);

        // 获取请求的参数
        String argsName[] = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, Object> paramMap = new HashMap<>();
        if (argsName.length > 0) {
            paramMap = getParam(joinPoint, argsName, method.getName());
        }
        String args = JSON.toJSONString(paramMap);


        // 日志实体类封装
        LogEntity logEntity = new LogEntity();

        logEntity.setIp(ip);
        logEntity.setContent(context);
        logEntity.setDetail(args);
        logEntity.setTime(new Date());
        logEntity.setInterfacePath(interfacePath.toString());
        try {
            logService.saveLog(logEntity);
        } catch (Exception e) {
            //日志插入错误，继续执行，保证接口访问不受影响
            log.error(e.getMessage());
            return;
        }
    }

    //服务层，返回之后记录
    @AfterReturning(value = "servicePointcut()")
    public void serviceAfterLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        String context = "";       //  定义操作内容

        // 获取注解中的操作方式
        if (method != null && !"".equals(method)) {
            // 获取自定义注解操作
            ServiceLog serviceLog = method.getAnnotation(ServiceLog.class);
            // 获取用户操作内容
            context = serviceLog.context();
        }
        StringBuffer interfacePath = new StringBuffer();
        // 获取请求的类名
        interfacePath.append(joinPoint.getTarget().getClass().getName());
        // 获取请求的方法名
        interfacePath.append("." + method.getName());

        // 获取请求的ip地址
        String ip = HttpUtil.getClientIp(request);

        // 获取请求的参数
        String argsName[] = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, Object> paramMap = new HashMap<>();
        if (argsName.length > 0) {
            paramMap = getParam(joinPoint, argsName, method.getName());
        }
        String args = JSON.toJSONString(paramMap);

        method.getReturnType();


        // 日志实体类封装
        LogEntity logEntity = new LogEntity();

        logEntity.setIp(ip);
        logEntity.setContent(context);
        logEntity.setDetail(args);
        logEntity.setTime(new Date());
        logEntity.setInterfacePath(interfacePath.toString());

        logService.saveLog(logEntity);

    }

    // 处理参数格式,并返回需要的参数
    public static Map<String, Object> getParam(JoinPoint joinPoint, String argsName[], String methodName, String key) {
        Map<String, Object> detailMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();

        // 获取参数值
        Object args[] = joinPoint.getArgs();
        // 获取参数名
        argsName = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        for (int i = 0; i < argsName.length; i++) {
            if (!argsName[i].equals("model") && !argsName[i].equals("request")
                    && !argsName[i].equals("response") && !argsName[i].equals("session")) {
                if (argsName[i].equals("info")) {
                    if (key != null && !"".equals(key)) {
                        //对加密的信息进行解密
                        map.put(argsName[i], AES.decrypt((String) args[i], key));
                    } else {
                        map.put(argsName[i], args[i]);
                    }
                } else {
                    map.put(argsName[i], args[i]);
                }
            }

        }
        detailMap.put("method", methodName);
        detailMap.put("params", map);

        return detailMap;
    }

    // 处理参数格式,并返回需要的参数
    public static Map<String, Object> getParam(JoinPoint joinPoint, String argsName[], String methodName) {
        Map<String, Object> detailMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();

        // 获取参数值
        Object args[] = joinPoint.getArgs();
        // 获取参数名
        argsName = ((CodeSignature) joinPoint.getSignature()).getParameterNames();

        for (int i = 0; i < argsName.length; i++) {
            if (!argsName[i].equals("model") && !argsName[i].equals("request")
                    && !argsName[i].equals("response") && !argsName[i].equals("session")) {
                map.put(argsName[i], args[i]);
            }
        }
        detailMap.put("method", methodName);
        detailMap.put("params", map);

        return detailMap;
    }

    private LogEntity initLog(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入点所在的方法
        Method method = signature.getMethod();

        String operateType = "";         //  定义操作方式
        String operateContent = "";       //  定义操作内容
        String key = "";

        // 获取注解中的操作方式
        if (method != null && !"".equals(method)) {
            // 获取自定义注解操作
            ControlLog controlLog = method.getAnnotation(ControlLog.class);
            // 获取用户操作方式
            operateType = controlLog.operateType();
            // 获取用户操作内容
            operateContent = controlLog.context();
            //key
            key = controlLog.key();
            if (key != null) {
                //获取对应的属性值
                String fileName = "application-prod.properties";
                key = PropertiesUtil.getKey(fileName, key);
            }
        }


        StringBuffer interfacePath = new StringBuffer();     //定义类路径
        // 获取请求的类名
        interfacePath.append(joinPoint.getTarget().getClass().getName());
        // 获取请求的方法名
        interfacePath.append("." + method.getName());

        // 获取请求的ip地址
        String ip = HttpUtil.getClientIp(request);

        // 获取请求的参数
        String argsName[] = ((CodeSignature) joinPoint.getSignature()).getParameterNames();
        Map<String, Object> paramMap = new HashMap<>();
        if (argsName.length > 0) {
            paramMap = getParam(joinPoint, argsName, method.getName(), key);
        }

        //value出现空值不抛出异常
        String args = JSON.toJSONString(paramMap);

        LogEntity logEntity = new LogEntity();
        //初始化日志信息
        //设置来访者真实IP地址
        logEntity.setIp(ip);
        //设置访问参数
        logEntity.setArgs(args);
        //设置内容
        logEntity.setContent(operateContent);
        logEntity.setStartTime(startTime);
        //设置开始访问接口时间
        logEntity.setTime(new Date());
        //设置操作类型
        logEntity.setType(operateType);
        //设置访问类路径
        logEntity.setInterfacePath(interfacePath.toString());
        //获取来访者用户名
        //用户未登录情况，且参数加密
        if (key != null) {
            Map<String, Object> params = (Map<String, Object>) paramMap.get("params");
            JSONObject infoObject = JSON.parseObject((String) params.get("info"));
            //用户未登录情况
            String username = infoObject.getString("phone");
            if (username == null) {
                username = infoObject.getString("number");
            }
            logEntity.setUsername(username);
        }
        //用户已登录情况
        String userId = CookieUtil.getCookie(request, "userId");
        if (userId != null && !"".equals(userId)) {
            User user = userService.getUser(userId);
            if (user != null) {
                logEntity.setUsername(user.getPhone());
            }
        }


        return logEntity;
    }

    /**
     * @param limitedIpMap
     * @Description 过滤受限的IP，剔除已经到期的限制IP
     */
    private void filterLimitedIpMap(HashMap<Object, Object> limitedIpMap) {
        if (limitedIpMap == null) {
            return;
        }
        Set<Object> keys = limitedIpMap.keySet();
        Iterator<Object> keyIt = keys.iterator();
        long currentTimeMillis = System.currentTimeMillis();
        while (keyIt.hasNext()) {
            long expireTimeMillis = Long.parseLong(limitedIpMap.get(keyIt.next()).toString());
            if (expireTimeMillis <= currentTimeMillis) {
                keyIt.remove();
            }
        }
        //更新redis内的黑名单信息
        redisUtil.hsetAll("limitedIpMap", limitedIpMap);
    }

    /**
     * @param limitedIpMap
     * @param ip
     * @return true : 被限制 | false : 正常
     * @Description 是否是被限制的IP
     */
    private boolean isLimitedIP(Map<Object, Object> limitedIpMap, String ip) {
        if (limitedIpMap == null || ip == null) {
            // 没有被限制
            return false;
        }
        Set<Object> keys = limitedIpMap.keySet();
        Iterator<Object> keyIt = keys.iterator();
        while (keyIt.hasNext()) {
            String key = (String) keyIt.next();
            if (key.equals(ip)) {
                // 被限制的IP
                return true;
            }
        }
        return false;
    }

    @Around("execution(public * *(..)) && @annotation(com.unifs.sdbst.app.annotation.FormCommit)")
    public Object interceptor(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        FormCommit form = method.getAnnotation(FormCommit.class);
        String key = getCacheKey(method, pjp.getArgs());
        if (!StringUtils.isEmpty(key)) {
            if (redisUtil.get(key) != null) {
                Resp resp = new Resp(RespCode.REPATESUBMIT);
                return resp;

            }
            // 如果是第一次请求,就将key存入缓存中，缓存有效时间5秒
            redisUtil.set(key, key, 5);
        }
        try {
            return pjp.proceed();
        } catch (Throwable throwable) {
            throw new MyException(throwable.getMessage(), 0);
        }
    }

    /**
     * 将来还要加上用户的唯一标识
     */
    private String getCacheKey(Method method, Object[] args) {
        return method.getName() + args[0];
    }


}
