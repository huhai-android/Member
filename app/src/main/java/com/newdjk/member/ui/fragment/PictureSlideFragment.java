package com.newdjk.member.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.youth.banner.loader.ImageLoader;

import uk.co.senab.photoview.PhotoView;

public class PictureSlideFragment extends Fragment {
    private String url;
    private PhotoView imageView;
    private ImageLoader loader;     // 获取URL

    public static PictureSlideFragment newInstance(String url) {
        PictureSlideFragment f = new PictureSlideFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        url = getArguments().getString("url");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {        /*加载视图*/
        View v = inflater.inflate(R.layout.fragment_picture_slide, container, false);
        imageView = (PhotoView) v.findViewById(R.id.photo_view_pic);         /*加载图片*/
        Glide.with(MyApplication.getContext())
                .load(url)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
        /*loader = ImageLoader.getInstance();
        loader.displayImage(url, imageView);*/
        return v;
    }

}
