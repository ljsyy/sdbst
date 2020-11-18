package com.unifs.sdbst.app.service.browse;

import com.unifs.sdbst.app.bean.browse.SdbstOffice;
import com.unifs.sdbst.app.dao.second.browse.SdbstOfficeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SdbstOfficeService {

    @Autowired
    private SdbstOfficeMapper sdbstOfficeMapper;

    public SdbstOffice findNewModelId(String modelId) {
        return sdbstOfficeMapper.findNewModelId(modelId);
    }
}
