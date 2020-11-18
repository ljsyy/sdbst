package com.unifs.sdbst.app.bean.life.medical;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class HospitalClinic {
    private String id;

    private String name;

    private String idCode;

    private String sex;

    private String age;

    private String hospital;

    private String phone;

    private String fromWuhan;

    private String fromHubei;

    private String fromOther;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date illTime;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date doctorTime;

    private String diagnosis;

    private String temperature;

    private String respiratory;

    private String digestive;

    private String traffic;

    private String contacts;

    private String toGuangdong;

    private String clinic;

    private String noClinic;

    private Date insertDate;

    private String operNo;

    private String operName;

    private String col1;

    private String col2;

    private String col3;

    private String col4;

    private String col5;

    private String col6;

    private Date bakTime1;

    private Date bakTime2;

    private Date bakTime3;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode == null ? null : idCode.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age == null ? null : age.trim();
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital == null ? null : hospital.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFromWuhan() {
        return fromWuhan;
    }

    public void setFromWuhan(String fromWuhan) {
        this.fromWuhan = fromWuhan == null ? null : fromWuhan.trim();
    }

    public String getFromHubei() {
        return fromHubei;
    }

    public void setFromHubei(String fromHubei) {
        this.fromHubei = fromHubei == null ? null : fromHubei.trim();
    }

    public String getFromOther() {
        return fromOther;
    }

    public void setFromOther(String fromOther) {
        this.fromOther = fromOther == null ? null : fromOther.trim();
    }

    public Date getIllTime() {
        return illTime;
    }

    public void setIllTime(Date illTime) {
        this.illTime = illTime;
    }

    public Date getDoctorTime() {
        return doctorTime;
    }

    public void setDoctorTime(Date doctorTime) {
        this.doctorTime = doctorTime;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis == null ? null : diagnosis.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getRespiratory() {
        return respiratory;
    }

    public void setRespiratory(String respiratory) {
        this.respiratory = respiratory == null ? null : respiratory.trim();
    }

    public String getDigestive() {
        return digestive;
    }

    public void setDigestive(String digestive) {
        this.digestive = digestive == null ? null : digestive.trim();
    }

    public String getTraffic() {
        return traffic;
    }

    public void setTraffic(String traffic) {
        this.traffic = traffic == null ? null : traffic.trim();
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts == null ? null : contacts.trim();
    }

    public String getToGuangdong() {
        return toGuangdong;
    }

    public void setToGuangdong(String toGuangdong) {
        this.toGuangdong = toGuangdong == null ? null : toGuangdong.trim();
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic == null ? null : clinic.trim();
    }

    public String getNoClinic() {
        return noClinic;
    }

    public void setNoClinic(String noClinic) {
        this.noClinic = noClinic == null ? null : noClinic.trim();
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getOperNo() {
        return operNo;
    }

    public void setOperNo(String operNo) {
        this.operNo = operNo == null ? null : operNo.trim();
    }

    public String getOperName() {
        return operName;
    }

    public void setOperName(String operName) {
        this.operName = operName == null ? null : operName.trim();
    }

    public String getCol1() {
        return col1;
    }

    public void setCol1(String col1) {
        this.col1 = col1 == null ? null : col1.trim();
    }

    public String getCol2() {
        return col2;
    }

    public void setCol2(String col2) {
        this.col2 = col2 == null ? null : col2.trim();
    }

    public String getCol3() {
        return col3;
    }

    public void setCol3(String col3) {
        this.col3 = col3 == null ? null : col3.trim();
    }

    public String getCol4() {
        return col4;
    }

    public void setCol4(String col4) {
        this.col4 = col4 == null ? null : col4.trim();
    }

    public String getCol5() {
        return col5;
    }

    public void setCol5(String col5) {
        this.col5 = col5 == null ? null : col5.trim();
    }

    public String getCol6() {
        return col6;
    }

    public void setCol6(String col6) {
        this.col6 = col6 == null ? null : col6.trim();
    }

    public Date getBakTime1() {
        return bakTime1;
    }

    public void setBakTime1(Date bakTime1) {
        this.bakTime1 = bakTime1;
    }

    public Date getBakTime2() {
        return bakTime2;
    }

    public void setBakTime2(Date bakTime2) {
        this.bakTime2 = bakTime2;
    }

    public Date getBakTime3() {
        return bakTime3;
    }

    public void setBakTime3(Date bakTime3) {
        this.bakTime3 = bakTime3;
    }
}