package com.unifs.sdbst.app.controller.user;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.annotation.FormCommit;
import com.unifs.sdbst.app.annotation.RequestLimit;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.bean.user.vo.PasswordVo;
import com.unifs.sdbst.app.bean.user.vo.RegisterVo;
import com.unifs.sdbst.app.bean.user.vo.UpdateVo;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.AES;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.EncryptUtil;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.util.List;

/**
 * 　　@version V1.0
 *
 * @title: UserController
 * @projectName sdbst
 * @description: app用户控制层
 * @author： 张恭雨
 * @date 2019/8/13 10:02
 */


@RestController
@RequestMapping(value = "/app/user", method = {RequestMethod.POST, RequestMethod.GET})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisUtil redisUtil;
    private Resp resp;      //返回对象声明
    @Value("${aes.key}")
    private String aesKey;

    /**
     * 　　* @description: app用户注册接口
     * 　　* @param ${RegisterVo}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/8/14 22:03
     */
//    @FormCommit
//    @ControlLog(operateType = "/register", context = "用户注册")       //日志记录注解
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Resp register(@Valid RegisterVo vo) throws Exception {
        userService.register(vo);
        resp = new Resp(RespCode.SUCCESS);
        return resp;
    }

//    @FormCommit
//    @ControlLog(operateType = "/phoneRegister", context = "注册(手机号)")       //日志记录注解
//    @RequestMapping(value = "/phoneRegister", method = RequestMethod.POST)
    public Resp phoneRegister(@NotNull String phone, @NotNull String code) throws Exception {
        String password = userService.registerByPhone(phone, code);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(password);
        return resp;
    }

    /**
     * 功能描述 :自动注册并登录
     *
     * @param * @param null
     * @return
     * @author 张恭雨
     * @date 2020/2/7
     */
//    @ControlLog(operateType = "/autoLogin", context = "身份证号自动注册登录")
//    @RequestMapping(value = "/autoLogin", method = RequestMethod.POST)
    public Resp autoLogin(@NotNull String loginName, @NotNull String number,
                          String identity, String loginFlag, HttpServletResponse response,
                          HttpServletRequest request) throws Exception {
        //验证身份证号不为空
        if (identity == null || "".equals(identity)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("证件号不能为空！");
            return resp;
        }
        User user = userService.autoLogin(loginName, number, identity, loginFlag, request);
        //将用户信息放到redis缓存中设置超时时间为一小时
        redisUtil.set(user.getId(), user.toString(), 60 * 60);
//        CookieUtil.sendCookie(response,"userId",user.getId());
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(user.getId());
        return resp;
    }

   /** 
    * app用户登录接口
    * @author 张恭雨
    * @date 2020/6/3
    * @params [loginName, password, loginFlag, request] 
    * @return com.unifs.sdbst.app.base.Resp
    */
//    @ControlLog(operateType = "/login", context = "帐号密码登录")
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Resp login(String loginName, String password, String loginFlag, HttpServletRequest request) throws Exception {
        User user = userService.verifyInfo(loginName, password, loginFlag, request);
        //将用户信息放到redis缓存中设置超时时间为一小时
        redisUtil.set(user.getId(), user.toString(), 60 * 60);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(user.getId());
        System.out.println(user.toString());
        return resp;
    }

    /**
     * 　　* @description: 手机号验证码登录模式
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/9/5 9:36
     */
//    @ControlLog(operateType = "/phoneLogin", context = "手机号登录")
//    @RequestMapping(value = "/phoneLogin")
    public Resp phoneLogin(String phone, String code, String loginFlag, HttpServletRequest request) {
        User user = userService.phoneLogin(phone, code, loginFlag, request);
        //将用户信息放到redis缓存中设置超时时间为一小时
        redisUtil.set(user.getId(), user.toString(), 60 * 60);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(user.getId());
        System.out.println(user.toString());
        return resp;
    }

    /**
     * 　　* @description: 修改用户信息
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/8/15 16:35
     */
