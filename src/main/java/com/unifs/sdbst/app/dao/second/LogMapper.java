package com.unifs.sdbst.app.dao.second;

import com.unifs.sdbst.app.base.LogEntity;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface LogMapper {
    int insert(LogEntity record);

    List<LogEntity> selectAll();
}