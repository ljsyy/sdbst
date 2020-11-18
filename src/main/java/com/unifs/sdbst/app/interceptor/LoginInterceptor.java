package com.unifs.sdbst.app.interceptor;

import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.CookieUtil;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @version V1.0
 * @title: 登录拦截器
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/8/16 10:54
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(LoginInterceptor.class);
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //设置返回数据类型
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out;

        //设置编码格式
        response.setCharacterEncoding("UTF-8");
        /*获取用户登录信息*/
        String userId = CookieUtil.getCookie(request, "userId");
        if (!StringUtils.isEmpty(userId)) {
            User user = userService.getUser(userId);
            if (user == null) {
                /*用户信息为空,跳转主页*/
                log.info("未通过验证，返回未登录错误！" + request.getContextPath());
                Resp resp = new Resp(RespCode.DEFAULT);
                resp.setMsg("该用户还未登录，请先登录！");
                String json = JSON.toJSONString(resp);
                out = response.getWriter();
                out.append(json);
                return false;
            }
        } else {
            Resp resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("用户身份验证失败！");
            String json = JSON.toJSONString(resp);
            out = response.getWriter();
            out.append(json);
            return false;
        }
        log.info("拦截验证通过");
        return true;
    }
}
