/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.sdcount;


import com.unifs.sdbst.app.bean.sdcount.SdIndex;
import com.unifs.sdbst.app.dao.primary.sdcount.SdIndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service
 * @author chengpneng
 * @version 2017-11-23
 */
@Service
@Transactional(readOnly = true)
public class SdIndexService{
	@Autowired
	private SdIndexMapper sdIndexDao;
	public SdIndex get(String id) {

		return sdIndexDao.get(id);
	}
	
	public List<SdIndex> findList(SdIndex sdIndex) {

		return sdIndexDao.findList(sdIndex);
	}
	
//	public Page<SdIndex> findPage(Page<SdIndex> page, SdIndex sdIndex) {
//		return super.findPage(page, sdIndex);
//	}
	
	@Transactional(readOnly = false)
	public void save(SdIndex sdIndex) {
		sdIndexDao.insert(sdIndex);
	}
	
	@Transactional(readOnly = false)
	public void delete(SdIndex sdIndex) {
		sdIndexDao.delete(sdIndex);
	}
	@Transactional(readOnly = false)
	public SdIndex findId(String  name) {
		return sdIndexDao.findId(name);
	}
	@Transactional(readOnly = false)
	public List<SdIndex> findName() {
		return sdIndexDao.findName();
	}
}