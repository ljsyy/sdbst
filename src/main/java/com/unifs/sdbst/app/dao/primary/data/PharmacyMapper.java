package com.unifs.sdbst.app.dao.primary.data;

import com.unifs.sdbst.app.bean.data.Pharmacy;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface PharmacyMapper {
    int deleteByPrimaryKey(String id);

    int insert(Pharmacy record);

    Pharmacy selectByPrimaryKey(String id);

    List<Pharmacy> selectAll();

    int updateByPrimaryKey(Pharmacy record);

    List<Pharmacy> getNewsList2(int start, int end, String objzjname,String objpname);

}