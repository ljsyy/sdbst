package com.unifs.sdbst.app.dao.primary.life.medical;

import com.unifs.sdbst.app.bean.life.medical.HospitalClinic;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface HospitalClinicMapper {
    int insert(HospitalClinic record);
    int update(HospitalClinic record);

    List<HospitalClinic> selectAll();

    List<HospitalClinic> selectClilnic(HospitalClinic hospitalClinic);
}