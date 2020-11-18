package com.unifs.sdbst.app.service.data;

import com.unifs.sdbst.app.bean.data.EducationEntity;
import com.unifs.sdbst.app.bean.data.SchoolEntity;
import com.unifs.sdbst.app.dao.primary.data.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rxframework.persistent.cmd.IQueryResult;

import java.util.List;

/**
 * @version V1.0
 * @title: EducationService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/2 9:47
 */
@Service
@Transactional
public class EducationService {
    @Autowired
    private SchoolMapper schoolMapper;

    public List<SchoolEntity> getNewsList2(int start, int end, String objzjname, String objschool) {
        return schoolMapper.selectListByFactor(start, end, objzjname, objschool);

    }


}
