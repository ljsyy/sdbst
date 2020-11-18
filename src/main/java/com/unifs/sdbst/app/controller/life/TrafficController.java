package com.unifs.sdbst.app.controller.life;


import com.unifs.sdbst.app.bean.traffic.Digital;
import com.unifs.sdbst.app.service.traffic.DigitalService;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 交通  首相 相关功能 的 控制器
 * @author QuXiaoTao 2017年8月9日
 *
 */

@Controller
@RequestMapping("/traffic")
public class TrafficController {

    @Autowired
    private DigitalService digitalService;

	@RequestMapping("scene/test")
	public String xxx() {
		return "/modules/traffic/test";
	}
	/***
	 * 违章接口，已中转！直接调用
	 * */
	@ResponseBody
	@RequestMapping("scene")
	public  String scene(String jsonData,String apiNum,HttpServletRequest request) throws UnsupportedEncodingException {
		if (null == jsonData || "".equals(jsonData) || null == apiNum || "".equals(apiNum)) {
			return "参数错误！";
		}
		//String data = new String(jsonData.getBytes("ISO-8859-1"),"UTF-8");
		 String url= new String("http://124.172.189.170:8082/shundeAPI/traffic/scene?apiNum="+apiNum+"&jsonData="+URLEncoder.encode(jsonData,"UTF-8"));
		 System.out.println(jsonData);
		System.out.println(url);
		String relut=HttpUtil.sendGet(url, null,"");
		System.out.println("违章参数="+relut);
		return relut;
	}
	
	/*违章查询*/
	@RequestMapping(value = "wzcl/queryIllegal")
	public String queryIllegal() {
		return "/modules/traffic/jtbl/wzcl/queryIllegal";
	}
	
	/*违章查询内容*/
	@RequestMapping(value = "wzcl/queryList")
	public String queryList() {
		return "/modules/traffic/jtbl/wzcl/list";
	}
	
	/*违章查询绑定*/
	@RequestMapping(value = "wzcl/binding")
	public String wzclBinding() {
		return "/modules/traffic/jtbl/wzcl/binding";
	}
	
	/***
	 * 公共自行车接口，已中转！直接调用
	 * */
	@ResponseBody
	@RequestMapping("bike")
	public String bike(String lng,String lat,String keyword,String pageSize,String pageIndex,HttpServletRequest request) throws UnsupportedEncodingException {
		keyword = URLEncoder.encode(keyword,"UTF-8");
		String url= new String("http://124.172.189.170:8082/shundeAPI/app/wsdl/wsdlxzxc?keyword="+keyword+"&lat="+lat+"&lng="+lng+"&pageSize="+pageSize+"&pageIndex="+pageIndex);
		//String url= new String("http://124.172.189.170:8082/shundeAPI/traffic/scene?apiNum="+apiNum+"&jsonData="+URLEncoder.encode(jsonData,"UTF-8"));
		System.out.println(url);
		String relut=HttpUtil.sendGet(url, null,"");
		System.out.println("自行车参数="+relut);
		return relut;
	}
	
	@RequestMapping(value = "jtbl/ggzxc")
	public String ggzxc() {
		return "/modules/traffic/jtbl/ggzxc";
	}
	@RequestMapping(value = "jtbl/ggzxcMap")
	public String ggzxcMap() {
		return "/modules/traffic/jtbl/ggzxcMap";
	}
	
	@RequestMapping(value = "/map")
	public String map() {
		return "/app/traffic/map";
	}
	//机场快线
	@RequestMapping(value = "/jckx")
	public String jckx() {
		return "/app/traffic/jckx";
	}
	
	@RequestMapping(value = "jtbl/kc")
	public String kc() {
		return "/modules/traffic/jtbl/kc";
	}
	
	@RequestMapping(value = "jtbl/trafficinformation")
	public String trafficinformation() {
		return "/modules/traffic/jtbl/trafficinformation";
	}
	@RequestMapping(value = "jtbl/BusTravelname")
	public String BusTravelname() {
		return "/modules/traffic/jtbl/BusTravelname";
	}
	@RequestMapping(value = "jtbl/BusTravel")
	public String BusTravel() {
		return "/modules/traffic/jtbl/BusTravel";
	}
	
	@RequestMapping("getjsonBusTravel")
	@ResponseBody
	public String getjsonBusTravel(String url) throws UnsupportedEncodingException {
		System.out.println(url);
		String newurl=URLDecoder.decode(url, "UTF-8");;
		System.out.println(newurl);
		String result=HttpUtil.sendPost(newurl, null);
		return result;
	}
	
	//电子眼地图
	@RequestMapping(value = "digitalMap")
	public String digitalMap() {
		return "app/traffic/digitalMap";
	}
	//电子眼一层
	@RequestMapping(value = "digital")
	public String digital() {
		return "/app/traffic/digital";
	}
	
	//电子眼二层列表
	@RequestMapping(value = "digitalList")
	public String digitalList() {
		return "app/traffic/digitalList";
	}
	
