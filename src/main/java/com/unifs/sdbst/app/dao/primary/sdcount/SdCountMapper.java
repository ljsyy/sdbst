/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.sdcount;

import com.unifs.sdbst.app.bean.sdcount.SdCount;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * aDAO接口
 * @author Chengpeng
 * @version 2017-11-23
 */
@Component
public interface SdCountMapper {
	public SdCount findCountAll(@Param("name") String name, @Param("cDate") String cDate);

	public List<SdCount> findcDate();

	public List<SdCount> findCountMonth(String name);

	public List<SdCount> findList(SdCount sdCount);

	public void delete(SdCount sdCount);

	public void insert(SdCount sdCount);

	public SdCount get(@Param("id") String id);
	//同比
	public SdCount tb(@Param("name") String name, @Param("cDate") String cDate);

	public List<SdCount> findSun(@Param("id") String id, @Param("cDate") String cDate);
	
}