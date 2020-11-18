package com.unifs.sdbst.app.bean.life.livingPay;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

@Data
public class IPayForEducationFee extends Ipay {
    private String  SchoolNo;
    private String StudentPayeeNo;
    private String StudentName;
    private String ChargeType;
    private String TransAmount;
    private String AcctNo;

    public JSONArray getList() {
        return null;
    }

    @Override
    public String toString() {
        return "SchoolNo=" + SchoolNo + 
                "|StudentPayeeNo=" + StudentPayeeNo + 
                "|StudentName=" + StudentName + 
                "|ChargeType=" + ChargeType + 
                "|TransAmount=" + TransAmount + 
                "|AcctNo=" + AcctNo;
    }
}
