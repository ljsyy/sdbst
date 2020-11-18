package com.unifs.sdbst.app.controller.menus;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.unifs.sdbst.app.bean.government.ProtectInfo;
import com.unifs.sdbst.app.bean.government.SysGuide;
import com.unifs.sdbst.app.common.constant.GlobalURL;
import com.unifs.sdbst.app.service.government.ProtectEmailService;
import com.unifs.sdbst.app.service.government.SysGuideService;
import com.unifs.sdbst.app.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 政务  首相 相关功能 的 控制器
 * @author
 *
 */

@Controller
@RequestMapping("/government")
public class GovernmentController {
//	@Autowired
//	private MapService mapService;
	@Autowired
	private SysGuideService sysGuideService;
	
	// 消费维权
	@RequestMapping("protect")
	public String protect(){
		return "/app/government/protect";
	}
	
	// 消费维权 /表单提交  发信
	@RequestMapping("protect/submit")
	@ResponseBody
	public String protectSubmit(ProtectInfo pi){
		ProtectEmailService pes=new ProtectEmailService();
		return pes.sendEmail(pi);
	}
	/*
	// 网上信访
	@RequestMapping("petition")
	public String petition(){
		return "/modules/government/SinglePage/petition";
	}
	// 网上信访/局长信箱
	@RequestMapping("petition/chiefemail")
	public String chiefEmail(){
		return "/modules/government/petition/chiefEmail";
	}

	// 网上信访/局长信箱  发信
	@RequestMapping("petition/chiefemail/submit")
	@ResponseBody
	public String chiefEmailSubmit(ChiefEmailInfo pi){
		ChiefEmailService pes=new ChiefEmailService();
		return pes.sendEmail(pi);
	}*/
	// 网上信访/使用帮助
	@RequestMapping("petition/help")
	public String petitionHelp(){
		return "/modules/government/petition/help";
	}
	
	// 网上信访/投诉电话
	@RequestMapping("petition/complainPhone")
	public String petitionComplain(){
		return "/app/government/petition/complainPhone";
	}
	
	// 网上信访/信访查询/处理中
	@RequestMapping("petition/search/dispose")
	public String petitionDispose(){
		return "/modules/government/petition/dispose";
	}
	
	// 网上信访/信访查询/回复
	@RequestMapping("petition/search/reply")
	public String petitionReply(){
		return "/modules/government/petition/reply";
	}
	
	//机构职能 / 领导班子 / 详情
	@RequestMapping("institution/detail")
	public String institutionDetail(String id,String no,Model model) {
		model.addAttribute("id",id);
		model.addAttribute("no",no);
		return "/modules/government/institution/personDetail";
	}
	
	// 三农服务 三家要闻  详情
	@RequestMapping("yaowenlist")
	public String yaoWenlist(String fid,String id,Model model){
		model.addAttribute("fid", fid);
		model.addAttribute("id", id);
		return "/modules/government/sanNongService/yaoWenList";
	}
	
	// 三农服务 三家要闻  
	@RequestMapping("yaowen")
	public String yaoWen(){
		return "/modules/government/sanNongService/yaoWen";
	}
	
	// 三农服务 三农政策法规 详情
	@RequestMapping("faguilist")
	public String faGuiList(String fid,String id,Model model){
		model.addAttribute("fid", fid);
		model.addAttribute("id", id);
		return "/modules/government/sanNongService/faGuiList";
	}
	
	// 三农服务 三农政策法规
	@RequestMapping("fagui")
	public String faGui(){
		return "/modules/government/sanNongService/faGui";
	}
	
	// 失信者名单
	@RequestMapping("others/unbelievers")
	public String unbelievers(){
		return "/modules/government/others/unbelievers";
	}
	
	// 避难场所
	@RequestMapping("others/shelter")
	public String shelter(){
		return "/modules/government/others/shelter";
	}
		
	// 办事预约/ 我要预约
	@RequestMapping("/order/start")
	public String OrderStart() {
		return "modules/government/order/start";
	}
	

