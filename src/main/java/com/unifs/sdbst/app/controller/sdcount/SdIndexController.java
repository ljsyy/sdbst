/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.controller.sdcount;


import com.unifs.sdbst.app.bean.sdcount.SdIndex;
import com.unifs.sdbst.app.service.sdcount.SdIndexService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * sController
 * @author chengpneng
 * @version 2017-11-23
 */
@Controller
@RequestMapping(value = "/sdcount/sdIndex")
public class SdIndexController{

	@Autowired
	private SdIndexService sdIndexService;
	
	@ModelAttribute
	public SdIndex get(@RequestParam(required=false) String id) {
		SdIndex entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sdIndexService.get(id);
		}
		if (entity == null){
			entity = new SdIndex();
		}
		return entity;
	}
	
/*
	@RequiresPermissions("sdcount:sdIndex:view")
	@RequestMapping(value = {"list", ""})
	public String list(SdIndex sdIndex, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SdIndex> page = sdIndexService.findPage(new Page<SdIndex>(request, response), sdIndex); 
		model.addAttribute("page", page);
		return "modules/sdcount/sdIndexList";
	}
*/

	@RequiresPermissions("sdcount:sdIndex:view")
	@RequestMapping(value = "form")
	public String form(SdIndex sdIndex, Model model) {
		model.addAttribute("sdIndex", sdIndex);
		return "modules/sdcount/sdIndexForm";
	}

/*	@RequiresPermissions("sdcount:sdIndex:edit")
	@RequestMapping(value = "save")
	public String save(SdIndex sdIndex, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, sdIndex)){
			return form(sdIndex, model);
		}
		sdIndexService.save(sdIndex);
		addMessage(redirectAttributes, "保存1成功");
		return "redirect:"+Global.getAdminPath()+"/sdcount/sdIndex/?repage";
	}*/
	
/*	@RequiresPermissions("sdcount:sdIndex:edit")
	@RequestMapping(value = "delete")
	public String delete(SdIndex sdIndex, RedirectAttributes redirectAttributes) {
		sdIndexService.delete(sdIndex);
		addMessage(redirectAttributes, "删除1成功");
		return "redirect:"+Global.getAdminPath()+"/sdcount/sdIndex/?repage";
	}*/

}