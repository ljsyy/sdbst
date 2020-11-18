package com.unifs.sdbst.app.bean.enterprise;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class EnterpriseReserve {
    private String id;

    private Date createDate;

    private String name;

    private String type;

    private String address;
    //  已复工日期
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date resumedWorkDate;
    //  拟复工日期
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date toResumeWorkDate;
    //  企业人数
    private Integer peopleNumber;
    //  复工人数
    private Integer resumedWorkNumber;
    //  口罩数量
    private Integer numberMasks;
    //  企业联系人
    private String contactPerson;
    //  联系方式
    private String phoneNumber;
    //  企业信用代码
    private String creditCode;
    //是否可修改标志
    private String isModify = "false";

    private Date updateDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getIsModify() {
        return isModify;
    }

    public void setIsModify(String isModify) {
        this.isModify = isModify;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Date getResumedWorkDate() {
        return resumedWorkDate;
    }

    public void setResumedWorkDate(Date resumedWorkDate) {
        this.resumedWorkDate = resumedWorkDate;
    }

    public Date getToResumeWorkDate() {
        return toResumeWorkDate;
    }

    public void setToResumeWorkDate(Date toResumeWorkDate) {
        this.toResumeWorkDate = toResumeWorkDate;
    }

    public Integer getPeopleNumber() {
        return peopleNumber;
    }

    public void setPeopleNumber(Integer peopleNumber) {
        this.peopleNumber = peopleNumber;
    }

    public Integer getResumedWorkNumber() {
        return resumedWorkNumber;
    }

    public void setResumedWorkNumber(Integer resumedWorkNumber) {
        this.resumedWorkNumber = resumedWorkNumber;
    }

    public Integer getNumberMasks() {
        return numberMasks;
    }

    public void setNumberMasks(Integer numberMasks) {
        this.numberMasks = numberMasks;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson == null ? null : contactPerson.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }
}
