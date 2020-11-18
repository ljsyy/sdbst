package com.unifs.sdbst.app.controller.cms;


import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.cms.Category;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.cms.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cms/categoryApi")
public class CategoryApiController{
	
	@Autowired
	private CategoryService categoryService;
	private Resp resp;


	
	/**
	 * 获取首页栏目列表
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="homeColumn")
	public Resp homeColumn(Category category){
		
		category.setInMenu("1");
		List<Map<String, Object>> homeColumn = categoryService.homeColumn(category);
		category.setDelFlag("0");
		
		resp=new Resp(RespCode.SUCCESS);
		resp.setData(homeColumn);
		return resp;
	}
	
	/**
	 * 获取所有栏目列表
	 * */
	@ResponseBody
	@RequestMapping(value="getList")
	public Resp getList(){
		Category category = new Category();
		category.setDelFlag("0");
		
		List<Category> categorys= categoryService.getList(category);

		resp=new Resp(RespCode.SUCCESS);
		resp.setData(categorys);
		return resp;
	}
}
