package com.unifs.sdbst.app.service.other;


import com.unifs.sdbst.app.bean.other.QyPollsResult;
import com.unifs.sdbst.app.bean.other.QyPollsTheme;
import com.unifs.sdbst.app.dao.primary.other.QyPollsMapper;
import com.unifs.sdbst.app.dao.primary.other.QyPollsResultMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 企业投票调查题目Service
 * @author pxy 2018-08-31
 * */

@Service
@Transactional(readOnly = true)
public class QyPollsService{
	@Autowired
	private QyPollsMapper qyPollsMapper;
	@Autowired
	private QyPollsResultMapper qyPollsResultMapper;

	public QyPollsTheme get(String id) {
		if(StringUtils.isNotBlank(id)) {
			return qyPollsMapper.get(id);
		}else {
			return null;
		}
		
	}
	
	public int saveOrUpdate(QyPollsTheme entity) {
		if(StringUtils.isBlank(entity.getId())) {
			//插入
			entity.preInsert();
			return qyPollsMapper.insert(entity);
		}else {
			//修改
			return qyPollsMapper.update(entity);
		}
	}
	
	public int updateSort(QyPollsTheme entity) {
		return qyPollsMapper.updateSort(entity);
	}
	
	public Integer getMaxSort() {
		return qyPollsMapper.getMaxSort();
	}
	//获取调查问卷信息
	public List<QyPollsTheme> findList(){
		String isDel="0";
		return qyPollsMapper.findList(isDel);
	}

	//查询所有的调查结果，只查询id answer advice三个字段
	public List<QyPollsResult> findResultList(){
		List<QyPollsResult> list = qyPollsResultMapper.findResultList();
		for(QyPollsResult result : list) {
			//拆分答案
			String[] answers = StringUtils.split(result.getAnswer(), ",");
			result.setAnswers(answers);
		}
		return list;
	}

	//保存调查结果数据
	public int saveResult(QyPollsResult result){
		result.preInsert();
		return qyPollsResultMapper.insert(result);
	}
}
