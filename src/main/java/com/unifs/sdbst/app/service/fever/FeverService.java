package com.unifs.sdbst.app.service.fever;

import com.unifs.sdbst.app.bean.fever.FeverPatient;
import com.unifs.sdbst.app.bean.fever.FeverUser;
import com.unifs.sdbst.app.dao.primary.fever.FeverPatientMapper;
import com.unifs.sdbst.app.dao.primary.fever.FeverUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class FeverService {

    @Autowired
    private FeverPatientMapper feverPatientMapper;

    @Autowired
    private FeverUserMapper feverUserMapper;

    public void patientInfoSubmit(FeverPatient feverPatient){
        feverPatientMapper.insert(feverPatient);
    }

    public List<Map<String, String>> patientCount(String userId, String startTime, String endTime) {
        return feverPatientMapper.patientCount(userId,startTime,endTime);
    }

    public List<FeverPatient> patientRecord(String userId, String startTime, String endTime) {
        return feverPatientMapper.patientRecord(userId,startTime,endTime);
    }

    public void updatePatientRecord(FeverPatient feverPatient) {
        feverPatientMapper.updatePatientRecord(feverPatient);
    }

    public FeverUser findUserByMobileAndCid(String mobile, String cid) {
        return feverUserMapper.findUserByMobileAndCid(mobile,cid);
    }
}
