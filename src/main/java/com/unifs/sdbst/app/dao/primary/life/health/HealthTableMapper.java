package com.unifs.sdbst.app.dao.primary.life.health;

import com.unifs.sdbst.app.bean.life.health.HealthTable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface HealthTableMapper {
    int insert(HealthTable record);

    List<Map<String, Object >> selectByjiaotong();

    List<Map<String, Object >> selectByHubei();

    List<Map<String, Object >> selectBihuan();

}