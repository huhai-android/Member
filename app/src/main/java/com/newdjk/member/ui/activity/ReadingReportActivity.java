package com.newdjk.member.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.entity.AskCheckEntity;
import com.newdjk.member.ui.entity.GetMonitorDataEntity;
import com.newdjk.member.ui.entity.GetReportDetailEntity;
import com.newdjk.member.ui.entity.SubmitAskEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.Listener;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.CommomDialog;
import com.newdjk.member.views.FhrPlayView;
import com.newdjk.member.views.FhrView;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReadingReportActivity extends BasicActivity {

    @BindView(R.id.monitor_date_tv)
    TextView monitorDateTv;
    @BindView(R.id.consulting_number_tv)
    TextView consultingNumberTv;
    @BindView(R.id.username_tv)
    TextView usernameTv;
    @BindView(R.id.gestational_weeks_tv)
    TextView gestationalWeeksTv;
    @BindView(R.id.age_tv)
    TextView ageTv;
    @BindView(R.id.tel_tv)
    TextView telTv;
    @BindView(R.id.monitor_time_tv)
    TextView monitorTimeTv;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.end_time_tv)
    TextView endTimeTv;

    @BindView(R.id.nst_rating_tv)
    TextView nstRatingTv;
    @BindView(R.id.nst_score_tv)
    TextView nstScoreTv;
    @BindView(R.id.nst_result_tv)
    TextView nstResultTv;
    @BindView(R.id.monitor_detail_tv)
    TextView monitorDetailTv;
    @BindView(R.id.doctor_advice_tv)
    TextView doctorAdviceTv;
    @BindView(R.id.doctor_name_tv)
    TextView doctorNameTv;
    @BindView(R.id.read_time_tv)
    TextView readTimeTv;
    @BindView(R.id.ask_doctor_btn)
    Button askDoctorBtn;
    @BindView(R.id.ask_doctor_rl)
    RelativeLayout askDoctorRl;
    @BindView(R.id.playdemoView)
    FhrPlayView playdemoView;
    @BindView(R.id.tv_heart_number)
    TextView rateTv;
    @BindView(R.id.tv_gongsuo_number)
    TextView tocoTv;

    private int id;
    private int status;
    //status== 2 的时候进行判读的情况
    private String ServicePhone;
    private String go_path;

    private int duration = 0;
    private Listener.TimeData[] datas;
    //    private Listener.TimeData timeData;
