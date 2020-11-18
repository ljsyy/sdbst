package com.unifs.sdbst.app.dao.primary.fever;

import com.unifs.sdbst.app.bean.fever.FeverUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface FeverUserMapper {
    int insert(FeverUser record);

    int insertSelective(FeverUser record);

    FeverUser findUserByMobileAndCid(@Param("mobile") String mobile, @Param("cid") String cid);
}
