package com.unifs.sdbst.app.bean.fingertips;

import java.util.Date;

public class FingertipsUserInfo {
    private String id;

    private String account;

    private String accountType;

    private String cid;

    private String ctype;

    private String clevel;

    private String linkPersonCid;

    private String linkPersonCtype;

    private String linkPersonName;

    private String loginType;

    private String mobile;

    private String name;

    private String origin;

    private String uuid;

    private String uversion;

    private String ufrom;

    private Date insertTime;

    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType == null ? null : accountType.trim();
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    public String getCtype() {
        return ctype;
    }

    public void setCtype(String ctype) {
        this.ctype = ctype == null ? null : ctype.trim();
    }

    public String getClevel() {
        return clevel;
    }

    public void setClevel(String clevel) {
        this.clevel = clevel == null ? null : clevel.trim();
    }

    public String getLinkPersonCid() {
        return linkPersonCid;
    }

    public void setLinkPersonCid(String linkPersonCid) {
        this.linkPersonCid = linkPersonCid == null ? null : linkPersonCid.trim();
    }

    public String getLinkPersonCtype() {
        return linkPersonCtype;
    }

    public void setLinkPersonCtype(String linkPersonCtype) {
        this.linkPersonCtype = linkPersonCtype == null ? null : linkPersonCtype.trim();
    }

    public String getLinkPersonName() {
        return linkPersonName;
    }

    public void setLinkPersonName(String linkPersonName) {
        this.linkPersonName = linkPersonName == null ? null : linkPersonName.trim();
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType == null ? null : loginType.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getUversion() {
        return uversion;
    }

    public void setUversion(String uversion) {
        this.uversion = uversion == null ? null : uversion.trim();
    }

    public String getUfrom() {
        return ufrom;
    }

    public void setUfrom(String ufrom) {
        this.ufrom = ufrom == null ? null : ufrom.trim();
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}