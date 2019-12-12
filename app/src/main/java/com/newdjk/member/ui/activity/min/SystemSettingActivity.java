package com.newdjk.member.ui.activity.min;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.AboutUsActivity;
import com.newdjk.member.ui.activity.ShopDebugActivity;
import com.newdjk.member.ui.activity.login.LoginActivity;
import com.newdjk.member.utils.CleanMessageUtil;
import com.newdjk.member.utils.LogOutUtil;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class SystemSettingActivity extends BasicActivity {

    @BindView(R.id.mCacheCount)
    TextView mCacheCount;
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
    @BindView(R.id.mClearCache)
    RelativeLayout mClearCache;
    @BindView(R.id.mExitlogin)
    AppCompatButton mExitlogin;

    @BindView(R.id.mSuggesstion)
    RelativeLayout mSuggesstion;
    @BindView(R.id.mAboutUs)
    RelativeLayout mAboutUs;
    @BindView(R.id.modifyPwd)
    RelativeLayout modifyPwd;
    @BindView(R.id.mAccount)
    TextView mAccont;


    String totalCacheSize = "";
    @BindView(R.id.shopdebug)
    RelativeLayout shopdebug;
    private String mobile;

    @Override
    protected int initViewResId() {
        return R.layout.activity_system_setting;
    }

    @Override
    protected void initView() {
        initTitle("设置").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);


        //获取缓存数据
        getCache();
        //显示缓存数据的大小
        mCacheCount.setText(totalCacheSize);

        //  显示手机号码的数据
        mobile = (String) SpUtils.get(Contants.Mobile, "");
        mAccont.setText(mobile);
        if (BuildConfig.IS_DEBUG){
            shopdebug.setVisibility(View.VISIBLE);
        }

    }

    private String getCache() {
        try {
            totalCacheSize = CleanMessageUtil.getTotalCacheSize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalCacheSize;

    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
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

    @OnClick({R.id.mAccount, R.id.modifyPwd, R.id.mAboutUs, R.id.mSuggesstion, R.id.mClearCache, R.id.mExitlogin, R.id.shopdebug})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mClearCache:
                CleanMessageUtil.clearAllCache(this);
                ToastUtil.setToast("已经清除了" + totalCacheSize);
                mCacheCount.setText(getCache());
                totalCacheSize = getCache();
                break;
            case R.id.mExitlogin:

                CleanMessageUtil.clearAllCache(this);
                LogOutUtil.getInstance().loginOut(this, true);

//                login();
                break;
            case R.id.mAccount:
                Intent intent = new Intent(this, ModifyMobileActivity.class);
                intent.putExtra("mobile", mobile);
                toActivity(intent);
                break;

            case R.id.modifyPwd:
                Intent modifyPasswordIntent = new Intent(this, ModifyPasswordActivity.class);
                modifyPasswordIntent.putExtra("mobile", mobile);
                toActivity(modifyPasswordIntent);
                break;
            case R.id.mAboutUs:
                Intent aboutIntent = new Intent(SystemSettingActivity.this, AboutUsActivity.class);
                startActivity(aboutIntent);
                break;
            case R.id.mSuggesstion:
                toActivity(new Intent(this, SuggestionActivity.class));
                break;

            case R.id.shopdebug:
                Intent debugindent = new Intent(SystemSettingActivity.this, ShopDebugActivity.class);
                startActivity(debugindent);

                break;
        }
    }

    /**
     * 登录
     */
    public void login() {
        SpUtils.put(Contants.IS_FIRST_USE, false);
        toActivity(LoginActivity.getAct(this));
        finish();
    }


}
