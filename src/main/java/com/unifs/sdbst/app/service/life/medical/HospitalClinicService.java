/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.unifs.sdbst.app.service.life.medical;

import com.github.pagehelper.PageHelper;
import com.unifs.sdbst.app.bean.life.medical.*;
import com.unifs.sdbst.app.dao.primary.life.medical.*;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.utils.ExcelUtil;
import com.unifs.sdbst.app.utils.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 医疗药品Service
 *
 * @author liuzibo
 * @version 2018-02-06
 */
@Service
@Transactional(readOnly = true)
public class HospitalClinicService {

    @Autowired
    private HospitalClinicMapper hospitalClinicMapper;

    public void saveClinic(HospitalClinic hospitalClinic) {
        if (StringUtils.isEmpty(hospitalClinic.getId())){
            hospitalClinic.setId(UUID.randomUUID().toString().replace("-", ""));
        }
        if (hospitalClinic.getInsertDate() == null){
            hospitalClinic.setInsertDate(new Date());
        }
        hospitalClinicMapper.insert(hospitalClinic);
    }

    public List<HospitalClinic> selectClilnic(HospitalClinic hospitalClinic) {
        return hospitalClinicMapper.selectClilnic(hospitalClinic);
    }


    public void batchImport(String fileName, MultipartFile file , HospitalClinic clinics) throws Exception {
        /*获取文件输入流*/
        InputStream in = file.getInputStream();
        /*调用excel工具类获取excel内容集合*/
        List<List<Object>> lists = ExcelUtil.getListByExcel(in, fileName);

        /*日期格式声明*/
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        /*遍历集合保存数据*/
        for (List<Object> list : lists) {

//            HospitalClinic clinics = new HospitalClinic();
//            clinics.setCol4((String) list.get(0));
            clinics.setId(UUID.randomUUID().toString().replace("-", ""));
            clinics.setInsertDate(new Date());

            clinics.setCol5("1");
            clinics.setName((String) list.get(1));
            clinics.setIdCode((String) list.get(2));
            clinics.setSex((String) list.get(3));
            clinics.setAge((String) list.get(4));
            clinics.setOperName( (String) list.get(5));
            clinics.setCol1((String) list.get(6));
            clinics.setCol4((String) list.get(7));
            clinics.setCol3((String) list.get(8));
            clinics.setPhone((String) list.get(9));

            if (!"".equals(list.get(10))) {
                clinics.setFromWuhan((String) list.get(11));
            }else{
                clinics.setFromWuhan((String) list.get(10));
            }
            clinics.setFromOther((String) list.get(12));

            String illtime = (String) list.get(13);
            if (!"".equals(illtime)){
                clinics.setIllTime(sdf.parse(illtime));
            }
            String doctortime = (String) list.get(14);
            if (!"".equals(doctortime)){
                clinics.setDoctorTime(sdf.parse(doctortime));
            }

            clinics.setDiagnosis( (String) list.get(15));
            clinics.setTemperature((String) list.get(16));
            clinics.setRespiratory((String) list.get(17));
            clinics.setDigestive((String) list.get(18));
            clinics.setTraffic((String) list.get(19));
            clinics.setContacts( (String) list.get(20));
            clinics.setToGuangdong( (String) list.get(21));
            clinics.setClinic((String) list.get(22));
            clinics.setNoClinic((String) list.get(23));

            hospitalClinicMapper.insert(clinics);

        }
    }

    public void update(HospitalClinic clinics) throws Exception {
        hospitalClinicMapper.update(clinics);
    }
}