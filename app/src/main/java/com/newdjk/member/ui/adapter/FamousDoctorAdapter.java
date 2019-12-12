package com.newdjk.member.ui.adapter;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.FamousDoctorOrNurseEntity;
import com.newdjk.member.views.CircleImageView;

import java.util.List;

/**
 * Created by EDZ on 2018/9/17.
 */

public class FamousDoctorAdapter extends BaseQuickAdapter<FamousDoctorOrNurseEntity.DataBean,BaseViewHolder> {

    private int type;

    public FamousDoctorAdapter(List<FamousDoctorOrNurseEntity.DataBean> data) {
        super(R.layout.famous_doctor,data);
    }

    public void setType(int type){
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, FamousDoctorOrNurseEntity.DataBean item) {
        CircleImageView view = helper.getView(R.id.mPhoto);
        if (type == 1) {
            Glide.with(mContext).load(item.getPicturePath()).error(R.drawable.icon_iv_doctor).into(view);
        } else {
            Glide.with(mContext).load(item.getPicturePath()).error(R.drawable.icon_nurse).into(view);
        }
        helper.setText(R.id.mName,item.getDrName());
        helper.setText(R.id.mHospitalName,item.getHospitalName());
        helper.setText(R.id.mDepartmentName,item.getDepartmentName());
        helper.setBackgroundRes(R.id.mHospitalName,type==1?R.color.famous_doctor_bg:R.color.famous_nurse_bg);
        helper.setText(R.id.mPraiseRate,item.getPraiseRate()+"%");
        helper.setText(R.id.mConsultCount, item.getManuInterVolume()+"");
        helper.addOnClickListener(R.id.mContainer);
    }

}
