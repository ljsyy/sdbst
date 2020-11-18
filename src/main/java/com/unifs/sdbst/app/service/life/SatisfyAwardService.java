package com.unifs.sdbst.app.service.life;

import com.unifs.sdbst.app.bean.life.SatisfyAward;
import com.unifs.sdbst.app.dao.primary.life.SatisfyAwardMapper;
import com.unifs.sdbst.app.dao.primary.life.SatisfyWinnersMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 　　* @description: APP满意度调查的奖品 Service
 　　* @param ${tags}
 　　* @return ${return_type}
 　　* @throws
 　　* @author 张恭雨
 　　* @date 2019/10/30 9:26
 　　*/

@Service
@Transactional(readOnly = true)
public class SatisfyAwardService{
	@Autowired
	private SatisfyWinnersMapper winnersMapper;
	@Autowired
	private SatisfyAwardMapper awardMapper;

	
	public Integer getMaxSort() {
		return awardMapper.getMaxSort();
	}
	
	public int updateSort(SatisfyAward entity) {
		return awardMapper.updateSort(entity);
	}
	
	public SatisfyAward get(String id) {
		if(StringUtils.isNotBlank(id)) {
			return awardMapper.get(id);
		}else {
			return null;
		}
	}
	
	public int saveOrUpdate(SatisfyAward award) {
		if(StringUtils.isBlank(award.getId())) {
			award.preInsert();
			return awardMapper.insert(award);
		}else {
			return awardMapper.update(award);
		}
	}
		 
/*	public List<SatisfyAward> findList(SatisfyAward entity){
		//查询奖品表
		List<SatisfyAward> list=awardMapper.findList(entity);
		//查询已抽中的奖品数量
		List<SatisfyAward> outList= winnersMapper.countAward();
		//计算剩余数量
		if(list!=null && outList!=null) {
			for(SatisfyAward award: list) {
				boolean noOut=true;	
				for(SatisfyAward outAward: outList) {
					if(StringUtils.isNotBlank(award.getName()) && StringUtils.isNotBlank(outAward.getName()) && award.getName().equals(outAward.getName())) {
						award.setOut(outAward.getOut());
						if(award.getTotal()!=null) {
							award.setRemain(award.getTotal()-award.getOut());
						}
						noOut=false;
						break;
					}
				}
				//没有奖品被抽中
				if(noOut) {
					award.setOut(0);
					award.setRemain(award.getTotal());
				}
			}
		}
		
		return list;
	}*/
	
	
	
}

