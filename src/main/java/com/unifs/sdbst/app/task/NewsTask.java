package com.unifs.sdbst.app.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.bean.cms.Article;
import com.unifs.sdbst.app.bean.cms.Category;
import com.unifs.sdbst.app.service.cms.ArticleService;
import com.unifs.sdbst.app.service.cms.CategoryService;
import com.unifs.sdbst.app.utils.HttpUtil;
import com.unifs.sdbst.app.utils.PropertiesUtil;
import com.unifs.sdbst.app.utils.ZFWDBHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @version V1.0
 * @title: NewsTask
 * @projectName sdbst
 * @description: 新闻数据抓取定时任务
 * @author： 张恭雨
 * @date 2019/11/12 9:19
 */
@Configuration
@EnableScheduling   //定时任务
public class NewsTask {
    private static Map<String, String> sqlList = new HashMap<>();
    private static final String fileName = "news-sql.properties";
    private static final Logger log = LoggerFactory.getLogger(NewsTask.class);
    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ZFWDBHelper zfwdbHelper;
    @Value("${news.zscd.url}")
    private String url;

    //静态代码库,初始化sql集
    static {
        Set<String> names = PropertiesUtil.getNames(fileName);
        for (String name : names) {
            String value = PropertiesUtil.getKey(fileName, name);
            sqlList.put(name, value);
        }
    }

    //数据抓取定时任务
//    @Scheduled(cron = "0 0 3 * * 2")  //定时时间，每周二凌晨三点执行
//    @Scheduled(fixedRate = 60 * 60 * 5)
    private void dataCapture() {
        //获取当前系统日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        //遍历sql集合
        for (Map.Entry<String, String> entry : sqlList.entrySet()) {
            String key = entry.getKey();
            String type = "";
            if (key.contains("zwyw")) {
                type = "政务要闻";
            } else if (key.contains("sdfb")) {
                type = "顺德发布";
            } else if (key.contains("sqdt")) {
                type = "社区动态";
            } else if (key.contains("tzgg")) {
                type = "通知公告";
            }
            //获分类ID
            Category category = categoryService.getCategory(type);
            if (category == null) {
                log.info(type + "分类不存在！");
                continue;
            }
            //设置sql参数
            String sql = entry.getValue();
            sql = sql.replace("@A", date);
            try {
                List<Article> articles = dbSelect(sql, category.getId());
                //遍历集合，保存抓取数据
                for (Article articleTemp : articles) {
                    //判断该文章不存在，则插入数据库
                    if (articleService.findById(articleTemp.getId()) == null) {
                        articleService.save(articleTemp);
                    }
                }
            } catch (Exception e) {
                //出现异常，记录错误日志
                log.error(e.getMessage());
            }
        }
    }

    /**
     * 功能描述 : url类型新闻数据采集
     *
     * @param * @param
     * @return void
     * @author 张恭雨
     * @date 2019/12/31
     */
//    @Scheduled(cron = "0 10 3 * * 2")  //定时时间，每周二凌晨三点十分执行
//        @Scheduled(fixedRate = 60 * 60 * 5)
    private void urlDataCapture() {
        //对需要请求的url发起请求
        String reuslt = HttpUtil.sendGet(url, "", "utf-8");
        //对接收到的字符串做处理，转化为json格式的字符串数组
        reuslt = reuslt.replace("pushInfoJsonpCallBack(", "");
        reuslt = reuslt.replace(")", "");
        log.info(reuslt);
        //将json字符数组转化为json集合
        JSONArray array = JSON.parseArray(reuslt);

        //日期格式类
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //获分类ID
            Category category = categoryService.getCategory("政声传递");
            if (category == null) {
                log.info("政声传递分类不存在！");
                return;
            }
            //初始化文章对象
            Article article = new Article();
            article.setLinkType("1");
            article.setType("1");
            article.setWeight(999);
            article.setPosid(",null,");
            article.setCategoryId(category.getId());
            article.setDelFlag("2");
            //遍历集合，获取对象值，保存到库表
            for (Object obj : array) {
                JSONObject jsonObject = (JSONObject) obj;
                article.setTitle(jsonObject.getString("title"));
                article.setLink(jsonObject.getString("link"));
                article.setCreateDate(sdf.parse(jsonObject.getString("pubDate")));
                article.setId(UUID.randomUUID().toString().replaceAll("-", ""));
                if (articleService.findByTitle(article.getTitle()) == null) {
                    articleService.save(article);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            log.error("日期转换错误：" + e.getMessage());
        }

    }

    private List<Article> dbSelect(String sql, String categoryId) throws Exception {
        //日期格式类
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //创建文章集合对象
        List<Article> articles = new ArrayList<>();
        //声明文章对象
        Article article;
        //获取数据库连接
        Connection conn = zfwdbHelper.getConn();
        log.info("news Query=" + sql);
        //sql预处理
        PreparedStatement ps = conn.prepareStatement(sql);
        //执行sql获取查询结果
        ResultSet rst = ps.executeQuery();
        //查询结果处理
        while (rst.next()) {
            article = new Article();
            article.setLink(rst.getString("station_sh") + rst.getString("href"));
            article.setTitle(rst.getString("bt"));
            article.setLinkType("1");
            article.setType("1");
            article.setWeight(999);
            article.setPosid(",null,");
            article.setCreateDate(sdf.parse(rst.getString("cjrq")));
            article.setId(rst.getString("id"));
            article.setCategoryId(categoryId);
            article.setDelFlag("2");
            articles.add(article);
        }
        return articles;
    }

}
