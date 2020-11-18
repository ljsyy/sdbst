package com.unifs.sdbst.app.dao.primary.other;


import com.unifs.sdbst.app.bean.other.QyPollsResult;
import com.unifs.sdbst.app.bean.other.QyPollsTheme;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 企业投票调查题目Dao
 *
 * @author pxy 2018-08-31
 */

@Component
public interface QyPollsMapper {
    QyPollsTheme get(String id);

    int insert(QyPollsTheme theme);

    int update(QyPollsTheme theme);

    int updateSort(QyPollsTheme entity);

    Integer getMaxSort();

    List<QyPollsTheme> findList(String isDel);
}

