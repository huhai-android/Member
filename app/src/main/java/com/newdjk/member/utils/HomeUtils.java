package com.newdjk.member.utils;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.ui.entity.AppEntity;

public enum HomeUtils {
    INSTANCE;

    public void checkVersion(final UpdateListener listener) {
        String url = HttpUrl.ip + "/PlatFormAPI/KnowledgeBase/GetAppManage?AppCode=newdjkpatapp";
        MyApplication.getInstance().getMyOkHttp().get().url(url).tag(this).enqueue(new GsonResponseHandler<AppEntity>() {
            @Override
            public void onSuccess(int statusCode, AppEntity response) {

                listener.success(response);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                listener.failed(statusCode, errorMsg);
            }
        });
    }

    public interface UpdateListener {
        void success(AppEntity entity);

        void failed(int statusCode, String errorMsg);
    }
}
