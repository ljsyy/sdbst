/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.menus;

import com.unifs.sdbst.app.bean.menus.Dict;
import com.unifs.sdbst.app.dao.primary.menus.DictMapper;
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
public class DictService {


	@Autowired
	private DictMapper dictMapper;

	@Transactional(readOnly = false)
	public List<Dict> findList(Dict dict){
		return dictMapper.findList(dict);
	}

}

