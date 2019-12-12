package com.newdjk.member.utils;

import android.content.Context;

public class MyOkHttpContext {
    private static Context mContext;

    public static void setContext(Context context) {
        mContext = context;
    }

    public static Context getContext() {
        return mContext;
    }
}
