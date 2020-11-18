package com.unifs.sdbst.app.dao.primary.data;

import com.unifs.sdbst.app.bean.data.CateringServiceUnitEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version V1.0
 * @title: CateringServiceUnitMapper
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/5 16:45
 */
@Component
public interface CateringServiceUnitMapper {
    List<CateringServiceUnitEntity> selectListByFactor(@Param("objzjname") String objzjname, @Param("objjyjname") String objjyjname,
                                                       @Param("gradename") String gradename, @Param("start") int start, @Param("end") int end);
}
