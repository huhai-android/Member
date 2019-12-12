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
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.R;
import com.newdjk.member.alipay.PayResult;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.ChatActivity;
import com.newdjk.member.ui.activity.MyMapActivity;
import com.newdjk.member.ui.activity.OrderNoteActivity;
import com.newdjk.member.ui.activity.PictureViewerActivity;
import com.newdjk.member.ui.entity.AddressEntity;
import com.newdjk.member.ui.entity.AliPayInfo;
import com.newdjk.member.ui.entity.ConsultMessageEntity;
import com.newdjk.member.ui.entity.DefaultPatientEntity;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.HospitalAddressInfo;
import com.newdjk.member.ui.entity.ImDoctorEntity;
import com.newdjk.member.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.member.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.member.ui.entity.PaintListEntity;
import com.newdjk.member.ui.entity.PictureMessageEntity;
import com.newdjk.member.ui.entity.PrescriptionMessageEntity;
import com.newdjk.member.ui.entity.ResponseEntity;
import com.newdjk.member.ui.entity.ServicePackageDetailEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.ImageBase64;
import com.newdjk.member.utils.LogUtils;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.PermissionUtil;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.SelectedPictureDialog;
import com.newdjk.member.wxapi.WXPayEntryActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class WebHeadViewActivity extends BasicActivity {


    private static final String TAG = "WebViewActivity";
    @BindView(R.id.mWebView)
    BridgeWebView mWebView;
    private String mUrl = BuildConfig.H5_IP_HOST;
    private int type;
    private SelectedPictureDialog mSelectedPictureDialog;
    private CallBackFunction mFunction;
    private String mPicturePath;
    private final static int REQ_PERMISSION_CODE = 0x100;
    private Gson mGson;
    private String mMobile;
    private CallBackFunction mfunction;
    private static final int SDK_PAY_FLAG = 1;
    private String mPayType;
    private PictureMessageEntity mPictureList;
    private String mReturnSign;


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
                        Toast.makeText(WebHeadViewActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        if (mfunction != null) {
                            mfunction.onCallBack("success");
                        }

                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(WebHeadViewActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
                        if (mfunction != null) {
                            mfunction.onCallBack("fail");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };
    private String mLinkUrl;

    @Override
    protected int initViewResId() {
        return R.layout.activity_web_header_view;
    }

    @Override
    protected void initView() {
        initTitle("详情").setLeftImage(R.mipmap.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);
        EventBus.getDefault().register(this);
        mGson = new Gson();
        Intent intent = getIntent();
        if (intent != null) {
            type = intent.getIntExtra("type", 0);
            mLinkUrl = intent.getStringExtra("url");
            switch (type) {
                case WebUtil.link:
                    mUrl = mLinkUrl;
                    break;
                case WebUtil.secondReport:
                    mUrl = BuildConfig.H5_IP_HOST+"reportDetail";
                    break;

            }
        }
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
        // String appInfo = SpUtils.getString(Contants.AppInfo);

        //Log.d("mUserInfo", mUserInfo);
        mWebView.callHandler("UserInfo", mUserInfo, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                Log.d("onCallBack", "data=" + data);
            }
        });
    }


    private void initJsBridge() {
        // 注册一个方法给JS调用：关闭网页
        mWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack(); //goBack()表示返回WebView的上一页面
                } else {
                    finish();
                }
            }
        });

        mWebView.registerHandler("getLocation", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (TextUtils.isEmpty(data)) {
                    return;
                }
                HospitalAddressInfo info = mGson.fromJson(data, HospitalAddressInfo.class);
                Intent intent = new Intent(WebHeadViewActivity.this, MyMapActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("hospitalAddInfo", info);
                intent.putExtras(bundle);
                toActivity(intent);
            }
        });

        mWebView.registerHandler("chooseAddress", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                AddressEntity.DataBean address = mGson.fromJson(data, AddressEntity.DataBean.class);
                EventBus.getDefault().postSticky(address);
                finish();
            }
        });

        mWebView.registerHandler("dial", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mMobile = data;
                if (PermissionUtil.INSTANCE.phoneCall(WebHeadViewActivity.this)) {
                    call();
                }

            }
        });



        mWebView.registerHandler("choosePatient", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null) {
                    PaintListEntity.DataBean info = mGson.fromJson(data, PaintListEntity.DataBean.class);
                    EventBus.getDefault().postSticky(info);
                }
            }
        });

        mWebView.registerHandler("msgContent", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("WebViewActivity", "data=" + data);
                Intent intent = new Intent();
                intent.putExtra("msgContent", data);
                setResult(RESULT_OK, intent);
            }
        });

        mWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                if (data != null && data.equals("Img")) {
                    mFunction = function;
                    mSelectedPictureDialog = new SelectedPictureDialog(WebHeadViewActivity.this);
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

        // 患者支付成功之后跳转到ChatActivity页面的监听
        mWebView.registerHandler("ImInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("ImInfo===", data);
                ImDoctorEntity imDoctorEntity = mGson.fromJson(data, ImDoctorEntity.class);
                String serviceCode = imDoctorEntity.getServiceCode();
                String recordId = String.valueOf(imDoctorEntity.getRelationId());
                if (serviceCode.equals("1101")) {
                    QueryConsultDoctorAppMessageByPage(recordId);
                } else if (serviceCode.equals("1102")) {
                    QueryvideoDoctorAppMessageByPage(recordId);
                } else if (serviceCode.equals("1103")) {
                    QueryRenewalDoctorAppMessageByPage(recordId);
                }
            }
        });


        // 注册一个方法给JS调用：微信支付
        mWebView.registerHandler("pay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("data==", data);
                mfunction = function;
                if ("1".equals(mPayType)) {
                    WXPayEntryActivity.weixinPay(data);
                } else {
                    AliPayInfo aliPayInfo = mGson.fromJson(data, AliPayInfo.class);
                    aliPay(aliPayInfo.getBody());
                }
            }
        });

        //[{"PayOrderNo":"20181129183548037228"},0.02,2]
        mWebView.registerHandler("packagePayment", (data, function) -> {
            LogUtils.e(data);
            if (data != null && data.contains(",")) {
                String[] array = data.split(",");
                mReturnSign = array[0];
                mPayType = array[2];

            }

        });


        mWebView.registerHandler("gf_payment", (data, function) -> {
            if ("1".equals(mPayType)) {
                WXPayEntryActivity.weixinPay(mReturnSign);
            } else {
                aliPay(mReturnSign);
            }
            //  LogUtils.e(data);
        });

        // 注册一个方法给JS调用：微信支付
        mWebView.registerHandler("payType", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("mPayType==", data);
                mPayType = data;
            }
        });

        //图片预览效果
        mWebView.registerHandler("Preview", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mPictureList = mGson.fromJson(data, PictureMessageEntity.class);
                Intent intent = new Intent(WebHeadViewActivity.this, PictureViewerActivity.class);
                intent.putExtra("pic_all", (ArrayList<String>) mPictureList.getUrl());
                intent.putExtra("pic_position", mPictureList.getPosition());
                startActivity(intent);
            }
        });

        //获取选择的默认PatientId
        mWebView.registerHandler("changeDefaultPatient", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                DefaultPatientEntity.DataBean patientEntity = mGson.fromJson(data, DefaultPatientEntity.DataBean.class);
                SpUtils.put(Contants.Id, patientEntity.getPatientId());
            }
        });
    }


    public void aliPay(final String orderInfo) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(WebHeadViewActivity.this);
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
            case PermissionUtil.REQUEST_CODE:
                if (PermissionUtil.INSTANCE.phoneCall(this)) {
                    call();
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
                ActivityCompat.requestPermissions(WebHeadViewActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
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
                mMyOkhttp.post().url(HttpUrl.ReportImageUpload).params(map).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<Entity>() {
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
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWebView.canGoBack()) {
                mWebView.goBack(); //goBack()表示返回WebView的上一页面
                return true;
            } else {
                finish();
                return true;
            }
        }
        return false;
    }


    /**
     * 微信支付
     *
     * @paramdata
     */
    private void weixinPay(String data) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        IWXAPI api = WXAPIFactory.createWXAPI(this, MainConstant.WEIXIN_APP_ID);
        // 将该app注册到微信
        api.registerApp(MainConstant.WEIXIN_APP_ID);
        Log.d(TAG, "data=" + data);
        try {
            JSONObject json = new JSONObject(data);
            PayReq req = new PayReq();
            req.appId = json.getString("appid");
            req.partnerId = json.getString("partnerid");
            req.prepayId = json.getString("prepayid");
            req.nonceStr = json.getString("noncestr");
            req.timeStamp = json.getString("timestamp");
            req.packageValue = json.getString("package");
            req.sign = json.getString("sign");
            req.extData = "app data"; // optional
            // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
            api.sendReq(req);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void call() {
        if (mMobile != null) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mMobile));
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        if (mWebView != null) {
            ViewParent viewParent = mWebView.getParent();
            if (viewParent != null) {
                ((ViewGroup) viewParent).removeView(mWebView);
            }

            mWebView.stopLoading();
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            try {
                mWebView.destroy();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onDestroy();
    }

    private void QueryConsultDoctorAppMessageByPage(String id) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetConsultRecord).params(requestMap).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ConsultMessageEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<ConsultMessageEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    ConsultMessageEntity consultMessageEntity = entity.getData();
                    Type caonsultJsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), caonsultJsonType);
                    prescriptionMessageEntity.setPatient(consultMessageEntity);
                    String json = mGson.toJson(prescriptionMessageEntity);
                    String doctorImId = "doc_" + consultMessageEntity.getDoctorId();
                    String doctorName = consultMessageEntity.getDoctorName();
                    Intent consultIntentTalk = new Intent(WebHeadViewActivity.this, ChatActivity.class);
                    Log.i("zdp", "json=" + json);
                    consultIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    consultIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, doctorImId);
                    consultIntentTalk.putExtra("consultMessageEntity", consultMessageEntity);
                    consultIntentTalk.putExtra("action", "pictureConsult");
                    consultIntentTalk.putExtra("prescriptionMessage", json);
                    startActivity(consultIntentTalk);
                    finish();
                } else {
                    Toast.makeText(WebHeadViewActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryvideoDoctorAppMessageByPage(String id) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetInquiryRecord).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<InquiryRecordListDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<InquiryRecordListDataEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    InquiryRecordListDataEntity inquiryRecordListDataEntity = entity.getData();
                    Type videoJsonType = new TypeToken<PrescriptionMessageEntity<InquiryRecordListDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<InquiryRecordListDataEntity> videoMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), videoJsonType);
                    videoMessageEntity.setPatient(inquiryRecordListDataEntity);
                    String videoJson = mGson.toJson(videoMessageEntity);
                    Intent videoIntentTalk = new Intent(WebHeadViewActivity.this, ChatActivity.class);
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, inquiryRecordListDataEntity.getPatientName());
                    videoIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, inquiryRecordListDataEntity.getApplicantIMId());
                    videoIntentTalk.putExtra("inquiryRecordListDataEntity", inquiryRecordListDataEntity);
                    videoIntentTalk.putExtra("action", "videoInterrogation");
                    videoIntentTalk.putExtra("prescriptionMessage", videoJson);
                    startActivity(videoIntentTalk);
                    finish();
                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(WebHeadViewActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void QueryRenewalDoctorAppMessageByPage(String id) {
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("Id", id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.GetPrescriptionApply).headers(headMap).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<OnlineRenewalDataEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<OnlineRenewalDataEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                if (entity.getCode() == 0) {
                    OnlineRenewalDataEntity onlineRenewalDataEntity = entity.getData();
                    Type renewalJsonType = new TypeToken<PrescriptionMessageEntity<OnlineRenewalDataEntity>>() {
                    }.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<OnlineRenewalDataEntity> renewalMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), renewalJsonType);
                    renewalMessageEntity.setPatient(onlineRenewalDataEntity);
                    String renewalJson = mGson.toJson(renewalMessageEntity);
                    String doctorImId = "doc_" + onlineRenewalDataEntity.getDoctorId();
                    String doctorName = onlineRenewalDataEntity.getDoctorName();
                    Intent renewalIntentTalk = new Intent(WebHeadViewActivity.this, ChatActivity.class);
                    renewalIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    renewalIntentTalk.putExtra("onlineRenewalDataEntity", onlineRenewalDataEntity);
                    renewalIntentTalk.putExtra("action", "onlineRenewal");
                    renewalIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, doctorImId);
                    renewalIntentTalk.putExtra("prescriptionMessage", renewalJson);
                    startActivity(renewalIntentTalk);
                    finish();
                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(WebHeadViewActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.WXPAYRESULT:
                int payCode = event.getPayCode();
                if (payCode == 0) {
                    if (mfunction != null) {
                        mfunction.onCallBack("success");
                    }
                } else {
                    if (mfunction != null) {
                        mfunction.onCallBack("fail");
                    }
                }
                break;
        }
    }

}
