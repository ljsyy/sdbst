package com.unifs.sdbst.app.service.life.health;

import com.unifs.sdbst.app.dao.primary.life.health.HealthTableMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HealthTableService {

    @Autowired
    private HealthTableMapper healthTableMapper;
    public List<Map<String, Object >> selectByjiaotong(){
        List<Map<String, Object >> list=healthTableMapper.selectByjiaotong();
        return list;
    }

    public List<Map<String, Object >> selectByHubei(){
        List<Map<String, Object >> list=healthTableMapper.selectByHubei();
        return list;
    }


    public List<Map<String, Object >> selectBihuan(){
        List<Map<String, Object >> list=healthTableMapper.selectBihuan();
        return list;
    }
}
