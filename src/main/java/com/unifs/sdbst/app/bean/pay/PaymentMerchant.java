package com.unifs.sdbst.app.bean.pay;

import java.util.Date;

public class PaymentMerchant {
    private String projectCode;

    private String type;

    private String projectName;

    private String businessName;

    private String bussinessProjectName;

    private Date createDate;

    private Date updateDate;

    public String getBussinessProjectName() {
        return bussinessProjectName;
    }

    public void setBussinessProjectName(String bussinessProjectName) {
        this.bussinessProjectName = bussinessProjectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode == null ? null : projectCode.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}