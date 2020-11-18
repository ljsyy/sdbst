package com.unifs.sdbst.app.bean.life;

import com.unifs.sdbst.app.common.entity.DataEntity;
import lombok.Data;

/**
 * 疫苗接种医院信息
 * */
@Data
public class Vaccination extends DataEntity<Vaccination> {
	
	private static final long serialVersionUID = 1L;
	
	private String street;			
	private String point;
	private String address;
	private String kind;
	private String time;
	private String hours;
	private String phone;
	private String lng;
	private String lat;
	private double longx;
	

	
}
