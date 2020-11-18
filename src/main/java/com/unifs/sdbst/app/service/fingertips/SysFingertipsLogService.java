package com.unifs.sdbst.app.service.fingertips;

import com.unifs.sdbst.app.bean.fingertips.FingertipsUserInfo;
import com.unifs.sdbst.app.bean.fingertips.SysFingertipsLog;
import com.unifs.sdbst.app.dao.primary.fingertips.FingertipsUserInfoMapper;
import com.unifs.sdbst.app.dao.second.fingertips.SysFingertipsLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysFingertipsLogService {
    @Autowired
    private SysFingertipsLogMapper sysFingertipsLogMapper;


    @Autowired
    private FingertipsUserInfoMapper fingertipsUserInfoMapper;

    public void insertLog(SysFingertipsLog sysFingertipsLog) {
        sysFingertipsLogMapper.insert(sysFingertipsLog);
    }

    public void insertFingertipsUserInfo(FingertipsUserInfo fingertipsUserInfo) {
        fingertipsUserInfoMapper.insert(fingertipsUserInfo);
    }
}
