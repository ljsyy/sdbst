package com.unifs.sdbst.app.bean.other;

import lombok.Data;

import java.text.DecimalFormat;

/**
 * 企业投票调查结果统计
 * @author pxy 2018-08-31
 * */
@Data
public class QyPollsResultStatis {
	
	private int totalA;			//合计A.非常满意
	private int totalB;			//合计B.满意
	private int totalC;			//合计C.可以接受
	private int totalD;			//合计D.非常不满意
	private double total;		//总计
	private String  percentA;	//百分比
	private String  percentB;	//百分比
	private String  percentC;	//百分比
	private String  percentD;	//百分比
	
	public QyPollsResultStatis() {}
	public QyPollsResultStatis(int totalA, int totalB, int totalC, int totalD) {
		this.totalA = totalA;
		this.totalB = totalB;
		this.totalC = totalC;
		this.totalD = totalD;
		//计算百分比
		this.total = 0.0;
		this.total += totalA;
		this.total += totalB;
		this.total += totalC;
		this.total += totalD;
		DecimalFormat df = new DecimalFormat("#.00%");
		percentA = df.format(this.totalA / this.total);
		percentB = df.format(this.totalB / this.total);
		percentC = df.format(this.totalC / this.total);
		percentD = df.format(this.totalD / this.total);
	}




}
