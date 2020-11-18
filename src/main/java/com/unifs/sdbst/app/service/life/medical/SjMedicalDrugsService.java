/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.life.medical;

import com.github.pagehelper.PageHelper;
import com.unifs.sdbst.app.bean.life.medical.*;
import com.unifs.sdbst.app.dao.primary.life.medical.*;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 医疗药品Service
 *
 * @author liuzibo
 * @version 2018-02-06
 */
@Service
@Transactional(readOnly = true)
public class SjMedicalDrugsService {

    @Autowired
    private SjMedicalDrugsMapper dao;

    @Autowired
    private InternetWordMapper internetWordMapper;

    @Autowired
    private InfoRecordMapper infoRecordMapper;

    @Autowired
    private PersonRecordMapper personRecordMapper;

    @Autowired
    private PoliceCheckMapper policeCheckMapper;

    @Autowired
    private SiteRecordMapper siteRecordMapper;

    @Autowired
    private RedisUtil redisUtil;

//	public SjMedicalDrugs get(String id) {
//		return super.get(id);
//	}
//
//	public List<SjMedicalDrugs> findList(SjMedicalDrugs sjMedicalDrugs) {
//		return super.findList(sjMedicalDrugs);
//	}

//	public Page<SjMedicalDrugs> findPage(Page<SjMedicalDrugs> page, SjMedicalDrugs sjMedicalDrugs) {
//		return super.findPage(page, sjMedicalDrugs);
//	}

    @Transactional(readOnly = false)
    public SjMedicalDrugs findMedId(String id) {
        return dao.findMedId(id);
    }

    @Transactional(readOnly = false)
    public List<SjMedicalDrugs> findMedicalDrugs(String bigClass, String englishName, String inClass, String chineseName) {
        return dao.findMedicalDrugs(bigClass, englishName, inClass, chineseName);
    }

    //	@Transactional(readOnly = false)
//	public void save(SjMedicalDrugs sjMedicalDrugs) {
//		super.save(sjMedicalDrugs);
//	}
//
//	@Transactional(readOnly = false)
//	public void delete(SjMedicalDrugs sjMedicalDrugs) {
//		super.delete(sjMedicalDrugs);
//	}
//
    public void insert(InternetWord internetWord) {
        //获取验证码
        String sysCode = redisUtil.get(internetWord.getPhone());

        // 验证验证码是否过期
        if (StringUtils.isEmpty(sysCode)) {
            throw new MyException("验证码已失效！请重新获取", 0);
        }
        //判断验证码是否正确
        if (!sysCode.equals(internetWord.getCode())) {
            throw new MyException("验证码错误！", 0);
        }
        internetWord.setInsertDate(new Date());
        internetWord.setId(UUID.randomUUID().toString().replace("-", ""));
        internetWordMapper.insert(internetWord);
    }

    public List<InternetWord> findList() {
        return internetWordMapper.selectAll();
    }

    @Transactional(readOnly = false)
    public void saveInfoRecord(InfoRecord infoRecord) {
        if (StringUtils.isEmpty(infoRecord.getId())){
            infoRecord.setId(UUID.randomUUID().toString().replace("-", ""));
        }
        if (infoRecord.getInsertDate() == null){
            infoRecord.setInsertDate(new Date());
        }
        infoRecordMapper.insert(infoRecord);
    }

    public List<String> test(){
        return  internetWordMapper.test();
    }
    public List<PersonRecord> getPersonByPhone(String phone) {

        return personRecordMapper.getPersonByPhone(phone);
    }
    public List<PersonRecord> selectAllPersonRecord() {

        return personRecordMapper.selectAll();
    }

    public List<InfoRecord> getInfoRecordBySite(Integer page, Integer limit, String siteCode, String names, String phones, String type) {
        PageHelper.startPage(page, limit);
        if ("1".equals(type)){
            return infoRecordMapper.getInfoRecordBySite(siteCode, names, phones);
        }else if ("2".equals(type)){
            return infoRecordMapper.getInfoRecordBySite2(siteCode, names, phones);
        }else if ("3".equals(type)){
            return infoRecordMapper.getInfoRecordBySite3(siteCode, names, phones);
        }else {
            List<InfoRecord> list = new ArrayList<InfoRecord>();
            return list;
        }
    }

    public InfoRecord getInfoRecordById(String rid) {
        return infoRecordMapper.getInfoRecordById(rid);
    }

    public void updateInfoRecordById(InfoRecord infoRecord, String type) {
        if ("1".equals(type)){
            infoRecordMapper.updateInfoRecordById(infoRecord);
        }else if ("2".equals(type)){
            infoRecordMapper.updateInfoRecordById2(infoRecord);
        }else if ("3".equals(type)){
            infoRecordMapper.updateInfoRecordById3(infoRecord);
        }else {

        }
    }

    public void insertPolice(PoliceCheck policeCheck) {
        policeCheck.setSystemid(UUID.randomUUID().toString().replace("-", ""));
        policeCheck.setInsertDate(new Date());
        policeCheckMapper.insert(policeCheck);
    }

    public SiteRecord getSiteRecordById(String siteId) {
        return siteRecordMapper.getSiteRecordById(siteId);
    }

    public void updateInfoRecordBySysId(InfoRecord infoRecord) {
        infoRecordMapper.updateInfoRecordBySysId(infoRecord);
    }

    public  List<Map<String, Object >> getTotalNumber()  {
        return siteRecordMapper.getTotalNumber();
    }

    public List<InfoRecord> selectInfoRecord() {
        return infoRecordMapper.selectInfoRecord();
    }

    public List<PoliceCheck> getPoliceBySysId(String systemid) {
        return policeCheckMapper.getPoliceBySysId(systemid);
    }


    public void updatePoliceBySysId(PoliceCheck policeCheck) {
        policeCheckMapper.updatePoliceBySysId(policeCheck);

    }

    public List<Map<String, Object>> getMessageTotal(Date date1, Date date2) {
        return policeCheckMapper.getMessageTotal(date1,date2);
    }
}