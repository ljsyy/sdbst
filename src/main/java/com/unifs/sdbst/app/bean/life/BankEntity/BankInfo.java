package com.unifs.sdbst.app.bean.life.BankEntity;

public class BankInfo {
    private String id;

    private String bankName;

    private String bankAddress;

    private String bankPhone;

    private String bankLatitude;

    private String bankLongitude;

    private double longx;

    private String bankWintoPub;

    private String bankRealEstateReg;

    private String bankCarMortgageRelease;

    public double getLongx() {
        return longx;
    }

    public void setLongx(double longx) {
        this.longx = longx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress == null ? null : bankAddress.trim();
    }

    public String getBankPhone() {
        return bankPhone;
    }

    public void setBankPhone(String bankPhone) {
        this.bankPhone = bankPhone == null ? null : bankPhone.trim();
    }

    public String getBankLatitude() {
        return bankLatitude;
    }

    public void setBankLatitude(String bankLatitude) {
        this.bankLatitude = bankLatitude == null ? null : bankLatitude.trim();
    }

    public String getBankLongitude() {
        return bankLongitude;
    }

    public void setBankLongitude(String bankLongitude) {
        this.bankLongitude = bankLongitude == null ? null : bankLongitude.trim();
    }

    public String getBankWintoPub() {
        return bankWintoPub;
    }

    public void setBankWintoPub(String bankWintoPub) {
        this.bankWintoPub = bankWintoPub;
    }

    public String getBankRealEstateReg() {
        return bankRealEstateReg;
    }

    public void setBankRealEstateReg(String bankRealEstateReg) {
        this.bankRealEstateReg = bankRealEstateReg;
    }

    public String getBankCarMortgageRelease() {
        return bankCarMortgageRelease;
    }

    public void setBankCarMortgageRelease(String bankCarMortgageRelease) {
        this.bankCarMortgageRelease = bankCarMortgageRelease;
    }
}
