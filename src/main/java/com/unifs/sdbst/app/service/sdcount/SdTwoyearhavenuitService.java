/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.sdcount;

import com.unifs.sdbst.app.bean.sdcount.SdTwoyearhavenuit;
import com.unifs.sdbst.app.dao.primary.sdcount.SdTwoyearhavenuitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 1Service
 * @author cp
 * @version 2017-12-18
 */
@Service
@Transactional(readOnly = true)
public class SdTwoyearhavenuitService {
	@Autowired
	private SdTwoyearhavenuitMapper dao;
//	public SdTwoyearhavenuit get(String id) {
//		return super.get(id);
//	}
//
//	public List<SdTwoyearhavenuit> findList(SdTwoyearhavenuit sdTwoyearhavenuit) {
//		return super.findList(sdTwoyearhavenuit);
//	}
//
//	public Page<SdTwoyearhavenuit> findPage(Page<SdTwoyearhavenuit> page, SdTwoyearhavenuit sdTwoyearhavenuit) {
//		return super.findPage(page, sdTwoyearhavenuit);
//	}
//
//	@Transactional(readOnly = false)
//	public void save(SdTwoyearhavenuit sdTwoyearhavenuit) {
//		super.save(sdTwoyearhavenuit);
//	}
//
//	@Transactional(readOnly = false)
//	public void delete(SdTwoyearhavenuit sdTwoyearhavenuit) {
//		super.delete(sdTwoyearhavenuit);
//	}
	@Transactional(readOnly = false)
	public List<SdTwoyearhavenuit> year(String type,String year){
		return dao.year(type, year);
	}
	@Transactional(readOnly = false)
	public SdTwoyearhavenuit yearTwo(String type,String year,String name){
		return dao.yearTwo(type, year,name);
	}
	//通过名称查询
	@Transactional(readOnly = false)
	public SdTwoyearhavenuit findName(String type,String year,String name){
		return dao.findName(type, year,name);
	}
	//子类查询
	@Transactional(readOnly = false)
	public List<SdTwoyearhavenuit> yearTwozl(String type,String year,String parentType,String name){
		return dao.yearTwozl(type, year,parentType,name);
	}
	//国名基本情况
	@Transactional(readOnly = false)
	public List<SdTwoyearhavenuit> yearGm(String type,String year,String parentType){
		return dao.yearGm(type, year,parentType);
	}
}