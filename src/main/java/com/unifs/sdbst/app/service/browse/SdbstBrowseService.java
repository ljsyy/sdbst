package com.unifs.sdbst.app.service.browse;

import com.unifs.sdbst.app.bean.browse.SdbstBrowse;
import com.unifs.sdbst.app.dao.second.browse.SdbstBrowseMapper;
import com.unifs.sdbst.app.utils.IdGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SdbstBrowseService {
    @Autowired
    private SdbstBrowseMapper sdbstBrowseMapper;

    @Transactional(readOnly = false)
    public int getData(String dateMonth, String phoneCode, String modelId) {
        int map = sdbstBrowseMapper.getData(dateMonth, phoneCode, modelId);
        return map;
    }
    @Transactional(readOnly = false)
    public List<SdbstBrowse> ipTo(String ipjq) {
        List<SdbstBrowse> reg = sdbstBrowseMapper.ipTo(ipjq);
        return reg;
    }
    @Transactional(readOnly = false)
    public void save(SdbstBrowse sdbstBrowse) {
        sdbstBrowse.setId(IdGen.uuid());
        sdbstBrowse.setSubTable(System.currentTimeMillis());
        sdbstBrowse.setSubLibrary(IdGen.randomLong());
        sdbstBrowseMapper.insert(sdbstBrowse);
    }

    @Transactional(readOnly = false)
    public void insertS(SdbstBrowse sdbstBrowse) {
        sdbstBrowse.setId(IdGen.uuid());
        sdbstBrowse.setSubTable(System.currentTimeMillis());
        sdbstBrowse.setSubLibrary(IdGen.randomLong());
        sdbstBrowseMapper.insertS(sdbstBrowse);
    }
}

