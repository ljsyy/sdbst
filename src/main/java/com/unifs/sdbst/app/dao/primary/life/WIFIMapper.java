package com.unifs.sdbst.app.dao.primary.life;

import com.unifs.sdbst.app.bean.life.WIFI;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface WIFIMapper {

    WIFI selectByPrimaryKey(String id);

    List<WIFI> selectAll();
}