//    @ControlLog(operateType = "/updatePhone", context = "修改手机号码")
//    @RequestMapping(value = "/updatePhone", method = RequestMethod.POST)
    public Resp updatePhone(@Valid UpdateVo vo, HttpServletRequest request) throws Exception {
        boolean flag = userService.updatePhone(vo, request);
        if (flag) {
            resp = new Resp(RespCode.SUCCESS);
        } else {
            resp = new Resp(RespCode.DEFAULT);
        }
        return resp;
    }

    /*获取该手机号所包含的用户列表信息 */
    @ControlLog(operateType = "/userList", context = "获取该手机号所包含的用户列表信息", key = "aes.key")
    @GetMapping(value = "/userList")
    public Resp userList(String info) {
        Resp resp;
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, aesKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String phone = jsonObject.getString("phone");
        String code = jsonObject.getString("code");
        List<User> users = userService.userList(phone, code);
        if (users == null || users.isEmpty()) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("该手机用户信息不存在");
            return resp;
        }
        resp = new Resp(RespCode.SUCCESS);
        //信息加密
        String json = JSON.toJSONString(users);
        resp.setData(AES.encrypt(json, aesKey));
        return resp;
    }

    /*手机号验证码登录新接口*/
    @ControlLog(operateType = "/phoneLoginNew", context = "手机号验证码登录-身份选择", key = "aes.key")
    @PostMapping(value = "/phoneLoginNew")
    public Resp phoneLoginNew(String info, HttpServletRequest request) {
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, aesKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String phone = jsonObject.getString("phone");
        String code = jsonObject.getString("code");
        String loginFlag = jsonObject.getString("loginFlag");
        String token = jsonObject.getString("token");
        User user = userService.phoneLogin(phone, code, loginFlag, token, request);
        resp = new Resp(RespCode.SUCCESS);
        System.out.println(user.toString());
        resp.setData(AES.encrypt(user.toString(), aesKey));
        return resp;
    }

    /*应急登录接口*/
    @ControlLog(operateType = "/phoneLoginEmergency", context = "应急登录", key = "aes.key")
    @PostMapping(value = "/phoneLoginEmergency")
    public Resp phoneLoginEmergency(String info, HttpServletRequest request) {
        /*//获取app模式信息
        String appMode = redisUtil.get("appMode");
        if (appMode == null || appMode.equals("")) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("请先设置访问模式!");
            return resp;
        } else {
            if (appMode.equals("normal")) {
                //切换为应急
                resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("应急模式下方可访问");
                return resp;
            }
        }*/
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, aesKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String phone = jsonObject.getString("phone");
        String loginFlag = jsonObject.getString("loginFlag");
        String identity = jsonObject.getString("identity");
        String identityType = jsonObject.getString("identityType");
        User user = userService.EmergencyLogin(phone, identity, loginFlag, identityType, request);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(AES.encrypt(user.toString(), aesKey));
        return resp;
    }

    //注册新接口
    @ControlLog(operateType = "/registerNew", context = "用户注册-新", key = "aes.key")
    @RequestMapping(value = "/registerNew", method = RequestMethod.POST)
    public Resp registerNew(String info, HttpServletRequest request) throws Exception {

        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, aesKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String loginName = jsonObject.getString("loginName");
        String number = jsonObject.getString("number");
        String loginFlag = jsonObject.getString("loginFlag");
        String identity = jsonObject.getString("identity");
        String code = jsonObject.getString("code");
        String identityType = jsonObject.getString("identityType");
        //验证用户名不为空
        if (loginName == null || "".equals(loginName)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("姓名不能为空！");
            return resp;
        }
        //验证身份证号不为空
        if (identity == null || "".equals(identity)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("证件号不能为空！");
            return resp;
        }
        User user = userService.register(loginName, number, identity, code, loginFlag, identityType, request);
        //注册完成
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(AES.encrypt(user.toString(), aesKey));
        return resp;
    }

    //应急注册接口
    @ControlLog(operateType = "/registerEmergency", context = "应急注册", key = "aes.key")
    @PostMapping(value = "/registerEmergency")
    public Resp registerEmergency(String info, HttpServletRequest request) throws Exception {
        /*//获取app模式信息
        String appMode = redisUtil.get("appMode");
        if (appMode == null || appMode.equals("")) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("请先设置访问模式");
            return resp;
        } else {
            if (appMode.equals("normal")) {
                //切换为应急
                resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("应急模式下方可访问");
                return resp;
            }
        }*/
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, aesKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String loginName = jsonObject.getString("loginName");
        String number = jsonObject.getString("number");
        String loginFlag = jsonObject.getString("loginFlag");
        String identity = jsonObject.getString("identity");
        String identityType = jsonObject.getString("identityType");
        User user = userService.register(loginName, number, identity, loginFlag, identityType, request);
        //注册完成
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(AES.encrypt(user.toString(), aesKey));
        return resp;
    }

    //app注册登录模式获取
    @ControlLog(operateType = "/appMode", context = "app注册登录模式获取")
    @GetMapping(value = "/appMode")
    public Resp appMode() {
        Resp resp = new Resp(RespCode.SUCCESS);
        //获取app模式信息
        String appMode = redisUtil.get("appMode");
        if (appMode == null || appMode.equals("")) {
            //首次访问,初始化
            appMode = "normal";
            redisUtil.set("appMode", appMode);
        }
        resp.setData(appMode);
        return resp;
    }

    //app注册登录模式切换
    @ControlLog(operateType = "/modeChange", context = "app注册登录模式获取")
    @GetMapping(value = "/modeChange")
    public Resp modeChange() {
        Resp resp = new Resp(RespCode.SUCCESS);
        //获取app模式信息
        String appMode = redisUtil.get("appMode");
        if (appMode == null || appMode.equals("")) {
            //首次访问,初始化
            appMode = "normal";
        } else {
            if (appMode.equals("normal")) {
                //切换为应急
                appMode = "emergency";
            } else {
                //切换为正常
                appMode = "normal";
            }
        }
        redisUtil.set("appMode", appMode);
        resp.setMsg("切换成功，当前状态：" + appMode);
        return resp;
    }

    //修改证件信息
    @ControlLog(operateType = "modifyInfo", context = "修改证件信息")
    @PostMapping(value = "/modifyInfo")
    public Resp modifyInfo(String info) {
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, aesKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String token = jsonObject.getString("token");
        String identity = jsonObject.getString("identity");
        String identityType = jsonObject.getString("identityType");
        userService.modifyInfo(token, identity, identityType);
        return new Resp(RespCode.SUCCESS);
    }

    //修改用户信息
    @ControlLog(operateType = "modifyUserInfo", context = "修改用户信息")
    @PostMapping(value = "/modifyUserInfo")
    public Resp modifyUserInfo(String info) {
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        try {
            userService.modifyInfo(info);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Resp(RespCode.ERROR);
        }
        return new Resp(RespCode.SUCCESS);
    }

    //删除证件信息
    @ControlLog(operateType = "/dropIdentity", context = "删除证件信息")
    @PostMapping(value = "/dropIdentity")
    public Resp dropIdentity(String info) {
        if (info == null || "".equals(info)) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("参数不能为空！");
            return resp;
        }
        info = AES.decrypt(info, aesKey);
        JSONObject jsonObject = JSON.parseObject(info);
        String token = jsonObject.getString("token");
        userService.dropIdentity(token);
        return new Resp(RespCode.SUCCESS);
    }

    /**
     * 　　* @description: 修改密码
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/8/19 9:49
     */

