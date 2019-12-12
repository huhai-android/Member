package com.example.wxsharelibrary;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

public class ShareBottomDialog implements View.OnClickListener, IWXAPIEventHandler {
    private Dialog mDialog;
    private View mInflate;
    private LinearLayout mLlWxFriends, mLlWxZoom;
    private TextView mTvCancel;
    private WxShareCallBack mCallBack;
    private IWXAPI iwxapi;
    private String mSdPath;
    private Context mContext;

    enum SHARE_TYPE {Type_WXSceneSession, Type_WXSceneTimeline}

    public void setCallBack(WxShareCallBack callBack) {
        this.mCallBack = callBack;
    }

    /**
     * @param view    父控件
     * @param intent  当前上下文的Intent getIntent()
     * @param context 上下文
     * @Param path 要分享的本地图片路径
     */
    public void showBottomDialog(View view, String path, Intent intent, Context context) {
        this.mContext = context;
        this.mSdPath = path;
        iwxapi = WXAPIFactory.createWXAPI(context, Constant.APP_ID, false);
        iwxapi.handleIntent(intent, this);
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        mInflate = LayoutInflater.from(context).inflate(R.layout.dialog_share, null);
        //初始化控件
        mLlWxFriends = mInflate.findViewById(R.id.ll_wx_friend);
        mLlWxZoom = mInflate.findViewById(R.id.ll_wx_zoom);
        mTvCancel = mInflate.findViewById(R.id.tv_cancel);
        mLlWxFriends.setOnClickListener(this);
        mLlWxZoom.setOnClickListener(this);
        mTvCancel.setOnClickListener(this);
        //将布局设置给Dialog
        mDialog.setContentView(mInflate);
        //获取当前Activity所在的窗体
        Window dialogWindow = mDialog.getWindow();
        //设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        //获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 10;//设置Dialog距离底部的距离
//       将属性设置给窗体
        lp.width = LinearLayout.LayoutParams.MATCH_PARENT;
        dialogWindow.setAttributes(lp);
        mDialog.show();//显示对话框
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_wx_friend) {
            share(SHARE_TYPE.Type_WXSceneSession);
        } else if (v.getId() == R.id.ll_wx_zoom) {
            share(SHARE_TYPE.Type_WXSceneTimeline);
        } else if (v.getId() == R.id.tv_cancel) {
            if (mDialog != null && mDialog.isShowing()) {
                mCallBack.bottomCancel();
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        String result;
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                result = "分享成功";
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                result = "取消分享";
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                result = "分享被拒绝";
                break;
            default:
                result = "发送返回";
                break;
        }

        mCallBack.shareResult(result);
    }

    private void share(SHARE_TYPE type) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(mSdPath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (fis == null) {
            return;
        }
        Bitmap thumb = BitmapFactory.decodeStream(fis);
        WXImageObject imageObject = new WXImageObject(thumb);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imageObject;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        switch (type) {
            case Type_WXSceneSession:
                req.scene = SendMessageToWX.Req.WXSceneSession;
                break;
            case Type_WXSceneTimeline:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                break;
        }

        iwxapi.sendReq(req);

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

//    private void getImagePath(String imgUrl) {
//        FutureTarget<File> future = Glide.with(mContext)
//                .load(imgUrl)
//                .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
//        try {
//            File cacheFile = future.get();
//            mSdPath = cacheFile.getAbsolutePath();
//        } catch (InterruptedException | ExecutionException e) {
//            e.printStackTrace();
//        }
//    }

}