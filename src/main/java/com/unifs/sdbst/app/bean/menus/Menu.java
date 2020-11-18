/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.menus;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unifs.sdbst.app.bean.user.User;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 菜单Entity
 *
 * @author ThinkGem
 * @version 2013-05-15
 */
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    private Menu parent;        // 父级菜单
    private String showMenu;   // 菜单类型区分
    private String parentIds;    // 所有父级编号
    private String name;        // 名称
    private String href;        // 链接
    private String target;        // 目标（ mainFrame、_blank、_self、_parent、_top）
    private String icon;        // 图标
    private Integer sort;        // 排序
    private String modelid;    // 统计id
    private String isShow;        // 是否在菜单中显示（1：显示；0：不显示）
    private String mobileShow;    //是否在手机中显示（1：显示；0：不显示）
    private String wxShow;        //是否在手机中显示（1：显示；0：不显示）
    private String permission;    // 权限标识
    private String type;        //列表样式标识
    private String mobileIcon;    //手机图片地址
    private String userId;
    private String disclaimer;    //免责（默认0：否； 1：是）
    private String tag;            // 标签，用于搜索
    private String monitor;        // 监控巡查（1：显示；0：不显示）
    private String remarks;    // 备注
    private User createBy;    // 创建者
    private Date createDate;    // 创建日期
    private User updateBy;    // 更新者
    private Date updateDate;    // 更新日期
    private String delFlag;    // 删除标记（0：正常；1：删除；2：审核）
    private String id;//实体编号（唯一标识）
    private String openWay;
    private String appId;
    private String userName;
    private String path;
    private String useX5;
    private String parentId;
    private List<Menu> children;    //子集
    private String appName;
    private String androidDownload;
    private String iosDownload;

    private String nameList;
    private String hrefList;
    private String mobileIconList;
    private String openwayList;
    private String usernameList;
    private String pathList;
    private String idList;
    private String appNameList;
    private String androidDownloadList;
    private String iosDownloadList;
    private String banner;

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    public String getAppNameList() {
        return appNameList;
    }

    public void setAppNameList(String appNameList) {
        this.appNameList = appNameList;
    }

    public String getAndroidDownloadList() {
        return androidDownloadList;
    }

    public void setAndroidDownloadList(String androidDownloadList) {
        this.androidDownloadList = androidDownloadList;
    }

    public String getIosDownloadList() {
        return iosDownloadList;
    }

    public void setIosDownloadList(String iosDownloadList) {
        this.iosDownloadList = iosDownloadList;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getNameList() {
        return nameList;
    }

    public void setNameList(String nameList) {
        this.nameList = nameList;
    }

    public String getHrefList() {
        return hrefList;
    }

    public void setHrefList(String hrefList) {
        this.hrefList = hrefList;
    }

    public String getMobileIconList() {
        return mobileIconList;
    }

    public void setMobileIconList(String mobileIconList) {
        this.mobileIconList = mobileIconList;
    }

    public String getOpenwayList() {
        return openwayList;
    }

    public void setOpenwayList(String openwayList) {
        this.openwayList = openwayList;
    }

    public String getUsernameList() {
        return usernameList;
    }

    public void setUsernameList(String usernameList) {
        this.usernameList = usernameList;
    }

    public String getPathList() {
        return pathList;
    }

    public void setPathList(String pathList) {
        this.pathList = pathList;
    }

    public boolean isNewRecord() {
        return isNewRecord;
    }

    public void setNewRecord(boolean newRecord) {
        isNewRecord = newRecord;
    }

    public List<Menu> getChildren() {
        return children;
    }

    public void setChildren(List<Menu> children) {
        this.children = children;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getUseX5() {
        return useX5;
    }

    public void setUseX5(String useX5) {
        this.useX5 = useX5;
    }

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    private boolean isNewRecord = false;

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     *
     * @return
     */
    public boolean getIsNewRecord() {
        return isNewRecord || StringUtils.isBlank(getId());
    }

    /**
     * 是否是新记录（默认：false），调用setIsNewRecord()设置新记录，使用自定义ID。
     * 设置为true后强制执行插入语句，ID不会自动生成，需从手动传入。
     */
    public void setIsNewRecord(boolean isNewRecord) {
        this.isNewRecord = isNewRecord;
    }

    public Menu() {
        super();
        this.sort = 30;
        this.monitor = "0";
        this.isShow = "1";
        this.mobileShow = "1";
        this.wxShow = "1";
        this.disclaimer = "0";
        this.openWay = "1";
    }

    public Menu(String id, Menu parent, String parentIds, String name, Integer sort, String isShow, User createBy, User updateBy,
                String delFlag, String mobileShow, String wxShow, Date createDate, Date updateDate, String href, String openWay) {
        this.id = id;
        this.parentIds = parentIds;
        this.parent = parent;
        this.name = name;
        this.sort = sort;
        this.isShow = isShow;
        this.createBy = createBy;
        this.updateBy = updateBy;
        this.delFlag = delFlag;
        this.mobileShow = mobileShow;
        this.wxShow = wxShow;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.href = href;
        this.openWay = openWay;
    }

    @Length(min = 0, max = 255)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @JsonIgnore
    public User getCreateBy() {
        return createBy;
    }

    public void setCreateBy(User createBy) {
        this.createBy = createBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @JsonIgnore
    public User getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(User updateBy) {
        this.updateBy = updateBy;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    @JsonIgnore
    @Length(min = 1, max = 1)
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
//	public Menu(String id){
//		super(id);
//	}
//
//	public Menu(String id, String name){
//		super(id);
//		this.name=name;
//	}


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMonitor() {
        return monitor;
    }

    public void setMonitor(String monitor) {
        this.monitor = monitor;
    }

    public String getOpenWay() {
        return openWay;
    }

    public void setOpenWay(String openWay) {
        this.openWay = openWay;
    }

    public String getModelid() {
        return modelid;
    }

    public void setModelid(String modelid) {
        this.modelid = modelid;
    }

    @JsonBackReference
    @NotNull
    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    @Length(min = 1, max = 2000)
    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    @Length(min = 1, max = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Length(min = 0, max = 2000)
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Length(min = 0, max = 20)
    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    @Length(min = 0, max = 100)
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @NotNull
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Length(min = 1, max = 1)
    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    @Length(min = 1, max = 1)
    public String getWxShow() {
        return wxShow;
    }

    public void setWxShow(String wxShow) {
        this.wxShow = wxShow;
    }

    @Length(min = 1, max = 1)
    public String getMobileShow() {
        return mobileShow;
    }

    public void setMobileShow(String mobileShow) {
        this.mobileShow = mobileShow;
    }

    @Length(min = 0, max = 200)
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @JsonIgnore
    public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Menu e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        Menu child = sourcelist.get(j);
                        if (child.getParent() != null && child.getParent().getId() != null
                                && child.getParent().getId().equals(e.getId())) {
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @JsonIgnore
    public static String getRootId() {
        return "1";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    public String getShowMenu() {
        return showMenu;
    }

    public void setShowMenu(String showMenu) {
        this.showMenu = showMenu;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobileIcon() {
        return mobileIcon;
    }

    public void setMobileIcon(String mobileIcon) {
        this.mobileIcon = mobileIcon;
    }

    public String getDisclaimer() {
        return disclaimer;
    }

    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAndroidDownload() {
        return androidDownload;
    }

    public void setAndroidDownload(String androidDownload) {
        this.androidDownload = androidDownload;
    }

    public String getIosDownload() {
        return iosDownload;
    }

    public void setIosDownload(String iosDownload) {
        this.iosDownload = iosDownload;
    }
}
