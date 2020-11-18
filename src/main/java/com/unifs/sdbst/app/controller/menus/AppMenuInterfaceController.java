package com.unifs.sdbst.app.controller.menus;

import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.bean.menus.Dict;
import com.unifs.sdbst.app.bean.menus.HealthCare;
import com.unifs.sdbst.app.bean.menus.HealthExplain;
import com.unifs.sdbst.app.service.data.DataQueryserviceImplServiceSoapBindingStub;
import com.unifs.sdbst.app.service.menus.DictService;
import com.unifs.sdbst.app.service.menus.HealthCareService;
import com.unifs.sdbst.app.utils.WsClientTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单接口控制器
 * @author HuangHaiMing
 * @version 2017-8-14
 * */

@Controller
@RequestMapping(value = "/app/menu/interface")
public class AppMenuInterfaceController{
	@Autowired
	private DictService dictService;
	@Value("${common.base.url}")
	private String address;
	@Autowired
	private HealthCareService healthCareService;


	//水费查询
	@RequestMapping(value = "/waterQuery")
	public String waterQuery() {
		System.out.println("水费查询！");
		return "app/government/zwfw/bmcx/waterQuery";
	}

	//水费查询结果
	@RequestMapping(value = "/waterQueryResult")
	public String waterQueryResult(String businessNo,String account,String startTime,String endTime,Model model) {
		model.addAttribute("businessNo",businessNo);
		model.addAttribute("account",account);
		model.addAttribute("startTime",startTime);
		model.addAttribute("endTime",endTime);
		return "app/government/zwfw/bmcx/waterQueryResult";
	}

	@RequestMapping(value = "electricQuery")
	public String electricQuery() {
		return "modules/government/zwfw/bmcx/electricQuery";
	}
	
	@RequestMapping(value = "letterQuery")
	public String letterQuery() {
		return "modules/government/zwfw/bmcx/letterQuery";
	}
	
	@RequestMapping(value = "list")
	public String list() {
		return "modules/government/zwfw/xzsp/list";
	}
	
	@RequestMapping(value = "searchList")
	public String searchList() {
		return "modules/government/zwfw/xzsp/searchList";
	}
	
	//事项查询
	@RequestMapping(value = "itemQueryList")
	public String itemQueryList(HttpServletRequest request, HttpServletResponse response, Model model) throws UnsupportedEncodingException {
		String keyWord= request.getParameter("keyWord");
		keyWord = URLEncoder.encode(keyWord.trim(), "UTF-8");
		System.out.println(keyWord+"  UTF-8");
		model.addAttribute("keyWord", keyWord);
		return "modules/government/zwfw/xzsp/itemQueryList";
	}
	
	@RequestMapping(value = "guideDetail")
	public String guideDetail() {
		return "modules/government/zwfw/xzsp/guideDetail";
	}
	
	@RequestMapping(value = "deptList")
	public String deptList() {
		return "modules/government/zwfw/xzsp/deptList";
	}
	
	@RequestMapping(value = "areaList")
	public String areaList() {
		return "modules/government/zwfw/xzsp/areaList";
	}
	
	@RequestMapping(value = "areaDetail")
	public String areaDetail() {
		return "modules/government/zwfw/xzsp/areaDetail";
	}
	
	@RequestMapping(value = "governmentGuide")
	public String governmentGuide() {
		return "app/government/zwgk/zwfg/governmentGuide";
	}
	
	@RequestMapping(value = "governmentCatalog")
	public String governmentCatalog() {
		return "app/government/zwgk/zwfg/governmentCatalog";
	}
	
	@RequestMapping(value = "shundeGovernment")
	public String shundeGovernment() {
		return "modules/government/zwgk/zwfg/shundeGovernment";
	}
	
	@RequestMapping(value = "shundeFileList")
	public String shundeFileList() {
		return "modules/government/zwgk/zwfg/shundeFileList";
	}
	
	@RequestMapping(value = "shundeDetailList")
	public String shundeDetailList() {
		return "modules/government/zwgk/zwfg/shundeDetailList";
	}
	
	@RequestMapping(value = "normativeFile")
	public String normativeFile() {
		return "app/government/zwgk/zwfg/normativeFile";
	}
	
	@RequestMapping(value = "policyGuide")
	public String policyGuide() {
		return "modules/government/zwgk/zwfg/policyGuide";
	}
	
	@RequestMapping(value = "detail")
	public String detail() {
		return "modules/government/zwgk/zwfg/detail";
	}
	
	@RequestMapping(value = "personList")
	public String personList() {
		return "modules/government/zwfw/shundeCourt/personList";
	}
	
