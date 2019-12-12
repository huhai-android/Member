package com.newdjk.member.ui.activity.min;

import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.login.LoginActivity;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.utils.MyCountDownTimer;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.StrUtil;
import com.newdjk.member.views.LoadDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

public class ModifyPasswordActivity extends BasicActivity {


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
    @BindView(R.id.mOldMobile)
    EditText mOldMobile;
    @BindView(R.id.input_bd_code)
    EditText inputBdCode;
    @BindView(R.id.tv_bd_code)
    TextView tvBdCode;
    @BindView(R.id.relate_code_bd)
    RelativeLayout relateCodeBd;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.mConfirmPassword)
    EditText mConfirmPassword;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    private MyCountDownTimer mcdt;


    @Override
    protected int initViewResId() {
        return R.layout.activity_modify_password;
    }

    @Override
    protected void initView() {
        initTitle("修改密码").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);
        mcdt = new MyCountDownTimer(tvBdCode, 60000, 1000);
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
        if (!StrUtil.isMobile(StrUtil.getString(mOldMobile))) {
            toast("手机号码格式不正确");
        } else {
            loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Mobile", StrUtil.getString(mOldMobile));
            map.put("MobileCode", "");
            mMyOkhttp.post().url(HttpUrl.SendMobileCode).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
                        mcdt.start();

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

    /**
     * 下一步
     */
    @OnClick(R.id.btn_submit)
    public void onBtnSubmitClicked() {
        if (!StrUtil.isMobile(StrUtil.getString(mOldMobile))) {
            toast("手机号输入有误,请重新输入");
            return;
        } else if (!StrUtil.isNotEmpty(inputBdCode, true)) {
            toast("验证码不能为空");
            return;
        } else if (!StrUtil.isNotEmpty(inputPassword, true)) {
            toast("请输入密码");
        } else if (!isValidPassword(inputPassword.getText().toString())) {
            toast("密码为8-12位数字和字母组合");
            return;
        } else if (!StrUtil.isNotEmpty(mConfirmPassword, true)) {
            toast("请再次输入密码");
        } else if (!StrUtil.getString(inputPassword).equals(StrUtil.getString(mConfirmPassword))) {
            toast("两次输入的密码不一致");
        } else {

            loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Mobile", StrUtil.getString(mOldMobile));
            map.put("MobileCode", StrUtil.getString(inputBdCode));
            map.put("NewPass", StrUtil.getString(inputPassword));
            map.put("TwoPass", StrUtil.getString(mConfirmPassword));
            mMyOkhttp.post().url(HttpUrl.ModifyPassword).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
                        SpUtils.put(Contants.inputPassword, "");
                        startActivity(new Intent(ModifyPasswordActivity.this, LoginActivity.class));
                        finish();
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


    @Override
    protected void otherViewClick(View view) {

    }

    private boolean isValidPassword(String password) {
        if (!isMatcherFinded("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,12}$", password)) {
            return false;
        }
        return true;
    }

    public static boolean isMatcherFinded(String patternStr, CharSequence input) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

}
