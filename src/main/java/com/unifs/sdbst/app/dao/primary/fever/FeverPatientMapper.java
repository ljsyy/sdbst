package com.unifs.sdbst.app.dao.primary.fever;

import com.unifs.sdbst.app.bean.fever.FeverPatient;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface FeverPatientMapper {
    int insert(FeverPatient record);

    int insertSelective(FeverPatient record);

    List<Map<String, String>> patientCount(String userId, String startTime, String endTime);

    List<FeverPatient> patientRecord(@Param("userId") String userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

    void updatePatientRecord(FeverPatient feverPatient);
}
