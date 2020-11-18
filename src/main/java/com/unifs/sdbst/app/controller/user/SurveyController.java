package com.unifs.sdbst.app.controller.user;


import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.user.SurveyResult;
import com.unifs.sdbst.app.bean.user.SurveyTheme;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.user.SurveyThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 满意度调查
 * */
@Controller
@RequestMapping(value = "/app/survey")
class SurveyController{
	
	@Autowired
	private SurveyThemeService surveyThemeService;

	//满意度调查
	@RequestMapping(value = "/page")
	@ControlLog(operateType = "/page", context = "满意度调查")       //日志记录注解
	public String title(Model model) {

		//获取题目
		List<SurveyTheme> list= surveyThemeService.findList();
		model.addAttribute("list", list);
		return "/app/user/survey";
	}
	
	//满意度调查提交
	@ControlLog(operateType = "/submiit", context = "满意度调查问卷提交")       //日志记录注解
	@RequestMapping(value = "/submit")
	@ResponseBody
	public Resp submit(SurveyResult result) {
		//保存数据
		surveyThemeService.save(result);
		Resp resp=new Resp(RespCode.SUCCESS);
		return resp;
	}
	
	
}


