package com.unifs.sdbst.app.bean.life.medical;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class PoliceCheck {
    private String systemid;

    private String townId;

    private String townName;

    private String siteId;

    private String siteName;

    private String carNumber;

    private String name;

    private String phone;

    private String idCard;

    private String age;

    private String sex;

    private String fromHubei;

    private String fromWenzhou;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;

    private String closePerson;

    private String address;

    private String temperature;

    private String otherName;

    private String otherPhone;

    private String chargeSd;

    private String street;

    private String country;

    private String school;

    private String contactPerson;

    private String contactPhone;

    private String content;

    private Date insertDate;

    private String authorization;

    private String timestamp;

    private String col1;

    private String col2;

    private String col3;

    private Date date1;

    private Date date2;

    private Date date3;

    private String schoolType;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date sccjsj;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date txrq;

    private String zjzl;

    private String mz;

    private String opermeasure;

    private String jcs1;

    private String jcs1xxqk;

    private String jcs2;

    private String jcs2xxqk;

    private String sffr;

    private String zgtw;

    private String sfqtzz;

    private String sfgrbd;

    private String qtzzxl;

    private String gjclcs;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date rq1;

    private String hsd1;

    private String jsd1;

    private String jsr1lxdh;

    private String jsr1xm;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date rq2;

    private String hsd2;

    private String jsd2;

    private String jsr2xm;

    private String jsr2lxdh;

    private String ysjsdw;

    private String gh;

    private String ysjsr;

    private String cph;

    private String jsrlxfs;

    private String ywryqm;

    private String ywrydh;

    private String dbmjqm;

    private String zbmjdh;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zbmjqrrq;

    private String sfbh;

    private String bz;

    private String zxzt;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date djsj;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date xgsj;

    private String djrId;

    private String xgrId;

    private String cfd;

    private String mdd;

    private String appCode;

    private String appName;

    private String remark1;

    private String remark2;

    private String remark3;

    private String remark4;

    private String remark5;

    private String remark6;

    private String remark7;

    private String remark8;

    private String remark9;

    private String remark10;

    private String remark11;

    private String remark12;

    private String remark13;

    private String remark14;

    private String remark15;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date remarkDate1;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date remarkDate2;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date remarkDate3;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date remarkDate4;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date remarkDate5;

    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date zzsbsj;


    private String rylb;

    private String lhjyzdlx;


    public Date getZzsbsj() {
        return zzsbsj;
    }

    public void setZzsbsj(Date zzsbsj) {
        this.zzsbsj = zzsbsj;
    }

    public String getRylb() {
        return rylb;
    }

    public void setRylb(String rylb) {
        this.rylb = rylb;
    }

    public String getLhjyzdlx() {
        return lhjyzdlx;
    }

    public void setLhjyzdlx(String lhjyzdlx) {
        this.lhjyzdlx = lhjyzdlx;
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getSystemid() {
        return systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFromHubei() {
        return fromHubei;
    }

    public void setFromHubei(String fromHubei) {
        this.fromHubei = fromHubei;
    }

    public String getFromWenzhou() {
        return fromWenzhou;
    }

    public void setFromWenzhou(String fromWenzhou) {
        this.fromWenzhou = fromWenzhou;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String getClosePerson() {
        return closePerson;
    }

    public void setClosePerson(String closePerson) {
        this.closePerson = closePerson;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getOtherPhone() {
        return otherPhone;
    }

    public void setOtherPhone(String otherPhone) {
        this.otherPhone = otherPhone;
    }

    public String getChargeSd() {
        return chargeSd;
    }

    public void setChargeSd(String chargeSd) {
        this.chargeSd = chargeSd;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1;
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2;
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public String getSchoolType() {
        return schoolType;
    }

    public void setSchoolType(String schoolType) {
        this.schoolType = schoolType;
    }

    public Date getSccjsj() {
        return sccjsj;
    }

    public void setSccjsj(Date sccjsj) {
        this.sccjsj = sccjsj;
    }

    public Date getTxrq() {
        return txrq;
    }

    public void setTxrq(Date txrq) {
        this.txrq = txrq;
    }

    public String getZjzl() {
        return zjzl;
    }

    public void setZjzl(String zjzl) {
        this.zjzl = zjzl;
    }

    public String getMz() {
        return mz;
    }

    public void setMz(String mz) {
        this.mz = mz;
    }

    public String getOpermeasure() {
        return opermeasure;
    }

    public void setOpermeasure(String opermeasure) {
        this.opermeasure = opermeasure;
    }

    public String getJcs1() {
        return jcs1;
    }

    public void setJcs1(String jcs1) {
        this.jcs1 = jcs1;
    }

    public String getJcs1xxqk() {
        return jcs1xxqk;
    }

    public void setJcs1xxqk(String jcs1xxqk) {
        this.jcs1xxqk = jcs1xxqk;
    }

    public String getJcs2() {
        return jcs2;
    }

    public void setJcs2(String jcs2) {
        this.jcs2 = jcs2;
    }

    public String getJcs2xxqk() {
        return jcs2xxqk;
    }

    public void setJcs2xxqk(String jcs2xxqk) {
        this.jcs2xxqk = jcs2xxqk;
    }

    public String getSffr() {
        return sffr;
    }

    public void setSffr(String sffr) {
        this.sffr = sffr;
    }

    public String getZgtw() {
        return zgtw;
    }

    public void setZgtw(String zgtw) {
        this.zgtw = zgtw;
    }

    public String getSfqtzz() {
        return sfqtzz;
    }

    public void setSfqtzz(String sfqtzz) {
        this.sfqtzz = sfqtzz;
    }

    public String getSfgrbd() {
        return sfgrbd;
    }

    public void setSfgrbd(String sfgrbd) {
        this.sfgrbd = sfgrbd;
    }

    public String getQtzzxl() {
        return qtzzxl;
    }

    public void setQtzzxl(String qtzzxl) {
        this.qtzzxl = qtzzxl;
    }

    public String getGjclcs() {
        return gjclcs;
    }

    public void setGjclcs(String gjclcs) {
        this.gjclcs = gjclcs;
    }

    public Date getRq1() {
        return rq1;
    }

    public void setRq1(Date rq1) {
        this.rq1 = rq1;
    }

    public String getHsd1() {
        return hsd1;
    }

    public void setHsd1(String hsd1) {
        this.hsd1 = hsd1;
    }

    public String getJsd1() {
        return jsd1;
    }

    public void setJsd1(String jsd1) {
        this.jsd1 = jsd1;
    }

    public String getJsr1lxdh() {
        return jsr1lxdh;
    }

    public void setJsr1lxdh(String jsr1lxdh) {
        this.jsr1lxdh = jsr1lxdh;
    }

    public String getJsr1xm() {
        return jsr1xm;
    }

    public void setJsr1xm(String jsr1xm) {
        this.jsr1xm = jsr1xm;
    }

    public Date getRq2() {
        return rq2;
    }

    public void setRq2(Date rq2) {
        this.rq2 = rq2;
    }

    public String getHsd2() {
        return hsd2;
    }

    public void setHsd2(String hsd2) {
        this.hsd2 = hsd2;
    }

    public String getJsd2() {
        return jsd2;
    }

    public void setJsd2(String jsd2) {
        this.jsd2 = jsd2;
    }

    public String getJsr2xm() {
        return jsr2xm;
    }

    public void setJsr2xm(String jsr2xm) {
        this.jsr2xm = jsr2xm;
    }

    public String getJsr2lxdh() {
        return jsr2lxdh;
    }

    public void setJsr2lxdh(String jsr2lxdh) {
        this.jsr2lxdh = jsr2lxdh;
    }

    public String getYsjsdw() {
        return ysjsdw;
    }

    public void setYsjsdw(String ysjsdw) {
        this.ysjsdw = ysjsdw;
    }

    public String getGh() {
        return gh;
    }

    public void setGh(String gh) {
        this.gh = gh;
    }

    public String getYsjsr() {
        return ysjsr;
    }

    public void setYsjsr(String ysjsr) {
        this.ysjsr = ysjsr;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getJsrlxfs() {
        return jsrlxfs;
    }

    public void setJsrlxfs(String jsrlxfs) {
        this.jsrlxfs = jsrlxfs;
    }

    public String getYwryqm() {
        return ywryqm;
    }

    public void setYwryqm(String ywryqm) {
        this.ywryqm = ywryqm;
    }

    public String getYwrydh() {
        return ywrydh;
    }

    public void setYwrydh(String ywrydh) {
        this.ywrydh = ywrydh;
    }

    public String getDbmjqm() {
        return dbmjqm;
    }

    public void setDbmjqm(String dbmjqm) {
        this.dbmjqm = dbmjqm;
    }

    public String getZbmjdh() {
        return zbmjdh;
    }

    public void setZbmjdh(String zbmjdh) {
        this.zbmjdh = zbmjdh;
    }

    public Date getZbmjqrrq() {
        return zbmjqrrq;
    }

    public void setZbmjqrrq(Date zbmjqrrq) {
        this.zbmjqrrq = zbmjqrrq;
    }

    public String getSfbh() {
        return sfbh;
    }

    public void setSfbh(String sfbh) {
        this.sfbh = sfbh;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getZxzt() {
        return zxzt;
    }

    public void setZxzt(String zxzt) {
        this.zxzt = zxzt;
    }

    public Date getDjsj() {
        return djsj;
    }

    public void setDjsj(Date djsj) {
        this.djsj = djsj;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public String getDjrId() {
        return djrId;
    }

    public void setDjrId(String djrId) {
        this.djrId = djrId;
    }

    public String getXgrId() {
        return xgrId;
    }

    public void setXgrId(String xgrId) {
        this.xgrId = xgrId;
    }

    public String getCfd() {
        return cfd;
    }

    public void setCfd(String cfd) {
        this.cfd = cfd;
    }

    public String getMdd() {
        return mdd;
    }

    public void setMdd(String mdd) {
        this.mdd = mdd;
    }


    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1 == null ? null : remark1.trim();
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2 == null ? null : remark2.trim();
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3 == null ? null : remark3.trim();
    }

    public String getRemark4() {
        return remark4;
    }

    public void setRemark4(String remark4) {
        this.remark4 = remark4 == null ? null : remark4.trim();
    }

    public String getRemark5() {
        return remark5;
    }

    public void setRemark5(String remark5) {
        this.remark5 = remark5 == null ? null : remark5.trim();
    }

    public String getRemark6() {
        return remark6;
    }

    public void setRemark6(String remark6) {
        this.remark6 = remark6 == null ? null : remark6.trim();
    }

    public String getRemark7() {
        return remark7;
    }

    public void setRemark7(String remark7) {
        this.remark7 = remark7 == null ? null : remark7.trim();
    }

    public String getRemark8() {
        return remark8;
    }

    public void setRemark8(String remark8) {
        this.remark8 = remark8 == null ? null : remark8.trim();
    }

    public String getRemark9() {
        return remark9;
    }

    public void setRemark9(String remark9) {
        this.remark9 = remark9 == null ? null : remark9.trim();
    }

    public String getRemark10() {
        return remark10;
    }

    public void setRemark10(String remark10) {
        this.remark10 = remark10 == null ? null : remark10.trim();
    }

    public String getRemark11() {
        return remark11;
    }

    public void setRemark11(String remark11) {
        this.remark11 = remark11 == null ? null : remark11.trim();
    }

    public String getRemark12() {
        return remark12;
    }

    public void setRemark12(String remark12) {
        this.remark12 = remark12 == null ? null : remark12.trim();
    }

    public String getRemark13() {
        return remark13;
    }

    public void setRemark13(String remark13) {
        this.remark13 = remark13 == null ? null : remark13.trim();
    }

    public String getRemark14() {
        return remark14;
    }

    public void setRemark14(String remark14) {
        this.remark14 = remark14 == null ? null : remark14.trim();
    }

    public String getRemark15() {
        return remark15;
    }

    public void setRemark15(String remark15) {
        this.remark15 = remark15 == null ? null : remark15.trim();
    }

    public Date getRemarkDate1() {
        return remarkDate1;
    }

    public void setRemarkDate1(Date remarkDate1) {
        this.remarkDate1 = remarkDate1;
    }

    public Date getRemarkDate2() {
        return remarkDate2;
    }

    public void setRemarkDate2(Date remarkDate2) {
        this.remarkDate2 = remarkDate2;
    }

    public Date getRemarkDate3() {
        return remarkDate3;
    }

    public void setRemarkDate3(Date remarkDate3) {
        this.remarkDate3 = remarkDate3;
    }

    public Date getRemarkDate4() {
        return remarkDate4;
    }

    public void setRemarkDate4(Date remarkDate4) {
        this.remarkDate4 = remarkDate4;
    }

    public Date getRemarkDate5() {
        return remarkDate5;
    }

    public void setRemarkDate5(Date remarkDate5) {
        this.remarkDate5 = remarkDate5;
    }
}