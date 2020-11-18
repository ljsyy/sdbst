package com.unifs.sdbst.app.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @version V1.0
 * @title: FormCommit
 * @projectName sdbst
 * @description: 防止数据重复提交注解
 * @author： 张恭雨
 * @date 2020/2/19 11:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FormCommit {
    String name() default "name:";
}
