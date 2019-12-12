package com.newdjk.member.ui.activity.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.MainActivity;
import com.newdjk.member.ui.entity.AgreementEntity;
import com.newdjk.member.ui.entity.AppConfigurationInfo;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.LoginEntity;
import com.newdjk.member.ui.entity.Position;
import com.newdjk.member.ui.entity.PrescriptionMessageEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.MyCountDownTimer;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.StrUtil;
import com.newdjk.member.views.LoadDialog;
import com.newdjk.member.views.MyCheckBox;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 注册
 */
public class RegisterActivity extends BasicActivity {

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
    TextView tvBdCode;
    @BindView(R.id.relate_code_bd)
    RelativeLayout relateCodeBd;
    @BindView(R.id.input_password)
    EditText inputPassword;
    @BindView(R.id.CheckBox)
    MyCheckBox CheckBox;
    @BindView(R.id.cb_agreement)
    android.widget.CheckBox cbAgreement;
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    @BindView(R.id.btn_submit)
    AppCompatButton btnSubmit;
    private MyCountDownTimer mcdt;
    private String openId;

    public static Intent getAct(Context context) {
        return new Intent(context, RegisterActivity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.tm, "");
    }

    @Override
    protected void initListener() {
        CheckBox.setOnCheckedChangeListener(onCheckedChangeListener);
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            openId = intent.getStringExtra("OpenId");
        }
        mcdt = new MyCountDownTimer(tvBdCode, 60000, 1000);
    }


    @Override
    protected void otherViewClick(View view) {

    }

    /**
     * 用户协议
     */
    @OnClick(R.id.tv_agreement)
    public void onAgreementClicked() {
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setDoctor("1");
        String json = new Gson().toJson(agreementEntity);
        Intent intent = new Intent(RegisterActivity.this, AgreementActivity.class);
        intent.putExtra("userInfo", json);
        startActivity(intent);
        //toActivity(AgreementActivity.getInten(mContext));
    }


    /**
     * 显示密码
     */
    CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                //如果选中，显示密码
                inputPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            } else {
                //否则隐藏密码
                inputPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            inputPassword.setSelection(inputPassword.length());//将光标移至文字末尾
        }
    };



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
        if (StrUtil.isMobile(StrUtil.getString(inputUser))) {
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
        } else {
            toast("手机号码格式不正确");
        }
    }


    /**
     * 注册
     */
    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        requstVerifyMobileCode();
    }


    /**
     * 验证会员手机验证码(注册验证)
     */
    public void requstVerifyMobileCode() {
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
            mMyOkhttp.post().url(HttpUrl.VerifyMobileCode).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                @Override
                public void onSuccess(int statusCode, Entity entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
                        requstRegister();
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

    /**
     * 注册请求
     */
    public void requstRegister() {
        if (!StrUtil.isMobile(StrUtil.getString(inputUser))) {
            toast("手机号码格式不正确");
            return;
        } else if (!StrUtil.isNotEmpty(inputBdCode, true)) {
            toast("验证码不能为空");
            return;
        } else if (!StrUtil.isNotEmpty(inputPassword, true)) {
            toast("登录密码不能为空");
            return;
        } else if (!isValidPassword(inputPassword.getText().toString())) {
            toast("密码为8-12位数字和字母组合");
            return;
        } else if (!cbAgreement.isChecked()) {
            toast("请确认已阅读用户协议");
            return;
        } else {
            loading(true);
            Map<String, String> map = new HashMap<>();
            if (TextUtils.isEmpty(openId)) {
                map.put("Mobile", StrUtil.getString(inputUser));//                手机号
                map.put("PatientPassword", StrUtil.getString(inputPassword));//                密码
                map.put("MobileCode", StrUtil.getString(inputBdCode));//                验证码
                map.put("AppId", "newdjkpatapp");
                map.put("Source", "1");//
                map.put("RegId", MyApplication.mRegistrationId);
                mMyOkhttp.post().url(HttpUrl.RegistPatient).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                    @Override
                    public void onSuccess(int statusCode, Entity entituy) {
                        LoadDialog.clear();
                        if (entituy.getCode() == 0) {
                            login();
//                            EventBus.getDefault().post(new LoginEb(StrUtil.getString(inputUser), StrUtil.getString(inputPassword)));
//                            finish();
                        } else {
                            toast(entituy.getMessage());
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
            } else {
                map.put("Mobile", StrUtil.getString(inputUser));//                手机号
                map.put("PatientPassword", StrUtil.getString(inputPassword));//                密码
                map.put("MobileCode", StrUtil.getString(inputBdCode));//                验证码
                map.put("AppId", "newdjkpatapp");//                验证码
                map.put("OpenId", openId);//                验证码
                mMyOkhttp.post().url(HttpUrl.WechatRegist).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
                    @Override
                    public void onSuccess(int statusCode, Entity entituy) {
                        LoadDialog.clear();
                        if (entituy.getCode() == 0) {
                            finish();
                        }
                        toast(entituy.getMessage());
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

    private void login() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Access-Client", BuildConfig.ACCESS_ClIENT);
        headMap.put("Access-RegistrationId", MyApplication.mRegistrationId);//极光推送的设备唯一性标识RegistrationID
        headMap.put("Access-Platform", "Android");
        headMap.put("Access-Appkey", BuildConfig.JPUSH_APPKEY);
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("Mobile", StrUtil.getString(inputUser));
        paramsMap.put("Type", "2");//登录类型:1验证码，2账号
        paramsMap.put("Code", StrUtil.getString(inputPassword));
        mMyOkhttp.post().url(HttpUrl.login).headers(headMap).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<LoginEntity>() {
            @Override
            public void onSuccess(int statusCode, LoginEntity entituy) {
                LoadDialog.clear();
                if (entituy.getCode() == 0) {
                    LoginEntity.DataEntity data = entituy.getData();
                    myShared(data);
                    toActivity(MainActivity.getAct(mContext));
                    toast(getString(R.string.registerSuccess));
                } else {
                    toast(entituy.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                LoadDialog.clear();
                if (statusCode == 1001) {
                    errorMsg = getString(R.string.accountOrPassError);
                }
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    /**
     * 本地保存
     *
     * @param entituy
     */
    private void myShared(LoginEntity.DataEntity entituy) {
        Gson mGson = new Gson();
        AppConfigurationInfo.DataBean appInfo = mGson.fromJson(SpUtils.getString(Contants.AppInfo), AppConfigurationInfo.DataBean.class);
        PrescriptionMessageEntity prescriptionMessageEntity = new PrescriptionMessageEntity();
        prescriptionMessageEntity.setPatient(entituy);
        prescriptionMessageEntity.setAppInfo(appInfo);

        float lat = (float) SpUtils.get(MainConstant.LAT, 0.0f);
        float lon = (float) SpUtils.get(MainConstant.LON, 0.0f);
        Position position = new Position(lat, lon);
        prescriptionMessageEntity.setPosition(position);
        String mUserInfoGson = mGson.toJson(prescriptionMessageEntity);
        SpUtils.put(Contants.LoginJson, mUserInfoGson);
        SpUtils.put(Contants.Id, entituy.getData().getId());
        SpUtils.put(Contants.Mobile, entituy.getData().getMobile()); //登陆的手机号码
        SpUtils.put(Contants.Name, entituy.getData().getName());
        SpUtils.put(Contants.Sex, entituy.getData().getSex());
        SpUtils.put(Contants.Token, entituy.getToken());
        SpUtils.put(Contants.AccountId, entituy.getData().getAccountId());
        SpUtils.put(Contants.userName, StrUtil.getString(inputUser));
        SpUtils.put(Contants.inputPassword, StrUtil.getString(inputPassword)); //上一次登陆的密码保存在本地
        SpUtils.put(Contants.MUSERINFO, StrUtil.getString(mUserInfoGson)); //获取到的个人数据
    }

    /**
     * 快速登录校验
     *
     * @return
     */
    private boolean isNull() {
        if (!StrUtil.isMobile(StrUtil.getString(inputUser))) {
            toast("手机号格式不正确");
            return false;
        } else if (!StrUtil.isNotEmpty(inputBdCode, true)) {
            toast("验证码不能为空");
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
