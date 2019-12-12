package com.newdjk.member.utils;

import android.app.Activity;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.BuildConfig;
import com.newdjk.member.MyApplication;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.update.AppUpdateInfo;

/**
 * 检查版本更新工具类
 */
public class HomeUtil {

    public void checkUpdateInfo(Activity context,final UpdateListener listener) {
        String AppCode = BuildConfig.APP_CODE;
        String PlatformType = "0";
        String AppVersion = SystemUitl.packageCode(context)[0];
        String url = new StringBuffer(HttpUrl.GetAppManage)
                .append("?AppCode=")
                .append(AppCode)
                .append("&PlatformType=")
                .append(PlatformType)
                .append("&AppVersion=")
                .append(AppVersion).toString();

        MyApplication.getInstance().getMyOkHttp().get().headers(HeadUitl.instance.getHeads()).url(url).tag(context).enqueue(new GsonResponseHandler<AppUpdateInfo>() {
            @Override
            public void onSuccess(int statusCode, AppUpdateInfo response) {
                listener.success(response);
//                if (response.getCode() == 0) {
//                    if (response.getData() != null) {
//                        final String apkUrl = response.getData().getAppPath();
//                        int appVersion = response.getData().getAppVersion();
//                        if (appVersion > Integer.parseInt(AppVersion)) {
//                            GroupButtonDialog dialog = new GroupButtonDialog(context);
//                            dialog.show(context.getResources().getString(R.string.versionUpdate), response.getData().getRemark() == null ? context.getResources().getString(R.string.findNewVersion) : response.getData().getRemark(), new GroupButtonDialog.DialogListener() {
//                                @Override
//                                public void cancel() {
//                                    if (response.getData().getMustUpdate() == 1) {
//                                        context.finish();
//                                    }
//                                }
//
//                                @Override
//                                public void confirm() {
//                                    if (apkUrl != null) {
//                                        UpdateManage updateManage = new UpdateManage(context, apkUrl);
//                                        updateManage.showDownloadDialog();
//                                    } else {
//                                        ToastUtil.setToast(context.getString(R.string.urlError));
//                                    }
//
//                                }
//                            });
//                        }
//                    }
//                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                listener.failed(statusCode,errorMsg);
            }
        });
    }
    public interface UpdateListener {
        void success(AppUpdateInfo entity);

        void failed(int statusCode, String errorMsg);
    }
}
