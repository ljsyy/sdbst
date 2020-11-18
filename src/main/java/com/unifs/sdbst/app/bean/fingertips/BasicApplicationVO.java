package com.unifs.sdbst.app.bean.fingertips;

import lombok.Data;

@Data
public class BasicApplicationVO {

    private String agentCertificateNum ;//代理人证件号码 ,
    private String agentCertificateType ;// 代理人证件类型 ,
    private String agentName ;// 代理人姓名 ,
    private String agentPhone;// 代理人手机 ,
    private String applicantCertificateNum ; //申报对象证件号码 ,
    private String applicantCertificateType ;// 申报对象证件类型
    private String applicantName ;//申报对象名称 ,
    private String applicantType ;// 申请人类型 ,
    private String contactName ;// 联系人姓名 ,
    private String applicantPhone ;//联系人手机
    private boolean investProject ;// 是否投资项目 ,
    private String projectCode ;// 项目代码 ,
    private int projectId ;// 项目 id ,
    private int matterId ;// 事项 id ,
    private String memo;//备注 ,
    private String postcode ;// 申请人邮政编码
    private AddressVO addressVO;//通信地址 ,


//    private String agentCertificateNum ;//代理人证件号码 ,
//    private String agentCertificateType ;// 代理人证件类型 ,
//    private String agentName ;// 代理人姓名 ,
//    private String agentPhone;// 代理人手机 ,
//    private String applicantName ;//申报对象名称 ,
//    private String applicantCertificateNum ; //申报对象证件号码 ,
//    private String applicantCertificateType ;// 申报对象证件类型
//    private String applicantPhone;//申请人手机
//    private long matterId;//事项 id ,

}
