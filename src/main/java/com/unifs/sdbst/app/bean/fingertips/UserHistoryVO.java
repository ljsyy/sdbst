package com.unifs.sdbst.app.bean.fingertips;

public class UserHistoryVO {
    private static final long serialVersionUID = 1L;
    private String id;
    private String agentCertificateNum;//代理人证件号码
    private String agentName;//代理人姓名
    private String agentPhone;//代理人手机
    private String applicantType;//申请人类型  1自然人 2企业
    private String applicantName;//申报对象名称
    private String applicantCertificateNum;//申报对象证件号码
    private String applicantPhone;//申请人手机
    private String enApplicantName;//企业名称
    private String enApplicantCertificateNum;//企业证件
    private String enApplicantPhone;//企业联系电话


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgentCertificateNum() {
        return agentCertificateNum;
    }

    public void setAgentCertificateNum(String agentCertificateNum) {
        this.agentCertificateNum = agentCertificateNum;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentPhone() {
        return agentPhone;
    }

    public void setAgentPhone(String agentPhone) {
        this.agentPhone = agentPhone;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getApplicantCertificateNum() {
        return applicantCertificateNum;
    }

    public void setApplicantCertificateNum(String applicantCertificateNum) {
        this.applicantCertificateNum = applicantCertificateNum;
    }

    public String getApplicantPhone() {
        return applicantPhone;
    }

    public void setApplicantPhone(String applicantPhone) {
        this.applicantPhone = applicantPhone;
    }

    public String getApplicantType() {
        return applicantType;
    }

    public void setApplicantType(String applicantType) {
        this.applicantType = applicantType;
    }

    public String getEnApplicantName() {
        return enApplicantName;
    }

    public void setEnApplicantName(String enApplicantName) {
        this.enApplicantName = enApplicantName;
    }

    public String getEnApplicantCertificateNum() {
        return enApplicantCertificateNum;
    }

    public void setEnApplicantCertificateNum(String enApplicantCertificateNum) {
        this.enApplicantCertificateNum = enApplicantCertificateNum;
    }

    public String getEnApplicantPhone() {
        return enApplicantPhone;
    }

    public void setEnApplicantPhone(String enApplicantPhone) {
        this.enApplicantPhone = enApplicantPhone;
    }
}