	//----政务公开——>财政预算
	@RequestMapping(value = "zwgk/czys")
	public String czys() {
		return "modules/government/zwgk/czys";
	}
	//----政务公开——>财政预算详情
	@RequestMapping(value = "zwgk/DetailsPage")
	public String DetailsPage() {
		return "modules/government/zwgk/DetailsPage";
	}
	//一门式
	@RequestMapping(value = "zwfw/OneGate")
	public String OneGate() {
		return "modules/government/zwfw/OneGate";
	}
	//市民之窗
	@RequestMapping(value = "publicwindow/public")
	public String publicwindow() {
		return "app/government/publicwindow/public";
	}
	//市民之窗接口
	@RequestMapping("getjsonPublicWindow")
	@ResponseBody
	public String test1(String url,String num) throws UnsupportedEncodingException {
		url=URLEncoder.encode(url,"UTF-8");
		String result=HttpUtil.sendPost(GlobalURL.POST_URL+"/app/menu/getjson?url="+url, null);
		return result;
	}
	//市民之窗
	@RequestMapping(value = "publicwindow/publicdetails")
	public String publicdetails(Model model,String id) {
		model.addAttribute("id",id);
		return "/government/publicwindow/publicdetails";
	}
	//市民之窗  
	/**
	 * 根据ID查询数据库得到经纬度,若没有查到,则将id,lat,lng插入数据库,返回lat,lng;
	 * @param id	接口自还的ID
	 * @param lat	接口自带的
	 * @param lng	接口自带的
	 * @return  返回json数据
	 * 测试  http://X:8080/shundemap/government/publicwindow/getCoordinate?id=4C54F9C1F191478CA26ED8B140A8FD5D&lat=22.932486&lng=113.057243
	 * */
//	@RequestMapping(value = "publicwindow/getCoordinate")
//	@ResponseBody
//	public Map<String,String> getCoordinate(String id,String lat,String lng) {
//		Map<String,String> map = mapService.getOrInsert(id,lat,lng,"window");
//		return map;
//	}
	
	//统计表
	@RequestMapping(value = "statisticstable/statistics")
	public String statisticstable() {
		return "modules/government/statisticstable/statistics";
	}
	
	//存储锁定信息
	/*@RequestMapping(value = "appoint/save", method = RequestMethod.POST)
	@ResponseBody
	//返回json数据
	public void binding(HttpServletRequest request, HttpServletResponse response, String data) {
		System.out.println(JSONObject.fromObject(data));
		HttpSession session = request.getSession();
		session.setAttribute("appointSave", JSONObject.fromObject(data));
		//session.removeAttribute("appointSave");
		//System.out.println(jsonData);
	}
	
	//清除存储锁定信息
	@RequestMapping(value = "appoint/remove", method = RequestMethod.GET)
	@ResponseBody
	//返回json数据
	public void appointRemove(HttpServletRequest request, HttpServletResponse response, String data) {
		System.out.println(JSONObject.fromObject(data));
		HttpSession session = request.getSession();
		//session.setAttribute("appointSave", JSONObject.fromObject(data));
		session.removeAttribute("appointSave");
		//System.out.println(jsonData);
	}*/

	// ios定位test
	@RequestMapping("appoint/test")
	public String test() {
		return "modules/government/appoint/test";
	}
	
	// 安卓定位test
	@RequestMapping("appoint/test1")
	public String test1() {
		return "modules/government/appoint/test1";
	}
	
	// 政策匹配test
	@RequestMapping("qy/test")
	public String qytest() {
		return "modules/government/qy/test";
	}
	
	//企业直通车登录返回信息
	@RequestMapping(value = "qy/save", method = RequestMethod.POST)
	@ResponseBody
	//返回json数据
	public void login(HttpServletRequest request, HttpServletResponse response, String userName, String password) {
		System.out.println(userName);
		System.out.println(password);
		HttpSession session = request.getSession();
		session.setAttribute("qyUserName", userName);
		session.setAttribute("qyPassword", password);
	}
	
