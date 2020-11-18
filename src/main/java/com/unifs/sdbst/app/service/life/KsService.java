package com.unifs.sdbst.app.service.life;

import com.unifs.sdbst.app.bean.life.KsEntity;
import com.unifs.sdbst.app.dao.primary.life.KsEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class KsService {
	@Autowired
	private KsEntityMapper ksDao;
	@Transactional(readOnly = false)
	public List<KsEntity> queryByType(String ksh, String csrq) {
		return ksDao.queryByType(ksh,csrq);
	}
	
}