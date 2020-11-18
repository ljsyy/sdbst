package com.unifs.sdbst.app.service.fingertips;

import com.alibaba.fastjson.JSONObject;
import com.unifs.sdbst.app.bean.fingertips.FingertipsEMS;
import com.unifs.sdbst.app.bean.fingertips.FingertipsRecord;
import com.unifs.sdbst.app.bean.fingertips.UserHistoryVO;
import com.unifs.sdbst.app.dao.primary.fingertips.FingertipsEMSMapper;
import com.unifs.sdbst.app.dao.primary.fingertips.FingertipsMapper;
import com.unifs.sdbst.app.dao.primary.fingertips.FingertipsRecordMapper;
import com.unifs.sdbst.app.utils.CreateEMSParamUtils;
import com.unifs.sdbst.app.utils.EMSHttpUtil;
import com.unifs.sdbst.app.utils.EncryptUtil;
import com.unifs.sdbst.app.utils.StringUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FingertipsService {
    @Autowired
    private FingertipsMapper fingertipsMapper;

    @Autowired
    private FingertipsEMSMapper fingertipsEMSMapper;

    @Autowired
    private FingertipsRecordMapper fingertipsRecordMapper;

    @Value("${ems.url}")
    private String EMS_URL;
    @Value("${ems.appkey}")
    private String EMS_APPKEY;
    @Value("${ems.appsecret}")
    private String EMS_APPSECRET;
    @Value("${ems.authorization}")
    private String EMS_authorization;

    @Value("${aes.key}")
    private String aesKey;

    public int findUserHistoryByPhone(UserHistoryVO userHistoryVO){
        return fingertipsMapper.findUserHistoryByPhone(userHistoryVO);
    }

    public int addUserHistory(UserHistoryVO userHistoryVO){
        return fingertipsMapper.addUserHistory(userHistoryVO);
    }

    public int updateUserHistoryByPhone(UserHistoryVO userHistoryVO){
        return fingertipsMapper.updateUserHistoryByPhone(userHistoryVO);
    }

    public UserHistoryVO findUserHistory(String phone){
        return fingertipsMapper.findUserHistory(phone);
    }

    public int findUserAddressCountByAgentPhone(Map<String,String> userAddress){
        return fingertipsMapper.findUserAddressCountByAgentPhone(userAddress);
    }

    public int updateUserAddress(Map<String,String> userAddress){
        return fingertipsMapper.updateUserAddress(userAddress);
    }

    public int addUserAddress(Map<String,String> userAddress){
        return fingertipsMapper.addUserAddress(userAddress);
    }

    public Map<String,String> findUserAddressByAgentPhone(String agentPhone){
        return fingertipsMapper.findUserAddressByAgentPhone(agentPhone);
    }

    private String DEFAULTCHARSET = "UTF-8";

    @Transactional
    public boolean createOrder(JSONObject gotInfos) {
//        int count = fingertipsEMSMapper.findByTxLogisticID(gotInfos.getString("txLogisticID"));
//        if(count > 0){
//            System.out.println("添加ems订单接口，txLogisticID不可重复："+gotInfos.getString("txLogisticID"));
//            return false;
//        }
//        String dePhoneEnc = gotInfos.getString("dePhoneEnc");
//        String matterId = gotInfos.getString("matterId");
//        String agentCertificateNum = gotInfos.getString("agentCertificateNum");
//        String agentName = gotInfos.getString("agentName");
//        String agentPhone = gotInfos.getString("agentPhone");
//        String caseId = gotInfos.getString("caseId");
//        gotInfos.remove("dePhoneEnc");
//        gotInfos.remove("matterId");
//        gotInfos.remove("agentCertificateNum");
//        gotInfos.remove("agentName");
//        gotInfos.remove("agentPhone");
//        gotInfos.remove("caseId");
        Map<String,String> params = new HashedMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String date = simpleDateFormat.format(new Date());

        //构造必要参数
        params.put("timestamp", date);
        params.put("version","V3.01");
        params.put("method","ems.inland.waybill.got");
        params.put("format","json");
        params.put("app_key",EMS_APPKEY);
        params.put("authorization",EMS_authorization);
        params.put("gotInfo",gotInfos.toJSONString());
        params.put("charset",DEFAULTCHARSET);

        //合成签名
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CreateEMSParamUtils.getSortParams(params));
        stringBuilder.append(EMS_APPSECRET);

        //签名加密
        String sign = CreateEMSParamUtils.sign(stringBuilder.toString(),DEFAULTCHARSET);
        params.put("sign",sign);
        //参数排列并转为字符串
        String body = CreateEMSParamUtils.toKey(params);
        String result = null;
        System.out.println(sign);
        System.out.println(body);
        try {
            //请求
            result = EMSHttpUtil.sendHttpPost(EMS_URL, body,DEFAULTCHARSET,null,"application/x-www-form-urlencoded; charset=UTF-8");
//            PointUtil.info("EMS上门取件结果："+result);
            System.out.println("EMS上门取件结果："+result);
            JSONObject resultObject = JSONObject.parseObject(result);
            if(resultObject.getString("success").equals("T")){
//                addEMSInfo(gotInfos,1);
                fingertipsEMSMapper.updateEMSCodeById(gotInfos.getString("txLogisticID"),"T");
//                FingertipsEMS fingertipsEMS = new FingertipsEMS();
//                fingertipsEMS.setId(gotInfos.getString("txLogisticID"));
//                fingertipsEMS.setCollects(gotInfos.getString("collect"));
//                fingertipsEMS.setSender(gotInfos.getString("sender"));
//                fingertipsEMS.setInsertDate(new Date());
//                fingertipsEMS.setReceiver(gotInfos.getString("receiver"));
//                fingertipsEMS.setRemark(gotInfos.getString("remark"));
//                fingertipsEMS.setPhone(EncryptUtil.aesDecrypt(dePhoneEnc,aesKey));
//                fingertipsEMS.setAgentCertificatenum(agentCertificateNum);
//                fingertipsEMS.setAgentName(agentName);
//                fingertipsEMS.setCaseId(caseId);
//                fingertipsEMS.setMatterId(matterId);
//                fingertipsEMS.setAgentPhone(agentPhone);
//                fingertipsEMSMapper.insert(fingertipsEMS);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    /**
     * 通过caseId查找ems
     * @param caseId
     * @return
     */
    public FingertipsEMS getFingertipsEMSByCaseId(String caseId){
       return fingertipsEMSMapper.getFingertipsEMSByCaseId(caseId);
    }

    /**
     * 更新mes信息
     * @param ems
     */
   public void updateEMS(FingertipsEMS ems){
        fingertipsEMSMapper.updateEMS(ems);
    }

    @Transactional
    public boolean createOrder2(JSONObject gotInfos) throws Exception {//插入记录，不掉ems
        String dePhoneEnc = gotInfos.getString("dePhoneEnc");
        String matterId = gotInfos.getString("matterId");
        String agentCertificateNum = gotInfos.getString("agentCertificateNum");
        String agentName = gotInfos.getString("agentName");
        String agentPhone = gotInfos.getString("agentPhone");
        String caseId = gotInfos.getString("caseId");
//        gotInfos.remove("dePhoneEnc");
//        gotInfos.remove("matterId");
//        gotInfos.remove("agentCertificateNum");
//        gotInfos.remove("agentName");
//        gotInfos.remove("agentPhone");
//        gotInfos.remove("caseId");

        FingertipsEMS fingertipsEMS = new FingertipsEMS();
        fingertipsEMS.setId(gotInfos.getString("txLogisticID"));
        fingertipsEMS.setCollects(gotInfos.getJSONObject("collect").toString());
        fingertipsEMS.setSender(gotInfos.getJSONObject("sender").toString());
        fingertipsEMS.setInsertDate(new Date());
        fingertipsEMS.setReceiver(gotInfos.getJSONObject("receiver").toString());
        fingertipsEMS.setRemark(gotInfos.getString("remark"));
        fingertipsEMS.setPhone(EncryptUtil.aesDecrypt(dePhoneEnc,aesKey));
        fingertipsEMS.setAgentCertificatenum(agentCertificateNum);
        fingertipsEMS.setAgentName(agentName);
        fingertipsEMS.setCaseId(caseId);
        fingertipsEMS.setMatterId(matterId);
        fingertipsEMS.setAgentPhone(agentPhone);
        fingertipsEMSMapper.insert(fingertipsEMS);

        return true;
    }

    @Transactional
    public boolean cancellation(JSONObject gotInfos) {
//        int count = emsWaybillMapper.findByTxLogisticID(gotInfos.getString("txLogisticID"));
//        if(count > 0){
//            PointUtil.info("添加ems订单接口，txLogisticID不可重复："+gotInfos.getString("txLogisticID"));
//            return false;
//        }
        Map<String,String> params = new HashedMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String date = simpleDateFormat.format(new Date());

        //构造必要参数
        params.put("timestamp", date);
        params.put("version","V3.01");
        params.put("method","ems.inland.waybill.got.cancellation");
        params.put("format","json");
        params.put("app_key",EMS_APPKEY);
        params.put("authorization",EMS_authorization);
        params.put("txLogisticID",gotInfos.getString("txLogisticID"));
        params.put("cancelCode",gotInfos.getString("cancelCode"));
        params.put("charset",DEFAULTCHARSET);

        //合成签名
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CreateEMSParamUtils.getSortParams(params));
        stringBuilder.append(EMS_APPSECRET);

        //签名加密
        String sign = CreateEMSParamUtils.sign(stringBuilder.toString(),DEFAULTCHARSET);
        params.put("sign",sign);
        //参数排列并转为字符串
        String body = CreateEMSParamUtils.toKey(params);
        String result = null;
        try {
            //请求
            result = EMSHttpUtil.sendHttpPost(EMS_URL, body,DEFAULTCHARSET,null,"application/x-www-form-urlencoded; charset=UTF-8");
//            PointUtil.info("EMS上门取件结果："+result);
            System.out.println("EMS撤单结果："+result);
            JSONObject resultObject = JSONObject.parseObject(result);
            if(resultObject.getString("success").equals("T")){
//                addEMSInfo(gotInfos,1);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }



    @Transactional
    public boolean reminder(JSONObject gotInfos) {
//        int count = emsWaybillMapper.findByTxLogisticID(gotInfos.getString("txLogisticID"));
//        if(count > 0){
//            PointUtil.info("添加ems订单接口，txLogisticID不可重复："+gotInfos.getString("txLogisticID"));
//            return false;
//        }
        Map<String,String> params = new HashedMap();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        String date = simpleDateFormat.format(new Date());

        //构造必要参数
        params.put("timestamp", date);
        params.put("version","V3.01");
        params.put("method","ems.inland.waybill.got.reminder");
        params.put("format","json");
        params.put("app_key",EMS_APPKEY);
        params.put("authorization",EMS_authorization);
        params.put("txLogisticID",gotInfos.getString("txLogisticID"));
        params.put("charset",DEFAULTCHARSET);

        //合成签名
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CreateEMSParamUtils.getSortParams(params));
        stringBuilder.append(EMS_APPSECRET);

        //签名加密
        String sign = CreateEMSParamUtils.sign(stringBuilder.toString(),DEFAULTCHARSET);
        params.put("sign",sign);
        //参数排列并转为字符串
        String body = CreateEMSParamUtils.toKey(params);
        String result = null;
        try {
            //请求
            result = EMSHttpUtil.sendHttpPost(EMS_URL, body,DEFAULTCHARSET,null,"application/x-www-form-urlencoded; charset=UTF-8");
//            PointUtil.info("EMS上门取件结果："+result);
            System.out.println("EMS催单结果："+result);
            JSONObject resultObject = JSONObject.parseObject(result);
            if(resultObject.getString("success").equals("T")){
//                addEMSInfo(gotInfos,1);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

//    @Transactional
//    public boolean statuspush(JSONObject gotInfos) {
////        int count = emsWaybillMapper.findByTxLogisticID(gotInfos.getString("txLogisticID"));
////        if(count > 0){
////            PointUtil.info("添加ems订单接口，txLogisticID不可重复："+gotInfos.getString("txLogisticID"));
////            return false;
////        }
//        Map<String,String> params = new HashedMap();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
//        String date = simpleDateFormat.format(new Date());
//
//        //构造必要参数
//        params.put("timestamp", gotInfos.getString("timestamp"));
//        params.put("version",gotInfos.getString("version"));
//        params.put("method",gotInfos.getString("method"));
//        params.put("format",gotInfos.getString("format"));
//        params.put("app_key",gotInfos.getString("app_key"));
//        params.put("authorization",gotInfos.getString("authorization"));
//        params.put("txLogisticID",gotInfos.getString("txLogisticID"));
//        params.put("charset",gotInfos.getString("charset"));
//
//        //合成签名
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(CreateEMSParamUtils.getSortParams(params));
//        stringBuilder.append("45a9fdfbea2b22d8cbdf559f419d69ba");
//
//        String charset = DEFAULTCHARSET;
//        if (!StringUtils.isNullOrEmpty(gotInfos.getString("charset"))){
//            charset = gotInfos.getString("charset");
//        }
//        //签名加密
//        String sign = CreateEMSParamUtils.sign(stringBuilder.toString(),charset);
//        params.put("sign",sign);
//        //参数排列并转为字符串
//        String body = CreateEMSParamUtils.toKey(params);
//        String result = null;
//        try {
//            //请求
//            result = EMSHttpUtil.sendHttpPost("http://60.205.8.187:18001/api/gateway", body,DEFAULTCHARSET,null,"application/x-www-form-urlencoded; charset=UTF-8");
////            PointUtil.info("EMS上门取件结果："+result);
//            System.out.println("EMS催单结果："+result);
//            JSONObject resultObject = JSONObject.parseObject(result);
//            if(resultObject.getString("success").equals("T")){
////                addEMSInfo(gotInfos,1);
//                return true;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//        return false;
//    }

    public void updateEMSById(String txLogisticID, String status, String desc, String mailNum,Date updateDate) {
        fingertipsEMSMapper.updateEMSById(txLogisticID,status,desc,mailNum,updateDate);
    }

    public void insertFingertipsRecord(FingertipsRecord fingertipsRecord) {
        if (StringUtils.isNullOrEmpty(fingertipsRecord.getId())){
            fingertipsRecord.setId(UUID.randomUUID().toString().replaceAll("-",""));
        }
        if (fingertipsRecord.getInsertTime() == null){
            fingertipsRecord.setInsertTime(new Date());
        }
        fingertipsRecordMapper.insert(fingertipsRecord);
    }

    public List<FingertipsEMS> getFingertipsEMSByNotEms() {
        return fingertipsEMSMapper.getFingertipsEMSByNotEms();
    }

    public void updateStatusById(FingertipsEMS fingertipsEMS){
        fingertipsEMSMapper.updateStatusById(fingertipsEMS);
    }
}
