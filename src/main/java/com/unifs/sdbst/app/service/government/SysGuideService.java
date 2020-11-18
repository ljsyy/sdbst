/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.government;


import com.unifs.sdbst.app.bean.government.SysGuide;
import com.unifs.sdbst.app.dao.primary.other.SysGuideMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 危化品办事指南Service
 * @author panxiyi
 * @version 2018-08-13
 */
@Service
@Transactional(readOnly = true)
public class SysGuideService {
	@Autowired
	private SysGuideMapper guideMapper;

	
	@Transactional(readOnly = false)
	public List<SysGuide> getList(){
		return guideMapper.getList();
	}

	@Transactional(readOnly = false)
	public List<SysGuide> getDetail(String id) {
		return guideMapper.getDetail(id);
	}
	
}