package com.newdjk.member.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.GetReadDoctorListEntity;

import java.util.List;

public class ServiceTeamAdapter extends RecyclerView.Adapter<ServiceTeamAdapter.MyViewHolder> {

    private Context mContext;
    private List<GetReadDoctorListEntity.DataBean> mLists;

    public ServiceTeamAdapter(Context context, List lists) {
        this.mContext = context;
        this.mLists = lists;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.service_team_item_layout, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        Glide.with(mContext).load(mLists.get(position).getPicturePath()).into(holder.doctor_head_image_iv);
        holder.doctor_name_tv.setText(mLists.get(position).getDrName());
        holder.doctor_department_tv.setText(mLists.get(position).getDepartmentName());
        holder.doctor_hospital_tv.setText(mLists.get(position).getHospitalName());
        holder.doctor_position_tv.setText(mLists.get(position).getPositionName());
    }

    @Override
    public int getItemCount() {
        return mLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView doctor_head_image_iv;
        private TextView doctor_name_tv;
        private TextView doctor_department_tv;
        private TextView doctor_hospital_tv;
        private TextView doctor_position_tv;
        public MyViewHolder(View itemView) {
            super(itemView);
            doctor_head_image_iv = (ImageView) itemView.findViewById(R.id.doctor_head_image_iv);
            doctor_name_tv = (TextView) itemView.findViewById(R.id.doctor_name_tv);
            doctor_department_tv = (TextView) itemView.findViewById(R.id.doctor_department_tv);
            doctor_hospital_tv = (TextView) itemView.findViewById(R.id.doctor_hospital_tv);
            doctor_position_tv = (TextView) itemView.findViewById(R.id.doctor_position_tv);
        }
    }
}
