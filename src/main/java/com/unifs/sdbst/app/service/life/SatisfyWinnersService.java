package com.unifs.sdbst.app.service.life;


import com.unifs.sdbst.app.bean.life.Satisfaction;
import com.unifs.sdbst.app.bean.life.SatisfyAward;
import com.unifs.sdbst.app.bean.life.SatisfyWinners;
import com.unifs.sdbst.app.dao.primary.life.SatisfyWinnersMapper;
import com.unifs.sdbst.app.service.user.SatisfactionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * APP满意度调查中奖名单 Service
 * */
@Service
@Transactional(readOnly = true)
public class SatisfyWinnersService {
	@Autowired
	private SatisfactionService SatisfactionService;
	@Autowired
	private SatisfyWinnersMapper winnersMapper;
	
	
	public List<SatisfyAward> countAward(){
		return winnersMapper.countAward();
	}
	
	public int updateAdvice(SatisfyWinners entity) {
		return winnersMapper.updateAdvice(entity);
	}
	
	public int updateAwardNum(SatisfyWinners entity) {
		return winnersMapper.updateAwardNum(entity);
	}
	
	public int deleteAll() {
		return winnersMapper.deleteAll();
	}

	public void save(SatisfyWinners winners){
	    winners.preInsert();
		winnersMapper.insert(winners);
	}
	
	//查询手机号是否存在
	public Integer countPhone(String phone) {
		return winnersMapper.countPhone(phone);
	}

	/*public SatisfyWinners get(String id) {
		if(StringUtils.isNotBlank(id)) {
			SatisfyWinners winners=winnersMapper.get(id);
			if(winners!=null) {
				//计算分数和答案
				List<Satisfaction> SatisfactionList=SatisfactionService.findList(new Satisfaction());
				int score=0;
				if(StringUtils.isNotBlank(winners.getAnswer())) {
					//拆分答案
					String[] answers=StringUtils.split(winners.getAnswer(), ",");
					for(int i=0; i<answers.length; i++) {
						if(answers[i].indexOf("-") >= 0) {
							String[] as=StringUtils.split(answers[i], "-");
							if(as.length==2) {
								for(Satisfaction satisfaction: SatisfactionList) {
									if(satisfaction.getId().equals(as[0])) {
										//判断答案是否正确
										if(satisfaction.getMaxOption().indexOf(as[1]) >= 0) {
											score++;
											answers[i]=as[1]+"-1";
										}else {
											answers[i]=as[1]+"-0";
										}
										break;
									}
								}
							}
						}
					}
					winners.setAnswers(answers);	//设置答案
				}
				winners.setScore(score);	//设置分数
				
				return winners;
			}else {
				return new SatisfyWinners();
			}
		}else {
			return new SatisfyWinners();
		}
		
	}*/
	
	//设置奖品名称和领奖码
/*	public List<SatisfyWinners> updateAward(String checkIds, String awardName){
		List<SatisfyWinners> list=null;
		if(StringUtils.isNotBlank(checkIds) && StringUtils.isNotBlank(awardName)) {
			String[] ids=StringUtils.split(checkIds, ",");
			//根据ids查询数据集合
			list=winnersMapper.findListByIds(ids);
			if(list!=null && list.size()>0) {
				for(SatisfyWinners winners: list) {
					winners.setAward(awardName);
					winners.setAwardNum(1);		//未领奖
					
					int code;
					Integer count=null;
					do {
						//生成6位数领奖码
						code=(int)(Math.random()*900000)+100000;
						//查询领奖码是否存在
						count=winnersMapper.countCode(code+"");
					}while(count!=null && count>0);
					winners.setCode(code+"");
					//保存
					winnersMapper.updateAward(winners);
				}
				//重新加载数据
				list=winnersMapper.findListByIds(ids);
			}
		}
		
		return list;
	}

	*/
	
}

