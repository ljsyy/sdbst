package com.unifs.sdbst.app.dao.primary.life;


import com.unifs.sdbst.app.bean.life.ExamCalendar;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * 考试日历
 * @author Panxiyi
 * @version 2018-6-1
 */

@Component
public interface ExamCalendarMapper {

	//查询全部
	List<ExamCalendar> getAll();

	//添加
	int insert(ExamCalendar examCalendar);

	//修改
	int update(ExamCalendar examCalendar);

	//删除
	int delete(ExamCalendar examCalendar);

	//根据id查询
	List<ExamCalendar> getById(ExamCalendar examCalenda);
	

}
