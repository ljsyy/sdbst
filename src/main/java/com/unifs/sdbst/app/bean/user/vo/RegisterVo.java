package com.unifs.sdbst.app.bean.user.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @version V1.0
 * @title: RegisterVo
 * @projectName sdbst
 * @description: 注册VO
 * @author： 张恭雨
 * @date 2019/8/13 14:22
 */
@Data
public class RegisterVo {
    @NotNull(message = "用户名不能为空！")
    private String loginName;


    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",message = "手机号码不正确！")
    private String phoneNumber;


//    @Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,20}")
//    @Length(min = 6,max = 20,message = "密码为6-20位字母数字组合")
    private String password;


//    @Pattern(regexp="^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z0-9]{6,20}")
//    @Length(min = 6,max = 20,message = "密码为6-20位字母数字组合")
    private String confirmPassword;

}
