package com.unifs.sdbst.app.bean.life;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * APP满意度调查的奖品 Entity
 * */
@Data
public class SatisfyAward extends DataEntity<SatisfyAward> {

	private static final long serialVersionUID = 1L;
	
	private String name;		//奖品名称
	private Integer sort;		//排序
	private Integer total;		//总数
	private Integer remain;		//剩余数量
	private Integer out;		//用户已抽中的数量


}
