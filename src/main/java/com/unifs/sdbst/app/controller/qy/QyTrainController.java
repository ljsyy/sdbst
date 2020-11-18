package com.unifs.sdbst.app.controller.qy;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.utils.HttpUtil;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


/**
 * 企业直通车 控制器
 * @author pxy 2018-09-03
 * 
 * 外网：http://sdztc.shunde.gov.cn
 * 内网：http://19.200.90.109
 */

@Controller
@RequestMapping("qy/train")
public class QyTrainController {
	private String token;	//token
	public String getInfo ;	//获取用户信息
	public String updateInfo ;	//修改会员信息
	public String quit ;	//退出登录
	public String zcpp ;	//政策匹配
	public String wdzxlb ;	//我的咨询列表
	public String wdzxxq ;	//我的咨询详情
	public String tjzx ;	//提交咨询
	@Value("${qy.train.url}")
	private String baseUrl;
	
	private String cookie;	//定义cookie-JSESSIONID

	private Resp resp;

	/***
	 * 登录
	 * 
	 * userName：用户名
	 * password：密码
	 * */
	@RequestMapping(value = "/login")
	@ResponseBody
	public Resp login(String userName, String password) throws IOException {
		String login =HttpUtil.sendGet(baseUrl+"/QYZTCWS/member.do?login&userName="+userName+"&password="+password,"","");
    	JSONObject jsonObject=JSONObject.parseObject(login);
		String errorCode = jsonObject.getString("errorCode");
		System.out.println("登录======"+login);
    	if(errorCode.equals("0")) {
			String token=(jsonObject.getJSONObject("data")).getString("token");
			System.out.println(token);
			this.token=token;
			resp=new Resp(RespCode.SUCCESS);
			return resp;
		}else{
			System.out.println("body："+login);
			Resp resp=new Resp(RespCode.DEFAULT);
			resp.setMsg(jsonObject.getString("errorMsg"));
			return resp;
		}
		
	}

	
	/*获取用户信息（需登录）*/
	@RequestMapping(value = "getInfo")
	@ResponseBody
	public String getInfo() throws  IOException {
		String result=HttpUtil.sendGet(baseUrl+"/QYZTCWS/member.do?getMemberInfo&token="+token,"","");
		System.out.println("获取用户信息======"+result);
		return result;
	}
	
	/***
	 * 修改会员信息（需登录）
	 * 
	 * realName：真名
	 * etpName：企业名称
	 * jobPosition：岗位名称
	 * etpDistrictCode：企业所在区域编码
	 * industryCode：企业行业类别编码
	 * scaleCode：企业规模编码
	 * */
	@RequestMapping(value = "updateInfo")
	@ResponseBody
	public String updateInfo(String realName, String etpName, String jobPosition, String etpDistrictCode, String industryCode, String scaleCode) throws InterruptedException, IOException {
		String result=HttpUtil.sendGet(baseUrl+"/QYZTCWS/member.do?updateMemberInfo&realName="+realName+"&etpName="+etpName+"&jobPosition="+jobPosition+"&etpDistrictCode=" +
				""+etpDistrictCode+"&industryCode="+industryCode+"&scaleCode="+scaleCode+"&token="+token,"","");
		System.out.println("修改会员信息======"+result);
		return result;
	}
	
	/*退出登录（需登录）*/
	@RequestMapping(value = "quit")
	@ResponseBody
	public String quit() throws InterruptedException, IOException {
		String result=HttpUtil.sendGet(baseUrl+"/QYZTCWS/member.do?quit&token="+token,"","");
		System.out.println("退出登录======"+result);
		return result;
	}

	/*政策匹配 需要登录*/
	@RequestMapping(value = "zcpp")
	@ResponseBody
	public String zcpp() throws IOException {
		String result=HttpUtil.sendGet(baseUrl+"/QYZTCWS/policy.do?getEtpMatchPolicyList&token="+token,"","");
		System.out.println("政策匹配======"+result);
		return result;
	}
	
	/*获取我的咨询列表*/
	@RequestMapping(value = "wdzxlb")
	@ResponseBody
	public String wdzxlb(String start, String limit) throws InterruptedException, IOException {
		String result=HttpUtil.sendGet(baseUrl+"/QYZTCWS/faq.do?getMyFaqRunList&start="+start+"&limit="+limit,"","");
		System.out.println("获取我的咨询列表======"+result);
		return result;
	}
	
	/*获取我的咨询详情*/
	@RequestMapping(value = "wdzxxq")
	@ResponseBody
	public String wdzxxq(String id)  {
		String result=HttpUtil.sendGet(baseUrl+"/QYZTCWS/faq.do?getMyFaqRunById&id="+id,"","");
		System.out.println("获取我的咨询详情======"+result);

		return result;
	}
	
