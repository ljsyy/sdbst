package com.unifs.sdbst.app.bean.push;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * ALI_PUSH_USER
 * @author 
 */
@Data
public class AliPushUser implements Serializable {
    private String id;

    private String userId;

    private String deviceId;

    private String alive;

    private String mobileType;

    private String mobileTypeDetail;

    private Date createDate;

    private Date updateDate;

    private String delFlag;

    private String remarks;

    private String loginIp;

    private static final long serialVersionUID = 1L;
}