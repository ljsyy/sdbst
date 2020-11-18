package com.unifs.sdbst.app.controller.menus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.unifs.sdbst.app.annotation.ControlLog;
import com.unifs.sdbst.app.base.LogEntity;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.appinfo.AppVersion;
import com.unifs.sdbst.app.bean.menus.Dict;
import com.unifs.sdbst.app.bean.menus.Menu;
import com.unifs.sdbst.app.bean.menus.SysUserMenu;
import com.unifs.sdbst.app.bean.menus.SysWorkMenu;
import com.unifs.sdbst.app.bean.menus.view.HomeMenuView;
import com.unifs.sdbst.app.bean.menus.vo.HomeMenuVo;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.service.LogService;
import com.unifs.sdbst.app.service.appinfo.AppinfoService;
import com.unifs.sdbst.app.service.cms.ArticleService;
import com.unifs.sdbst.app.service.menus.AppMenuService;
import com.unifs.sdbst.app.service.menus.DictService;
import com.unifs.sdbst.app.service.menus.SysUserMenuService;
import com.unifs.sdbst.app.service.menus.SysWordMenuService;
import com.unifs.sdbst.app.utils.*;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.scripts.JS;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping(value = "app/menu")
public class AppMenuController {
    @Autowired
    private AppMenuService appMenuService;

    @Autowired
    private SysUserMenuService sysUserMenuService;

    @Autowired
    private SysWordMenuService sysWordMenuService;

    @Autowired
    private DictService dictService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private AppinfoService appinfoService;

    @Autowired
    private LogService logService;

    @Autowired
    RedisUtil redisUtil;

    //
//        @RequestMapping(value = "news")
//        public String news() {
//            return "modules/app/news";
//        }
//
//        @RequestMapping(value = "search")
//        public String search() {
//            return "modules/app/search";
//        }
//
//        @RequestMapping(value = "menuSearch")
//        public String menuSearch() {
//            return "modules/app/menuSearch";
//        }
//
//        @RequestMapping(value = "searchResult")
//        public String searchResult(String keyword, Model model) {
//            model.addAttribute("keyword", keyword);
//            return "modules/app/searchResult";
//        }
//
//
//        /*功能升级中*/
//        @RequestMapping(value = "error")
//        public String error() {
//            return "modules/app/error";
//        }
//
//
    @RequestMapping(value = "/fourSlider")
    public String fourSlider(String id, String name, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "/app/menu/fourSlider";
    }

    //
    @RequestMapping(value = "fourList")
    public String fourList(HttpServletRequest request, Model model, String id) {
        //获取菜单名称
        String name = appMenuService.getMenu(id).getName();
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("noHead", request.getParameter("noHead") == null ? true : request.getParameter("noHead"));
        return "app/menu/fourListOld";
    }

    @RequestMapping(value = "fourListTest")
    public String fourListTest(HttpServletRequest request, Model model, String id) {
        //获取菜单名称
        String name = appMenuService.getMenu(id).getName();
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("noHead", request.getParameter("noHead") == null ? true : request.getParameter("noHead"));
        return "app/menu/fourList";
    }
//
//        @RequestMapping(value = "fiveSliderList")
//        public String fiveSliderList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//            return "modules/app/fiveSliderList";
//        }

    @RequestMapping(value = "fiveTree")
    public String fiveTree(String name, Model model, String id) {

        model.addAttribute("name", name);
        model.addAttribute("id", id);
        return "app/menu/fiveTree";
    }

    @RequestMapping(value = "fiveTreeTest")
    public String fiveTreeTest(String name, Model model, String id) {
        model.addAttribute("name", name);
        model.addAttribute("id", id);
        return "app/menu/fiveTreeNew";
    }

//        @RequestMapping(value = "five16")
//        public String five16(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//            return "modules/app/five16";
//        }

