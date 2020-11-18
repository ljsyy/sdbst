package com.unifs.sdbst.app.dao.primary.fingertips;

import com.unifs.sdbst.app.bean.fingertips.FingertipsEMS;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
@Component
public interface FingertipsEMSMapper {
    int insert(FingertipsEMS record);

    List<FingertipsEMS> selectAll();

    int findByTxLogisticID(String txLogisticID);

       void updateEMSById(String id, String status, String descs, String mailNum, Date updateDate);

    void updateEMS(FingertipsEMS ems);


    List<FingertipsEMS> getFingertipsEMSByNotEms();

    void updateEMSCodeById(String id,String returnCode);

    void updateStatusById(FingertipsEMS fingertipsEMS);

    FingertipsEMS getFingertipsEMSByCaseId(String caseId);
}