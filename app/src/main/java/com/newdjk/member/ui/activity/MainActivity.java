package com.newdjk.member.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.google.gson.Gson;
import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.common.handler.ConnectHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.service.MyService;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.login.LoginActivity;
import com.newdjk.member.ui.entity.IMMessageEntity;
import com.newdjk.member.ui.entity.PushDataDaoEntity;
import com.newdjk.member.ui.fragment.FunctionFragment;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.CheckPatientStatusUtil;
import com.newdjk.member.utils.FragmentController;
import com.newdjk.member.utils.FragmentFactory;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.LogOutUtil;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.MyMapUtil;
import com.newdjk.member.utils.SQLiteUtils;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.BottomTabRadioButton;
import com.newdjk.member.views.LoadDialog;
import com.tencent.TIMCallBack;
import com.tencent.TIMConversation;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushToken;
import com.tencent.TIMUserStatusListener;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 主界面
 */
public class MainActivity extends BasicActivity implements FunctionFragment.BackListener {


    private static final String TAG = "MainActivity";
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.rb_tab1)
    BottomTabRadioButton rbTab1;
    @BindView(R.id.rb_tab2)
    BottomTabRadioButton rbTab2;
    @BindView(R.id.rb_tab3)
    BottomTabRadioButton rbTab3;
    @BindView(R.id.rb_tab4)
    BottomTabRadioButton rbTab4;
    private List<Fragment> listFragment = new ArrayList();
    private FragmentController fgtController = null;
    private MyMapUtil mMapUtil;
    private AMapLocation mMapLocation;
    private Gson mGson = new Gson();
    private int mId;
    private FragmentFactory fragmentFactory;
    private PagerAdapter mAdapter;
    private String mCurrentFlag = "";
    private int accout = 1;

    public static Intent getAct(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected int initViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        super.onCreate(savedInstanceState);
//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
//        }

        HMSAgent.connect(this, new ConnectHandler() {
            @Override
            public void onConnect(int rst) {
                Log.d(TAG,"HMS connect end:" + rst);
                getToken();
            }
        });
    }


    /**
     * 获取token
     */

    private void getToken() {
        Log.d(TAG,"get token: begin");
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rst) {
                Log.d(TAG,"get token: end" + rst);
            }


        });
    }



    @Override
    protected void changeStatusBarTextColor(boolean isBlack) {
        super.changeStatusBarTextColor(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        mId = SpUtils.getInt(Contants.AccountId, 0);
        rbTab1.setDrawablesTop(R.drawable.home_selectot);
        rbTab2.setDrawablesTop(R.drawable.monitor_selectot);
        rbTab3.setDrawablesTop(R.drawable.message_selectot);
        rbTab4.setDrawablesTop(R.drawable.personal_selectot);

        fragmentFactory = new FragmentFactory();
        viewPager.setOffscreenPageLimit(4);
        mAdapter = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                pageSelected(position);
            }
        });

    }

    @Override
    protected void initListener() {
        rbTab1.setOnClickListener(this);
        rbTab2.setOnClickListener(this);
        rbTab3.setOnClickListener(this);
        rbTab4.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        pageSelected(0);
        CheckPatientStatusUtil.getDefaultPatientStatus(this);
        CheckPatientStatusUtil.getDefaultPatient(this);
        getImMessage();
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.rb_tab1:
                pageSelected(0);
                break;
            case R.id.rb_tab2:
                pageSelected(1);
                break;
            case R.id.rb_tab3:
                pageSelected(2);
                break;
            case R.id.rb_tab4:
                pageSelected(3);
                break;
        }
    }

    public void toHomeFragment() {
        pageSelected(0);
    }

    private long exitTime = 0;

    public void exit() {
        if (!TextUtils.isEmpty(mCurrentFlag)) {
            if (mCurrentFlag.equals("fragment2")) {
                ((FunctionFragment) fragmentFactory.createFragment(1)).goBack(this);
                return;
            }

        }
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            //  Fragment fragment = fgtController.getFragment();
            Toast.makeText(getApplicationContext(), R.string.exitApp, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            killAll();
            finish();
        }
    }

    @Override
    public void goBck() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            //  Fragment fragment = fgtController.getFragment();
            Toast.makeText(getApplicationContext(), R.string.exitApp, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            killAll();
            finish();
        }
    }

    public void exitNoTip() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            killAll();
            System.exit(0);
        }
    }

    @Override
    public void onBackPressed() {
        exit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getImMessage() {
        int id = SpUtils.getInt(Contants.Id, 0);
        Map<String, String> map = new HashMap<>();
        map.put("UserId", id + "");
        map.put("UserType", "1");
        mMyOkhttp.post().url(HttpUrl.loginIm).headers(HeadUitl.instance.getHeads()).params(map).tag(this).enqueue(new GsonResponseHandler<IMMessageEntity>() {
            @Override
            public void onSuccess(int statusCode, IMMessageEntity entity) {
                if (entity.getCode() == 0) {
                    IMMessageEntity.DataBean data = entity.getData();
                    if (data != null) {
                        Log.i("zdp", "statusCode = " + statusCode);
                        Log.i("zdp", "usersig=" + data.getUserSig());
                        SpUtils.put(Contants.Identifier, data.getIdentifier());
                        SpUtils.put(Contants.FaceUrl, data.getFaceUrl());
                        loginSDK(data.getIdentifier(), data.getUserSig());
                    } else {
                        if (accout>2) {
                            Toast.makeText(MainActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                            LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                        } else {
                            accout++;
                            getImMessage();
                        }
                    }
                } else {
                    LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                    Toast.makeText(MainActivity.this, entity.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("zdp", "statusCode=" + statusCode + ",errorMsg=" + errorMsg);
                LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void loginSDK(final String id, String userSig) {
        ILiveLoginManager.getInstance().iLiveLogin(id, userSig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {

                //登录成功后，上报证书 ID 及设备 token
                TIMOfflinePushToken param = new TIMOfflinePushToken();
                param.setToken(MyApplication.mRegId);
                param.setBussid(MyApplication.busid);
                TIMManager.getInstance().setOfflinePushToken(param, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.i("MainActivity","errorCode="+i+",s="+s);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("MainActivity","success");
                    }
                });


                //设置用户状态变更监听器，在回调中进行相应的处理
                TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //  EventBus.getDefault().post(new NoticeLoginEntity(true));
                        Toast.makeText(MainActivity.this, "当前账号在其它设备登录，请重新登录", Toast.LENGTH_SHORT).show();
                        MessageEvent event = new MessageEvent();
                        event.setmType(MainConstant.LOGOUTLOGIN);
                        EventBus.getDefault().post(event);
                    }

                    @Override
                    public void onUserSigExpired() {
                        MessageEvent event = new MessageEvent();
                        event.setmType(MainConstant.LOGOUTLOGIN);
                        EventBus.getDefault().post(event);
                        Toast.makeText(MainActivity.this, "当前账号凭证已过期，请 重新登录", Toast.LENGTH_SHORT).show();
                    }
                });
                SpUtils.put(Contants.ImId, id);
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setmType(MainConstant.UpdateConversation);
                EventBus.getDefault().post(messageEvent);

                startService(new Intent(MainActivity.this, MyService.class));
                updateBottomMessageNum();//更新下未读消息的数量
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if (errCode == 6208) {
                    loginSDK(id, userSig);
                } else {
                    LogOutUtil.getInstance().loginOut(MainActivity.this, false);
                    Toast.makeText(mContext, "Login failed:" + module + "|" + errCode + "|" + errMsg, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    protected void clearAll() {
        loading(true);
        List<BasicActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BasicActivity>(mActivities);
        }
        for (BasicActivity activity : copy) {
            activity.finish();
        }
        LoadDialog.clear();
        SQLiteUtils.getInstance().deleteAllImData();
        String userName = SpUtils.getString(Contants.Mobile);
        String password = SpUtils.getString(Contants.inputPassword);
        Intent loginOutIntent = new Intent(MainActivity.this, LoginActivity.class);
        loginOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        SpUtils.clear();
        SpUtils.put(Contants.IS_FIRST_USE, false);
        SpUtils.put(Contants.Mobile, userName);
        SpUtils.put(Contants.inputPassword, password);
        startActivity(loginOutIntent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.LOGOUTLOGIN:
                clearAll();
                break;
            case MainConstant.UPDATEPUSHMESSAGELIST:
                updateBottomMessageNum();
                break;
        }
    }

    /**
     * 更新底部未读消息的数量
     */
    private void updateBottomMessageNum() {
        int num = 0;
        List<TIMConversation> conversionList = TIMManager.getInstance().getConversionList();
        for (TIMConversation timConversation : conversionList) {
            if (!TextUtils.isEmpty(timConversation.getPeer())) {
                long unreadMessageNum = timConversation.getUnreadMessageNum();
                if (unreadMessageNum > 0) {
                    num += 1;
                }
            }
        }
        num += getUnreadUnm();
        if (num > 0) {
            rbTab3.setHint_mode(2);
            rbTab3.setNum(num);
        } else {
            rbTab3.setHint_mode(0);
        }

    }

    /**
     * 获取数据库中的系统消息的数据
     *
     * @return
     */
    private int getUnreadUnm() {
        List<PushDataDaoEntity> pushList = SQLiteUtils.getInstance().selectAllContactsByDoctorId(mId + "");
        int orderUnreadNum = 0; //订单提醒的数量
        int serviceUnreadNum = 0;//服务提醒的数量
        int prescriptionUnreadNum = 0;//处方提醒
        int followUnreadNum = 0;//随访提醒
        int sysUnreadNum = 0; //系统消息
        for (int i = 0; i < pushList.size(); i++) {
            PushDataDaoEntity pushDataDaoEntity = pushList.get(i);
            boolean isread = pushDataDaoEntity.isRead;
            if (!isread) {
                int msgType = pushDataDaoEntity.getMsgType();
                switch (msgType) {
                    //1：订单提醒 2：服务提醒 3：处方提醒 4 随访提醒 5 系统消息
                    case 1:
                        orderUnreadNum += 1;
                        break;
                    case 2:
                        serviceUnreadNum += 1;
                        break;
                    case 3:
                        prescriptionUnreadNum += 1;
                        break;
                    case 4:
                        followUnreadNum += 1;
                        break;
                    case 5:
                        sysUnreadNum += 1;
                        break;
                }
            }
        }
        return orderUnreadNum + serviceUnreadNum + prescriptionUnreadNum + followUnreadNum + sysUnreadNum;
    }


    private class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentFactory.createFragment(position);
        }

        @Override
        public int getCount() {
            return 4;
        }

    }

    private void pageSelected(int position) {
        viewPager.setCurrentItem(position, false);
        updateTabContainer(position);

    }

    //更新底部几个按钮状态
    private void updateTabContainer(int position) {
        switch (position) {
            case 0:
                mCurrentFlag = "";
                rbTab1.setChecked(true);
                rbTab2.setChecked(false);
                rbTab3.setChecked(false);
                rbTab4.setChecked(false);
                StatusBarUtil.setColor(this, getResources().getColor(R.color.theme), 0);

                break;
            case 1:
                mCurrentFlag = "fragment2";
                rbTab1.setChecked(false);
                rbTab2.setChecked(true);
                rbTab3.setChecked(false);
                rbTab4.setChecked(false);
                StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
                break;
            case 2:
                mCurrentFlag = "";
                rbTab1.setChecked(false);
                rbTab2.setChecked(false);
                rbTab3.setChecked(true);
                rbTab4.setChecked(false);
                StatusBarUtil.setColor(this, getResources().getColor(R.color.theme), 0);

                break;
            case 3:
                mCurrentFlag = "";
                rbTab1.setChecked(false);
                rbTab2.setChecked(false);
                rbTab3.setChecked(false);
                rbTab4.setChecked(true);
                StatusBarUtil.setColor(this, getResources().getColor(R.color.theme), 0);

                break;
            case 4:

                rbTab1.setChecked(false);
                rbTab2.setChecked(false);
                rbTab3.setChecked(false);
                rbTab4.setChecked(false);

                break;
        }
    }
}



