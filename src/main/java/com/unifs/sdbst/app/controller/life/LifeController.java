package com.unifs.sdbst.app.controller.life;


import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.annotation.FormCommit;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.cms.Article;
import com.unifs.sdbst.app.bean.cms.Category;
import com.unifs.sdbst.app.bean.life.Environment;
import com.unifs.sdbst.app.bean.life.SmallFoodWorkshop;
import com.unifs.sdbst.app.bean.life.Vaccination;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.service.cms.ArticleService;
import com.unifs.sdbst.app.service.life.CommonService;
import com.unifs.sdbst.app.service.life.EnvironService;
import com.unifs.sdbst.app.service.life.VaccinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 生活  首相 相关功能 的 控制器
 *
 * @author DL04 2017年8月9日
 */

@Controller
@RequestMapping("life/")
public class LifeController {
    //	@Autowired
//	private CgPaiService cgPaiService;
    @Autowired
    private ArticleService articleService;
    //	@Autowired
//	private HospitalService hospitalService;
    @Autowired
    private VaccinationService vaccinationService;
    @Autowired
    private EnvironService environService;
    @Autowired
    private CommonService commonService;
//	@Autowired
//	private TestIdService testIdService;
//
//	private EnvironService environService=SpringContextHolder.getBean(EnvironService.class);
//
//	@Autowired
//	private FoodTraceService foodTraceService;
//
//	private Map<String, String> map = new HashMap<String,String>();	// 用于返回 json 数据
//
//
//	/**
//	 *  1.	商品详细信息
//	 * @param code 商品条形码,如： 6921098883891
//	 * @return	json字符串：如：{"c":"200","d":{"FirmValidDate":"1/13/2019 12:00:00 AM","ItemPackagingTypeCode":"","ItemSpecification":"610mL×15支",
//	 * 							"ItemNetContent":"0","FirmLoginDate":"1/13/1997 12:00:00 AM","arrayOfKeyValue":null,"productEx":null,"Image":[],
//	 * 							"ItemDescription":"http://www.gds.org.cn/goods.aspx?base_id=F25F56A9F703ED74042193F0AA6E34C926CE228CE052997396DDB891B42B8A490BEA4E3EECB75ABB",
//	 * 							"alermCount":"0","keepOnRecord":"","ItemWidth":"400MM","BrandName":"石湾牌","recallCount":"0","ItemHeight":"315MM","ItemGrossWeight":"0",
//	 * 							"qualificationCount":"0","FirmStatus":"有效","ItemNetWeight":"0","ItemPackagingMaterialCode":"","ItemName":"玉冰烧","productFangWei":null,
//	 * 							"FirmContactMan":"欧阳凯廷","ItemDepth":"255MM","FirmContactPhone":"82276788","diffYearsMonthsDays":"[20,11,21]","QS":[],"ItemClassCode":"50202206",
//	 * 							"FirmName":"广东石湾酒厂集团有限公司","FirmAddress":"广东省佛山市石湾镇太平街106号","cosmeticList":[],"batch":"","FirmLogoutDate1":"","honestCount":"0",
//	 * 							"productCode":"06921098883891","ItemShortDescription":"","FirmLogoutDate":""}}
//	 */
//	@RequestMapping("food/info")
//	@ResponseBody
//	public String foodInfo(String code) {
//		String macUrl = "/api/getProductData?productCode="+code;
//		String mac = FoodUtil.getMac(macUrl);
//		String url="http://webapi.chinatrace.org/api/getProductData?productCode="+code+"*mac="+mac;
//		String result=HttpUtils.sendGet(Global.PostUrl+"/app/menu/getjson?url="+url, null);
//		JSONObject resultObj=JSONObject.fromObject(result);
//		JSONObject dObj=resultObj.getJSONObject("d");
//		String FirmValidDate = "";
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy K:m:s a",Locale.ENGLISH);
//		SimpleDateFormat fromt = new SimpleDateFormat("yyyy年-MM月-dd日");
//		try {
//			Date date = sdf.parse(dObj.getString("FirmValidDate"));
//			FirmValidDate = fromt.format(date);
//			dObj.put("FirmValidDate", FirmValidDate);
//			//dObj.replace("FirmValidDate",FirmValidDate);
//		} catch (Exception e) {
//			return resultObj.toString();
//		}
//		return resultObj.toString();
//	}
//
//	*//**//**//**//**
//	 * 2.	追溯数据信息
//	 * @param code	产品代码,如：6920380551937,可以在第1步返回的json里找productCode键值
//	 * @param batch 批次, 如：AF0301173,可以在第1步返回的json里找batch_list和batch两个键，如若没有或值为空，则此步查不到信息
//	 * @return	json字符串,如：{"c":"200","d":{"flows":[{"flow_state":"","amount":null,"unit":"","receive_area_name":"******","sale_date":"2016-07-19",
//	 * 							"flow_id":"795801174","send_name":"广东华润顺峰药业有限公司","receive_name":"******","send_area_name":"广东省佛山市顺德区大良街道金桔嘴"},
//	 * 							{"flow_state":"","amount":null,"unit":"","receive_area_name":"******","sale_date":"2016-07-25","flow_id":"795801178",
//	 * 							"send_name":"广东华润顺峰药业有限公司","receive_name":"******","send_area_name":"广东省佛山市顺德区大良街道金桔嘴"}],
//	 * 							"productInfos":[{"segement_desc":"包装图","trace_key_guid":"238288","trace_key_name":"产品生产",
//	 * 							"trace_key_image":"http://www.chinatrace.org//uploadFile/segementImages/b7/ec/61/b7ec61c7304744bdb4324a1bdff60007_THUMBNAIL.png"}],
//	 * 							"checkInfos":[{"CHECK_REPORT_GUID":"5366","CHECK_MODEL_NAME":"顺峰宝宝芦芭水润爽肤乳检验报告"}]}}
//	 *//**//**//**//*
//	@RequestMapping("food/findinfo")
//	@ResponseBody
//	public String foodFindInfo(String productCode,String batch) {
//		String url = "/api/getProductData?productCode="+productCode+"&batch="+batch;
//		String mac = FoodUtil.getMac(url);
////		String urlx = FoodUtil.URL + url + "*mac="+mac;
////		String result = ApiController.getJson(urlx, null);
//
//		String urlx = FoodUtil.URL + url +"&mac=" + mac ;
//		String result = FoodUtil.getJson(urlx);
//		return result;
//	}
//
//	/*
//	 * 3.	生产内容
//	 * @param productCode	产品代码
//	 * @param traceKey		追溯信息id（由步骤2查询）
//	 * @param batch			批次
//	 * @return	json字符串,如：{"c":"200","d":[{"sgemeng":{"groupList":[{"description":"","groupId":464121,"name":"原材料","objectList":[{"dataDetailList":[],
//	 * 						"dataList":[{"dataId":34923,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：甘油-A012016041103;使用量：0;供应商：1、\t浙江遂昌惠康药业;供应商地址：浙江省遂昌县妙高镇凯恩路224号;"},
//	 * 						{"dataId":34924,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：2,6-二叔丁基对甲酚-A032016010802;使用量：0;供应商：湖南尔康制药;供应商地址：湖南浏阳生物医药园;"},
//	 * 						{"dataId":34925,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：依地酸二钠-A032016031402;使用量：0;供应商：湖南尔康制药;供应商地址：湖南浏阳生物医药园;"},
//	 * 						{"dataId":34926,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：维生素E-A012016021901;使用量：0;供应商：巴斯夫;供应商地址：广州市荔湾区东沙荷景路9号2幢202;"},
//	 * 						{"dataId":34927,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：牛油果树果脂-A012016031705;使用量：0;供应商：巴斯夫;供应商地址：广州市荔湾区东沙荷景路9号2幢202;"},
//	 * 						{"dataId":34928,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：蔗糖多硬脂酸酯、氢化聚异丁烯-A012015112601;使用量：0;供应商：巴斯夫;供应商地址：广州市荔湾区东沙荷景路9号2幢202;"},
//	 * 						{"dataId":34929,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：碳酸二辛酯-A012016031704;使用量：0;供应商：巴斯夫;供应商地址：广州市荔湾区东沙荷景路9号2幢202;"},
//	 * 						{"dataId":34930,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：苯氧乙醇、甲基异噻唑啉酮-A012015040802;使用量：0;供应商：昆山双友日用化工;供应商地址：广州市前进路基立道28号102室;"}],
//	 * 						"name":"主要原料","objectId":1117378},{"dataDetailList":[],"dataList":[],"name":"辅料","objectId":1117379},{"dataDetailList":[],"dataList":[],"name":"添加剂","objectId":1117380},{"dataDetailList":[],"dataList":[],"name":"其他","objectId":1117381}]},{"description":"","groupId":464122,"name":"包装材料","objectList":[{"dataDetailList":[],"dataList":[{"dataId":34956,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：顺峰宝宝芦芭水润爽肤乳纸箱-C032014073102;使用量：51个;供应商：佛山市顺德区大福成业彩印纸箱实业有限公司;供应商地址：佛山市顺德区容桂街道福业路6号;包装材料类别:纸包装材料：纸张、纸板;"},{"dataId":34957,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：顺峰宝宝芦芭水润爽肤乳瓶-C012014072906;使用量：1351个;供应商：东莞市卓比塑料制品有限公司;供应商地址：东莞市清溪镇谢坑管理区金寓二街5号;包装材料类别:复合包装材料：纸/塑复合材料、铝/塑复合材料、纸/铝/塑复合材料、纸/纸复合材料、塑/塑复合材料、其他;"},{"dataId":34958,"dataTime":"2016-03-29","iBatchNo":"","iProductCode":"","materialType":"income","str":"用料批次号：顺峰宝宝芦芭水润爽肤乳瓶盖-C012014072902;使用量：1225个;供应商：东莞市卓比塑料制品有限公司;供应商地址：东莞市清溪镇谢坑管理区金寓二街5号;包装材料类别:复合包装材料：纸/塑复合材料、铝/塑复合材料、纸/铝/塑复合材料、纸/纸复合材料、塑/塑复合材料、其他;"}],"name":"包装材料","objectId":1117382}]}],"name":"产品生产","segementDesc":"包装图","segementId":0}}]}
//	 */
//	@RequestMapping("food/content")
//	@ResponseBody
//	public String foodContent(String productCode,String traceKey,String batch) {
//		String url = "/api/attribValue?productCode=" + productCode + "&batch=" + batch + "&trace_key_guid=" + traceKey;
//		String mac = FoodUtil.getMac(url);
////		String urlx = FoodUtil.URL + url + "*mac="+mac;
////		String result = ApiController.getJson(urlx, null);
//
//		String urlx = FoodUtil.URL + url +"&mac=" + mac ;
//		String result = FoodUtil.getJson(urlx);
//		return result;
//	}
//
//	/**
//	 * 4.	检验数据信息
//	 * @param productCode	商品条码
//	 * @param traceKey		检验数据id（由步骤2查询）
//	 * @param batch			批次
//	 * @return
//	 */
//	@RequestMapping("food/check")
//	@ResponseBody
//	public String foodCheck(String productCode,String traceKey,String batch) {
//		String url = "/api/checkValue?productCode="+productCode+"&batch="+batch+"&trace_key_guid="+traceKey;
//		String mac = FoodUtil.getMac(url);
////		String urlx = FoodUtil.URL + url + "*mac="+mac;
////		String result = ApiController.getJson(urlx, null);
//
//		String urlx = FoodUtil.URL + url +"&mac=" + mac ;
//		String result = FoodUtil.getJson(urlx);
//		return result;
//	}
//
//	/**
//	 　　* 5.	流向信息
//	 * @param productCode 	商品条码
//	 * @param flowId		流向信息id（由步骤2查询）
//	 * @return
//	 　　*/
//	@RequestMapping("food/flow")
//	@ResponseBody
//	public String foodFlow(String productCode,String flowId) {
//		String url = "/api/flowValue?productCode=" + productCode + "&flow_id=" + flowId;
//		String mac = FoodUtil.getMac(url);
////		String urlx = FoodUtil.URL + url + "*mac="+mac;
////		String result = ApiController.getJson(urlx, null);
//
//		String urlx = FoodUtil.URL + url + "&mac=" + mac ;
//		String result = FoodUtil.getJson(urlx);
//		return result;
//	}
//
//
//
//	public static void main(String[] args) throws IOException {
//		String url = "/api/getProductData?productCode=6921098883891";	//1.	商品详细信息
////		String url = "/api/keyData?productCode=06920380551937&batch=AF0301173";	//2.	追溯数据信息
////		String url = "/api/attribValue?productCode=6920380551937&batch=AF0301173&trace_key_guid=238288"; //3.	生产内容
////		String url = "/api/checkValue?productCode=6920380551937&batch=AF0301173&check_report_guid=5366";	//4.	检验数据信息
////		String url = "/api/flowValue?productCode=6920380551937&flow_id=795801174";	// 5.	流向信息
//
//		String mac = FoodUtil.getMac(url);
//		String urlx = FoodUtil.URL + url + "&mac="+mac;
//		System.out.println(FoodUtil.getJson(urlx));
//
//
//
//
//
//		String urly = "http://webapi.chinatrace.org/api/getProductData?productCode=6920380551937&mac=D8A0C7C367023050DC2827CFFDA3F69019116556325EAD7D68E32AE160E60FFD";
//		String urlz = urly.replace("&", "*");
//		urlz = URLEncoder.encode(urlz,"UTF-8");
//		String result = HttpUtils.sendPost(Global.PostUrl+"/app/menu/getjson?url="+urlz, null);
//
//		System.err.println("不加中转返回的值：" + FoodUtil.getJson(urly));
//		System.err.println("加中转返回的值：" + result);
//		System.err.println("本地工具返回值：" + HttpUtils.sendPost(urly, null));
//	}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//	//扫描 进入页面
//	@RequestMapping("scan")
//	public String scan() {
//		return "/app/life/scan/scan";
//	}
//
//	//扫描 获取签证
//	@RequestMapping("scan/getJssdkSignature")
//	@ResponseBody
//	public WxEntity testPage(Model model, HttpServletRequest request) {
//		String url = "";
//		url = request.getParameter("url");
//		System.err.println("URL:--:" + url);
//        WxEntity we = WxJSUtil.sign(url);
//        return we;
//
//    }
//
//	/*功能升级中*/
//	@RequestMapping(value = "error")
//	public String error() {
//		return "app/app/error";
//	}

