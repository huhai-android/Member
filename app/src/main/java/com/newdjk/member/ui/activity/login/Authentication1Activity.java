package com.newdjk.member.ui.activity.login;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;

/**
 * 注册认证 第一步
 */
public class Authentication1Activity extends BasicActivity {

    @Override
    protected int initViewResId() {
        return R.layout.activity_authentication1;
    }

    @Override
    protected void initView() {
        initBackTitle("注册认证");
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
}
