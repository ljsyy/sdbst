/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.menus;


import com.unifs.sdbst.app.bean.menus.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 菜单DAO接口
 * @author ThinkGem
 * @version 2014-05-16
 */
@Component
public interface MenuMapper {

	
	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
	public List<Menu> findMainMenu(Menu menu);
	
	public List<Menu> findChildsMenu(Menu menu);
	
	public List<Menu> find1To3Menu(Menu menu);

	List<Menu> testselect(Menu menu);


	public List<Menu> find1To4Menu(Menu menu);
	
	public Menu getByName(Menu menu);
	
	public List<Menu> findPhoneParentIdsLike(Menu menu);
	public List<Menu> findPhoneParentIdLike(Menu Menu);
	public List<Menu> findhs(Menu menu);
	//修改父菜单下所有子菜单的状态
	public void updatePid(Menu Menu);
	public void deleteAll();
	public void deleteMenus(String id);
	public void deleteId(String id);
	public void updateMenus(String id);
	public void updateId(String id);
	public void updateAll();
	public List<Menu> findList();

	public List<Menu> findMenuByTag(@Param("tag") String tag);

	public void setMonitor(Menu menu);

	public List<Menu> findOfInterface(@Param("monitorInterface") String monitorInterface);

	public List<Menu> findMenuByName(@Param("name") String name);
	public List<Menu> findName(@Param("name") String name);

	public Menu getByUri(@Param("uri") String uri);
	
	public  List<Menu> findHrefMenu();
	
	public List<Menu> getHref(String name);

	Menu get(String id);
	public List<Menu> getOneMenu();

	Menu selectMenuTree(String parentId);

	List<Menu> selectByParentId(String parentId);


}
