package com.unifs.sdbst.app.dao.second.server;

import com.unifs.sdbst.app.bean.server.HardLog;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface HardLogDao {
    int insert(HardLog record);

    int insertSelective(HardLog record);

    int update();

    List<HardLog> selectNewLog(HardLog record);
}