    // 中高考成绩查询  进入页面
    @RequestMapping("score")
    public String score() {
        return "/app/life/score/score";
    }

//	// 中高考成绩查询
//	@RequestMapping("score/search")
//	@ResponseBody
//	public String scoreSearch(String num,String birth) throws UnsupportedEncodingException{
//		//String url="http://202.104.25.196/RXWSSTWeb/p_education.web?";
//		String url="http://sdbst.shunde.gov.cn/RXWSSTWeb/p_education.web?";
//		String type="gkcxList";
//		if(num.length()>10) {
//			type="zkcxList";
//		}
//		url+=(type+"&ksh="+num+"&csrq="+birth+"&%20");
//
//		String result=ApiController.getJson(url, true);
//
//		return result;
//	}
//
//
//
//

    // 医疗药品 / 医疗信息详情
    @RequestMapping("yiliao/detail")
    public String yiLiaoStatus(String id, Model model) {
        model.addAttribute("id", id);
        return "/app/life/yiLiaoYaoPing/detail";
    }

    // 医疗药品 / 医疗信息
    @RequestMapping("yiliao/info")
    public String yiLiaoInfo(String title, Model model) {
        model.addAttribute("title", title);
        return "/app/life/yiLiaoYaoPing/info";
    }

    // 医疗机构 / 社会保险定点医疗机构
    @RequestMapping("yiliao/jigou")
    public String yiLiaoJiGou() {
        return "/app/life/yiLiaoJiGou/jiGou";
    }

