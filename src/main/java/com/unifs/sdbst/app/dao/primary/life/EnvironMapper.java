package com.unifs.sdbst.app.dao.primary.life;


import com.unifs.sdbst.app.bean.life.Environment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface EnvironMapper {

	//插入或更新
	Integer update(@Param("type") String type, @Param("result") String result);

	//根据type查询
	Environment queryByType(@Param("type") String type);

}
