package com.newdjk.member.uploadimagelib;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.newdjk.member.R;

import java.util.List;


/**
 * 添加上传图片适配器
 * <p>
 * 作者： 周旭 on 2017/6/21/0021.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 */

public class GridViewAdapter extends android.widget.BaseAdapter {
    
    private Context mContext;
    private List<String> mList;
    private LayoutInflater inflater;
    private OnItemDelOnClickListener onItemDelOnClickListener;

    public GridViewAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        //return mList.size() + 1;//因为最后多了一个添加图片的ImageView 
        int count = mList == null ? 1 : mList.size() + 1;
        if (count > MainConstant.MAX_SELECT_PIC_NUM) {
            return mList.size();
        } else {
            return count;
        }
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.grid_item, parent,false);
        ImageView iv = (ImageView) convertView.findViewById(R.id.pic_iv);
        ImageView mDel = (ImageView) convertView.findViewById(R.id.mDel);
        if (position < mList.size()) {
            //代表+号之前的需要正常显示图片
            String picUrl = mList.get(position); //图片路径
            Glide.with(mContext).load(picUrl).into(iv);
            mDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemDelOnClickListener != null) {
                        onItemDelOnClickListener.onItemDelClickListener(position);
                    }
                }
            });
        } else {
            iv.setImageResource(R.mipmap.image_add);//最后一个显示加号图片
            mDel.setVisibility(View.GONE);
        }
        return convertView;
    }


    public interface OnItemDelOnClickListener{
        void onItemDelClickListener(int pos);
    }

    public void setOnItemDelOnClickListener(OnItemDelOnClickListener onItemDelOnClickListener){
        this.onItemDelOnClickListener = onItemDelOnClickListener;
    }



}  