    // 医疗机构 / 医保定点零售药店
    @RequestMapping("yiliao/yaodian")
    public String yaoDian() {
        return "/app/life/yiLiaoJiGou/yaoDian";
    }


    //	// 个人社保 / 社保卡办理进度查询
//	@RequestMapping("jinduchaxun")
//	public String jinDuChaXun(){
//		return "/app/life/geRenSheBao/jinDu";
//	}
//
//	// 个人社保 / 参保信息
//	@RequestMapping("canbaoxinxi")
//	public String canBaoXinXi(){
//		return "/app/life/geRenSheBao/canBaoXinXi";
//	}
//
    // 餐饮企业信用
    @RequestMapping("/foodcredit")
    public String foodcredit() {
        return "/app/life/canYinQiYeXinYong/foodCredit";
    }

    //
//	// 台风预警
//	@RequestMapping("typhoonwarning")
//	public String typhoonWarning(){
//		return "/app/life/typhoonWarning/typhoon";
//	}
//
//	// 人才招聘
//	@RequestMapping("findjob")
//	public String findJob(){
//		return "/app/life/findJob/job";
//	}
//
//	// 教育机构/ 学校
//	@RequestMapping("education/school")
//	public String schoolx(){
//		return "/app/life/education/school";
//	}
//
//	// 教育机构/ 教育局
//	@RequestMapping("education/jiaoyuju")
//	public String jiaoYuju(){
//		return "app/life/education/jiaoYuJu";
//	}
//
//	// 公共厕所
//	@RequestMapping("zhfw/sdgc")
//	public String ggcs(){
//		return "app/life/zhfw/sdgc";
//	}
    // 天气预告
    @RequestMapping("zhfw/href")
    public String href() {
        return "app/life/zhfw/href";
    }


