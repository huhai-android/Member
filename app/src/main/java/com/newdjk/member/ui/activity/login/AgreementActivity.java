package com.newdjk.member.ui.activity.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.jaeger.library.StatusBarUtil;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.tools.Contants;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用戶協議
 */
public class AgreementActivity extends BasicActivity {

    @BindView(R.id.wb)
    BridgeWebView wb;
    private String mAction;

    public static Intent getInten(Context context) {
        return new Intent(context, AgreementActivity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_agreement;
    }

    @Override
    protected void initView() {
        mAction = getIntent().getStringExtra("userInfo");
//        wb.loadUrl("http://172.18.30.139/#/Agreement");
        wb.loadUrl("http://testapi.newstarthealth.cn/platformapi/userprotocol.html");
        sendNative();
        wb.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("zdp","data="+data);
                finish();
            }
        });
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
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white),0);
    }



    public void sendNative() {
        wb.callHandler("UserInfo", mAction, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.i("zdp", data);
            }

        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (wb.canGoBack()) {
                wb.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                finish();
                return true;
            }

        }
        return false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
