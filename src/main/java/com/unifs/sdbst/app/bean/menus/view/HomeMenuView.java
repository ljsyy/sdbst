package com.unifs.sdbst.app.bean.menus.view;

import com.unifs.sdbst.app.bean.appinfo.AppVersion;
import com.unifs.sdbst.app.bean.menus.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @version V1.0
 * @title: HomeMenuView
 * @projectName sdbst
 * @description: 首页返回数据实体封装类
 * @author： 张恭雨
 * @date 2020/2/12 17:32
 */
@Data
public class HomeMenuView implements Serializable {
    private Map<String, Object> articleMap;     //轮播图
    private List<Menu> hotMenu;              //热门菜单
    private List<Menu> banners;              //banner图块
    private Menu epidemicColumn;                //防疫专栏
    private String appMode;                 //注册登录模式
    private List<AppVersion> appVersions;   //app版本号
}
