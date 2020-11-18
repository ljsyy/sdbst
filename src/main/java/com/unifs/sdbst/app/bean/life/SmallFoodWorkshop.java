package com.unifs.sdbst.app.bean.life;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SmallFoodWorkshop {
    private String registerCode;

    private String socialCode;

    private String administrativeName;

    private String licensedContent;

    private String delegateName;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date decideDate;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date cutoffDate;

    @JsonFormat(pattern="yyyy-MM-dd", timezone="GMT+8")
    private Date createDate;

    private String delFlag;

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode == null ? null : registerCode.trim();
    }

    public String getSocialCode() {
        return socialCode;
    }

    public void setSocialCode(String socialCode) {
        this.socialCode = socialCode == null ? null : socialCode.trim();
    }

    public String getAdministrativeName() {
        return administrativeName;
    }

    public void setAdministrativeName(String administrativeName) {
        this.administrativeName = administrativeName == null ? null : administrativeName.trim();
    }

    public String getLicensedContent() {
        return licensedContent;
    }

    public void setLicensedContent(String licensedContent) {
        this.licensedContent = licensedContent == null ? null : licensedContent.trim();
    }

    public String getDelegateName() {
        return delegateName;
    }

    public void setDelegateName(String delegateName) {
        this.delegateName = delegateName == null ? null : delegateName.trim();
    }

    public Date getDecideDate() {
        return decideDate;
    }

    public void setDecideDate(Date decideDate) {
        this.decideDate = decideDate;
    }

    public Date getCutoffDate() {
        return cutoffDate;
    }

    public void setCutoffDate(Date cutoffDate) {
        this.cutoffDate = cutoffDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }
}