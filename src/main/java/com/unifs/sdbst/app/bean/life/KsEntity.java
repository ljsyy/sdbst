package com.unifs.sdbst.app.bean.life;

public class KsEntity {
    private String ksh;

    private String xm;

    private String kmh;

    private String kmmc;

    private Long cj;

    private String csrq;

    public String getKsh() {
        return ksh;
    }

    public void setKsh(String ksh) {
        this.ksh = ksh == null ? null : ksh.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }

    public String getKmh() {
        return kmh;
    }

    public void setKmh(String kmh) {
        this.kmh = kmh == null ? null : kmh.trim();
    }

    public String getKmmc() {
        return kmmc;
    }

    public void setKmmc(String kmmc) {
        this.kmmc = kmmc == null ? null : kmmc.trim();
    }

    public Long getCj() {
        return cj;
    }

    public void setCj(Long cj) {
        this.cj = cj;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        this.csrq = csrq == null ? null : csrq.trim();
    }
}