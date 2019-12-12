package com.newdjk.member.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.newdjk.member.R;
import com.newdjk.member.ui.entity.CityEntity;

import java.util.List;

public class ProvenceAdapter extends BaseAdapter {
    private List<CityEntity.DataBean> mList;
    private Context mContext;
    private int mPosition;
    private boolean mLevel;

    public ProvenceAdapter(List<CityEntity.DataBean> mList, Context mContext,boolean mLevel) {
        this.mContext = mContext;
        this.mList = mList;
        this.mPosition = 0;
        this.mLevel = mLevel;
    }

    public void selectItem(int position) {
        this.mPosition = position;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_provence, null);
            holder.tv = convertView.findViewById(R.id.tv_provence);
            holder.line = convertView.findViewById(R.id.adapter_line);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CityEntity.DataBean bean = mList.get(position);
        holder.tv.setText(bean.getAreaName());
        if (mLevel){
            if (this.mPosition == position) {
               setBackWhite(holder);
            } else {
               setBackOther(holder);
            }
        }else {
           setBackWhite(holder);
           holder.line.setBackgroundColor(mContext.getResources().getColor(R.color.white));
        }

        return convertView;
    }

    private void setBackWhite(ViewHolder holder){
        holder.tv.setBackgroundColor(mContext.getResources().getColor(R.color.white));
    }

    private void setBackOther(ViewHolder holder){
        holder.tv.setBackgroundColor(mContext.getResources().getColor(R.color.activity_bg));
    }

    private class ViewHolder {
        TextView tv;
        View line;
    }

}
