package com.example.wxsharelibrary;

public interface WxShareCallBack {
    /**
     * 分享回调
     * 分享成功 取消分享 分享被拒绝 发送返回
     */
    void shareResult(String result);

    /**
     * 底部按钮取消回调
     */
    void bottomCancel();
}
