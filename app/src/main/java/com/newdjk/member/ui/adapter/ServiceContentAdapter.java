package com.newdjk.member.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;
import com.newdjk.member.ui.entity.QueryServicePackPageEntity;

import java.util.List;

public class ServiceContentAdapter extends RecyclerView.Adapter<ServiceContentAdapter.MyViewHolder> {

    private Context mContext;
    private List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean> mLists;

    private OnItemClickLitener mOnItemClickLitener;

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public ServiceContentAdapter(Context context, List lists) {
        this.mContext = context;
        this.mLists = lists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.service_content_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.service_content_item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
            }
        });
        holder.service_content_title_tv.setText(mLists.get(position).getServiceItemName());
        holder.service_content_data_tv.setText(mLists.get(position).getNumValue() + mLists.get(position).getNumTypeName());
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout service_content_item_rl;
        private TextView service_content_title_tv;
        private TextView service_content_data_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            service_content_item_rl = (RelativeLayout) itemView.findViewById(R.id.service_content_item_rl);
            service_content_title_tv = (TextView) itemView.findViewById(R.id.service_content_title_tv);
            service_content_data_tv = (TextView) itemView.findViewById(R.id.service_content_data_tv);
        }
    }
}
