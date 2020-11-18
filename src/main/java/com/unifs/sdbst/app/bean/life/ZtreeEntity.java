package com.unifs.sdbst.app.bean.life;

import lombok.Data;

/**
 * @version V1.0
 * @title: ZtreeEntity
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/8/27 10:37
 */

public class ZtreeEntity {
    private String id;
    private String pId;
    private String name;
    private Boolean isParent;
    private Boolean open;
    private String iconSkin;
    private Boolean isCam;
    private Boolean isOnline;
    private Object data;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return this.pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getParent() {
        return this.isParent;
    }

    public void setParent(Boolean parent) {
        this.isParent = parent;
    }

    public Boolean getOpen() {
        return this.open;
    }

    public void setOpen(Boolean open) {
        this.open = open;
    }

    public String getIconSkin() {
        return this.iconSkin;
    }

    public void setIconSkin(String iconSkin) {
        this.iconSkin = iconSkin;
    }

    public Boolean getCam() {
        return this.isCam;
    }

    public void setCam(Boolean cam) {
        this.isCam = cam;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Boolean getIsOnline() {
        return this.isOnline;
    }

    public void setIsOnline(Boolean online) {
        this.isOnline = online;
    }

    public String toString() {
        return "ZtreeEntity{id='" + this.id + '\'' + ", pId='" + this.pId + '\'' + ", name='" + this.name + '\'' + ", isParent=" + this.isParent + ", open=" + this.open + ", iconSkin='" + this.iconSkin + '\'' + ", isCam=" + this.isCam + ", data=" + this.data + '}';
    }
}
