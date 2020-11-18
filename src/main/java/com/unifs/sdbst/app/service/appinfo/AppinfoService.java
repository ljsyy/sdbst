package com.unifs.sdbst.app.service.appinfo;

import com.unifs.sdbst.app.bean.appinfo.AndroidVersion;
import com.unifs.sdbst.app.bean.appinfo.AppVersion;
import com.unifs.sdbst.app.dao.primary.appinfo.AndroidVersionMapper;
import com.unifs.sdbst.app.dao.primary.appinfo.AppVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppinfoService {

    @Autowired
    private AppVersionMapper appVersionMapper;

    @Cacheable(value = "appVersion", key = "'keys'+#appVersion.startUp+#appVersion.type")
    public List<AppVersion> findList(AppVersion appVersion) {
        return appVersionMapper.findList(appVersion);
    }

    public List<AppVersion> findAndroid(AppVersion appVersion) {
        return appVersionMapper.findAndroid(appVersion);
    }


    //Android
    @Autowired
    private AndroidVersionMapper androidVersionMapper;

    @Cacheable(value = "androidAppVersion", key = "'androidKey'")
    public List<AndroidVersion> selectAndroidAll() {
        return androidVersionMapper.selectAll();
    }
}