    @RequestMapping(value = "sixTree")
    public String sixTree(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        return "app/menu/sixTree";
    }

//        //部门风采列表
//        @RequestMapping(value = "departmentList")
//        public String departmentList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//            //return "modules/app/departmentList";
//            return "modules/app/depList";
//        }
//
//        //部门风采列表（新）
//        @RequestMapping(value = "depList")
//        public String depList(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//            return "modules/app/depList";
//        }
//        //部门风采列表-详情（新）
//        @RequestMapping(value = "depListD")
//        public String depList_details(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
//                return "modules/app/depList-details";
//        }
//        @RequestMapping(value = "nextList")
//        public String nextList() {
//            return "modules/app/nextList";
//        }
//
//        @RequestMapping(value = "pictureList")
//        public String pictureList() {
//            return "modules/app/pictureList";
//        }
//
//        @RequestMapping(value = "head")
//        public String head() {
//            return "modules/app/head";
//        }
//
//
//        //分享页面
//        @RequestMapping(value = "share")
//        public String share() {
//            return "modules/app/share";
//        }
//
//        //温馨提示
//        @RequestMapping(value = "prompt")
//        public String prompt() {
//            return "modules/app/prompt";
//        }
//
//        //我的足迹
//        @RequestMapping(value = "myFootprint")
//        public String myFootprint() {
//            return "modules/person/myFootprint";
//        }
//
//        //个人中心分享
//        @RequestMapping(value = "myShare")
//        public String myShare() {
//            return "modules/person/myShare";
//        }

//        @RequestMapping(value = "findMainMenu")
//        @ResponseBody
//        public String findMainMenu(HttpServletRequest request, HttpServletResponse response, Model model) {
//            List<Menu> list = appMenuService.findMainMenu();
//            ResultEntity entity = new ResultEntity();
//            entity.setData(list);
//            entity.setIsTrue(true);
//            String jsonObject = JSON.toJSONString(entity);
//            return jsonObject;
////            return renderString(response, entity);
//        }

    @RequestMapping("getDemo")
    @ResponseBody
    public String getDemo(String url, HttpServletRequest request) throws Exception {
        url = url.replace("*", "&");
        String param = request.getParameter("param");
        //转编码
        param = new String(param.getBytes("UTF-8"), "ISO-8859-1");
        System.out.println("#url:" + url);
        System.out.println("#param:" + param);
        String result = AppHttpUtils.sendHttpPost(url, param);
        return result;
    }

    //项目审批系统 进度查询
    @RequestMapping(value = "project/queryProgress")
    public String queryProgress(Model model) {
        //登录地址
        String url = "http://19.200.90.199/webapi.do?login&appid=sdbst&secret=369d465e6906ae37040eecc76e2108ce";
        String result = HttpUtil.sendGet(url, null, "");
        System.out.println("result:" + result);
        //获取access_token
        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.containsKey("code")) {
            String code = resultJson.getString("code");
            if ("0".equals(code)) {
                //code=0，登录成功
                JSONObject dataJson = JSONObject.parseObject(resultJson.getString("data"));
                if (dataJson.containsKey("access_token")) {
                    String token = dataJson.getString("access_token");
                    model.addAttribute("access_token", token);
                    System.out.println("token:" + token);
                }
            }
        }

        return "app/government/project/queryProgress";
    }

    //项目审批系统 审批流转意见查询
    @RequestMapping(value = "project/queryOpinion")
    public String queryOpinion(Model model, String username, String password, String id) {
        //登录地址
        String url = "http://19.200.90.199/webapi.do?login&appid=sdbst&secret=369d465e6906ae37040eecc76e2108ce";
        String result = HttpUtil.sendGet(url, null, "");
        System.out.println("result:" + result);
        //获取access_token
        JSONObject resultJson = JSONObject.parseObject(result);
        if (resultJson.containsKey("code")) {
            String code = resultJson.getString("code");
            if ("0".equals(code)) {
                //code=0，登录成功
                JSONObject dataJson = JSONObject.parseObject(resultJson.getString("data"));
                if (dataJson.containsKey("access_token")) {
                    String token = dataJson.getString("access_token");
                    model.addAttribute("access_token", token);
                    System.out.println("token:" + token);
                }
            }
        }
        model.addAttribute("username", username);
        model.addAttribute("password", password);
        model.addAttribute("id", id);
        return "app/government/project/queryOpinion";
    }

    //项目审批系统 登录
    @RequestMapping(value = "project/login")
    public String projectLogin(Model model) {
//        //登录地址
//        String url="http://19.200.90.199/webapi.do?login&appid=sdbst&secret=369d465e6906ae37040eecc76e2108ce";
//        String result=HttpUtil.sendGet(url, null,"");
//        System.out.println("result:"+result);
//        //获取access_token
//        JSONObject resultJson=JSONObject.parseObject(result);
//        if(resultJson.containsKey("code")) {
//            String code=resultJson.getString("code");
//            if("0".equals(code)) {
//                //code=0，登录成功
//                JSONObject dataJson=JSONObject.parseObject(resultJson.getString("data"));
//                if(dataJson.containsKey("access_token")) {
//                    String token=dataJson.getString("access_token");
//                    model.addAttribute("access_token", token);
//                    System.out.println("token:"+token);
//                }
//            }
//        }
        return "app/government/project/login5";
    }

    @RequestMapping(value = "findChildsMenu")
    @ResponseBody
    public String findChildsMenu(Menu menu) {
        List<Menu> list = appMenuService.findChildsMenu(menu);
        Resp entity = new Resp(RespCode.SUCCESS, list);
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }


    @RequestMapping("getjson")
    @ResponseBody
    public String getjson(String url) {
        String result = HttpUtil.sendGet(url, null, "");
        System.out.println(result);
        return result;
    }


    @RequestMapping("/getJson")
    @ResponseBody
    public String getJson(String url) {
        String result = HttpUtil.sendGet(url, null, "");
        System.out.println(result);
        return result;
    }

    //        @RequestMapping("queryJson")
