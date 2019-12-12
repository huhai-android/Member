package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
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

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.adapter.ServiceBuyPackageDetailAdapter;
import com.newdjk.member.ui.entity.DefaultPatientEntity;
import com.newdjk.member.ui.entity.GetContactInfoEntity;
import com.newdjk.member.ui.entity.WXBuyServicePackEntity;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;
import com.newdjk.member.ui.entity.ZFBBuyServicePackEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.OrderUtil;
import com.newdjk.member.views.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderNoteActivity extends BasicActivity {
    @BindView(R.id.service_deposit_all_ll)
    LinearLayout serviceDepositAllLl;

    @BindView(R.id.service_deposit_rl)
    RelativeLayout serviceDepositRl;
    @BindView(R.id.service_deposit_ll)
    LinearLayout serviceDepositLl;
    @BindView(R.id.service_deposit_right_arrow)
    ImageView serviceDepositRighArrow;
    @BindView(R.id.service_deposit_data_tv)
    TextView serviceDepositDataTv;
    @BindView(R.id.total_price_tv)
    TextView totalPriceTv;

    @BindView(R.id.service_package_rl)
    RelativeLayout servicePackageRl;
    @BindView(R.id.service_package_ll)
    LinearLayout servicePackageLl;
    @BindView(R.id.service_package_right_arrow)
    ImageView servicePackageRightArrow;
    @BindView(R.id.service_package_data_tv)
    TextView servicePackageDataTv;
    @BindView(R.id.service_total_price_tv)
    TextView serviceTotalPriceTv;

    @BindView(R.id.alipay_iv)
    ImageView alipayIv;
    @BindView(R.id.alipay_rl)
    RelativeLayout alipayRl;
    @BindView(R.id.wechatpay_iv)
    ImageView wechatpayIv;
    @BindView(R.id.wechatpay_rl)
    RelativeLayout wechatpayRl;
    @BindView(R.id.agree_iv)
    ImageView agreeIv;
    @BindView(R.id.agree_ll)
    LinearLayout agreeLl;
    @BindView(R.id.agree_book_tv)
    TextView agreeBookTv;
    @BindView(R.id.pay_btn)
    Button payBtn;
    @BindView(R.id.pay_price_tv)
    TextView payPriceTv;

    @BindView(R.id.default_patient_rl)
    RelativeLayout defaultPatientRl;
    @BindView(R.id.default_patient_name_tv)
    TextView defaultPatientNameTv;
    @BindView(R.id.default_patient_note_tv)
    TextView defaultPatientNoteTv;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.service_package_data_des)
    TextView servicePackageDataDes;

    private DefaultPatientEntity.DataBean mDefaultPatient;
    private int OrgId;
    private int ServicePackId;
    private List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean> detailsBean;
    private int payWay = 0;
    private String service_package_text = "";
    private int ServiceLong;
    private String EndTime;
    private double payPrice;
    private GetContactInfoEntity mContactInfo;
    private double mOriginPrice;


    private String go_path;

    @Override
    protected int initViewResId() {
        return R.layout.activity_order_note;
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.order_title_string));
        Intent intent = getIntent();
        mDefaultPatient = intent.getParcelableExtra("Patient");
        OrgId = intent.getIntExtra("OrgId", 0);
        ServicePackId = intent.getIntExtra("ServicePackId", 0);
        detailsBean = (List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean>) getIntent().getSerializableExtra("detailsBean");
        ServiceLong = intent.getIntExtra("ServiceLong", 0);
        EndTime = intent.getStringExtra("EndTime");
        payPrice = intent.getDoubleExtra("payPrice", 0);
        go_path = intent.getStringExtra("go_path");
        mOriginPrice = intent.getDoubleExtra("originPrice", 0);

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.FINISH:
                finish();
                break;
        }
    }

    @Override
    protected void initData() {

        if (detailsBean == null) {
            return;
        }
        obtainOrigInfo();


        LinearLayoutManager healthGovernment = new LinearLayoutManager(this);
        healthGovernment.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(healthGovernment);
        ServiceBuyPackageDetailAdapter adapter = new ServiceBuyPackageDetailAdapter(detailsBean, this);
        rv.setAdapter(adapter);

       /* StringBuffer sb = new StringBuffer();
        for (int i = 0; i < detailsBean.size(); i++) {
            sb.append(detailsBean.get(i).getServiceItemName());
            sb.append("：");
            sb.append(detailsBean.get(i).getEachPrice());
            sb.append("/");
            sb.append(detailsBean.get(i).getNumTypeName());
            sb.append(detailsBean.get(i).getNumValue());
            sb.append("\n");
        }

        sb.append(getResources().getString(R.string.order_service_long_string) + ServiceLong + getResources().getString(R.string.order_day_string));
        servicePackageDataTv.setText(sb.toString());*/
        servicePackageDataTv.setText(getResources().getString(R.string.order_service_long_string));
        servicePackageDataDes.setText(ServiceLong + getResources().getString(R.string.order_day_string));
        serviceTotalPriceTv.setText(getResources().getString(R.string.order_total_money_unit_string) + mOriginPrice);
        payPriceTv.setText(payPrice + "");

        if (TextUtils.isEmpty(getIntent().getStringExtra("DueDate"))) {
            defaultPatientNoteTv.setVisibility(View.GONE);
        } else {
            defaultPatientNoteTv.setText("预产期：" + getIntent().getStringExtra("DueDate") + "\n" + "紧急联系人：" +
                    getIntent().getStringExtra("EmergencyName") + "\n" + "紧急联系电话：" +
                    getIntent().getStringExtra("EmergencyPhone"));
        }

        defaultPatientNameTv.setText(getIntent().getStringExtra("PatientName"));

    }

    @Override
    protected void otherViewClick(View view) {

    }


    private void obtainOrigInfo() {
        HashMap<String, String> params = new HashMap<>();
        params.put("orgId", OrgId + "");
        mMyOkhttp.get().url(HttpUrl.GetContactInfo).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<GetContactInfoEntity>() {

            @Override
            public void onSuccess(int statusCode, GetContactInfoEntity response) {
                if (response.getCode() == 0) {
                    if (response.getData() != null) {
                        for (int i = 0; i < detailsBean.size(); i++) {
                            if (detailsBean.get(i).getSericeItemCode().equals("1501")) {
                                serviceDepositAllLl.setVisibility(View.VISIBLE);
                                mContactInfo = response;
                                String mDeviceDes = OrderUtil.connectStr(response);
                                serviceDepositDataTv.setText(mDeviceDes);
                                totalPriceTv.setText(getResources().getString(R.string.order_total_money_unit_string) + detailsBean.get(i).getTotalPrice());
                            } else {
                                service_package_text = service_package_text + detailsBean.get(i).getServiceItemName() + "(" + detailsBean.get(i).getNumValue() + detailsBean.get(i).getNumTypeName() + "):¥" + detailsBean.get(i).getTotalPrice() + "\n";
                                //service_Total_Price = service_Total_Price + detailsBean.get(i).getTotalPrice();
                            }
                        }
                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    private boolean agree_boolean = false;
    private boolean service_deposit_boolean = false;
    private boolean service_package_boolean = false;

    @OnClick({R.id.pay_btn, R.id.agree_book_tv, R.id.alipay_rl, R.id.wechatpay_rl, R.id.agree_ll, R.id.service_deposit_rl, R.id.service_package_rl, R.id.default_patient_rl})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.pay_btn:

                if (payWay != 1 && payWay != 2) {
                    Toast.makeText(OrderNoteActivity.this, R.string.order_no_payway_toast_string, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!agree_boolean) {
                    Toast.makeText(OrderNoteActivity.this, R.string.order_no_agree_string, Toast.LENGTH_SHORT).show();
                    return;
                }
                checkInfo();

                break;
            case R.id.agree_book_tv:
//                Intent intent = new Intent(OrderNoteActivity.this, AgreeBookActivity.class);
//                toActivity(intent);
                Intent intent = new Intent(OrderNoteActivity.this, AgreeBookActivity.class);
                startActivityForResult(intent, 0x001);
                break;
            case R.id.alipay_rl:
                alipayIv.setBackgroundResource(R.mipmap.radio_button_check);
                wechatpayIv.setBackgroundResource(R.mipmap.radio_button_uncheck);
                payWay = 2;
                break;
            case R.id.wechatpay_rl:
                alipayIv.setBackgroundResource(R.mipmap.radio_button_uncheck);
                wechatpayIv.setBackgroundResource(R.mipmap.radio_button_check);
                payWay = 1;
                break;
            case R.id.agree_ll:
                if (agree_boolean) {
                    agreeIv.setBackgroundResource(R.mipmap.agree_uncheck);
                    agree_boolean = false;
                } else {
                    agreeIv.setBackgroundResource(R.mipmap.agree_check);
                    agree_boolean = true;
                }
                break;
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
            case R.id.default_patient_rl:
               /* Intent intent_patient = new Intent(OrderNoteActivity.this, UserNoteActivity.class);
                intent_patient.putExtra("Patient", mDefaultPatient);
                intent_patient.putExtra("OrgId", OrgId);
                intent_patient.putExtra("ServicePackId", ServicePackId);
                intent_patient.putExtra("detailsBean", (Serializable) detailsBean);
                intent_patient.putExtra("ServiceLong", ServiceLong);
                intent_patient.putExtra("EndTime", EndTime);
                intent_patient.putExtra("payPrice", payPrice);
                intent_patient.putExtra("go_path", go_path);
                toActivity(intent_patient);*/
                OrderNoteActivity.this.finish();
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 0x001:
                    agree_boolean = true;
                    agreeIv.setBackgroundResource(R.mipmap.agree_check);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void checkInfo() {
        /*String url = HttpUrl.HaveBuyDevice + "?accountId=" + SpUtils.getString(Contants.AccountId) + "&patientId=" + patientBean.getPatientId();
        mMyOkhttp.get().headers(HeadUitl.instance.getHeads()).url(url).tag(this).enqueue(new GsonResponseHandler<DeviceInfo>() {
            @Override
            public void onSuccess(int statusCode, DeviceInfo response) {
                mDeviceInfo = response;
                if (mDeviceInfo.getCode() == 1) {
                    toast(mDeviceInfo.getMessage());
                    return;
                }*/
        LoadDialog.show(OrderNoteActivity.this);

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("Application", "0");//应用:0芸生，1保定（默认为芸生
        paramsMap.put("PatientId", mDefaultPatient.getPatientId() + "");
        paramsMap.put("PatientName", mDefaultPatient.getPatientName());
        paramsMap.put("OrgId", OrgId + "");
        paramsMap.put("ServicePackId", ServicePackId + "");
        paramsMap.put("PayChannel", payWay + "");//1 微信  2 支付宝 payWay
        paramsMap.put("PayType", "1");
        //固定参数
        paramsMap.put("OpenId", "");
        paramsMap.put("RecommendId", "0");
        paramsMap.put("RecommendName", "");
        paramsMap.put("DrId", "0");
        paramsMap.put("DrName", "");


        if (payWay == 1) {
            mMyOkhttp.post().url(HttpUrl.BuyServicePack).headers(HeadUitl.instance.getHeads()).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<WXBuyServicePackEntity>() {
                @Override
                public void onSuccess(int statusCode, WXBuyServicePackEntity response) {
                    LoadDialog.clear();
                    if (response.getCode() == 0) {
                        Intent intent = new Intent(OrderNoteActivity.this, CheckOrderActivity.class);
                        intent.putExtra("detailsBean", (Serializable) detailsBean);
                        intent.putExtra("ServiceLong", ServiceLong);
                        intent.putExtra("EndTime", EndTime);
                        intent.putExtra("PayOrderNo", response.getPayOrderNo());
                        intent.putExtra("payPrice", payPrice);
                        intent.putExtra("payData", response.getData());
                        intent.putExtra("PayChannel", payWay);
                        intent.putExtra("go_path", go_path);
                        intent.putExtra("GetContactInfoEntity", mContactInfo);
                        toActivity(intent);
                    } else {
                        toast(response.getMessage());
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    LoadDialog.clear();
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        } else if (payWay == 2) {
            mMyOkhttp.post().url(HttpUrl.BuyServicePack).headers(HeadUitl.instance.getHeads()).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<ZFBBuyServicePackEntity>() {
                @Override
                public void onSuccess(int statusCode, ZFBBuyServicePackEntity response) {
                    LoadDialog.clear();
                    if (response.getCode() == 0) {
                        Intent intent = new Intent(OrderNoteActivity.this, CheckOrderActivity.class);
                        intent.putExtra("detailsBean", (Serializable) detailsBean);
                        intent.putExtra("ServiceLong", ServiceLong);
                        intent.putExtra("EndTime", EndTime);
                        intent.putExtra("PayOrderNo", response.getPayOrderNo());
                        intent.putExtra("payPrice", payPrice);
                        intent.putExtra("payData", response.getData());
                        intent.putExtra("PayChannel", payWay);
                        intent.putExtra("go_path", go_path);
                        intent.putExtra("GetContactInfoEntity", mContactInfo);
                        toActivity(intent);
                    } else {
                        toast(response.getMessage());
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    LoadDialog.clear();
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }

            /*}

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });*/
    }

}
