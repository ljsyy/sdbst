package com.unifs.sdbst.app.dao.primary.push;

import com.unifs.sdbst.app.bean.push.AliPushUser;
import org.springframework.stereotype.Component;

@Component
public interface AliPushUserMapper {
    int insert(AliPushUser record);

    int insertSelective(AliPushUser record);

    int update(AliPushUser record);

    int queryPushUser(AliPushUser record);
}
