package com.unifs.sdbst.app.bean.user;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 满意度调查结果
 * */
@Data
public class SurveyResult extends DataEntity<SurveyResult> {

	private static final long serialVersionUID = 1L;
	
	private String answer;		//答案 205ee4b6c1f34db9a6178e37eaf32b51-A,205ee4b6c1f34db9a6178e37eaf32b51-C
	private String advice;		//建议
	private String phone;		//联系电话
	private String versions;	//APP版本号
	private String type; 		//类型(wx、Android、ios)
	private String[] answers;	//答案
	
}
