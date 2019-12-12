package com.newdjk.member.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.newdjk.member.BuildConfig;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FetalMonitorMeaningActivity extends BasicActivity {

    @BindView(R.id.mWebView)
    WebView mWebView;

    @Override
    protected int initViewResId() {
        return R.layout.activity_fetal_monitor_meaning;
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.fetal_monitor_meaning_title_string));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mWebView.loadUrl(BuildConfig.URL+"/FetalHeartAPI/helper.html");
    }

    @Override
    protected void otherViewClick(View view) {

    }

}
