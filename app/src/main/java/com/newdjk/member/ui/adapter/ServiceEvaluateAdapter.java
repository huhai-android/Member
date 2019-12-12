package com.newdjk.member.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newdjk.member.R;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;

import java.util.List;

public class ServiceEvaluateAdapter extends RecyclerView.Adapter<ServiceEvaluateAdapter.MyViewHolder> {

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

    public ServiceEvaluateAdapter(Context context, List lists) {
        this.mContext = context;
        this.mLists = lists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.service_evaluate_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        /*holder.service_content_item_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = holder.getLayoutPosition();
                mOnItemClickLitener.onItemClick(holder.itemView, pos);
            }
        });
        holder.service_content_title_tv.setText(mLists.get(position).getServiceItemName());*/
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);

        }
    }
}
