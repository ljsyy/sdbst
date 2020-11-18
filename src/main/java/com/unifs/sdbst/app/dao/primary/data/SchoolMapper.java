package com.unifs.sdbst.app.dao.primary.data;

import com.unifs.sdbst.app.bean.data.SchoolEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version V1.0
 * @title: EducationMapper
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/2 15:25
 */
@Component
public interface SchoolMapper {
    List<SchoolEntity> selectListByFactor(@Param("start") int start, @Param("end")int end,
                                          @Param("objzjname")String objzjname, @Param("objschool")String objschool);
}
