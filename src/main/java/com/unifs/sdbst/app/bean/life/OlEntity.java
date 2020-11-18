package com.unifs.sdbst.app.bean.life;


import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

@Data
public class OlEntity extends DataEntity<OlEntity> {
	private static final long serialVersionUID = 1L;
	
	private String identification;	//身份证
	private String sex;	//性别
	private String name;//姓名
	private String phone;//手机
	private String qq;	//qq
	private String email;//邮箱

}