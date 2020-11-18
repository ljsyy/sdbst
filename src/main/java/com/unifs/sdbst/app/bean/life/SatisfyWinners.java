package com.unifs.sdbst.app.bean.life;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * APP满意度调查中奖名单 Entity
 * */
@Data
public class SatisfyWinners extends DataEntity<SatisfyWinners> {

	private static final long serialVersionUID = 1L;
	
	private String name;		//中奖者名称
	private String loginName;	//APP登录名
	private Integer age;		//年龄
	private String sex;			//性别
	private String phone;		//电话
	private String education;	//学历
	private String industry;	//行业性质：（工厂、公司）
	private String answer;		//答案 205ee4b6c1f34db9a6178e37eaf32b51-A,205ee4b6c1f34db9a6178e37eaf32b51-C
	private String advice;		//建议
	private String otherAdvice;	//其他建议
	private String award;		//奖品
	private Integer awardNum;	//奖品数量，1表示未领奖，0表示已领奖，-1表示无奖品
	private String code;		//领奖码，6位随机数
	private Integer score;		//分数
	private String[] answers;	//答案 A-1 B-0
	private Integer rank;		//排名

	
}
