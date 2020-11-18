/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.cms;


import com.unifs.sdbst.app.bean.cms.Article;
import com.unifs.sdbst.app.bean.cms.Category;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ArticleMapper {

    int insert(Article record);

    List<Article> selectAll();

    Article get(String id);

    Article selectById(String id);

    Article selectByTitle(String title);

    List<Article> findListByParentCategory(Category category);

    List<Article> findListByCategory(Category category);

    List<Article> findList(Article record);

    List<Article> findByCategoryId(String categoryId);

    int updateHitsAddOne(String id);

}
