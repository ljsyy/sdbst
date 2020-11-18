/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.sdcount;

import com.unifs.sdbst.app.bean.sdcount.Twoyearnotnuit;
import com.unifs.sdbst.app.dao.primary.sdcount.TwoyearnotnuitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * cService
 * @author cp
 * @version 2017-12-19
 */
@Service
@Transactional(readOnly = true)
public class TwoyearnotnuitService {
	@Autowired
	private TwoyearnotnuitMapper dao;
//	public Twoyearnotnuit get(String id) {
//		return super.get(id);
//	}
//
//	public List<Twoyearnotnuit> findList(Twoyearnotnuit twoyearnotnuit) {
//		return super.findList(twoyearnotnuit);
//	}
//
//	public Page<Twoyearnotnuit> findPage(Page<Twoyearnotnuit> page, Twoyearnotnuit twoyearnotnuit) {
//		return dao.findPage(page, twoyearnotnuit);
//	}
//
//	@Transactional(readOnly = false)
//	public void save(Twoyearnotnuit twoyearnotnuit) {
//		dao.save(twoyearnotnuit);
//	}
//
//	@Transactional(readOnly = false)
//	public void delete(Twoyearnotnuit twoyearnotnuit) {
//		dao.delete(twoyearnotnuit);
//	}
	@Transactional(readOnly = false)
	public Twoyearnotnuit year(String type,String year,String name){
		return dao.year(type, year, name);
	}
	@Transactional(readOnly = false)
	public List<Twoyearnotnuit> yearList(String type,String year,String name){
		return dao.yearList(type, year, name);
	}
	@Transactional(readOnly = false)
	public List<Twoyearnotnuit> yearzl(String type,String year,String parentType,String name){
		return dao.yearzl(type, year, parentType,name);
	}
}