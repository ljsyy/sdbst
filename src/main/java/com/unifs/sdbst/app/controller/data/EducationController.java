package com.unifs.sdbst.app.controller.data;

import com.unifs.sdbst.app.bean.data.SchoolEntity;
import com.unifs.sdbst.app.service.data.EducationService;
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

/**
 * @version V1.0
 * @title: EducationController
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/2 9:41
 */
@Controller
@RequestMapping({"/education"})
public class EducationController {
    @Autowired
    private EducationService educationService;

    @RequestMapping(value = {"/getList"})
    @ResponseBody
    public AjaxJson getList(HttpServletRequest request)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");

        int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
        int pagesize = Integer.parseInt(request.getParameter("pageSize"));
        int start = 1;
        int end = pagesize;
        if (pageIndex > 1) {
            pageIndex--;
            start = pageIndex * pagesize + 1;
            end = pageIndex * pagesize + pagesize;
        }
        String objzjname = request.getParameter("zjname");
        String objschool = request.getParameter("school");

        List<SchoolEntity> slist = educationService.getNewsList2(start, end, objzjname,objschool);
        AjaxJson result = new AjaxJson();
        List<HashMap<String, Object>> list = new ArrayList();
        for (SchoolEntity item : slist) {
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("id", item.getId());
            hashMap.put("zjname", item.getZjname());
            hashMap.put("school", item.getSchool());
            hashMap.put("levels", item.getLevels());
            hashMap.put("address", item.getAddress());
            hashMap.put("mobile", item.getMobile());
            hashMap.put("remark", item.getRemark());
            hashMap.put("lng", item.getJd());
            hashMap.put("lat", item.getWd());
            list.add(hashMap);
        }
        result.setMsg("操作成功！");
        result.setSuccess(true);
        result.setObj(list);

        return result;
    }
}
