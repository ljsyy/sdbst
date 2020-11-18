package com.unifs.sdbst.app.controller;

import com.unifs.sdbst.app.bean.traffic.MapCoordinate;
import com.unifs.sdbst.app.service.traffic.MapService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共接口
 *
 * @author DL04
 */
@Controller
@RequestMapping("api/")
public class ApiController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MapService mapService;
//
//	// 坐标返回
//		/**
//		 * 根据ID查询数据库得到经纬度,若没有查到,则将id,lat,lng插入数据库,返回lat,lng;
//		 * @param id	接口自带的唯一标识，如 ip,PARK_NO
//		 * @param lat	接口自带的纬度，如 lat,PARK_Y
//		 * @param lng	接口自带的经度，如 lng,PARK_X
//		 * @return  返回json数据
//		 * 测试  http://X:8080/shundemap/api/map/getCoordinate?id=4C54F9C1F191478CA26ED8B140A8FD5D&lat=22.932486&lng=113.057243&type=window
//		 */
//		@RequestMapping(value = "map/getCoordinate")
//		@ResponseBody
//		public Map<String,String> getCoordinate(String id,String lat,String lng,String type) {
//			/*String url = "http://202.104.25.196/RXWSSTWeb/m_smzcTerminal.web?getListByNear&pageIndex=";
////			String url = "http://202.104.25.196/RXWSSTWeb/publicBicycle.web?getNearGgzxcList&lontitude=113.39799940928819&latitude=23.13513315162037&keyWord=&pageIndex=";
//			int i=1;
//			while(true) {
//				String json = HttpUtils.sendPost(url+ i++, null);
//				JSONObject jsona = JSONObject.fromObject(json);
//
//				if(jsona.getString("success").equals("true") && jsona.getString("obj")!=null) {
//					JSONArray jsonb = JSONArray.fromObject(jsona.getString("obj"));
//					for (Object object : jsonb) {
//						JSONObject jsonP = JSONObject.fromObject(object);
//						System.out.println(jsonP.getString("id")+"|"+jsonP.getString("lat")+"|"+jsonP.getString("lng"));
//						mapService.getOrInsert(jsonP.getString("id"),jsonP.getString("lat"),jsonP.getString("lng"),"window");
//					}
//				}else {
//					return null;
//				}
//			}*/
//
//			Map<String,String> map = mapService.getOrInsert(id,lat,lng,type);
//
//			return map;
//
//		}


    /**
     * 根据坐标返回最近的10个目标
     *
     * @params [lat, lng, type, address, servicePoint]
     * @return java.util.Map<java.lang.String   ,   java.util.List   <   com.unifs.sdbst.app.bean.traffic.MapCoordinate>>
     * @author 张恭雨
     * @date 2020/5/13
     */
    @RequestMapping(value = "map/getNearbyTarget")
    @ResponseBody
    public Map<String, List<MapCoordinate>> getNearbyTarget(String lat, String lng, String type, String address, String servicePoint) {
        Map<String, List<MapCoordinate>> map = new HashMap<String, List<MapCoordinate>>();
        List<MapCoordinate> list = mapService.getNearbyTarget(lat, lng, type, address, servicePoint);
        map.put("list", list);
        return map;
    }

}
