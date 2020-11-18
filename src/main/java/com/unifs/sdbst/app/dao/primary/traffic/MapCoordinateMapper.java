package com.unifs.sdbst.app.dao.primary.traffic;

import com.unifs.sdbst.app.bean.traffic.MapCoordinate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface MapCoordinateMapper {
    int insert(MapCoordinate record);

    List<MapCoordinate> selectAll();

    MapCoordinate getByOldId(@Param("id")String id);

    List<MapCoordinate> getByType(@Param("type")String type);

    List<MapCoordinate> getByWindow(@Param("type")String type);
}