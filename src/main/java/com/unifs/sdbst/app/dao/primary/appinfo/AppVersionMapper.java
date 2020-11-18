package com.unifs.sdbst.app.dao.primary.appinfo;

import com.unifs.sdbst.app.bean.appinfo.AppVersion;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface AppVersionMapper {
    int deleteByPrimaryKey(String id);

    int insert(AppVersion record);

    AppVersion selectByPrimaryKey(String id);

    List<AppVersion> selectAll();

    int updateByPrimaryKey(AppVersion record);

    List<AppVersion> findList(AppVersion appVersion);

    List<AppVersion> findAndroid(AppVersion appVersion);
}