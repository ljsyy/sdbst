package com.unifs.sdbst.app.service.life;


import com.unifs.sdbst.app.bean.life.Vaccination;
import com.unifs.sdbst.app.dao.primary.life.VaccinationMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class VaccinationService {
	
	@Autowired
	private VaccinationMapper vaccinationMapper;
	
	private static double EARTH_RADIUS = 6371.393;

	private static double rad(double d){
		return d * Math.PI / 180.0;
	}
	
	/** 
     * 计算两个经纬度之间的距离 
     * @param lat1 
     * @param lng1 
     * @param lat2 
     * @param lng2 
     * @return 	16.6896km
     * 			16.67193213118755km
     * 			16.67090km
     */
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2)  
    {  
       double radLat1 = rad(lat1);  
       double radLat2 = rad(lat2);  
       double a = radLat1 - radLat2;      
       double b = rad(lng1) - rad(lng2);  
       double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +   
       Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
       s = s * EARTH_RADIUS;  
       //s = Math.round(s * 1000);  
       return s;  
    }
	
	public List<Vaccination> getAll(String lng, String lat) {
		List<Vaccination> list = new ArrayList<Vaccination>();
		list = vaccinationMapper.getAll();
		for (Vaccination cd : list) {
    		double latx = 0.0,lngx=0.0;
    		if(StringUtils.isBlank(cd.getLat()) && StringUtils.isBlank(cd.getLng())) {
    			latx = Double.valueOf(cd.getLat());
    			lngx = Double.valueOf(cd.getLng());
    		}else {
    			latx = Double.valueOf(cd.getLat());
    			lngx = Double.valueOf(cd.getLng());
    		}
			cd.setLongx(GetDistance(latx,lngx,Double.valueOf(lat),Double.valueOf(lng)));
		}
		Vaccination temp=null;
    	for(int i=0; i<list.size()-1; i++) {
    		for(int j=i+1; j<list.size(); j++) {
    			if(list.get(i).getLongx()>list.get(j).getLongx()) {
    				temp=list.get(i);
    				list.set(i, list.get(j));
    				list.set(j, temp);
    			}
    		}
    	}
    	return list;
	}



}
