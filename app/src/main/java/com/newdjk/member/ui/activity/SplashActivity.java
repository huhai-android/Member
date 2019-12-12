package com.newdjk.member.ui.activity;

import android.Manifest;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.login.LoginActivity;
import com.newdjk.member.ui.entity.AppConfigurationInfo;
import com.newdjk.member.ui.entity.LoginEntity;
import com.newdjk.member.ui.entity.Position;
import com.newdjk.member.ui.entity.PrescriptionMessageEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.CleanMessageUtil;
import com.newdjk.member.utils.LogOutUtil;
import com.newdjk.member.utils.MyMapUtil;
import com.newdjk.member.utils.NetWorkUtilcopy;
import com.newdjk.member.utils.SpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;
import me.weyye.hipermission.PermissionItem;

/**
 * Created by xiangzhihong on 2015/12/10 on 11:33.
 */
public class SplashActivity extends BasicActivity implements AMapLocationListener {
    @BindView(R.id.splash_image)
    ImageView splashImage;
    @BindView(R.id.splash_timeCount)
    TextView splashTimeCount;
    @BindView(R.id.step_over)
    Button stepOver;

    private MyMapUtil mMapUtil;
    private AMapLocation mMapLocation;
    private float latitude;
    private float longitude;
    private int mTime;
    private CountDownTimer mTimer;
    private Object appConfiguration;

    @Override
    protected int initViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init() {
        super.init();

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
    }

    @Override
    protected void initView() {
        if (mMapUtil == null) {
            mMapUtil = new MyMapUtil();
            mMapUtil.getCurrentLocation(0, this, this);
        }
        getAppConfiguration();
        mTime = 1 * 1000;
//        getAppConfiguration();
        mTimer = new CountDownTimer(mTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // tvTimer.setText(new StringBuffer("还剩").append(mI).append("s"));
            }

            @Override
            public void onFinish() {
                jumpPage();
            }
        }.start();
        splashImage.setImageResource(R.mipmap.launcher_image);
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

    @OnClick(R.id.step_over)
    public void stepOver(View view) {

    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                mMapLocation = aMapLocation;
                latitude = (float) mMapLocation.getLatitude();
                longitude = (float) mMapLocation.getLongitude();
                SpUtils.put(MainConstant.LAT, latitude);
                SpUtils.put(MainConstant.LON, longitude);
            }

        }
    }


    private void jumpPage() {
        boolean flag = SpUtils.getBoolean(Contants.IS_FIRST_USE, true);
        if (flag) {
            startActivity(new Intent(SplashActivity.this, GuideActivity.class));
        } else {
            List<PermissionItem> permissionItems = new ArrayList<>();
            permissionItems.add(new PermissionItem(Manifest.permission.CAMERA, "照相机", R.drawable.permission_ic_camera));
            permissionItems.add(new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "位置信息", R.drawable.permission_ic_location));
            permissionItems.add(new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "存储空间", R.drawable.permission_ic_storage));
            HiPermission.create(this)
                    .permissions(permissionItems)
                    .checkMutiPermission(new PermissionCallback() {
                        @Override
                        public void onClose() {

                        }

                        @Override
                        public void onFinish() {
                            String loginJson = SpUtils.getString(Contants.LoginJson);
                            if (TextUtils.isEmpty(loginJson)) {
                                toActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                SplashActivity.this.noAnimFinish();
                                return;
                            }
                            if (NetWorkUtilcopy.isNetworkAvailable(SplashActivity.this)) {
                                String jsonLogin = SpUtils.getString(Contants.LoginJson);
                                if (!TextUtils.isEmpty(jsonLogin)) {
                                    quitLogin();
                                } else {
                                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                                    toActivity(intent);
                                }

                            } else {
                                toast(getString(R.string.networkError));
                                toActivity(LoginActivity.getAct(SplashActivity.this));
                            }

                        }

                        @Override
                        public void onDeny(String permission, int position) {
                            switch (permission) {
                                case Manifest.permission.ACCESS_FINE_LOCATION:
                                    mMapUtil.getCurrentLocation(1, SplashActivity.this, SplashActivity.this);
                                    toActivity(MainActivity.getAct(mContext));
                                    finish();
                                    break;
                            }
                        }

                        @Override
                        public void onGuarantee(String permission, int position) {
                            switch (permission) {
                                case Manifest.permission.ACCESS_FINE_LOCATION:
                                    mMapUtil.getCurrentLocation(0, SplashActivity.this, SplashActivity.this);
                                    break;
                            }
                        }
                    });


        }
    }

    @Override
    public void finish() {
        super.finish();
    }

    private void quitLogin() {

        Map<String, String> headMap = new HashMap<>();
        headMap.put("Access-Client", BuildConfig.ACCESS_ClIENT);
        headMap.put("Access-RegistrationId", MyApplication.mRegistrationId);//极光推送的设备唯一性标识RegistrationID
        headMap.put("Access-Platform", "Android");
        headMap.put("Access-Appkey", BuildConfig.JPUSH_APPKEY);
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("Mobile", SpUtils.getString(Contants.userName));
        bodyMap.put("Type", "3");//登录类型:1验证码，2账号,3 令牌登陆
        bodyMap.put("Code", SpUtils.getString(Contants.Token));
        Log.i("LoginActivity", "appId=" + MyApplication.mRegistrationId);
        mMyOkhttp.post().url(HttpUrl.login).headers(headMap).params(bodyMap).tag(this).enqueue(new GsonResponseHandler<LoginEntity>() {
            @Override
            public void onSuccess(int statusCode, LoginEntity entituy) {

                if (entituy.getCode() == 0) {
                    myShared(entituy.getData());
                    toActivity(MainActivity.getAct(mContext));
                } else {
                    toActivity(new Intent(SplashActivity.this, LoginActivity.class));
                }
                SplashActivity.this.noAnimFinish();
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CleanMessageUtil.clearAllCache(SplashActivity.this);
                LogOutUtil.getInstance().loginOut(SplashActivity.this, true);
                Log.e("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                toActivity(LoginActivity.getAct(SplashActivity.this));
                SplashActivity.this.noAnimFinish();
                //CommonMethod.requestError(statusCode, errorMsg);



            }
        });
    }

    /**
     * 本地保存  主要是更新一下经纬度
     *
     * @param
     */
    private void myShared(LoginEntity.DataEntity entituy) {
        if (entituy == null) return;
        Gson mGson = new Gson();
        AppConfigurationInfo.DataBean appInfo = mGson.fromJson(SpUtils.getString(Contants.AppInfo), AppConfigurationInfo.DataBean.class);
        PrescriptionMessageEntity prescriptionMessageEntity = new PrescriptionMessageEntity();
        prescriptionMessageEntity.setPatient(entituy);
        prescriptionMessageEntity.setAppInfo(appInfo);
        Position position = null;
        if (latitude == 0.0f && longitude == 0.0f) {
            float lat = (float) SpUtils.get(MainConstant.LAT, 0.0f);
            float lon = (float) SpUtils.get(MainConstant.LON, 0.0f);
            position = new Position(lat, lon);
        } else {
            position = new Position(latitude, longitude);
        }
        prescriptionMessageEntity.setPosition(position);
        String mUserInfoGson = mGson.toJson(prescriptionMessageEntity);
        SpUtils.put(Contants.LoginJson, mUserInfoGson);
    }

    /**
     * 获取应用配置信息
     */
    private void getAppConfiguration() {
        SpUtils.put(Contants.OrgId, "0");
        SpUtils.put(Contants.AreaId, "0");
    }
}
