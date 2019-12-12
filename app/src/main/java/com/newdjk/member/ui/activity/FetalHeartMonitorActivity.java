package com.newdjk.member.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.luckcome.lmtpdecorder.data.FhrData;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.service.BLEBluetoothService;
import com.newdjk.member.service.BluetoothBaseService;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.entity.GetMonitorSetEntity;
import com.newdjk.member.ui.entity.MonitorDataEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.Listener;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.CommomDialog;
import com.newdjk.member.views.DragFloatActionButton;
import com.newdjk.member.views.FhrView;
import com.newdjk.member.views.LoadDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FetalHeartMonitorActivity extends BasicActivity {


    @BindView(R.id.start_monitor_btn)
    Button startMonitorBtn;
    @BindView(R.id.contractions_reset_btn)
    Button contractionsResetBtn;
    @BindView(R.id.quickening_click_btn)
    Button quickeningClickBtn;
    @BindView(R.id.quickening_click_data_tv)
    TextView quickeningClickDataTv;
    @BindView(R.id.contractions_reset_data_tv)
    TextView contractionsResetDataTv;

    @BindView(R.id.current_date_tv)
    TextView currentDateTv;
    @BindView(R.id.contractions_pressure_data_tv)
    TextView contractionsPressureDataTv;
    @BindView(R.id.heart_rate_data_tv)
    TextView heartRateDataTv;
    @BindView(R.id.move_number_tv)
    TextView moveNumberTv;

    @BindView(R.id.record_time_long_tv)
    TextView recordTimeLongTv;
    @BindView(R.id.circle_button)
    DragFloatActionButton mCircleButton;

    private final int MSG_SERVICE_INFOR = 1;
    private final int MSG_SERVICE_STATUS = 2;
    private final int MSG_SERVICE_DATA = 3;
    public final int REFRESH = 4;    // 刷新整个界面
    public final int TIME_FINISH = 5;

    private BluetoothDevice mBtDevice;
    private int PatientId;
    private String go_path;
    private String DeviceNo;
    private String ServicePhone;
    // 绑定的蓝牙服务
    private BluetoothBaseService mBluetoothBaseService = null;
    private boolean isListen = false;
    private boolean isRecord = false;
    private DataThread dataThread;
    private byte status1 = 0;

    //上传数据
    private int heartRate;
    private int fhr2;
    private int tocoWave;
    private int afmWave;
    private int fhrSignal_data;
    private int devicePower_data;
    private int afmFlag_data;
    private int fmFlag_data;
    private int tocoFlag_data;

    private MyApplication myApplication;
    //上传数据列表
    private LinkedList<MonitorDataEntity> monitor_data_lists = new LinkedList<>();

    private LinkedList<Listener.TimeData> dataList = new LinkedList<Listener.TimeData>();
    private FhrView mFhrView;

    //选择状态
    private int OpenAutoStart;
    private int OpenAutoFinish;
    private int OpenAutoFh;
    //选项选中数据
    private boolean FinishOptionIsSelected_1;
    private boolean FinishOptionIsSelected_2;
    private boolean FinishOptionIsSelected_3;

    private int set_record_time;
    //头标时间
    private String start_time;
    //结束时间
    private String end_time;
    /**
     * 服务绑定连接类，在这里要设置服务的蓝牙设备，设置回调接口，
     * 并启动服务的相关线程
     */
    private ServiceConnection mSCon = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 兼容ble和spp 2种连接方式
            mBluetoothBaseService = ((BLEBluetoothService.BluetoothBinder) service).getService();
            mBluetoothBaseService.setBluetoothDevice(mBtDevice);
            mBluetoothBaseService.setCallback(mCallback);
            mBluetoothBaseService.start();
            if (OpenAutoStart == 1) {
                startListen();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBluetoothBaseService = null;
        }

    };

    @Override
    protected int initViewResId() {
        return R.layout.activity_fetal_heart_monitor;
    }

    @Override
    protected void initView() {
        initBackTitleLeftImg(R.color.fetalheart_monitor_data_bg_color, getResources().getString(R.string.fetalheart_monitor_title_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //保持屏蔽常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //提示正在连接
        LoadDialog.show(FetalHeartMonitorActivity.this, getResources().getString(R.string.fetalheart_monitor_connecting_tip_string));

        mFhrView = (FhrView) findViewById(R.id.fhrview);
        mFhrView.setDataList(dataList);

        myApplication = (MyApplication) this.getApplication();
    }

    @Override
    protected void initListener() {
        mCircleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_intent = new Intent(FetalHeartMonitorActivity.this, GuideOperateActivity.class);
                toActivity(to_intent);
            }
        });
    }

    @Override
    protected void initData() {

        mBtDevice = getIntent().getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        PatientId = getIntent().getIntExtra("PatientId", -1);
        DeviceNo = getIntent().getStringExtra("DeviceNo");
        ServicePhone = getIntent().getStringExtra("ServicePhone");
        go_path = getIntent().getStringExtra("go_path");

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("accountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
        mMyOkhttp.get().url(HttpUrl.GetMonitorSet).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<GetMonitorSetEntity>() {
            @Override
            public void onSuccess(int statusCode, GetMonitorSetEntity response) {
                if (response.getData() == null) {
                    return;
                }
                if (response.getCode() == 0) {
                    OpenAutoStart = response.getData().getOpenAutoStart();
                    OpenAutoFinish = response.getData().getOpenAutoFinish();
                    OpenAutoFh = response.getData().getOpenAutoFh();

                    if (OpenAutoStart == 0) {//关闭
                        startMonitorBtn.setText(getResources().getString(R.string.fetalheart_monitor_start_string));

//                        contractionsResetBtn.setEnabled(false);
//                        quickeningClickBtn.setEnabled(false);

                    } else if (OpenAutoStart == 1) {//开启
                        startMonitorBtn.setText(getResources().getString(R.string.fetalheart_monitor_end_string));

                        initTitleRightStrClick(getResources().getString(R.string.fetalheart_monitor_agagin_monitor_string), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                new CommomDialog(FetalHeartMonitorActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.fetalheart_monitor_agagin_monitor_tip_string), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.cancel();
                                        if (confirm) {
                                            //重新设置开始时间
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                                            start_time = sdf.format(new Date());
                                            //传递画图的数据清空
                                            monitor_data_lists.clear();
                                            //界面清空
                                            mFhrView.clear();
                                            //停止记录
                                            startEndRecord();
                                            //停止画数据
                                            if (dataThread.timer != null) {
                                                dataThread.timer.cancel();
                                                dataThread.timer = null;
                                            }
                                            if (dataThread.timerTask != null) {
                                                dataThread.timerTask.cancel();
                                                dataThread.timerTask = null;
                                            }
                                            //重新画图
                                            dataThread = new DataThread();
                                            dataThread.start();

                                            //开始记录
                                            startEndRecord();

                                            //这里startListen(); 在ServiceConnection mSCon里调用
                                        }
                                    }
                                }).show();

                            }
                        });
                    }
                    //绑定服务
                    bindBlueService();

                    //显示时间
                    showStartTime();

                    if (OpenAutoFinish == 0) { // 监测自动完成（0否，1是）
                        set_record_time = 20;
                    } else if (OpenAutoFinish == 1) {//
                        FinishOptionIsSelected_1 = response.getData().getFinishOptions().get(0).isIsSelected();
                        if (FinishOptionIsSelected_1) {
                            set_record_time = 20;
                        }
                        FinishOptionIsSelected_2 = response.getData().getFinishOptions().get(1).isIsSelected();
                        if (FinishOptionIsSelected_2) {
                            set_record_time = 30;
                        }
                        FinishOptionIsSelected_3 = response.getData().getFinishOptions().get(2).isIsSelected();
                        if (FinishOptionIsSelected_3) {
                            set_record_time = 40;
                        }
                    }
