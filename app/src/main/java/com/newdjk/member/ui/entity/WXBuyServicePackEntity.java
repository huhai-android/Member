package com.newdjk.member.ui.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WXBuyServicePackEntity {

    /**
     * PayOrderNo : 20181107170913417532
     * Code : 0
     * Message : 支付提交成功
     * Data : {"appid":"wx793c97279c12d9c0","noncestr":"8E296A067A37563370DED05F5A3BF3EC","package":"Sign=WXPay","partnerid":"1516233751","prepayid":"wx071709136236167ef3b072cb0043618141","sign":"BA1CAE38719D6124F3CCAA3D038A610A","timestamp":"1541581754"}
     */

    private String PayOrderNo;
    private int Code;
    private String Message;
    private DataBean Data;

    public String getPayOrderNo() {
        return PayOrderNo;
    }

    public void setPayOrderNo(String PayOrderNo) {
        this.PayOrderNo = PayOrderNo;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean implements Serializable {
        /**
         * appid : wx793c97279c12d9c0
         * noncestr : 8E296A067A37563370DED05F5A3BF3EC
         * package : Sign=WXPay
         * partnerid : 1516233751
         * prepayid : wx071709136236167ef3b072cb0043618141
         * sign : BA1CAE38719D6124F3CCAA3D038A610A
         * timestamp : 1541581754
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
}
