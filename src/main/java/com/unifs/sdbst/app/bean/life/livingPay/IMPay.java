package com.unifs.sdbst.app.bean.life.livingPay;

import lombok.Data;

/**
 * 自助缴费
 */
@Data
public class IMPay {
    private String LegalDepId;
    private String PlatNo;
    private String MerchantDateTime;
    private String OrderId;
    private String MerchantId;
    private String TermCode;
    private String MerchantUrl;
    private String TranAbbr;
    private String PayTrsCode;
    private String Remark1;

    private String TrsCode;

    @Override
    public String toString() {
        return "LegalDepId=" + LegalDepId +
                "|PlatNo=" + PlatNo + 
                "|MerchantDateTime=" + MerchantDateTime + 
                "|OrderId=" + OrderId + 
                "|MerchantId=" + MerchantId + 
                "|TermCode=" + TermCode + 
                "|MerchantUrl=" + MerchantUrl + 
                "|TranAbbr=" + TranAbbr +
                "|PayTrsCode=" + PayTrsCode+
                "|Remark1=" + Remark1+
                "|TrsCode="+TrsCode;
    }
}
