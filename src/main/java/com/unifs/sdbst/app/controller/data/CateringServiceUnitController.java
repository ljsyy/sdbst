package com.unifs.sdbst.app.controller.data;

import com.unifs.sdbst.app.bean.data.CateringServiceUnitEntity;
import com.unifs.sdbst.app.service.data.CateringServiceUnitService;
import com.unifs.sdbst.app.utils.StringUtils;
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
 * @title: CateringServiceUnitController
 * @projectName sdbst
 * @description: TODO
 * @author： 张恭雨
 * @date 2019/11/5 16:39
 */
@Controller
@RequestMapping({"/cateringServiceUnit"})
public class CateringServiceUnitController {
    @Autowired
    private CateringServiceUnitService cateringServiceUnitService;


    @RequestMapping(value = {"/getCateringServiceUnitlist"})
    @ResponseBody
    public AjaxJson getCateringServiceUnitlist(HttpServletRequest request)
            throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        String pageIndexStr = request.getParameter("pageIndex");
        String pageSizeStr = request.getParameter("pageSize");
        int pageIndex = 1;
        if (!StringUtils.isNullOrEmpty(pageIndexStr)) {
            pageIndex = Integer.parseInt(pageIndexStr);
        }
        int pagesize = 10;
        if (!StringUtils.isNullOrEmpty(pageSizeStr)) {
            pagesize = Integer.parseInt(pageSizeStr);
        }
        int start = 1;
        int end = pagesize;
        if (pageIndex > 1) {
            pageIndex--;
            start = pageIndex * pagesize + 1;
            end = pageIndex * pagesize + pagesize;
        }
        String objzjname = request.getParameter("unitName");
        String objjyjname = request.getParameter("town");
        String gradename = request.getParameter("grade");

        List<CateringServiceUnitEntity> mlist = cateringServiceUnitService.getNewsList2(objzjname, objjyjname, gradename, start, end);
        AjaxJson result = new AjaxJson();
        List<HashMap<String, Object>> list = new ArrayList();
        for (CateringServiceUnitEntity item : mlist) {
            HashMap<String, Object> hashMap = new HashMap();
            hashMap.put("id", item.getId());
            hashMap.put("unitName", item.getUnitName());
            hashMap.put("town", item.getTown());
            hashMap.put("unitAddress", item.getUnitAddress());
            hashMap.put("detailedAddress", item.getDetailedAddress());
            hashMap.put("grade", item.getGrade());
            hashMap.put("category", item.getCategory());
            hashMap.put("lat", item.getLat());
            hashMap.put("lng", item.getLng());
            list.add(hashMap);
        }
        result.setMsg("操作成功！");
        result.setSuccess(true);
        result.setObj(list);

        return result;
    }
}
