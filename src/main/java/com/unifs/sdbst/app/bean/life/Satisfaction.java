package com.unifs.sdbst.app.bean.life;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * APP满意度调查 Entity
 * */
@Data
public class Satisfaction extends DataEntity<Satisfaction> {

	private static final long serialVersionUID = 1L;
	
	private String title;		//题目
	private Integer sort;		//排序
	private String optionA;		//A选择
	private String optionB;		//B选择
	private String optionC;		//C选择
	private String optionD;		//D选择
	private Integer countA;		//A选择次数
	private Integer countB;		//B选择次数
	private Integer countC;		//C选择次数
	private Integer countD;		//D选择次数
	private String maxOption;	//选择次数最多的答案A B C D

	
}
