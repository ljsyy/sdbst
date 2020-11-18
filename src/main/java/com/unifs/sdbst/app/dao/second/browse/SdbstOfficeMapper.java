package com.unifs.sdbst.app.dao.second.browse;

import com.unifs.sdbst.app.bean.browse.SdbstOffice;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SdbstOfficeMapper {
    int insert(SdbstOffice record);

    List<SdbstOffice> selectAll();

    SdbstOffice findNewModelId(String modelId);
}