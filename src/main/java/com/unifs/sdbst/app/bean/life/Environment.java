package com.unifs.sdbst.app.bean.life;

import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 环境质量定时更新
 * @author Panxiyi
 * @version 2018-5-16
 * */
@Data
public class Environment extends DataEntity<Environment> {
	
	private static final long serialVersionUID = 1L;
	
	private String type;	//类型
	private String result;	//返回Json值

}
