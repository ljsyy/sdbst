package com.unifs.sdbst.app.controller.data;

import com.unifs.sdbst.app.bean.data.Hospital;
import com.unifs.sdbst.app.bean.data.Pharmacy;
import com.unifs.sdbst.app.service.data.IPHospitalService;
import com.unifs.sdbst.app.service.data.IPPharmacyService;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("medical/")
public class MedicalController {

    @Autowired
    private IPPharmacyService pharmacyService;
    @Autowired
    private IPHospitalService hospitalService;

    @RequestMapping("getplist")
    @ResponseBody
    public AjaxJson getplist(HttpServletRequest request)
            throws UnsupportedEncodingException
    {
        request.setCharacterEncoding("UTF-8");

        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        int pagesize = Integer.parseInt(request.getParameter("pageSize"));
        int start = 1;
        int end = pagesize;
        if (pageIndex > 1)
        {
            pageIndex--;
            start = pageIndex * pagesize + 1;
            end = pageIndex * pagesize + pagesize;
        }
        String objzjname = request.getParameter("zjname");
        String objpname = request.getParameter("pname");
//
//        String wheresql = " ";
//        if (objzjname != null) {
//            if (!StringUtils.isNullOrEmpty(objzjname.toString().trim())) {
//                wheresql =
//                        wheresql + " and (e.zjname like '%" + objzjname.toString().trim() + "%')";
//            }
//        }
//        if ((objpname != null) &&
//                (!StringUtils.isNullOrEmpty(objpname.toString().trim()))) {
//            wheresql = wheresql + " and (e.pname like '%" + objpname.toString().trim() + "%')";
//        }
//        System.out.println(wheresql);
        List<Pharmacy> mlist = pharmacyService.getNewsList2(start, end, objzjname,objpname);
        AjaxJson result = new AjaxJson();
        List<HashMap<String, Object>> list = new ArrayList();
        for (Pharmacy item : mlist)
        {
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("id", item.getId());
            hashMap.put("zjname", item.getZjname());
            hashMap.put("pname", item.getPname());
            hashMap.put("address", item.getAddress());
            hashMap.put("mobile", item.getMobile());
            hashMap.put("remark", item.getRemark());
            hashMap.put("lng", item.getJd());
            hashMap.put("lat", item.getWd());
            list.add(hashMap);
        }
        result.setMsg("操作成功");
        result.setSuccess(true);
        result.setObj(list);

        return result;
    }

    @RequestMapping("gethlist")
    @ResponseBody
    public AjaxJson gethlist(HttpServletRequest request)
            throws UnsupportedEncodingException
    {
        request.setCharacterEncoding("UTF-8");

        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        int pagesize = Integer.parseInt(request.getParameter("pageSize"));
        int start = 1;
        int end = pagesize;
        if (pageIndex > 1)
        {
            pageIndex--;
            start = pageIndex * pagesize + 1;
            end = pageIndex * pagesize + pagesize;
        }
        String objzjname = request.getParameter("zjname");
        String objhname = request.getParameter("hname");

//        String wheresql = " ";
//        if (objzjname != null) {
//            if (!StringUtils.isNullOrEmpty(objzjname.toString().trim())) {
//                wheresql =
//                        wheresql + " and (e.zjname like '%" + objzjname.toString().trim() + "%')";
//            }
//        }
//        if ((objhname != null) &&
//                (!StringUtils.isNullOrEmpty(objhname.toString().trim()))) {
//            wheresql = wheresql + " and (e.hname like '%" + objhname.toString().trim() + "%')";
//        }
        List<Hospital> mlist = hospitalService.getNewsList2(start, end, objzjname,objhname);
        AjaxJson result = new AjaxJson();
        List<HashMap<String, Object>> list = new ArrayList();
        for (Hospital item : mlist)
        {
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("id", item.getId());
            hashMap.put("zjname", item.getZjname());
            hashMap.put("hname", item.getHname());
            hashMap.put("address", item.getAddress());
            hashMap.put("mobile", item.getMobile());
            hashMap.put("remark", item.getRemark());
            hashMap.put("lng", item.getJd());
            hashMap.put("lat", item.getWd());
            list.add(hashMap);
        }
        result.setMsg("操作成功");
        result.setSuccess(true);
        result.setObj(list);

        return result;
    }
}
