package com.unifs.sdbst.app.dao.primary.life;

import com.unifs.sdbst.app.bean.life.KsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface KsEntityMapper {
    int insert(KsEntity record);

    List<KsEntity> selectAll();

    //根据type查询
    List<KsEntity> queryByType(@Param("ksh") String ksh, @Param("csrq") String csrq);
}