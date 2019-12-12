package com.newdjk.member.ui.fragment;


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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.R;
import com.newdjk.member.alipay.PayResult;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.ChatActivity;
import com.newdjk.member.ui.activity.MainActivity;
import com.newdjk.member.ui.activity.OrderNoteActivity;
import com.newdjk.member.ui.activity.PayFailActivity;
import com.newdjk.member.ui.activity.PaySuccessActivity;
import com.newdjk.member.ui.activity.UserNoteActivity;
import com.newdjk.member.ui.entity.AliPayInfo;
import com.newdjk.member.ui.entity.ConsultMessageEntity;
import com.newdjk.member.ui.entity.DefaultPatientEntity;
import com.newdjk.member.ui.entity.DeviceInfo;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.ImDoctorEntity;
import com.newdjk.member.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.member.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.member.ui.entity.PrescriptionMessageEntity;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;
import com.newdjk.member.ui.entity.ResponseEntity;
import com.newdjk.member.ui.entity.ServicePayInfo;
import com.newdjk.member.ui.entity.ServicePayWxInfo;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.ImageBase64;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.LoadDialog;
import com.newdjk.member.views.SelectedPictureDialog;
import com.newdjk.member.wxapi.WXPayEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 功能
 */
public class FunctionFragment extends BasicFragment   {

    private static final String TAG = "FunctionFragment";
    @BindView(R.id.mWebView)
    BridgeWebView mWebView;
    Unbinder unbinder;
    private String mUrl = BuildConfig.H5_IP_HOST+"doctor-list?hide=1?";
    private CallBackFunction mfunction;
    private static final int SDK_PAY_FLAG = 1;

    private String mPayType;
    private Gson mGson;
    private String mUserInfo;

    private final static int REQ_PERMISSION_CODE = 0x100;
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
                            Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                            if (mfunction != null) {
                                mfunction.onCallBack("success");
                            }

                            if (mFlag) {
                                Intent intent = new Intent(getActivity(), PaySuccessActivity.class);
                                intent.putExtra("payPrice", mPayInfo.getPrice());
                                intent.putExtra("PayChannel", mPayInfo.getPayChannel());
                                intent.putExtra("go_path", "webView");
                                toActivity(intent);
                                mFlag = false;
                            }

                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                            if (mfunction != null) {
                                mfunction.onCallBack("fail");
                            }

