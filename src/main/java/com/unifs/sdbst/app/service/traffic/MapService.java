package com.unifs.sdbst.app.service.traffic;

import com.unifs.sdbst.app.bean.traffic.MapCoordinate;
import com.unifs.sdbst.app.dao.primary.traffic.MapCoordinateMapper;
import com.unifs.sdbst.app.utils.IdGen;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MapService {

    @Autowired
    private MapCoordinateMapper mapDao;

        //private static double EARTH_RADIUS = 6378.137;
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



        public static void main(String[] args) {
            System.out.println(GetDistance(22.977943, 113.16462, 22.948738, 113.27546));
        }

        public List<MapCoordinate> getNearbyTarget(String lat, String lng, String type, String address, String servicePoint){
            List<MapCoordinate> list = new ArrayList<MapCoordinate>();
            if("window".equals(type)) {
                list = mapDao.getByWindow(type);
            }else {
                list = mapDao.getByType(type);
            }
            for (MapCoordinate cd : list) {
                double latx = 0.0,lngx=0.0;
                if(StringUtils.isBlank(cd.getLat()) && StringUtils.isBlank(cd.getLng())) {
                    latx = Double.valueOf(cd.getOldLat());
                    lngx = Double.valueOf(cd.getOldLng());
                }else {
                    latx = Double.valueOf(cd.getLat());
                    lngx = Double.valueOf(cd.getLng());
                }
                cd.setLongx(GetDistance(latx,lngx,Double.valueOf(lat),Double.valueOf(lng)));
            }

            Collections.sort(list, new Comparator<MapCoordinate>(){
                /*
                 * int compare(Student o1, Student o2) 返回一个基本类型的整型，
                 * 返回负数表示：o1 小于o2，
                 * 返回0 表示：o1和o2相等，
                 * 返回正数表示：o1大于o2。
                 */
                public int compare(MapCoordinate o1, MapCoordinate o2) {

                    //按照学生的年龄进行升序排列
                    if(o1.getLongx() > o2.getLongx()){
                        return 1;
                    }
                    if(o1.getLongx() == o2.getLongx()){
                        return 0;
                    }
                    return -1;
                }
            });

            if(list.size() < 10 || "driverSchool".equals(type)) {
                return list;
            }else {
                List<MapCoordinate> list1 = list.subList(0, 100);
                return list1;
            }

    	/*List<MapCoordinate> list1 = list.subList(0, 50);
		return list1;*/
        }



        public Map<String, String> getOrInsert(String id, String lat, String lng, String type) {
            MapCoordinate cdn = null;
            if(StringUtils.isBlank(id))
                return null;
            cdn = mapDao.getByOldId(id);
            if(cdn==null) {
                cdn = new MapCoordinate();
                cdn.setOldId(id);
                cdn.setOldLat(lat);
                cdn.setOldLng(lng);
                cdn.setType(type);

                cdn.setId(IdGen.uuid());
                cdn.setCreateDate(new Date());
                mapDao.insert(cdn);
            }
            Map<String,String> map = new HashMap<String,String>();
            if(StringUtils.isBlank(cdn.getLat()) || StringUtils.isBlank(cdn.getLng())) {
                map.put("lat", cdn.getOldLat());
                map.put("lng", cdn.getOldLng());
            }else {
                map.put("lat", cdn.getLat());
                map.put("lng", cdn.getLng());
            }
            return map;
        }

//        public String saveOrUpdate(MapCoordinate entity) {
//            MapCoordinate temp = mapDao.getByOldId( entity.getOldId() );
//            String str="";
//            if(temp == null) {
//                entity.preInsert();
//                mapDao.insert(entity);
//                str= "insert";
//            }else {
//                entity.preUpdate();
//                mapDao.update(entity);
//                str= "update";
//            }
//            return str;
//        }



}
