package com.unifs.sdbst.app.dao.primary.user;

import com.unifs.sdbst.app.bean.user.UserSwt;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserSwtMapper {
    int deleteByPrimaryKey(String uid);

    int insert(UserSwt record);

    UserSwt selectByPrimaryKey(String account);

    UserSwt selectByUserId(String userId);

    List<UserSwt> selectAll();

    int updateByPrimaryKey(UserSwt record);
}