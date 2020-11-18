package com.unifs.sdbst.app.service.user;

import com.unifs.sdbst.app.bean.user.Advice;
import com.unifs.sdbst.app.dao.primary.user.AdviceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @version V1.0
 * @title: AdviceService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/8/28 11:23
 */
@Service
@Transactional
public class AdviceService {
    @Autowired
    private AdviceMapper adviceMapper;

    public void save(Advice advice){
        advice.preInsert();
        advice.setCreateDate(new Date());
        adviceMapper.insert(advice);
    }
}
