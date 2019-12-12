package com.newdjk.member.ui.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.adapter.DeviceListAdapter;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.CommomDialog;
import com.newdjk.member.views.LoadDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceConnectActivity extends BasicActivity {
    private final int SDK_PERMISSION_REQUEST = 127;

    private static final int REQUEST_ENABLE_BT = 1;

    @BindView(R.id.device_connect_all_ll)
    LinearLayout deviceConnectAllLl;
    @BindView(R.id.connecting_iv)
    ImageView connectingIv;
    @BindView(R.id.top_right)
    ImageView topRight;

    private Handler mHandler;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            anim.stop();
        }
    };

    private BluetoothAdapter mBluetoothAdapter;
    private boolean mScanning;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 30000;

    private int code;
    private String DeviceNo;
    private String DeviceNo_LastNight;
    private AnimationDrawable anim;

    private HaveBindEntity.DataBean.PatientBean patientBean;
    private int ServicePackId;
    private String ServicePhone;
    private final static int SHOW_TIPS = 100;
    private final static int SHOW_NO_BLUEE_TIPS = 200;
//    private Timer timer;

    @Override
    protected int initViewResId() {
        return R.layout.activity_device_connect;
    }

    @Override
    protected void initView() {
        //setStatusBar(getResources().getColor(R.color.white));
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.device_connect_title_string)).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
        topRight.setImageResource(R.mipmap.device_setting);
        topRight.setVisibility(View.VISIBLE);
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SHOW_TIPS) {
                    LoadDialog.clear();
                    new CommomDialog(DeviceConnectActivity.this, getResources().getString(R.string.no_device_tip), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {

                            dialog.dismiss();
                        }
                    }).show();
                } else if (msg.what == SHOW_NO_BLUEE_TIPS) {
                    new CommomDialog(DeviceConnectActivity.this, getResources().getString(R.string.no_bluee_tip), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {

                            dialog.dismiss();
                        }
                    }).show();
                }
                super.handleMessage(msg);
            }
        };
