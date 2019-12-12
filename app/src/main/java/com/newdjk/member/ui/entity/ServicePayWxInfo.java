package com.newdjk.member.ui.entity;

import com.google.gson.annotations.SerializedName;

public class ServicePayWxInfo {


    /**
     * payInfo : {"appid":"wx62ecbd5cae5a1c30","noncestr":"303ED4C69846AB36C2904D3BA8573050","package":"Sign=WXPay","partnerid":"1519481201","prepayid":"wx301826562945860580cf44ed4126237022","sign":"696490A45E79CC986CCE119EBD900C60","timestamp":"1543573616","PayOrderNo":"20181130182656037516"}
     * price : 0.02
     * payChannel : 1
     */

    private PayInfoBean payInfo;
    private double price;
    private int payChannel;

    public PayInfoBean getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(PayInfoBean payInfo) {
        this.payInfo = payInfo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(int payChannel) {
        this.payChannel = payChannel;
    }

    public static class PayInfoBean {
        /**
         * appid : wx62ecbd5cae5a1c30
         * noncestr : 303ED4C69846AB36C2904D3BA8573050
         * package : Sign=WXPay
         * partnerid : 1519481201
         * prepayid : wx301826562945860580cf44ed4126237022
         * sign : 696490A45E79CC986CCE119EBD900C60
         * timestamp : 1543573616
         * PayOrderNo : 20181130182656037516
         */

        private String appid;
        private String noncestr;
        @SerializedName("package")
        private String packageX;
        private String partnerid;
        private String prepayid;
        private String sign;
        private String timestamp;
        private String PayOrderNo;

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

        public String getPayOrderNo() {
            return PayOrderNo;
        }

        public void setPayOrderNo(String PayOrderNo) {
            this.PayOrderNo = PayOrderNo;
        }
    }
}
