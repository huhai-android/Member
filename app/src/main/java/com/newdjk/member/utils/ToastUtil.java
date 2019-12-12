package com.newdjk.member.utils;

import android.widget.Toast;

import com.newdjk.member.MyApplication;
import com.newdjk.member.ui.entity.CityEntity;
import com.newdjk.member.ui.entity.Entity;


/**
 * Created by gaosheng on 2016/12/1.
 * 23:34
 * com.example.gaosheng.myapplication.utils
 */

public class ToastUtil {
    public static Toast toast;

    public static void setToast(String str) {

        if (toast == null) {
            toast = Toast.makeText(MyApplication.getContext(), str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

}
