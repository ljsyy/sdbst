package com.unifs.sdbst.app.interceptor;

import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.utils.AES;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.PropertiesUtil;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version V1.0
 * @title: AccessAuthInterceptor
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2020/2/27 14:46
 */
@Component
public class AccessAuthInterceptor implements HandlerInterceptor {
    @Value("${aes.key}")
    private String key;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取校验开关状态
        String filename = "application.properties";
        String flag = PropertiesUtil.getKey(filename, "sms.verify.open");
        if (!"true".equals(flag)) {
            //如果校验开关未处于打开状态，则直接跳过校验
            return true;
        }

        //接口访问校验
        String appToken = CookieUtil.getCookie(request, "appToken");
        if (appToken == null || "".equals(appToken)) {
            throw new MyException("认证失败！", 0);
        }
        //解密
        appToken = AES.decrypt(appToken, key);
        //字符串处理
        String str[] = appToken.split("\\|");
        //获取存储redis的值
        String strTime = redisUtil.get(str[0]);
        if (strTime == null || "".equals(strTime)) {
            //首次访问，放行,并将认证信息保存
            redisUtil.set(str[0], str[1]);
            return true;
        } else {
            Long sessionTime = Long.parseLong(strTime);
            //校验时间戳
            Long appTime = Long.parseLong(str[1]);
            //本次访问时间戳大于上次时间戳，
            if (appTime > sessionTime) {
                //更新时间
                redisUtil.set(str[0], str[1]);
                //校验通过
                return true;
            } else {
                throw new MyException("认证失败！", 0);
            }
        }
    }
}
