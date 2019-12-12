package com.newdjk.member.ui.entity;

public class ServicePayInfo {


    /**
     * payInfo : {"Body":"app_id=2018111562191483&biz_content=%7b%22body%22%3a%22%e4%bf%9d%e5%ae%9a%e4%b8%93%e9%a1%b9%e6%9c%8d%e5%8a%a1%e5%8c%85%22%2c%22goods_type%22%3a%220%22%2c%22out_trade_no%22%3a%2279d30d3d1c524b4f95a64a9b465ee2c1%22%2c%22seller_id%22%3a%222088231943886208%22%2c%22subject%22%3a%22%e4%bf%9d%e5%ae%9a%e4%b8%93%e9%a1%b9%e6%9c%8d%e5%8a%a1%e5%8c%85%22%2c%22total_amount%22%3a%220.02%22%7d&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fapi.newstarthealth.cn%2fPayAPI%2fAliPay%2fNotify&sign_type=RSA2&timestamp=2018-11-30+18%3a04%3a00&version=1.0&sign=SGWjMt7uaLl5hs0H5nmSgo9qrnOOUij%2f8JN8U%2fYiXGr%2bZVfV2WOEZwlrPcEtxDrhwZD11I4B2frAp30MqmOSe1vAAyiWuySKi4wiUYHqctRGt4TmLaHwUETvHMCejsTzPBzA5%2bMkxMfQ9auwr77WsbhdHO1CPWLCQy1xRT5L87jvdWrMfkQAdHfBL2iAQ2mxg6XRM29AJdqERxRrsy0rqunpgCMWJTBSslSzMMrliB4dz%2fP%2f0g5hLJ7K4piRArR7U5b5%2bvRrvwPafNZP%2bRmBUSDMrZcIOvK6gPeQci1Vrog9FWl16KUPUvZvHaBAPmfXu1bUD4RYO%2fAZLzk1p%2fVnwQ%3d%3d","PayOrderNo":"20181130180400200154"}
     * price : 0.02
     * payChannel : 2
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
         * Body : app_id=2018111562191483&biz_content=%7b%22body%22%3a%22%e4%bf%9d%e5%ae%9a%e4%b8%93%e9%a1%b9%e6%9c%8d%e5%8a%a1%e5%8c%85%22%2c%22goods_type%22%3a%220%22%2c%22out_trade_no%22%3a%2279d30d3d1c524b4f95a64a9b465ee2c1%22%2c%22seller_id%22%3a%222088231943886208%22%2c%22subject%22%3a%22%e4%bf%9d%e5%ae%9a%e4%b8%93%e9%a1%b9%e6%9c%8d%e5%8a%a1%e5%8c%85%22%2c%22total_amount%22%3a%220.02%22%7d&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fapi.newstarthealth.cn%2fPayAPI%2fAliPay%2fNotify&sign_type=RSA2&timestamp=2018-11-30+18%3a04%3a00&version=1.0&sign=SGWjMt7uaLl5hs0H5nmSgo9qrnOOUij%2f8JN8U%2fYiXGr%2bZVfV2WOEZwlrPcEtxDrhwZD11I4B2frAp30MqmOSe1vAAyiWuySKi4wiUYHqctRGt4TmLaHwUETvHMCejsTzPBzA5%2bMkxMfQ9auwr77WsbhdHO1CPWLCQy1xRT5L87jvdWrMfkQAdHfBL2iAQ2mxg6XRM29AJdqERxRrsy0rqunpgCMWJTBSslSzMMrliB4dz%2fP%2f0g5hLJ7K4piRArR7U5b5%2bvRrvwPafNZP%2bRmBUSDMrZcIOvK6gPeQci1Vrog9FWl16KUPUvZvHaBAPmfXu1bUD4RYO%2fAZLzk1p%2fVnwQ%3d%3d
         * PayOrderNo : 20181130180400200154
         */

        private String Body;
        private String PayOrderNo;

        public String getBody() {
            return Body;
        }

        public void setBody(String Body) {
            this.Body = Body;
        }

        public String getPayOrderNo() {
            return PayOrderNo;
        }

        public void setPayOrderNo(String PayOrderNo) {
            this.PayOrderNo = PayOrderNo;
        }
    }
}
