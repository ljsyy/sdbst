/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.cms;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.sun.javafx.util.Utils;
import com.unifs.sdbst.app.bean.cms.Article;
import com.unifs.sdbst.app.bean.cms.ArticleData;
import com.unifs.sdbst.app.bean.cms.Bulletin;
import com.unifs.sdbst.app.bean.cms.Category;
import com.unifs.sdbst.app.dao.primary.cms.ArticleDataMapper;
import com.unifs.sdbst.app.dao.primary.cms.ArticleMapper;
import com.unifs.sdbst.app.dao.primary.cms.BulletinMapper;
import com.unifs.sdbst.app.dao.primary.cms.CategoryMapper;
import com.unifs.sdbst.app.utils.DateUtils;
import com.unifs.sdbst.app.utils.IdGen;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * 文章Service
 *
 * @author ThinkGem
 * @version 2013-05-15
 */
@Service
@Transactional(readOnly = true)
public class ArticleService {

    @Autowired
    private ArticleDataMapper articleDataMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private BulletinMapper bulletinMapper;


    public Article get(String id) {
        return articleMapper.get(id);
    }
//	@Transactional(readOnly = false)
//	public Page<Article> findPage(Page<Article> page, Article article, boolean isDataScopeFilter) {
//		// 更新过期的权重，间隔为“6”个小时
//		Date updateExpiredWeightDate =  (Date)CacheUtils.get("updateExpiredWeightDateByArticle");
//		if (updateExpiredWeightDate == null || (updateExpiredWeightDate != null
//				&& updateExpiredWeightDate.getTime() < new Date().getTime())){
//			dao.updateExpiredWeight(article);
//			CacheUtils.put("updateExpiredWeightDateByArticle", DateUtils.addHours(new Date(), 6));
//		}
//		return super.findPage(page, article);
//
//	}

    public ArticleData getData(String id) {
        return articleDataMapper.get(id);
    }

    public Article findById(String id) {
        return articleMapper.selectById(id);
    }

    public Article findByTitle(String title) {
        return articleMapper.selectByTitle(title);
    }

    /**
     * 查询列表数据
     *
     * @param entity
     * @return
     */
    public List<Article> findList(Article entity) {
        return articleMapper.findList(entity);
    }

    /**
     * 返回JSON格式Map
     *
     * @param article
     * @param infoType
     * @param pageIndex
     * @return
     */
    @Transactional(readOnly = false)
    @Cacheable(value = "articleMap",key = "#article.posid")
    public Map<String, Object> articleMap(Article article, String infoType, int pageIndex, int pageSize, HttpServletRequest request) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        LinkedList<Map<String, String>> list = new LinkedList<Map<String, String>>();