//        @ResponseBody
//        public String queryJson(String url) throws UnsupportedEncodingException {
//            url=URLEncoder.encode(url, "UTF-8");	//转码
//            System.out.println(url);
//            String result=HttpUtils.sendPost(PostUrl+"/app/menu/getjson?url="+url, null);
//            return result;
//        }
//
//        //市民之窗
//        @RequestMapping("getjsonPublicWindow")
//        @ResponseBody
//        public String test1(String url) throws UnsupportedEncodingException {
//            String newurl=url.replace("*", "&");
//            //String newurl=url;
//            int flag1=newurl.indexOf("word=");
//            if(flag1>=0) {
//                int flag2=newurl.lastIndexOf("&");
//                String url1=newurl.substring(0, flag1+5);
//                String word=newurl.substring(flag1+5,flag2);
//                String url2=newurl.substring(flag2);
//                word=URLEncoder.encode(word,"UTF-8");
//                newurl=url1+word+url2;
//            }
//            //System.out.println(newurl);
//            String result=test(newurl);
//            //System.out.println(result);
//            return result;
//        }

    @RequestMapping(value = "/find1To3Menu")
    @ResponseBody
    public String find1To3Menu(Menu menu) {
        List<Menu> list = appMenuService.find1To3Menu(menu);
        Resp entity = new Resp(RespCode.SUCCESS, list);
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }

    @ApiOperation(value = "三级菜单", notes = "一级，二级，三级菜单")
    @RequestMapping(value = "/clearSession")
    @ResponseBody
    public String clearSession(HttpServletRequest request) {

        request.getSession().invalidate();
        Resp entity = new Resp(RespCode.SUCCESS, "清除所有缓存");
        //            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(entity);
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }

    /***
     * 保存用户菜单（个人定制）
     * @param userMenu
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "saveUserMenu")
    public Map<String, String> saveUserMenu(SysUserMenu userMenu, HttpServletRequest request) {
		/*if (!beanValidator(model, sysUserMenu)){
			return form(sysUserMenu,new Menu(), model);
		}*/
        Map<String, String> map = Maps.newLinkedHashMap();
        map.put("success", "true");
        if (!StringUtils.isEmpty(userMenu.getMenuIds())) {
            int length = userMenu.getMenuIds().split(",").length;
            if (length > 7) {
                map.put("success", "false");
                map.put("obj", "菜单数不能超过7个！");
                return map;
            } else if (length < 1) {
                map.put("success", "false");
                map.put("obj", "菜单数必须大于1个！");
                return map;
            }
        } else {
            map.put("success", "false");
            map.put("obj", "菜单数不能为0！");
            return map;
        }
        //获取用户菜单
