package com.unifs.sdbst.app.bean.life.livingPay;

import lombok.Data;

/**
 * 农商行查询交易参数
 */
@Data
public class Agribusiness {
    private String LegalDepId;//法人机构Id
    private String PlatNo;//平台编号

    private String MerchantDateTime;//商户日期时间
    private String OrderId;//商户流水号
    private String MerchantId;//商户号
    private String TermCode;//终端号
    private String PayWaterFeeNo;//

    private String PayEleFeeAcNo;//缴费户号
    private String PayType;//欠费/预存标志(0/1) 0-缴费欠费 1-预存电费
    private String DistrictNo;//所属地区代码 120-顺德 121-恩平 122-南海 123-英德 660-高明 661-丰城

    private String NoticeNo;//通知单编号
    private String PayAmount;//缴存金额


    private String NoticeType;//通知书类型
    private String SchoolNo;
   // private String NoticeNo;//通知书编号


    private String StudentPayeeNo;//学生缴费编号


    private String queryUrl;

    private String payAddr;

    @Override
    public String toString() {
        return  removeNullField("LegalDepId=" + LegalDepId +
                "|PlatNo=" + PlatNo + 
                "|MerchantDateTime=" + MerchantDateTime + 
                "|OrderId=" + OrderId + 
                "|MerchantId=" + MerchantId + 
                "|TermCode=" + TermCode + 
                "|PayWaterFeeNo=" + PayWaterFeeNo + 
                "|PayEleFeeAcNo=" + PayEleFeeAcNo + 
                "|PayType=" + PayType + 
                "|DistrictNo=" + DistrictNo+
                "|NoticeType="+NoticeType+
                "|NoticeNo=" + NoticeNo+
                "|PayAmount=" + PayAmount+
                "|SchoolNo="+SchoolNo+
                "|StudentPayeeNo="+StudentPayeeNo);
    }

    private String removeNullField(String sourceStr){
        String[] split = sourceStr.split("\\|");
        String result = "";
        for(int i = 0; i < split.length; i++){
            if(split[i].contains("=null"))continue;
            if( i == split.length -1){
                return  result += split[i];
            }
            result += split[i] + "|";
        }
        return result.endsWith("|")?result.substring(0,result.length()-1):result;
    }
}
