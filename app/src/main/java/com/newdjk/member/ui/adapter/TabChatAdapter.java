package com.newdjk.member.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.AppLicationEntity;

import java.util.List;

public class TabChatAdapter extends BaseQuickAdapter<AppLicationEntity, BaseViewHolder> {
    private String TAG = "MyDiagnoseImageAdapter";
    private Gson mGson;
    private List<AppLicationEntity> datalist;
    private int type=0;


    public TabChatAdapter(@Nullable List<AppLicationEntity> data, int type) {
        super(R.layout.tab_chat_item, data);

        datalist = data;
        mGson = new Gson();
        this.type=type;
    }
    public TabChatAdapter(@Nullable List<AppLicationEntity> data) {
        super(R.layout.tab_chat_item, data);

        datalist = data;
        mGson = new Gson();
    }
    @Override
    protected void convert(BaseViewHolder helper, final AppLicationEntity item) {

        helper.setText(R.id.item_chat_title, item.getAppDesc() + "");

        ImageView avatar = helper.getView(R.id.item_chat_img);
        Glide.with(MyApplication.getContext())
                .load(item.getImageID())

                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(avatar);
//        helper.addOnClickListener(R.id.item_chat_img);
//        helper.addOnClickListener(R.id.function);
    }
}
