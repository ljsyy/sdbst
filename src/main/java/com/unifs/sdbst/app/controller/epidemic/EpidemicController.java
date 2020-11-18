package com.unifs.sdbst.app.controller.epidemic;

import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.cms.Article;
import com.unifs.sdbst.app.bean.data.EchartsEpidemic;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.cms.ArticleService;
import com.unifs.sdbst.app.service.data.EpidemicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "epidemic")
public class EpidemicController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private EpidemicService epidemicService;

    @ControlLog(operateType = "/page", context = "企业集结号")
    @RequestMapping(value = "/page", method = {RequestMethod.GET})
    public String epidemicPage() {
        return "/app/epidemic/epidemic";
    }

    @GetMapping(value = "/list")
    public String list() {
        return "/app/epidemic/list";
    }

    /**
     * 功能描述 : 获取疫情相关新闻信息
     *
     * @param * @param null
     * @return
     * @author 张恭雨
     * @date 2020/2/4
     */
    @GetMapping(value = "/articleList")
    @ResponseBody
    public Resp articleList(String categoryId) {
        List<Article> articleList = articleService.findByCategoryId(categoryId);
        Resp resp;
        if (articleList.isEmpty()) {
            resp = new Resp(RespCode.DEFAULT);
        } else {
            resp = new Resp(RespCode.SUCCESS);
            resp.setData(articleList);
        }
        return resp;
    }

    /**
     * 功能描述 :根据类型获取Echarts疫情数据
     *
     * @param * @param null
     * @return
     * @author 张恭雨
     * @date 2020/2/4
     */
    @GetMapping(value = "/echartsData")
    @ResponseBody
    public Resp echartsData(int type) {
        EchartsEpidemic echartsEpidemic = epidemicService.echartsData(type);
        Resp resp;
        if (echartsEpidemic == null) {
            resp = new Resp(RespCode.DEFAULT);
        } else {
            resp = new Resp(RespCode.SUCCESS);
            resp.setData(echartsEpidemic);
        }
        return resp;
    }
    /**
     * 功能描述 :疫情查询助手
     * @author 张恭雨
     * @date 2020/2/17
     * @param  * @param
     * @return java.lang.String
     */
    @RequestMapping(value = "/strokeInquire",method = RequestMethod.GET)
    public String strokeInquire(){
        return "/app/epidemic/strokeInquire";
    }
}
