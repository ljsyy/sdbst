package com.unifs.sdbst.app.bean.department;

import com.unifs.sdbst.app.bean.menus.Menu;

import java.util.List;

public class DepartmentInfo {
    private static final long serialVersionUID = 1L;
    //部门id
    private String id;
    //部门类型
    private String departmentType;
    //部门名称
    private String departmentName;
    //部门简介
    private String departmentIntroduction;
    //部门职能
    private String departmentMechanism;
    //部门科室
    private String departmentOffice;
    //部门分工
    private String departmentLaborDivision;
    //联系电话
    private String contact;
    //其它联系方式
    private String otherContact;
    //联系地址
    private String address;

    public DepartmentInfo() {
    }

    public DepartmentInfo(String departmentIntroduction, String departmentMechanism, String contact, String address) {
        this.departmentIntroduction = departmentIntroduction;
        this.departmentMechanism = departmentMechanism;
        this.contact = contact;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(String departmentType) {
        this.departmentType = departmentType;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }
    public String getDepartmentIntroduction() {
        return departmentIntroduction;
    }

    public void setDepartmentIntroduction(String departmentIntroduction) {
        this.departmentIntroduction = departmentIntroduction;
    }

    public String getDepartmentMechanism() {
        return departmentMechanism;
    }

    public void setDepartmentMechanism(String departmentMechanism) {
        this.departmentMechanism = departmentMechanism;
    }


    public String getDepartmentOffice() {
        return departmentOffice;
    }

    public void setDepartmentOffice(String departmentOffice) {
        this.departmentOffice = departmentOffice;
    }

    public String getDepartmentLaborDivision() {
        return departmentLaborDivision;
    }

    public void setDepartmentLaborDivision(String departmentLaborDivision) {
        this.departmentLaborDivision = departmentLaborDivision;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getOtherContact() {
        return otherContact;
    }

    public void setOtherContact(String otherContact) {
        this.otherContact = otherContact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