    // 预约挂号、健康档案 登陆、注册
    @RequestMapping("appoint/login")
    public String appointLogin() {
        return "app/life/appointment/appointLogin";
    }

    // 预约挂号、健康档案 个人信息、修改密码
    @RequestMapping("appoint/info")
    public String appointInfo() {
        return "app/life/appointment/appointInfo";
    }

    // 预约挂号、健康档案 我的预约
    @RequestMapping("appoint/mybooking")
    public String appointMybooking() {
        return "app/life/appointment/appointMybooking";
    }

    // 预约挂号
    @RequestMapping("booking")
    public String booking() {
        return "app/life/appointment/booking";
    }

    //按医院预约  选择地区-医院-科室
    @RequestMapping("booking/area")
    public String area() {
        return "app/life/appointment/area";
    }

    //按科室预约  选择科室
    @RequestMapping("booking/department")
    public String department() {
        return "app/life/appointment/department";
    }

    //按医生名预约  选择日期获取号源
    @RequestMapping("booking/regSrcInfo")
    public String regSrcInfo() {
        return "app/life/appointment/regSrcInfo";
    }

    //按医生名预约  输入关键字获取医生列表
    @RequestMapping("booking/doctorName")
    public String doctorName() {
        return "app/life/appointment/doctorName";
    }

