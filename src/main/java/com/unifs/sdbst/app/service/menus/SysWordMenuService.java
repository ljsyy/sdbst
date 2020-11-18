/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.menus;

import com.unifs.sdbst.app.bean.menus.SysWorkMenu;
import com.unifs.sdbst.app.dao.primary.menus.SysWorkMenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 用户目录Service
 * @author ctj
 * @version 2017-08-30
 */
@Service
@Transactional(readOnly = true)
public class SysWordMenuService {


	@Autowired
	private SysWorkMenuMapper sysWorkMenuMapper;


//	public SysWorkMenu get(String id) {
//		return sysWorkMenuMapper.get(id);
//	}

	@Transactional(readOnly = false)
	public List<SysWorkMenu> getListName(){ //查询一键办事中的办事事项名称
		return sysWorkMenuMapper.getListName();
	}

}

