package com.unifs.sdbst.app.service.user;


import com.unifs.sdbst.app.bean.user.SurveyResult;
import com.unifs.sdbst.app.bean.user.SurveyTheme;
import com.unifs.sdbst.app.dao.primary.appinfo.AppVersionMapper;
import com.unifs.sdbst.app.dao.primary.user.SurveyResultMapper;
import com.unifs.sdbst.app.dao.primary.user.SurveyThemeMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 满意度调查题目Service
 * */
@Service
@Transactional(readOnly = true)
public class SurveyThemeService {
	@Autowired
	private SurveyThemeMapper surveyThemeMapper;
	@Autowired
	private SurveyResultMapper surveyResultMapper;

	public SurveyTheme get(String id) {
		if(StringUtils.isNotBlank(id)) {
			return surveyThemeMapper.get(id);
		}else {
			return null;
		}
		
	}
	
	public int saveOrUpdate(SurveyTheme entity) {
		if(StringUtils.isBlank(entity.getId())) {
			//插入
			entity.preInsert();
			return surveyThemeMapper.insert(entity);
		}else {
			//修改
			return surveyThemeMapper.update(entity);
		}
	}
	
	public int updateSort(SurveyTheme entity) {
		return surveyThemeMapper.updateSort(entity);
	}
	
	public Integer getMaxSort() {
		return surveyThemeMapper.getMaxSort();
	}
	
	public List<SurveyTheme> findList(){
		return surveyThemeMapper.selectAll();
	}

	public void save(SurveyResult result){
		result.preInsert();
		surveyResultMapper.insert(result);
	};
	
}

