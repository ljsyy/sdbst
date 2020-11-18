package com.unifs.sdbst.app.service.menus;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.unifs.sdbst.app.bean.appinfo.AppVersion;
import com.unifs.sdbst.app.bean.cms.Article;
import com.unifs.sdbst.app.bean.cms.Category;
import com.unifs.sdbst.app.bean.menus.Menu;
import com.unifs.sdbst.app.bean.menus.SysUserMenu;
import com.unifs.sdbst.app.bean.menus.Tag;
import com.unifs.sdbst.app.bean.menus.view.HomeMenuView;
import com.unifs.sdbst.app.bean.menus.vo.HomeMenuVo;
import com.unifs.sdbst.app.dao.primary.appinfo.AppVersionMapper;
import com.unifs.sdbst.app.dao.primary.cms.ArticleMapper;
import com.unifs.sdbst.app.dao.primary.cms.CategoryMapper;
import com.unifs.sdbst.app.dao.primary.menus.MenuMapper;
import com.unifs.sdbst.app.dao.primary.menus.SysUserMenuMapper;
import com.unifs.sdbst.app.dao.primary.menus.TagMapper;
import com.unifs.sdbst.app.utils.DateUtils;
import com.unifs.sdbst.app.utils.IdGen;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppMenuService {
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;
    @Autowired
    private AppVersionMapper appVersionMapper;
    @Autowired
    private TagMapper tagMapper;
    @Autowired
    private RedisUtil redisUtil;

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    @Cacheable(value = "find1To3Menu")
    public List<Menu> find1To3Menu(Menu menu) {
        return menuMapper.find1To3Menu(menu);
    }

    public List<Menu> testselect(Menu menu) {
        return menuMapper.testselect(menu);
    }

    public List<Menu> find1To4Menu(Menu menu) {
        return menuMapper.find1To4Menu(menu);
    }

    public List<Menu> findChildsMenu(Menu menu) {
        return menuMapper.findChildsMenu(menu);
    }

    public List<Menu> findMainMenu() {
        return menuMapper.findMainMenu(new Menu());
    }

    public Menu getMenu(String id) {
        return menuMapper.get(id);
    }

    //热门标签
    public List<Tag> findHotTags() {
        return tagMapper.findHotTags();
    }

    /**
     * 用户按关键词搜索菜单功能, 搜索前将搜索词插入表
     *
     * @param tag 关键词
     * @return
     */
    public List<Menu> findMenuByTag(String tag) {
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 0);

        //一天的开始时间yyyy:MM:dd00:00:00
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();

        //一天的结束时间yyyy:MM:dd23:59:59
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        Date dayEnd = calendar.getTime();

        Tag tagCur = tagMapper.findByDate(dayStart, dayEnd);
        List<Menu> list = menuMapper.findName(tag);
        if (tagCur == null || StringUtils.isBlank(tagCur.getId())) {
            tagCur = new Tag();
            tagCur.setId(IdGen.uuid());
            tagCur.setCreateDate(new Date());
            tagCur.setTag(tag);
            if (list.size() > 0) {
                tagMapper.insert(tagCur);
            }
        } else {
//			tagCur.setCreateDate(new Date());
//            tagCur.setTag(tagCur.getTag()+","+tag);
            if (list.size() > 0) {
                tagMapper.update(tagCur);
            }
        }
        //热门搜索
        Tag tags = tagMapper.findName(tag);
        if (null == tags || StringUtils.isBlank(tags.getId())) {
            tags = new Tag();
            tags.setId(IdGen.uuid());
            tags.setCreateDate(new Date());
            tags.setTag(tag);
            tags.setTags(tag);
            tags.setNums(1);
            if (list.size() > 0) {
                tagMapper.insert(tags);
            }
        } else {
            tags.setNums(tags.getNums() + 1);
            if (list.size() > 0) {
                tagMapper.updateNums(tags);
            }
        }
        return menuMapper.findMenuByTag(tag);
    }

    /**
     * 根据祖ID获取栏目
     *
     * @param menu
     * @return
     */
    public List<Menu> findPhoneParentIdsLike(Menu menu) {
        return menuMapper.findPhoneParentIdsLike(menu);
    }

    public List<Menu> findPhoneParentIdLike(Menu menu) {
        return menuMapper.findPhoneParentIdLike(menu);
    }

    /**
     * 根据ID获取手机2级和3级菜单
     *
     * @return
     */
    public List<Menu> menuToUserApp(String id, String source) {
        List<Menu> menuList = Lists.newArrayList();
        Menu menu = new Menu();
        menu.setParentIds("0,1," + id + ",");
        if ("1".equals(source)) {//移动端
            menu.setMobileShow("1");
            menu.setWxShow(null);
        } else if ("2".equals(source)) {//微信端
            menu.setWxShow("1");
            menu.setMobileShow(null);
        } else {//默认移动端
            menu.setMobileShow("1");
            menu.setWxShow(null);
        }
        List<Menu> menu3l = findPhoneParentIdLike(menu);
        System.out.println(menu3l);
        System.out.println(222222);
        for (Menu menu3 : menu3l) {
            menu.setParentIds("0,1," + id + "," + menu3.getId() + ",");
            try {
                List<Menu> menu4l = findPhoneParentIdLike(menu);
                System.out.println(menu4l);
                System.out.println(3);
                if (menu4l == null || menu4l.size() == 0) {
                    menuList.add(menu3);
                } else {
                    for (Menu menu4 : menu4l) {
                        menuList.add(menu4);
							/*menu.setParentIds(menu4.getParentIds()+menu4.getId()+",");
							List<Menu> menu5l = findPhoneParentIdLike(menu);
							System.out.println(menu5l);
							System.out.println(4);
							if(menu5l==null||menu5l.size()==0){
								menuList.add(menu4);
							}else{

								for (Menu menu5 : menu5l) {
								    menuList.add(menu5);
									menu.setParentIds(menu5.getParentIds()+menu5.getId()+",");
									List<Menu> menu6l = systemService.findPhoneParentIdLike(menu);
									if(menu6l==null||menu6l.size()==0){
										menuList.add(menu5);
									}else{
										for (Menu menu6 : menu6l) {
											menuList.add(menu6);
										}
									}
								}
							}*/
                    }

                }
            } catch (Exception e) {
                menuList.add(menu3);
            }
        }

        return menuList;
    }

    public List<Menu> getOneMenu() {
        return menuMapper.getOneMenu();
    }

    //获取指定父节点下的所有子节点
    @Cacheable(value = "epidemicColumnMenu")
    public Menu epidemicColumnMenu(String parentId) {
        return menuMapper.selectMenuTree(parentId);
    }

    //获取首页banner菜单
    @Cacheable(value = "bannerMenu")
    public List<Menu> bannerMenu(String bannerId) {
        return menuMapper.selectByParentId(bannerId);
    }

    @Cacheable(value = "homeMenuData",key = "#vo.toString()")
    public HomeMenuView getHomeMenuData(HomeMenuVo vo) {
        HomeMenuView view = new HomeMenuView();

        //首页轮播
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        LinkedList<Map<String, String>> list = new LinkedList<Map<String, String>>();
        map.put("success", true);
        map.put("msg", "操作成功！");
        if (!StringUtils.isEmpty(vo.getInfoType())) {//设置文章类型，如果类型为空，则查询所有类型数据
            Category category = new Category();
            category.setId(vo.getInfoType());
            List<Category> categoryList = categoryMapper.findByParentIdsLike(category);
            if (categoryList == null || categoryList.size() == 0) {//如果是最底层栏目，则查询当前栏目数据
                if (vo.getArticle().getCategory() == null) {
                    vo.getArticle().setCategory(new Category());
                    vo.getArticle().getCategory().setId(vo.getInfoType());
                } else {
                    vo.getArticle().getCategory().setId(vo.getInfoType());
                }
            } else {//如果有下级栏目，查询所有下级栏目数据
                vo.getArticle().setCategoryList(categoryList);
            }
        } else {
            vo.getArticle().setCategory(new Category());
        }

        PageHelper.startPage(vo.getPageIndex(), vo.getPageSize());
        List<Article> findArticleList = articleMapper.findList(vo.getArticle());

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
        view.setArticleMap(map);

        //热门菜单
        SysUserMenu userMenu = sysUserMenuMapper.get("1");
        List<Menu> menuList = new ArrayList<>();
        if (userMenu != null) {
            String[] ids = userMenu.getMenuIds().split(",");
            for (String str : ids) {
                //读取菜单详情
                Menu menu = menuMapper.get(str);
                ;
                if (menu != null) {
                    menuList.add(menu);
                }
            }
        }
        view.setHotMenu(menuList);

        //首页banner
        view.setBanners(menuMapper.selectByParentId("909d7ae134d54863af3ed43b377def0f"));

        //防疫专栏数据
        view.setEpidemicColumn(menuMapper.selectMenuTree("1d9940770d004a88a892e814a09ae650"));

        /*app注册登录模式获取*/
        //获取app模式信息
        String appMode = redisUtil.get("appMode");
        if (appMode == null || appMode.equals("")) {
            //首次访问,初始化
            appMode = "normal";
            redisUtil.set("appMode", appMode);
        }
        view.setAppMode(appMode);

        /*app版本号*/
        AppVersion appVersion = new AppVersion();
        appVersion.setStartUp(vo.getStartUp());
        appVersion.setType(vo.getType());
        List<AppVersion> version = appVersionMapper.findList(appVersion);
        view.setAppVersions(version);
        return view;
    }
}
