package com.newdjk.member.ui.entity;

import java.io.Serializable;

public class MonitorDataEntity implements Serializable {

    private int fhr1;//胎心 1
    private int fhr2;//胎心 2,
    private int toco;//宫缩,
    private int afm;//胎动曲线,
    private int fhrsign;//信号质量（冗余参数）,
    private int devicePower;////电池电量（冗余参数）
    private int afmcount;//自动胎动标记
    private int fmcount;//手动胎动标记
    private int charge;//充电状态（冗余参数）
    private int tocoreset;

    public MonitorDataEntity() {
        super();
    }

    public int getFhr1() {
        return fhr1;
    }

    public void setFhr1(int fhr1) {
        this.fhr1 = fhr1;
    }

    public int getFhr2() {
        return fhr2;
    }

    public void setFhr2(int fhr2) {
        this.fhr2 = fhr2;
    }

    public int getToco() {
        return toco;
    }

    public void setToco(int toco) {
        this.toco = toco;
    }

    public int getAfm() {
        return afm;
    }

    public void setAfm(int afm) {
        this.afm = afm;
    }


    public int getDevicePower() {
        return devicePower;
    }

    public void setDevicePower(int devicePower) {
        this.devicePower = devicePower;
    }


    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public int getTocoreset() {
        return tocoreset;
    }

    public void setTocoreset(int tocoreset) {
        this.tocoreset = tocoreset;
    }

    public int getFhrsign() {
        return fhrsign;
    }

    public void setFhrsign(int fhrsign) {
        this.fhrsign = fhrsign;
    }

    public int getAfmcount() {
        return afmcount;
    }

    public void setAfmcount(int afmcount) {
        this.afmcount = afmcount;
    }

    public int getFmcount() {
        return fmcount;
    }

    public void setFmcount(int fmcount) {
        this.fmcount = fmcount;
    }

}
