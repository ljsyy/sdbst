package com.unifs.sdbst.app.dao.primary.life;

import com.unifs.sdbst.app.bean.life.SmallFoodWorkshop;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SmallFoodWorkshopMapper {
    int deleteByPrimaryKey(String registerCode);

    int insert(SmallFoodWorkshop record);

    SmallFoodWorkshop selectByKeyword(String keyword);

    List<SmallFoodWorkshop> selectAll();

    int updateByPrimaryKey(SmallFoodWorkshop record);
}