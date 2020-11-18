package com.unifs.sdbst.app.bean.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User extends DataEntity<User> implements Serializable {

    private String id;

    private String loginName;
    @JsonIgnore
    private String password;

    private String phone;

    private String headUrl;
    @JsonIgnore
    private String loginIp;
    @JsonIgnore
    private Date loginDate;
    @JsonIgnore
    private String loginFlag;

    private Integer loginCount;

    private String identityNumber;

    private String identityType;

    private String account;

    private String accountType;

    private String ctype;

    private String clevel;

    private String loginType;

    private String origin;

    private String uid;

    private String uversion;

    private String linkPersonCid;

    private String linkPersonCtype;

    private String linkPersonName;

    @Override
    public String toString() {
        String realNumber;
        //企业用户身份证号处理
        if ("".equals(accountType) || accountType == null) {
            realNumber = identityNumber;
        } else {
            realNumber = !"human".equals(accountType) ? linkPersonCid : identityNumber;
        }
        return "{" +
                "\"id\":\"" + id + '\"' +
                ", \"loginName\":\"" + loginName + '\"' +
                ", \"identityNumber\":\"" + realNumber + '\"' +
                ", \"identityType\":\"" + identityType + '\"' +
                ", \"phone\":\"" + phone + '\"' +
                ", \"headUrl\":\"" + headUrl + '\"' +
                '}';
    }
}