//		SysUserMenu user = sysUserMenuService.get(UserUtils.getUser().getId());
//		if(user!=null){
//			//设置菜单
//			user.setMenuIds(userMenu.getMenuIds());
//			userMenu=user;
//		}else{
//		}

        String userId = CookieUtil.getCookie(request, "userId");

        userMenu.setId(userId);
        System.out.println(userMenu);
        if (userId == null || userId.length() == 0) {
            map.put("success", "false");
            map.put("obj", "用户未登陆！");
            return map;
        } else {

            SysUserMenu sysUserMenu = sysUserMenuService.get(userMenu.getId());
            if (sysUserMenu == null) {
                userMenu.setIsNewRecord(true);
                userMenu.setUpdateDate(new Date());
                userMenu.setUpdateBy(userId);
                userMenu.setRemarks("新增" + userId + "个人定制");
            } else {
                userMenu.setIsNewRecord(false);
            }
//		userMenu.setIsNewRecord(true);
            sysUserMenuService.save(userMenu, request);
            map.put("success", "true");
            map.put("obj", "保存菜单成功");
            return map;
        }

    }


    @RequestMapping(value = "homeMenu")
    @ResponseBody
    public String homeMenu(HttpServletRequest request, HttpServletResponse response) {
        String userId = CookieUtil.getCookie(request, "userId");

//            User user  = (User) request.getSession().getAttribute("user");
        if (userId == null || userId.length() == 0) {
            String Ids[] = null;
//                request.getSession().invalidate();
            List<Menu> meunList = (List<Menu>) request.getSession().getAttribute("defaultMenu");
            if (meunList == null || meunList.size() == 0) {//如果没有缓存
                meunList = new ArrayList<>();
                SysUserMenu userMenu = sysUserMenuService.get("1");
                System.out.println(userMenu.getId());
                System.out.println(userMenu.getMenuIdsList());
                System.out.println(userMenu.getMenuIds());
                if (userMenu != null) {
                    Ids = userMenu.getMenuIds().split(",");
                    for (String id : Ids) { //读取菜单详情
                        Menu menu = appMenuService.getMenu(id);
                        if (menu != null) {
                            meunList.add(menu);
                        }
                    }
                    request.getSession().setAttribute("defaultMenu", meunList);
                    Resp entity = new Resp(RespCode.SUCCESS, meunList);
                    String jsonObject = JSON.toJSONString(entity);
                    return jsonObject;
                } else {
                    Resp entity = new Resp(RespCode.DEFAULT, meunList);
                    String jsonObject = JSON.toJSONString(entity);
                    return jsonObject;
                }
            } else {
                Resp entity = new Resp(RespCode.SUCCESS, meunList);
                String jsonObject = JSON.toJSONString(entity);
                return jsonObject;
            }
        } else {
            List<Menu> menuList = sysUserMenuService.UserMenu(userId);
            Resp entity = new Resp(RespCode.SUCCESS, menuList);
            String jsonObject = JSON.toJSONString(entity);
            return jsonObject;
//                String tokens = null;
//                try {
//                    tokens = Cryptos.aesDecrypt(token);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                String userId = tokens.split("|")[0];
//                return "redirect:/app/menu/userMenu";
        }
    }


//        @ApiOperation(value="个人定制菜单（默认）", notes="用户未登陆，则显示默认的定制菜单")
////        @RequestMapping(value = "defaultMenu")
//        @RequestMapping(value = "/defaultMenu")
//        @ResponseBody
//        public String defaultMenu(HttpServletRequest request, HttpServletResponse response, Model model) {
//
//            String Ids[] = null;
//            request.getSession().invalidate();
//            List<Menu> meunList = (List<Menu>)request.getSession().getAttribute("defaultMenu");
//            if(meunList==null||meunList.size()==0) {//如果没有缓存
//                meunList = new ArrayList<>();
//                SysUserMenu userMenu = sysUserMenuService.get("1");
//                System.out.println(userMenu.getId());
//                System.out.println(userMenu.getMenuIdsList());
//                System.out.println(userMenu.getMenuIds());
//                if (userMenu != null) {
//                    Ids = userMenu.getMenuIds().split(",");
//                    for (String id : Ids) { //读取菜单详情
//                        Menu menu = appMenuService.getMenu(id);
//                        if (menu != null) {
//                            meunList.add(menu);
//                        }
//                    }
//                    request.getSession().setAttribute("defaultMenu",meunList);
//                    Resp entity = new Resp(RespCode.SUCCESS, meunList);
//                    String jsonObject = JSON.toJSONString(entity);
//                    return jsonObject;
//                } else {
//                    Resp entity = new Resp(RespCode.DEFAULT, meunList);
//                    String jsonObject = JSON.toJSONString(entity);
//                    return jsonObject;
//                }
//            }else {
//                Resp entity = new Resp(RespCode.SUCCESS, meunList);
//                String jsonObject = JSON.toJSONString(entity);
//                return jsonObject;
//            }
////            return renderString(response, entity);
//        }


