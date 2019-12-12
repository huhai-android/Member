package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PayFailActivity extends BasicActivity {

    @BindView(R.id.pay_type_tv)
    TextView payTypeTv;
    @BindView(R.id.pay_money_tv)
    TextView payMoneyTv;
    @BindView(R.id.pay_again_btn)
    Button payAgainBtn;
    @BindView(R.id.pay_give_up_btn)
    Button payGiveUpBtn;
    @BindView(R.id.pay_type_iv)
    ImageView payTypeIv;

    private int PayChannel;
    private double payPrice;

    private String go_path;
    @Override
    protected int initViewResId() {
        return R.layout.activity_pay_fail;
    }

    @Override
    protected void initView() {
        initBackTitleLeftImg(R.color.white, getResources().getString(R.string.pay_fail_title_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPath();
            }
        });
        initTitleRightStrClick(getResources().getString(R.string.pay_fail_complete_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPath();
            }
        });
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        PayChannel = intent.getIntExtra("PayChannel", 0);
        payPrice = intent.getDoubleExtra("payPrice", 0);
        go_path = intent.getStringExtra("go_path");

        if (PayChannel == 1) {
            payTypeTv.setText(getResources().getString(R.string.pay_fail_weixin_string));
            payTypeIv.setBackgroundResource(R.mipmap.wechatpay_grey);
        } else if (PayChannel == 2) {
            payTypeTv.setText(getResources().getString(R.string.pay_fail_zfb_string));
            payTypeIv.setBackgroundResource(R.mipmap.zfb_grey);
        }
        payMoneyTv.setText(getResources().getString(R.string.pay_fail_money_unit_string) + payPrice);

    }

    @Override
    protected void otherViewClick(View view) {

    }

    private void goToPath() {
        //加入activity管理
        ServiceListActivity.activity_lists.add(PayFailActivity.this);
        //清掉管理的activity
        for (int i = 0; i < ServiceListActivity.activity_lists.size(); i ++) {
            ServiceListActivity.activity_lists.get(i).finish();
        }
        if (go_path.equals("device_connect")) {
            Intent intent = new Intent(PayFailActivity.this, DeviceConnectActivity.class);
            toActivity(intent);
        } else if (go_path.equals("fetalHeartMonitorMine")) {
            Intent intent = new Intent(PayFailActivity.this, FetalHeartMonitorMineActivity.class);
            toActivity(intent);
        }
    }

    @OnClick({R.id.pay_again_btn, R.id.pay_give_up_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pay_again_btn:
                PayFailActivity.this.finish();
                break;
            case R.id.pay_give_up_btn:
                goToPath();
                break;
        }
    }

}
