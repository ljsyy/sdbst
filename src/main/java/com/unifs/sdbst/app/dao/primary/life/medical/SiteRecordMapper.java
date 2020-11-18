package com.unifs.sdbst.app.dao.primary.life.medical;

import com.unifs.sdbst.app.bean.life.medical.SiteRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public interface SiteRecordMapper {
    int insert(SiteRecord record);

    List<SiteRecord> selectAll();

    SiteRecord getSiteRecordById(String siteCode);

    List<Map<String, Object >> getTotalNumber();

    List<Map<String, Object>> getMessageTotal(@Param(value="date1")Date date1, @Param(value="date2")Date date2);
}