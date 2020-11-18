/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.controller.sdcount;

import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.sdcount.Twoyearnotnuit;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.sdcount.TwoyearnotnuitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 两年不带单位统计Controller
 * @author cp
 * @version 2017-12-18
 */
@Controller
@RequestMapping(value = "/sdcount/twoyearnotnuit")
public class TwoyearnotnuitController {

	@Autowired
	private TwoyearnotnuitService twoyearnotnuitService;
	
//	@ModelAttribute
//	public Twoyearnotnuit get(@RequestParam(required=false) String id) {
//		Twoyearnotnuit entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = twoyearnotnuitService.get(id);
//		}
//		if (entity == null){
//			entity = new Twoyearnotnuit();
//		}
//		return entity;
//	}
	
//	@RequestMapping(value = {"list", ""})
//	public String list(Twoyearnotnuit twoyearnotnuit, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<Twoyearnotnuit> page = twoyearnotnuitService.findPage(new Page<Twoyearnotnuit>(request, response), twoyearnotnuit);
//		model.addAttribute("page", page);
//		return "modules/count/twoyearnotnuitList";
//	}

	@RequestMapping(value = "form")
	public String form(Twoyearnotnuit twoyearnotnuit, Model model) {
		model.addAttribute("twoyearnotnuit", twoyearnotnuit);
		return "modules/count/twoyearnotnuitForm";
	}

//	@RequestMapping(value = "save")
//	public String save(Twoyearnotnuit twoyearnotnuit, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, twoyearnotnuit)){
//			return form(twoyearnotnuit, model);
//		}
//		twoyearnotnuitService.save(twoyearnotnuit);
//		addMessage(redirectAttributes, "保存成功");
//		return "redirect:"+Global.getAdminPath()+"/count/twoyearnotnuit/?repage";
//	}
	
//	@RequestMapping(value = "delete")
//	public String delete(Twoyearnotnuit twoyearnotnuit, RedirectAttributes redirectAttributes) {
//		twoyearnotnuitService.delete(twoyearnotnuit);
//		addMessage(redirectAttributes, "删除成功");
//		return "redirect:"+Global.getAdminPath()+"/count/twoyearnotnuit/?repage";
//	}
	/**
	 * 地方税收合计 财政支出 国家税收合计  （贷款 存贷差额）  存款 
		合同利用外资 利用外贸项目 利用外资 进出口贸易总和  批发和零售总额
		社会消费品总额 限额批发零售财务指标 限额住宿餐饮财务指标 
		全社会用电量
		综合生产总值 接口
	 * @param year
	 * @param type
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "yearnotnuit")
	public String yearnotnuit(String year, String type, HttpServletRequest request, HttpServletResponse response){
		int a=Integer.parseInt(year);
		int num=0;
		String name=null;
		List<Twoyearnotnuit> list= new ArrayList<>();
		if(type.equals("限额住宿餐饮财务指标")||type.equals("限额批发零售财务指标")){
			name="";
			 list=twoyearnotnuitService.yearList(type, year, name);
		}else{
		if(type.equals("地方税收合计")){
			name="各项税收总合计";
		}if(type.equals("单位数")){
			name="全部工业企业单位数";
		}if(type.equals("总产值")){
			name="总计";
		}if(type.equals("全社会固定资产投资情况")){
			name="完成投资总计";
		}if(type.equals("财政支出")){
			name="财政支出总计";
		}if(type.equals("国家税收合计")){
			name="各项税收合计";
		}if(type.equals("存款")){
			name="全区各项存款合计";
		}if(type.equals("贷款")){
			name="全区各项贷款合计";
		}if(type.equals("存贷差额")){
			name="存贷差额";
		}if(type.equals("社会消费品总额")){
			name="社会消费品零售总额";
		}if(type.equals("进出口贸易总和")){
			name="进出口贸易总额";
		}if(type.equals("利用外贸项目")){
			name="利用外资项目合计";
		}if(type.equals("合同利用外资")){
			name="合同利用外资合计";
		}if(type.equals("利用外资")){
			name="实际利用外资合计";
		}if(type.equals("批发和零售总额")){
			name="批发总额";
		}if(type.equals("批发和零售总额")){
			name="零售总额";
		}if(type.equals("全社会用电量")){
			name="合计";
		}if(type.equals("综合生产总值")){
			name="地区生产总值";
		}
		for(int i=a;i<2019;i--){
			if(num==5){
				break;
			}
			Twoyearnotnuit sd=twoyearnotnuitService.year(type, a+"", name);
			if(null==sd){
				sd =new Twoyearnotnuit();
				sd.setYearDate(a+"");
				sd.setName(name);
				sd.setFirstNum("0");
				sd.setLastNum("0");
				sd.setZzl("0");
			}
			list.add(sd);
			num++;
			a--;
		}
		}
//		if (WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM)) {
//			ResultEntity entity = new ResultEntity(true);
//			entity.setData(list);
//			OtherUtils.sendJsonToMobile(entity, request, response);
//		}
		Resp entity = new Resp(RespCode.SUCCESS, list);
		String jsonObject = JSON.toJSONString(entity);
		return jsonObject;
	}
	/**
	 * 地方税收合计 财政支出 国家税收合计  （贷款 存贷差额）  存款 
		合同利用外资 利用外贸项目 利用外资 进出口贸易总和  批发和零售总额
		社会消费品总额 限额批发零售财务指标 限额住宿餐饮财务指标 
		全社会用电量
		综合生产总值 子类接口
	 */
	@ResponseBody
	@RequestMapping(value = "yearnotnuitzl")
	public String yearnotnuitzl(String year, String type, HttpServletRequest request, HttpServletResponse response){
		String parentType=null;
		String name=null;
		if(type.equals("地方税收合计")){
			parentType="各项税收总合计";
		}if(type.equals("单位数")){
			parentType="全部工业企业单位数";
		}if(type.equals("总产值")){
			parentType="总计";
		}if(type.equals("全社会固定资产投资情况")){
			parentType="完成投资总计";
		}if(type.equals("财政支出")){
			parentType="财政支出总计";
		}if(type.equals("国家税收合计")){
			parentType="各项税收合计";
		}if(type.equals("存款")){
			parentType="全区各项存款合计";
		}if(type.equals("贷款")){
			parentType="全区各项贷款合计";
		}if(type.equals("存贷差额")){
			parentType="存贷差额";
		}if(type.equals("社会消费品总额")){
			parentType="按销售单位所在地分组";
		}if(type.equals("进出口贸易总和")){
			parentType="进出口贸易总额";
			name ="按出口地区分";
		}if(type.equals("利用外贸项目")){
			parentType="按投资方式分";
		}if(type.equals("合同利用外资")){
			parentType="按投资方式分";
		}if(type.equals("利用外资")){
			parentType="按投资方式分";
		}if(type.equals("批发和零售总额")){
			parentType="按行业分类";
		}if(type.equals("批发和零售总额")){
			parentType="零售总额";
		}if(type.equals("全社会用电量")){
			parentType=null;
		}if(type.equals("综合生产总值")){
			parentType="地区生产总值 ";
		}
		List<Twoyearnotnuit> list=twoyearnotnuitService.yearzl(type, year, parentType,name);
//		if (WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM)) {
//			ResultEntity entity = new ResultEntity(true);
//			entity.setData(list);
//			OtherUtils.sendJsonToMobile(entity, request, response);
//		}
		Resp entity = new Resp(RespCode.SUCCESS, list);
		String jsonObject = JSON.toJSONString(entity);
		return jsonObject;
	}
//	//子类列表通过名称查询5 年数据
//		@RequestMapping(value = "yearnotnuitFindName")
//		public void twoyearhavenuitFindName(String year, String type, String name, String parentType, HttpServletRequest request, HttpServletResponse response){
//			List<Twoyearnotnuit> list= new ArrayList<>();
//			int a=Integer.parseInt(year);
//			int num=0;
//			for(int i=a;i<2019;i--){
//				if(num==5){
//					break;
//				}
//				Twoyearnotnuit sd= twoyearnotnuitService.year(type, a+"", name);
//				if(sd==null){
//					sd =new Twoyearnotnuit();
//					sd =new Twoyearnotnuit();
//					sd.setYearDate(a+"");
//					sd.setName(name);
//					sd.setFirstNum("0");
//					sd.setLastNum("0");
//					sd.setZzl("0");
//				}
//				list.add(sd);
//				num++;
//				a--;
//			}
//			if (WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM)) {
//				ResultEntity entity = new ResultEntity(true);
//				entity.setData(list);
//				OtherUtils.sendJsonToMobile(entity, request, response);
//			}
//		}
	/**
	 * 地方税收合计 财政支出 国家税收合计  （贷款 存贷差额）  存款 
		合同利用外资 利用外贸项目 利用外资 进出口贸易总和  批发和零售总额
		社会消费品总额 限额批发零售财务指标 限额住宿餐饮财务指标 
		全社会用电量
		综合生产总值 三级子菜单接口
	 * @param year
	 * @param type
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "yearnotnuitThree")
	public void yearnotnuitThree(String year, String type, HttpServletRequest request, HttpServletResponse response){
		String parentType=null;
		if(type.equals("地方税收合计")){
			parentType="在合计中";
		}if(type.equals("财政支出")){
			parentType="一般公共预算支出合计";
			//政府性基金支出合计
		}if(type.equals("国家税收合计")){
			parentType="在合计中";
		}if(type.equals("综合生产总值")){
			parentType="第二产业";
			//第三产业
		}
	}
//	/**
//	 *
//	 * @param 两年不带单位导入
//	 * @param redirectAttributes
//	 * @return
//	 */
//	@RequestMapping(value = "import", method = RequestMethod.POST)
//	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
//		if (Global.isDemoMode()) {
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/sys/checkwork/list?repage";
//		}
//		try {
//			int successNum = 0;
//			int failureNum = 0;
//			StringBuilder failureMsg = new StringBuilder();
//			ImportExcel ei = new ImportExcel(file, 1, 0);
//			List<Twoyearnotnuit> list = ei.getDataList(Twoyearnotnuit.class);
//			for (Twoyearnotnuit twoyearnotnuit : list) {
//				try {
//					BeanValidators.validateWithException(validator, twoyearnotnuit);// 服务端验证
//					twoyearnotnuitService.save(twoyearnotnuit);
//					successNum++;
//				} catch (ConstraintViolationException ex) {
//					failureMsg.append("<br/> :" + twoyearnotnuit.getName() + ",导入失败<br/>");
//					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//					for (String message : messageList) {
//						failureMsg.append(message + "; ");
//						failureNum++;
//					}
//				} catch (Exception ex) {
//					failureMsg.append("<br/> :" + twoyearnotnuit.getName() + ",导入失败：" + ex.getMessage());
//				}
//			}
//			if (failureNum > 0) {
//				failureMsg.insert(0, "，失败 " + failureNum + " 条数据，导入信息如下：");
//			}
//			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条数据" + failureMsg);
//		} catch (Exception e) {
//			addMessage(redirectAttributes, "导入数据失败！失败信息：" + e.getMessage());
//		}
//		return "redirect:"+Global.getAdminPath()+"/count/twoyearnotnuit/?repage";
//	}
}