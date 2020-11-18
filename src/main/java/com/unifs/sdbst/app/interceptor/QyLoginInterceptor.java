package com.unifs.sdbst.app.interceptor;

import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.enums.RespCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @version V1.0
 * @title: QyLoginInterceptor
 * @projectName sdbst
 * @description: 企业直通车登录拦截器
 * @author： 张恭雨
 * @date 2019/11/2 19:01
 */
@Component
public class QyLoginInterceptor implements HandlerInterceptor {
    private Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入企业直通车拦截器");
        String qyUser= (String) request.getSession().getAttribute("qyUser");
        if(StringUtils.isEmpty(qyUser)){
            response.sendRedirect("/sdbst/qy/train/loginPage");

            return false;
        }
        return true;
    }
}
