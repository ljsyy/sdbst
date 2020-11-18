package com.unifs.sdbst.app.annotation;

/**
 * @创建人 张恭雨
 * @创建时间 2018/12/18
 * @描述：一段时间内IP地址访问次数的限制
 */

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
    /**
     * 允许访问的最大次数
     */
    int count() default Integer.MAX_VALUE;

    /**
     * 时间段，单位为毫秒，默认值一分钟
     */
    long time() default 1000 * 60;

    /**
     * ip地址限制时长，单位为毫秒，默认值24小时
     */
    long limitTime() default 24 * 60 * 60 * 1000;
}
