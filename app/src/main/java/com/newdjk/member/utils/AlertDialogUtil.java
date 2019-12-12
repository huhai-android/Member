package com.newdjk.member.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.newdjk.member.R;


/**
 * @version V1.0
 * @功能描述: Dialog对话框总工具类
 */
public final class AlertDialogUtil {
    /**
     * 单例模式
     */
    private static AlertDialogUtil AlertDialogUtil;

    /**
     * 显示正在加载的对话框
     */
    private Dialog LoadDialog;

    private AlertDialogUtil() {
    }

    public static AlertDialogUtil getInstences() {
        if (AlertDialogUtil == null) {
            AlertDialogUtil = new AlertDialogUtil();
        }
        return AlertDialogUtil;
    }


}
