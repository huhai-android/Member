package com.newdjk.member.ui.activity.min;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.ImageBase64;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.SelectedPictureDialog;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class PersonalDataActivity extends BasicActivity {

    @BindView(R.id.mWebView)
    BridgeWebView mWebView;
    private Gson mGson;
    private SelectedPictureDialog mSelectedPictureDialog;
    private CallBackFunction mFunction;
    //    private PictureMessageEntity mPictureList;
    private String mUrl = BuildConfig.H5_IP_HOST+"Patient-add?fromCenter=1";
    private String mPicturePath;
    private final static int REQ_PERMISSION_CODE = 0x100;

    @Override
    protected int initViewResId() {
        return R.layout.activity_personal_data;
    }

    @Override
    protected void initView() {
        // 这里一定要加这个  不然会有缓存
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        mWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d("mUrl", url);
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
        mWebView.loadUrl(mUrl);
        initJsBridge();
        sendDataToHtml();

    }

    private void sendDataToHtml() {
        String mUserInfo = SpUtils.getString(Contants.LoginJson);
        Log.d("mUserInfo", mUserInfo);
        mWebView.callHandler("UserInfo", mUserInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d("onCallBack", "data=" + data);
            }
        });
    }


    private void initJsBridge() {
        mWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                onBackPressed();
            }
        });
        mWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null && data.equals("Img")) {
                    mFunction = function;
                    mSelectedPictureDialog = new SelectedPictureDialog(PersonalDataActivity.this);
                    mSelectedPictureDialog.show();
                    mSelectedPictureDialog.setmOnPictrueClickListener(new SelectedPictureDialog.OnPictrueClickListener() {
                        @Override
                        public void onCamera() {
                            if (checkPermission()) {
                                gotoCamera();
                            }
                        }

                        @Override
                        public void onALbum() {
                            if (checkPermission()) {
                                gotoAlbum();
                            }
                        }
                    });
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack(); //goBack()表示返回WebView的上一页面
        } else {
            MessageEvent messageEvent = new MessageEvent();
            messageEvent.setmType(MainConstant.UPDATE_PHOTO);
            EventBus.getDefault().post(messageEvent);
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(this, "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
    }


    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(PersonalDataActivity.this, (String[]) permissions.toArray(new String[0]), REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }


    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mGson = new Gson();
    }

    @Override
    protected void otherViewClick(View view) {

    }


    private void gotoAlbum() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 3);
    }

    private void gotoCamera() {
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String f = System.currentTimeMillis() + ".jpg";
        mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/UploadImage/" + f;
        File file = new File(mPicturePath);
        file.getParentFile().mkdirs();
        Log.i("zdp", mPicturePath);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            uri = FileProvider.getUriForFile(mContext, "com.newdjk.member.file.provider", file);
        } else {
            uri = Uri.fromFile(file);
        }

        //添加权限
        openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(openCameraIntent, 1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    uploadPicture(mPicturePath);
                    break;
                case 3:
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String path = cursor.getString(columnIndex);  //获取照片路径
                    uploadPicture(path);
                    break;
            }
        }
    }


    @SuppressLint("StaticFieldLeak")
    private void uploadPicture(String path) {
        new AsyncTask<String, Integer, String>() {
            @Override
            protected String doInBackground(String... strings) {
                String image64 = ImageBase64.bitmapToString(strings[0]);
                return image64;
            }

            @Override
            protected void onPostExecute(String s) {
                Map<String, String> map = new HashMap<>();
                map.put("Base64Str", s);
                mMyOkhttp.post().url(HttpUrl.updatePhoto).headers(HeadUitl.instance.getHeads()).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                    @Override
                    public void onSuccess(int statusCode, Entity entituy) {
                        if (entituy.getCode() == 0) {
                            mFunction.onCallBack(entituy.getData().toString());
                        } else {
                            toast(entituy.getMessage());
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            }
        }.execute(path);
    }


}
