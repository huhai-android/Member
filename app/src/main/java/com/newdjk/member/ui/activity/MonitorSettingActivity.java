package com.newdjk.member.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.entity.GetMonitorSetEntity;
import com.newdjk.member.ui.entity.MonitorSetEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.CommomDialog;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MonitorSettingActivity extends BasicActivity {


    @BindView(R.id.automatically_start_switch_iv)
    ImageView automaticallyStartSwitchIv;
    @BindView(R.id.call_police_switch_iv)
    ImageView callPoliceSwitchIv;
    @BindView(R.id.automatically_complete_monitor_switch_iv)
    ImageView automaticallyCompleteMonitorSwitchIv;
    @BindView(R.id.wifi_automatically_upload_switch_iv)
    ImageView wifiAutomaticallyUploadSwitchIv;
    @BindView(R.id.monitor_setting_move_monitor_switch_iv)
    ImageView monitorSettingMoveMonitorSwitchIv;

    @BindView(R.id.interval_five_second_rl)
    RelativeLayout intervalFiveSecondRl;
    @BindView(R.id.interval_ten_second_rl)
    RelativeLayout intervalTenSecondRl;
    @BindView(R.id.interval_fifteen_second_rl)
    RelativeLayout intervalFifteenSecondRl;
    @BindView(R.id.interval_twenty_second_rl)
    RelativeLayout intervalTwentySecondRl;
    @BindView(R.id.interval_twenty_five_second_rl)
    RelativeLayout intervalTwentyFiveSecondRl;
    @BindView(R.id.interval_thirty_second_rl)
    RelativeLayout intervalThirtySecondRl;
    @BindView(R.id.complete_monitor_twenty_mins_rl)
    RelativeLayout completeMonitorTwentyMinsRl;
    @BindView(R.id.complete_monitor_thirty_mins_rl)
    RelativeLayout completeMonitorThirtyMinsRl;
    @BindView(R.id.complete_monitor_forty_mins_rl)
    RelativeLayout completeMonitorFortyMinsRl;

    @BindView(R.id.interval_five_second_tv)
    TextView intervalFiveSecondTv;
    @BindView(R.id.interval_five_second_yes_iv)
    ImageView intervalFiveSecondYesIv;
    @BindView(R.id.interval_ten_second_tv)
    TextView intervalTenSecondTv;
    @BindView(R.id.interval_ten_second_yes_iv)
    ImageView intervalTenSecondYesIv;
    @BindView(R.id.interval_fifteen_second_tv)
    TextView intervalFifteenSecondTv;
    @BindView(R.id.interval_fifteen_second_yes_iv)
    ImageView intervalFifteenSecondYesIv;
    @BindView(R.id.interval_twenty_second_tv)
    TextView intervalTwentySecondTv;
    @BindView(R.id.interval_twenty_second_yes_iv)
    ImageView intervalTwentySecondYesIv;
    @BindView(R.id.interval_twenty_five_second_tv)
    TextView intervalTwentyFiveSecondTv;
    @BindView(R.id.interval_twenty_five_second_yes_iv)
    ImageView intervalTwentyFiveSecondYesIv;
    @BindView(R.id.interval_thirty_second_tv)
    TextView intervalThirtySecondTv;
    @BindView(R.id.interval_thirty_second_yes_iv)
    ImageView intervalThirtySecondYesIv;
    @BindView(R.id.complete_monitor_twenty_mins_tv)
    TextView completeMonitorTwentyMinsTv;
    @BindView(R.id.complete_monitor_twenty_mins_yes_iv)
    ImageView completeMonitorTwentyMinsYesIv;
    @BindView(R.id.complete_monitor_thirty_mins_tv)
    TextView completeMonitorThirtyMinsTv;
    @BindView(R.id.complete_monitor_thirty_mins_yes_iv)
    ImageView completeMonitorThirtyMinsYesIv;
    @BindView(R.id.complete_monitor_forty_mins_tv)
    TextView completeMonitorFortyMinsTv;
    @BindView(R.id.complete_monitor_forty_mins_yes_iv)
    ImageView completeMonitorFortyMinsYesIv;

    @BindView(R.id.call_police_select_item_rl)
    RelativeLayout callPoliceSelectItemRl;
    @BindView(R.id.automatically_complete_monitor_select_item_rl)
    RelativeLayout automaticallyCompleteMonitorSelectItemRl;

    private int OpenAutoStart;
    private int OpenAlarm;
    private int OpenAutoFinish;
    private int WifiAutoUpload;
    private int OpenAutoFh;

    private int OpenAutoStart_init;
    private int OpenAlarm_init;
    private int OpenAutoFinish_init;
    private int WifiAutoUpload_init;
    private int OpenAutoFh_init;

    private int AlarmOptionId;
    private int AlarmOptionId_1;
    private int AlarmOptionId_2;
    private int AlarmOptionId_3;
    private int AlarmOptionId_4;
    private int AlarmOptionId_5;
    private int AlarmOptionId_6;

    //用于判断是否更改了选中值
    private int AlarmOptionId_init;

    private int AlarmOptionValue_1;
    private int AlarmOptionValue_2;
    private int AlarmOptionValue_3;
    private int AlarmOptionValue_4;
    private int AlarmOptionValue_5;
    private int AlarmOptionValue_6;

    private boolean AlarmOptionIsSelected_1;
    private boolean AlarmOptionIsSelected_2;
    private boolean AlarmOptionIsSelected_3;
    private boolean AlarmOptionIsSelected_4;
    private boolean AlarmOptionIsSelected_5;
    private boolean AlarmOptionIsSelected_6;

    private int FinishOptionId;
    private int FinishOptionId_1;
    private int FinishOptionId_2;
    private int FinishOptionId_3;

    //用于判断是否更改了选中值
    private int FinishOptionId_init;

    private int FinishOptionValue_1;
    private int FinishOptionValue_2;
    private int FinishOptionValue_3;

    private boolean FinishOptionIsSelected_1;
    private boolean FinishOptionIsSelected_2;
    private boolean FinishOptionIsSelected_3;

    @Override
    protected int initViewResId() {
        return R.layout.activity_monitor_setting;
    }

    @Override
    protected void initView() {

        initBackTitleLeftImg(R.color.white, getResources().getString(R.string.monitor_setting_title_string), new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveSettingContent();

            }
        });

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Map<String, String> paramsMap = new HashMap<>();
        String mAccountId = String.valueOf(SpUtils.getInt(Contants.AccountId,0));
        paramsMap.put("accountId", mAccountId);
        mMyOkhttp.get().url(HttpUrl.GetMonitorSet).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<GetMonitorSetEntity>() {
            @Override
            public void onSuccess(int statusCode, GetMonitorSetEntity response) {
                if (response.getCode() == 0) {

                    // 每个选项是否开始或关闭 0否，1是
                    OpenAutoStart = response.getData().getOpenAutoStart();
                    OpenAlarm = response.getData().getOpenAlarm();
                    OpenAutoFinish = response.getData().getOpenAutoFinish();
                    WifiAutoUpload = response.getData().getWifiAutoUpload();
                    OpenAutoFh = response.getData().getOpenAutoFh();
                    //保存初始化的值
                    OpenAutoStart_init = OpenAutoStart;
                    OpenAlarm_init = OpenAlarm;
                    OpenAutoFinish_init = OpenAutoFinish;
                    WifiAutoUpload_init = WifiAutoUpload;
                    OpenAutoFh_init = OpenAutoFh;

                    if (OpenAutoStart == 0) {
                        automaticallyStartSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                    } else if (OpenAutoStart == 1) {
                        automaticallyStartSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                    }

                    if (OpenAlarm == 0) {
                        callPoliceSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                        callPoliceSelectItemRl.setVisibility(View.GONE);
                    } else if (OpenAlarm == 1) {
                        callPoliceSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                        callPoliceSelectItemRl.setVisibility(View.VISIBLE);
                    }

                    if (OpenAutoFinish == 0) {
                        automaticallyCompleteMonitorSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                        automaticallyCompleteMonitorSelectItemRl.setVisibility(View.GONE);
                    } else if (OpenAutoFinish == 1) {
                        automaticallyCompleteMonitorSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                        automaticallyCompleteMonitorSelectItemRl.setVisibility(View.VISIBLE);
                    }

                    if (WifiAutoUpload == 0) {
                        wifiAutomaticallyUploadSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                    } else if (WifiAutoUpload == 1) {
                        wifiAutomaticallyUploadSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                    }

                    if (OpenAutoFh == 0) {
                        monitorSettingMoveMonitorSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                    } else if (OpenAutoFh == 1) {
                        monitorSettingMoveMonitorSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                    }

                    //用于返回时修改传id
                    AlarmOptionId_1 = response.getData().getAlarmOptions().get(0).getId();
                    AlarmOptionId_2 = response.getData().getAlarmOptions().get(1).getId();
                    AlarmOptionId_3 = response.getData().getAlarmOptions().get(2).getId();
                    AlarmOptionId_4 = response.getData().getAlarmOptions().get(3).getId();
                    AlarmOptionId_5 = response.getData().getAlarmOptions().get(4).getId();
                    AlarmOptionId_6 = response.getData().getAlarmOptions().get(5).getId();

                    //用于选项列表的展示
                    AlarmOptionValue_1 = response.getData().getAlarmOptions().get(0).getValue();
                    intervalFiveSecondTv.setText(AlarmOptionValue_1 + getResources().getString(R.string.monitor_setting_interval_unit_string));
                    AlarmOptionValue_2 = response.getData().getAlarmOptions().get(1).getValue();
                    intervalTenSecondTv.setText(AlarmOptionValue_2 + getResources().getString(R.string.monitor_setting_interval_unit_string));
                    AlarmOptionValue_3 = response.getData().getAlarmOptions().get(2).getValue();
                    intervalFifteenSecondTv.setText(AlarmOptionValue_3 + getResources().getString(R.string.monitor_setting_interval_unit_string));
                    AlarmOptionValue_4 = response.getData().getAlarmOptions().get(3).getValue();
                    intervalTwentySecondTv.setText(AlarmOptionValue_4 + getResources().getString(R.string.monitor_setting_interval_unit_string));
                    AlarmOptionValue_5 = response.getData().getAlarmOptions().get(4).getValue();
                    intervalTwentyFiveSecondTv.setText(AlarmOptionValue_5 + getResources().getString(R.string.monitor_setting_interval_unit_string));
                    AlarmOptionValue_6 = response.getData().getAlarmOptions().get(5).getValue();
                    intervalThirtySecondTv.setText(AlarmOptionValue_6 + getResources().getString(R.string.monitor_setting_interval_unit_string));

                    //用于判读是否被选中
                    AlarmOptionIsSelected_1 = response.getData().getAlarmOptions().get(0).isIsSelected();
                    if (AlarmOptionIsSelected_1) {
                        intervalFiveSecondYesIv.setVisibility(View.VISIBLE);
                        AlarmOptionId = AlarmOptionId_1;
                        //保存选中的初始值
                        AlarmOptionId_init = AlarmOptionId_1;
                    } else {
                        intervalFiveSecondYesIv.setVisibility(View.GONE);
                    }
                    AlarmOptionIsSelected_2 = response.getData().getAlarmOptions().get(1).isIsSelected();
                    if (AlarmOptionIsSelected_2) {
                        intervalTenSecondYesIv.setVisibility(View.VISIBLE);
                        AlarmOptionId = AlarmOptionId_2;
                        //保存选中的初始值
                        AlarmOptionId_init = AlarmOptionId_2;
                    } else {
                        intervalTenSecondYesIv.setVisibility(View.GONE);
                    }
                    AlarmOptionIsSelected_3 = response.getData().getAlarmOptions().get(2).isIsSelected();
                    if (AlarmOptionIsSelected_3) {
                        intervalFifteenSecondYesIv.setVisibility(View.VISIBLE);
                        AlarmOptionId = AlarmOptionId_3;
                        //保存选中的初始值
                        AlarmOptionId_init = AlarmOptionId_3;
                    } else {
                        intervalFifteenSecondYesIv.setVisibility(View.GONE);
                    }
                    AlarmOptionIsSelected_4 = response.getData().getAlarmOptions().get(3).isIsSelected();
                    if (AlarmOptionIsSelected_4) {
                        intervalTwentySecondYesIv.setVisibility(View.VISIBLE);
                        AlarmOptionId = AlarmOptionId_4;
                        //保存选中的初始值
                        AlarmOptionId_init = AlarmOptionId_4;
                    } else {
                        intervalTwentySecondYesIv.setVisibility(View.GONE);
                    }
                    AlarmOptionIsSelected_5 = response.getData().getAlarmOptions().get(4).isIsSelected();
                    if (AlarmOptionIsSelected_5) {
                        intervalTwentyFiveSecondYesIv.setVisibility(View.VISIBLE);
                        AlarmOptionId = AlarmOptionId_5;
                        //保存选中的初始值
                        AlarmOptionId_init = AlarmOptionId_5;
                    } else {
                        intervalTwentyFiveSecondYesIv.setVisibility(View.GONE);
                    }
                    AlarmOptionIsSelected_6 = response.getData().getAlarmOptions().get(5).isIsSelected();
                    if (AlarmOptionIsSelected_6) {
                        intervalThirtySecondYesIv.setVisibility(View.VISIBLE);
                        AlarmOptionId = AlarmOptionId_6;
                        //保存选中的初始值
                        AlarmOptionId_init = AlarmOptionId_6;
                    } else {
                        intervalThirtySecondYesIv.setVisibility(View.GONE);
                    }

                    //用于返回时修改传id
                    FinishOptionId_1 = response.getData().getFinishOptions().get(0).getId();
                    FinishOptionId_2 = response.getData().getFinishOptions().get(1).getId();
                    FinishOptionId_3 = response.getData().getFinishOptions().get(2).getId();

                    //用于选项列表的展示
                    FinishOptionValue_1 = response.getData().getFinishOptions().get(0).getValue();
                    completeMonitorTwentyMinsTv.setText(getResources().getString(R.string.monitor_setting_complete_front_string) + FinishOptionValue_1 + getResources().getString(R.string.monitor_setting_complete_behind_string));
                    FinishOptionValue_2 = response.getData().getFinishOptions().get(1).getValue();
                    completeMonitorThirtyMinsTv.setText(getResources().getString(R.string.monitor_setting_complete_front_string) + FinishOptionValue_2 + getResources().getString(R.string.monitor_setting_complete_behind_string));
                    FinishOptionValue_3 = response.getData().getFinishOptions().get(2).getValue();
                    completeMonitorFortyMinsTv.setText(getResources().getString(R.string.monitor_setting_complete_front_string) + FinishOptionValue_3 + getResources().getString(R.string.monitor_setting_complete_behind_string));

                    //用于判读是否被选中
                    FinishOptionIsSelected_1 = response.getData().getFinishOptions().get(0).isIsSelected();
                    if (FinishOptionIsSelected_1) {
                        completeMonitorTwentyMinsYesIv.setVisibility(View.VISIBLE);
                        FinishOptionId = FinishOptionId_1;
                        //保存选中的初始值
                        FinishOptionId_init = FinishOptionId_1;
                    } else {
                        completeMonitorTwentyMinsYesIv.setVisibility(View.GONE);
                    }
                    FinishOptionIsSelected_2 = response.getData().getFinishOptions().get(1).isIsSelected();
                    if (FinishOptionIsSelected_2) {
                        completeMonitorThirtyMinsYesIv.setVisibility(View.VISIBLE);
                        FinishOptionId = FinishOptionId_2;
                        //保存选中的初始值
                        FinishOptionId_init = FinishOptionId_2;
                    } else {
                        completeMonitorThirtyMinsYesIv.setVisibility(View.GONE);
                    }
                    FinishOptionIsSelected_3 = response.getData().getFinishOptions().get(2).isIsSelected();
                    if (FinishOptionIsSelected_3) {
                        completeMonitorFortyMinsYesIv.setVisibility(View.VISIBLE);
                        FinishOptionId = FinishOptionId_3;
                        //保存选中的初始值
                        FinishOptionId_init = FinishOptionId_3;
                    } else {
                        completeMonitorFortyMinsYesIv.setVisibility(View.GONE);
                    }
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){

            saveSettingContent();

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void otherViewClick(View view) {

    }

    private void saveSettingContent() {
        if (OpenAutoStart != OpenAutoStart_init || OpenAlarm != OpenAlarm_init || OpenAutoFinish != OpenAutoFinish_init || WifiAutoUpload != WifiAutoUpload_init || AlarmOptionId != AlarmOptionId_init || FinishOptionId != FinishOptionId_init || OpenAutoFh != OpenAutoFh_init) {

            new CommomDialog(MonitorSettingActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.monitor_setting_save_tip_string), new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.cancel();
                    if (confirm) {
                        Map<String, String> paramsMap = new HashMap<>();
                        paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
                        paramsMap.put("OpenAutoStart", OpenAutoStart + "");
                        paramsMap.put("OpenAlarm", OpenAlarm + "");
                        paramsMap.put("OpenAutoFinish", OpenAutoFinish + "");
                        paramsMap.put("OpenAutoFh", OpenAutoFh + "");
                        paramsMap.put("AlarmOptionId", AlarmOptionId + "");
                        paramsMap.put("FinishOptionId", FinishOptionId + "");
                        paramsMap.put("WifiAutoUpload", WifiAutoUpload + "");

                        mMyOkhttp.post().url(HttpUrl.MonitorSet).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<MonitorSetEntity>() {
                            @Override
                            public void onSuccess(int statusCode, MonitorSetEntity response) {
                                if (response.getCode() == 0) {
                                    MonitorSettingActivity.this.finish();
                                } else {
                                    Toast.makeText(MonitorSettingActivity.this,getResources().getString(R.string.monitor_setting_modify_fail_string),Toast.LENGTH_SHORT).show();
                                    MonitorSettingActivity.this.finish();
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

        } else {
            MonitorSettingActivity.this.finish();
        }
    }

    @OnClick({R.id.automatically_start_switch_iv, R.id.call_police_switch_iv, R.id.automatically_complete_monitor_switch_iv, R.id.wifi_automatically_upload_switch_iv, R.id.interval_five_second_rl, R.id.interval_ten_second_rl, R.id.interval_fifteen_second_rl, R.id.interval_twenty_second_rl, R.id.interval_twenty_five_second_rl, R.id.interval_thirty_second_rl, R.id.complete_monitor_twenty_mins_rl, R.id.complete_monitor_thirty_mins_rl, R.id.complete_monitor_forty_mins_rl,R.id.monitor_setting_move_monitor_switch_iv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.automatically_start_switch_iv:
                if (OpenAutoStart == 0) {
                    automaticallyStartSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                    OpenAutoStart = 1;
                } else if (OpenAutoStart == 1) {
                    automaticallyStartSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                    OpenAutoStart = 0;
                }
                break;
            case R.id.call_police_switch_iv:
                if (OpenAlarm == 0) {
                    callPoliceSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                    OpenAlarm = 1;
                    callPoliceSelectItemRl.setVisibility(View.VISIBLE);
                } else if (OpenAlarm == 1) {
                    callPoliceSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                    OpenAlarm = 0;
                    callPoliceSelectItemRl.setVisibility(View.GONE);
                }
                break;
            case R.id.automatically_complete_monitor_switch_iv:
                if (OpenAutoFinish == 0) {
                    automaticallyCompleteMonitorSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                    OpenAutoFinish = 1;
                    automaticallyCompleteMonitorSelectItemRl.setVisibility(View.VISIBLE);
                } else if (OpenAutoFinish == 1) {
                    automaticallyCompleteMonitorSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                    OpenAutoFinish = 0;
                    automaticallyCompleteMonitorSelectItemRl.setVisibility(View.GONE);
                }
                break;
            case R.id.wifi_automatically_upload_switch_iv:
                if (WifiAutoUpload == 0) {
                    wifiAutomaticallyUploadSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                    WifiAutoUpload = 1;
                } else if (WifiAutoUpload == 1) {
                    wifiAutomaticallyUploadSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                    WifiAutoUpload = 0;
                }
                break;

            case R.id.interval_five_second_rl:
                intervalFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));
                intervalTenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalFifteenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentyFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalThirtySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));

                intervalFiveSecondYesIv.setVisibility(View.VISIBLE);
                intervalTenSecondYesIv.setVisibility(View.GONE);
                intervalFifteenSecondYesIv.setVisibility(View.GONE);
                intervalTwentySecondYesIv.setVisibility(View.GONE);
                intervalTwentyFiveSecondYesIv.setVisibility(View.GONE);
                intervalThirtySecondYesIv.setVisibility(View.GONE);

                AlarmOptionId = AlarmOptionId_1;
                break;
            case R.id.interval_ten_second_rl:
                intervalFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));
                intervalFifteenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentyFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalThirtySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));

                intervalFiveSecondYesIv.setVisibility(View.GONE);
                intervalTenSecondYesIv.setVisibility(View.VISIBLE);
                intervalFifteenSecondYesIv.setVisibility(View.GONE);
                intervalTwentySecondYesIv.setVisibility(View.GONE);
                intervalTwentyFiveSecondYesIv.setVisibility(View.GONE);
                intervalThirtySecondYesIv.setVisibility(View.GONE);

                AlarmOptionId = AlarmOptionId_2;
                break;
            case R.id.interval_fifteen_second_rl:
                intervalFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalFifteenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));
                intervalTwentySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentyFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalThirtySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));

                intervalFiveSecondYesIv.setVisibility(View.GONE);
                intervalTenSecondYesIv.setVisibility(View.GONE);
                intervalFifteenSecondYesIv.setVisibility(View.VISIBLE);
                intervalTwentySecondYesIv.setVisibility(View.GONE);
                intervalTwentyFiveSecondYesIv.setVisibility(View.GONE);
                intervalThirtySecondYesIv.setVisibility(View.GONE);

                AlarmOptionId = AlarmOptionId_3;
                break;
            case R.id.interval_twenty_second_rl:
                intervalFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalFifteenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));
                intervalTwentyFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalThirtySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));

                intervalFiveSecondYesIv.setVisibility(View.GONE);
                intervalTenSecondYesIv.setVisibility(View.GONE);
                intervalFifteenSecondYesIv.setVisibility(View.GONE);
                intervalTwentySecondYesIv.setVisibility(View.VISIBLE);
                intervalTwentyFiveSecondYesIv.setVisibility(View.GONE);
                intervalThirtySecondYesIv.setVisibility(View.GONE);

                AlarmOptionId = AlarmOptionId_4;
                break;
            case R.id.interval_twenty_five_second_rl:
                intervalFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalFifteenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentyFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));
                intervalThirtySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));

                intervalFiveSecondYesIv.setVisibility(View.GONE);
                intervalTenSecondYesIv.setVisibility(View.GONE);
                intervalFifteenSecondYesIv.setVisibility(View.GONE);
                intervalTwentySecondYesIv.setVisibility(View.GONE);
                intervalTwentyFiveSecondYesIv.setVisibility(View.VISIBLE);
                intervalThirtySecondYesIv.setVisibility(View.GONE);

                AlarmOptionId = AlarmOptionId_5;
                break;
            case R.id.interval_thirty_second_rl:
                intervalFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalFifteenSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalTwentyFiveSecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                intervalThirtySecondTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));

                intervalFiveSecondYesIv.setVisibility(View.GONE);
                intervalTenSecondYesIv.setVisibility(View.GONE);
                intervalFifteenSecondYesIv.setVisibility(View.GONE);
                intervalTwentySecondYesIv.setVisibility(View.GONE);
                intervalTwentyFiveSecondYesIv.setVisibility(View.GONE);
                intervalThirtySecondYesIv.setVisibility(View.VISIBLE);

                AlarmOptionId = AlarmOptionId_6;
                break;

            case R.id.complete_monitor_twenty_mins_rl:
                completeMonitorTwentyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));
                completeMonitorThirtyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                completeMonitorFortyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));

                completeMonitorTwentyMinsYesIv.setVisibility(View.VISIBLE);
                completeMonitorThirtyMinsYesIv.setVisibility(View.GONE);
                completeMonitorFortyMinsYesIv.setVisibility(View.GONE);

                FinishOptionId = FinishOptionId_1;
                break;
            case R.id.complete_monitor_thirty_mins_rl:
                completeMonitorTwentyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                completeMonitorThirtyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));
                completeMonitorFortyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));

                completeMonitorTwentyMinsYesIv.setVisibility(View.GONE);
                completeMonitorThirtyMinsYesIv.setVisibility(View.VISIBLE);
                completeMonitorFortyMinsYesIv.setVisibility(View.GONE);

                FinishOptionId = FinishOptionId_2;
                break;
            case R.id.complete_monitor_forty_mins_rl:
                completeMonitorTwentyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                completeMonitorThirtyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_set_content_color));
                completeMonitorFortyMinsTv.setTextColor(getResources().getColor(R.color.monitor_setting_select_item_color));

                completeMonitorTwentyMinsYesIv.setVisibility(View.GONE);
                completeMonitorThirtyMinsYesIv.setVisibility(View.GONE);
                completeMonitorFortyMinsYesIv.setVisibility(View.VISIBLE);

                FinishOptionId = FinishOptionId_3;
                break;
            case R.id.monitor_setting_move_monitor_switch_iv:
                if (OpenAutoFh == 0) {
                    monitorSettingMoveMonitorSwitchIv.setBackgroundResource(R.mipmap.setting_toggle_btn_checked);
                    OpenAutoFh = 1;
                } else if (OpenAutoFh == 1) {
                    monitorSettingMoveMonitorSwitchIv.setBackgroundResource(R.mipmap.toggle_btn_unchecked);
                    OpenAutoFh = 0;
                }
                break;
        }
    }

}
