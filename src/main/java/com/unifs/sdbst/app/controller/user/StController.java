package com.unifs.sdbst.app.controller.user;

import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.bean.user.UserSwt;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.service.user.UserSwtService;
import com.unifs.sdbst.app.utils.AES;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;


/**
 * 省网厅账号绑定
 *
 * @author liuzibo
 * @version 2018-06-22
 */

@Controller
@RequestMapping(value = "/app/st")
public class StController {
    @Autowired
    private UserSwtService userSwtService;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${AES.EPIDEMIC.KEY}")
    private String key;

    private Resp resp;

    //统一认证登录接口
    /*@ControlLog(operateType = "省厅登录", context = "统一认证登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Resp login(@Validated UserSwt userSwt) throws Exception {
        User user = userSwtService.saveAndLogin(userSwt);
        //将用户信息放到redis缓存中设置超时时间为一小时
        redisUtil.set(user.getId(), user.toString(), 60 * 60);
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(user.getId());
        return resp;
    }*/

    //统一认证登录接口
    @ControlLog(operateType = "省厅登录", context = "统一认证登录接口")
    @RequestMapping(value = "/newLogin", method = RequestMethod.POST)
    @ResponseBody
    public Resp newLogin(@NotNull String stInfo, String loginFlag, HttpServletRequest request) throws Exception {
        //解密加密字符串
        stInfo = AES.decrypt(stInfo, key);
        //将json字符串转换为对象
        UserSwt userSwt = JSON.parseObject(stInfo, UserSwt.class);
        System.out.println(userSwt.toString());
        User user = userSwtService.saveAndLogin(userSwt, loginFlag, request);
        resp = new Resp(RespCode.SUCCESS);
        System.out.println(user.toString());
        resp.setData(AES.encrypt(user.toString(), key));
        return resp;
    }


}

