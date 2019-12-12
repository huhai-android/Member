package com.newdjk.member.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.entity.AskCheckEntity;
import com.newdjk.member.ui.entity.FHREntity;
import com.newdjk.member.ui.entity.FhrDataEntity;
import com.newdjk.member.ui.entity.GetMonitorDataEntity;
import com.newdjk.member.ui.entity.MonitorDataEntity;
import com.newdjk.member.ui.entity.SubmitAskEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.Listener;
import com.newdjk.member.utils.ScreenUtil;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.CommomDialog;
import com.newdjk.member.views.FhrView;
import com.newdjk.member.views.LoadDialog;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MonitorDetailActivity extends BasicActivity implements IWXAPIEventHandler {

    public static List<Activity> heart_monitor_lists = new ArrayList<>();

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

    @BindView(R.id.monitor_record_rl)
    RelativeLayout monitorRecordRl;
    @BindView(R.id.play_stop_btn)
    Button playStopBtn;
    @BindView(R.id.ask_doctor_btn)
    Button askDoctorBtn;
    @BindView(R.id.share_btn)
    Button shareBtn;

    @BindView(R.id.data_show_ll)
    LinearLayout dataShowLl;
    @BindView(R.id.play_stop_btn_agagin)
    Button playStopBtnAgagin;
    @BindView(R.id.share_btn_agagin)
    Button shareBtnAgagin;


    private String flag;
    private String DeviceNo;
    private String ServicePhone;
    private int PatientId;
    private String go_path;
    private List<MonitorDataEntity> monitor_data_lists;
    private String record_time;
    private int record_time_int;
    private long begindate;

    private List data_lists = new LinkedList();
    private int ask_MonitorRecordId;

    private FhrView mFhrView;
    private Listener.TimeData timeData;
    private LinkedList<Listener.TimeData> fhrView_dataList = new LinkedList<Listener.TimeData>();
    private Dialog mDialog;

    private View mInflate;

    private LinearLayout mFriend, mZoom;
    private TextView mTvCancel;
    private int id;
    //胎动次数
    private int beatCount = 0;
    private int OpenAutoFh;

    @Override
    protected int initViewResId() {
        return R.layout.activity_monitor_detail;
    }

    private IWXAPI iwxapi;
    private MyApplication myApplication;

    @Override
    public void onBackPressed() {
        MonitorDetailActivity.this.finish();
        if (flag.equals("1")) {
            myApplication.clearMonitorDataEntityList();
        }
    }

    @Override
    protected void initView() {
        mFhrView = (FhrView) findViewById(R.id.fhrview);
        //保持屏蔽常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //initBackTitleBgRes(R.color.fetalheart_monitor_data_bg_color, getResources().getString(R.string.monitor_detail_title_string));
        initBackTitleLeftImg(R.color.fetalheart_monitor_data_bg_color, getResources().getString(R.string.monitor_detail_title_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MonitorDetailActivity.this.finish();
                if (flag.equals("1")) {
                    myApplication.clearMonitorDataEntityList();
                }
            }
        });
        iwxapi = WXAPIFactory.createWXAPI(this, MainConstant.WEIXIN_APP_ID, false);
        iwxapi.registerApp(MainConstant.WEIXIN_APP_ID);
        iwxapi.handleIntent(getIntent(), this);

        flag = getIntent().getStringExtra("flag");
        record_time = getIntent().getStringExtra("tlong");
        recordTimeLongTv.setText(record_time);
        if (flag.equals("1")) {
            myApplication = (MyApplication) this.getApplication();
            PatientId = getIntent().getIntExtra("PatientId", -1);
            OpenAutoFh = getIntent().getIntExtra("OpenAutoFh", 0); //开启自动胎心（0否，1是）  默认是非自动胎心
            go_path = getIntent().getStringExtra("go_path");
            DeviceNo = getIntent().getStringExtra("DeviceNo");
            ServicePhone = getIntent().getStringExtra("ServicePhone");
            //monitor_data_lists = (List<MonitorDataEntity>) getIntent().getSerializableExtra("monitor_data");
            monitor_data_lists = myApplication.getMonitorDataEntityList();

            int mins = Integer.parseInt(record_time.substring(0, 2)) * 60;
            int sec = Integer.parseInt(record_time.substring(3));
            record_time_int = mins + sec;
            begindate = getIntent().getLongExtra("begindate", 0);
            begindate = (begindate - begindate % 1000) / 1000;

            //数据默认值
            contractionsPressureDataTv.setText(monitor_data_lists.get(monitor_data_lists.size() - 1).getToco() + "");
            heartRateDataTv.setText(monitor_data_lists.get(monitor_data_lists.size() - 1).getFhr1() + "");
            ////开启自动胎心（0否，1是）
//            String text = OpenAutoFh == 0 ? (monitor_data_lists.get(monitor_data_lists.size() - 1).getFmcount() + "") : (monitor_data_lists.get(monitor_data_lists.size() - 1).getAfmcount() + "");
            String text =  (monitor_data_lists.get(monitor_data_lists.size() - 1).getFmcount() +  monitor_data_lists.get(monitor_data_lists.size() - 1).getAfmcount()) + "";
            moveNumberTv.setText(text);

            //开始时间和结束时间的显示
            String start_time_text = getIntent().getStringExtra("start_time");
            String end_time_text = getIntent().getStringExtra("end_time");
            if (start_time_text.substring(0, 10).equals(end_time_text.substring(0, 10))) {
                currentDateTv.setText(start_time_text + " " + end_time_text.substring(11));
            } else {
                currentDateTv.setText(start_time_text + " " + end_time_text);
            }


            mFhrView.setDataList(fhrView_dataList);
            //时间总长
            //endTimeTv.setText((mins >= 10 ? mins : "0" + mins) + ":" + (sec >= 10 ? sec : "0" + sec));
        } else if (flag.equals("2")) {
            mFhrView.setDataList(fhrView_dataList);
            id = getIntent().getIntExtra("id", -1);
            Map<String, String> paramsMap_data = new HashMap<>();
            paramsMap_data.put("id", id + "");
            mMyOkhttp.get().url(HttpUrl.GetMonitorData).headers(HeadUitl.instance.getHeads()).params(paramsMap_data).tag(this).enqueue(new GsonResponseHandler<GetMonitorDataEntity>() {
                @Override
                public void onSuccess(int statusCode, GetMonitorDataEntity response) {

                    if (response.getCode() == 0) {
                        monitor_data_lists = new LinkedList<>();
                        List<Integer> data = response.getData().getData();
                        for (int i = 0; i < data.size() / 10; i++) {
                            timeData = new Listener.TimeData(data.get(i + 10), data.get(3 + i * 10), data.get(2 + i * 10), 0, 0, 0);
                            mFhrView.addBeat(timeData);
                            //fhrView_dataList.add(timeData);
                            //画出胎动点击的标记
//                            if (OpenAutoFh == 0) {
                            if (response.getData().getData().get(7 + i * 10) == 1) {
                                mFhrView.addSelfBeat();
                                beatCount++;
                            }
//                            } else {
                            if (response.getData().getData().get(8 + i * 10) == 1) {
                                mFhrView.addSelfBeat();
                                beatCount++;
                            }
//                            }

                            //用于点击和回放
                            MonitorDataEntity monitorDataEntity = new MonitorDataEntity();
                            monitorDataEntity.setFhr1(data.get(0 + i * 10));
                            monitorDataEntity.setFhr2(data.get(1 + i * 10));
                            monitorDataEntity.setAfm(data.get(2 + i * 10));
                            monitorDataEntity.setToco(data.get(3 + i * 10));
                            monitorDataEntity.setFhrsign(data.get(4 + i * 10));
                            monitorDataEntity.setDevicePower(data.get(5 + i * 10));
                            monitorDataEntity.setAfmcount(data.get(6 + i * 10));
                            monitorDataEntity.setFmcount(data.get(7 + i * 10));
                            monitorDataEntity.setCharge(data.get(8 + i * 10));
                            monitorDataEntity.setTocoreset(data.get(9 + i * 10));
                            monitor_data_lists.add(monitorDataEntity);
                        }
                        moveNumberTv.setText(String.valueOf(beatCount));
                        //时间总长
                        int tlong = response.getData().getTlong();
                        int mins = tlong / 60;
                        int seconds = tlong % 60;
                        //endTimeTv.setText((mins >= 10 ? mins : "0" + mins) + "：" +  (seconds >= 10 ? seconds : "0" + seconds ));
                        //开始时间
                        currentDateTv.setText(secondToDate(response.getData().getBegindate(), "yyyy-MM-dd hh:mm:ss"));
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        if (flag.equals("1")) {
            monitorRecordRl.setVisibility(View.VISIBLE);
            dataShowLl.setVisibility(View.GONE);
            //提示正在上传数据
            LoadDialog.show(MonitorDetailActivity.this, getResources().getString(R.string.monitor_detail_upload_tip_string));
            //上传数据功能
            for (int i = 0; i < monitor_data_lists.size(); i++) {
                data_lists.add(i * 10 + 0, monitor_data_lists.get(i).getFhr1());
                data_lists.add(i * 10 + 1, monitor_data_lists.get(i).getFhr2());
                data_lists.add(i * 10 + 2, monitor_data_lists.get(i).getAfm());
                data_lists.add(i * 10 + 3, monitor_data_lists.get(i).getToco());
                data_lists.add(i * 10 + 4, monitor_data_lists.get(i).getFhrsign());
                data_lists.add(i * 10 + 5, monitor_data_lists.get(i).getDevicePower());
                data_lists.add(i * 10 + 6, monitor_data_lists.get(i).getAfmcount());
                data_lists.add(i * 10 + 7, monitor_data_lists.get(i).getFmcount());
                data_lists.add(i * 10 + 8, monitor_data_lists.get(i).getCharge());
                data_lists.add(i * 10 + 9, monitor_data_lists.get(i).getTocoreset());

                //显示画图
                timeData = new Listener.TimeData(monitor_data_lists.get(i).getFhr1(), monitor_data_lists.get(i).getToco(), monitor_data_lists.get(i).getAfm(), 0, 0, 0);
                mFhrView.addBeat(timeData);
                //画出胎动点击的标记
//                if (OpenAutoFh == 0) {
                if (monitor_data_lists.get(i).getFmcount() == 1) {
                    mFhrView.addSelfBeat();
                    beatCount++;
                }
//                } else {
                if (monitor_data_lists.get(i).getAfmcount() == 1) {
                    mFhrView.addSelfBeat();
                    beatCount++;
                }
//                }
            }
            moveNumberTv.setText(String.valueOf(beatCount));
            FHREntity fhrEntity = new FHREntity();
            fhrEntity.setAccountId(SpUtils.getInt(Contants.AccountId,0));
            fhrEntity.setPatientId(PatientId);
            fhrEntity.setDeviceNo(DeviceNo);

            FHREntity.FhrDataBean fhrDataBean = new FHREntity.FhrDataBean();
            fhrDataBean.setType("3p");
            fhrDataBean.setBluenum(DeviceNo);

            FHREntity.FhrDataBean.KeyBean keyBean = new FHREntity.FhrDataBean.KeyBean();
            keyBean.setFhr1(0);
            keyBean.setFhr2(1);
            keyBean.setAfm(2);
            keyBean.setToco(3);
            keyBean.setFhrsign(4);
            keyBean.setAfmcount(5);
            keyBean.setFmcount(6);
            keyBean.setBattery(7);
            keyBean.setCharge(8);
            keyBean.setTocoreset(9);
            fhrDataBean.setKey(keyBean);

            fhrDataBean.setData(data_lists);
            fhrDataBean.setTlong(record_time_int);
            fhrDataBean.setBegindate(begindate);

            fhrEntity.setFhrData(fhrDataBean);

            mMyOkhttp.post().url(HttpUrl.FhrData).headers(HeadUitl.instance.getHeads()).jsonParams(new Gson().toJson(fhrEntity)).tag(this).enqueue(new GsonResponseHandler<FhrDataEntity>() {
                @Override
                public void onSuccess(int statusCode, FhrDataEntity response) {
                    if (response.getCode() == 0) {
                        LoadDialog.clear();
                        Toast.makeText(MonitorDetailActivity.this, "上传成功！", Toast.LENGTH_SHORT).show();
                        ask_MonitorRecordId = response.getData();
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        } else if (flag.equals("2")) {
            monitorRecordRl.setVisibility(View.GONE);
            dataShowLl.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {

            case R.id.mWechatFriend:
                share(SHARE_TYPE.Type_WXSceneSession);
                mDialog.dismiss();
                break;
            case R.id.mWechatZone:
                share(SHARE_TYPE.Type_WXSceneTimeline);
                mDialog.dismiss();
                break;
            case R.id.mCancel:
                mDialog.dismiss();
                break;
        }

    }

    private int draw_postion = 0;
    private Timer playR_timer;
    private TimerTask playR_task;

    private void startPlayRTask() {
        playR_timer = new Timer();
        playR_task = new TimerTask() {
            @Override
            public void run() {
                Message msg = mhandler.obtainMessage();
                msg.arg1 = 1010;
                mhandler.sendMessage(msg);
            }
        };
        playR_timer.schedule(playR_task, 0, 250);
    }

    private void endPlayRTask() {
        if (playR_timer != null) {
            playR_timer.cancel();
            playR_timer = null;
        }
        if (playR_task != null) {
            playR_task.cancel();
            playR_task = null;
        }
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.arg1) {
                case 1010:
                    if (draw_postion < monitor_data_lists.size()) {
                        //图上画点
                        timeData = new Listener.TimeData(monitor_data_lists.get(draw_postion).getFhr1(), monitor_data_lists.get(draw_postion).getToco(), monitor_data_lists.get(draw_postion).getAfm(), 0, 0, 0);
                        //添加点的数据
                        mFhrView.addBeat(timeData);
                        //画出胎动点击的标记
//                        if (OpenAutoFh == 0) {
                        if (monitor_data_lists.get(draw_postion).getFmcount() == 1) {
                            mFhrView.addSelfBeat();
                        }
//                        } else {
                        if (monitor_data_lists.get(draw_postion).getAfmcount() == 1) {
                            mFhrView.addSelfBeat();
                        }
//                        }

                        //宫压缩数据显示
                        contractionsPressureDataTv.setText(monitor_data_lists.get(draw_postion).getToco() + "");
                        //胎心率数据显示
                        heartRateDataTv.setText(monitor_data_lists.get(draw_postion).getFhr1() + "");
                        //胎动数显示
//                        if (OpenAutoFh == 0) {
//                            int moveNumber = beatCount + monitor_data_lists.get(draw_postion).getFmcount() + monitor_data_lists.get(draw_postion).getFmcount();
//                            moveNumberTv.setText(moveNumber + "");
//                        } else {
//                            int moveNumber = beatCount + monitor_data_lists.get(draw_postion).getAfmcount() + monitor_data_lists.get(draw_postion).getAfmcount();
//                            moveNumberTv.setText(moveNumber + "");
//                        }
                        int moveNumber =(beatCount + (monitor_data_lists.get(draw_postion).getFmcount() + monitor_data_lists.get(draw_postion).getFmcount())+(monitor_data_lists.get(draw_postion).getAfmcount() + monitor_data_lists.get(draw_postion).getAfmcount()));
                        moveNumberTv.setText(moveNumber + "");
                        //时间进度显示
                        if (draw_postion % 4 == 0) {
                            int mins = draw_postion * 250 / 1000 / 60;
                            int sec = draw_postion * 250 / 1000 % 60;
                            recordTimeLongTv.setText((mins >= 10 ? mins : "0" + mins) + ":" + (sec >= 10 ? sec : "0" + sec) + "/" + record_time);
                        }
                        draw_postion = draw_postion + 1;
                    } else {
                        endPlayRTask();
                        draw_postion = 0;
//                        playStopBtn.setText("播放");
                        beatCount = 0;

                        draw_view = true;
                    }

                    break;
            }
        }
    };

    private boolean draw_view = true;

    @OnClick({R.id.ask_doctor_btn, R.id.play_stop_btn, R.id.share_btn, R.id.play_stop_btn_agagin, R.id.share_btn_agagin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ask_doctor_btn:
                if (record_time_int < 20 * 60) {
                    Toast.makeText(MonitorDetailActivity.this, getResources().getString(R.string.monitor_detail_time_low_no_ask_doctor_string), Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
                paramsMap.put("MonitorRecordId", ask_MonitorRecordId + "");
                mMyOkhttp.post().url(HttpUrl.AskCheck).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<AskCheckEntity>() {
                    @Override
                    public void onSuccess(int statusCode, AskCheckEntity response) {
                        if (response.getCode() == 0) {
                            new CommomDialog(MonitorDetailActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.fetal_heart_ask_doctor_tip_string), new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    dialog.cancel();
                                    if (confirm) {
                                        Map<String, String> paramsMap = new HashMap<>();
                                        paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
                                        paramsMap.put("MonitorRecordId", ask_MonitorRecordId + "");
                                        mMyOkhttp.post().url(HttpUrl.SubmitAsk).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<SubmitAskEntity>() {
                                            @Override
                                            public void onSuccess(int statusCode, SubmitAskEntity response) {
                                                if (response.getCode() == 0) {
                                                    Intent intent = new Intent(MonitorDetailActivity.this, RequestResultsActivity.class);
                                                    intent.putExtra("ServicePhone", ServicePhone);
                                                    intent.putExtra("go_path", go_path);
                                                    toActivity(intent);
                                                    //加入界面管理
                                                    heart_monitor_lists.add(MonitorDetailActivity.this);
                                                    //清除内存
                                                    myApplication.clearMonitorDataEntityList();
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
                            Toast.makeText(MonitorDetailActivity.this, getResources().getString(R.string.fetal_heart_service_out_date_string), Toast.LENGTH_SHORT).show();
                        } else if (response.getCode() == 2) {
                            Toast.makeText(MonitorDetailActivity.this, getResources().getString(R.string.fetal_heart_no_service_string), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
                break;
            case R.id.play_stop_btn:
                if (draw_view) {

                    beatCount = 0;

                    if (draw_postion == 0) {
                        if (fhrView_dataList.size() != 0) {
                            mFhrView.clear();
                        }
                    }

                    startPlayRTask();

                    draw_view = false;
//                    playStopBtn.setText("停止");
                } else {
                    endPlayRTask();

                    draw_view = true;
//                    playStopBtn.setText("播放");
                }
                break;

            case R.id.share_btn:
                showBottomDialog(view);
                break;

            case R.id.play_stop_btn_agagin:
                if (draw_view) {

                    beatCount = 0;

                    if (draw_postion == 0) {
                        if (fhrView_dataList.size() != 0) {
                            mFhrView.clear();
                        }
                    }

                    startPlayRTask();

                    draw_view = false;
                    playStopBtnAgagin.setText("停止");
                } else {
                    endPlayRTask();

                    draw_view = true;
                    playStopBtnAgagin.setText("播放");
                }
                break;
            case R.id.share_btn_agagin:
                showBottomDialog(view);
                break;
        }
    }

    @OnClick()
    public void onViewClicked() {
    }

    //秒转换成日期格式
    private String secondToDate(long second, String patten) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(second * 1000);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(patten);
        String dateString = format.format(date);
        return dateString;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();

    }

    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}

    private void share(SHARE_TYPE type) {
        Bitmap thumb = ScreenUtil.getScreen(this);
        WXImageObject imageObject = new WXImageObject(thumb);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        switch (type) {
            case Type_WXSceneSession:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Type_WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }

        iwxapi.sendReq(req);

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }


    public void showBottomDialog(View view) {
        mDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(this).inflate(R.layout.dialog_share, null);
        //初始化控件
        mFriend = (LinearLayout) mInflate.findViewById(R.id.mWechatFriend);
        mZoom = (LinearLayout) mInflate.findViewById(R.id.mWechatZone);
        mTvCancel = mInflate.findViewById(R.id.mCancel);
        mFriend.setOnClickListener(this);
        mZoom.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        //将布局设置给Dialog
        mDialog.setContentView(mInflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 10;//设置Dialog距离底部的距离
//       将属性设置给窗体
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

}