                            if (mFlag) {
                                Intent intent = new Intent(getActivity(), PayFailActivity.class);
                                intent.putExtra("payPrice", mPayInfo.getPrice());
                                intent.putExtra("PayChannel", mPayInfo.getPayChannel());
                                intent.putExtra("go_path", "webView");
                                toActivity(intent);
                            }
                        }
                        break;


                    }
                default:
                    break;
            }
        }
    };
    private boolean mFlag;
    private ServicePayWxInfo mWxInfo;
    private ServicePayInfo mPayInfo;
    private SelectedPictureDialog mSelectedPictureDialog;
    private String mPicturePath;
    private CallBackFunction mFunction;


    public static FunctionFragment getFragment() {
        return new FunctionFragment();
    }

    @Override
    protected int initViewResId() {
        return R.layout.fragment_function;
    }

    @Override
    protected void initView() {

        mGson = new Gson();
        // 这里一定要加这个  不然会有缓存
        mWebView.clearCache(true);
        mWebView.clearHistory();
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.FAR);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setTextZoom(100);  //消除系统大小的设置对webview字体大小的影响
        mWebView.getSettings().setDomStorageEnabled(true); //解决加载不出webview白屏问题
        mWebView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebViewClient(new BridgeWebViewClient(mWebView) {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);


            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LoadDialog.clear();
                sendDataToHtml();
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
//      mWebView.loadUrl("file:///android_asset/index.html#/seekDoctor");
        mWebView.loadUrl(mUrl);
        initJsBridge();
    }

    private void sendDataToHtml() {
        String mUserInfo = SpUtils.getString(Contants.LoginJson);
        if (mWebView != null) {
            mWebView.callHandler("UserInfo", mUserInfo, new CallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    Log.d("onCallBack","data="+data);
                }
            });
        }

    }

    private void initJsBridge() {
        // 注册一个方法给JS调用：关闭网页
        mWebView.registerHandler("Back", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("handler","Back");
                // 关闭页面
//                if (mWebView.canGoBack()) {
//                    mWebView.canGoBack();
//                } else {
                    ((MainActivity) getActivity()).toHomeFragment();
//                }
            }
        });
        // 注册一个方法给JS调用：微信支付
        mWebView.registerHandler("pay", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("data==",data);
                mfunction = function;
                if ("1".equals(mPayType)) {
                    WXPayEntryActivity.weixinPay(data);
                } else {
                    AliPayInfo aliPayInfo = mGson.fromJson(data, AliPayInfo.class);
                    aliPay(aliPayInfo.getBody());
                }
            }
        });

        // 注册一个方法给JS调用：微信支付
        mWebView.registerHandler("payType", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("mPayType==",data);
                mPayType = data;
            }
        });



        mWebView.registerHandler("packagePayment2", (data, function) -> {
            if (data.endsWith("1}")) {
                mWxInfo = mGson.fromJson(data, ServicePayWxInfo.class);
                mPayType = String.valueOf(mWxInfo.getPayChannel());
            } else if (data.endsWith("2}")) {
                ServicePayInfo info = mGson.fromJson(data, ServicePayInfo.class);
                mPayType = String.valueOf(info.getPayChannel());
                mPayInfo = info;
            }
        });


        //去购买服务包
        mWebView.registerHandler("toBuyService", (data, function) -> {
            loading(true);
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("ServicePackId", data);
            String defaultPatientJson = SpUtils.getString(Contants.DefaultPatientAccountJson);
            DefaultPatientEntity.DataBean defaultPatient
                    = mGson.fromJson(defaultPatientJson, DefaultPatientEntity.DataBean.class);
            mMyOkhttp.get().url(HttpUrl.QueryServicePackAndDetailByPackId).headers(HeadUitl.instance.getHeads()).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<QueryServicePackAndDetailByPackIdEntity>() {
                @Override
                public void onSuccess(int statusCode, final QueryServicePackAndDetailByPackIdEntity response) {
                    LoadDialog.clear();
                    if (response.getCode() == 0) {
                        final QueryServicePackAndDetailByPackIdEntity queryServicePackAndDetailByPackIdEntity = response;
                        boolean flag = false;
                        int size = queryServicePackAndDetailByPackIdEntity.getData().getDetails().size();
                        for (int i = 0; i < size; i++) {
                            if (queryServicePackAndDetailByPackIdEntity.getData().getDetails().get(i).getSericeItemCode().equals("1501")) {
                                flag = true;
                            }
                        }
                        if (flag) {
                            String url = HttpUrl.HaveBuyDevice + "?accountId=" + SpUtils.getInt(Contants.AccountId,0) + "&patientId=" + defaultPatient.getPatientId();
                            mMyOkhttp.get().headers(HeadUitl.instance.getHeads()).url(url).tag(this).enqueue(new GsonResponseHandler<DeviceInfo>() {
                                @Override
                                public void onSuccess(int statusCode, DeviceInfo response) {
                                    if (response.getCode() == 1) {
                                        toast(response.getMessage());
                                    } else {
                                        Intent intent = new Intent(getActivity(), UserNoteActivity.class);
                                        intent.putExtra("OrgId", queryServicePackAndDetailByPackIdEntity.getData().getOrgId());
                                        intent.putExtra("ServicePackId", queryServicePackAndDetailByPackIdEntity.getData().getServicePackId());
                                        intent.putExtra("detailsBean", (Serializable) queryServicePackAndDetailByPackIdEntity.getData().getDetails());
                                        intent.putExtra("ServiceLong", queryServicePackAndDetailByPackIdEntity.getData().getServiceLong());
                                        intent.putExtra("EndTime", queryServicePackAndDetailByPackIdEntity.getData().getEndTime());
                                        intent.putExtra("payPrice", queryServicePackAndDetailByPackIdEntity.getData().getPrice());
                                        intent.putExtra("go_path", "webView");
                                        intent.putExtra("originPrice", queryServicePackAndDetailByPackIdEntity.getData().getOriginalPrice());
                                        toActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailures(int statusCode, String errorMsg) {
                                    CommonMethod.requestError(statusCode, errorMsg);
                                }
                            });
                        } else {

                            String defaultPatientStr = SpUtils.getString(Contants.DefaultPatientAccountJson);

                            if (TextUtils.isEmpty(defaultPatientStr)) {
                                return;
                            }
                            DefaultPatientEntity.DataBean mSelectPatient = mGson.fromJson(defaultPatientStr, DefaultPatientEntity.DataBean.class);
                            Intent intent = new Intent(getActivity(), OrderNoteActivity.class);
                            intent.putExtra("PatientName", mSelectPatient.getPatientName());
                            intent.putExtra("Patient", mSelectPatient);
                            intent.putExtra("OrgId", queryServicePackAndDetailByPackIdEntity.getData().getOrgId());
                            intent.putExtra("ServicePackId", queryServicePackAndDetailByPackIdEntity.getData().getServicePackId());
                            intent.putExtra("detailsBean", (Serializable) queryServicePackAndDetailByPackIdEntity.getData().getDetails());
                            intent.putExtra("ServiceLong", queryServicePackAndDetailByPackIdEntity.getData().getServiceLong());
                            intent.putExtra("EndTime", queryServicePackAndDetailByPackIdEntity.getData().getEndTime());
                            intent.putExtra("payPrice", queryServicePackAndDetailByPackIdEntity.getData().getPrice());
                            intent.putExtra("originPrice", queryServicePackAndDetailByPackIdEntity.getData().getOriginalPrice());
                            intent.putExtra("go_path", "webView");
                            toActivity(intent);
                        }
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        });


        mWebView.registerHandler("APP", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                mFunction = function;
                if (data != null && data.equals("Img")) {
                    mSelectedPictureDialog = new SelectedPictureDialog(getActivity());
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


        mWebView.registerHandler("gf_payment", (data, function) -> {
            mFlag = true;
            if ("1".equals(mPayType)) {
                if (mWxInfo.getPayInfo().getAppid() == null) {
                    Intent intent = new Intent(getActivity(), PaySuccessActivity.class);
                    intent.putExtra("payPrice", mWxInfo.getPrice());
                    intent.putExtra("PayChannel", mWxInfo.getPayChannel());
                    intent.putExtra("go_path", "webView");
                    toActivity(intent);
                    return;
                }
                WXPayEntryActivity.weixinPay(mGson.toJson(mWxInfo.getPayInfo()));
            } else {
                if (mPayInfo.getPayInfo().getBody() == null) {
                    Intent intent = new Intent(getActivity(), PaySuccessActivity.class);
                    intent.putExtra("payPrice", mPayInfo.getPrice());
                    intent.putExtra("PayChannel", mPayInfo.getPayChannel());
                    intent.putExtra("go_path", "webView");
                    toActivity(intent);
                    return;
                }
                aliPay(mPayInfo.getPayInfo().getBody());
            }
            //  LogUtils.e(data);
        });


        // 患者支付成功之后跳转到ChatActivity页面的监听
        mWebView.registerHandler("ImInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.d("ImInfo===",data);
                ImDoctorEntity imDoctorEntity = mGson.fromJson(data, ImDoctorEntity.class);
                String serviceCode = imDoctorEntity.getServiceCode();
                String recordId = String.valueOf(imDoctorEntity.getRelationId());
                if (serviceCode.equals("1101")||serviceCode.equals("1201")) {
                    QueryConsultDoctorAppMessageByPage(recordId);
                } else if (serviceCode.equals("1102")||serviceCode.equals("1202")) {
                    QueryvideoDoctorAppMessageByPage(recordId);
                } else if (serviceCode.equals("1103")) {
                    QueryRenewalDoctorAppMessageByPage(recordId);
                }

            }
        });
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            switch (requestCode) {
                case 1:
                    uploadPicture(mPicturePath);
                    break;
                case 3:
                    Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
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
                mMyOkhttp.post().url(HttpUrl.ReportImageUpload).headers(HeadUitl.instance.getHeads()).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
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

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }

            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(getActivity(),
                        (String[]) permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(getActivity(), "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
    }



    public void aliPay(final String orderInfo){
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
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

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        mUserInfo = (String) SpUtils.get(Contants.MUSERINFO, "");
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public  void goBack(BackListener listener){
        if (mWebView.canGoBack()) {
            mWebView.goBack(); //goBack()表示返回WebView的上一页面
        } else {
            listener.goBck();
        }
    }
    public interface BackListener{
        void goBck();
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
                    Type caonsultJsonType = new TypeToken<PrescriptionMessageEntity<ConsultMessageEntity>>() {}.getType();
                    //  LoginEntity LoginEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), com.newdjk.doctor.ui.entity.LoginEntity.class);
                    PrescriptionMessageEntity<ConsultMessageEntity> prescriptionMessageEntity = mGson.fromJson(SpUtils.getString(Contants.LoginJson), caonsultJsonType);
                    prescriptionMessageEntity.setPatient(consultMessageEntity);
                    String json = mGson.toJson(prescriptionMessageEntity);
                    String doctorImId = consultMessageEntity.getDoctorIMId();
                    String doctorName = consultMessageEntity.getDoctorName();
                    Intent consultIntentTalk = new Intent(mContext, ChatActivity.class);
                    Log.i("zdp", "json=" + json);
                    consultIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    consultIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, doctorImId);
                    consultIntentTalk.putExtra("consultMessageEntity", consultMessageEntity);
                    consultIntentTalk.putExtra("drId", consultMessageEntity.getDoctorId());
                    consultIntentTalk.putExtra("action", "pictureConsult");
                    consultIntentTalk.putExtra("prescriptionMessage", json);
                    startActivity(consultIntentTalk);
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
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
                    String doctorImId = onlineRenewalDataEntity.getDoctorIMId();
                    String doctorName = onlineRenewalDataEntity.getDoctorName();
                    Intent renewalIntentTalk = new Intent(mContext, ChatActivity.class);
                    renewalIntentTalk.putExtra(Contants.FRIEND_NAME, doctorName);
                    renewalIntentTalk.putExtra("onlineRenewalDataEntity", onlineRenewalDataEntity);
                    renewalIntentTalk.putExtra("action", "onlineRenewal");
                    renewalIntentTalk.putExtra("drId", onlineRenewalDataEntity.getDoctorId());
                    renewalIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, doctorImId);
                    renewalIntentTalk.putExtra("prescriptionMessage", renewalJson);
                    startActivity(renewalIntentTalk);
                    //  Log.i("HomeFragment","entity="+entity.getData().toString());
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
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
                    Intent videoIntentTalk = new Intent(mContext, ChatActivity.class);
                    videoIntentTalk.putExtra(Contants.FRIEND_NAME, inquiryRecordListDataEntity.getDoctorName());
                    videoIntentTalk.putExtra(Contants.FRIEND_IDENTIFIER, inquiryRecordListDataEntity.getDoctorIMId());
                    videoIntentTalk.putExtra("inquiryRecordListDataEntity", inquiryRecordListDataEntity);
                    videoIntentTalk.putExtra("drId", inquiryRecordListDataEntity.getDoctorId());
                    videoIntentTalk.putExtra("action", "videoInterrogation");
                    videoIntentTalk.putExtra("prescriptionMessage", videoJson);
                    startActivity(videoIntentTalk);
                } else {
                    Toast.makeText(mContext, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

}
