package com.newdjk.member.ui.entity;

/**
 * Created by EDZ on 2018/10/23.
 */

public class AliPayInfo {


    /**
     * Body : app_id=2018092961523980&biz_content=%7b%22body%22%3a%22%e5%9b%be%e6%96%87%e9%97%ae%e8%af%8a%22%2c%22goods_type%22%3a%220%22%2c%22out_trade_no%22%3a%22b7d9927bdb49476795ee32ad4c01bfd5%22%2c%22seller_id%22%3a%222088231943886208%22%2c%22subject%22%3a%22%e5%9b%be%e6%96%87%e9%97%ae%e8%af%8a%22%2c%22total_amount%22%3a%220.01%22%7d&charset=UTF-8&format=json&method=alipay.trade.app.pay&notify_url=http%3a%2f%2fapi.newstarthealth.cn%2fPayAPI%2fAliPay%2fNotify&sign_type=RSA2&timestamp=2018-10-23+16%3a42%3a40&version=1.0&sign=qlgVa9Dc7dJOtMwQI4nbI6yUQ1MetKolj4ePwYIDP%2bVsg9gcbGf82ghM4TDDIST5DwFGvmats7FQGv1LWiiBlyTOWCeC9dIru1HNn76lknfZx4IeCCOE9mcy420Y7fElWV%2fVNm1gUPFYXd4ocuJB5y6Nrh80uAu9j9OdjDJTo9B8peMwugEl47cgpcmBrCt2pHeEB56RG93XWktl3LqUoXijnWjiyjx%2f2D5M%2bm85VlXhTnD8NKF5siERzXBJ4kHJawvgjWKR%2bNyjLuHUOOcT6D0YkPnH3F4UDQDuGWYXRbfr1Qj8Difz3IEsg3f0mb90LZobEEg7AoCIhWqYGtrBBA%3d%3d
     */

    private String Body;

    public String getBody() {
        return Body;
    }

    public void setBody(String Body) {
        this.Body = Body;
    }
}
