package com.unifs.sdbst.app.dao.primary.menus;

import com.unifs.sdbst.app.bean.menus.SysWorkMenu;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface SysWorkMenuMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysWorkMenu record);

    SysWorkMenu selectByPrimaryKey(String id);

    List<SysWorkMenu> selectAll();

    int updateByPrimaryKey(SysWorkMenu record);

    List<SysWorkMenu> getListName();
}