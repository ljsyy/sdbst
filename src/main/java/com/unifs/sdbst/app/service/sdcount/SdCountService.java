/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.sdcount;


import com.unifs.sdbst.app.bean.sdcount.SdCount;
import com.unifs.sdbst.app.dao.primary.sdcount.SdCountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * aService
 * @author chengpeng
 * @version 2017-11-23
 */
@Service
@Transactional(readOnly = true)
public class SdCountService{
	@Autowired
	private SdCountMapper sdCountDao;
	public SdCount get(String id) {
		return sdCountDao.get(id);
	}
	
	public List<SdCount> findList(SdCount sdCount) {
		return sdCountDao.findList(sdCount);
	}
	
/*	public Page<SdCount> findPage(Page<SdCount> page, SdCount sdCount) {
		return super.findPage(page, sdCount);
	}*/
	
	@Transactional(readOnly = false)
	public void save(SdCount sdCount) {
		sdCountDao.insert(sdCount);
	}
	
	@Transactional(readOnly = false)
	public void delete(SdCount sdCount) {
		sdCountDao.delete(sdCount);
	}
	/*
	 * 年度环比
	 */
	@Transactional(readOnly = false)
	public SdCount findCountAll(String name,String cDate){
		return sdCountDao.findCountAll(name,cDate);
	}
	/*
	 * 年度环比
	 */
	@Transactional(readOnly = false)
	public SdCount tb(String name,String cDate){
		return sdCountDao.tb(name,cDate);
	}
	
	/**
	 * 子类查询
	 * @param name
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<SdCount> findSun(String id,String cDate){
		return sdCountDao.findSun(id,cDate);
	}
	/**
	 * 指标按月统计
	 * @param cDate
	 * @return
	 */
	@Transactional(readOnly = false)
	public List<SdCount> findCountMonth(String cDate){
		return sdCountDao.findCountMonth(cDate);
	}
	
	@Transactional(readOnly = false)
	public List<SdCount> findcDate(){
		return sdCountDao.findcDate();
	}
	
}