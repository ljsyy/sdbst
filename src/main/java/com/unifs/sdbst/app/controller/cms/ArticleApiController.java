package com.unifs.sdbst.app.controller.cms;

import com.github.pagehelper.PageHelper;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.cms.Article;
import com.unifs.sdbst.app.bean.cms.Bulletin;
import com.unifs.sdbst.app.bean.cms.Category;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.cms.ArticleService;
import com.unifs.sdbst.app.utils.DateUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Controller
@RequestMapping(value = "/cms/articleApi")
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;

    @ModelAttribute
    public Article get(@RequestParam(required = false) String id) {
        System.out.println("id:" + id);
        if (StringUtils.isNotBlank(id)) {
            return articleService.get(id);
        } else {
            return new Article();
        }
    }

    /**
     * 获取文章列表
     *
     * @return
     */
    @ApiOperation(value = "首页轮播", notes = "首页轮播图接口,參數posid為1，pageIndex，pageSize")
    @ResponseBody
    @RequestMapping(value = "articleMap")
    public Map<String, Object> getbypageNo(Article article, String infoType, int pageIndex, Integer pageSize, HttpServletRequest request) {
        article.setDelFlag("2");
        if (pageSize == null) {
            pageSize = 15;
        }
        Map<String, Object> articleMap = articleService.articleMap(article, infoType, pageIndex, pageSize, request);
        return articleMap;
    }

    @ApiOperation(value = "新闻列表", notes = "根据新闻分类id获取新闻列表，參數id")
    @ResponseBody
    @RequestMapping(value = "articleMapTwo")
    public Map<String, Object> articleMapTwo(
            Category category, Integer pageIndex, Integer pageSize,
//			@ApiParam(value = "分类ID", required = true) @RequestParam Category category,
//			@ApiParam(value = "第几页", required = true) @RequestParam Integer pageIndex,
//			@ApiParam(value = "每页个数", required = false) @RequestParam Integer pageSize,
            HttpServletRequest request) {
        if (pageSize == null) {
            pageSize = 15;
        }
        System.out.println("1:" + pageIndex);
        System.out.println(pageSize);
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("success", true);
        map.put("msg", "操作成功！");
        LinkedList<Map<String, String>> list = new LinkedList<Map<String, String>>();

//		Page<Category> page = new Page<Category>(pageIndex, pageSize);
//		category.setPage(page);
        PageHelper.startPage(pageIndex, pageSize);
        List<Article> findList = articleService.findListByParentCategory(category);

        for (Article articlel : findList) { //封装json数据
            Map<String, String> art = new LinkedHashMap<String, String>();
            art.put("id", articlel.getId());
            art.put("title", articlel.getTitle());
            art.put("description", articlel.getDescription());
            art.put("linkType", articlel.getLinkType());
            art.put("ios", articlel.getIos());
            art.put("android", articlel.getAndroid());
            art.put("wx", articlel.getWx());
            art.put("type", articlel.getType());
            if ("3".equals(articlel.getLinkType())) {//访问本地文章
                art.put("link", "/app/artic/view-" + articlel.getCategory().getId() + "-" + articlel.getId() + ".html");
            } else {
                art.put("link", articlel.getLink());
            }
            art.put("image", articlel.getImage());

            if (null != articlel.getUpdateDate()) {
                art.put("publishTime", DateUtils.formatDateTime(articlel.getUpdateDate()));
            } else {
                art.put("publishTime", DateUtils.formatDateTime(new Date()));
            }

            list.add(art);
        }
        map.put("obj", list);
        return map;
    }

//	/**
//	 * 顺德美食获取文章列表
//	 * */
//	@ResponseBody
//	@RequestMapping(value="findList")
//	public List<Article> findListByCategory(Category category, HttpServletRequest request, HttpServletResponse response, Model model) {
//		List<Article> list = articleService.findListByCategory(category);
//		return list;
//	}

    //跳转文章列表页
    @RequestMapping(value = "/toList")
    public String toList(String title, Model model) {
        model.addAttribute("title", title);
        return "app/cms/newsList";
    }

    /**
     * 功能描述 :获取公告集合
     *
     * @param * @param
     * @return com.unifs.sdbst.backstage.base.ResEntity
     * @author 张恭雨
     * @date 2020/1/14
     */
    @RequestMapping(value = "/bulletinList")
    @ResponseBody
    public Resp bulletinList() {
        List<Bulletin> bulletins = articleService.getBulletinList();
        Resp resEntity = new Resp(RespCode.SUCCESS);
        resEntity.setData(bulletins);
        return resEntity;
    }
}


