package com.unifs.sdbst.app.service.data;

import com.unifs.sdbst.app.bean.data.CateringServiceUnitEntity;
import com.unifs.sdbst.app.dao.primary.data.CateringServiceUnitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version V1.0
 * @title: CateringServiceUnitService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/5 16:43
 */
@Service
public class CateringServiceUnitService {
    @Autowired
    private CateringServiceUnitMapper mapper;

    public List<CateringServiceUnitEntity> getNewsList2(String objzjname, String objjyjname,
                                                        String gradename, int start, int end) {
        return mapper.selectListByFactor(objzjname,objjyjname,gradename,start,end);
    }
}
