package com.unifs.sdbst.app.service.life;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.sdcb.payment.client.SignatureService;
import com.unifs.sdbst.app.bean.life.livingPay.Agribusiness;
import com.unifs.sdbst.app.bean.life.livingPay.IMPay;
import com.unifs.sdbst.app.bean.life.livingPay.IPayForNontaxFee;
import com.unifs.sdbst.app.bean.life.livingPay.Ipay;
import com.unifs.sdbst.app.utils.DateUtils;
import com.unifs.sdbst.app.utils.HttpUtil;
import com.unifs.sdbst.app.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Set;

@Service("livingPayService")
public class LivingPayService {

    @Value("${agribusiness.merchantUrl}")
    private String merchantUrl;

    /**
     * 农商查询结果
     * @param agribusiness
     * @param queryType
     * @return
     */
    public String queryResult(Agribusiness agribusiness, String queryType) throws Exception{

        String dateStr = agribusiness.getMerchantDateTime();
        if("iWaterOweQuery".equals(queryType)){
            //缴交水费查询
            agribusiness.setOrderId("iw"+dateStr);//商户流水号
        }else if("iElePayQry".equals(queryType)){
            //缴交电费查询
            agribusiness.setOrderId("ie"+dateStr);//商户流水号
        }else if("iPayForNontaxFeeQry".equals(queryType)){
//                缴交非税查询
            agribusiness.setOrderId("inf"+dateStr);//商户流水号
        }else if("iSchoolQry".equals(queryType)){
            //缴交教育费-学校查询(iSchoolQry)
            agribusiness.setOrderId("is"+dateStr);//商户流水号
        }else if("iPayForEducationFeeQry".equals(queryType)){
            //缴交教育费查询
            agribusiness.setOrderId("ief"+dateStr);//商户流水号
        }else if("iPayRepairFundQry".equals(dateStr)){
            agribusiness.setOrderId("irf"+dateStr);//商户流水号
        }
        String plain = agribusiness.toString();
        //System.out.println("plain ："+plain);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("transName",queryType);
        jsonObject.put("Plain",plain);
        jsonObject.put("Signature", SignatureService.sign(plain));
        String s = jsonObject.toJSONString();
        //System.out.println(s);
        String result = HttpUtil.postJsonData(agribusiness.getQueryUrl(),s);
        //System.out.println("result : "+result);

        if(result.contains("Error")){
            return result;
        }
        JSONObject jsonObject1 = JSONObject.parseObject(result);
        jsonObject1.put("payAddr",agribusiness.getPayAddr());
        if("iPayForNontaxFeeQry".equals(queryType)){
            //计算交易金额 添加到返回值
            String Amount = (String) jsonObject1.get("Amount");
            String LateFee = (String) jsonObject1.get("LateFee");
            String TransAmount = "";
            if(StringUtils.isNullOrEmpty(Amount)){
                Amount = "0.00";
            }
            if(StringUtils.isNullOrEmpty(LateFee)){
                LateFee = "0.00";
            }
            if("0.00".equals(Amount) && "0.00".equals(LateFee)){
                TransAmount = "0.00";
            }else{
                BigDecimal b1 = new BigDecimal(Double.valueOf(Amount));
                BigDecimal b2 = new BigDecimal(Double.valueOf(LateFee));
                TransAmount = String.valueOf(b1.add(b2).doubleValue());
            }
            System.out.println(TransAmount);
            jsonObject1.put("TransAmount",TransAmount);
            jsonObject1.put("BillAmount",Amount);//票据金额 接口返回的Amount
        }else if("iWaterOweQuery".equals(queryType)){
            jsonObject1.put("TransAmount",jsonObject1.get("Amount"));
        }else if("iPayForEducationFeeQry".equals(queryType)){
            jsonObject1.put("TransAmount",jsonObject1.get("PayeeAmount"));
        }else if("iElePayQry".equals(queryType)){
           // JSONArray list  = (JSONArray)jsonObject1.get("List");
            jsonObject1.put("DistrictNo","120");
        }
        return jsonObject1.toJSONString();
    }

    /**
     * 组装自助缴费数据
     * @param jsonObject 查询结果数据
     * @param payType 查询类型
     * @return
     */
    public String getPayData(JSONObject jsonObject, String payType) throws Exception {

        //自助缴费参数
        IMPay imPay = new IMPay();
        imPay.setLegalDepId((String) jsonObject.get("LegalDepId"));
        imPay.setPlatNo((String) jsonObject.get("PlatNo"));
        String dateStr = DateUtils.formatDate(new Date(),"yyyyMMddHHmmss");
        imPay.setMerchantDateTime(dateStr);
        String OrderId = "sd"+dateStr;
        imPay.setOrderId(OrderId);
        String MerchantId = (String) jsonObject.get("MerchantId");
        imPay.setMerchantId(MerchantId);
        String TermCode = (String) jsonObject.get("TermCode");
        imPay.setTermCode(TermCode);
        //System.out.println("merchantUrl: -- "+merchantUrl);
        String MerchantUrl = URLEncoder.encode(merchantUrl,"UTF-8");//url 缴费后商户跳转地址 最长为200位 ，java.net.URLEncoder类进行编码
        imPay.setMerchantUrl(MerchantUrl);
        String TranAbbr = "MBER";
        imPay.setTranAbbr(TranAbbr);
        imPay.setPayTrsCode(payType);
        String Remark1="";
        if("iElePay".equals(payType)){
            Remark1="电费";
        }else if("iPayForNontaxFee".equals(payType)){
            Remark1="非税";
        }else if("iPayWaterFee".equals(payType)){
            Remark1="水费";
        }else if("iPayForEducationFee".equals(payType)){
            Remark1="教育费";
        }else if("iPayRepairFundQry".equals(payType)){
            Remark1="维修资金";
        }else{
            Remark1=payType;
        }
        imPay.setRemark1(Remark1);
        imPay.setTrsCode("iMPay");
        //组织上送字段
        String s = payFields(jsonObject,payType);
        return imPay.toString()+"|"+s;
    }

    /**
     * 上送字段
     * @param jsonObject
     * @return
     */
    private String payFields(JSONObject jsonObject,String className) throws Exception{
        Gson gson = new Gson();
        Class<?> aClass = Class.forName("com.unifs.sdbst.app.bean.life.livingPay." + className.substring(0,1).toUpperCase()+className.substring(1));
        //转换为上送字段的类
        Ipay ipay = (Ipay) gson.fromJson(jsonObject.toJSONString(), aClass);
        JSONArray list = ipay.getList();//若不用上送列表的，在子类处理
        if(!CollectionUtils.isEmpty(list)){
            String str = "";
            for(int i = 0; i < list.size(); i++){
                JSONObject jsonObject1 = list.getJSONObject(i);
                Set<String> keys = jsonObject1.keySet();
                String s = "";
                for(String key : keys){
                    s += key +"="+(String)jsonObject1.get(key)+"|";
                }
                str += s;
            }
            return ipay.toString()+"|"+(str.endsWith("|")?str.substring(0,str.length()-1):str);
        }
        return ipay.toString();
    }
}
