package com.unifs.sdbst.app.dao.primary.life;


import com.unifs.sdbst.app.bean.life.SatisfyAward;
import com.unifs.sdbst.app.bean.life.SatisfyWinners;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * APP满意度调查中奖名单 Dao
 * */
@Component
 public interface SatisfyWinnersMapper {

	//删除所有数据
	 int deleteAll();

	//查询已抽中的奖品数量
	 List<SatisfyAward> countAward();

	//修改建议
	 int updateAdvice(SatisfyWinners entity);


	 List<SatisfyWinners> findAdviceList(SatisfyWinners entity);

	//根据ids查询数据集合
	 List<SatisfyWinners> findListByIds(@Param("ids") String[] ids);
	
	//查询领奖码是否存在
	 Integer countCode(String code);
	
	//查询手机号是否存在
	 Integer countPhone(String phone);
	
	//设置奖品
	 int updateAward(SatisfyWinners entity);
	
	//修改奖品数量/状态
	 int updateAwardNum(SatisfyWinners entity);

	 SatisfyWinners get(String id);

	 int insert(SatisfyWinners winners);
	
}
