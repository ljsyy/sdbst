/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.cms;

import com.unifs.sdbst.app.bean.cms.ArticleData;
import org.springframework.stereotype.Component;

/**
 * 文章DAO接口
 * @author ThinkGem
 * @version 2013-8-23
 */
@Component
public interface ArticleDataMapper{
    ArticleData get(String id);
	
}