	@RequestMapping(value = "personInfo")
	public String personInfo() {
		return "modules/government/zwfw/shundeCourt/personInfo";
	}
	//统计年报
	@RequestMapping(value = "/yearlyReport")
	public String yearlyReport() {
		return "/app/government/staticsData/yearlyReport";
	}
	
	@RequestMapping(value = "classify")
	public String classify() {
		return "modules/government/zwgk/statisData/classify";
	}
	
	@RequestMapping(value = "classifyList")
	public String classifyList() {
		return "modules/government/zwgk/statisData/classifyList";
	}
	
	@RequestMapping(value = "getData")
	public String getData(HttpServletRequest request, String url, Model model) {
		String test=url.replace("*", "&");
		String title=request.getParameter("title");
		
		request.setAttribute("title", title);		
		request.setAttribute("url", test);
		
		//免责声明
		String disclaimer=request.getParameter("disclaimer");
		if( StringUtils.isNotBlank(disclaimer) ) {
			//查询字典
			Dict dict=new Dict();
			dict.setType("disclaimer");
			List<Dict> dictList = dictService.findList(dict);
			if(dictList != null && dictList.size()>0) {
				for(int i=0; i<dictList.size(); i++) {
					if( disclaimer.equals( dictList.get(i).getValue() ) ) {
						dict=dictList.get(i);
						break;
					}
				}
			}
			model.addAttribute("dict", dict);
			model.addAttribute("disclaimer", disclaimer);
		}
		return "/app/menu/detail";
	}
	
	@RequestMapping(value = "doAsTheQuery") //企业办照进度查询
	public String doAsTheQuery() {
		return "modules/government/zwfw/bmcx/doAsTheQuery";
	}
	
	@RequestMapping(value = "enterpriseInfoQuery") //企业资料查询
	public String enterpriseInfoQuery() {
		return "modules/government/zwfw/bmcx/enterpriseInfoQuery";
	}
	
	@RequestMapping(value = "highwayPermission") //路政许可
	public String highwayPermission() {
		return "modules/government/zwfw/bmcx/highwayPermission";
	}
	
	@RequestMapping(value = "resultShow")
	public String resultShow() {
		return "modules/government/zwfw/bmcx/resultShow";
	}
	
	@RequestMapping(value = "head")
	public String head() {
		return "modules/app/head";
	}
	
	@RequestMapping(value = "socialOrg")	//社会组织
	public String socialOrg() {
		return "modules/government/others/socialOrg";
	}
	
	@RequestMapping(value = "parks")	//各镇街公园
	public String park() {
		return "app/government/others/park";
	}
	
