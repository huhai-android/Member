package com.newdjk.member.utils;

import android.content.Context;

/**
 * Created by EDZ on 2018/9/18.
 */

public class PhotoUtils {

    // 直接读取系统里状态栏高度的值，但是无法判断状态栏是否显示
    public static int getStatusBarHeight(Context context){
        int height = 0;
        //获取status_bar_height资源的ID
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

}
