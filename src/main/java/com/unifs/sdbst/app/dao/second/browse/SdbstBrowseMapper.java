package com.unifs.sdbst.app.dao.second.browse;

import com.unifs.sdbst.app.bean.browse.SdbstBrowse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SdbstBrowseMapper {
    int insert(SdbstBrowse record);

    List<SdbstBrowse> selectAll();

    // 查询当月是否存在访问
    int getData(@Param("dateMonth") String dateMonth, @Param("phoneCode") String phoneCode,
                @Param("modelId") String modelId);

    List<SdbstBrowse> ipTo(@Param("ipjq")String ipjq);

    void insertS(SdbstBrowse sdbstBrowse);

}