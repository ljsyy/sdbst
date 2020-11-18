/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.life;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;


/**
 * 周边服务Entity
 * @author liuzibo
 * @version 2018-02-05
 */
@Data
public class SjSurService extends DataEntity<SjSurService> {
	

	private String serviceType;		// 服务类型
	private String companyName;		// 单位名称
	private String companyAdress;		// 单位地址
	private String detailedAdress;		// 详细地址
	private String ownedTown;		// 所属镇街
	private String telephone;		// 对外电话
	private String longitude;		// 经度
	private String latitude;		// 纬度
	private String foodGrading;		// 量化分级等级（餐饮服务单位管理）
	private String foodType;		// 餐饮类型
	private String remarks;			//备注
	private String schoolSection;		// 各级学校学段
	private String mianJi;			//面积（避难场所）
	private String peopleNumber;    //容纳人数（避难场所）
	private String kfTime;			//开放时间（公园）
	private String menPiao;         //门票（公园）
	private double longx;
	private String lableId;
	private String label;
	

}