//        timer = new Timer();
        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!DeviceConnectActivity.this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(DeviceConnectActivity.this, "BLE is not supported", Toast.LENGTH_SHORT).show();
            DeviceConnectActivity.this.finish();
        }

        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) DeviceConnectActivity.this.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(DeviceConnectActivity.this, "Bluetooth not supported.", Toast.LENGTH_SHORT).show();
            DeviceConnectActivity.this.finish();
        }

        // Ensures Bluetooth is enabled on the device.  If Bluetooth is not currently enabled,
        // fire an intent to display a dialog asking the user to grant permission to enable it.
        if (!mBluetoothAdapter.isEnabled()) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
        }

        //获取账号数据
        Intent intent = getIntent();
        code = intent.getIntExtra("Code", 5);
        if (code == 0) {
            DeviceNo = intent.getStringExtra("DeviceNo");
            DeviceNo_LastNight = DeviceNo.substring(DeviceNo.length() - 8, DeviceNo.length());
            ServicePhone = intent.getStringExtra("ServicePhone");
        } else if (code == 3) {
            ServicePackId = intent.getIntExtra("ServicePackId", 0);
        } else if (code == 4) {
            ServicePhone = intent.getStringExtra("ServicePhone");
        }
        patientBean = (HaveBindEntity.DataBean.PatientBean) intent.getSerializableExtra("Patient");

        //申请权限
        getPersimmions();

        boolean user_first_boolean = intent.getBooleanExtra("user_first", false);
        if (!user_first_boolean) {
            Intent to_intent = new Intent(DeviceConnectActivity.this, GuideOperateActivity.class);
            toActivity(to_intent);
        }

        connectingIv.setImageResource(R.drawable.connecting_animation);
        anim = (AnimationDrawable) connectingIv.getDrawable();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("accountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
        paramsMap.put("patientId", SpUtils.getInt(Contants.Id,0)+"");
        mMyOkhttp.get().url(HttpUrl.HaveBind).params(paramsMap).tag(this).headers(HeadUitl.instance.getHeads()).enqueue(new GsonResponseHandler<HaveBindEntity>() {
            @Override
            public void onSuccess(int statusCode, HaveBindEntity response) {//(Code说明:0设备已绑定,(1未登记用户,)2未购买母胎服务包,3母胎服务包未支付,4已购服务包未绑定设备)*/
                patientBean.setPatientId(SpUtils.getInt(Contants.Id,0));
                patientBean.setPatientName(SpUtils.getString(Contants.Name));
                if (response.getCode() == 0) {//服务包，设备，病人信息
                    code = 0;
                    DeviceNo = response.getData().getDeviceNo();
                    DeviceNo_LastNight = DeviceNo.substring(DeviceNo.length() - 8, DeviceNo.length());
                    //patientBean = response.getData().getPatient();
                    ServicePhone = response.getData().getServicePhone();
                } else if (response.getCode() == 2) {//人绑定，服务包没有，设备没有
                    code = 2;
                    //patientBean = response.getData().getPatient();
                } else if (response.getCode() == 3) {//人绑定，设备没有，服务包没有支付
                    code = 3;
                    //patientBean = response.getData().getPatient();
                    ServicePackId = response.getData().getServicePackId();
                } else if (response.getCode() == 4) {//人绑定，设备没有，服务有
                    code = 4;
                    //patientBean = response.getData().getPatient();
                    ServicePhone = response.getData().getServicePhone();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // User chose not to enable Bluetooth.
        if (requestCode == REQUEST_ENABLE_BT && resultCode == Activity.RESULT_CANCELED) {
            DeviceConnectActivity.this.finish();
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(runnable, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    @OnClick({R.id.device_connect_all_ll, R.id.top_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.device_connect_all_ll:

                if (code == 0) {
                    if (mBluetoothAdapter.isEnabled()) {
                        //判断蓝牙是否开启
                        if (anim != null && anim.isRunning()) {
                            anim.stop();
                        }

                        if (mScanning) {
                            mBluetoothAdapter.stopLeScan(mLeScanCallback);
                            mScanning = false;
                            mHandler.removeMessages(0);
                        }

                        anim.start();

                        //搜索设备
                        scanLeDevice(true);
                        LoadDialog.show(DeviceConnectActivity.this);
//                    count = 0;
//                    hasDevice = false;
//                    setTime();

                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (mScanning) {
                                    mHandler.sendEmptyMessage(SHOW_TIPS);
                                }
                            }
                        }, 10 * 1000);
                    } else {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                    mHandler.sendEmptyMessage(SHOW_NO_BLUEE_TIPS);
                            }
                        }, 0);
                    }

                } else if (code == 2 || code == 5) {
                    new CommomDialog(DeviceConnectActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.device_connect_no_service_string), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.cancel();
                            if (confirm) {
                                Intent toSerivceList = new Intent(DeviceConnectActivity.this, ServiceListActivity.class);
                                toSerivceList.putExtra("Patient", patientBean);
                                //从连接界面进入，区别于我的胎心监护
                                toSerivceList.putExtra("go_path", "device_connect");
                                toActivity(toSerivceList);
                            }
                        }
                    }).show();
                } else if (code == 3) {
                    new CommomDialog(DeviceConnectActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.device_connect_no_pay_order_string), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.cancel();
                            if (confirm) {
                               /* Intent intent = new Intent(DeviceConnectActivity.this, ServiceDetailActivity.class);
                                intent.putExtra("ServicePackId", ServicePackId);
                                intent.putExtra("Patient", patientBean);
                                intent.putExtra("go_path", "device_connect");*/

                                Intent intent = new Intent(DeviceConnectActivity.this, WebViewActivity.class);
                                intent.putExtra("id", ServicePackId);
                                intent.putExtra("type", WebUtil.fetalHeart);
                                toActivity(intent);
                            }
                        }
                    }).show();
                } else if (code == 4) {
                    new CommomDialog(DeviceConnectActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.device_connect_no_bind_front_string) + ServicePhone + getResources().getString(R.string.device_connect_no_bind_behind_string), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            dialog.cancel();
                            if (confirm) {
                                Intent intent2 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ServicePhone));
                                startActivity(intent2);
                                DeviceConnectActivity.this.finish();
                            }
                        }
                    }).show();
                }
                break;
            case R.id.top_right:
                Dialog dialog = new Dialog(DeviceConnectActivity.this, R.style.MyDialog);
                dialog.setContentView(R.layout.activity_device_setting);
                RelativeLayout monitor_setting_rl = (RelativeLayout) dialog.findViewById(R.id.monitor_setting_rl);
                monitor_setting_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DeviceConnectActivity.this, MonitorSettingActivity.class);
                        toActivity(intent);
                        dialog.cancel();
                    }
                });
                RelativeLayout guide_rl = (RelativeLayout) dialog.findViewById(R.id.guide_rl);
                guide_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DeviceConnectActivity.this, GuideOperateActivity.class);
                        toActivity(intent);
                        dialog.cancel();
                    }
                });
                dialog.setCanceledOnTouchOutside(true);
                Window window = dialog.getWindow();
                window.setGravity(Gravity.RIGHT | Gravity.TOP);
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.x = 10; // 新位置X坐标
                lp.y = 10; // 新位置Y坐标
                window.setAttributes(lp);
                dialog.show();
                break;
        }
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();

            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (code == 4) {
                if (this.checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    permissions.add(Manifest.permission.CALL_PHONE);
                }
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {

                @Override
                public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (null != device.getName() && !device.getName().equals("") && device.getName().contains(DeviceNo_LastNight)) {
                                LoadDialog.clear();
                                if (anim.isRunning()) {
                                    anim.stop();
                                }
                                if (mScanning) {
                                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                                    mScanning = false;
                                }
                                Intent intent = new Intent(DeviceConnectActivity.this, FetalHeartMonitorActivity.class);
                                intent.putExtra(BluetoothDevice.EXTRA_DEVICE, device);
                                intent.putExtra("PatientId", patientBean.getPatientId());
                                intent.putExtra("DeviceNo", DeviceNo);
                                intent.putExtra("ServicePhone", ServicePhone);
                                intent.putExtra("go_path", "device_connect");
                                startActivity(intent);
                            }
                        }
                    });
                }
            };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