//        /**
//         * 获取首页用户栏目
//         * @return
//         */
//        @ApiOperation(value="个人定制菜单（登录）", notes="首页用户个人定制菜单")
//        @RequestMapping(value = "userMenu",method = RequestMethod.GET)
//        @ResponseBody
//        public String userMenu( HttpServletRequest request) {
//            User user  = (User) request.getSession().getAttribute("user");
//            List<Menu> menuList=sysUserMenuService.UserMenu(user.getId());
//            Resp entity = new Resp(RespCode.SUCCESS,menuList);
//            String jsonObject = JSON.toJSONString(entity);
//            return  jsonObject;
//        }


    @ApiOperation(value = "一键服务", notes = "首页一键服务菜单")
    @RequestMapping(value = "/SysWorkMenu/getListName")
    @ResponseBody
    public Map<String, Object> getListName(HttpServletRequest request, HttpServletResponse response) {

        Map reMap = (Map) request.getSession().getAttribute("SysWorkMenu");
        if (reMap == null) {
            Map<String, Object> map = Maps.newLinkedHashMap();

            List<SysWorkMenu> list = new ArrayList<SysWorkMenu>();
            List<SysWorkMenu> sys_list = sysWordMenuService.getListName();
            map.put("success", true);
            map.put("msg", "操作成功");
            for (SysWorkMenu sysWorkMenu : sys_list) {
                //查询菜单的href和type
                System.out.println(sysWorkMenu.toString());
//                    System.out.println(sysWorkMenu.getMenuIds());
                Menu menu = appMenuService.getMenu(sysWorkMenu.getMenuIds());
                if (menu != null && StringUtils.isNotBlank(menu.getHref())) {
                    sysWorkMenu.setHref(menu.getHref());
                    sysWorkMenu.setType(menu.getType());
                    sysWorkMenu.setOpenWay(menu.getOpenWay());
                }

                list.add(sysWorkMenu);
            }
            map.put("obj", list);
            request.getSession().setAttribute("SysWorkMenu", map);
            return map;

        } else {
            return reMap;
        }
    }

    /**
     * 用户按关键词搜索菜单功能,
     * 不仅按 菜单tag属性模糊查询 ， 还会按菜单name属性模糊查询
     *
     * @param tag 关键词
     * @return
     */
    @RequestMapping(value = "findByTag")
    @ResponseBody
    public Map<String, Object> findMenueByTag(String tag) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isBlank(tag)) {
            map.put("operate", "false");
            map.put("message", "请填写搜索词");
            return map;
        }
        /**
         * 筛选菜单
         * 政务1d87c70238694e89a13a59ffaa86a3cd
         * 交通057b4773d1944480b417866950ce0e2a
         * 生活5f374d2fb2a446718294611fcbbdfb8a
         * 办事74acdf64837e4b0bb5517e40d05c2ae4
         * */
        List<Menu> list = appMenuService.findMenuByTag(tag);
        List<Menu> subList = new ArrayList<Menu>();
        for (Menu menu : list) {
            if (StringUtils.isNotBlank(menu.getParentIds())) {
                if (menu.getParentIds().indexOf("1d87c70238694e89a13a59ffaa86a3cd") >= 0 || menu.getParentIds().indexOf("057b4773d1944480b417866950ce0e2a") >= 0 || menu.getParentIds().indexOf("5f374d2fb2a446718294611fcbbdfb8a") >= 0 || menu.getParentIds().indexOf("74acdf64837e4b0bb5517e40d05c2ae4") >= 0) {
                    subList.add(menu);
                }
            }
        }
        System.out.println("list:" + list.size() + "  subList:" + subList.size());
        map.put("list", subList);
        map.put("tag", tag);
        return map;
    }

    @RequestMapping(value = "findHotTag")
    @ResponseBody
    public String findHotTag(HttpServletRequest request, HttpServletResponse response) {
        @SuppressWarnings("rawtypes")
        List list = appMenuService.findHotTags();
        Resp resp = new Resp(RespCode.SUCCESS, list);
        String jsonObject = JSON.toJSONString(resp);
        return jsonObject;
    }

    @RequestMapping(value = "getData")
    public String getData(HttpServletRequest request, HttpServletResponse response, HttpSession session, String url, Model model, String adapt) {
        String test = url.replace("*", "&");
        String title = request.getParameter("title");
        System.out.println("titil::====" + title);
        model.addAttribute("title", title);
        model.addAttribute("url", test);
        model.addAttribute("adapt", adapt);

        Enumeration pNames = request.getParameterNames();
        while (pNames.hasMoreElements()) {
            String name = (String) pNames.nextElement();
            String value = request.getParameter(name);
            System.out.println(name + "=" + value);
        }
        //免责声明
        String disclaimer = request.getParameter("disclaimer");
        model.addAttribute("disclaimer", disclaimer);

        if (StringUtils.isNotBlank(disclaimer)) {
            //查询字典
            Dict dict = new Dict();
            dict.setType("disclaimer");
            List<Dict> dictList = dictService.findList(dict);
            if (dictList != null && dictList.size() > 0) {
                for (int i = 0; i < dictList.size(); i++) {
                    if (disclaimer.equals(dictList.get(i).getValue())) {
                        dict = dictList.get(i);
                        break;
                    }
                }
            }
            model.addAttribute("dict", dict);
            model.addAttribute("disclaimer", disclaimer);
        }
        return "app/detail";
    }

