package com.unifs.sdbst.app.bean.life.livingPay;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

@Data
public class IElePay extends Ipay {
    private String PayType;
    private String AcctNo;
    private String TransAmount;
    private String PayEleFeeAcNo;
    private String DistrictNo;//DistrictNo


    public JSONArray getList() {
        return null;
    }

    @Override
    public String toString() {
        return "PayType=" + PayType +
                "|AcctNo=" + AcctNo + 
                "|TransAmount=" + TransAmount + 
                "|PayEleFeeAcNo=" + PayEleFeeAcNo+
                "|DistrictNo="+DistrictNo;
    }
}
