package com.unifs.sdbst.app.bean.appinfo;

import java.io.Serializable;
import java.util.Date;

public class AppVersion  implements Serializable {
    private String id;

    private String name;

    private String version;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String remarks;

    private String updateBy;

    private String appUrl;

    private String startUp;

    private String minVersion;

    private String isUpdate;

    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl == null ? null : appUrl.trim();
    }

    public String getStartUp() {
        return startUp;
    }

    public void setStartUp(String startUp) {
        this.startUp = startUp == null ? null : startUp.trim();
    }

    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVsersion) {
        this.minVersion = minVsersion == null ? null : minVsersion.trim();
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate == null ? null : isUpdate.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    @Override
    public String toString() {
        return "AppVersion{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", createDate=" + createDate +
                ", createBy='" + createBy + '\'' +
                ", updateDate=" + updateDate +
                ", remarks='" + remarks + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", appUrl='" + appUrl + '\'' +
                ", startUp='" + startUp + '\'' +
                ", minVersion='" + minVersion + '\'' +
                ", isUpdate='" + isUpdate + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}