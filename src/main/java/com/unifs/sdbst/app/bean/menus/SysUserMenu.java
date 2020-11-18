/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.bean.menus;


import com.unifs.sdbst.app.common.entity.DataEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户目录Entity
 * @author ctj
 * @version 2017-08-30
 */
public class SysUserMenu extends DataEntity<SysUserMenu> {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String menuIds;		// menu_ids
	private List<Menu> menuList;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	@Length(min=1, max=2000, message="menu_ids长度必须介于 1 和 2000 之间")
	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	
	public List<String> getMenuIdsList(){
		List<String> list=new ArrayList<>();
		if(!StringUtils.isEmpty(menuIds)){
			String[] split = menuIds.split(",");
			 list = Arrays.asList(split);			
		}
		return list;
	}
	
	public void setMenuIdsList(List<String> list) {
		menuIds=","+ StringUtils.join(list, ",")+",";
	}
	
}