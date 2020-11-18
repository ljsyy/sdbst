package com.unifs.sdbst.app.dao.primary.traffic;

import com.unifs.sdbst.app.bean.traffic.Digital;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface DigitalMapper {
    int insert(Digital record);

    List<Digital> selectAll();

    List<Digital> getAllType();

    List<Digital> getByType(@Param("typeId")String typeId);

    List<Digital> getAll();
}