package com.unifs.sdbst.app.config;

import com.unifs.sdbst.app.interceptor.AccessAuthInterceptor;
import com.unifs.sdbst.app.interceptor.LoginInterceptor;
import com.unifs.sdbst.app.interceptor.QyLoginInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 　　*
 *
 * @创建人 张恭雨
 * @创建时间 2018/8/22
 * @描述 拦截器配置器
 */


@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    LoginInterceptor loginInterceptor;
    @Autowired
    QyLoginInterceptor qyLoginInterceptor;
    @Autowired
    AccessAuthInterceptor authInterceptor;

    //不拦截资源路径
    final String[] noLoginInterceptPaths = {"/app/user/register", "/app/user/login", "/app/user/changePassword",
            "/app/user/phoneLogin", "/app/user/phoneRegister", "/app/user/autoLogin", "/app/user/userList"};
    //拦截资源路径  "/app/user/dropIdentity"
    final String[] LoginInterceptPaths = {"/app/user/updatePhone", "/app/user/changePassword",
            "/app/user/info", "/app/user/exit","/app/user/dropIdentity", "/app/otherUser/*"};
    //企业直通车拦截路径
    final String[] qyLoginInterceptPaths = {"/qy/train/*"};
    //企业直通车不拦截路径
    final String[] qyNoLoginInterptPahts = {"/qy/train/login", "/qy/train/loginPage", "/qy/train/registerPage", "/qy/train/register", "/qy/train/getCode"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录拦截器注册
        registry.addInterceptor(loginInterceptor).addPathPatterns(LoginInterceptPaths);
        //认证拦截器注册
//        registry.addInterceptor(authInterceptor).addPathPatterns("/app/user/*","/app/st/*").excludePathPatterns("/app/user/modeChange");
        //企业直通车
//       registry.addInterceptor(qyLoginInterceptor).addPathPatterns(qyLoginInterceptPaths).excludePathPatterns(qyNoLoginInterptPahts);
        super.addInterceptors(registry);
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //设置默认首页
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/public/toDownload");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }

}