    // 按医院预约1
    @RequestMapping("booking/hospital1")
    public String hospitalBooking1() {
        return "app/life/appointment/hospital1";
    }

    // 按医院预约2
    @RequestMapping("booking/hospital2")
    public String hospitalBooking2() {
        return "app/life/appointment/hospital2";
    }

    //按医院预约、按科室预约  选择日期获取医生列表
    @RequestMapping("booking/hospital3")
    public String hospitalBooking3() {
        return "app/life/appointment/hospital3";
    }

    // 按科室预约1
    @RequestMapping("booking/department1")
    public String departmentBooking1() {
        return "app/life/appointment/department1";
    }

    //按医院预约、按科室预约  获取号源
    @RequestMapping("booking/department3")
    public String departmentBooking3() {
        return "app/life/appointment/department3";
    }

    //按医院预约、按科室预约  提交预约信息
    @RequestMapping("booking/department4")
    public String departmentBooking4() {
        return "app/life/appointment/department4";
    }

    //健康档案首页
    @RequestMapping("health/index")
    public String heatlhIndex() {
        return "app/life/health/index";
    }

    //居民基本信息查询/个人健康档案
    @RequestMapping("health/info")
    public String personInfo() {
        return "app/life/health/personInfo";
    }

    //列表
    @RequestMapping("health/list")
    public String healthList() {
        return "app/life/health/list";
    }

    //详情
    @RequestMapping("health/detail")
    public String healthDetail() {
        return "app/life/health/detail";
    }

