package com.unifs.sdbst.app.dao.third;

import com.unifs.sdbst.app.bean.user.AppUser;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public interface AppUserMapper {
    int insert(AppUser record);

    int insertSelective(AppUser record);


    Date getBackTime();
}
