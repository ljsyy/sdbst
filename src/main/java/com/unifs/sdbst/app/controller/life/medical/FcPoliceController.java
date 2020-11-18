package com.unifs.sdbst.app.controller.life.medical;

import com.google.common.collect.Maps;
import com.unifs.sdbst.app.base.Resp;
import com.unifs.sdbst.app.bean.life.medical.*;
import com.unifs.sdbst.app.bean.user.User;
import com.unifs.sdbst.app.enums.RespCode;
import com.unifs.sdbst.app.exception.MyException;
import com.unifs.sdbst.app.service.life.medical.SjMedicalDrugsService;
import com.unifs.sdbst.app.service.user.UserService;
import com.unifs.sdbst.app.utils.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 公安系统对接（凤城管家）Controller
 *
 * @author liuzibo
 * @version 2018-02-07
 */
@Controller
@RequestMapping(value = "data/police")
public class FcPoliceController {

    @Autowired
    private SjMedicalDrugsService sjMedicalDrugsService;

    @Value("${aes.key}")
    private String aesKey;

    private  Logger logger = LoggerFactory.getLogger(FcPoliceController.class);

    @RequestMapping(value = "fcgjInterface",method = RequestMethod.POST)
    @ResponseBody
    public Resp fcgjInterface(PoliceCheck policeCheck,String operType) {
        try {
            System.out.println("开始时间："+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
            long time = System.currentTimeMillis() / 1000;
            long submitTimestamp = Long.parseLong(policeCheck.getTimestamp());//提交的时间戳
            if (StringUtils.isEmpty(policeCheck.getSiteId()) || StringUtils.isEmpty(policeCheck.getPhone()) || StringUtils.isEmpty(policeCheck.getCarNumber()) || StringUtils.isEmpty(policeCheck.getName())
                    || StringUtils.isEmpty(policeCheck.getAge()) || StringUtils.isEmpty(policeCheck.getSex()) || StringUtils.isEmpty(policeCheck.getTownId()) || StringUtils.isEmpty(policeCheck.getTemperature())
                    || StringUtils.isEmpty(policeCheck.getTownName()) || StringUtils.isEmpty(policeCheck.getIdCard()) || StringUtils.isEmpty(policeCheck.getSiteName())
                    || StringUtils.isEmpty(policeCheck.getAuthorization()) || StringUtils.isEmpty(policeCheck.getTimestamp()) || StringUtils.isEmpty(operType) || StringUtils.isEmpty(policeCheck.getSystemid())) {
                Resp resp = new Resp(RespCode.DEFAULT);
                resp.setCode(404);
                resp.setMsg("参数无效");
                return resp;
            }
            String keyCode = "fslt_fcgj_interface" + policeCheck.getPhone() + submitTimestamp;
            String key = DigestUtils.shaHex(keyCode);
            if (submitTimestamp > time + 120 || submitTimestamp < time - 120) {
                Resp resp = new Resp(RespCode.DEFAULT);
                resp.setCode(400);
                resp.setMsg("无效的时间戳");
                return resp;
            } else if (!key.equals(policeCheck.getAuthorization())) {
                Resp resp = new Resp(RespCode.DEFAULT);
                resp.setCode(402);
                resp.setMsg("签名错误");
                return resp;
            } else {
                System.out.println("验证通过："+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
//                InfoRecord infoRecord = new InfoRecord();
//                //站点id 转化
//                SiteRecord siteRecord = sjMedicalDrugsService.getSiteRecordById(policeCheck.getSiteId());
//                if (siteRecord != null){
////                    infoRecord.setSiteCode(siteRecord.getAppCode());
////                    infoRecord.setCol1(siteRecord.getAppName());
//                    policeCheck.setAppCode(siteRecord.getAppCode());
//                    policeCheck.setAppName(siteRecord.getAppName());
//                }else {
//                    infoRecord.setSiteCode(policeCheck.getSiteId());
//                    infoRecord.setCol1(policeCheck.getSiteName());
//                }

//                infoRecord.setZjDate(policeCheck.getFromDate());
//                infoRecord.setCarNumber(policeCheck.getCarNumber());
//                infoRecord.setPhone(policeCheck.getPhone());
//                infoRecord.setName(policeCheck.getName());
//                infoRecord.setCard(policeCheck.getIdCard());
//                infoRecord.setAge(policeCheck.getAge());
//                infoRecord.setSex(policeCheck.getSex());
//                infoRecord.setTemperature(policeCheck.getTemperature());
//                infoRecord.setAddress(policeCheck.getAddress());
//                infoRecord.setToSd(policeCheck.getFromHubei());
//                infoRecord.setReturnDate(policeCheck.getFromDate());
//                infoRecord.setZhejiang(policeCheck.getFromWenzhou());
//                infoRecord.setClosePerson(policeCheck.getClosePerson());
//                infoRecord.setContent(policeCheck.getContent());
//                infoRecord.setName2(policeCheck.getOtherName());
//                infoRecord.setPhone2(policeCheck.getOtherPhone());
//                infoRecord.setWork(policeCheck.getSchool());
//                infoRecord.setWorkCharge(policeCheck.getContactPerson());
//                infoRecord.setWorkPhone(policeCheck.getContactPhone());
//                infoRecord.setCol4(policeCheck.getSchoolType());
//                infoRecord.setCol7( policeCheck.getStreet()!=null?policeCheck.getStreet():"");
//                infoRecord.setCol5( policeCheck.getCountry()!=null?policeCheck.getCountry():"");
//                infoRecord.setCol12("1");
//                infoRecord.setCol2(policeCheck.getOpermeasure());

//                infoRecord.setTime1(date);
//                infoRecord.setId(policeCheck.getSystemid());//设置唯一标识
//                infoRecord.setResplonTime(policeCheck.getRq1());
//                infoRecord.setPoliceDate(policeCheck.getRq2());
//                infoRecord.setHospital(policeCheck.getHsd1());
//                infoRecord.setCol2(policeCheck.getOpermeasure());
//
//                InfoRecord infoRecord1 = sjMedicalDrugsService.getInfoRecordById(policeCheck.getSystemid());//判断是否已经插入数据
//                if (infoRecord1 == null ){//若为空，则重新插入数据
//                    infoRecord.setInsertDate(date);
//                    sjMedicalDrugsService.saveInfoRecord(infoRecord);
//                }else {//有，则更新数据
//                    sjMedicalDrugsService.updateInfoRecordBySysId(infoRecord);
//                }
//                sjMedicalDrugsService.saveInfoRecord(infoRecord);
                Date date = new Date();
                policeCheck.setCol1(operType);
                policeCheck.setCol3(operType);
                policeCheck.setCol2(policeCheck.getSystemid());
//                sjMedicalDrugsService.insertPolice(policeCheck);
                policeCheck.setDate1(date);
//                if ("XZ".equals(operType)){//新增
                    List<PoliceCheck> list = sjMedicalDrugsService.getPoliceBySysId(policeCheck.getSystemid());//判断id是否存在
                System.out.println("查询时间："+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));
                    if (list.size()>0){
                        policeCheck.setCol3(list.get(0).getCol3()+"_"+operType);
                        sjMedicalDrugsService.updatePoliceBySysId(policeCheck);
                        System.out.println("更新时间："+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));

                    }else {
                        sjMedicalDrugsService.insertPolice(policeCheck);
                        System.out.println("插入时间："+new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss:SSS").format(new Date()));

                    }
//                }else if ("GX".equals(operType)){//修改
//                    List<PoliceCheck> list = sjMedicalDrugsService.getPoliceBySysId(policeCheck.getSystemid());//判断id是否存在
//                    if (list.size()>0){
//                        sjMedicalDrugsService.updatePoliceBySysId(policeCheck.getSystemid());
//                    }else {
//                        sjMedicalDrugsService.insertPolice(policeCheck);
//                    }
//                }
                logger.info(policeCheck.getName()+"：传送成功！");
                return new Resp(RespCode.SUCCESS);
            }
        } catch (Exception e) {
            logger.error(policeCheck.getName()+"：传送失败！系统id："+policeCheck.getSystemid());
            logger.error(e.getMessage());
            e.printStackTrace();
            Resp resp = new Resp(RespCode.DEFAULT);
            resp.setCode(500);
            resp.setMsg("无效访问");
            resp.setData(e.getMessage());
            return resp;
        }
    }


    @RequestMapping(value = "dapingData"/*,method = RequestMethod.POST*/)
    @ResponseBody
    public Resp fcgjInterface(String startTime,String endTime) {
        Date date1 = null;
        Date date2 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try{
            if (startTime != null && startTime != "") {
                date1 = sdf.parse(startTime);
            }
            if (endTime != null && endTime != "") {
                date2 = sdf.parse(endTime);
            }
            List<Map<String, Object >> list = sjMedicalDrugsService.getMessageTotal(date1,date2);
            return new Resp(RespCode.SUCCESS,list);
        }catch (Exception e){
            logger.error(e.getMessage());
            e.printStackTrace();
            Resp resp = new Resp(RespCode.DEFAULT);
            resp.setCode(500);
            resp.setMsg("出错了");
            resp.setData(e.getMessage());
            return resp;
        }

    }
}
