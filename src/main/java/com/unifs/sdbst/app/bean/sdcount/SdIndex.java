/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.sdcount;

import com.unifs.sdbst.app.common.entity.DataEntity;
import org.hibernate.validator.constraints.Length;

/**
 * sEntity
 * @author chengpneng
 * @version 2017-11-23
 */
public class SdIndex extends DataEntity<SdIndex> {
	
	private static final long serialVersionUID = 1L;
	private String parent;		// parent_id
	private String name;		// 指标名称
	private String type;		// type
	
	public SdIndex() {
		super();
	}

	public SdIndex(String id){
		super(id);
	}

	@Length(min=0, max=64, message="parent_id长度必须介于 0 和 64 之间")
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
	@Length(min=0, max=64, message="指标名称长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=5, message="type长度必须介于 0 和 5 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}