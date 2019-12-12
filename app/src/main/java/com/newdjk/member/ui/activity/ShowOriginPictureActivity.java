package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

public class ShowOriginPictureActivity extends BasicActivity {


    @BindView(R.id.origin_picture)
    PhotoView originPicture;
    private String mPath;

    @Override
    protected int initViewResId() {
        return R.layout.show_picture;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        originPicture.setOnClickListener(this);
        Intent intent = getIntent();
        mPath = intent.getStringExtra("path");
        Log.i("zdp", "path=" + mPath);
        Glide.with(MyApplication.getContext())
                .load(mPath)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(originPicture);
    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.origin_picture:
                finish();
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
