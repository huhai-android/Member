package com.newdjk.member.ui.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.SearchDoctorEntity;
import com.newdjk.member.views.CircleImageView;
import com.newdjk.member.views.FlowLayout;
import com.newdjk.member.views.TagFlowLayout;

import java.util.List;

/**
 * Created by EDZ on 2018/11/5.
 */

public class DoctorAdapter extends BaseQuickAdapter<SearchDoctorEntity.DataBean.ReturnDataBean, BaseViewHolder> {

    private LayoutInflater mInflater;

    public DoctorAdapter(@Nullable List<SearchDoctorEntity.DataBean.ReturnDataBean> data) {
        super(R.layout.doctor_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDoctorEntity.DataBean.ReturnDataBean item) {
        mInflater = LayoutInflater.from(mContext);
        helper.setText(R.id.mName, item.getDrName());
        CircleImageView view = helper.getView(R.id.mPhoto);
        Glide.with(mContext).load(item.getPicturePath()).error(R.drawable.icon_iv_doctor).into(view);
        helper.setText(R.id.mHospitalName,item.getHospitalName());
        helper.setText(R.id.mPosotionName,item.getPositionName());
        helper.setText(R.id.mDepartmentName,item.getDepartmentName());
       // int consultCount = item.getConsultVolume() + item.getOnPrescription() + item.getVolumeVideo();
        helper.setText(R.id.mConsultCount,"问诊量:"+item.getManuInterVolume());
        final TagFlowLayout mTagFlowLayout = helper.getView(R.id.mServiceFlowlayout);
        List<SearchDoctorEntity.DataBean.ReturnDataBean.DrServiceItemsBean> drServiceItems = item.getDrServiceItems();
        mTagFlowLayout.setAdapter(new TagAdapter<SearchDoctorEntity.DataBean.ReturnDataBean.DrServiceItemsBean>(drServiceItems){

            @Override
            public View getView(FlowLayout parent, int position, SearchDoctorEntity.DataBean.ReturnDataBean.DrServiceItemsBean drServiceItemsBean) {
                TextView tv = (TextView) mInflater.inflate(R.layout.service_tv_layout, mTagFlowLayout, false);
                tv.setText(drServiceItemsBean.getSericeItemName());
                return tv;
            }
        });
    }
}
