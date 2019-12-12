package com.newdjk.member.utils;

import android.content.Intent;
import android.util.Log;

import com.newdjk.member.MyApplication;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.service.MyService;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.login.LoginActivity;
import com.newdjk.member.views.LoadDialog;
import com.tencent.TIMCallBack;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.core.ILiveLoginManager;


public class LogOutUtil implements TIMCallBack {
    private BasicActivity mActivity;

    @Override
    public void onError(int i, String s) {
        clearAllAndJump();
    }

    @Override
    public void onSuccess() {
        clearAllAndJump();
    }

    private static class SingletonHolder {
        private static LogOutUtil instance = new LogOutUtil();
    }

    private LogOutUtil() {
    }

    public static LogOutUtil getInstance() {
        return SingletonHolder.instance;
    }

    public void loginOut(BasicActivity activity,boolean isLogin) {
        mActivity = activity;
        if (mActivity != null) {
            activity.loading(true);
            if (isLogin){
                iLiveLogout();
                Intent intent = new Intent(mActivity, MyService.class);
                mActivity.stopService(intent);
            }else {
                clearAllAndJump();
            }

        } else {
            LogUtils.e("no activity result");
        }

    }


    protected void clearAllAndJump() {
        if (mActivity != null) {
            LoadDialog.clear();
            String userName = SpUtils.getString(Contants.Mobile);
            String password = SpUtils.getString(Contants.inputPassword);
            Intent loginOutIntent = new Intent(mActivity, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            SpUtils.clear();
            SpUtils.put(Contants.IS_FIRST_USE, false);
            SpUtils.put(Contants.Mobile, userName);
            SpUtils.put(Contants.inputPassword, password);
            mActivity.startActivity(loginOutIntent);
        }

    }
    public void iLiveLogout() {
        //TODO 新方式登出ILiveSDK
        ILiveLoginManager.getInstance().iLiveLogout(new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {
                Log.i("LogOutUtil","iLiveLogoutdata="+data.toString());
                clearAllAndJump();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                clearAllAndJump();
                Log.i("LogOutUtil","iLiveLogoutonError="+errCode+",errMsg="+errMsg);
            }
        });
    }
    public void logout() {
        String userName = SpUtils.getString(Contants.Mobile);
        String areaId = SpUtils.getString(Contants.AreaId);
        String origId = SpUtils.getString(Contants.OrgId);
        String appInfoJson = SpUtils.getString(Contants.AppInfo);
        String token = SpUtils.getString(Contants.Token);
        String pass = SpUtils.getString(Contants.inputPassword);
        Intent loginOutIntent = new Intent(MyApplication.getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        SpUtils.clear();
        SpUtils.put(Contants.IS_FIRST_USE, false);
        SpUtils.put(Contants.Mobile, userName);
        SpUtils.put(Contants.AreaId, areaId);
        SpUtils.put(Contants.OrgId, origId);
        SpUtils.put(Contants.AppInfo, appInfoJson);
        SpUtils.put(Contants.Token, token);
        SpUtils.put(Contants.inputPassword,pass);
        MyApplication.getContext().startActivity(loginOutIntent);
    }


}
