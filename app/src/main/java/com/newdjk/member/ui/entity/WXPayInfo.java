package com.newdjk.member.ui.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by EDZ on 2018/10/23.
 */

public class WXPayInfo {


    /**
     * appid : wx793c97279c12d9c0
     * noncestr : CCC0AA1B81BF81E16C676DDB977C5881
     * package : Sign=WXPay
     * partnerid : 1516233751
     * prepayid : wx23114346703275a22ee2ac243510159004
     * sign : 6A2672710142821E3AD12F1D8F1C0DBB
     * timestamp : 1540266227
     */

    private String appid;
    private String noncestr;
    @SerializedName("package")
    private String packageX;
    private String partnerid;
    private String prepayid;
    private String sign;
    private String timestamp;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
