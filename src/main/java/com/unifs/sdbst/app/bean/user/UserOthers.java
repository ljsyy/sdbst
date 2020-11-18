package com.unifs.sdbst.app.bean.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "UserOthers",description = "其他用户")
public class UserOthers{
//    @ApiModelProperty(hidden = true)
    private String id;

    private String username;
    //密码
    private String psd;

    private String type;
    @ApiModelProperty(hidden = true)
    private String userId;
    @JsonIgnore
    private String delFlag;
}