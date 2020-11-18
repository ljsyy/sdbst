package com.unifs.sdbst.app.dao.primary.user;


import com.unifs.sdbst.app.bean.user.SurveyResult;
import org.springframework.stereotype.Component;

/**
 * 满意度调查结果Dao
 * */
@Component
public interface SurveyResultMapper{
	//查询所有的调查结果，只查询id answer advice三个字段
	int insert(SurveyResult result);

}
