package com.unifs.sdbst.app.dao.primary.appinfo;

import com.unifs.sdbst.app.bean.appinfo.AndroidVersion;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AndroidVersionMapper {
    int insert(AndroidVersion record);

    List<AndroidVersion> selectAll();
}