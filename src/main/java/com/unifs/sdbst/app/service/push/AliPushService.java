package com.unifs.sdbst.app.service.push;

import com.unifs.sdbst.app.bean.push.AliPushUser;
import com.unifs.sdbst.app.dao.primary.push.AliPushUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AliPushService {
    @Autowired
    private AliPushUserMapper aliPushUserMapper;

    public int addPushUser(AliPushUser aliPushUser){
        int count=aliPushUserMapper.insert(aliPushUser);
        return count;
    }

    public int updatePushUser(AliPushUser aliPushUser){
        int count=aliPushUserMapper.update(aliPushUser);
        return count;
    }

    public int queryPushUser(AliPushUser aliPushUser){
        int count=aliPushUserMapper.queryPushUser(aliPushUser);
        return count;
    }

}