        map.put("success", true);
        map.put("msg", "操作成功！");
        if (!StringUtils.isEmpty(infoType)) {//设置文章类型，如果类型为空，则查询所有类型数据
            Category category = new Category();
            category.setId(infoType);
            List<Category> categoryList = categoryMapper.findByParentIdsLike(category);
            if (categoryList == null || categoryList.size() == 0) {//如果是最底层栏目，则查询当前栏目数据
                if (article.getCategory() == null) {
                    article.setCategory(new Category());
                    article.getCategory().setId(infoType);
                } else {
                    article.getCategory().setId(infoType);
                }
            } else {//如果有下级栏目，查询所有下级栏目数据
                article.setCategoryList(categoryList);
            }
        } else {
            article.setCategory(new Category());
        }
//		Page<Article> page = new Page<Article>(pageIndex,pageSize,999);
////		Page<Article> page = new Page<Article>(0,3,4);
//		article.setPage(page);
        PageHelper.startPage(pageIndex, pageSize);
        List<Article> findArticleList = findList(article);
        PageInfo<Article> pageInfo = new PageInfo<Article>(findArticleList);

//		page.setList(findList);
        for (Article articlel : findArticleList) { //封装json数据
            Map<String, String> art = new LinkedHashMap<String, String>();
            art.put("id", articlel.getId());
            art.put("title", articlel.getTitle());
            art.put("description", articlel.getDescription());
            art.put("linkType", articlel.getLinkType());
            art.put("ios", articlel.getIos());
            art.put("android", articlel.getAndroid());
            art.put("wx", articlel.getWx());
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

//	@Transactional(readOnly = false)
//	public void save(Article article) {
//		if (article.getArticleData().getContent()!=null){
//			article.getArticleData().setContent(StringEscapeUtils.unescapeHtml4(
//					article.getArticleData().getContent()));
//		}
//		// 如果没有审核权限，则将当前内容改为待审核状态
//		if (!UserUtils.getSubject().isPermitted("cms:article:audit")){
//			article.setDelFlag(Article.DEL_FLAG_AUDIT);
//		}
//		// 如果栏目不需要审核，则将该内容设为发布状态
////		if (article.getCategory()!=null&&StringUtils.isNotBlank(article.getCategory().getId())){
////			Category category = categoryDao.get(article.getCategory().getId());
////			if (!Global.YES.equals(category.getIsAudit())){
////				article.setDelFlag(Article.DEL_FLAG_NORMAL);
////			}
////		}
//		article.setUpdateBy(UserUtils.getUser());
//		//article.setUpdateDate(new Date());
//		article.setCreateDate(new Date());	//qxt 修改于 2017年9月15日
//        if (StringUtils.isNotBlank(article.getViewConfig())){
//            article.setViewConfig(StringEscapeUtils.unescapeHtml4(article.getViewConfig()));
//        }
//
//        ArticleData articleData = new ArticleData();;
//		if (StringUtils.isBlank(article.getId())){
//			/*article.preInsert();
//			articleData = article.getArticleData();
//			articleData.setId(article.getId());
//			dao.insert(article);
//			articleDataDao.insert(articleData);  qxt 修改于 2017年9月15日*/
//
//			Date updateDate=article.getUpdateDate();
//			article.preInsert();
//			article.setUpdateDate(updateDate);
//			articleData = article.getArticleData();
//			articleData.setId(article.getId());
//			dao.insert(article);
//			articleDataDao.insert(articleData);
//		}else{
//			/*			article.preUpdate(); qxt 修改 2017年9月15日
//			articleData = article.getArticleData();
//			articleData.setId(article.getId());
//			dao.update(article);
//			articleDataDao.update(article.getArticleData());
//			 */
//
//			Date updateDate=article.getUpdateDate();
//			article.preUpdate();
//			article.setUpdateDate(updateDate);
//			articleData = article.getArticleData();
//			articleData.setId(article.getId());
//			dao.update(article);
//			articleDataDao.update(article.getArticleData());
//		}
//	}
//
//	@Transactional(readOnly = false)
//	public void delete(Article article, Boolean isRe) {
////		dao.updateDelFlag(id, isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
//		// 使用下面方法，以便更新索引。
//		//Article article = dao.get(id);
//		//article.setDelFlag(isRe!=null&&isRe?Article.DEL_FLAG_NORMAL:Article.DEL_FLAG_DELETE);
//		//dao.insert(article);
//		super.delete(article);
//	}


    /**
     * 通过编号获取内容标题
     *
     * @return new Object[]{栏目Id,文章Id,文章标题}
     */
    public List<Object[]> findByIds(String ids) {
        if (ids == null) {
            return new ArrayList<Object[]>();
        }
        List<Object[]> list = Lists.newArrayList();
        String[] idss = StringUtils.split(ids, ",");
        Utils.split(ids, ",");
        Article e = null;
        for (int i = 0; (idss.length - i) > 0; i++) {
            e = articleMapper.get(idss[i]);
            list.add(new Object[]{e.getCategory().getId(), e.getId(), abbr(e.getTitle(), 50)});
        }
        return list;
    }

    /**
     * 点击数加一
     */
    @Transactional(readOnly = false)
    public void updateHitsAddOne(String id) {
        articleMapper.updateHitsAddOne(id);
    }

    /**
     * 更新索引
     */
    public void createIndex() {
        //dao.createIndex();
    }

    /**
     * 全文检索
     */
    //FIXME 暂不提供检索功能
////	public Page<Article> search(Page<Article> page, String q, String categoryId, String beginDate, String endDate){

//		// 设置查询条件
////		BooleanQuery query = dao.getFullTextQuery(q, "title","keywords","description","articleData.content");
////
////		// 设置过滤条件
////		List<BooleanClause> bcList = Lists.newArrayList();
////
////		bcList.add(new BooleanClause(new TermQuery(new Term(Article.FIELD_DEL_FLAG, Article.DEL_FLAG_NORMAL)), Occur.MUST));
////		if (StringUtils.isNotBlank(categoryId)){
////			bcList.add(new BooleanClause(new TermQuery(new Term("category.ids", categoryId)), Occur.MUST));
////		}
////
////		if (StringUtils.isNotBlank(beginDate) && StringUtils.isNotBlank(endDate)) {
////			bcList.add(new BooleanClause(new TermRangeQuery("updateDate", beginDate.replaceAll("-", ""),
////					endDate.replaceAll("-", ""), true, true), Occur.MUST));
////		}
//
//		//BooleanQuery queryFilter = dao.getFullTextQuery((BooleanClause[])bcList.toArray(new BooleanClause[bcList.size()]));
//
////		System.out.println(queryFilter);
//
//		// 设置排序（默认相识度排序）
//		//FIXME 暂时不提供lucene检索
//		//Sort sort = null;//new Sort(new SortField("updateDate", SortField.DOC, true));
//		// 全文检索
//		//dao.search(page, query, queryFilter, sort);
//		// 关键字高亮
//		//dao.keywordsHighlight(query, page.getList(), 30, "title");
//		//dao.keywordsHighlight(query, page.getList(), 130, "description","articleData.content");
//
//		return page;
//	}
    /**
     * 获取栏目首页推荐数
     * @param article
     * @return
     */
//	public Article getCnt(Article article){
//		return dao.getCnt(article);
//	}

    /*
     * 修改点赞量
     * */
//	public int updateGoods(Article article) {
//		Article temp=articleDao.get(article.getId());
//
//		if(temp.getGoods()==0) {
//			//点赞量为空
//			temp.setGoods(1);
//		}else {
//			temp.setGoods(temp.getGoods()+article.getGoods());
//		}
//
//		return articleDao.updateGoods(temp);
//	}

    /**
     * 根据栏目查询它以及它下面栏目的文章
     */
    public List<Article> findListByParentCategory(Category category) {
        return articleMapper.findListByParentCategory(category);
    }

    /**
     * 根据栏目查询文章
     */
    public List<Article> findListByCategory(Category category) {
        return articleMapper.findListByCategory(category);
    }

    /**
     * 缩略字符串（不区分中英文字符）
     *
     * @param str    目标字符串
     * @param length 截取长度
     * @return
     */
    public static String abbr(String str, int length) {
        if (str == null) {
            return "";
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray()) {
                currentLength += String.valueOf(c).getBytes("GBK").length;
                if (currentLength <= length - 3) {
                    sb.append(c);
                } else {
                    sb.append("...");
                    break;
                }
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 替换掉HTML标签方法
     */
    public static String replaceHtml(String html) {
        if (isBlank(html)) {
            return "";
        }
        String regEx = "<.+?>";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(html);
        String s = m.replaceAll("");
        return s;
    }

    //新增记录
    public void save(Article article) {
        articleMapper.insert(article);
    }

    //获取公告集合
    public List<Bulletin> getBulletinList() {
        return bulletinMapper.selectAll();
    }

    //获取疫情信息新闻
    @Cacheable(value = "findByCategoryId", key = "'categoryId'+#categoryId")
    public List<Article> findByCategoryId(String categoryId){
        return articleMapper.findByCategoryId(categoryId);
    }

}
