package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestResultsActivity extends BasicActivity {

    @BindView(R.id.complete_btn)
    Button completeBtn;
    @BindView(R.id.time_tv)
    TextView timeTv;
    @BindView(R.id.tip_tv)
    TextView tipTv;
    @BindView(R.id.service_phone_tv)
    TextView servicePhoneTv;
    @BindView(R.id.time_end_ll)
    LinearLayout timeEndLl;

    private int mins = 29;
    private int seconds = 60;

    private String mins_text;
    private String seconds_text;

    private String ServicePhone;
    private String go_path;

    private Timer timer;
    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            Message msg = mHandler.obtainMessage();
            msg.arg1 = 1024;
            mHandler.sendMessage(msg);
        }
    };

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 1024:
                    if (seconds > 0) {
                        seconds = seconds - 1;
                    } else {
                        mins = mins - 1;
                        if (mins == -1) {
                            tipTv.setVisibility(View.GONE);
                            timeEndLl.setVisibility(View.VISIBLE);
                            servicePhoneTv.setText(ServicePhone);
                            if (timer != null) {
                                timer.cancel();
                                timer = null;
                            }
                            if (timerTask != null) {
                                timerTask.cancel();
                                timerTask = null;
                            }
                            return;
                        }
                        seconds = 59;
                    }
                    mins_text = mins >= 10 ? mins + "" : "0" + mins;
                    seconds_text = seconds >= 10 ? seconds + "" : "0" + seconds;
                    timeTv.setText(mins_text + "：" + seconds_text);
                    break;
            }
        }
    };

    @Override
    protected int initViewResId() {
        return R.layout.activity_request_results;
    }

    @Override
    protected void initView() {
        initBackTitleLeftImg(R.color.white, getResources().getString(R.string.request_result_title_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPath();
            }
        });

        timeTv.setText("30:00");
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        tipTv.setVisibility(View.VISIBLE);
        timeEndLl.setVisibility(View.GONE);

        timer = new Timer();
        timer.schedule(timerTask, 0, 1000);

        Intent intent = getIntent();
        ServicePhone = intent.getStringExtra("ServicePhone");
        go_path = intent.getStringExtra("go_path");
    }

    @Override
    protected void otherViewClick(View view) {

    }

    private void goToPath() {
        if (go_path.equals("device_connect")) {
            //加入界面管理
            MonitorDetailActivity.heart_monitor_lists.add(RequestResultsActivity.this);
            //清掉管理的activity
            for (int i = 0; i < MonitorDetailActivity.heart_monitor_lists.size(); i ++) {
                MonitorDetailActivity.heart_monitor_lists.get(i).finish();
            }
        } else if (go_path.equals("fetalHeartMonitorMine")){
            RequestResultsActivity.this.finish();
        }
    }

    @OnClick(R.id.complete_btn)
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.complete_btn:
                goToPath();
                break;
        }
    }

}
