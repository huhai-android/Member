package com.newdjk.member.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.entity.DefaultPatientEntity;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.ui.entity.PatientRegisterEntity;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.CheckPatientStatusUtil;
import com.newdjk.member.utils.DateUtil;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.LogUtils;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.CommomDialog;
import com.newdjk.member.views.CustomDatePicker;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class UserNoteActivity extends BasicActivity {

    @BindView(R.id.save_btn)
    Button saveBtn;
    @BindView(R.id.patient_tv)
    TextView patientTv;
    @BindView(R.id.patient_rl)
    RelativeLayout patientRl;
    @BindView(R.id.default_patient_tv)
    TextView defaultPatientTv;
    @BindView(R.id.pregnancy_cycle_rl)
    RelativeLayout pregnancyCycleRl;
    @BindView(R.id.pregnancy_cycle_tv)
    TextView pregnancyCycleTv;
    @BindView(R.id.emergency_contact_person_edt)
    EditText emergencyContactPersonEdt;
    @BindView(R.id.emergency_contact_tel_edt)
    EditText emergencyContactTelEdt;

    private int PatientId;
    private String PatientName;
    private String Age;
    private String Mobile;
    private int id;

    private Gson mGson;

    private int OrgId;
    private int ServicePackId;
    private List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean> detailsBean;
    private int ServiceLong;
    private String EndTime;
    private double payPrice;
    private String go_path;
    private double mOriPrice;

    private int patientSex;
    private int newborn;
    private String birthday;
    private DefaultPatientEntity.DataBean mSelectPatient;
    private String mCurrentDate;
    //孕产期时间选择器
    private CustomDatePicker pregnancyCycle_date_picker;

    private HaveBindEntity.DataBean.PatientBean patientBean;
    private String type;

    @Override
    protected int initViewResId() {
        return R.layout.activity_user_note;
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.white,getResources().getString(R.string.user_note_title_string));
        EventBus.getDefault().register(this);
        type = getIntent().getStringExtra("type");
        id = getIntent().getIntExtra("id", -1);
        mGson = new Gson();
        OrgId = getIntent().getIntExtra("OrgId", 0);
        ServicePackId = getIntent().getIntExtra("ServicePackId", 0);
        detailsBean = (List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean>) getIntent().getSerializableExtra("detailsBean");
        ServiceLong = getIntent().getIntExtra("ServiceLong", 0);
        EndTime = getIntent().getStringExtra("EndTime");
        payPrice = getIntent().getDoubleExtra("payPrice", 0);
        go_path = getIntent().getStringExtra("go_path");
        mOriPrice = getIntent().getDoubleExtra("originPrice", 0);
        initDefaultPatient();
        initUrgent();
    }

    private void initUrgent() {
        String name = SpUtils.getString(Contants.urgentName);
        String mobile = SpUtils.getString(Contants.urgentMobile);
        String date = SpUtils.getString(Contants.GestationalPeriod);
        emergencyContactPersonEdt.setText(name == null ? "" : name);
        emergencyContactTelEdt.setText(mobile == null ? "" : mobile);
        pregnancyCycleTv.setText(date == null ? "" : date);
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String now = sdf.format(new Date());
        //pregnancyCycleTv.setText(now.split(" ")[0]);
        mCurrentDate = now.split(" ")[0];
        pregnancyCycle_date_picker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                LogUtils.e(time);
                if (DateUtil.instance.dfDate(System.currentTimeMillis(), time)) {
                    pregnancyCycleTv.setText(time.split(" ")[0]);
                } else {
                    toast(getString(R.string.dateError));
                }

            }
        }, now, "2100-01-01 00:00"); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        pregnancyCycle_date_picker.showSpecificTime(false); // 不显示时和分
        pregnancyCycle_date_picker.setIsLoop(false); // 不允许循环滚动


    }

    @Override
    protected void otherViewClick(View view) {

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void receviePaintSelectInfo(DefaultPatientEntity.DataBean info) {

        if (info != null) {
            mSelectPatient = info;

            setPatientName();
        } else {
            toast(getString(R.string.pleaseFillPatientInfo));

            //用于判断 （姓名，性别，出生日期）不能为空   （性别女，新生儿）不能被选为就诊人
            birthday = info.getBirthday();
            patientSex = info.getPatientSex();
            newborn = info.getNewborn();

        }
    }
    private void initDefaultPatient() {
        String defaultPatientStr = SpUtils.getString(Contants.DefaultPatientAccountJson);
        if (TextUtils.isEmpty(defaultPatientStr)) {
            return;
        }

        mSelectPatient = mGson.fromJson(defaultPatientStr, DefaultPatientEntity.DataBean.class);
        setPatientName();
    }

    private void setPatientName() {
        if (TextUtils.isEmpty(mSelectPatient.getPatientName())) {
            patientTv.setText(getResources().getString(R.string.selectPaint));
        } else {
            patientTv.setText(mSelectPatient.getPatientName());
        }
//        patientTv.setText(mSelectPatient.getPatientName() == null ? getResources().getString(R.string.selectPaint) : mSelectPatient.getPatientName());
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @OnClick({R.id.save_btn, R.id.patient_rl, R.id.pregnancy_cycle_rl})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.save_btn://确定、取消

                if (mSelectPatient == null|| TextUtils.isEmpty(mSelectPatient.getPatientName())) {
                    toast(getString(R.string.pleaseSelectPatient));
                    return;
                }
                if (mSelectPatient.getCredNo() == null) {
                toast("当前选择就诊人身份证号码不能为空");
                return;
            }
                if (mSelectPatient.getNewborn() == 1) {
                    toast(getString(R.string.patientNotNewBorn));
                    return;
                }
                int sex = mSelectPatient.getPatientSex();
                if (sex == 1) {
                    toast(getString(R.string.PatientNotMan));
                    return;
                } else if (sex == 3) {
                    toast(getString(R.string.pleaseFillPatientInfo));
                    return;
                }
                if (TextUtils.isEmpty(pregnancyCycleTv.getText().toString())){
                    toast(getString(R.string.enterDate));
                    return;
                }

                if (emergencyContactPersonEdt.getText().toString().equals("")) {
                    Toast.makeText(UserNoteActivity.this,getResources().getString(R.string.user_note_emergency_contact_person_null_string),Toast.LENGTH_SHORT).show();
                    return;
                }
                if (emergencyContactTelEdt.getText().toString().equals("")) {
                    Toast.makeText(UserNoteActivity.this,getResources().getString(R.string.user_note_emergency_contact_tel_null_string),Toast.LENGTH_SHORT).show();
                    return;
                }


                SpUtils.put(Contants.urgentMobile, emergencyContactTelEdt.getText().toString().trim());
                SpUtils.put(Contants.urgentName, emergencyContactPersonEdt.getText().toString().trim());
                SpUtils.put(Contants.GestationalPeriod, pregnancyCycleTv.getText().toString().trim());
                new CommomDialog(UserNoteActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.user_note_dialog_tip_string), new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(final Dialog dialog, boolean confirm) {
                        dialog.cancel();

                        if (confirm) {
                            Map<String, String> paramsMap = new HashMap<>();
                            paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
                            paramsMap.put("PatientId", mSelectPatient.getPatientId() + "");
                            paramsMap.put("PatientName", mSelectPatient.getPatientName());
                            //paramsMap.put("Age", Age.replace(getResources().getString(R.string.user_note_age_remove_string), ""));
                            paramsMap.put("DueDate", pregnancyCycleTv.getText().toString().trim());
                            paramsMap.put("ContactPhone", mSelectPatient.getMobile());
                            paramsMap.put("EmergencyName", emergencyContactPersonEdt.getText().toString().trim());
                            paramsMap.put("EmergencyPhone", emergencyContactTelEdt.getText().toString().trim());

                            mMyOkhttp.post().url(HttpUrl.PatientRegister).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<PatientRegisterEntity>() {
                                @Override
                                public void onSuccess(int statusCode, PatientRegisterEntity response) {

                                    if (response.getCode() == 0) {
                                        CheckPatientStatusUtil.getDefaultPatientStatus(UserNoteActivity.this);
                                        if (type == null) {
                                            Intent intent = new Intent(UserNoteActivity.this, OrderNoteActivity.class);
                                            intent.putExtra("PatientName", mSelectPatient.getPatientName());
                                            intent.putExtra("DueDate", pregnancyCycleTv.getText().toString().trim());
                                            intent.putExtra("EmergencyName", emergencyContactPersonEdt.getText().toString().trim());
                                            intent.putExtra("EmergencyPhone", emergencyContactTelEdt.getText().toString().trim());

                                            intent.putExtra("Patient", mSelectPatient);
                                            intent.putExtra("OrgId", OrgId);
                                            intent.putExtra("ServicePackId", ServicePackId);
                                            intent.putExtra("detailsBean", (Serializable) detailsBean);
                                            intent.putExtra("ServiceLong", ServiceLong);
                                            intent.putExtra("EndTime", EndTime);
                                            intent.putExtra("payPrice", payPrice);
                                            intent.putExtra("originPrice", mOriPrice);
                                            intent.putExtra("go_path", go_path);
                                            toActivity(intent);
                                            //UserNoteActivity.this.noAnimFinish();
                                        } else {
                                            Intent intent = new Intent(UserNoteActivity.this, WebViewActivity.class);
                                            intent.putExtra("type", WebUtil.fetalHeart);
                                            intent.putExtra("id", id);
                                            intent.putExtra("Patient", mSelectPatient);
                                            toActivity(intent);
                                            //UserNoteActivity.this.finish();
                                        }

                                    } else {
                                        Toast.makeText(UserNoteActivity.this, getResources().getString(R.string.user_note_register_fail_string), Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailures(int statusCode, String errorMsg) {
                                    CommonMethod.requestError(statusCode, errorMsg);
                                }
                            });
                        }

                    }
                })
                        .show();
                break;
            case R.id.patient_rl:
//                Intent mVisitPersonIntent = new Intent(UserNoteActivity.this, WebViewActivity.class);
//                mVisitPersonIntent.putExtra("type", 7);
//                toActivity(mVisitPersonIntent);

                Intent mVisitPersonIntent = new Intent(UserNoteActivity.this, WebViewActivity.class);
                mVisitPersonIntent.putExtra("type", WebUtil.choosePatient);
                toActivity(mVisitPersonIntent);
                break;
            case R.id.pregnancy_cycle_rl:
                pregnancyCycle_date_picker.show(mCurrentDate);
                break;
        }
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.FINISH:
                finish();
                break;
        }
    }



}
