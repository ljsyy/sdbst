package com.unifs.sdbst.app.controller.life;


import com.unifs.sdbst.app.bean.life.ExamCalendar;
import com.unifs.sdbst.app.service.life.ExamCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 考试日历 的控制器
 * @author DL04 2017年8月9日
 *
 */

@Controller
@RequestMapping("/calendar")
public class ExamCalendarController {
	@Autowired
	private ExamCalendarService examCalendarService;
	
	//考试日历
	@RequestMapping("/examCalendar")
	public String examCalendar(){
		return "app/life/calendar/examCalendar";
	}
	
	//考试日历 查询
	@RequestMapping(value = "/getExamCalendar")
	@ResponseBody
	public Map<String, Object> getExamCalendar() throws UnsupportedEncodingException {
		Map<String,Object> map = new HashMap<String,Object>();
		ExamCalendar examCalendar = new ExamCalendar();
		List<ExamCalendar> list = examCalendarService.getAll();
		map.put("name", "考试日历");
		map.put("data", list);
		return map;
	}
	
	//考试日历 插入
	@RequestMapping(value = "/insertCalendar")
	@ResponseBody
	public Map<String,Object> insertCalendar(ExamCalendar examCalendar) {
		Map<String,Object> map = new HashMap<String,Object>();
		examCalendarService.insert(examCalendar);
		map.put("state", "插入成功");
		return map;
	}
	
	//考试日历 修改
	@RequestMapping(value = "/updateCalendar")
	@ResponseBody
	public Map<String,Object> updateCalendar(ExamCalendar examCalendar) {
		Map<String,Object> map = new HashMap<String,Object>();
		examCalendarService.update(examCalendar);
		map.put("state", "更新成功");
		return map;
	}
	
	//考试日历 删除
	@RequestMapping(value = "/deleteCalendar")
	@ResponseBody
	public Map<String,Object> deleteCalendar(ExamCalendar examCalendar) {
		Map<String,Object> map = new HashMap<String,Object>();
		examCalendarService.delete(examCalendar);
		map.put("state", "删除成功");
		return map;
	}
	
	//考试日历 根据id查询
	@RequestMapping(value = "/selectOneCalendar")
	@ResponseBody
	public Map<String, Object> selectOneCalendar(ExamCalendar examCalendar){
		Map<String,Object> map = new HashMap<String,Object>();
		List<ExamCalendar> list = examCalendarService.getById(examCalendar);
		map.put("name", "查询成功");
		map.put("data", list);
		return map;
	}
}
