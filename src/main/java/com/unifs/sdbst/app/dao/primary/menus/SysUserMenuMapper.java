/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.menus;


import com.unifs.sdbst.app.bean.menus.SysUserMenu;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysUserMenuMapper {

	int insert(SysUserMenu record);

	List<SysUserMenu> selectAll();

	SysUserMenu get(String id);

	int update(SysUserMenu entity);

}
