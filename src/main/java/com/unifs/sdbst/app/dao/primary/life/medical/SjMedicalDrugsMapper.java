/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.dao.primary.life.medical;

import com.unifs.sdbst.app.bean.life.medical.SjMedicalDrugs;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 医疗药品DAO接口
 * @author liuzibo
 * @version 2018-02-06
 */
@Component
public interface SjMedicalDrugsMapper  {
	public SjMedicalDrugs findMedId(@Param("id") String id);
	public List<SjMedicalDrugs>findMedicalDrugs(@Param("bigClass") String bigClass, @Param("englishName") String englishName, @Param("inClass") String inClass, @Param("chineseName") String chineseName);
}