package com.unifs.sdbst.app.service.user;

import com.unifs.sdbst.app.bean.life.Satisfaction;
import com.unifs.sdbst.app.dao.primary.life.AppSatisfactionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @version V1.0
 * @title: SatisfactionService
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/8/28 11:42
 */
@Service
@Transactional
public class SatisfactionService {
    @Autowired
    private AppSatisfactionMapper satisfactionMapper;

    public Satisfaction get(String id) {
        if(StringUtils.isNotBlank(id)) {
            return satisfactionMapper.get(id);
        }else {
            return null;
        }

    }

    public int saveOrUpdate(Satisfaction satisfaction) {
        if(StringUtils.isBlank(satisfaction.getId())) {
            //插入
            satisfaction.preInsert();
            satisfaction.setCountA(0);
            satisfaction.setCountB(0);
            satisfaction.setCountC(0);
            satisfaction.setCountD(0);
            return satisfactionMapper.insert(satisfaction);
        }else {
            //修改
            return satisfactionMapper.update(satisfaction);
        }
    }

    public int updateSort(Satisfaction entity) {
        return satisfactionMapper.updateSort(entity);
    }

    public int clearCount() {
        return satisfactionMapper.clearCount();
    }

    public Integer getMaxSort() {
        return satisfactionMapper.getMaxSort();
    }

    //修改ABCD的选择次数
    public Integer updateCount(Satisfaction entity) {
        return satisfactionMapper.updateCount(entity);
    }

    public List<Satisfaction> findList(Satisfaction entity) {
        List<Satisfaction> list=satisfactionMapper.findList(entity);
        //计算选择次数最多的答案
        if(list!=null && list.size()>0) {
            for(Satisfaction satisfaction: list) {
                int maxCount=satisfaction.getCountA();	//最大的选择次数
                if(satisfaction.getCountB() > maxCount) {
                    maxCount=satisfaction.getCountB();
                }
                if(satisfaction.getCountC() > maxCount) {
                    maxCount=satisfaction.getCountC();
                }
                if(satisfaction.getCountD() > maxCount) {
                    maxCount=satisfaction.getCountD();
                }
                String maxOption="";	//选择次数最多的答案A B C D
                if(maxCount!=0) {
                    if(maxCount == satisfaction.getCountA()) {
                        maxOption+="A";
                    }
                    if(maxCount == satisfaction.getCountB()) {
                        maxOption+="B";
                    }
                    if(maxCount == satisfaction.getCountC()) {
                        maxOption+="C";
                    }
                    if(maxCount == satisfaction.getCountD()) {
                        maxOption+="D";
                    }
                }
                satisfaction.setMaxOption(maxOption);
            }
        }
        return list;
    }
}
