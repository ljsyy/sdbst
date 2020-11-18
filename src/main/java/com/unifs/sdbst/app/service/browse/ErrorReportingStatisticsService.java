/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.browse;


import com.unifs.sdbst.app.bean.browse.ErrorReportingStatistics;
import com.unifs.sdbst.app.dao.second.browse.ErrorReportingStatisticsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 错误信息Service
 * @author yinxubo
 * @version 2017-12-25
 */
@Service
@Transactional(readOnly = true)
public class ErrorReportingStatisticsService{

	@Autowired
	private ErrorReportingStatisticsMapper mapper;

	
	@Transactional(readOnly = false)
	public void save(ErrorReportingStatistics errorReportingStatistics) {
		errorReportingStatistics.preInsert();
		mapper.insert(errorReportingStatistics);
	}

}