package com.unifs.sdbst.app.bean.user;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * APP_USER
 * @author 
 */
@Data
public class AppUser implements Serializable {
    /**
     * 主键
     */
    private String id;

    /**
     * 登录名
     */
    private String loginName;

    /**
     * 密码
     */
    private String password;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 登录IP
     */
    private String loginIp;

    /**
     * 登录时间
     */
    private Date loginDate;

    /**
     * 登录类型 android / ios
     */
    private String loginFlag;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    private Date updateDate;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 删除标志,0:正常，1：已删除
     */
    private String delFlag;

    /**
     * 头像路径
     */
    private String headUrl;

    /**
     * 登录次数
     */
    private Integer loginCount;

    private static final long serialVersionUID = 1L;
}