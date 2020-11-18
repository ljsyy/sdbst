/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.government;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 危化品办事指南Entity
 * @author panxiyi
 * @version 2018-08-13
 */
@Data
public class SysGuide extends DataEntity<SysGuide> {
	
	private static final long serialVersionUID = 1L;
	private String guideNum;		// 事项编号
	private String guideName;		// 事项名称
	private String guideApply;		// 申请资料
	private String guideLaw;		// 相关法律法规
	private String guideFile;	    // 附件
	

}