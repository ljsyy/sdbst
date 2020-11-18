package com.unifs.sdbst.app.service;

import com.unifs.sdbst.app.dao.primary.user.UserMapper;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.utils.AES;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.PropertiesUtil;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @version V1.0
 * @title: PublicService
 * @projectName sdbst
 * @description: 公共接口业务逻辑处理类
 * @author： 张恭雨
 * @date 2019/8/31 20:56
 */
@Service
@Transactional
public class PublicService {
    @Autowired
    private UserMapper userMapper;
    @Value("${aes.key}")
    private String key;
    @Value("${AES.SUBSIDY}")
    private String subsidyKey;
    @Autowired
    private RedisUtil redisUtil;

    //用户获取验证码时验证该号码是否正确
    public void codeVerify(HttpServletRequest request) {
        //获取校验开关状态
        String filename = "application.properties";
        String flag = PropertiesUtil.getKey(filename, "sms.verify.open");
        if (!"true".equals(flag)) {
            //如果校验开关未处于打开状态，则直接跳过校验
            return;
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
            return;
        } else {
            Long sessionTime = Long.parseLong(strTime);
            //校验时间戳
            Long appTime = Long.parseLong(str[1]);
            //本次访问时间戳大于上次时间戳
            if (appTime > sessionTime) {
                //校验通过,更新redis信息
                redisUtil.set(str[0], str[1]);
                return;
            } else {
                throw new MyException("认证失败！", 0);
            }
        }
    }


    //用户获取验证码时验证该号码是否正确
    public void accessAuth(HttpServletRequest request) {
        //接口访问校验
        String appToken = CookieUtil.getCookie(request, "appToken");
        if (appToken == null || "".equals(appToken)) {
            throw new MyException("认证失败！", 0);
        }
        //解密
        appToken = AES.decrypt(appToken, subsidyKey);
        //字符串处理
        String str[] = appToken.split("\\|");
        //获取存储redis的值
        String strTime = redisUtil.get(str[0]);
        if (strTime == null || "".equals(strTime)) {
            //首次访问，放行,并将认证信息保存
            redisUtil.set(str[0], str[1]);
            return;
        } else {
            Long sessionTime = Long.parseLong(strTime);
            //校验时间戳
            Long appTime = Long.parseLong(str[1]);
            //本次访问时间戳大于上次时间戳
            if (appTime > sessionTime) {
                //校验通过,更新redis信息
                redisUtil.set(str[0], str[1]);
                return;
            } else {
                throw new MyException("认证失败！", 0);
            }
        }
    }
}
