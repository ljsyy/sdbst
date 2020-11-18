package com.unifs.sdbst.app.bean.menus;

import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 佛山市公立医院基本医疗服务项目和价格
 * */
@Data
public class HealthCare extends DataEntity<HealthCare> {
	
	private static final long serialVersionUID = 1L;
	
	private String oneNum;			//一级编号
	private String oneName;			//一级名称
	private String twoNum;			//二级编号
	private String twoName;			//二级名称
	private String threeNum;		//三级编号
	private String threeName;		//三级名称
	private String fourNum;			//四级编号
	private String fourName;		//四级名称
	private String content;			//项目内涵
	private String otherContent;	//其他内容
	private String unit;			//计价单位
	private String threeExplain;	//三级说明
	private String twoExplain;		//二级说明
	private String threePrice;		//三级价格
	private String twoPrice;		//二级价格
	
	

	
	
}
