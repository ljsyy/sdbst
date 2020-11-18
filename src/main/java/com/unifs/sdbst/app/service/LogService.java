package com.unifs.sdbst.app.service;

import com.unifs.sdbst.app.base.LogEntity;
import com.unifs.sdbst.app.dao.second.LogMapper;
import com.unifs.sdbst.app.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @title: LogService
 * @projectName ExcetptionAndValid
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/4/29 12:01
 */
@Service
public class LogService {
    @Autowired
    private LogMapper logMapper;


    public void saveLog(LogEntity logEntity) {
        logEntity.setSubTable(System.currentTimeMillis());
        logEntity.setSubLibrary(IdGen.randomLong());
        logMapper.insert(logEntity);

    }

    public List<LogEntity> getAll() {
        return logMapper.selectAll();
    }
}
