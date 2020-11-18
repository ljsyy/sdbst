package com.unifs.sdbst.app.dao.primary.user;

import com.unifs.sdbst.app.bean.user.UserOthers;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserOthersMapper {
    int insert(UserOthers record);

    List<UserOthers> selectAll();

    UserOthers selectByFilter(@Param("userId") String userId, @Param("type") String type);

    void delete(String id);

    List<UserOthers> selectByUserId(String userId);

    void update(UserOthers others);
}