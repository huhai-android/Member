package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.media.ExifInterface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.ui.entity.GetContactInfoEntity;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.OrderUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySuccessActivity extends BasicActivity {

    @BindView(R.id.complete_btn)
    Button completeBtn;
    @BindView(R.id.pay_type_tv)
    TextView payTypeTv;
    @BindView(R.id.pay_money_tv)
    TextView payMoneyTv;
    @BindView(R.id.pay_type_iv)
    ImageView payTypeIv;
    @BindView(R.id.get_note_tv)
    TextView getNoteTv;
    @BindView(R.id.ll_container)
    LinearLayout mContainer;
    @BindView(R.id.mTvTip)
    TextView mTvTip;

    private List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean> detailsBean;
    private String go_path = "";
    private double payPrice;
    private int PayChannel;
    private GetContactInfoEntity mGetContactInfoEntity;

    @Override
    protected int initViewResId() {
        return R.layout.activity_pay_success;
    }

    @Override
    protected void initView() {
        initBackTitleLeftImg(R.color.white, getResources().getString(R.string.pay_fail_title_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (go_path != null) {
                    goToPath();
                } else {
                    PaySuccessActivity.this.finish();
                }
            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        detailsBean = (List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean>) intent.getSerializableExtra("detailsBean");
        go_path = intent.getStringExtra("go_path");
        payPrice = intent.getDoubleExtra("payPrice", 0.00);
        PayChannel = intent.getIntExtra("PayChannel", 0);
        mGetContactInfoEntity = (GetContactInfoEntity) intent.getSerializableExtra("GetContactInfoEntity");

        if (PayChannel == 1) {
            payTypeTv.setText(getResources().getString(R.string.pay_success_weixin_string));
            payTypeIv.setBackgroundResource(R.mipmap.wechatpay_grey);
        } else if (PayChannel == 2) {
            payTypeTv.setText(getResources().getString(R.string.pay_success_zfb_string));
            payTypeIv.setBackgroundResource(R.mipmap.zfb_grey);
        }
        payMoneyTv.setText(getResources().getString(R.string.pay_success_money_unit_string) + String.format("%.2f",payPrice));

//        if (detailsBean != null&&detailsBean.size()>0) {
//            for (int i = 0; i < detailsBean.size(); i++) {
//                if (detailsBean.get(i).getSericeItemCode().equals("1501")) {
//                    getNoteTv.setText(detailsBean.get(i).getDescription());
//                }
//            }
//        }

        if (mGetContactInfoEntity != null) {
            mContainer.setVisibility(View.VISIBLE);
            String mDeviceDes = OrderUtil.connectStr(mGetContactInfoEntity);
            getNoteTv.setText(mDeviceDes);
            mTvTip.setVisibility(View.VISIBLE);
        } else {
            mContainer.setVisibility(View.INVISIBLE);
            mTvTip.setVisibility(View.GONE);
        }

    }

    @Override
    protected void otherViewClick(View view) {

    }

    private void goToPath() {
        MessageEvent messageEvent = new MessageEvent();
        messageEvent.setmType(MainConstant.FINISH);
        EventBus.getDefault().post(messageEvent);
        if (go_path.equals("device_connect")) {
            Intent intent = new Intent(PaySuccessActivity.this, DeviceConnectActivity.class);
            toActivity(intent);
        } else if (go_path.equals("fetalHeartMonitorMine")) {
            Intent intent = new Intent(PaySuccessActivity.this, FetalHeartMonitorMineActivity.class);
            toActivity(intent);
        } else if (go_path.equals("webView")) {
            Intent intent = new Intent(this, MainActivity.class);
            toActivity(intent);
        }
        finish();
    }

    @OnClick(R.id.complete_btn)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.complete_btn:
                goToPath();
                break;
        }
    }

}
