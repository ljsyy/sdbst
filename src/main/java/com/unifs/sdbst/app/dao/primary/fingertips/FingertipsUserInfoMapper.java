package com.unifs.sdbst.app.dao.primary.fingertips;

import com.unifs.sdbst.app.bean.fingertips.FingertipsUserInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface FingertipsUserInfoMapper {
    int insert(FingertipsUserInfo record);

    List<FingertipsUserInfo> selectAll();
}