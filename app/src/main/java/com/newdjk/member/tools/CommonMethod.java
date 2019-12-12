package com.newdjk.member.tools;

import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.utils.LogOutUtil;
import com.newdjk.member.utils.LogUtils;
import com.newdjk.member.utils.ToastUtil;
import com.newdjk.member.views.LoadDialog;

public class CommonMethod<T> {

    /**
     * 请求失败统一处理
     *
     * @param statusCode
     * @param errorMsg
     */
    public static void requestError(int statusCode, String errorMsg) {
        LogUtils.e("statusCode:" + statusCode + "    errorMsg:" + errorMsg);
        ToastUtil.setToast(errorMsg);
        if (statusCode == 401) {
            LogOutUtil.getInstance().logout();
                ToastUtil.setToast(MyApplication.getContext().getResources().getString(R.string.tokenFailed));
            return;
        }

        ToastUtil.setToast(errorMsg);
    }
}
