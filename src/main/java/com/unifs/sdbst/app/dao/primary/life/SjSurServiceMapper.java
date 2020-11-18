/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.life;


import com.unifs.sdbst.app.bean.life.SjSurService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * 周边服务DAO接口
 * @author liuzibo
 * @version 2018-02-05
 */
@Component
public interface SjSurServiceMapper {
	SjSurService get(String id);
	int save(SjSurService sjSurService);
	int delete(SjSurService sjSurService);
	List<SjSurService>getAll(@Param("type") String type);
	List<SjSurService>getTypeList();
	List<SjSurService> findList(SjSurService sjSurService);
}