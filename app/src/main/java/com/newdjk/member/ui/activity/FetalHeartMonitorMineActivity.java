package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.ui.entity.ServiceDetailEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.views.CircleImageView1;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FetalHeartMonitorMineActivity extends BasicActivity {

    @BindView(R.id.buy_service_package_btn)
    Button buyServicePackageBtn;
    @BindView(R.id.fetal_heart_monitor_record_rl)
    RelativeLayout fetalHeartMonitorRecordRl;
    @BindView(R.id.fetal_monitor_significance_rl)
    RelativeLayout fetalMonitorSignificanceRl;
    @BindView(R.id.user_head_iv)
    CircleImageView1 userHeadIv;
    @BindView(R.id.user_name_tv)
    TextView userNameTv;
    @BindView(R.id.gestation_weeks_tv)
    TextView gestationWeeksTv;
    @BindView(R.id.start_date_tv)
    TextView startDateTv;
    @BindView(R.id.service_cycle_tv)
    TextView serviceCycleTv;
    @BindView(R.id.service_deadline_date_tv)
    TextView serviceDeadlineDateTv;
    @BindView(R.id.monitor_statue_tv)
    TextView monitorStatueTv;
    @BindView(R.id.using_proportion_tv)
    TextView usingProportionTv;

    private HaveBindEntity.DataBean.PatientBean patientBean;
    private String ServicePhone;
    private int code;
    @Override
    protected int initViewResId() {
        return R.layout.activity_fetal_heart_monitor_mine;
    }

    @Override
    protected void initView() {
        setStatusBar(getResources().getColor(R.color.fetalheart_monitor_mine_data_bg_color));
        initBackTitleBgRes(R.color.fetalheart_monitor_mine_data_bg_color, getResources().getString(R.string.fetal_heart_monitor_mine_title_string));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        patientBean = (HaveBindEntity.DataBean.PatientBean) intent.getSerializableExtra("Patient");
        ServicePhone = intent.getStringExtra("ServicePhone");
        code = intent.getIntExtra("Code", 5);

        int accountId = ((MyApplication) FetalHeartMonitorMineActivity.this.getApplication()).getAccountId();
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("accountId", accountId+"");
        paramsMap.put("patientId", patientBean.getPatientId() + "");
        mMyOkhttp.get().url(HttpUrl.ServiceDetail).headers(HeadUitl.instance.getHeads()).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<ServiceDetailEntity>() {
            @Override
            public void onSuccess(int statusCode, ServiceDetailEntity response) {
                if (response.getCode() == 0) {
                    Glide.with(FetalHeartMonitorMineActivity.this).load(response.getData().getPicturePath()).into(userHeadIv);
                    userNameTv.setText(response.getData().getPatientName());
                    gestationWeeksTv.setText(response.getData().getGestationWeeks());
                    startDateTv.setText(response.getData().getBeginTime());
                    serviceCycleTv.setText(response.getData().getServiceWeeks());
                    serviceDeadlineDateTv.setText(response.getData().getEndTime());
                    if (response.getData().getStatus() == 0) {//(0监测中,1已结束)
                        monitorStatueTv.setText(getResources().getString(R.string.fetal_heart_monitor_mine_ing_string));
                    } else if (response.getData().getStatus() == 1) {
                        monitorStatueTv.setText(getResources().getString(R.string.fetal_heart_monitor_mine_end_string));
                    }
                    usingProportionTv.setText(response.getData().getUsedNum() + "/" + response.getData().getTotalNum());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick({R.id.buy_service_package_btn, R.id.fetal_heart_monitor_record_rl, R.id.fetal_monitor_significance_rl})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.buy_service_package_btn:
                if (code != 1) {
                    intent = new Intent(FetalHeartMonitorMineActivity.this, ServiceListActivity.class);
                    intent.putExtra("go_path", "fetalHeartMonitorMine");
                    intent.putExtra("Patient", patientBean);
                    toActivity(intent);
                } else {
                    Toast.makeText(FetalHeartMonitorMineActivity.this,getResources().getString(R.string.fetal_heart_monitor_unregister_tip_string),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fetal_heart_monitor_record_rl:
                if (code != 1) {
                    intent = new Intent(FetalHeartMonitorMineActivity.this, FetalHeartRecordActivity.class);
                    intent.putExtra("Patient", patientBean);
                    intent.putExtra("ServicePhone", ServicePhone);
                    intent.putExtra("go_path", "fetalHeartMonitorMine");
                    toActivity(intent);
                } else {
                    Toast.makeText(FetalHeartMonitorMineActivity.this,getResources().getString(R.string.fetal_heart_monitor_unregister_tip_string),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.fetal_monitor_significance_rl:
                intent = new Intent(FetalHeartMonitorMineActivity.this, FetalMonitorMeaningActivity.class);
                toActivity(intent);
                break;
        }
    }

}
