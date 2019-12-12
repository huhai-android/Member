package com.newdjk.member.utils;

/**
 * 用来监听网络变化接口工具
 */
public interface NetworkListener {
    /**
     * 回调当前网络状态变化
     *
     * @param status 当前网络类型
     */
    void netWorkStatus(int status);
}
