package com.unifs.sdbst.app.dao.primary.data;

import com.unifs.sdbst.app.bean.data.Hospital;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface HospitalMapper {
    int deleteByPrimaryKey(String id);

    int insert(Hospital record);

    Hospital selectByPrimaryKey(String id);

    List<Hospital> selectAll();

    int updateByPrimaryKey(Hospital record);

    List<Hospital> getNewsList2(int start, int end, String objzjname,String objhname);
}