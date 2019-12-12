package com.newdjk.member.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.utils.MyCountDownTimer;
import com.newdjk.member.utils.StrUtil;
import com.newdjk.member.views.LoadDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 忘记密码
 */
public class ForgetActivity extends BasicActivity {


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
    @BindView(R.id.input_user)
    EditText inputUser;
    @BindView(R.id.input_bd_code)
    EditText inputBdCode;
    @BindView(R.id.tv_bd_code)
    TextView tvCode;
    @BindView(R.id.relate_code_bd)
    RelativeLayout relateCodeBd;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    private MyCountDownTimer mcdt;

    public static Intent getIntent(Context context) {
        return new Intent(context, ForgetActivity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initView() {
        initBackTitle("找回密码").setTitleBgRes(R.color.white);
        mcdt = new MyCountDownTimer(tvCode, 60000, 1000);
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


    /**
     * 发送验证码
     */
    @OnClick(R.id.tv_bd_code)
    public void onCodeClicked() {
        requestCode();
    }

    /**
     * 发送验证码请求
     */
    public void requestCode() {
        if (!StrUtil.isMobile(StrUtil.getString(inputUser))) {
            toast("手机号码格式不正确");
        } else {
            loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Mobile", StrUtil.getString(inputUser));
            map.put("MobileCode", "");
            mMyOkhttp.post().url(HttpUrl.SendMobileCode).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
                        mcdt.start();
                    }
                    toast(entity.getMessage());
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    LoadDialog.clear();
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mcdt != null) {
            mcdt.clear();
            mcdt.cancel();
        }
    }

    /**
     * 下一步
     */
    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        if (!StrUtil.isMobile(StrUtil.getString(inputUser))) {
            toast("手机号码格式不正确");
            return;
        } else if (!StrUtil.isNotEmpty(inputBdCode, true)) {
            toast("验证码不能为空");
            return;
        } else {
            loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Mobile", StrUtil.getString(inputUser));
            map.put("MobileCode", StrUtil.getString(inputBdCode));
            mMyOkhttp.post().url(HttpUrl.QueryPatientAccountByMobileCode).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
//                            finish();
                        toActivity(ResettingActivity.getIntent(mContext, StrUtil.getString(entity.getData())));
                    } else {
                        toast(entity.getMessage());
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    LoadDialog.clear();
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });

        }
    }



}
