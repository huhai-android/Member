package com.newdjk.member.views;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;


import com.newdjk.member.R;

import com.newdjk.member.utils.LogUtils;



public class SelectedPictureDialog implements View.OnClickListener {
    TextView photograph;
    TextView album;
    private Activity mContext;
    private  Dialog mDialog;



    public SelectedPictureDialog(Context context) {

        mContext = (Activity)context;
    }



    //显示dialog的方法
    public  void show() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.dialog_select_picture, null);
            photograph = view.findViewById(R.id.photograph);
            album = view.findViewById(R.id.album);
            mDialog.setContentView(view);//dialog布局文件
            /*mDialog.setCancelable(true);
            mDialog.setCanceledOnTouchOutside(false);//点击外部不允许关闭dialog*/
            mDialog.show();
            photograph.setOnClickListener(this);
            album.setOnClickListener(this);
        } catch (Exception e) {
            LogUtils.e("LoadDialog  error!!!");
        }
    }
    private void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.photograph:
                if (mOnPictrueClickListener != null) {
                    mOnPictrueClickListener.onCamera();
                }
                close();

                break;
            case R.id.album:
                if (mOnPictrueClickListener != null) {
                    mOnPictrueClickListener.onALbum();
                }
                close();
                break;
        }
    }


    public interface OnPictrueClickListener{
        void onCamera();
        void onALbum();
    }

    private OnPictrueClickListener mOnPictrueClickListener;

    public void setmOnPictrueClickListener(OnPictrueClickListener mOnPictrueClickListener) {
        this.mOnPictrueClickListener = mOnPictrueClickListener;
    }
}
