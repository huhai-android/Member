package com.newdjk.member.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.activity.SplashActivity;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.StrUtil;
import com.newdjk.member.views.LoadDialog;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 重置密码
 */
public class ResettingActivity extends BasicActivity {
    @BindView(R.id.input_Password)
    EditText inputPassword;
    @BindView(R.id.input_new_Password)
    EditText inputNewPassword;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    private String userId = "";//1 = 用户id

    public static Intent getIntent(Context context, String userId) {
        Intent intent = new Intent(context, ResettingActivity.class);
        intent.putExtra("userId", userId);
        return intent;
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_resetting;
    }

    @Override
    protected void initView() {
        initBackTitle("找回密码");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        userId = getIntent().getStringExtra("userId");
    }

    @Override
    protected void otherViewClick(View view) {

    }

    /**
     * 完成
     */
    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (isNull()) {
            if (!isValidPassword(inputPassword.getText().toString())) {
                toast("密码为8-12位数字和字母组合");
                return;
            }
            loading(true);
            Map<String, String> map = new HashMap<>();
            map.put("Id", StrUtil.getString(userId));
            map.put("NewPass", StrUtil.getString(inputPassword));
            map.put("TwoPass", StrUtil.getString(inputNewPassword));
            mMyOkhttp.post().url(HttpUrl.ChangePatientAccountPassword).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entituy) {
                    LoadDialog.clear();
                    if (entituy.getCode() == 0) {
//                        finish();
                        toActivity(new Intent(ResettingActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        toast(entituy.getMessage());
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

    public boolean isNull() {
        if (TextUtils.isEmpty(StrUtil.getString(inputPassword))) {
            toast("请输入新密码");
            return false;
        }
        if (TextUtils.isEmpty(StrUtil.getString(inputNewPassword))) {
            toast("请输入确认密码");
            return false;
        }
        if (StrUtil.getString(inputPassword).length() < 6) {
            toast("新密码至少8位");
            return false;
        } else if (!StrUtil.getString(inputPassword).equals(StrUtil.getString(inputNewPassword))) {
            toast("两次密码不一致");
            return false;
        }
        return true;
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
