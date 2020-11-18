package com.unifs.sdbst.app.dao.primary.data;

import com.unifs.sdbst.app.bean.data.EpidemicData;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface EpidemicDataMapper {
    int insert(EpidemicData record);

    List<EpidemicData> selectAll();

    List<Integer> selectAmountByType(int type);

    List<String> selectDateByType(int tyep);
}