//    @ControlLog(operateType = "/changePassword", context = "修改密码")
//    @PostMapping(value = "/changePassword")
    public Resp changePassword(@Valid PasswordVo vo, HttpServletRequest request) throws Exception {
        userService.changePassword(vo, request);
        resp = new Resp(RespCode.SUCCESS);
        return resp;
    }

    /**
     * 　　* @description: 获取用户个人信息
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/8/19 11:03
     */

//    @ControlLog(operateType = "/info", context = "个人信息")
//    @RequestMapping(value = "/info", method = RequestMethod.POST)
    public Resp info(HttpServletRequest request) throws Exception {
        String userId = CookieUtil.getCookie(request, "userId");
        String userStr = redisUtil.get(userId);
        JSONObject jsonStr = JSONObject.parseObject(userStr);
        String phone = jsonStr.getString("phone");
        String dePhone = EncryptUtil.aesEncrypt(phone, aesKey);
        System.out.println(dePhone);
        jsonStr.put("dePhone", dePhone);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(jsonStr);
        return resp;
    }


    /**
     * 　　* @description: 退出登录
     * 　　* @param ${tags}
     * 　　* @return ${return_type}
     * 　　* @throws
     * 　　* @author 张恭雨
     * 　　* @date 2019/8/19 12:23
     */
    @ControlLog(operateType = "/exit", context = "退出登录")
    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    public Resp exit(HttpServletRequest request) {
        String userId = CookieUtil.getCookie(request, "userId");
        redisUtil.del(userId);
        resp = new Resp(RespCode.SUCCESS);
        return resp;
    }

}
