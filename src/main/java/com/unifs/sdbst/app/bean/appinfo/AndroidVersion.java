package com.unifs.sdbst.app.bean.appinfo;

import java.io.Serializable;
import java.util.Date;

public class AndroidVersion implements Serializable {
    private String id;

    private String name;

    private String versioncode;

    private String versionname;

    private String updatestatus;

    private Date createDate;

    private String createBy;

    private Date updateDate;

    private String modifycontent;

    private String updateBy;

    private String downloadurl;

    private String apksize;

    private String apkmd5;

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

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode == null ? null : versioncode.trim();
    }

    public String getVersionname() {
        return versionname;
    }

    public void setVersionname(String versionname) {
        this.versionname = versionname == null ? null : versionname.trim();
    }

    public String getUpdatestatus() {
        return updatestatus;
    }

    public void setUpdatestatus(String updatestatus) {
        this.updatestatus = updatestatus == null ? null : updatestatus.trim();
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

    public String getModifycontent() {
        return modifycontent;
    }

    public void setModifycontent(String modifycontent) {
        this.modifycontent = modifycontent == null ? null : modifycontent.trim();
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl == null ? null : downloadurl.trim();
    }

    public String getApksize() {
        return apksize;
    }

    public void setApksize(String apksize) {
        this.apksize = apksize == null ? null : apksize.trim();
    }

    public String getApkmd5() {
        return apkmd5;
    }

    public void setApkmd5(String apkmd5) {
        this.apkmd5 = apkmd5 == null ? null : apkmd5.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}