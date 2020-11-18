package com.unifs.sdbst.app.service.data;

import com.unifs.sdbst.app.bean.data.Hospital;
import com.unifs.sdbst.app.dao.primary.data.HospitalMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class IPHospitalService {

    @Autowired
    private HospitalMapper hospitalMapper;


    public List<Hospital> getNewsList2(int start, int end, String objzjname,String objhname)
    {

        return hospitalMapper.getNewsList2(start,end,objzjname,objhname);
    }
}
