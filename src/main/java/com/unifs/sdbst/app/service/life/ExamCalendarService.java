package com.unifs.sdbst.app.service.life;


import com.unifs.sdbst.app.bean.life.ExamCalendar;
import com.unifs.sdbst.app.dao.primary.life.ExamCalendarMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
 * 考试日历
 * @author Panxiyi
 * @version 2018-6-1
 */

@Service
@Transactional(readOnly = true)
public class ExamCalendarService {
	
	@Autowired
	private ExamCalendarMapper examCalendarMapper;

	//查询全部
	@Transactional(readOnly = false)
	public List<ExamCalendar> getAll() {
		// TODO Auto-generated method stub
		return examCalendarMapper.getAll();
	}

	public int insert(ExamCalendar examCalendar) {
		// TODO Auto-generated method stub
		if(StringUtils.isBlank(examCalendar.getId())) {
			examCalendar.preInsert();
		}
		return examCalendarMapper.insert(examCalendar);
	}

	public int update(ExamCalendar examCalendar) {
		// TODO Auto-generated method stub
		return examCalendarMapper.update(examCalendar);
	}

	public int delete(ExamCalendar examCalendar) {
		// TODO Auto-generated method stub
		return examCalendarMapper.delete(examCalendar);
	}

	public List<ExamCalendar> getById(ExamCalendar examCalendar) {
		// TODO Auto-generated method stub
		return examCalendarMapper.getById(examCalendar);
	}

}
