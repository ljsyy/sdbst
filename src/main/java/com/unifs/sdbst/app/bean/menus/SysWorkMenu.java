package com.unifs.sdbst.app.bean.menus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
@ToString
public class SysWorkMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;		// 办事事项名称
    private String href;		// 链接
    private String mobileIcon;	// 手机图标
    private String mobileShow;	// 移动端可见（1：显示、0：不显示）
    private String wxShow;		// 微信端可见（1：显示、0：不显示）
    private String menuIds;		// 菜单（MENU）ID
    private String type;		// 菜单（MENU）列表样式标识
    private String color;		// 方块背景颜色
    private String content;		// 內容簡介
    private Integer sort;			// 排序
    private int tjl;
    private String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）

    private String id;//实体编号（唯一标识）

    private String openWay; //

    public String getOpenWay() {
        return openWay;
    }

    public void setOpenWay(String openWay) {
        this.openWay = openWay;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setId(String id) {
        this.id = id;
    }
    @JsonIgnore
    @Length(min=1, max=1)
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Length(min=0, max=64, message="办事事项名称长度必须介于 0 和 64 之间")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min=0, max=1024, message="链接长度必须介于 0 和 1024 之间")
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Length(min=0, max=64, message="手机图标长度必须介于 0 和 64 之间")
    public String getMobileIcon() {
        return mobileIcon;
    }

    public void setMobileIcon(String mobileIcon) {
        this.mobileIcon = mobileIcon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min=0, max=1, message="移动端显示/隐藏长度必须介于 0 和 1 之间")
    public String getMobileShow() {
        return mobileShow;
    }

    public void setMobileShow(String mobileShow) {
        this.mobileShow = mobileShow;
    }

    @Length(min=0, max=1, message="微信端显示/隐藏长度必须介于 0 和 1 之间")
    public String getWxShow() {
        return wxShow;
    }

    public void setWxShow(String wxShow) {
        this.wxShow = wxShow;
    }

    @Length(min=0, max=64, message="菜单（MENU）ID长度必须介于 0 和 64 之间")
    public String getMenuIds() {
        return menuIds;
    }

    public void setMenuIds(String menuIds) {
        this.menuIds = menuIds;
    }

    public int getTjl() {
        return tjl;
    }

    public void setTjl(int tjl) {
        this.tjl = tjl;
    }


}