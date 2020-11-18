package com.unifs.sdbst.app.dao.primary.other;


import com.unifs.sdbst.app.bean.other.QyPollsResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 企业投票调查结果Dao
 *
 * @author pxy 2018-08-31
 */

@Component
public interface QyPollsResultMapper {

    //查询所有的调查结果，只查询id answer advice三个字段
    List<QyPollsResult> findResultList();

    int insert(QyPollsResult result);

}
