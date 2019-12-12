package com.newdjk.member.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.service.MyService;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.login.AgreementActivity;
import com.newdjk.member.ui.entity.AboutUsEntity;
import com.newdjk.member.ui.entity.AppEntity;
import com.newdjk.member.utils.AppUpdateUtils;
import com.newdjk.member.utils.AppUtils;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.HomeUtils;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.SystemUitl;
import com.newdjk.member.utils.ToastUtil;
import com.newdjk.member.views.LoadDialog;

import butterknife.BindView;

public class
AboutUsActivity extends BasicActivity {


    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.mVersion)
    TextView mVersion;
    @BindView(R.id.tv_agreement)
    TextView tv_agreement;
    @BindView(R.id.tv_mail)
    TextView tvMail;
    @BindView(R.id.tv_tel)
    TextView tvTel;
    @BindView(R.id.tv_update_desc)
    TextView tvUpdateDesc;
    @BindView(R.id.tv_update)
    TextView tvUpdate;
    @BindView(R.id.lv_update)
    LinearLayout lvUpdate;
    private AppEntity appentity;
    private int mMustupdate = 0;
    private String mApkUrl = "";
    private String[] mAppInfo;
    private final static String TAG = "aboutUs";

    @Override
    protected int initViewResId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView() {
        initTitle("关于我们").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);
        getLocalVersion(MyApplication.getContext());
        checkUpdate();
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
    }


    public void getLocalVersion(Context context) {
        String localVersion;
        try {
            PackageInfo packageInfo = context.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            localVersion = packageInfo.versionName;
            mVersion.setText("当前版本号：V" + localVersion);
            Log.d("TAG", "本软件的版本号。。" + localVersion);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initListener() {
        tv_agreement.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        mAppInfo = SystemUitl.packageCode(this);
        loadInfo();
        obtainAboutInfo();
    }

    private void obtainAboutInfo() {
        loading(true);
        Log.d(TAG, "obtainAboutInfo: " + HttpUrl.GetAboutInfo);
        mMyOkhttp.post().url(HttpUrl.GetAboutInfo).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<AboutUsEntity>() {
            @Override
            public void onSuccess(int statusCode, AboutUsEntity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    tvTel.setText(new StringBuffer("客服热线: ").append(response.getData().getMobile()));
                    tvMail.setText(new StringBuffer("客服邮箱: ").append(response.getData().getMail()));
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
            }
        });
    }

    private void loadInfo() {
        if (mAppInfo != null) {
            if (mAppInfo.length != 0) {
                mVersion.setText("当前版本: v" + mAppInfo[1]);
            }
        }

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.tv_agreement:
                Intent intent = new Intent(AboutUsActivity.this, AgreementActivity.class);
                intent.putExtra("userInfo", SpUtils.getString(Contants.LoginJson));
                startActivity(intent);
                // toActivity(AgreementActivity.getInten(mContext));
                break;
            case R.id.tv_update:
                checkUpdate2();
                break;
        }
    }


    private void checkUpdate2() {

        HomeUtils.INSTANCE.checkVersion(new HomeUtils.UpdateListener() {
            @Override
            public void success(final AppEntity entity) {
                if (MyService.isDownload) {
                    ToastUtil.setToast("app正在升级中，请稍后");
                    return;
                }
                AppUpdateUtils.getInstance().update(entity, AboutUsActivity.this, AboutUsActivity.this);

            }


            @Override
            public void failed(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

            }
        });
    }
    private void checkUpdate() {

        HomeUtils.INSTANCE.checkVersion(new HomeUtils.UpdateListener() {
            @Override
            public void success(final AppEntity entity) {
                appentity = entity;
                if (entity.getCode() == 0) {
                    if (entity.getData() != null) {
                        final String apkUrl = entity.getData().getAppPath();
                        int apkCode = Integer.parseInt(entity.getData().getAppVersion());
                        int versionCode = AppUtils.getAppVersionCode(AboutUsActivity.this);
                        mMustupdate = entity.getData().getMustUpdate();
                        Log.d(TAG, apkCode + "  " + versionCode);
                        mApkUrl = apkUrl;
                        if (apkCode > versionCode) {
                            tvUpdateDesc.setText("检测到当前最新版本为" + entity.getData().getReleaseVersion());
                            tvUpdate.setVisibility(View.VISIBLE);
                        } else {
                            tvUpdateDesc.setText("当前版本已经是最新版本");
                            tvUpdate.setVisibility(View.GONE);
                        }
                    }
                }
            }


            @Override
            public void failed(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

            }
        });
    }
}
