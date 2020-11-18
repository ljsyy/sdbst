package com.unifs.sdbst.app.dao.primary.user;

import com.unifs.sdbst.app.bean.user.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface UserMapper {
    int insert(User record);

    List<User> selectAll();

    User getByLoginName(String loginName);

    int update(User user);

    int updatePhone(User user);

    User selectById(String id);

    User selectByPhone(String phone);

    List<User> selectUserByPhone(String phone);

    List<User> selectByIdentity(String identity);

    User identityIsExist(String identity);

    int updateByIdentity(User user);

    User selectByFactor(@Param("phone") String phone, @Param("identity") String identity,
                        @Param("loginName")String loginName);

    int dropInfo(String token);

    List<User> selectByLimit(@Param("startRow") int startRow,@Param("endRow") int endRow);

    List<User> selectAllByLimit(@Param("startRow") int startRow,@Param("endRow") int endRow);

    List<User> selectIdentityByLimit(@Param("startRow") int startRow,@Param("endRow") int endRow);
}