package com.unifs.sdbst.app.dao.primary.life;

import com.unifs.sdbst.app.bean.life.SatisfyAward;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * APP满意度调查的奖品 Dao
 * */
@Component
public interface SatisfyAwardMapper{

	Integer getMaxSort();
	
	int updateSort(SatisfyAward entity);

	SatisfyAward get(String id);

	int update(SatisfyAward satisfyAward);

	int insert(SatisfyAward satisfyAward);

	List<SatisfyAward> findList(SatisfyAward satisfyAward);

}