	//电子眼一层 接口
	Map<String,Object> map = new HashMap<String,Object>();
	@RequestMapping("getType")
	@ResponseBody
	public Map<String,Object> getType() throws UnsupportedEncodingException {
		List<Digital> list = digitalService.getType();
		map.clear();
		map.put("list", list);
		return map;
	}

	//电子眼二层 接口
	@RequestMapping("getAddress")
	@ResponseBody
	public Map<String,Object> getAddress(String typeId) throws UnsupportedEncodingException {
		List<Digital> list = digitalService.getByType(typeId);
		map.put("list", list);
		return map;
	}
	//电子眼所有信息
	@RequestMapping("getAll")
	@ResponseBody
	public Map<String,Object> getAll(String lng, String lat) throws UnsupportedEncodingException {
		List<Digital> list = digitalService.getAll(lat, lng);
		map.clear();
		map.put("list", list);
		return map;
	}
	//车驾管
	@RequestMapping(value = "DMV/index")
	public String DMVindex() {
		return "/modules/traffic/jtbl/DMV/index";
	}
	
	//车驾管 驾驶证
	@RequestMapping(value = "DMV/business")
	public String DMVbusiness() {
		return "/modules/traffic/jtbl/DMV/business";
	}
	
	//车驾管 行驶证
	@RequestMapping(value = "DMV/vehicle")
	public String DMVvehicle() {
		return "/modules/traffic/jtbl/DMV/vehicle";
	}
	
	//车驾管 声明
	@RequestMapping(value = "DMV/statement")
	public String DMVstatement() {
		return "/modules/traffic/jtbl/DMV/statement";
	}
	
	//车驾管 表格样板
	@RequestMapping(value = "DMV/template")
	public String DMVtemplate() {
		return "/modules/traffic/jtbl/DMV/template";
	}
	
	//车驾管 填写驾驶证信息
	@RequestMapping(value = "DMV/info")
	public String DMVinfo() {
		return "/modules/traffic/jtbl/DMV/info";
	}
	
	/*//驾驶证信息
	@RequestMapping(value = "DMV/detail", method = RequestMethod.POST)
	@ResponseBody
	//返回json数据
	public void binding(HttpServletRequest request, HttpServletResponse response, String data) {
		System.out.println(JSONObject.fromObject(data));
		HttpSession session = request.getSession();
		session.setAttribute("DMVdetail", JSONObject.fromObject(data));
		//System.out.println(jsonData);
	}*/
	
	
	//新公交出行--附近站点
		@RequestMapping(value = "jtbl/new/nearbus")
		public String fjzd() {
			return "/modules/traffic/jtbl/newbus/nearbus";
		}
	//新公交出行--站点
		@RequestMapping(value = "jtbl/new/busstation")
		public String gjzd() {
			return "/modules/traffic/jtbl/newbus/busstation";
		}
	//新公交出行--路线
		@RequestMapping(value = "jtbl/new/busroutes")
		public String gjxl() {
			return "/modules/traffic/jtbl/newbus/busroutes";
		}
	//新公交出行--路线
		@RequestMapping(value = "jtbl/new/hisCookie")
		public String hisCookie() {
			return "/modules/traffic/jtbl/newbus/hisCookie";
		}
	//新公交出行--站点
		@RequestMapping(value = "jtbl/new/Travelname")
		public String Travel() {
			return "/modules/traffic/jtbl/newbus/Travelname";
		}
	//新公交出行--路线
		@RequestMapping(value = "jtbl/new/Travel")
		public String newTravel() {
			return "/modules/traffic/jtbl/newbus/Travel";
		}
	//交通
		@RequestMapping(value = "jtbl/collage1")
		public String collage1() {
			return "/modules/traffic/jtbl/collage1";
		}
//	//交通--公交出行--公交连线
//	@RequestMapping(value = "jtbl/new/travelLine")
//	@ResponseBody
//	public List<OracleJdbc> travelLine(String type) {
//		System.out.println(type);
//		OracleJdbcTest test=new OracleJdbcTest();
//		String sql="select bbl.name,bbs.name as stationName,bli.sort,bli.lng,bli.lat from BDE.v_BDE_BUS_LINE bbl left join BDE.v_BDE_BUS_SUB_LINE bbsl on BBL.LINE_ID = BBSL.LINE_ID LEFT JOIN BDE.v_BDE_LINE_STATION bls on bls.SUB_LINE_ID = bbsl.SUB_LINE_ID left join BDE.V_BDE_LINE_INFLEXION bli on bli.R_LINE_STATION_ID = bls.R_LINE_STATION_ID left join BDE.v_BDE_BUS_STATION bbs on bbs.STATION_ID = bls.STATION_ID where BBL.NAME like '%" + type +"%'";
//		List<OracleJdbc> list=null;
//		try {
//			list=test.query(sql, true);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return list;
//	}
	
	@RequestMapping(value = "driverSchool")
	public String driverSchool() {
		return "app/traffic/driverSchool";
	}
	@RequestMapping(value = "driverSchoolMap")
	public String driverSchoolMap() {
		return "/app/traffic/driverSchoolMap";
	}
}
