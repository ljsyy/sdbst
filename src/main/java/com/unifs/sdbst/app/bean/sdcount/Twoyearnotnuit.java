/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.sdcount;

import com.unifs.sdbst.app.annotation.ExcelField;
import org.hibernate.validator.constraints.Length;

/**
 * cEntity
 * @author cp
 * @version 2017-12-18
 */
public class Twoyearnotnuit  {
	
	private static final long serialVersionUID = 1L;
	private String yearDate;		// year_date
	private String name;		// 名称
	private String firstNum;		// 上年
	private String lastNum;		// 上上年
	private String type;		// 类别
	private String parentType;		// 父类别
	private String bigType;		// big_type
	private String zzl;//增长率
	private String id;//实体编号（唯一标识）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Twoyearnotnuit() {
		super();
	}


	@ExcelField(title = "日期", align = 2, sort = 1)
	@Length(min=0, max=64, message="year_date长度必须介于 0 和 64 之间")
	public String getYearDate() {
		return yearDate;
	}

	public void setYearDate(String yearDate) {
		this.yearDate = yearDate;
	}
	@ExcelField(title = "名称", align = 2, sort = 2)
	@Length(min=0, max=64, message="名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(title = "本年", align = 2, sort = 3)
	public String getFirstNum() {
		return firstNum;
	}

	public void setFirstNum(String firstNum) {
		this.firstNum = firstNum;
	}
	@ExcelField(title = "上年", align = 2, sort = 4)
	public String getLastNum() {
		return lastNum;
	}

	public void setLastNum(String lastNum) {
		this.lastNum = lastNum;
	}
	@ExcelField(title = "类别", align = 2, sort = 6)
	@Length(min=0, max=64, message="类别长度必须介于 0 和 64 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@ExcelField(title = "父类", align = 2, sort = 7)
	@Length(min=0, max=64, message="父类别长度必须介于 0 和 64 之间")
	public String getParentType() {
		return parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}
	@ExcelField(title = "大类别", align = 2, sort = 8)
	@Length(min=0, max=64, message="big_type长度必须介于 0 和 64 之间")
	public String getBigType() {
		return bigType;
	}

	public void setBigType(String bigType) {
		this.bigType = bigType;
	}
	@ExcelField(title = "增长率", align = 2, sort = 5)
	public String getZzl() {
		return zzl;
	}

	public void setZzl(String zzl) {
		this.zzl = zzl;
	}
	
}