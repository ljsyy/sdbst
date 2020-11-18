package com.unifs.sdbst.app.dao.second.server;

import com.unifs.sdbst.app.bean.server.DataBackup;
import org.springframework.stereotype.Component;

@Component
public interface DataBackupLogMapper {
    int deleteByPrimaryKey(String id);

    int insert(DataBackup record);

    int insertSelective(DataBackup record);

    DataBackup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataBackup record);

    int updateByPrimaryKey(DataBackup record);
}
