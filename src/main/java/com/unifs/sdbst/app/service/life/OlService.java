package com.unifs.sdbst.app.service.life;

import com.unifs.sdbst.app.bean.life.OlEntity;
import com.unifs.sdbst.app.dao.primary.life.OlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OlService {
	@Autowired
	private OlMapper mapper;
	@Transactional(readOnly = false)
	public List<OlEntity> queryByType() {
		return mapper.queryByType();
	}
	
	public int insert(String identification,
			String sex,
			String name,
			String phone,
			String qq,
			String email) {
		return mapper.insert(identification,sex,name,phone,qq,email);
	}
	
}