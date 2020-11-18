/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.sdcount;

import com.unifs.sdbst.app.bean.sdcount.SdTwoyearhavenuit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 1DAO接口
 * @author cp
 * @version 2017-12-18
 */
@Component
public interface SdTwoyearhavenuitMapper {
	public List<SdTwoyearhavenuit> year(@Param("type") String type,
										@Param("year") String year);
	public SdTwoyearhavenuit yearTwo(@Param("type") String type,
                                     @Param("year") String year,
                                     @Param("name") String name);
	//子类
	public List<SdTwoyearhavenuit> yearTwozl(@Param("type") String type,
                                             @Param("year") String year,
                                             @Param("parentType") String parentType,
                                             @Param("name") String name);
	//通过名称查找
	public SdTwoyearhavenuit findName(@Param("type") String type,
                                      @Param("year") String year,
                                      @Param("name") String name);
	public List<SdTwoyearhavenuit> yearGm(@Param("type") String type,
                                          @Param("year") String year,
                                          @Param("parentType") String parentType);
}