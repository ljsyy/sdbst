package com.unifs.sdbst.app.controller.life;


import com.google.common.collect.Maps;
import com.unifs.sdbst.app.bean.life.SjSurService;
import com.unifs.sdbst.app.service.life.SjSurServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 周边服务接口Controller
 * @author liuzibo
 * @version 2018-02-25
 */
@Controller
@RequestMapping(value = "/data/SjSurService")
public class SjSurServiceController{

	@Autowired
	private SjSurServiceService surServiceService;
	
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
       return s;  
    }
    
    @RequestMapping(value = "/getAll")
	@ResponseBody
    public List<SjSurService> getAll(String type, String lat, String lng){
    	 ;
		List<SjSurService> list = surServiceService.getAll(type);
    	for (SjSurService cd : list) {
    		double latx = 0.0,lngx=0.0;
    		if(null == cd.getLatitude() && null == cd.getLongitude()) {
    			latx = Double.valueOf(cd.getLatitude());
    			lngx = Double.valueOf(cd.getLongitude());
    		}else {
    			latx = Double.valueOf(cd.getLatitude());
    			lngx = Double.valueOf(cd.getLongitude());
    		}
			cd.setLongx(GetDistance(latx,lngx,Double.valueOf(lat),Double.valueOf(lng)));
		}
    	
    	SjSurService temp=null;
    	for(int i=0; i<list.size()-1; i++) {
    		for(int j=i+1; j<list.size(); j++) {
    			if(list.get(i).getLongx()>list.get(j).getLongx()) {
    				temp=list.get(i);
    				list.set(i, list.get(j));
    				list.set(j, temp);
    			}
    		}
    	}
    	
    	/*if(list.size() < 10) {
    		return list;
    	}else {
    		List<SjSurService> list1 = list.subList(0, 10);
    		return list1;
    	}*/
    	
    	return list;
    }
    
    @RequestMapping(value = "getTypeList")
   	@ResponseBody
	public Map<String, Object> getTypeList(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> date = Maps.newLinkedHashMap();
		
		List<SjSurService> list = new ArrayList<SjSurService>();
		List<SjSurService> Dep_list = surServiceService.getTypeList();
		date.put("success", true);
		date.put("msg", "操作成功");
		for(SjSurService s : Dep_list){
			list.add(s);
			date.put("obj", list);
		}
		return date;
	}
    
}
