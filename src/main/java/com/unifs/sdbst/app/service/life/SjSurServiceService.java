/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.life;


import com.unifs.sdbst.app.bean.life.SjSurService;
import com.unifs.sdbst.app.dao.primary.life.SjSurServiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 周边服务Service
 * @author liuzibo
 * @version 2018-02-05
 */
@Service
@Transactional(readOnly = true)
public class SjSurServiceService{

	@Autowired
	private SjSurServiceMapper sjSurServiceMapper;
	
	public SjSurService get(String id) {
		return sjSurServiceMapper.get(id);
	}
	
	public List<SjSurService> getAll(String type){
		return sjSurServiceMapper.getAll(type);
	}
	
	public List<SjSurService> getTypeList(){
		return sjSurServiceMapper.getTypeList();
	}
	
	public List<SjSurService> findList(SjSurService sjSurService) {
		return sjSurServiceMapper.findList(sjSurService);
	}
	
	/*public Page<SjSurService> findPage(Page<SjSurService> page, SjSurService sjSurService) {
		return super.findPage(page, sjSurService);
	}*/
	
	@Transactional(readOnly = false)
	public void save(SjSurService sjSurService) {
		sjSurServiceMapper.save(sjSurService);
	}
	
	@Transactional(readOnly = false)
	public void delete(SjSurService sjSurService) {
		sjSurServiceMapper.delete(sjSurService);
	}
	
}