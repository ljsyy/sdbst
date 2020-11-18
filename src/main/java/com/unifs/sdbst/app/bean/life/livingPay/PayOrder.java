package com.unifs.sdbst.app.bean.life.livingPay;

import lombok.Data;

import java.util.Date;

/**
 * 生活缴费
 */
@Data
public class PayOrder {
    private String id;
    private String jylx;//交易类型

    private String status;//状态

    private String time;//时 间 戳(yyyyMMddHHmmss)

    private String ddbh;//订单号

    private String sign;//签名

    private String sourceType;//缴费来源

    private Date createDate;//创建日期

    private String userId;

    private String preJylx;

    private String fee;

    private String platform; //支付方式 ，0：微信支付，1：支付宝支付

    private String client;//客户端 iso 或者 android

}
