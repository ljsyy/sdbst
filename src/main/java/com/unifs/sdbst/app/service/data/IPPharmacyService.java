package com.unifs.sdbst.app.service.data;

import com.unifs.sdbst.app.bean.data.Pharmacy;
import com.unifs.sdbst.app.dao.primary.data.PharmacyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class IPPharmacyService {

    @Autowired
    private PharmacyMapper pharmacyMapper;


    public List<Pharmacy> getNewsList2(int start, int end, String objzjname,String objpname)
    {

        return pharmacyMapper.getNewsList2(start,end,objzjname,objpname);
    }
}
