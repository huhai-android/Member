package com.newdjk.member.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.newdjk.member.R;
import com.newdjk.member.alipay.PayResult;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.ui.adapter.ServiceBuyPackageDetailAdapter;
import com.newdjk.member.ui.entity.AliPayInfo;
import com.newdjk.member.ui.entity.GetContactInfoEntity;
import com.newdjk.member.ui.entity.WXBuyServicePackEntity;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;
import com.newdjk.member.ui.entity.ZFBBuyServicePackEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.OrderUtil;
import com.newdjk.member.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class CheckOrderActivity extends BasicActivity {

    private static final int SDK_PAY_FLAG = 1;

    @BindView(R.id.service_deposit_all_ll)
    LinearLayout serviceDepositAllLl;
    @BindView(R.id.service_deposit_data_tv)
    TextView serviceDepositDataTv;
    @BindView(R.id.total_price_tv)
    TextView totalPriceTv;
    @BindView(R.id.service_package_data_tv)
    TextView servicePackageDataTv;
    @BindView(R.id.service_package_data_des)
    TextView servicePackageDataDes;
    @BindView(R.id.pay_order_no_tv)
    TextView payOrderNoTv;
    @BindView(R.id.publish_order_time_tv)
    TextView publishOrderTimeTv;
    @BindView(R.id.service_deadline_tv)
    TextView serviceDeadlineTv;
    @BindView(R.id.service_total_price_tv)
    TextView serviceTotalPriceTv;

    @BindView(R.id.service_deposit_ll)
    LinearLayout serviceDepositLl;
    @BindView(R.id.service_deposit_right_arrow)
    ImageView serviceDepositRighArrow;
    @BindView(R.id.service_package_ll)
    LinearLayout servicePackageLl;
    @BindView(R.id.service_package_right_arrow)
    ImageView servicePackageRightArrow;
    @BindView(R.id.service_deposit_rl)
    RelativeLayout serviceDepositRl;

    @BindView(R.id.pay_price_tv)
    TextView payPriceTv;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.rv)
    RecyclerView rv;

    private List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean> detailsBean;
    private int ServiceLong;
    private String service_package_text = "";
    private String EndTime;
    private String PayOrderNo;
    private double service_Total_Price = 0;
    private double payPrice;
    private WXBuyServicePackEntity.DataBean dataBean;
    private ZFBBuyServicePackEntity.DataBean zfbdataBean;
    private int PayChannel;
    private String go_path;
    private GetContactInfoEntity mGetContactInfoEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_check_order;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.check_order_title_string));

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {



        Intent intent = getIntent();
        detailsBean = (List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean>) intent.getSerializableExtra("detailsBean");
        mGetContactInfoEntity = (GetContactInfoEntity) intent.getSerializableExtra("GetContactInfoEntity");
        ServiceLong = intent.getIntExtra("ServiceLong", 0);
        PayOrderNo = intent.getStringExtra("PayOrderNo");
        EndTime = intent.getStringExtra("EndTime");
        payPrice = intent.getDoubleExtra("payPrice", 0);
        PayChannel = intent.getIntExtra("PayChannel",0);
        go_path = intent.getStringExtra("go_path");


        LinearLayoutManager healthGovernment = new LinearLayoutManager(this);
        healthGovernment.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(healthGovernment);
        ServiceBuyPackageDetailAdapter adapter = new ServiceBuyPackageDetailAdapter(detailsBean, this);
        rv.setAdapter(adapter);


        if (PayChannel == 1) {
            dataBean = (WXBuyServicePackEntity.DataBean)intent.getSerializableExtra("payData");
        } else if (PayChannel == 2) {
            zfbdataBean = (ZFBBuyServicePackEntity.DataBean)intent.getSerializableExtra("payData");
        }
        payOrderNoTv.setText(PayOrderNo);
        publishOrderTimeTv.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        serviceDeadlineTv.setText(EndTime);
        for (int i = 0; i < detailsBean.size(); i++) {
            if (detailsBean.get(i).getSericeItemCode().equals("1501")) {
                serviceDepositAllLl.setVisibility(View.VISIBLE);
                serviceDepositLl.setVisibility(View.VISIBLE);
                service_deposit_boolean = false;
                service_package_boolean = false;
                totalPriceTv.setText(getResources().getString(R.string.order_total_money_unit_string) + detailsBean.get(i).getTotalPrice());
            } else {
                service_Total_Price = service_Total_Price + detailsBean.get(i).getTotalPrice();
            }
        }
        if (mGetContactInfoEntity != null) {
            String mDeviceDes = OrderUtil.connectStr(mGetContactInfoEntity);
            serviceDepositDataTv.setText(mDeviceDes);
        }
        servicePackageDataTv.setText(service_package_text + getResources().getString(R.string.order_service_long_string));
        servicePackageDataDes.setText(ServiceLong + getResources().getString(R.string.order_day_string));
        serviceTotalPriceTv.setText(getResources().getString(R.string.order_total_money_unit_string) + service_Total_Price);
        payPriceTv.setText(payPrice + "");
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private boolean service_deposit_boolean = true;
    private boolean service_package_boolean = true;

    @OnClick({R.id.service_deposit_rl, R.id.service_package_rl, R.id.pay_btn})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.service_deposit_rl:
                if (service_deposit_boolean) {
                    serviceDepositLl.setVisibility(View.VISIBLE);
                    serviceDepositRighArrow.setBackgroundResource(R.mipmap.arrow_bottom);
                    service_deposit_boolean = false;
                } else {
                    serviceDepositLl.setVisibility(View.GONE);
                    serviceDepositRighArrow.setBackgroundResource(R.mipmap.arrow_top);
                    service_deposit_boolean = true;
                }
                break;
            case R.id.service_package_rl:
                if (service_package_boolean) {
                    servicePackageLl.setVisibility(View.VISIBLE);
                    servicePackageRightArrow.setBackgroundResource(R.mipmap.arrow_bottom);
                    service_package_boolean = false;
                } else {
                    servicePackageLl.setVisibility(View.GONE);
                    servicePackageRightArrow.setBackgroundResource(R.mipmap.arrow_top);
                    service_package_boolean = true;
                }
                break;
            case R.id.pay_btn://1 微信  2 支付宝 payWay
                if (payPrice==0) {
                    Intent intent = new Intent(CheckOrderActivity.this,PaySuccessActivity.class);
                    intent.putExtra("detailsBean", (Serializable) detailsBean);
                    intent.putExtra("payPrice", payPrice);
                    intent.putExtra("PayChannel", PayChannel);
                    intent.putExtra("go_path",go_path);
                    intent.putExtra("GetContactInfoEntity",mGetContactInfoEntity);
                    toActivity(intent);
                } else {
                    Gson mGson = new Gson();
                    if (PayChannel == 1) {
                        WXPayEntryActivity.weixinPay(mGson.toJson(dataBean));
                    } else if (PayChannel == 2){
                        AliPayInfo aliPayInfo = mGson.fromJson(mGson.toJson(zfbdataBean), AliPayInfo.class);
                        aliPay(aliPayInfo.getBody());
                    }
                }
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.WXPAYRESULT:
                int payCode = event.getPayCode();
                if (payCode == 0) {
                    Intent intent = new Intent(CheckOrderActivity.this,PaySuccessActivity.class);
                    intent.putExtra("detailsBean", (Serializable) detailsBean);
                    intent.putExtra("payPrice", payPrice);
                    intent.putExtra("PayChannel", PayChannel);
                    intent.putExtra("go_path",go_path);
                    intent.putExtra("GetContactInfoEntity",mGetContactInfoEntity);
                    toActivity(intent);
                    finish();

                } else {
                    Intent intent = new Intent(CheckOrderActivity.this,PayFailActivity.class);
                    intent.putExtra("payPrice", payPrice);
                    intent.putExtra("PayChannel", PayChannel);
                    intent.putExtra("go_path",go_path);
                    toActivity(intent);
                    finish();
                }
                break;
            case MainConstant.FINISH:
                finish();
                break;
        }
    }

    public void aliPay(final String orderInfo){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(CheckOrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }



    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(CheckOrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckOrderActivity.this,PaySuccessActivity.class);
                        intent.putExtra("detailsBean", (Serializable) detailsBean);
                        intent.putExtra("payPrice", payPrice);
                        intent.putExtra("PayChannel", PayChannel);
                        intent.putExtra("go_path",go_path);
                        intent.putExtra("GetContactInfoEntity",mGetContactInfoEntity);
                        toActivity(intent);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(CheckOrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CheckOrderActivity.this,PayFailActivity.class);
                        intent.putExtra("payPrice", payPrice);
                        intent.putExtra("PayChannel", PayChannel);
                        intent.putExtra("go_path",go_path);
                        toActivity(intent);
                    }
                    break;
                }
                default:
                    break;
            }
        };
    };

    @OnClick()
    public void onViewClicked() {
    }
}
