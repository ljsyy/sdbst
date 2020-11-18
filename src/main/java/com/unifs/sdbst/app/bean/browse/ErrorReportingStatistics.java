/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.browse;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.unifs.sdbst.app.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * 错误信息Entity
 * @author yinxubo
 * @version 2017-12-25
 */
public class ErrorReportingStatistics extends DataEntity<ErrorReportingStatistics> {
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	private String errorMessage;		// 错误信息
	private String moduleName;		// 模块名
	private String wrongTime;		// 报错时间
	private String phoneCode;		// 手机唯一标识
	private String versions;		// 版本号
	private String brand;		// 操作系统
	private String userid;		// 用户id
	private String area;		// 地域
	private  String id;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private	int cuowushu;
	
	public int getCuowushu() {
		return cuowushu;
	}

	public void setCuowushu(int cuowushu) {
		this.cuowushu = cuowushu;
	}

	public ErrorReportingStatistics() {
		super();
	}

	public ErrorReportingStatistics(String id){
		super(id);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Length(min=0, max=255, message="模块名长度必须介于 0 和 255 之间")
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getWrongTime() {
		return wrongTime;
	}

	public void setWrongTime(String wrongTime) {
		this.wrongTime = wrongTime;
	}
	
	@Length(min=0, max=255, message="手机唯一标识长度必须介于 0 和 255 之间")
	public String getPhoneCode() {
		return phoneCode;
	}

	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	
	@Length(min=0, max=255, message="版本号长度必须介于 0 和 255 之间")
	public String getVersions() {
		return versions;
	}

	public void setVersions(String versions) {
		this.versions = versions;
	}
	
	@Length(min=0, max=255, message="操作系统长度必须介于 0 和 255 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Length(min=0, max=255, message="地域长度必须介于 0 和 255 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
}