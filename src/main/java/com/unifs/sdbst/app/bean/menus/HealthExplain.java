package com.unifs.sdbst.app.bean.menus;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 佛山市公立医院基本医疗服务项目和价格
 * */
@Data
public class HealthExplain extends DataEntity<HealthExplain> {
	
	private static final long serialVersionUID = 1L;
	
	private String oneNum;			//一级编号
	private String oneName;			//一级名称
	private String oneExplain;		//一级说明
	

	
}
