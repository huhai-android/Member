package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceSettingActivity extends BasicActivity {

    @BindView(R.id.monitor_setting_rl)
    RelativeLayout monitorSettingRl;
    @BindView(R.id.guide_rl)
    RelativeLayout guideRl;

    @Override
    protected int initViewResId() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_device_setting;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return super.onTouchEvent(event);
    }

    @OnClick({R.id.monitor_setting_rl, R.id.guide_rl})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.monitor_setting_rl:
                intent = new Intent(DeviceSettingActivity.this,MonitorSettingActivity.class);
                toActivity(intent);
                DeviceSettingActivity.this.finish();
                break;
            case R.id.guide_rl:
                intent = new Intent(DeviceSettingActivity.this,GuideOperateActivity.class);
                toActivity(intent);
                DeviceSettingActivity.this.finish();
                break;
        }
    }
}
