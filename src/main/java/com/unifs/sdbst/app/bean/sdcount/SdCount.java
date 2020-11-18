/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.sdcount;

import com.unifs.sdbst.app.annotation.ExcelField;
import com.unifs.sdbst.app.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * aEntity
 * @author chengpeng
 * @version 2017-11-23
 */
public class SdCount extends DataEntity<SdCount> {
	
	private static final long serialVersionUID = 1L;
	private String cDate;		// 日期
	private String name;		// 指标名称
	private Double total;		// 总量
	private String nuit;		// 单位
	private Double growthRate;		// 增长
	private Date beginCreateTime;
	private Double hbRate;
	private Double monthTotal;
	private Double tbRate;
	public SdCount() {
		super();
	}

	public SdCount(String id){
		super(id);
	}
	@ExcelField(title = "日期", align = 2, sort = 1)
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public String getcDate() {
		return cDate;
	}

	public void setcDate(String cDate) {
		this.cDate = cDate;
	}
	@ExcelField(title = "字段2", align = 2, sort = 2)
	@Length(min=0, max=64, message="指标名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(title = "字段4", align = 2, sort = 3)
	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}
	@ExcelField(title = "单位", align = 2, sort = 4)
	@Length(min=0, max=64, message="单位长度必须介于 0 和 64 之间")
	public String getNuit() {
		return nuit;
	}

	public void setNuit(String nuit) {
		this.nuit = nuit;
	}
	@ExcelField(title = "字段5", align = 2, sort = 5)
	public Double getGrowthRate() {
		return growthRate;
	}

	public void setGrowthRate(Double growthRate) {
		this.growthRate = growthRate;
	}

	public Date getBeginCreateTime() {
		return beginCreateTime;
	}

	public void setBeginCreateTime(Date beginCreateTime) {
		this.beginCreateTime = beginCreateTime;
	}
	@ExcelField(title = "环比", align = 2, sort = 8)
	public Double getHbRate() {
		return hbRate;
	}

	public void setHbRate(Double hbRate) {
		this.hbRate = hbRate;
	}
	@ExcelField(title = "总量", align = 2, sort = 7)
	public Double getMonthTotal() {
		return monthTotal;
	}

	public void setMonthTotal(Double monthTotal) {
		this.monthTotal = monthTotal;
	}
	@ExcelField(title = "同比", align = 2, sort =6 )
	public Double getTbRate() {
		return tbRate;
	}

	public void setTbRate(Double tbRate) {
		this.tbRate = tbRate;
	}


	
}