package com.unifs.sdbst.app.service.traffic;

import com.unifs.sdbst.app.bean.traffic.Digital;
import com.unifs.sdbst.app.dao.primary.traffic.DigitalMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DigitalService {

    @Autowired
    private DigitalMapper digitalMapper;

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

    public List<Digital> getAll(String lat, String lng){
        List<Digital> list = new ArrayList<Digital>();
        list = digitalMapper.getAll();
        for (Digital cd : list) {
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

    	/*Collections.sort(list, new Comparator<Digital>(){

             * int compare(Student o1, Student o2) 返回一个基本类型的整型，
             * 返回负数表示：o1 小于o2，
             * 返回0 表示：o1和o2相等，
             * 返回正数表示：o1大于o2。

          	public int compare(Digital o1, Digital o2) {

                //按照学生的年龄进行升序排列
                if(o1.getLongx() > o2.getLongx()){
                    return 1;
                }
                if(o1.getLongx() == o2.getLongx()){
                    return 0;
                }
                return -1;
            }
        });  */

        Digital temp=null;
        for(int i=0; i<list.size()-1; i++) {
            for(int j=i+1; j<list.size(); j++) {
                if(list.get(i).getLongx()>list.get(j).getLongx()) {
                    temp=list.get(i);
                    list.set(i, list.get(j));
                    list.set(j, temp);
                }
            }
        }

    	/*if(list.size() < 10)
    		return list;
    	else {
    		List<Coordinate> list1 = new ArrayList<Coordinate>();
    		for (Coordinate cd : list) {
    			if(cd.getLongx() < 2)
    				list1.add(cd);
    		}
    		List<Digital> list1 = list.subList(0, 10);
    		return list1;
    	}*/
        List<Digital> list1 = list.subList(0, 100);
        return list1;
    }

    @Transactional(readOnly = false)
    public List<Digital> getType() {
        return digitalMapper.getAllType();
    }

    @Transactional(readOnly = false)
    public List<Digital> getByType(String typeId) {
        return digitalMapper.getByType(typeId);
    }

    @Transactional(readOnly = false)
    public List<Digital> getAll() {
        return digitalMapper.getAll();
    }

}