//    //项目审批系统 进度查询
//    @RequestMapping(value = "/project/queryProgress")
//    public String queryProgress(HttpSession session) {
//        //登录地址
//        String url="http://19.200.90.199/webapi.do?login&appid=sdbst&secret=369d465e6906ae37040eecc76e2108ce";
//        String result=HttpUtil.sendGet(url, "","");
//        System.out.println("result:"+result);
//        //获取access_token
//        JSONObject resultJson=JSONObject.parseObject(result);
//        if(resultJson.containsKey("code")) {
//            String code=resultJson.getString("code");
//            if("0".equals(code)) {
//                //code=0，登录成功
//                JSONObject dataJson=JSONObject.parseObject(resultJson.getString("data"));
//                if(dataJson.containsKey("access_token")) {
//                    String token=dataJson.getString("access_token");
//                    session.setAttribute("access_token", token);
//                    System.out.println("token:"+token);
//                }
//            }
//        }
//
//        return "app/government/project/queryProgress";
//    }
//
//    //项目审批系统 审批流转意见查询
//    @RequestMapping(value = "/project/queryOpinion")
//    public String queryOpinion(HttpSession session) {
//        //登录地址
//        String url="http://19.200.90.199/webapi.do?login&appid=sdbst&secret=369d465e6906ae37040eecc76e2108ce";
//        String result=HttpUtil.sendGet(url, "","");
//        System.out.println("result:"+result);
//        //获取access_token
//        JSONObject resultJson=JSONObject.parseObject(result);
//        if(resultJson.containsKey("code")) {
//            String code=resultJson.getString("code");
//            if("0".equals(code)) {
//                //code=0，登录成功
//                JSONObject dataJson=JSONObject.parseObject(resultJson.getString("data"));
//                if(dataJson.containsKey("access_token")) {
//                    String token=dataJson.getString("access_token");
//                    session.setAttribute("access_token", token);
//                    System.out.println("token:"+token);
//                }
//            }
//        }
//        return "app/government/project/queryOpinion";
//    }
//
//    //项目审批系统 登录
//    @RequestMapping(value = "/project/login")
//    public String projectLogin(HttpSession session) {
//        //登录地址
//        String url="http://19.200.90.199/webapi.do?login&appid=sdbst&secret=369d465e6906ae37040eecc76e2108ce";
//        String result=HttpUtil.sendGet(url, null,"");
//        System.out.println("result:"+result);
//        //获取access_token
//        JSONObject resultJson=JSONObject.parseObject(result);
//        if(resultJson.containsKey("code")) {
//            String code=resultJson.getString("code");
//            if("0".equals(code)) {
//                //code=0，登录成功
//                JSONObject dataJson=JSONObject.parseObject(resultJson.getString("data"));
//                if(dataJson.containsKey("access_token")) {
//                    String token=dataJson.getString("access_token");
//                    session.setAttribute("access_token", token);
//                    System.out.println("token:"+token);
//                }
//            }
//        }
//        return "app/government/project/login";
//    }

    /**
     * 一级分类菜单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "oneMenu")
    @ResponseBody
    public String oneMenu(HttpServletRequest request) {

        List<Menu> lists = appMenuService.getOneMenu();//获取一级菜单，去除首页和我的

        String[] menuIds = new String[lists.size()];
        for (int i = 0; i < lists.size(); i++) {
            menuIds[i] = lists.get(i).getId();
        }
        List<Menu> meunList = Lists.newArrayList();
        for (int i = 0; i < menuIds.length; i++) {
            Menu menu = appMenuService.getMenu(menuIds[i]);
            if (menu != null) {
                meunList.add(menu);
            }
        }
        Resp entity = new Resp(RespCode.SUCCESS, meunList);
//            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(entity);
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }


    /**
     * 获取首页用户栏目
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "userMenu")
    @ResponseBody
    public String userMenu(HttpServletRequest request, HttpServletResponse response) {
        String userId = CookieUtil.getCookie(request, "userId");
        System.out.println("userMenu:userId : " + userId);
        List<Menu> menuList = sysUserMenuService.UserMenu(userId);
        Resp entity = new Resp(RespCode.SUCCESS, menuList);
//            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(entity);
        String jsonObject = JSON.toJSONString(entity);
        return jsonObject;
    }

    //需要带用户信息过来cookie,source 为1，id为分类id ，userId ：fb7e80028a5147518a8048de0b6e368f
    @RequestMapping(value = "sunMenu", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String sunMenu(String id, String source, HttpServletRequest request, HttpServletResponse response) {
//            Resp entity = new Resp(RespCode.SUCCESS);
//            String userId  ="9e97c4ff981a4705bcd7daa6a68f0c02";
        String userId = CookieUtil.getCookie(request, "userId");
        System.out.println("userId : " + userId);
        if (userId == null || userId.length() == 0) {
            Resp resp = new Resp(RespCode.DEFAULT);
            resp.setMsg("未传用户信息");
            String jsonObject = JSON.toJSONString(resp);
            return jsonObject;
        } else {
            List<Menu> meunList = appMenuService.menuToUserApp(id, source);
            List<Menu> userMenu = sysUserMenuService.UserMenu(userId);//获取用户菜单
            if (userMenu != null && meunList != null) {
                for (int i = 0; i < userMenu.size(); i++) {
                    Menu usermenu = userMenu.get(i);
                    a:
                    for (int j = 0; j < meunList.size(); j++) {
                        Menu menu = meunList.get(j);
                        if (menu != null && usermenu.getId().equals(menu.getId())) {//删除用户菜单
                            meunList.remove(j);
                            break a;

                        }
                    }
                }
            }

//            entity.setData(meunList);
//            return renderString(response, entity);
            Resp entity = new Resp(RespCode.SUCCESS, meunList);
//            JSONObject jsonObject = (JSONObject) JSONObject.toJSON(entity);
            String jsonObject = JSON.toJSONString(entity);
            return jsonObject;
        }

    }

    /**
     * 根据type和value查询免责声明
     */
    @RequestMapping(value = "getDisclaimer")
    @ResponseBody
    public Dict getDisclaimer(Dict dict, HttpServletRequest request, HttpServletResponse response) {
        if (StringUtils.isNotBlank(dict.getType()) && StringUtils.isNotBlank(dict.getValue())) {
            List<Dict> list = dictService.findList(dict);
            //根据value筛选
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getValue().equals(dict.getValue())) {
                        return list.get(i);
                    }
                }
            }
        }
        return new Dict();
    }

    @GetMapping(value = {"/homeMenuData"})
    @ResponseBody
    public String homeMenuData(HomeMenuVo vo, HttpServletRequest request) {
        //声明返回结果对象
        Resp resp;
        LogEntity logEntity = new LogEntity();
        logEntity.setTime(new Date());

        //如果redis宕机，直接返回静态json数据
        String filePath = "static/json/static-json.properties";
        String redisState = PropertiesUtil.getKey(filePath, "redisState");
        if (redisState.equals("false")) {
            String json = PropertiesUtil.getKey(filePath, "homeMenuData");
            return json;
        }

        //创建view对象
        HomeMenuView view = new HomeMenuView();
        /*首页轮播图数据获取*/
        vo.getArticle().setDelFlag("2");
        if (vo.getPageSize() == null) {
            vo.setPageSize(15);
        }
        Map<String, Object> articleMap = articleService.articleMap(vo.getArticle(),
                vo.getInfoType(), vo.getPageIndex(), vo.getPageSize(), request);
        //初始化视图轮播图数据
        view.setArticleMap(articleMap);

        /*热门菜单*/
        List<Menu> hotMenus = sysUserMenuService.hotMenu("1");
        view.setHotMenu(hotMenus);

        /*bannerList*/
        List<Menu> banners = appMenuService.bannerMenu("909d7ae134d54863af3ed43b377def0f");
        view.setBanners(banners);

        /*防疫专栏数据*/
        Menu menu = appMenuService.epidemicColumnMenu("1d9940770d004a88a892e814a09ae650");
        view.setEpidemicColumn(menu);

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
        List<AppVersion> version = appinfoService.findList(appVersion);
        view.setAppVersions(version);


        //初始化日志信息
        //设置来访者真实IP地址
        logEntity.setIp(HttpUtil.getClientIp(request));
        //设置访问参数
        logEntity.setArgs(vo.toString());
        //设置内容
        logEntity.setContent("首页总数据接口");
        logEntity.setStartTime(new Date());
        //设置开始访问接口时间
        logEntity.setTime(new Date());
        //设置操作类型
        logEntity.setType("homeMenuData");
        logEntity.setStatus("成功！");
        //设置访问类路径
        logEntity.setInterfacePath("/app/menu/homeMenuData");
        logService.saveLog(logEntity);


        /*返回结果*/
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(view);
        return JSONObject.toJSONString(resp);

    }


    @GetMapping(value = "/homeMenuData1")
    @ResponseBody
    public String homeMenuData1(HomeMenuVo vo, HttpServletRequest request) {
        //声明返回结果对象
        Resp resp;

        //创建view对象
        HomeMenuView view = new HomeMenuView();
        /*首页轮播图数据获取*/
        vo.getArticle().setDelFlag("2");
        if (vo.getPageSize() == null) {
            vo.setPageSize(15);
        }
        Map<String, Object> articleMap = articleService.articleMap(vo.getArticle(),
                vo.getInfoType(), vo.getPageIndex(), vo.getPageSize(), request);
        //初始化视图轮播图数据
        view.setArticleMap(articleMap);

        /*热门菜单*/
        List<Menu> hotMenus = sysUserMenuService.hotMenu("1");
        view.setHotMenu(hotMenus);

        /*bannerList*/
        List<Menu> banners = appMenuService.bannerMenu("909d7ae134d54863af3ed43b377def0f");
        view.setBanners(banners);

        /*防疫专栏数据*/
        Menu menu = appMenuService.epidemicColumnMenu("1d9940770d004a88a892e814a09ae650");
        view.setEpidemicColumn(menu);

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
        List<AppVersion> version = appinfoService.findList(appVersion);
        view.setAppVersions(version);

        /*返回结果*/
        resp = new Resp(RespCode.SUCCESS);
        resp.setData(view);
        return JSONObject.toJSONString(resp);

    }

    //优化后接口
    @GetMapping(value = "/homeMenuData01")
    @ResponseBody
    public String homeMenuData01(HomeMenuVo vo, HttpServletRequest request) {
        LogEntity logEntity = new LogEntity();
        logEntity.setTime(new Date());

        vo.getArticle().setDelFlag("2");
        if (vo.getPageSize() == null) {
            vo.setPageSize(15);
        }
        HomeMenuView view = appMenuService.getHomeMenuData(vo);
        /*返回结果*/
        Resp resp = new Resp(RespCode.SUCCESS);
        resp.setData(view);

        //初始化日志信息
        //设置来访者真实IP地址
        logEntity.setIp(HttpUtil.getClientIp(request));
        //设置访问参数
        logEntity.setArgs(vo.toString());
        //设置内容
        logEntity.setContent("首页总数据接口");
        logEntity.setStartTime(new Date());
        //设置开始访问接口时间
        logEntity.setTime(new Date());
        //设置操作类型
        logEntity.setType("homeMenuData");
        logEntity.setStatus("成功！");
        //设置访问类路径
        logEntity.setInterfacePath("/app/menu/homeMenuData");
        logService.saveLog(logEntity);
        return JSONObject.toJSONString(resp);
    }

    //优化后redis接口
    @GetMapping(value = "/homeMenuData02")
    @ResponseBody
    public String homeMenuData02(HomeMenuVo vo) {
        vo.getArticle().setDelFlag("2");
        if (vo.getPageSize() == null) {
            vo.setPageSize(15);
        }
        HomeMenuView view = appMenuService.getHomeMenuData(vo);
        /*返回结果*/
        Resp resp = new Resp(RespCode.SUCCESS);
        resp.setData(view);
        return JSONObject.toJSONString(resp);
    }


    //纯读取redis和写入测试
    @GetMapping(value = "/readInsert")
    @ResponseBody
    public String readInsert(HttpServletRequest request) {
        LogEntity logEntity = new LogEntity();
        logEntity.setTime(new Date());

        String appMode = redisUtil.get("appMode");

        //初始化日志信息
        //设置来访者真实IP地址
        logEntity.setIp(HttpUtil.getClientIp(request));
        //设置访问参数
        logEntity.setArgs("");
        //设置内容
        logEntity.setContent("纯读取redis和写入测试");
        logEntity.setStartTime(new Date());
        //设置开始访问接口时间
        logEntity.setTime(new Date());
        //设置操作类型
        logEntity.setType("readInsert");
        logEntity.setStatus("成功！");
        //设置访问类路径
        logEntity.setInterfacePath("/app/menu/readInsert");
        logService.saveLog(logEntity);
        return "成功！";
    }


    @GetMapping(value = "/homeMenuDataJson")
    @ResponseBody
    public String homeMenuDataJson() {
        //如果redis宕机，直接返回静态json数据
        String filePath = "static/json/static-json.properties";
        String json = PropertiesUtil.getKey(filePath, "homeMenuData");
        return json;

    }


}

