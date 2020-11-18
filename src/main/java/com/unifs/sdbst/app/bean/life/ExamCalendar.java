package com.unifs.sdbst.app.bean.life;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 考试日历
 * @author Panxiyi
 * @version 2018-6-1
 * */
@Data
public class ExamCalendar extends DataEntity<ExamCalendar> {
	
	private static final long serialVersionUID = 1L;
	
	private String id;				//id
	private String examtime;		//考试时间
	private String examname;		//考试名称
	private String signuptime;		//截止时间
	private String examtimetag;		//考试时间标记
	private String monthtag;		//月份
	

	
}
