package com.unifs.sdbst.app.dao.primary.menus;

import com.unifs.sdbst.app.bean.menus.HealthCare;
import com.unifs.sdbst.app.bean.menus.HealthExplain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 佛山市公立医院基本医疗服务项目和价格Dao
 */
@Component
public interface HealthCareMapper {
    //一级列表
    List<String> oneNumList();

    //根据一级列表查询二级列表
    List<String> twoNumList(HealthCare entity);

    //根据oneNum查询二级
    List<HealthCare> getTwoList(@Param("oneNum") String oneNum);

    //根据twoNum查询三级
    List<HealthCare> getThreeList(@Param("twoNum") String twoNum);

    //根据threeNum查询四级
    List<HealthCare> getFourList(@Param("threeNum") String threeNum);

    //根据oneNum查询说明
    List<HealthExplain> getExplain(@Param("oneNum") String oneNum);

    //根据关键字查询
    List<HealthCare> getKeyWord(@Param("keyword") String keyword);

    List<HealthCare> findList(HealthCare healthCare);

}
