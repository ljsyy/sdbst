package com.unifs.sdbst.app.dao.second.fingertips;

import com.unifs.sdbst.app.bean.fingertips.SysFingertipsLog;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SysFingertipsLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysFingertipsLog record);

    SysFingertipsLog selectByPrimaryKey(String id);

    List<SysFingertipsLog> selectAll();

    int updateByPrimaryKey(SysFingertipsLog record);
}