//                    if (OpenAutoFh == 0) {  //开启自动胎心（0否，1是）
//                        quickeningClickBtn.setVisibility(View.VISIBLE);
//                    } else if (OpenAutoFh == 1) {
//                        quickeningClickBtn.setVisibility(View.GONE);
//                    }
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

    private boolean onoffBoolean = true;

    @OnClick({R.id.start_monitor_btn, R.id.contractions_reset_btn, R.id.quickening_click_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_monitor_btn:

                if (OpenAutoStart == 0) { //关闭
                    if (onoffBoolean) {
                        contractionsResetBtn.setEnabled(true);
                        quickeningClickBtn.setEnabled(true);
                        startMonitorBtn.setText(getResources().getString(R.string.fetalheart_monitor_end_string));
                        showStartTime();
                        initTitleRightStrClick(getResources().getString(R.string.fetalheart_monitor_agagin_monitor_string), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                new CommomDialog(FetalHeartMonitorActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.fetalheart_monitor_agagin_monitor_tip_string), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.cancel();
                                        if (confirm) {
                                            //重新设置开始时间
                                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                                            start_time = sdf.format(new Date());
                                            //传递画图的数据清空
                                            monitor_data_lists.clear();
                                            //界面清空
                                            mFhrView.clear();
                                            //停止记录
                                            startEndRecord();
                                            //停止画数据
                                            if (dataThread.timer != null) {
                                                dataThread.timer.cancel();
                                                dataThread.timer = null;
                                            }
                                            if (dataThread.timerTask != null) {
                                                dataThread.timerTask.cancel();
                                                dataThread.timerTask = null;
                                            }
                                            //重新画图
                                            dataThread = new DataThread();
                                            dataThread.start();

                                            //开始记录
                                            startEndRecord();
                                        }
                                    }
                                }).show();

                            }
                        });

                        startListen();

                        dataThread = new DataThread();
                        dataThread.start();

                        startEndRecord();

                        onoffBoolean = false;
                    } else { //关闭代码
                        String timing = dataThread.listener.timing;
                        int mins = Integer.parseInt(timing.substring(0, 2)) * 60;
                        int sec = Integer.parseInt(timing.substring(3));
                        int record_time_int = mins + sec;
                        if (record_time_int < 20 * 60) {
                            finishDialog( getResources().getString(R.string.monitor_detail_time_low_no_ask_doctor_string), 1);
                            return;
                        } else {
                            finishDialog( getResources().getString(R.string.fetalheart_monitor_complete_string), 1);
                        }

                    }

                } else if (OpenAutoStart == 1) {//开启
                    String timing = dataThread.listener.timing;
                    int mins = Integer.parseInt(timing.substring(0, 2)) * 60;
                    int sec = Integer.parseInt(timing.substring(3));
                    int record_time_int = mins + sec;
                    if (record_time_int < 20 * 60) {
                        finishDialog( getResources().getString(R.string.monitor_detail_time_low_no_ask_doctor_string), 1);
                        return;
                    } else {
                        finishDialog( getResources().getString(R.string.fetalheart_monitor_complete_string), 1);
                    }
                }
                break;
            case R.id.contractions_reset_btn:
                if (mBluetoothBaseService == null) {
                    Toast.makeText(this, getResources().getString(R.string.fetalheart_monitor_start_bluetooth_connect_string), Toast.LENGTH_SHORT).show();
                    return;
                }
                mFhrView.addTocoReset();
                // 这里是固定宫缩复位到10，详见蓝牙移动终端协议解析
                byte[] byteToco = tocoReset(1);
                mBluetoothBaseService.setTocoResetBLE(byteToco);

                break;
            case R.id.quickening_click_btn:
                if (mBluetoothBaseService == null) {
                    Toast.makeText(this, getResources().getString(R.string.fetalheart_monitor_start_bluetooth_connect_string), Toast.LENGTH_SHORT).show();
                    return;
                }
                dataThread.listener.beatTimes++;
                mFhrView.addSelfBeat();
                mBluetoothBaseService.setFM();
                moveNumberTv.setText(Integer.toString(dataThread.listener.beatTimes));
                break;
        }
    }

    private void startEndRecord() {
        if (mBluetoothBaseService == null) {
            Toast.makeText(this, getResources().getString(R.string.fetalheart_monitor_start_bluetooth_connect_string), Toast.LENGTH_SHORT).show();
            return;
        }
        if (mBluetoothBaseService.getReadingStatus()) {
            if (mBluetoothBaseService.getRecordStatus()) {//关闭
                mBluetoothBaseService.recordFinished();
                Toast.makeText(this, getResources().getString(R.string.fetalheart_monitor_record_finished_string), Toast.LENGTH_SHORT).show();
            } else {//开始
                mBluetoothBaseService.recordStart();
                Toast.makeText(this, getResources().getString(R.string.fetalheart_monitor_record_start_string), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.fetalheart_monitor_start_bluetooth_connect_string), Toast.LENGTH_SHORT).show();
        }
    }

    private void finishDialog(String tip,int type) {//1是手动点击 2是设置时间到了
        new CommomDialog(FetalHeartMonitorActivity.this, R.style.MyDialogStyle, tip, new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.cancel();
                if (confirm) {
                    endCustody(type);
                }
            }
        }).show();
    }

    //完成监护
    private void endCustody(int type) {
//        if (type == 1) {
            //停止记录
            startEndRecord();
            //停止服务
            unbindBluerService();
            //停止画数据
            if (dataThread!=null){
                if (dataThread.timer != null) {
                    dataThread.timer.cancel();
                    dataThread.timer = null;
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
            end_time = sdf.format(new Date());
//        }

        myApplication.setMonitorDataEntityList(monitor_data_lists);
        Intent intent = new Intent(FetalHeartMonitorActivity.this, MonitorDetailActivity.class);
        intent.putExtra("flag", "1");
        intent.putExtra("go_path", go_path);
        intent.putExtra("PatientId", PatientId);
        //intent.putExtra("monitor_data", (Serializable) monitor_data_lists);
        intent.putExtra("tlong", dataThread.listener.timing);
        intent.putExtra("begindate", dataThread.listener.startT);
        intent.putExtra("DeviceNo", DeviceNo);
        intent.putExtra("ServicePhone", ServicePhone);
        intent.putExtra("start_time", start_time);
        intent.putExtra("end_time",end_time);
        intent.putExtra("OpenAutoFh",OpenAutoFh); //开启自动胎心（0否，1是）
        toActivity(intent);
        finish();
    }

    private void showStartTime() {
        //显示头部当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        start_time = sdf.format(new Date());
        currentDateTv.setText(start_time);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBluetoothBaseService != null) {
            mBluetoothBaseService.cancel();
        }
        unbindBluerService();
//        startEndRecord();
        //停止画数据
        if (dataThread != null) {
            if (dataThread.timer != null) {
                dataThread.timer.cancel();
                dataThread.timer = null;
            }
            if (dataThread.timerTask != null) {
                dataThread.timerTask.cancel();
                dataThread.timerTask = null;
            }
        }
    }

    /**
     * 绑定服务
     */
    private void bindBlueService() {
        Class<?> seviceClass = BLEBluetoothService.class;
        this.bindService(new Intent(this, seviceClass), mSCon, Context.BIND_AUTO_CREATE);
    }

    /**
     * 解除服务绑定
     */
    private void unbindBluerService() {
        if (mBluetoothBaseService != null) {
            this.unbindService(mSCon);
            mBluetoothBaseService = null;
        }
    }

    /**
     * 开始监测
     */
    private void startListen() {
        if (!isListen) {
            isListen = true;
            isRecord = true;
        }
    }

    private int heartRate_avg = 0;
    private int tocoWave_avg = 0;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_SERVICE_INFOR: // 信息显示
                    String infor = msg.getData().getString("infor");
                    //showServiceInfor(infor);
                    break;
                case MSG_SERVICE_STATUS: // 异常提示
                    String status = msg.getData().getString("status");
                    if (status != null) {
                        String txt = getResources().getString(R.string.fetalheart_monitor_service_read_data_fail_string);
                        if (status.equals(txt)) {
                            //noteDialog(txt);
                            CommomDialog commomDialog = new CommomDialog(FetalHeartMonitorActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.fetalheart_monitor_service_read_data_fail_string), new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    dialog.cancel();
                                    unbindBluerService();
                                    if (dataThread == null || dataThread.listener == null) {
                                        finish();
                                    } else {
                                        endCustody(1);
                                    }
//                                    finish();
                                }
                            });
                            commomDialog.setTitle("监测结束");
                            commomDialog.show();
                            if (dataThread != null) {
                                dataThread.stopSelf();
                            }
                            LoadDialog.clear();
                        }
                    }
                    break;
                case MSG_SERVICE_DATA: // 数据显示
                    heartRate = msg.getData().getInt("fhr1");
                    fhr2 = msg.getData().getInt("fhr2");
                    tocoWave = msg.getData().getInt("toco");
                    afmWave = msg.getData().getInt("afm");
                    fhrSignal_data = msg.getData().getInt("fhrSignal");
                    devicePower_data = msg.getData().getInt("devicePower");
                    afmFlag_data = msg.getData().getInt("afmFlag");
                    fmFlag_data = msg.getData().getInt("fmFlag");
                    tocoFlag_data = msg.getData().getInt("tocoreset");
                    if (!onoffBoolean && OpenAutoFh == 1) {
                        if (afmFlag_data == 1) {
                            dataThread.listener.beatTimes++;
                            mFhrView.addSelfBeat();
                            moveNumberTv.setText(Integer.toString(dataThread.listener.beatTimes));
                        }
                    }
                    break;
                case REFRESH: // 刷新界面
                    //平均数据
                    heartRate_avg = 0;
                    tocoWave_avg = 0;
                    for (int i = 0;i < monitor_data_lists.size(); i++) {
                        heartRate_avg = heartRate_avg + monitor_data_lists.get(i).getFhr1();
                        tocoWave_avg = tocoWave_avg + monitor_data_lists.get(i).getToco();
                    }
                    heartRateDataTv.setText(heartRate_avg / monitor_data_lists.size()+ "");
                    contractionsPressureDataTv.setText(tocoWave_avg / monitor_data_lists.size() + "");
                    //记录中计时
                    if (isRecord) {
                        if (dataThread.listener.timing != null) {
                            recordTimeLongTv.setText(dataThread.listener.timing);
                        }
                    }
                    //实时的胎心
                    if (heartRate < 50 || heartRate > 240) {
                        quickeningClickDataTv.setText("---");
                    } else {
                        quickeningClickDataTv.setText(heartRate + "");
                    }
                    //实时的宫缩
                    if ((tocoWave > 100) || (tocoWave < 0)) {
                        contractionsResetDataTv.setText("---");
                    } else {
                        contractionsResetDataTv.setText(tocoWave + "");
                    }
                    break;
                case TIME_FINISH:
                    //停止记录
                    startEndRecord();

                    //停止服务
                    unbindBluerService();

                    //停止画数据
                    if (dataThread.timer != null) {
                        dataThread.timer.cancel();
                        dataThread.timer = null;
                    }
                    if (dataThread.timerTask != null) {
                        dataThread.timerTask.cancel();
                        dataThread.timerTask = null;
                    }

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
                    end_time = sdf.format(new Date());
                    toast(getResources().getString(R.string.fetalheart_monitor_setting_complete_string));
                    endCustody(2);
