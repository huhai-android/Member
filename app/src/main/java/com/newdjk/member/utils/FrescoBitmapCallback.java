package com.newdjk.member.utils;

import android.net.Uri;

/**
 * Created by EDZ on 2019/3/20.
 */

public interface FrescoBitmapCallback<T> {
    void onSuccess(Uri uri, T result);

    void onFailure(Uri uri, Throwable throwable);

    void onCancel(Uri uri);
}
