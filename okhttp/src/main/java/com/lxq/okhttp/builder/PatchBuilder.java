package com.lxq.okhttp.builder;


import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.callback.MyCallback;
import com.lxq.okhttp.response.IResponseHandler;
import com.lxq.okhttp.utils.OkHttpLogUtils;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * patch builder
 * Created by tsy on 16/12/06.
 */
public class PatchBuilder extends OkHttpRequestBuilder<PatchBuilder> {

    public PatchBuilder(MyOkHttp myOkHttp) {
        super(myOkHttp);
    }

    @Override
    public void enqueue(final IResponseHandler responseHandler) {
        try {
            if(mUrl == null || mUrl.length() == 0) {
                throw new IllegalArgumentException("url can not be null !");
            }

            Request.Builder builder = new Request.Builder().url(mUrl);
            appendHeaders(builder, mHeaders);

            if (mTag != null) {
                builder.tag(mTag);
            }

            builder.patch(RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), ""));
            Request request = builder.build();

            mMyOkHttp.getOkHttpClient()
                    .newCall(request)
                    .enqueue(new MyCallback(responseHandler));
        } catch (Exception e) {
            OkHttpLogUtils.e("Patch enqueue error:" + e.getMessage());
            responseHandler.onFailure(0, e.getMessage());
        }
    }
}
