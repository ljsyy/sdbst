package com.unifs.sdbst.app.bean.fever;

import lombok.Data;

import java.util.Date;
@Data
public class FeverPatient {
    private String id;

    private Date createTime;

    private Date updateTime;

    private String patientName;

    private String patientCid;

    private String patientPhone;

    private String patientAddress;

    private String patientTemperature;

    private String operatorUserId;

    private String checkUserId;

    private String status;

    private String delFlag;

    private String checkFlag;

    private String sendAddress;

    private Date checkTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName == null ? null : patientName.trim();
    }

    public String getPatientCid() {
        return patientCid;
    }

    public void setPatientCid(String patientCid) {
        this.patientCid = patientCid == null ? null : patientCid.trim();
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone == null ? null : patientPhone.trim();
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress == null ? null : patientAddress.trim();
    }

    public String getPatientTemperature() {
        return patientTemperature;
    }

    public void setPatientTemperature(String patientTemperature) {
        this.patientTemperature = patientTemperature == null ? null : patientTemperature.trim();
    }

    public String getOperatorUserId() {
        return operatorUserId;
    }

    public void setOperatorUserId(String operatorUserId) {
        this.operatorUserId = operatorUserId == null ? null : operatorUserId.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getCheckUserId() {
        return checkUserId;
    }

    public void setCheckUserId(String checkUserId) {
        this.checkUserId = checkUserId;
    }

    public String getCheckFlag() {
        return checkFlag;
    }

    public void setCheckFlag(String checkFlag) {
        this.checkFlag = checkFlag;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }
}
