/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.other;


import com.unifs.sdbst.app.bean.government.SysGuide;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 危化品办事指南DAO接口
 *
 * @author panxiyi
 * @version 2018-08-13
 */
@Component
public interface SysGuideMapper {
    List<SysGuide> getList();

    List<SysGuide> getDetail(@Param("id") String id);
}