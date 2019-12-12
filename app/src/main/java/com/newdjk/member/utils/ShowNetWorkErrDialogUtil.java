package com.newdjk.member.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;


public enum ShowNetWorkErrDialogUtil {
    instance;
    public static final int REQUEST_SETTING_CODE = 20;

    public void showDialog(GroupButtonDialog dialog, Context context, GroupButtonDialog.DialogListener listener) {

        dialog = new GroupButtonDialog(context);

        dialog.show("温馨提示", "这个世界上最遥远的距离是没有网络，请检测是否打开网络", new GroupButtonDialog.DialogListener() {
            @Override
            public void cancel() {
                listener.cancel();
            }

            @Override
            public void confirm() {
                listener.confirm();
            }
        });
    }

    public void goSettings(Activity context) {
        context.startActivityForResult(new Intent(Settings.ACTION_SETTINGS), REQUEST_SETTING_CODE);
    }

}
