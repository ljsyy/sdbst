package com.unifs.sdbst.app.controller.other;


import com.unifs.sdbst.app.annotation.FormCommit;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.other.QyPollsResult;
import com.unifs.sdbst.app.bean.other.QyPollsResultStatis;
import com.unifs.sdbst.app.bean.other.QyPollsTheme;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.other.QyPollsService;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业投票调查Controller
 * @author pxy 2018-08-31
 * */

@Controller
@RequestMapping(value = "/qy/polls")
public class QyPollsController{
	
	@Autowired
	private QyPollsService qyPollsService;


	private Resp resp;
	
	//满意度调查页面
	@RequestMapping(value = "/title")
	public String title(Model model) {
		//获取题目
		List<QyPollsTheme> list = qyPollsService.findList();
		model.addAttribute("list", list);
		return "/app/government/polls/qyPolls";
	}
	
	//满意度调查提交
	@FormCommit
	@RequestMapping(value = "/submit")
	@ResponseBody
	public Resp submit(QyPollsResult result) {
		//保存数据
		qyPollsService.saveResult(result);
		resp=new Resp(RespCode.SUCCESS);
		return resp;
	}
	
	
	/*统计数据接口*/
	@RequestMapping(value = "/resultTotal")
	@ResponseBody
	public Map<String, Object> resultTotal( Model model) {
		//题目列表
		List<QyPollsTheme> list = qyPollsService.findList();
		//答案列表
		List<QyPollsResult> resultList = qyPollsService.findResultList();
		//统计答案
		int countAdvice=0;
		for(QyPollsResult result : resultList) {
			//统计建议条数
			if(StringUtils.isNotBlank(result.getAdvice())) {
				countAdvice++;
			}
			//统计ABCD选择次数
			String[] answers = result.getAnswers();
			for(int i=0; i<answers.length; i++) {
				//答案 205ee4b6c1f34db9a6178e37eaf32b51-A
				String[] ss = StringUtils.split(answers[i], "-");
				if(ss.length == 2) {
					for(QyPollsTheme theme : list) {
						//找到答案id对应的题目
						if(theme.getId().equals(ss[0])) {
							if("A".equals(ss[1])) {
								theme.setCountA(theme.getCountA()+1);
							}else if("B".equals(ss[1])) {
								theme.setCountB(theme.getCountB()+1);
							}else if("C".equals(ss[1])) {
								theme.setCountC(theme.getCountC()+1);
							}else if("D".equals(ss[1])) {
								theme.setCountD(theme.getCountD()+1);
							}
							break;
						}
					}
				}
			}
		}
		//设置最后一条问答题的建议条数
		list.get(list.size()-1).setCountAdvice(countAdvice);
		
		//计算合计与占比
		int totalA = 0;
		int totalB = 0;
		int totalC = 0;
		int totalD = 0;
		for(int i=0; i<list.size()-1; i++) {
			totalA += list.get(i).getCountA();
			totalB += list.get(i).getCountB();
			totalC += list.get(i).getCountC();
			totalD += list.get(i).getCountD();
		}
		//企业投票调查结果统计
		QyPollsResultStatis resultStatis = new QyPollsResultStatis(totalA, totalB, totalC, totalD);
		model.addAttribute("list", list);
		model.addAttribute("resultStatis", resultStatis);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("totalA", resultStatis.getTotalA());
		map.put("totalB", resultStatis.getTotalB());
		map.put("totalC", resultStatis.getTotalC());
		map.put("totalD", resultStatis.getTotalD());
		return map;
	}
	//统计分析页面
	@RequestMapping(value = "/index")
	public String news() {
		return "/app/government/polls/index";
	}
}


