package com.newdjk.member.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

public class ScreenUtil {

    public static Bitmap getScreen(Activity context) {
        View dView = context.getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        return bitmap;
    }
}