    //老年人信息
    @RequestMapping("health/olds")
    public String olds() {
        return "app/life/health/olds";
    }
//
//	//测试
//	@RequestMapping("booking/test")
//	public String test(){
//		return "app/life/appointment/test";
//	}
//
//	//测试
//	@RequestMapping("booking/test0")
//	public String test0(){
//		return "app/life/appointment/test0";
//	}
//
//	//登录
//	@RequestMapping(value = "appoint/savelogin", method = RequestMethod.POST)
//	@ResponseBody
//	//返回json数据
//	public void login(HttpServletRequest request, HttpServletResponse response, String data) {
//		System.out.println(JSONObject.fromObject(data));
//		HttpSession session = request.getSession();
//		session.setAttribute("appoinctlogin", JSONObject.fromObject(data));
//		//System.out.println(jsonData);
//	}
//
//	//绑定信息
//	@RequestMapping(value = "appoint/binding", method = RequestMethod.POST)
//	@ResponseBody
//	//返回json数据
//	public void binding(HttpServletRequest request, HttpServletResponse response, String data) {
//		System.out.println(JSONObject.fromObject(data));
//		HttpSession session = request.getSession();
//		session.setAttribute("appointBind", JSONObject.fromObject(data));
//		//System.out.println(jsonData);
//	}
//
//	//清除绑定信息
//	@RequestMapping(value = "appoint/removeBinding")
//	@ResponseBody
//	//返回json数据
//	public void removeBinding(HttpServletRequest request, HttpServletResponse response, String data) {
//		System.out.println(JSONObject.fromObject(data));
//		HttpSession session = request.getSession();
//		session.removeAttribute("appointBind");
//		//session.setAttribute("appointBind", JSONObject.fromObject(data));
//		//System.out.println(jsonData);
//	}
//
//
//
//	/**
//	 * 按医院预约
//	 * @param data 接收的json数据格式所对应的功能 如下
//	 * 		查询地区信息:{region:"all"} ,其中region只是一个标识,区别于日期
//	 * 		按区域查询医院信息: {areaCode:"440606005"} ,其中areaCode是地区编号
//	 * 		查询科室信息:{hospitalCode:"MZJJ0011"} ,其中hospitalCode是医院编号
//	 * 		查询预约日期:{systemDate:"all"} ,其中systemDate只是一个标识,区别于地区
//	 * 		查询专家/专科列表信息:{hospitalCode:"MZJJ0011",
//	 * 						departmentCode:"1021110",
//	 * 						doctorName:"",
//	 * 						regDate:"2017-11-17"}
//	 * 		查询专家/专科明细信息:{hospitalCode:"MZJJ0011",
//	 *						sourceId:"1021110",
//	 *						sourceType:"2"}
//	 *		查询号源信息:{hospitalCode:"MZJJ0011",
//	 *					sourceId:"1021110",
//	 *					sourceType:"2",
//	 *					regDate:"2017-11-17",
//	 *					firstOrReturn:"1"}
//	 *		预约信息提交: 参见说明文档
//	 * @return
//	 */
//	@RequestMapping("booking/getJsonAsHospital")
//	@ResponseBody
//	public String getBookingInfoAsHospital(String data) {
//		return hospitalService.getJsonAsHospital(data);
//	}
//
//	/**
//	 * 按科室预约
//	 * @param data
//	 * @return
//	 */
//	@RequestMapping("booking/getJsonAsDept")
//	@ResponseBody
//	public String getBookingInfoAsDept(String data) {
//		return hospitalService.getJsonAsDept(data);
//	}
//
//	/**
//	 * 按医生姓名预约
//	 * @param data
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("booking/getJsonAsAppointment")
//	public String appointment(String data) {
//		return hospitalService.getJsonAsAppointment(data);
//	}
//
//	/**
//	 * 预约前 注册/登录 接口
//	 * @param data
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("booking/getJsonIdentityRegister")
//	public String identityRegister(String data) {
//		return hospitalService.getJsonAsIdentityRegister(data);
//	}
//
//	/**
//	 * 居民健康历史查询
//	 * 其中例外项加入必要属性 type
//	 * 			2．2 个人病史: type = his;
//	 * 			2．3 家族疾病史: type = family;
//	 * 			2．4 过敏史: type = irrit;
//	 * 			2．5手术史: type = surgery;
//	 * 			2．6输血史: type = meta
//	 * @param data
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("booking/getJsonAsRhin")
//	public String getJsonAsRhin(String data) {
//		return hospitalService.getJsonAsRhin(data);
//	}
//
//	/**
//	 * 就诊记录
//	 * 其中例外项加入必要属性 type
//	 * 			2．7．1 就诊记录列表 type = vis
//	 * 			2．7．2 查询某次就诊记录的检验报告 type = info
//	 * 			2．7．3 查询某次就诊记录的检验明细 type = detail
//	 * 			2．7．4 查询某次就诊记录的医嘱信息 type = med
//	 * 			2．7．5 查询某次就诊记录的影像报告 type = ir
//	 * 			2．7．6 查询某次就诊记录的费用信息 type = fee
//	 * 			2．8影像报告 type = image
//	 * 			2．9医嘱信息 type = order
//	 * 			2．10．1 检验报告列表 type = check
//	 * 			2．11．1 病案首页列表 type = caseList
//	 * 			2．11．2 病案首页明细 type = case
//	 * 			2．12．1 体检信息列表 type = primaryList
//	 * 			2．12．2 体检明细 type = primary
//	 *
//	 * @param data
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("booking/getJsonAsHealth")
//	public String getJsonAsHealth(String data) {
//		return hospitalService.getJsonAsHealth(data);
//	}
//
//	/**
//	 * 公共卫生系统
//	 * 其中例外项加入必要属性 type
//	 * 			2．13个人健康档案（公共卫生系统） type = "person"
//	 * 			2．14暴露史 type = "expose"
//	 * 			2．15．1老人基本信息 type = "olds"
//	 * 			2．15．2老年人随访记录 type = "oldsList"
//	 * 			2．16．1妇女健康信息 type = "female"
//	 * 			2．16．2婚前检查 type = "preMarry"
//	 * 			2．16．3 计划生育 type = "baby"
//	 * 			2．16．4 孕妇管理 type = "pregnant"
//	 * 			2．16．5 产前筛查与诊断记录 type = "preCheck"
//	 * 			2．17．1糖尿病管理卡 type = "diab"
//	 * 			2．17．2糖尿病随访记录 type = "diabVisit"
//	 * 			2．17．3糖尿病用药记录 type = "diabMed"
//	 * 			2．17．4糖尿病辅助检查记录 type = "diabCheck"
//	 * 			2．18．1高血压管理卡 type = "hyper"
//	 * 			2．18．2高血压随访记录 type = "hyperVisit"
//	 * 			2．18．3高血压用药记录 type = "hyperMed"
//	 * 			2．18．4高血压辅助检查记录 type = "hyperCheck"
//	 * 			2．19免疫接种 type = "immun"
//	 * 			2．20．1儿童基本信息 type = "child"
//	 * 			2．20．4出生医学证明 type = "birth"
//	 * 			2．20．5儿童健康体检 type = "healthCheck"
//	 * 			2．20．6新生儿疾病筛查 type = "birth"
//	 * 			2．21献血记录 type = "donation"
//	 * @param data
//	 * @return
//	 */
//	@ResponseBody
//	@RequestMapping("booking/getJsonAsPublicHealth")
//	public String getJsonAsPublicHealth(String data) {
//		return hospitalService.getJsonAsPublicHealth(data);
//	}


