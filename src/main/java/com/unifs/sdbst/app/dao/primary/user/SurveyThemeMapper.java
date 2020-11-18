package com.unifs.sdbst.app.dao.primary.user;


import com.unifs.sdbst.app.bean.user.SurveyTheme;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 满意度调查题目Dao
 */
@Component
public interface SurveyThemeMapper {
    SurveyTheme get(String id);

    int insert(SurveyTheme entity);

    int update(SurveyTheme entity);

    int updateSort(SurveyTheme entity);

    Integer getMaxSort();

    List<SurveyTheme> selectAll();


}