//                    finishDialog(getResources().getString(R.string.fetalheart_monitor_setting_complete_string), 2);
                    break;
            }
        }
    };

    /**
     * 服务的回到接口的实现
     */
    BluetoothBaseService.Callback mCallback = new BluetoothBaseService.Callback() {

        @Override
        public void dispInfor(String infor) {
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putString("infor", infor);
            msg.setData(bundle);
            msg.what = MSG_SERVICE_INFOR;
            handler.sendMessage(msg);
        }

        @Override
        public void dispServiceStatus(String sta) {
            String status = sta;
            Log.e("TAG", "status: " + status);
            if (status != null) {
                if (status.equals("0")) { // 连接成功
                    LoadDialog.clear();
                    if (OpenAutoStart == 1) {
                        dataThread = new DataThread();
                        dataThread.start();
                    }
                }
            }
            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putString("status", sta);
            msg.setData(bundle);
            msg.what = MSG_SERVICE_STATUS;
            handler.sendMessage(msg);
        }

        @Override
        public void dispData(FhrData fhrData) {
            // TODO Auto-generated method stub
            status1 = (byte) ((fhrData.fhrSignal & 0x03)
                    + ((fhrData.afmFlag << 2) & 0x04)
                    + ((fhrData.fmFlag << 3) & 0x08)
                    + ((fhrData.tocoFlag << 4) & 0x10));

            Message msg = Message.obtain();
            Bundle bundle = new Bundle();
            bundle.clear();
            bundle.putInt("fhr1", fhrData.fhr1);//“fhr1”:胎心 1,
            bundle.putInt("fhr2", fhrData.fhr2);// ”fhr2”: 胎心 2,
            bundle.putInt("toco", fhrData.toco);// ”toco”:宫缩,
            bundle.putInt("afm", fhrData.afm);// ”afm”:胎动曲线,
            bundle.putInt("fhrSignal", fhrData.fhrSignal);// ”fhrsign”: 信号质量（冗余参数）,
            bundle.putInt("devicePower", fhrData.devicePower);// ”battery”:电池电量（冗余参数）,
            bundle.putInt("afmFlag", fhrData.afmFlag);// ”afmcount”:自动胎动标记,
            bundle.putInt("fmFlag", fhrData.fmFlag);// ”fmcount”:手动胎动标记,
            bundle.putInt("charge", 0);// ”charge”:充电状态（冗余参数）
            bundle.putInt("tocoreset", fhrData.tocoFlag);//tocoreset

            bundle.putInt("isHaveFhr1", fhrData.isHaveFhr1);
            bundle.putInt("isHaveToco", fhrData.isHaveToco);
            bundle.putInt("isHaveAfm", fhrData.isHaveAfm);
            msg.setData(bundle);//宫缩复位标记
            msg.what = MSG_SERVICE_DATA;
            handler.sendMessage(msg);

        }
    };

    /**
     * 绘制曲线的线程
     */
    private class DataThread extends Thread {

        // 监听器
        Listener listener;
        // 定时刷新数据
        Timer timer;
        TimerTask timerTask;

        public DataThread() {
            listener = new Listener();
            timer = new Timer();
            timerTask = new TimerTask() {

                @Override
                public void run() {

                    MonitorDataEntity monitorDataEntity = new MonitorDataEntity();
                    //用于上传记录数据
                    monitorDataEntity.setFhr1(heartRate);
                    monitorDataEntity.setFhr2(fhr2);
                    monitorDataEntity.setToco(tocoWave);
                    monitorDataEntity.setAfm(afmWave);
                    monitorDataEntity.setFhrsign(fhrSignal_data);
                    monitorDataEntity.setDevicePower(devicePower_data);
                    monitorDataEntity.setAfmcount(afmFlag_data);
                    monitorDataEntity.setFmcount(fmFlag_data);
                    monitorDataEntity.setCharge(0);
                    monitorDataEntity.setTocoreset(tocoFlag_data);

                    monitor_data_lists.add(monitorDataEntity);

                    Listener.TimeData timeData = new Listener.TimeData(heartRate, tocoWave, afmWave, status1, 0, 0);
                    mFhrView.addBeat(timeData);
                    listener.addBeat(timeData);
                    handler.sendEmptyMessage(REFRESH);

                    if (set_record_time * 60 == listener.secTime) {
                        Message msg = handler.obtainMessage();
                        msg.what = 5;
                        handler.sendMessage(msg);
                    }
                }
            };
        }

        @Override
        public void run() {
            timer.schedule(timerTask, 0, Listener.PRE);
        }

        /**
         * 停止更新
         */
        public void stopSelf() {
            if (timerTask!=null){
                timerTask.cancel();
            }
            if (timer!=null){
                timer.cancel();
            }
            handler.removeMessages(REFRESH);
            listener.save();
        }
    }

    /**
     * 宫缩复位包
     *
     * @param value 0-3
     * @return
     */
    public static byte[] tocoReset(int value) {
        byte[] cmd = new byte[9];
        byte tmp = (byte) value;
        if (value > 3 || value < 0) {
            tmp = 0;
        }
        cmd[0] = 85;
        cmd[1] = -86;
        cmd[2] = 10;
        cmd[3] = -1;
        cmd[4] = tmp;
        cmd[5] = 1;
        cmd[6] = -1;
        cmd[7] = -1;
        cmd[8] = makeSum(cmd);
        return cmd;
    }

    /**
     * 计算命令校验和
     *
     * @param cmd
     * @return
     */
    private static byte makeSum(byte[] cmd) {
        int len = cmd.length - 4;
        byte sum = 0;
        for (int i = 0; i < len; i++) {
            sum = (byte) ((sum + cmd[3 + i]) & 0xff);
        }
        return sum;
    }

    /**
     * 用户按手机外部键时的监听事件（这里监听音量大小和返回键）
     */
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        // TODO Auto-generated method stub
//        switch (keyCode) {
//            case KeyEvent.KEYCODE_BACK:// 返回键
//                if (keyCode == KeyEvent.KEYCODE_BACK) {
//                    if (isRecord) {
//                        new CommomDialog(FetalHeartMonitorActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.fetalheart_monitor_back_note_string), new com.newdjk.bdmember.widget.CommomDialog.OnCloseListener() {
//                            @Override
//                            public void onClick(Dialog dialog, boolean confirm) {
//                                dialog.cancel();
//                                if (confirm) {
//                                    unbindBluerService();
//                                    finish();
//                                }
//                            }
//                        }).show();
//                    }
//                    return true;
//                }
//                return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }

    @Override
    public void onBackPressed() {
        if (isRecord) {
            new CommomDialog(FetalHeartMonitorActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.fetalheart_monitor_back_note_string), new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.cancel();
                    if (confirm) {
                        unbindBluerService();
                        finish();
                    }
                }
            }).show();
        } else {
            unbindBluerService();
            finish();
        }

//        super.onBackPressed();

    }
}
