package com.huhai.videorecode;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by gaosheng on 2016/12/1.
 * 23:34
 * com.example.gaosheng.myapplication.utils
 */

public class ToastUtil {
    public static Toast toast;

    public static void setToast(Context context,String str) {
        try {
            if (str.contains("token失效")) {
                return;
            }
            if (toast == null) {
                toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
            } else {
                toast.setText(str);
            }
            toast.show();
        }catch (Exception e){

        }

    }
}