	/**
	 * 社会团体接口
	 * mcdm:单位名称或代码，非必填
	 * */
	@RequestMapping(value = "club")
	@ResponseBody
	public Map<String, Object> club(String pageNum, String pageSize, String mcdm, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		

			//本地
		
		//创建服务调用接口
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));	//2、输入文档提供的【用户】+【密码】

		//开始调用服务
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		if(StringUtils.isNotBlank(mcdm)) {
			params.put("mcdm", mcdm);
		}
		System.out.println("pageNum:"+pageNum+"  pageSize:"+pageSize+"  mcdm:"+mcdm);
		String requestJson = JSON.toJSONString(params);	//请求参数
		String jsonResult = stub.query("20581", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		//Map<String, String> outMap = WsClientTool.getOutMap(jsonResult);
		
		String status = WsClientTool.getStatus(jsonResult); // 获取返回状态
		System.out.println("社会团体接口返回状态:" + status);
		map.put("status", status);
		
		//字段说明
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonResult);	// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ": " + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonResult);	// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
	
		//分页参数
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonResult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	
	/**
	 * 食药监-行政许可数据接口调用
	 * xdrmc:企业名称/个人姓名，非必填
	 * xkwsh:许可文书号，非必填
	 * xkmc:许可项目名称，非必填
	 * */
	@RequestMapping(value = "xzxk")
	@ResponseBody
	public Map<String, Object> xzxk(String pageNum, String pageSize, String xdrmc, String xkwsh, String xkmc, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		if(StringUtils.isNotBlank(xdrmc)) {
			params.put("xdrmc", xdrmc);
		}
		if(StringUtils.isNotBlank(xkwsh)) {
			params.put("xkwsh", xkwsh);
		}
		if(StringUtils.isNotBlank(xkmc)) {
			params.put("xkmc", xkmc);
		}
		
		String requestJson = JSON.toJSONString(params);//请求参数
		String jsonresult = stub.query("19297", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
	
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("食药监-行政许可接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	
	/**
	 * 食药监-快检追溯数据接口调用
	 * */
	@RequestMapping(value = "kjzs")
	@ResponseBody
	public Map<String, Object> kjzs(String pageNum, String pageSize, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		

		//本地

		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		
		String requestJson = JSON.toJSONString(params);//请求参数
		String jsonresult = stub.query("17715", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
	
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("食药监-快检追溯接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		/*for(Map<String, Object> m: data) {
			FoodTrace foodTrace=new FoodTrace();
			foodTrace.setBJCPMC(m.get("BJCPMC").toString());
			foodTrace.setBJDW(m.get("BJDW").toString());
			foodTrace.setCJSJ(m.get("CJSJ").toString());
			foodTrace.setDATA_UUID(m.get("DATA_UUID").toString());
			foodTrace.setJCBZ(m.get("JCBZ").toString());
			foodTrace.setJCFF(m.get("JCFF").toString());
			foodTrace.setJCJG(m.get("JCJG").toString());
			foodTrace.setJCSB(m.get("JCSB").toString());
			foodTrace.setJCSJ(m.get("JCSJ").toString());
			foodTrace.setJCSYSID(m.get("JCSYSID").toString());
			foodTrace.setJCXM(m.get("JCXM").toString());
			foodTrace.setJCZ(m.get("JCZ").toString());
			foodTrace.setJZSJ(m.get("JZSJ").toString());
			foodTrace.setROWNUM_(m.get("ROWNUM_").toString());
			foodTrace.setRWBH(m.get("RWBH").toString());
			foodTrace.setSZDW(m.get("SZDW").toString());
			foodTrace.setYBBH(m.get("YBBH").toString());
			foodTrace.setYBJCJG(m.get("YBJCJG").toString());
			foodTrace.setYPDL(m.get("YPDL").toString());
			foodTrace.setYPXL(m.get("YPXL").toString());
			
			foodTraceService.saveData(foodTrace);
		}*/
		
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	
	/**
	 * 个人社保信息接口
	 * sfzhm:身份证号，二选一
	 * xm:姓名，二选一
	 * */
	@RequestMapping(value = "insurance")
	@ResponseBody
	public Map<String, Object> insurance(String pageNum, String pageSize, String sfzhm, String xm, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		

		//本地
		//创建服务调用接口
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));	//2、输入文档提供的【用户】+【密码】

		//开始调用服务
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		if(StringUtils.isNotBlank(sfzhm)) {
			params.put("sfzhm", sfzhm);
		}
		if(StringUtils.isNotBlank(xm)) {
			params.put("xm", xm);
		}
		System.out.println("pageNum:"+pageNum+"  pageSize:"+pageSize+"  sfzhm:"+sfzhm+"  xm:"+xm);
		String requestJson = JSON.toJSONString(params);	//请求参数
		String jsonResult = stub.query("20942", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		//Map<String, String> outMap = WsClientTool.getOutMap(jsonResult);
		
		String status = WsClientTool.getStatus(jsonResult); // 获取返回状态
		System.out.println("个人社保信息接口返回状态:" + status);
		map.put("status", status);
		
		//字段说明
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonResult);	// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ": " + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonResult);	// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
	
		//分页参数
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonResult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	/**
	 * 房屋预售接口调用
	 * NY格式201801
	 * */
	@RequestMapping(value = "fwys")
	@ResponseBody
	public Map<String, Object> fwys(String pageNum, String pageSize, String NY, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		

		//本地

		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("NY", NY);
		
		
		String requestJson = JSON.toJSONString(params);//请求参数
		String jsonresult = stub.query("20941", "1", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("房屋预售接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	/**
	 * 守合同重信用企业资料接口调用
	 * */
	@RequestMapping(value = "/credit")
	@ResponseBody
	public Map<String, Object> credit(String pageNum, String pageSize, String qymc, String qyzch, String gsnd, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();

		//本地
		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("qymc", qymc); //根据企业名称模糊查询。
		params.put("qyzch", qyzch);	//根据企业注册号模糊查询。
		params.put("gsnd", gsnd);	//公示年度根据公示年度精确查询，不分页。(格式：yyyy年)
		
		String requestJson = JSON.toJSONString(params);//请求参数
		System.out.println(requestJson);
		String jsonresult = stub.query("21012", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
	
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("守合同重信用企业资料接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	
	/**
	 * 土地使用权信息接口调用
	 * certNo:土地证编号
	 * owner:使用权人
	 * */
	@RequestMapping(value = "tdsyq")
	@ResponseBody
	public Map<String, Object> fwys(String pageNum, String pageSize, String certNo, String owner, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		

		//本地

		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("certNo", certNo);
		params.put("owner", owner);
		
		
		String requestJson = JSON.toJSONString(params);//请求参数
		String jsonresult = stub.query("21311", "2", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("土地使用权信息接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	/**
	 * 房屋产权证登记信息接口调用
	 * NY:年月（格式：YYYYMM）
	 * */
	@RequestMapping(value = "fwcqz")
	@ResponseBody
	public Map<String, Object> fwcqz(String pageNum, String pageSize, String NY, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		

		//本地

		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("fileType", new String[] {"date","number","string"});
		params.put("NY", "201510");
		
		
		String requestJson = JSON.toJSONString(params);//请求参数
		String jsonresult = stub.query("21312", "1", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("房屋产权证登记信息接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	/**
	 * 楼盘信息接口调用
	 * XMMC:项目名称（格式：值为空时返回全部数据）
	 * */
	@RequestMapping(value = "lpxx")
	@ResponseBody
	public Map<String, Object> lpxx(String pageNum, String pageSize, String XMMC, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		

		//本地

		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("XMMC", XMMC);
		
		
		String requestJson = JSON.toJSONString(params);//请求参数
		String jsonresult = stub.query("21313", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("楼盘信息接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	/**
	 * 楼盘总体情况信息接口调用
	 * XMMC:项目名称（格式：值为空时返回全部数据）
	 * */
	@RequestMapping(value = "lpztqk")
	@ResponseBody
	public Map<String, Object> lpztqk(String pageNum, String pageSize, String XMMC, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();
		

		//本地

		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("XMMC", XMMC);
		
		
		String requestJson = JSON.toJSONString(params);//请求参数
		String jsonresult = stub.query("21314", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("楼盘总体情况信息接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	/**
	 * 全市农副产品价格信息接口调用
	 * PZ:品种（必填）
	 * NOTLIMIT:是否限制返回所有数据(若为true时不限制返回数据数量，不作分页返回所有数据)
	 * */
	@RequestMapping(value = "nfcpjg")
	@ResponseBody
	public Map<String, Object> nfcpjg(String pageNum, String pageSize, String PZ, String NOTLIMIT, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();

		

		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("PZ", PZ);
		params.put("NOTLIMIT", NOTLIMIT);
		
		
		String requestJson = JSON.toJSONString(params);//请求参数
		String jsonresult = stub.query("21315", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("全市农副产品价格信息接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	/**
	 * 用水缴费查询接口调用
	 * businessNo:用户编号(必填)
	 * account:户名(必填)
	 * startTime:开始年月[时间段(可选)，格式（yyyy-mm）]
	 * endTime:结束年月[时间段(可选)，格式（yyyy-mm）]
	 * */
	@RequestMapping(value = "queryWater")
	@ResponseBody
	public Map<String, Object> queryWater(String pageNum, String pageSize, String businessNo, String account, String startTime, String endTime, HttpServletRequest request, HttpServletResponse response) throws MalformedURLException, RemoteException {
		Map<String, Object> map=new HashMap<String, Object>();

		/**
		 * 创建服务调用接口
		 */
		DataQueryserviceImplServiceSoapBindingStub stub = new DataQueryserviceImplServiceSoapBindingStub(new java.net.URL(address), new org.apache.axis.client.Service());
		stub.setHeader(WsClientTool.getSoapHead("BST_USER", "BST_0123"));// 2、输入文档提供的【用户】+【密码】

		/**
		 * 开始调用服务
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		params.put("businessNo", businessNo);
		params.put("account", account);
		params.put("startTime", startTime);
		params.put("endTime", endTime);
		
		
		String requestJson = JSON.toJSONString(params);//请求参数
		System.out.println("传送参数:" + requestJson);
		
		String jsonresult = stub.query("21371", "100", requestJson);	//3、输入需要调用的【服务ID】、【类型】和【请求参数】
		
		
		String status = WsClientTool.getStatus(jsonresult); // 获取返回状态
		System.out.println("用水缴费查询接口返回状态:" + status);
		map.put("status", status);
		
		Map<String, String> headMap = WsClientTool.getHeadMap(jsonresult);// 获取返回的数据说明
		map.put("headMap", headMap);
		System.out.println("----------字段说明----------");
		for (Map.Entry<String, String> k: headMap.entrySet()) {
			System.out.println(k.getKey() + ":" + k.getValue());
		}
		
		List<Map<String, Object>> data = WsClientTool.getData(jsonresult);// 获取返回的数据集
		System.out.println("数据量:" + data.size());
		map.put("data", data);
		
		System.out.println("----------分页参数---------");
		Map<String, Object> pageMap = WsClientTool.getPageMap(jsonresult);	// 获取分页参数
		System.out.println( "PAGE_COUNT:"+pageMap.get("PAGE_COUNT"));		// 总页数
		System.out.println( "PAGE_NUM:"+pageMap.get("PAGE_NUM"));			// 当前页
		System.out.println( "PAGE_SIZE:"+pageMap.get("PAGE_SIZE"));			// 分页总数
		System.out.println( "TOTAL_COUNT:"+pageMap.get("TOTAL_COUNT")); 	// 数据总数
		map.put("pageMap", pageMap);
		
		return map;
	}
	
	
	@RequestMapping(value = "HealthCareList")
	@ControlLog(context = "服务医疗")
	@ResponseBody
	public List<Map<String, Object>> HealthCareList(HttpSession session) {
		List<Map<String, Object>> backList=new ArrayList<Map<String, Object>>();
		
		//所有的数据
		List<HealthCare> list=null;
		Object obj= session.getAttribute("HealthCareList");
		if(obj != null) {
			list=(List<HealthCare>) obj;
		}else {
			list=healthCareService.findList(new HealthCare());
			session.setAttribute("HealthCareList", list);
		}
		
		//一级列表
		List<String> oneNumList=healthCareService.oneNumList();
		for(String oneNum: oneNumList) {
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("oneNum", oneNum);
			//根据oneNum获取oneName
			String oneName=healthCareService.getOneNameByOneNum(oneNum, list);
			map.put("oneName", oneName);
			
			//二级列表
			HealthCare entity=new HealthCare();
			entity.setOneNum(oneNum);
			List<String> twoNumList=healthCareService.twoNumList(entity);
			
			List<HealthCare> subList=new ArrayList<HealthCare>();
			for(String twoNum: twoNumList) {
				//根据twoNum获取twoName
				String twoName=healthCareService.getTwoNameByTwoNum(twoNum, list);
				
				HealthCare temp=new HealthCare();
				temp.setTwoNum(twoNum);
				temp.setTwoName(twoName);
				subList.add(temp);
			}
			map.put("data", subList);
			backList.add(map);
		}
		
		return backList;
	}
	
	//服务医疗价格查询第二层
	@RequestMapping(value = "HealthCareTwoList")
	@ControlLog(context = "服务医疗价格查询第二层")
	@ResponseBody
	public Map<String,Object> HealthCareTwoList(String oneNum) throws UnsupportedEncodingException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<HealthCare> list = healthCareService.getTwoList(oneNum);
		map.put("data", list);
		return map;
	}
	
	//服务医疗价格查询第三层
	@RequestMapping(value = "HealthCareThreeList")
	@ControlLog(context = "服务医疗价格查询第三层")
	@ResponseBody
	public Map<String,Object> HealthCareThreeList(String twoNum) throws UnsupportedEncodingException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<HealthCare> list = healthCareService.getThreeList(twoNum);
		map.put("data", list);
		return map;
	}
	
	//服务医疗价格查询第四层
	@RequestMapping(value = "HealthCareFourList")
	@ControlLog(context = "服务医疗价格查询第四层")
	@ResponseBody
	public Map<String,Object> HealthCareFourList(String threeNum) throws UnsupportedEncodingException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<HealthCare> list = healthCareService.getFourList(threeNum);
		map.put("data", list);
		return map;
	}
	
	//服务医疗价格第一层说明
	@RequestMapping(value = "HealthExplain")
	@ControlLog(context = "服务医疗价格第一层说明")
	@ResponseBody
	public Map<String,Object> HealthExplain(String oneNum) throws UnsupportedEncodingException {
		Map<String,Object> map = new HashMap<String,Object>();
		List<HealthExplain> list = healthCareService.getExpalin(oneNum);
		map.put("data", list);
		return map;
	}
	
	//服务医疗价格查询关键字
	@RequestMapping(value = "HealthCareKeyword")
	@ControlLog(context = "服务医疗价格查询关键字")
	@ResponseBody
	public Map<String,Object> HealthCareKeyword(String keyword) throws UnsupportedEncodingException {
		Map<String,Object> map = new HashMap<String,Object>();
		if(keyword.length()>30) {
			System.out.println("字符长度太长");
			map.put("state", "error");
			
		}else {
			List<HealthCare> list = healthCareService.getKeyword(keyword);
			map.put("data", list);
			map.put("state", "ok");
		}
		return map;
	}
}