    //环境质量查询
    @RequestMapping(value = "/HJZL")
    @ResponseBody
    public Map<String, Object> HJZL(String type) {
        Map<String, Object> map = new HashMap<>();
        Environment environ = environService.queryByType(type);
        map.put("list", environ);
        return map;
    }

    //
///*	@RequestMapping(value = "testId")
//	@ResponseBody
//	public String Test2(TestId testId) {
//
//		testIdService.insert(testId);
//		return null;
//	}*/
//
    //顺德美食
    @RequestMapping(value = "shundeFood")
    public String shundeFood(Category category, Model model, String type) {
        List<Article> list = articleService.findListByCategory(category);
        model.addAttribute("type", type);
        model.addAttribute("list", list);
        return "app/life/shundeFood";
    }


    //统计表--月报
    @RequestMapping(value = "statisticstable/statistics")
    public String statisticstable(String id, Model model) {
        model.addAttribute("id", id);
        return "/app/government/statisticstable/statistics";
    }

    //统计表--年报
    @RequestMapping(value = "statisticstable/annualreport")
    public String annualreporttable() {
        return "/app/government/staticsData/annualreport";
    }


    //统计表--公报
    @RequestMapping(value = "statisticstable/greport")
    public String greport() {
        return "app/government/statisticstable/greport";
    }


    /*办事*//*
	//办事-我的办事
	@RequestMapping(value = "business/myBusiness")
	public String myBusiness() {
		return "app/life/business/myBusiness";
	}
	//办事-我的预约
	@RequestMapping(value = "business/myAppoint")
	public String myAppoint() {
		return "app/life/business/myAppoint";
	}
	//办事-一键办事
	@RequestMapping(value = "business/aKey")
	public String aKey() {
		return "app/life/business/aKey";
	}
	
	//快检追溯
	@RequestMapping(value = "food/trace")
	public String foodTrace() {
		return "app/life/food/trace";
	}
	*/
	/*//快检追溯 接口
	@RequestMapping("getFoodTrace")
	@ResponseBody
	public Map<String, Object> getFoodTrace(Integer pageNo, Integer pageSize) throws UnsupportedEncodingException {
		Map<String, Object> map=new HashMap<String, Object>();
		
		Page<FoodTrace> page=new Page<FoodTrace>(pageNo, pageSize);
		FoodTrace foodTrace=new FoodTrace();
		foodTrace.setPage(page);
		List<FoodTrace> list = foodTraceService.getAll(foodTrace);
		map.put("data", list);
		map.put("count", list.size());
		return map;
	}*/

    //新市民积分入学
    //政策
    @RequestMapping(value = "school/integralPolicy")
    public String integralPolicy() {
        return "/app/life/education/integralPolicy";
    }

    //政策解读
    @RequestMapping(value = "school/policyImg")
    public String policyImg() {
        return "/app/life/education/policyImg";
    }

    //申请流程
    @RequestMapping(value = "school/application")
    public String application() {
        return "/app/life/education/application";
    }

    //学区划分
    @RequestMapping(value = "school/schoolDistrict")
    public String schoolDistrict() {
        return "/app/life/education/schoolDistrict";
    }

    //个人社保信息查询
    @RequestMapping(value = "shebao/query")
    public String sbQuery() {
        return "app/life/geRenSheBao/query";
    }

