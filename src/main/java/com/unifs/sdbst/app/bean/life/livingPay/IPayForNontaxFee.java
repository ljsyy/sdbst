package com.unifs.sdbst.app.bean.life.livingPay;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

/**
 * 非税缴费上送字段
 *
 */
@Data
public class IPayForNontaxFee extends Ipay{
    private String NoticeType;
    private String NoticeNo;
    //private String PayeeID;
    //private String PaperType ;
    //private String PayeeAcNo;
    //private String PayeeAcName;
    private String AcctNo;
   // private String BillAmount;
    //private String LateFee;
    private String TransAmount;
    //private String ProNumber;


    public JSONArray getList() {
        return null;
    }

    @Override
    public  String toString() {
        return "NoticeType=" + NoticeType +
                "|NoticeNo=" + NoticeNo + 
               // "|PayeeID=" + PayeeID +
               // "|PaperType=" + PaperType +
              //  "|PayeeAcNo=" + PayeeAcNo +
              //  "|PayeeAcName=" + PayeeAcName +
                "|AcctNo=" + AcctNo + 
                //"|BillAmount=" + BillAmount +
               // "|LateFee=" + LateFee +
                "|TransAmount=" + TransAmount ;
                //"|ProNumber=" + ProNumber;
    }
}