	/***
	 * 提交咨询（需登录）
	 * 
	 * typeCode：Id
	 * title：咨询标题
	 * question：咨询问题描述
	 * phone：联系手机
	 * eMail：电子邮箱
	 * fileNameStr：附件名称字符窜，多个用英文@符合分隔
	 * base64ImgStr：与fileNameStr对应，图片附件的base64编码字符窜，多个用英文@符合分隔
	 * */
	@RequestMapping(value = "tjzx")
	@ResponseBody
	public String tjzx(String typeCode, String title,String question, String phone, String eMail, String fileNameStr, String base64ImgStr) throws InterruptedException, IOException {
		String result=HttpUtil.sendGet(baseUrl+"/QYZTCWS/faq.do?addFaqRun&typeCode="+typeCode+"&title="+title+"&question="+question+"&phone="
				+phone+"&eMail="+eMail+"&fileNameStr="+fileNameStr+"&base64ImgStr="+base64ImgStr+"&token="+token,"","");

		System.out.println("提交咨询======"+result);

		return result;
	}
	
	/***
	 * 获取镇街区域（总）
	 * */
	@ResponseBody
	@RequestMapping("getAllZJ")
	public String getAllZJ() {
		String url = new String(baseUrl+"/QYZTCWX/common.do?getCommonTypeList&code=QY");
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("获取镇街区域（总）="+result);
		return result;
	}
	
	/***
	 * 获取行业分类（总）
	 * */
	@ResponseBody
	@RequestMapping("getAllHY")
	public String getAllHY() {
		String url = new String(baseUrl+"/QYZTCWX/common.do?getCommonTypeList&code=HYLX");
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("获取行业分类（总）="+result);
		return result;
	}
	
	/***
	 * 获取验证码
	 * 
	 * phone：手机号码
	 * */
	@ResponseBody
	@RequestMapping("getCode")
	public String getCode(String phone) {
		String url = new String(baseUrl+"/QYZTCWS/member.do?getVerifyCode&phone="+phone);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("验证码="+result);
		return result;
	}
	
	/***
	 * 注册
	 * 
	 * userName：用户名
	 * password：密码
	 * realName：真名
	 * verifyCode：验证码（看获取验证码接口）
	 * */
	@ResponseBody
	@RequestMapping("/register")
	public String register(String userName, String password, String realName, String verifyCode) {
		String url = new String(baseUrl+"/QYZTCWS/member.do?register&userName="+userName+"&password="+password+"&realName="+realName+"&verifyCode="+verifyCode);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("注册="+result);
		return result;
	}
	
	/***
	 * 搜索，获取政策列表
	 * 
	 * typeCodes:政策类型编码s，支持多个用英文逗号分隔
	 * districtCode:区域编码
	 * scaleCode:企业规模编码
	 * industryCode:行业类别编码
	 * gradeCode:政策级别编码
	 * keyword:关键字
	 * start:起始下标
	 * limit:每页多少条
	 * @throws UnsupportedEncodingException 
	 * */
	@ResponseBody
	@RequestMapping("getList")
	public String getList(String typeCodes, String districtCode, String scaleCode, String industryCode, String gradeCode, String keyword, String start, String limit) throws UnsupportedEncodingException {
		keyword = URLEncoder.encode(keyword,"UTF-8");
		String url = new String(baseUrl+"/QYZTCWS/policy.do?getPolicyList&typeCodes="+typeCodes+"&districtCode="+districtCode+"&scaleCode="+scaleCode+"&industryCode="+industryCode+"&gradeCode="+gradeCode+"&keyword="+keyword+"&start="+start+"&limit="+limit);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("政策列表="+result);
		return result;
	}
	
	/***
	 * 热门政策列表
	 * */
	@ResponseBody
	@RequestMapping("hotList")
	public String hotList(String start, String limit) {
		String url = new String(baseUrl+"/QYZTCWS/policy.do?getPolicyHotList&start="+start+"&limit="+limit);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("热门政策列表="+result);
		return result;
	}
	
	/***
	 * 通过ID，获取政策详情
	 * */
	@ResponseBody
	@RequestMapping("detail")
	public String detail(String id) {
		String url = new String(baseUrl+"/QYZTCWS/policy.do?getPolicyById&id="+id);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("政策详情="+result);
		return result;
	}
	
	/***
	 * 搜索，获取数据列表
	 * 
	 * typeCode:类型编码
	 * yearCode:年份编码
	 * */
	@ResponseBody
	@RequestMapping("getBigDataList")
	public String getBigDataList(String typeCode, String yearCode, String start, String limit) {
		String url = new String(baseUrl+"/QYZTCWS/bigdata.do?getBigdataList&typeCode="+typeCode+"&yearCode="+yearCode+"&start="+start+"&limit="+limit);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("数据列表="+result);
		return result;
	}
	
