/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.controller.sdcount;

import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.sdcount.SdTwoyearhavenuit;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.sdcount.SdTwoyearhavenuitService;
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
 * 两年带单位统计
 * @author cp
 * @version 2017-12-18
 */
@Controller
@RequestMapping(value = "/sdcount/sdTwoyearhavenuit")
public class SdTwoyearhavenuitController {

	@Autowired
	private SdTwoyearhavenuitService sdTwoyearhavenuitService;
	
//	@ModelAttribute
//	public SdTwoyearhavenuit get(@RequestParam(required=false) String id) {
//		SdTwoyearhavenuit entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = sdTwoyearhavenuitService.get(id);
//		}
//		if (entity == null){
//			entity = new SdTwoyearhavenuit();
//		}
//		return entity;
//	}
	
//	@RequestMapping(value = {"list", ""})
//	public String list(SdTwoyearhavenuit sdTwoyearhavenuit, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<SdTwoyearhavenuit> page = sdTwoyearhavenuitService.findPage(new Page<SdTwoyearhavenuit>(request, response), sdTwoyearhavenuit);
//		model.addAttribute("page", page);
//		return "modules/sdcount/sdTwoyearhavenuitList";
//	}

	@RequestMapping(value = "form")
	public String form(SdTwoyearhavenuit sdTwoyearhavenuit, Model model) {
		model.addAttribute("sdTwoyearhavenuit", sdTwoyearhavenuit);
		return "modules/sdcount/sdTwoyearhavenuitForm";
	}

//	@RequestMapping(value = "save")
//	public String save(SdTwoyearhavenuit sdTwoyearhavenuit, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, sdTwoyearhavenuit)){
//			return form(sdTwoyearhavenuit, model);
//		}
//		sdTwoyearhavenuitService.save(sdTwoyearhavenuit);
//		addMessage(redirectAttributes, "保存成功");
//		return "redirect:"+Global.getAdminPath()+"/sdcount/sdTwoyearhavenuit/?repage";
//	}
	
//	@RequestMapping(value = "delete")
//	public String delete(SdTwoyearhavenuit sdTwoyearhavenuit, RedirectAttributes redirectAttributes) {
//		sdTwoyearhavenuitService.delete(sdTwoyearhavenuit);
//		addMessage(redirectAttributes, "删除成功");
//		return "redirect:"+Global.getAdminPath()+"/sdcount/sdTwoyearhavenuit/?repage";
//	}
	/**
	 * *大中型企业活动情况  规模以上企业企业活动情况 产品质量  能源消费情况  房地产开发情况
		酒店经营情况  教育  文化  电力 交通  邮电   人均指标
		单位数 总产值  全社会固定资产投资情况  全社会企业生产情况  旅游人数  卫生
		国名基本情况接口
	 * @param year
	 * @param type
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "twoyearhavenuit")
	public String twoyearhavenuit(String year, String type, String parentType, HttpServletRequest request, HttpServletResponse response){
		int a=Integer.parseInt(year);
		int num=0;
		String name=null;
		List<SdTwoyearhavenuit> list =new ArrayList<>();
		/**大中型企业活动情况  规模以上企业企业活动情况 产品质量  能源消费情况  房地产开发情况
			酒店经营情况  教育  文化  电力 交通  邮电   人均指标
			*/
		if(	type.equals("大中型企业活动情况")||type.equals("规模以上企业企业活动情况")||
			type.equals("能源消费情况")||type.equals("产品质量")||
			type.equals("房地产开发情况")||type.equals("酒店经营情况")||
			type.equals("教育")||type.equals("文化")||
			type.equals("电力")||type.equals("交通")||
			type.equals("邮电")||type.equals("人均指标")||type.equals("全社会企业生产情况")){
			list= sdTwoyearhavenuitService.year(type, year);
		}else if(type.equals("国名基本情况")){
			list=sdTwoyearhavenuitService.yearGm(type, year, parentType);
		}else{
			if(type.equals("旅游人数")){
				name="接待过夜人数合计";
			}if(type.equals("旅游人数")){
				name="接待过夜人天合计";
			}if(type.equals("卫生")){
				name="机构数";
			}if(type.equals("卫生")){
				name="卫生工作人员总数";
			}if(type.equals("卫生")){
				name="病床数";
			}if(type.equals("全社会固定资产投资情况")){
				name="完成投资总计";
			}
			for(int i=a;i<2019;i--){
				if(num==5){
					break;
				}
				SdTwoyearhavenuit sd= sdTwoyearhavenuitService.yearTwo(type, a+"", name);
				if(sd==null){
					sd =new SdTwoyearhavenuit();
					sd.setName(name);
					sd.setYearDate(a+"");
					sd.setFirstNum("0");
					sd.setLastNum("0");
					sd.setZzl("0");
				}
				list.add(sd);
				num++;
				a--;
			}
		}
		//单位数 总产值  全社会固定资产投资情况  全社会企业生产情况  旅游人数  卫生
				// 国名基本情况   
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
	 * 大中型企业活动情况  规模以上企业企业活动情况 产品质量  能源消费情况  房地产开发情况
		酒店经营情况  教育  文化  电力 交通  邮电   人均指标
		单位数 总产值  全社会固定资产投资情况  全社会企业生产情况  旅游人数  卫生
		国名基本情况子类接口
	 * @param year
	 * @param type
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value = "twoyearhavenuitzl")
	public String twoyearhavenuitzl(String year, String type, HttpServletRequest request, HttpServletResponse response){
		String name =null;
		String parentType=null;
		if(type.equals("单位数")){
			parentType="全部工业企业单位数";
		}if(type.equals("总产值")){
			parentType="总计";
		}if(type.equals("全社会固定资产投资情况")){
			parentType="完成投资总计";
		}if(type.equals("旅游人数")){
			parentType="接待过夜人数合计";
			name="接待过夜人天合计";
		}if(type.equals("卫生")){
			parentType="机构数";
			name="卫生工作人员总数";
		}
		List<SdTwoyearhavenuit> list= sdTwoyearhavenuitService.yearTwozl(type, year, parentType, name);
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
//	@RequestMapping(value = "twoyearhavenuitFindName")
//	public void twoyearhavenuitFindName(String year, String type, String name, String parentType, HttpServletRequest request, HttpServletResponse response){
//		List<SdTwoyearhavenuit> list= new ArrayList<>();
//		int a=Integer.parseInt(year);
//		int num=0;
//		for(int i=a;i<2019;i--){
//			if(num==5){
//				break;
//			}
//			SdTwoyearhavenuit sd= sdTwoyearhavenuitService.findName(type, a+"", name);
//			if(sd==null){
//				sd =new SdTwoyearhavenuit();
//				sd.setName(name);
//				sd.setYearDate(a+"");
//				sd.setFirstNum("0");
//				sd.setLastNum("0");
//				sd.setZzl("0");
//			}
//			list.add(sd);
//			num++;
//			a--;
//		}
//		if (WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM)) {
//			ResultEntity entity = new ResultEntity(true);
//			entity.setData(list);
//			OtherUtils.sendJsonToMobile(entity, request, response);
//		}
//	}
//	/**
//	 *
//	 * @param 年度两年带单位导入
//	 * @param redirectAttributes
//	 * @return
//	 */
//	@RequestMapping(value = "import", method = RequestMethod.POST)
//	public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
//		if (Global.isDemoMode()) {
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:"+Global.getAdminPath()+"/sdcount/sdTwotype/?repage";
//		}
//		try {
//			StringBuilder failureMsg = new StringBuilder();
//			ImportExcel ei = new ImportExcel(file, 1, 0);
//			int successNum = 0;
//			List<SdTwoyearhavenuit> list = ei.getDataList(SdTwoyearhavenuit.class);
//			for (SdTwoyearhavenuit sdTwoyearhavenuit : list) {
//				try {
//					BeanValidators.validateWithException(validator, sdTwoyearhavenuit);// 服务端验证
//					sdTwoyearhavenuitService.save(sdTwoyearhavenuit);
//					successNum++;
//				} catch (ConstraintViolationException ex) {
//					failureMsg.append("<br/> :" + ",导入失败<br/>");
//					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//					for (String message : messageList) {
//						failureMsg.append(message + "; ");
//					}
//				} catch (Exception ex) {
//					failureMsg.append("<br/> :"  + ",导入失败：" + ex.getMessage());
//				}
//			}
//			addMessage(redirectAttributes, "已成功导入 " + successNum + " 条数据" + failureMsg);
//		} catch (Exception e) {
//			addMessage(redirectAttributes, "导入数据失败！失败信息：" + e.getMessage());
//		}
//		return "redirect:"+Global.getAdminPath()+"/sdcount/sdTwoyearhavenuit/?repage";
//	}
}