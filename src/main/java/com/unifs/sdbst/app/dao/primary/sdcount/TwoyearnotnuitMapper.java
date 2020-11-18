/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.sdcount;

import com.unifs.sdbst.app.bean.sdcount.Twoyearnotnuit;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * cDAO接口
 * @author cp
 * @version 2017-12-19
 */
@Component
public interface TwoyearnotnuitMapper {
	Twoyearnotnuit year(@Param("type") String type,
							   @Param("year") String year,
							   @Param("name") String name);
	List<Twoyearnotnuit> yearList(@Param("type") String type,
                                         @Param("year") String year,
                                         @Param("name") String name);
	List<Twoyearnotnuit> yearzl(@Param("type") String type,
                                       @Param("year") String year,
                                       @Param("parentType") String parentType,
                                       @Param("name") String name);
}