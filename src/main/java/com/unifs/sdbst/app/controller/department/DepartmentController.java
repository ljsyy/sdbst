package com.unifs.sdbst.app.controller.department;


import com.alibaba.fastjson.JSON;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.department.DepartmentInfo;
import com.unifs.sdbst.app.bean.menus.Menu;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.department.DepartmentService;
import com.unifs.sdbst.app.utils.DepartmentUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(value = "DepartmentController", description = "部门信息接口")    //swagger2生成API文档注解
@Controller
@RequestMapping(value = "department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;
    /**
     * 根据名字获取部门信息，包括部门简介，部门职能，部门科室，领导分工，联系方式，地址
     * @param dep_name
     * @return DepartmentInfo
     */
    @ApiOperation(value="获取部门信息")
    @RequestMapping(value = "/getDepartmentInfoByDepName")
    @ResponseBody
    public String getDepartmentInfoByDepName(String dep_name,String id){
        Map<String,Object> resultMap = new HashMap<>();
        DepartmentInfo departmentInfo = null;
        Resp entity = null;
        try{
            departmentInfo = departmentService.getDepartmentInfoByDepName(dep_name);
            //联系电话和其它联系方式，转成json返回
            String contact = departmentInfo.getContact();
            String otherContact = departmentInfo.getOtherContact();
            List<Map<String,String>> contactList = DepartmentUtil.stringToListMap(contact);
            List<Map<String,String>> otherContactList = DepartmentUtil.stringToListMap(otherContact);

            resultMap.put("address",departmentInfo.getAddress());
            resultMap.put("departmentType",departmentInfo.getDepartmentType());
            resultMap.put("contact",contactList);
            resultMap.put("otherContact",otherContactList);
//            resultMap.put("departmentIntroduction",departmentInfo.getDepartmentIntroduction()!=null?departmentInfo.getDepartmentIntroduction().trim():"");
            resultMap.put("departmentLaborDivision",departmentInfo.getDepartmentLaborDivision());
            resultMap.put("departmentMechanism",departmentInfo.getDepartmentMechanism()!=null?departmentInfo.getDepartmentMechanism().trim():"");
            resultMap.put("departmentName",departmentInfo.getDepartmentName());
            resultMap.put("departmentOffice",departmentInfo.getDepartmentOffice());
            resultMap.put("id",departmentInfo.getId());

            List<Menu> list = departmentService.findChildsMenu(id);
            resultMap.put("childsMenu",list);
            entity = new Resp(RespCode.SUCCESS, resultMap);
        }catch (Exception e){
            e.printStackTrace();
            entity = new Resp(RespCode.DEFAULT, resultMap);
        }
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }

    @ApiOperation(value="获取部门类型")
    @RequestMapping(value="/getDepartmentType")
    @ResponseBody
    public String getDepartmentType(){
        Resp entity = null;
        List<String> resultMap = null;
        try{
            resultMap = departmentService.getDepartmentType();
            entity = new Resp(RespCode.SUCCESS, resultMap);
        }catch (Exception e){
            e.printStackTrace();
            entity = new Resp(RespCode.DEFAULT, resultMap);
        }
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }

    /**
     * @param department_type
     * @return
     */
    @ApiOperation(value="根据部门类型获取部门数据")
    @RequestMapping(value="/getDepartmentByType")
    @ResponseBody
    public String getDepartmentByType(String department_type){
        Resp entity = null;
        List<String> resultMap = null;
        try{
            resultMap = departmentService.getDepartmentByType(department_type);
            entity = new Resp(RespCode.SUCCESS, resultMap);
        }catch (Exception e){
            e.printStackTrace();
            entity = new Resp(RespCode.DEFAULT, resultMap);
        }
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }

    @ApiOperation(value="根据部门名获取所有联系方式")
    @RequestMapping(value="/getDepartmentContactByDepName")
    @ResponseBody
    public String getDepartmentContactByDepName(String dep_name){
        Resp entity = null;
        Map<String,String> map = null;
        List<Map<String,String>> resultMap = new ArrayList<>();
        try{
            map = departmentService.getDepartmentContactByDepName(dep_name);
            if(map != null){
                String contact = map.get("contact");
                String otherContact = map.get("otherContact");
                List<Map<String,String>> contactMap = DepartmentUtil.stringToListMap(contact);
                List<Map<String,String>> otherContactMap = DepartmentUtil.stringToListMap(otherContact);
                resultMap.addAll(contactMap);
                resultMap.addAll(otherContactMap);
            }
            entity = new Resp(RespCode.SUCCESS, resultMap);
        }catch (Exception e){
            e.printStackTrace();
            entity = new Resp(RespCode.DEFAULT, resultMap);
        }
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }

    /**
     * 跳转政府部门电话页
     * @param request
     * @param model
     * @return
     */
    @ApiOperation(value="跳转政府部门电话页")
    @RequestMapping(value = "/departmentContact", method = {RequestMethod.GET})
    public String departmentContact(HttpServletRequest request, Model model) {
        return "/app/department/departmentContact";
    }

    @RequestMapping(value = "/departmentContact2", method = {RequestMethod.GET})
    public String departmentContact2(HttpServletRequest request, Model model) {
        return "/app/department/departmentContact2";
    }

    @ApiOperation(value="跳转政府部门电话详情页")
    @RequestMapping(value = "/departmentDetailContact", method = {RequestMethod.GET})
    public String departmentDetailContact(HttpServletRequest request, Model model) {
        String dep_name = request.getParameter("dep_name");
        model.addAttribute("dep_name",dep_name);
        return "/app/department/departmentDetailContact";
    }
}
