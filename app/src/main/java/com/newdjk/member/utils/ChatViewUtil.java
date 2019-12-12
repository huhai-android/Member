package com.newdjk.member.utils;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.ajguan.library.EasyRefreshLayout;

/**
 * 聊天页面工具类整合
 */
public enum ChatViewUtil {
    instance;
    public final int TYPE_PLAYING = 1;
    public final int TYPE_OVER = 2;

    /**
     * 根据问诊状态动态设置布局
     *
     * @param llOverBottomMenu
     * @param chatRecyclerView
     * @param type
     */
    public void setChatRvSize(final LinearLayout llOverBottomMenu, final EasyRefreshLayout chatRecyclerView, int type,int doctortype) {
        LogUtils.d("ChatViewUtil","医生类型"+doctortype+"");
        if (TYPE_PLAYING == type) {
            llOverBottomMenu.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = ( FrameLayout.LayoutParams) chatRecyclerView.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            chatRecyclerView.setLayoutParams(params);
        } else if (TYPE_OVER == type) {
            llOverBottomMenu.setVisibility(View.VISIBLE);
            if (doctortype==2){

            }else {

            }
            llOverBottomMenu.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    int height = llOverBottomMenu.getHeight();
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) chatRecyclerView.getLayoutParams();
                    params.setMargins(0, 0, 0, height + 20);
                    chatRecyclerView.setLayoutParams(params);
                }
            });

        }

    }
}
