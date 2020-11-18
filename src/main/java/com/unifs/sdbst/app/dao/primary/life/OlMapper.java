package com.unifs.sdbst.app.dao.primary.life;

import com.unifs.sdbst.app.bean.life.OlEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface OlMapper {
	//根据type查询
	 List<OlEntity> queryByType();
	 //
     int insert(@Param("identification") String identification,
                @Param("sex") String sex,
                @Param("name") String name,
                @Param("phone") String phone,
                @Param("qq") String qq,
                @Param("email") String email
     );
}