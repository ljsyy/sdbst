package com.unifs.sdbst.app.bean.user;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class UserSwt {
    @NotNull
    private String mobile;

    private String account;

    private String accountType;

    private String cid;

    private String ctype;

    private String clevel;

    private String loginType;

    private String name;

    private String origin;

    private String uid;

    private String uversion;

    private String userId;

    private String linkPersonCid;

    private String linkPersonCtype;

    private String linkPersonName;

}