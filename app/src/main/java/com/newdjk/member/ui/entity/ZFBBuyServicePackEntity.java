package com.newdjk.member.ui.entity;

import java.io.Serializable;

public class ZFBBuyServicePackEntity {

    /**
     * PayOrderNo : 20181110175557458248
     * Code : 0
     * Message : 支付提交成功
     * Data : {"Body":"app_id=2018092961523980&biz_content=%7b%22body%22%3a%22%e5%a6%87%e5%b9%bc%e6%af%8d%e8%83%8e%e7%9b%91%e6%b5%8b%e6%9c%8d%e5%8a%a1%e5%8c%85%22%2c%22goods_type%22%3a%220%22%2c%22out_trade_no%22%3a%22e4f80065bdd849b0a52abb820d02752c%22%2c%22seller_id%22%3a%222088231943886208%22%2c%22subject%22%3a%22%e5%a6%87%e5%b9%bc%e6%af%8d%e8%83%8e%e7%9b%91%e6%b5%8b%e6%9c%8d%e5%8a%a1%e5%8c%85%22%2c%22total_amount%22%3a%220.04%22%7d&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fapi.newstarthealth.cn%2fPayAPI%2fAliPay%2fNotify&sign_type=RSA2&timestamp=2018-11-10+17%3a55%3a57&version=1.0&sign=c7qX1Ycw390EeBtrDF8XTUKPU3gEl7xvzY4G3F1n%2fvSNPFHugGz4svTwX26s2nSLZ4btMXXBJSwUALu1M0Z7JWwsCg8o2xypqQEWOQwuuQBBE6Ye8u6XCu%2bwF87JcRM4nd9EtB7GsZsfC4GT0fQC83NAkpH6ses2lZd6R0QRLzfQf7D9JP5AsqsdoYg074vuzHCIs%2boP0CxlcrJw1OXpLX3x9RCyhuVwMUS8hDfJ5pd6jKulgTRcCHv279E47yP7F40STMHNXsSn8Fmb634d3okSxzh2%2fFSHwk6%2bl2L85DsoZ9ua6EsFjmJwUaMSgMb%2fc%2fzgUlU9mpPa8cWtU%2fYz4w%3d%3d"}
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
         * Body : app_id=2018092961523980&biz_content=%7b%22body%22%3a%22%e5%a6%87%e5%b9%bc%e6%af%8d%e8%83%8e%e7%9b%91%e6%b5%8b%e6%9c%8d%e5%8a%a1%e5%8c%85%22%2c%22goods_type%22%3a%220%22%2c%22out_trade_no%22%3a%22e4f80065bdd849b0a52abb820d02752c%22%2c%22seller_id%22%3a%222088231943886208%22%2c%22subject%22%3a%22%e5%a6%87%e5%b9%bc%e6%af%8d%e8%83%8e%e7%9b%91%e6%b5%8b%e6%9c%8d%e5%8a%a1%e5%8c%85%22%2c%22total_amount%22%3a%220.04%22%7d&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fapi.newstarthealth.cn%2fPayAPI%2fAliPay%2fNotify&sign_type=RSA2&timestamp=2018-11-10+17%3a55%3a57&version=1.0&sign=c7qX1Ycw390EeBtrDF8XTUKPU3gEl7xvzY4G3F1n%2fvSNPFHugGz4svTwX26s2nSLZ4btMXXBJSwUALu1M0Z7JWwsCg8o2xypqQEWOQwuuQBBE6Ye8u6XCu%2bwF87JcRM4nd9EtB7GsZsfC4GT0fQC83NAkpH6ses2lZd6R0QRLzfQf7D9JP5AsqsdoYg074vuzHCIs%2boP0CxlcrJw1OXpLX3x9RCyhuVwMUS8hDfJ5pd6jKulgTRcCHv279E47yP7F40STMHNXsSn8Fmb634d3okSxzh2%2fFSHwk6%2bl2L85DsoZ9ua6EsFjmJwUaMSgMb%2fc%2fzgUlU9mpPa8cWtU%2fYz4w%3d%3d
         */

        private String Body;

        public String getBody() {
            return Body;
        }

        public void setBody(String Body) {
            this.Body = Body;
        }
    }

}
