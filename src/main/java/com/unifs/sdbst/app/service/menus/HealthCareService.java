package com.unifs.sdbst.app.service.menus;


import com.unifs.sdbst.app.bean.menus.HealthCare;
import com.unifs.sdbst.app.bean.menus.HealthExplain;
import com.unifs.sdbst.app.dao.primary.menus.HealthCareMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 佛山市公立医院基本医疗服务项目和价格Service
 * */
@Service
@Transactional(readOnly = true)
public class HealthCareService{
	@Autowired
	private HealthCareMapper healthCareMapper;
	//一级列表
	public List<String> oneNumList(){
		return healthCareMapper.oneNumList();
	}
	//根据一级列表查询二级列表
	public List<String> twoNumList(HealthCare entity){
		return healthCareMapper.twoNumList(entity);
	}
	
	public String getOneNameByOneNum(String oneNum, List<HealthCare> list) {
		for(HealthCare hc: list) {
			if(oneNum != null && hc.getOneNum() != null && oneNum.equals(hc.getOneNum())) {
				return hc.getOneName();
			}
		}
		return "";
	}
	
	public String getTwoNameByTwoNum(String twoNum, List<HealthCare> list) {
		for(HealthCare hc: list) {
			if(twoNum != null && hc.getTwoNum() != null && twoNum.equals(hc.getTwoNum())) {
				return hc.getTwoName();
			}
		}
		return "";
	}
	
	//根据oneNum查询二级
	@Transactional(readOnly = false)
	public List<HealthCare> getTwoList(String oneNum) {
		return healthCareMapper.getTwoList(oneNum);
	}
	
	//根据twoNum查询三级
	@Transactional(readOnly = false)
	public List<HealthCare> getThreeList(String twoNum) {
		return healthCareMapper.getThreeList(twoNum);
	}
	
	//根据threeNum查询四级
	public List<HealthCare> getFourList(String threeNum) {
		// TODO Auto-generated method stub
		return healthCareMapper.getFourList(threeNum);
	}
	
	//根据oneNum查询说明
	public List<HealthExplain> getExpalin(String oneNum) {
		// TODO Auto-generated method stub
		return healthCareMapper.getExplain(oneNum);
	}
	
	//根据关键字查询
	public List<HealthCare> getKeyword(String keyword) {
		// TODO Auto-generated method stub
		return healthCareMapper.getKeyWord(keyword);
	}

	public List<HealthCare> findList(HealthCare healthCare){
		return healthCareMapper.findList(healthCare);
	}
	
}