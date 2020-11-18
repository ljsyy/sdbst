package com.unifs.sdbst.app.service.life;


import com.unifs.sdbst.app.bean.life.Environment;
import com.unifs.sdbst.app.dao.primary.life.EnvironMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EnvironService {
	
	@Autowired
	private EnvironMapper environMapper;
	
	@Transactional(readOnly = false)
	public Integer update(String type, String result) {
		// TODO Auto-generated method stub
		return environMapper.update(type,result);
	}

	@Transactional(readOnly = false)
	public Environment queryByType(String type) {
		// TODO Auto-generated method stub
		return environMapper.queryByType(type);
	}
	

}
