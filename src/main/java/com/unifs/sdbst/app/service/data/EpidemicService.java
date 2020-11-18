package com.unifs.sdbst.app.service.data;

import com.unifs.sdbst.app.bean.data.EchartsEpidemic;
import com.unifs.sdbst.app.dao.primary.data.EpidemicDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V1.0
 * @title: EpidemicService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2020/2/4 16:54
 */
@Service
@Transactional
public class EpidemicService {
    @Autowired
    private EpidemicDataMapper dataMapper;

    public EchartsEpidemic echartsData(int type){
        EchartsEpidemic echartsEpidemic=new EchartsEpidemic();
        List<Integer> amounts=dataMapper.selectAmountByType(type);
        echartsEpidemic.setAmounts(amounts);
        echartsEpidemic.setDates(dataMapper.selectDateByType(type));
        echartsEpidemic.setNewCount(amounts.get(amounts.size()-1)+"例");
        return echartsEpidemic;
    }
}
