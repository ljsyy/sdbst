package com.unifs.sdbst.app.dao.primary.fingertips;

import com.unifs.sdbst.app.bean.fingertips.FingertipsRecord;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface FingertipsRecordMapper {
    int insert(FingertipsRecord record);

    List<FingertipsRecord> selectAll();
}