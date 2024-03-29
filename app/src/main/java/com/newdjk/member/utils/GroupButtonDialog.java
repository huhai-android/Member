package com.newdjk.member.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.newdjk.member.R;


public class GroupButtonDialog {
    private Context mContext;
    private Dialog mDialog;

    public GroupButtonDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void show(String title, String content, final DialogListener listener) {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
            }
            mDialog = new Dialog(mContext, R.style.ActionSheetDialogStyle);//dialog样式
            View view = View.inflate(mContext, R.layout.layout_dialog, null);
            mDialog.setContentView(view);
            TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
            TextView tvContent = (TextView) view.findViewById(R.id.tv_content);
            TextView tvCancel = view.findViewById(R.id.tv_cancel);
            TextView tvConfirm = (TextView) view.findViewById(R.id.tv_confirm);
            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            }

            if (!TextUtils.isEmpty(content)) {
                tvContent.setText(content);
            }
            tvCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.cancel();
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });

            tvConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.confirm();
                    if (mDialog != null) {
                        mDialog.dismiss();
                    }
                }
            });
            Window dialogWindow = mDialog.getWindow();
            WindowManager.LayoutParams lp = dialogWindow.getAttributes();
            DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
            lp.width = (int) (d.widthPixels * 0.7); // 高度设置为屏幕的0.6
            dialogWindow.setAttributes(lp);
            mDialog.show();
        } catch (Exception e) {
            Log.e("error", "LoadDialog  error!!!");
        }
    }

    private void close() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    public interface DialogListener {
        void cancel();

        void confirm();
    }
}
