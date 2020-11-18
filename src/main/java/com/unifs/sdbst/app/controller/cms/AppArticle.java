package com.unifs.sdbst.app.controller.cms;


import com.unifs.sdbst.app.bean.cms.Article;
import com.unifs.sdbst.app.service.cms.ArticleService;
import com.unifs.sdbst.app.service.cms.CategoryService;
import com.unifs.sdbst.app.utils.CmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "app/artic")
public class AppArticle{
	@Autowired
	private ArticleService articleService;

	/**
	 * 显示内容
	 */
//	@RequestMapping(value = "view-{categoryId}-{contentId}${urlSuffix}")
//	public String articleview(HttpServletRequest request, @PathVariable String categoryId, @PathVariable String contentId, Model model) {
//		Article article = articleService.get(contentId);
//		if(article!=null){
//			// 文章阅读次数+1
//			articleService.updateHitsAddOne(contentId);
//			article.setArticleData(articleDataService.get(article.getId()));
//			model.addAttribute("article", article);
//			CmsUtils.addViewConfigAttribute(model, article.getCategory());
//			CmsUtils.addViewConfigAttribute(model, article.getViewConfig());
////			return "modules/cms/front/themes/"+category.getSite().getTheme()+"/"+getTpl(article);
//			return "/modules/cms/front/themes/basic/ArticleView";
//		}
//		return "error/404";
//	}
//

	@RequestMapping(value = "/cityView")
	public String cityView(String contentId, Model model) {
		Article article = articleService.get(contentId);
		if(article!=null){
			// 文章阅读次数+1
			articleService.updateHitsAddOne(contentId);
			article.setArticleData(articleService.getData(article.getId()));
			model.addAttribute("article", article);
			return "/app/cms/CityArticle";
		}
		return "error/404";
	}

	/*转自城市网*/
	/*@RequestMapping(value = "cityView-{categoryId}-{contentId}")
	public String cityView(@PathVariable String categoryId, @PathVariable String contentId, Model model) {
		Article article = articleService.get(contentId);
		if(article!=null){
			// 文章阅读次数+1
			articleService.updateHitsAddOne(contentId);
			article.setArticleData(articleService.getData(article.getId()));
			model.addAttribute("article", article);
			CmsUtils.addViewConfigAttribute(model, article.getCategory());
			CmsUtils.addViewConfigAttribute(model, article.getViewConfig());
			return "/app/cms/CityArticle";
		}
		return "error/404";
	}*/

}