//    private LinkedList<Listener.TimeData> dataList = new LinkedList<Listener.TimeData>();
//    private FhrView mFhrView;
    private byte status1 = 0;
    private String beginDateTime;
    private String monitorTime;
    private String mRecordTimeDuration;

    @Override
    protected int initViewResId() {
        return R.layout.activity_reading_report;
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.read_report_title_string));
//        mFhrView = (FhrView) findViewById(R.id.fhrview);
//        mFhrView.setDataList(dataList);
    }

    @Override
    protected void initListener() {
        playdemoView.setNotifycrolledListener(new FhrPlayView.notifyScrolledListener() {

            @Override
            public void notifyScrolled(int time) {

                setRate(time);
            }
        });
    }


    private void setRate(int milliseconds) {
        playdemoView.setTime(milliseconds);
        String currStr = Listener.formatTime(milliseconds / 1000);
        int index = milliseconds / 500;
        setRate(index, currStr);
    }



    /**
     * 显示胎心率数据
     */
    private void setRate(int index, String currStr) {
        // timingTv.setText(currStr + "/" + toatTimeStr);
        if (index >= datas.length || index < 0)
            return;
        Listener.TimeData data = datas[index];

        if (data.heartRate < 50 || data.heartRate > 210) {
            rateTv.setText(getString(R.string.data_none));
        } else {
            rateTv.setText(String.valueOf(data.heartRate));
        }
        // currRate = data.heartRate;
        tocoTv.setText(String.valueOf(data.tocoWave));
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        id = intent.getIntExtra("id", -1);
        status = intent.getIntExtra("status", -1);

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("id", id + "");
        mMyOkhttp.get().url(HttpUrl.GetReportDetail).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<GetReportDetailEntity>() {
            private String recordTimeDuration;

            @Override
            public void onSuccess(int statusCode, GetReportDetailEntity response) {
                if (response.getCode() == 0) {
                    beginDateTime = response.getData().getBeginDate();
                    monitorTime = response.getData().getMonitorTime();
                    if (!TextUtils.isEmpty(monitorTime)) monitorDateTv.setText(monitorTime.substring(0,10));
                    consultingNumberTv.setText(response.getData().getAskNo());
                    usernameTv.setText(response.getData().getPatientName());
                    gestationalWeeksTv.setText(response.getData().getWeeks());
                    ageTv.setText(response.getData().getAge() + "");
                    telTv.setText(response.getData().getMobile());
                    duration = response.getData().getDuration();
                    int mins = duration / 60;
                    int seconds = duration % 60;
                    mRecordTimeDuration = (mins >= 10 ? mins : "0" + mins) + ":" + (seconds >= 10 ? seconds : "0" + seconds);
                    monitorTimeTv.setText(mRecordTimeDuration);
                    startTimeTv.setText(response.getData().getBeginDate());
                    endTimeTv.setText(response.getData().getEndDate());

                    if (status == 0) {
                        nstRatingTv.setVisibility(View.GONE);
                        nstResultTv.setText("等待医生判读");
                        doctorAdviceTv.setText("等待医生判读");

                        askDoctorRl.setVisibility(View.GONE);
                    } else if (status == 1) {
                        nstRatingTv.setVisibility(View.VISIBLE);
                        nstScoreTv.setText("NST评分 " + response.getData().getScore() + "分");

                        nstResultTv.setText("NST:" + response.getData().getResult());
                        doctorAdviceTv.setText(response.getData().getAdvice());

                        askDoctorRl.setVisibility(View.GONE);
                    } else if (status == 2) {
                        nstRatingTv.setVisibility(View.GONE);
                        nstResultTv.setText("等待医生判读");
                        doctorAdviceTv.setText("等待医生判读");

                        ServicePhone = getIntent().getStringExtra("ServicePhone");
                        go_path = getIntent().getStringExtra("go_path");
                        askDoctorRl.setVisibility(View.VISIBLE);
                    }

                    doctorNameTv.setText(response.getData().getDoctorName());
                    readTimeTv.setText(response.getData().getReadTime());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

        Map<String, String> paramsMap_data = new HashMap<>();
        paramsMap_data.put("id", id + "");
        mMyOkhttp.get().url(HttpUrl.GetMonitorData).params(paramsMap_data).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<GetMonitorDataEntity>() {
            @Override
            public void onSuccess(int statusCode, GetMonitorDataEntity response) {

                if (response.getCode() == 0) {
//                    for (int i = 0;i < response.getData().getData().size() / 10;i++) {
//                        /*status1 = (byte) ((response.getData().getData().get(4 + 10 * i) & 0x03)
//                                + ((response.getData().getData().get(5 + 10 * i) << 2) & 0x04)
//                                + ((response.getData().getData().get(6 + 10 * i) << 3) & 0x08)
//                                + ((response.getData().getData().get(9 + 10 * 9) << 4) & 0x10));*/
//                        if (response.getData().getData().get(6 + i * 10) == 1) {
//                            mFhrView.addSelfBeat();
//                        }
//                        timeData = new Listener.TimeData(response.getData().getData().get(i + 10),response.getData().getData().get(3 + i * 10),response.getData().getData().get(2 + i * 10), 0,0,0);
//                        mFhrView.addBeat(timeData);
//                    }

                    if (response.getData().getData() != null) {
                        String[] alldata = response.getData().getData().toString().substring(1, response.getData().getData().toString().length()).split(",");
                        datas = new Listener.TimeData[alldata.length / 10];
                        for (int i = 0; i < alldata.length / 10; i++) {
                            datas[i] = new Listener.TimeData();
                            for (int j = 0; j < 10; j++) {
                                if (j == 0) {
                                    //宫缩在下面
                                    datas[i].heartRate = Integer.parseInt(alldata[i * 10 + j].trim());
                                }
                                if (j == 3) {
                                    //心率 在上面
                                    datas[i].tocoWave = Integer.parseInt(alldata[i * 10 + j].trim());
                                }
                                if (j == 6) {
                                    //红心的标志位
                                    datas[i].status1 = Integer.parseInt(alldata[i * 10 + j].trim());
                                }
                            }
                            //   Log.d(TAG, "第" + i + "条数据" + datas[i].toString());
                        }
                        // Log.d(TAG, "数据长度" + datas.length);
                        playdemoView.setDatas(datas);
                    }
                } else {
                    toast(response.getMessage());
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

//    public String getRecordTime(String beginTime,String endTime) {
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String recordTime = "";
//        try
//        {
//            Date d1 = df.parse(beginTime);
//            Date d2 = df.parse(endTime);
//            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
//            long days = diff / (1000 * 60 * 60 * 24);
//
//            long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
//            long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
//            long sec = ((diff -days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60)-minutes*(1000*60)))/1000;
//            recordTime = recordTime+(((minutes>9)?minutes:"0"+minutes)+":"+((sec>9?sec:"0"+sec)));
//        }catch (Exception e) {
//        }
//        return recordTime;
//    }

    @OnClick({R.id.nst_rating_tv, R.id.monitor_detail_tv, R.id.ask_doctor_btn})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.nst_rating_tv:
                intent =  new Intent(ReadingReportActivity.this,NSTRatingActivity.class);
                Log.e("》》》》》》", "onViewClicked: "+id );
                intent.putExtra("id",id);
                toActivity(intent);
                break;
            case R.id.monitor_detail_tv:
//                String recordTime = getRecordTime( monitorTime,beginDateTime);
                intent = new Intent(ReadingReportActivity.this,MonitorDetailActivity.class);
                intent.putExtra("flag","2");
                intent.putExtra("tlong",mRecordTimeDuration);
                intent.putExtra("id", id);
                toActivity(intent);
                break;
            case R.id.ask_doctor_btn:
                if (duration < 20 * 60) {
                    Toast.makeText(ReadingReportActivity.this, getResources().getString(R.string.monitor_detail_time_low_no_ask_doctor_string), Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
                paramsMap.put("MonitorRecordId", id + "");
                mMyOkhttp.post().url(HttpUrl.AskCheck).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<AskCheckEntity>() {
                    @Override
                    public void onSuccess(int statusCode, AskCheckEntity response) {
                        if (response.getCode() == 0) {
                            new CommomDialog(ReadingReportActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.fetal_heart_ask_doctor_tip_string), new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    dialog.cancel();
                                    if (confirm) {
                                        Map<String, String> paramsMap = new HashMap<>();
                                        paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
                                        paramsMap.put("MonitorRecordId", id + "");
                                        mMyOkhttp.post().url(HttpUrl.SubmitAsk).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<SubmitAskEntity>() {
                                            @Override
                                            public void onSuccess(int statusCode, SubmitAskEntity response) {
                                                if (response.getCode() == 0) {
                                                    Intent intent = new Intent(ReadingReportActivity.this, RequestResultsActivity.class);
                                                    intent.putExtra("ServicePhone", ServicePhone);
                                                    intent.putExtra("go_path", go_path);
                                                    toActivity(intent);
                                                    //加入activity管理
                                                    FetalHeartRecordActivity.fetalHeart_Record_lists.add(ReadingReportActivity.this);
                                                }
                                            }

                                            @Override
                                            public void onFailures(int statusCode, String errorMsg) {
                                                CommonMethod.requestError(statusCode, errorMsg);
                                            }
                                        });

                                    }
                                }
                            }).show();
                        } else if (response.getCode() == 1) {
                            Toast.makeText(ReadingReportActivity.this, getResources().getString(R.string.fetal_heart_service_out_date_string), Toast.LENGTH_SHORT).show();
                        } else if (response.getCode() == 2) {
                            Toast.makeText(ReadingReportActivity.this, getResources().getString(R.string.fetal_heart_no_service_string), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });


                break;
        }
    }
}