	/***
	 * 通过ID，获取数据详情
	 * */
	@ResponseBody
	@RequestMapping("getBigDataDeatil")
	public String getBigDataDeatil(String id) {
		String url = new String(baseUrl+"/QYZTCWS/bigdata.do?getBigdataById&id="+id);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("数据详情="+result);
		return result;
	}
	
	/***
	 * 获取在线咨询列表
	 * 
	 * typeCode:类型编码
	 * */
	@ResponseBody
	@RequestMapping("getFaqList")
	public String getFaqList(String typeCode, String start, String limit) {
		String url = new String(baseUrl+"/QYZTCWS/faq.do?getFaqList&typeCode="+typeCode+"&start="+start+"&limit="+limit);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("在线咨询列表="+result);
		return result;
	}
	
	/***
	 * 通过ID，获取咨询详情
	 * */
	@ResponseBody
	@RequestMapping("getFaqById")
	public String getFaqById(String id) {
		String url = new String(baseUrl+"/QYZTCWS/faq.do?getFaqById&id="+id);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("咨询详情="+result);
		return result;
	}
	
	/***
	 * 获取机构列表
	 * 
	 * type:传入值：1-本地机构；2-上级机构
	 * */
	@ResponseBody
	@RequestMapping("getDepListByType")
	public String getDepListByType(String type) {
		String url = new String(baseUrl+"/QYZTCWS/common.do?getDepListByType&type="+type);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("机构列表="+result);
		return result;
	}
	
	/***
	 * 获取企业列表
	 * 
	 * keyword:关键字，必填
	 * @throws UnsupportedEncodingException 
	 * */
	@ResponseBody
	@RequestMapping("getComListByType")
	public String getComListByType(String keyword, String start, String limit) throws UnsupportedEncodingException {
		keyword = URLEncoder.encode(keyword,"UTF-8");
		String url = new String(baseUrl+"/QYZTCWX/common.do?getEptList&start="+start+"&limit="+limit+"&keyword="+keyword);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("企业列表="+result);
		return result;
	}
	
	/***
	 * 根据ID，获取企业详情
	 * */
	@ResponseBody
	@RequestMapping("getEptById")
	public String getEptById(String id) {
		String url = new String(baseUrl+"/QYZTCWS/common.do?getEptById&id="+id);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("企业详情="+result);
		return result;
	}
	
	/***
	 * 个人信息，区域接口
	 * */
	@ResponseBody
	@RequestMapping("areaTree")
	public String areaTree() {
		String url = new String(baseUrl+"/QYZTCWX/common.do?getCommonTypeTree&code=QY");
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("区域="+result);
		return result;
	}
	
	/***
	 * 个人信息，行业接口
	 * */
	@ResponseBody
	@RequestMapping("HYTree")
	public String HYTree() {
		String url = new String(baseUrl+"/QYZTCWX/common.do?getCommonTypeTree&code=HYLX");
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("行业="+result);
		return result;
	}
	
	/***
	 * 根据code查询详情
	 * */
	@ResponseBody
	@RequestMapping("codeInfo")
	public String codeInfo(String code) {
		String url = new String(baseUrl+"/QYZTCWS/common.do?getCommonTypeByCode&code="+code);
		String result = HttpUtil.sendGet(url, "","");
		System.out.println("code详情="+result);
		return result;
	}
	
	// 登陆页面
	@RequestMapping("/loginPage")
	public String qylogin(String type, Model model) {
		model.addAttribute("type",type);
		return "app/government/qy/login";
	}
	
	// 政策匹配页面（需登录）
	@RequestMapping("loginList")
	public String qyLoginList() {
		return "app/government/qy/loginList";
	}
	
	// 详情页面（需登录）
	@RequestMapping("loginDetail")
	public String qyLoginDetail() {
		return "app/government/qy/loginDetail";
	}
	
	// 咨询与投诉（需登录）
	@RequestMapping("question")
	public String qyQuestion() {
		return "app/government/qy/question";
	}
	
	// 用户信息（需登录）
	@RequestMapping("personInfo")
	public String personInfo() {
		return "app/government/qy/personInfo";
	}
	
	// 政策订阅（需登录）
	@RequestMapping("subscribe")
	public String subscribe() {
		return "app/government/qy/subscribe";
	}
	
	// 政策订阅 设置（需登录）
	@RequestMapping("subscribeSet")
	public String subscribeSet() {
		return "app/government/qy/subscribeSet";
	}
	
	// 注册
	@RequestMapping("registerPage")
	public String registerPage() {
		return "app/government/qy/register";
	}
	
	// 新详情
	@RequestMapping("newDetail")
	public String newDetail(String id,Model model) {
		model.addAttribute("id",id);
		return "app/government/qy/newDetail";
	}
	
}
