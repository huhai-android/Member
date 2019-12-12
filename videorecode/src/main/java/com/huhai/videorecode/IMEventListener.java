package com.huhai.videorecode;


import android.util.Log;

/**
 * IM事件监听
 */

public abstract class IMEventListener {
    private final static String TAG = IMEventListener.class.getSimpleName();

    /**
     * 被踢下线时回调
     */
    public void onForceOffline() {
        Log.d(TAG, "recv onForceOffline");
    }

    /**
     * 用户票据过期
     */
    public void onUserSigExpired() {
        Log.d(TAG, "recv onUserSigExpired");
    }

    /**
     * 连接建立
     */
    public void onConnected() {
        Log.d(TAG, "recv onConnected");
    }

    /**
     * 连接断开
     *
     * @param code 错误码
     * @param desc 错误描述
     */
    public void onDisconnected(int code, String desc) {
       Log.d(TAG, "recv onDisconnected, code " + code + "|desc " + desc);
    }

    /**
     * WIFI需要验证
     *
     * @param name wifi名称
     */
    public void onWifiNeedAuth(String name) {
       Log.d(TAG, "recv onWifiNeedAuth, wifi name " + name);
    }


}
