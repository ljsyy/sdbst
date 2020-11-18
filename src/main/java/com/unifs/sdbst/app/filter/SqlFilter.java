package com.unifs.sdbst.app.filter;

import com.unifs.sdbst.app.exception.MyException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @version V1.0
 * @title: SqlFilter
 * @projectName sdbst
 * @description: sql注入过滤器
 * @author： 张恭雨
 * @date 2020/1/24 11:53
 */
@Component
@WebFilter(filterName = "SqlFilter", urlPatterns = {"/*"})
public class SqlFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获得所有请求参数名
        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements()) {
            // 得到参数名
            String name = params.nextElement().toString();
            // 得到参数对应值
            String[] value = request.getParameterValues(name);
            for (int i = 0; i < value.length; i++) {
                if (sqlValidate(value[i])) {
                    System.out.println(value[i]);
                    request.setAttribute("errorMsg", "您发送请求中的参数中含有非法字符");
                    throw new MyException("您发送请求中的参数中含有非法字符");
                }
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * 参数校验
     *
     * @param str
     */
    public static boolean sqlValidate(String str) {
        str = str.toLowerCase();//统一转为小写
        //|truncate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|table
        String badStr = "select|update|delete|insert|truncate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute|table";
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; i++) {
            //循环检测，判断在请求参数当中是否包含SQL关键字
            if (str.indexOf(badStrs[i]) >= 0) {
                return true;
            }
        }
        System.out.println(str);
        return false;
    }

}
