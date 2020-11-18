package com.unifs.sdbst.app.dao.primary.life.medical;

import com.unifs.sdbst.app.bean.life.medical.PoliceCheck;
import com.unifs.sdbst.app.bean.life.medical.SiteRecord;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface PoliceCheckMapper {
    int insert(PoliceCheck record);

    List<PoliceCheck> selectAll();

    List<PoliceCheck> getPoliceBySysId(String col2);

    void updatePoliceBySysId(PoliceCheck policeCheck);

    List<Map<String, Object>> getMessageTotal(Date date1, Date date2);
}