package com.unifs.sdbst.app.bean.life.livingPay;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * 水费缴费上送字段
 */
@Data
public class IPayWaterFee extends Ipay {
    private String AreaNo;
    private String AcctNo;
    private String PayWaterFeeNo;
    private String TransAmount;

    public JSONArray getList() {
        return null;
    }

    @Override
    public String toString() {
        return "AreaNo=" + AreaNo +
                "|AcctNo=" + AcctNo + 
                "|PayWaterFeeNo=" + PayWaterFeeNo + 
                "|TransAmount=" + TransAmount;
    }
}
