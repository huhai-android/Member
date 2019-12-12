package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.utils.SpUtils;

import butterknife.BindView;

public class NSTRatingActivity extends BasicActivity {
    @BindView(R.id.wb)
    BridgeWebView wb;

    private int id;

    @Override
    protected int initViewResId() {
        return R.layout.activity_nstrating;
    }

    @Override
    protected void initView() {
        //initBackTitleBgRes(R.color.white, getResources().getString(R.string.score_nst_title_string));

        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        // 这里一定要加这个  不然会有缓存
        wb.clearCache(true);
        wb.clearHistory();
        wb.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        wb.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wb.getSettings().setSupportZoom(true);
        wb.getSettings().setBuiltInZoomControls(true);
        wb.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        wb.getSettings().setUseWideViewPort(true);
        wb.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        wb.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        wb.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            wb.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        wb.setWebViewClient(new BridgeWebViewClient(wb) {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        String url = BuildConfig.H5_IP_HOST + "fetal/rating/?id=" + id + "&scoreType=2";
        // url = "https://www.baidu.com";
        wb.loadUrl(url);
       /* Intent intent1 = new Intent(this, WebViewActivity.class);
        intent1.putExtra("type", WebUtil.test);
        toActivity(intent1);*/
        sendDataToHtml();
        initJsBridge();
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
    protected void onDestroy() {
        if (wb != null) {
            ViewParent viewParent = wb.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeView(wb);
            }

            wb.stopLoading();
            wb.getSettings().setJavaScriptEnabled(false);
            wb.clearHistory();
            wb.clearView();
            wb.removeAllViews();
            try {
                wb.destroy();
            } catch (Exception e) {

            }

        }
        super.onDestroy();
    }

    private void sendDataToHtml() {
//        String mUserInfo = SpUtils.getString(Contants.LoginJson);
//        // String appInfo = SpUtils.getString(Contants.AppInfo);
//
//        //Log.d("mUserInfo", mUserInfo);
//        wb.callHandler("UserInfo", mUserInfo, new CallBackFunction() {
//            @Override
//            public void onCallBack(String data) {
//                Log.d("onCallBack", "data=" + data);
//            }
//        });
        String mUserInfo = SpUtils.getString(Contants.LoginJson);
        Log.d("mUserInfo", mUserInfo);
        wb.callHandler("UserInfo", mUserInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d("onCallBack", "data=" + data);
            }
        });
    }

    private void initJsBridge() {
        // 注册一个方法给JS调用：关闭网页
        wb.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (wb.canGoBack()) {
                    wb.goBack(); //goBack()表示返回WebView的上一页面
                } else {
                    finish();
                }
            }
        });
    }
}