    //房屋预售信息
    @RequestMapping(value = "/houseBooking")
    public String houseBooking() {
        return "app/life/service/houseBooking";
    }

    //公立医院基本医疗服务项目和价格 一层
    @RequestMapping(value = "/medicalPriceOne")
    public String medicalPriceOne(String id, Model model) {
        model.addAttribute("id", id);
        return "app/life/medicalPrice/medicalPriceOne";
    }

    //公立医院基本医疗服务项目和价格 二层
    @RequestMapping(value = "medicalPriceTwo")
    public String medicalPriceTwo(String oneNum, String twoNum, Model model) {
        model.addAttribute("twoNum", twoNum);
        model.addAttribute("oneNum", oneNum);
        return "app/life/medicalPrice/medicalPriceTwo";
    }

    //公立医院基本医疗服务项目和价格 三层
    @RequestMapping(value = "medicalPriceThree")
    public String medicalPriceThree(String threeNum, Model model) {
        model.addAttribute("threeNum", threeNum);
        return "app/life/medicalPrice/medicalPriceThree";
    }

    //公立医院基本医疗服务项目和价格 关键字查询
    @RequestMapping(value = "medicalPriceKeyword")
    public String medicalPriceKeyword(String keyword, Model model) {
        model.addAttribute("keyword", keyword);
        return "app/life/medicalPrice/medicalPriceKeyword";
    }

    //疫苗接种医院信息 查询
    @RequestMapping(value = "vaccination")
    @ResponseBody
    public List<Vaccination> getVaccination(String lng, String lat) {
        List<Vaccination> list = vaccinationService.getAll(lng, lat);
        return list;
    }

    //疫苗接种医院信息 地图
    @RequestMapping(value = "/vaccinationMap")
    public String vaccinationMap() {
        return "app/life/yiLiaoJiGou/vaccinationMap";
    }

    //疫苗接种医院信息 列表
    @RequestMapping(value = "vaccinationList")
    public String vaccinationList(String lng, String lat, Model model) {
        model.addAttribute("lng", lng);
        model.addAttribute("lat", lat);
        return "app/life/yiLiaoJiGou/vaccinationList";
    }

    //顺德公厕 地图
    @RequestMapping(value = "sdgcMap")
    public String sdgcMap() {
        return "app/life/zhfw/sdgcMap";
    }

    //周边服务
    @RequestMapping("/service/periphery")
    public String serviceIndex() {
        return "/app/life/service/periphery";
    }

    //园丁风采
    @RequestMapping(value = "/ydfc")
    public String ydfc(String id, Model model) {
        model.addAttribute("id", id);
        return "/app/life/education/ydfcList";
    }

    //缤纷校园
    @RequestMapping(value = "/bfxy")
    public String bfxy(String id, Model model) {
        model.addAttribute("id", id);
        return "/app/life/education/bfxyList";
    }

    //积分入户计算
    //一票否决
    @RequestMapping(value = "/newCitizens/ban")
    public String ban() {
        return "/app/life/newCitizens/ban";
    }

    //计算
    @RequestMapping(value = "/newCitizens/calc")
    public String calc() {
        return "/app/life/newCitizens/calc";
    }

    //公共WIFI（5G）
    @RequestMapping(value = "/publicWIFI")
    public String publicWIFI() {
        return "/app/life/wifi";
    }

    //向用户发送WIFI密码（短信）
    @ControlLog(operateType = "/sendWIFIInfo", context = "向用户发送WIFI密码（短信）")
    @FormCommit     //防止重复提交
    @RequestMapping(value = "/sendWIFIInfo")
    @ResponseBody
    public Resp sendWIFIInfo(String phone) {
        try {
            commonService.sendWIFIMsg(phone);
        } catch (UnsupportedEncodingException e) {
            throw new MyException("发送短信失败！不支持的编码格式！", 0);
        }
        Resp resp = new Resp(RespCode.SUCCESS);
        return resp;
    }

    //食品小作坊
    @RequestMapping(value = "/foodWorkshop")
    public String foodWorkshop() {
        return "app/life/smallFoodWorkshop";
    }

    //食品小作坊查询
    @RequestMapping(value = "/foodWorkshopSearch")
    @ResponseBody
    public Resp foodWorkshopSearch(String keyword) {
        SmallFoodWorkshop foodWorkshop = commonService.find(keyword);
        Resp resp;
        if (foodWorkshop == null) {
            resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("未找到相关内容！");
        } else {
            resp = new Resp(RespCode.SUCCESS);
            resp.setData(foodWorkshop);
        }
        return resp;
    }
}