	//清除存储锁定信息
	@RequestMapping(value = "qy/remove", method = RequestMethod.GET)
	@ResponseBody
	//返回json数据
	public void qyRemove(HttpServletRequest request, HttpServletResponse response, String data) {
		//System.out.println(JSONObject.fromObject(data));
		HttpSession session = request.getSession();
		session.removeAttribute("qyUserName");
		session.removeAttribute("qyPassword");
		
	}

	// 列表
	@RequestMapping("qy/list")
	public String qyList(String type,Model model) {
		model.addAttribute("type",type);
		return "/app/government/qy/list";
	}
	
	// 详情
	@RequestMapping("qy/detail")
	public String qyDetail(String id,String type,Model model) {
		model.addAttribute("type",type);
		model.addAttribute("id",id);
		return "/app/government/qy/detail";
	}
	
	// 提交咨询(需登录)
	@RequestMapping("qy/question")
	public String qyQuestion() {
		return "modules/government/qy/question";
	}
	
	
	//在线调研
	@RequestMapping("/OnlineSurvey/index")
	public String onlineSurvey(){
		return "/app/government/OnlineSurvey/index";
	}

	@RequestMapping("/OnlineSurvey/satic")
	public String onlineSurveySatic(){
		return "/app/government/OnlineSurvey/satic";
	}

	//摇一摇
	@RequestMapping("/shake")
	public String shake(){
		return "/app/government/OnlineSurvey/shake";
	}
	// 顺德简介、历史沿革、行政区划
	@RequestMapping("shunde")
	public String shunde(String type,Model model) {
        model.addAttribute("type",type);
		return "/app/government/inshunde/shunde";
	}
	
	// 服务电话
	@RequestMapping("servicePhone")
	public String servicePhone() {
		return "app/government/others/servicePhone";
	}
	
	// 行政审批-进度查询
	@RequestMapping("/queryProgress")
	public String queryProgress() {
		return "app/government/others/queryProgress";
	}
	// 行政审批-进度查询结果
	@RequestMapping("/queryProgressResult")
	public String queryProgressResult(String code,Model model) {
		model.addAttribute("code",code);
		return "app/government/others/queryProgressResult";
	}

	@RequestMapping(value = "/searchList")
	public String searchList(String keyword,Model model) {
		model.addAttribute("keyword",keyword);
		return "app/government/zwfw/xzsp/searchList";
	}

	@RequestMapping(value = "/guideDetail")
	public String guideDetail(String id,String title,boolean noHead,Model model) {
		model.addAttribute("id",id);
		model.addAttribute("title",title);
		model.addAttribute("noHead",noHead);
		return "app/government/zwfw/xzsp/guideDetail";
	}
	
	/***
	 * 行政审批 模糊查询 已中转
	 * */
	@ResponseBody
	@RequestMapping("/xzsp")
	public String xzsp(String keyword) throws UnsupportedEncodingException {
		keyword = URLEncoder.encode(keyword,"UTF-8");
		String url= new String("http://19.200.90.104/RXWSSTWeb/xzsp.web?itemThemeType&areaId=440606&typeId=0&keyWord="+keyword);
		//String url= new String("http://19.200.90.104/RXWSSTWeb/xzsp.web?itemThemeType*areaId=440606*typeId=0*keyWord="+keyword);
		System.out.println(url);
		String result=HttpUtil.sendGet(url, null,"");
		System.out.println("行政审批模糊查询="+result);
		return result;
	}


	
	// 社会团体
	@RequestMapping("/socialGroups")
	public String socialGroups() {
		return "app/government/others/socialGroups";
	}
	
	// 场景式服务
	@RequestMapping("/scene")
	public String scene(String type,String url,Model model) {
		model.addAttribute("type",type);
		model.addAttribute("url",url);
		return "app/government/scene/newList";
	}
	
