package com.unifs.sdbst.app.bean.life.medical;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class InfoRecord {
    private String id;

    private Object name;

    private String siteCode;

    private String carNumber;

    private String phone;

    private String card;

    private String sex;

    private String age;

    private String address;

    private String temperature;

    private String name2;

    private String phone2;

    private String content;

    private String work;

    private String workCharge;

    private String workPhone;
    @JsonFormat(timezone = "GMT+8", pattern = "HH:mm:ss")
    private Date insertDate;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date returnDate;

    private String toSd;

    private String closePerson;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date resplonTime;

    private String policeOperno;

    private String hospital;

    private String receivePerson;

    private String receivePhone;

    private String townId;

    private String townName;

    private String town;

    private String fromPlace;

    private String retrunPlace;

    private String place;

    private String person;

    private String operno;

    private String tell;

    private String carNum;

    private String col1;

    private String col2;

    private String col3;

    private String col4;

    private String col5;

    private String col6;

    private String col7;

    private String col8;

    private String col9;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date policeDate;

    private Date cunDate;

    private String benOwn;

    private String townNew;

    private String townNum;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date townDate;

    private String zhejiang;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date zjDate;

    private String col10;

    private String col11;

    private String col12;

    private Date time1;

    private Date time2;

    private Date time3;

    private String department;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date departDate;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode == null ? null : siteCode.trim();
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber == null ? null : carNumber.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card == null ? null : card.trim();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature == null ? null : temperature.trim();
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2 == null ? null : name2.trim();
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2 == null ? null : phone2.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work == null ? null : work.trim();
    }

    public String getWorkCharge() {
        return workCharge;
    }

    public void setWorkCharge(String workCharge) {
        this.workCharge = workCharge == null ? null : workCharge.trim();
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone == null ? null : workPhone.trim();
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getToSd() {
        return toSd;
    }

    public void setToSd(String toSd) {
        this.toSd = toSd == null ? null : toSd.trim();
    }

    public String getClosePerson() {
        return closePerson;
    }

    public void setClosePerson(String closePerson) {
        this.closePerson = closePerson == null ? null : closePerson.trim();
    }

    public Date getResplonTime() {
        return resplonTime;
    }

    public void setResplonTime(Date resplonTime) {
        this.resplonTime = resplonTime;
    }

    public String getPoliceOperno() {
        return policeOperno;
    }

    public void setPoliceOperno(String policeOperno) {
        this.policeOperno = policeOperno == null ? null : policeOperno.trim();
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital == null ? null : hospital.trim();
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson == null ? null : receivePerson.trim();
    }

    public String getReceivePhone() {
        return receivePhone;
    }

    public void setReceivePhone(String receivePhone) {
        this.receivePhone = receivePhone == null ? null : receivePhone.trim();
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId == null ? null : townId.trim();
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName == null ? null : townName.trim();
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town == null ? null : town.trim();
    }

    public String getFromPlace() {
        return fromPlace;
    }

    public void setFromPlace(String fromPlace) {
        this.fromPlace = fromPlace == null ? null : fromPlace.trim();
    }

    public String getRetrunPlace() {
        return retrunPlace;
    }

    public void setRetrunPlace(String retrunPlace) {
        this.retrunPlace = retrunPlace == null ? null : retrunPlace.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person == null ? null : person.trim();
    }

    public String getOperno() {
        return operno;
    }

    public void setOperno(String operno) {
        this.operno = operno == null ? null : operno.trim();
    }

    public String getTell() {
        return tell;
    }

    public void setTell(String tell) {
        this.tell = tell == null ? null : tell.trim();
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum == null ? null : carNum.trim();
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

    public String getCol7() {
        return col7;
    }

    public void setCol7(String col7) {
        this.col7 = col7 == null ? null : col7.trim();
    }

    public String getCol8() {
        return col8;
    }

    public void setCol8(String col8) {
        this.col8 = col8 == null ? null : col8.trim();
    }

    public String getCol9() {
        return col9;
    }

    public void setCol9(String col9) {
        this.col9 = col9 == null ? null : col9.trim();
    }

    public Date getPoliceDate() {
        return policeDate;
    }

    public void setPoliceDate(Date policeDate) {
        this.policeDate = policeDate;
    }

    public Date getCunDate() {
        return cunDate;
    }

    public void setCunDate(Date cunDate) {
        this.cunDate = cunDate;
    }

    public String getBenOwn() {
        return benOwn;
    }

    public void setBenOwn(String benOwn) {
        this.benOwn = benOwn == null ? null : benOwn.trim();
    }

    public String getTownNew() {
        return townNew;
    }

    public void setTownNew(String townNew) {
        this.townNew = townNew == null ? null : townNew.trim();
    }

    public String getTownNum() {
        return townNum;
    }

    public void setTownNum(String townNum) {
        this.townNum = townNum == null ? null : townNum.trim();
    }

    public Date getTownDate() {
        return townDate;
    }

    public void setTownDate(Date townDate) {
        this.townDate = townDate;
    }

    public String getZhejiang() {
        return zhejiang;
    }

    public void setZhejiang(String zhejiang) {
        this.zhejiang = zhejiang == null ? null : zhejiang.trim();
    }

    public Date getZjDate() {
        return zjDate;
    }

    public void setZjDate(Date zjDate) {
        this.zjDate = zjDate;
    }

    public String getCol10() {
        return col10;
    }

    public void setCol10(String col10) {
        this.col10 = col10 == null ? null : col10.trim();
    }

    public String getCol11() {
        return col11;
    }

    public void setCol11(String col11) {
        this.col11 = col11 == null ? null : col11.trim();
    }

    public String getCol12() {
        return col12;
    }

    public void setCol12(String col12) {
        this.col12 = col12 == null ? null : col12.trim();
    }

    public Date getTime1() {
        return time1;
    }

    public void setTime1(Date time1) {
        this.time1 = time1;
    }

    public Date getTime2() {
        return time2;
    }

    public void setTime2(Date time2) {
        this.time2 = time2;
    }

    public Date getTime3() {
        return time3;
    }

    public void setTime3(Date time3) {
        this.time3 = time3;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }
}