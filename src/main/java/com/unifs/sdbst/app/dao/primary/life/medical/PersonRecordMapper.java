package com.unifs.sdbst.app.dao.primary.life.medical;

import com.unifs.sdbst.app.bean.life.medical.PersonRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface PersonRecordMapper {
    int insert(PersonRecord record);

    List<PersonRecord> selectAll();

    List<PersonRecord> getPersonByPhone(@Param(value="phone")String phone);
}