	/**
	 * 网厅
	 * */
	@RequestMapping(value = "netHall")
	public String netHall() {
		return "modules/government/scene/newList";
	}	
	
	//行政许可
	@RequestMapping(value = "/license")
	public String license() {
		return "app/government/qy/license";
	}	
	
	
	//文章获取时间列表
	@RequestMapping(value = "articleList")
	public String articleList(Model model,String title,String id) {
		model.addAttribute("title",title);
		model.addAttribute("id",id);
		return "app/government/timelist/articleList";
	}
	
	//守合同重信用企业资料
	@RequestMapping(value = "/creditQY")
	public String creditQY() {
		return "app/government/qy/creditQY";
	}	
	
	//安全生产投诉
	@RequestMapping(value = "safeComplaint")
	public String safeComplaint() {
		return "modules/government/protect/safetyComplaint";
	}	
	
	//政府部门电话	列表
	@RequestMapping(value = "govPhone/list")
	public String govPhoneList() {
		return "modules/government/govPhone/list";
	}	
	//政府部门电话	详情
	@RequestMapping(value = "govPhone/detail")
	public String govPhoneDetail() {
		return "modules/government/govPhone/detail";
	}
	
	//公证处 办照进度查询
	@RequestMapping(value = "notaryOffice/queryProgress")
	public String notaryOfficeQueryProgress() {
		return "app/government/notaryOffice/queryProgress";
	}
	
	//公证处 办照进度查询接口
	@RequestMapping(value = "/gzc/interface")
	@ResponseBody
	public Map<String,Object> gzcInterface(String num,String name) throws UnsupportedEncodingException{
		Map<String,Object> map = new HashMap<String,Object>();
		String url = "http://19.200.90.219/api/Bzlc?lch="+num+"&vname="+name;
		System.out.println(url);
		String result=HttpUtil.sendGet(url, null,"");
		JSONObject jsonObject=JSONObject.parseObject(result);
		String ret = jsonObject.getString("ret");	
		String data = jsonObject.getString("data");	
		System.out.println(result);
		map.put("name", "公证处办照进度查询");
		map.put("ret", ret);
		map.put("data", data);
		System.out.println(map);
		return map;
	}
	
	//公证处 办证指南列表
	@RequestMapping(value = "/notaryOffice/regiGuide")
	public String regiGuide() {
		return "/app/government/notaryOffice/regiGuide";
	}
	
	//公证处 办证指南详情
	@RequestMapping(value = "/notaryOffice/detail")
	public String regiGuideDetail(String id,Model model) {
		model.addAttribute("id",id);
		return "/app/government/notaryOffice/detail";
	}
	
	//危险化学品办事指南 列表
	@RequestMapping(value = "/chemicalGuide/list")
	public String chemicalGuideList() {
		return "app/government/chemicalGuide/list";
	}
	
	//危险化学品办事指南 详情
	@RequestMapping(value = "/chemicalGuide/detail")
	public String chemicalGuideDetail(String id,String type,Model model) {
		model.addAttribute("id",id);
		model.addAttribute("type",type);
		return "app/government/chemicalGuide/detail";
	}

	//危险化学品办事指南 列表接口
	@RequestMapping(value = "/chemicalGuide/getList")
	@ResponseBody
	public Map<String,Object> getList(){
		Map<String,Object> map = Maps.newLinkedHashMap();
		List<SysGuide> list = sysGuideService.getList();
		map.put("name", "办事指南");
		map.put("data", list);
		return map;
	}
	//危险化学品办事指南 详情接口
	@RequestMapping(value = "/chemicalGuide/getDetail")
	@ResponseBody
	public Map<String,Object> getDetail(String id){
		Map<String,Object> map = Maps.newLinkedHashMap();
		List<SysGuide> list = sysGuideService.getDetail(id);
		map.put("name", "办事指南");
		map.put("data", list);
		return map;
	}
	
	// 政法委-律师维权
	@RequestMapping("zfw")
	public String zfw() {
		return "modules/government/inshunde/zfw";
	}
	
}
