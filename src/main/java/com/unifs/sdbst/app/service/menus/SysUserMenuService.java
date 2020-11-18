/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.menus;

import com.unifs.sdbst.app.bean.menus.Menu;
import com.unifs.sdbst.app.bean.menus.SysUserMenu;
import com.unifs.sdbst.app.dao.primary.menus.SysUserMenuMapper;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


/**
 * 用户目录Service
 *
 * @author ctj
 * @version 2017-08-30
 */
@Service
@Transactional(readOnly = true)
public class SysUserMenuService {


    @Autowired
    private SysUserMenuMapper sysUserMenuMapper;


    @Autowired
    private AppMenuService appMenuService;

    @Autowired
    private RedisUtil redisUtil;

    public SysUserMenu get(String id) {
        System.out.println("id:" + id);
        return sysUserMenuMapper.get(id);
    }

    public List<Menu> UserMenu(String userId) {
        String meunIds;
//		List<Menu> meunList=(List<Menu>)UserUtils.getCache("userMenu");//读取缓存菜单
        List<Menu> meunList = null;
        if (meunList == null || meunList.size() == 0) {//如果没有缓存
            meunList = new ArrayList<>();
            SysUserMenu userMenu = get(userId);//读取用户个人菜单
            if (userMenu != null) {
                meunIds = userMenu.getMenuIds();
            } else {//如果用户没有个人菜单，使用默认菜单
                meunIds = get("1").getMenuIds();
            }
            String Ids[] = meunIds.split(",");
            for (int i = 0; i < Ids.length; i++) { //读取菜单详情
                System.out.println(Ids[i]);
                Menu menu = appMenuService.getMenu(Ids[i]);
                if (menu != null) {
                    meunList.add(menu);
                }
            }
        }
//		UserUtils.putCache("userMenu", meunList);
        return meunList;
    }

    /**
     * 保存数据（插入或更新）
     *
     * @param entity
     */
    @Transactional(readOnly = false)
    public void save(SysUserMenu entity, HttpServletRequest request) {
        if (entity.getIsNewRecord()) {
            entity.preInsert();
            sysUserMenuMapper.insert(entity);
        } else {
            entity.preUpdate(request);
            sysUserMenuMapper.update(entity);
        }
    }

    /*热门菜单获取*/
    @Cacheable(value = "hotMenu", key = "#id")
    public List<Menu> hotMenu(String id) {
        SysUserMenu userMenu = sysUserMenuMapper.get(id);
        List<Menu> menuList = new ArrayList<>();
        if (userMenu != null) {
            String[] ids = userMenu.getMenuIds().split(",");
            for (String str : ids) {
                //读取菜单详情
                Menu menu = appMenuService.getMenu(str);
                if (menu != null) {
                    menuList.add(menu);
                }
            }
        }
        return menuList;
    }

}

