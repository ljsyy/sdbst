package com.unifs.sdbst.app.dao.primary.life;


import com.unifs.sdbst.app.bean.life.Satisfaction;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * APP满意度调查 Dao
 * */
@Component
public interface AppSatisfactionMapper{

	Satisfaction get(String id);

	int insert(Satisfaction satisfaction);

	int update(Satisfaction satisfaction);
	
	int updateSort(Satisfaction entity);

	List<Satisfaction> findList(Satisfaction satisfaction);
	
	//把ABCD的计数清零
	int clearCount();
	
	Integer getMaxSort();
	
	//修改ABCD的选择次数
	Integer updateCount(Satisfaction entity);

}
