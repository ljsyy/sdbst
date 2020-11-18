package com.unifs.sdbst.app.bean.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @version V1.0
 * @title: PasswordVo
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/8/19 10:04
 */

@Data
public class PasswordVo {
    @NotNull
    private String loginName;
//    @Length(min = 6,max = 20,message = "密码为6-20位字母数字组合")
    private String password;        //新密码
    @NotNull
    private String code; //验证码
}
