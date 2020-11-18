/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.cms;


import com.unifs.sdbst.app.bean.cms.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryMapper {

	int insert(Category record);

	List<Category> selectAll();

	Category get(String id);

	List<Category> findByParentIdsLike(Category entity);

	List<Category> findModule(Category category);

	List<Category> getList(Category category);

	Category selectByName(String name);
}
