package com.unifs.sdbst.app.dao.primary.life.medical;

import com.unifs.sdbst.app.bean.life.medical.InfoRecord;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface InfoRecordMapper {
    int insert(InfoRecord record);

    List<InfoRecord> selectAll();


    List<InfoRecord> getInfoRecordBySite(String siteCode, String name, String phone);

    List<InfoRecord> getInfoRecordBySite2(String siteCode, String name, String phone);

    List<InfoRecord> getInfoRecordBySite3(String siteCode, String name, String phone);

    InfoRecord getInfoRecordById(String id);

    void updateInfoRecordById(InfoRecord infoRecord);

    void updateInfoRecordById2(InfoRecord infoRecord);

    void updateInfoRecordById3(InfoRecord infoRecord);

    void updateInfoRecordBySysId(InfoRecord infoRecord);

    List<InfoRecord> selectInfoRecord();
}