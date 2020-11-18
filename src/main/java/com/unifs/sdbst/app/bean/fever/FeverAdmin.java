package com.unifs.sdbst.app.bean.fever;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FeverAdmin implements Serializable {

    private String id;

    private String username;

    private String password;

    private Date createTime;

    private Date updateTime;

    private Date lastLoginTime;

    private static final long serialVersionUID = 1L;
}
