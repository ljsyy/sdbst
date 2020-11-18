package com.unifs.sdbst.app.dao.primary.enterprise;

import com.unifs.sdbst.app.bean.enterprise.EnterpriseUser;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EnterpriseUserMapper {
    int deleteByPrimaryKey(String creditCode);

    int insert(EnterpriseUser record);

    EnterpriseUser selectByPrimaryKey(String creditCode);

    List<EnterpriseUser> selectAll();

    int updateByPrimaryKey(EnterpriseUser record);
}