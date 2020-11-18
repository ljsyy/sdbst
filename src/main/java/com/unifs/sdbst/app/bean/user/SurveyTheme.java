package com.unifs.sdbst.app.bean.user;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 满意度调查题目
 * */
@Data
public class SurveyTheme extends DataEntity<SurveyTheme> {

	private static final long serialVersionUID = 1L;
	
	private String title;			//题目
	private Integer sort;			//排序
	private Integer countA=0;		//A.非常满意，选择次数
	private Integer countB=0;		//B.满意，选择次数
	private Integer countC=0;		//C.可以接受，选择次数
	private Integer countD=0;		//D.非常不满意，选择次数
	private Integer countAdvice=0;	//建议的条数
	

	
}
