/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.menus;


import com.unifs.sdbst.app.bean.menus.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 菜单DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@Component
public interface TagMapper {

	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insert(Tag entity);

	/**
	 * 更新数据
	 * @param entity
	 * @return
	 */
	public int update(Tag entity);

	Tag findByDate(@Param("dayStart") Date dayStart, @Param("dayEnd") Date dayEnd);

	
	public  Tag findName(String tag);
	
	public List<Tag> findHotTags();
	public void updateNums(Tag tag);
}
