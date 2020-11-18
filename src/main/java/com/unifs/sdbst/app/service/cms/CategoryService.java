/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.cms;

import com.unifs.sdbst.app.bean.cms.Category;
import com.unifs.sdbst.app.dao.primary.cms.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 栏目Service
 * @author ThinkGem
 * @version 2013-5-31
 */
@Service
@Transactional(readOnly = true)
public class CategoryService {
	
	@Autowired
	private CategoryMapper categoryMapper;

	/**
	 * 首页栏目列表
	 * @param category
	 * @return
	 */
	public List<Map<String,Object>> homeColumn(Category category){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		List<Category> findModule = categoryMapper.findModule(category);
		if(findModule!=null){
			for (Category c : findModule) {
				LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("id", c.getId());
				map.put("name", c.getName());
				list.add(map);
			}
		}
		return list;
	}
	
	/**
	 * 查询所有列表
	 * */
	public List<Category> getList(Category category){
		return categoryMapper.getList(category);
	}

	//根据栏目名称获取该栏目的信息
	public Category getCategory(String name){
		return categoryMapper.selectByName(name);
	}
}


