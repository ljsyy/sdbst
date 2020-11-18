package com.unifs.sdbst.app.bean.enterprise;

import java.io.Serializable;

public class EnterpriseUser implements Serializable {
//  企业信用代码
    private String creditCode;
//  企业法人身份证号
    private String identityNumber;
//  企业名
    private String name;
//  企业类型
    private String type;
//  企业地址
    private String address;
//  参保人数
    private Integer insuredNumber;

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber == null ? null : identityNumber.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getInsuredNumber() {
        return insuredNumber;
    }

    public void setInsuredNumber(Integer insuredNumber) {
        this.insuredNumber = insuredNumber;
    }

    @Override
    public String toString() {
        return "EnterpriseUser{" +
                "creditCode='" + creditCode + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", address='" + address + '\'' +
                ", insuredNumber=" + insuredNumber +
                '}';
    }
}
