package com.unifs.sdbst.app.bean.user.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @version V1.0
 * @title: UpdateVo
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/8/19 9:37
 */
@Data
public class UpdateVo {
    @NotNull
    private String code;    //旧手机号收到的验证码
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",message = "手机号码不正确！")
    private String phone;
}
