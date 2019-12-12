package com.newdjk.member.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.GetMonitorPageEntity;

import java.util.List;

public class FetalHeartRecordListAdapter  extends BaseQuickAdapter<GetMonitorPageEntity.DataBean.ReturnDataBean, BaseViewHolder> {

    public FetalHeartRecordListAdapter(@Nullable List<GetMonitorPageEntity.DataBean.ReturnDataBean> data) {
        super(R.layout.fetal_heart_record_list_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetMonitorPageEntity.DataBean.ReturnDataBean item) {
        helper.setText(R.id.record_time_tv, item.getTLong() / 60 + mContext.getResources().getString(R.string.fetal_heart_min_string) + item.getTLong() % 60 + mContext.getResources().getString(R.string.fetal_heart_second_string));
        helper.setText(R.id.record_date_tv, item.getWeeks());
        helper.setText(R.id.create_time_tv, item.getCreateTime());
        switch (item.getStatus()) {
            case -1:
                helper.setText(R.id.right_arrow_iv, mContext.getResources().getString(R.string.fetal_heart_ask_doctor_string));
                break;
            case 0:
                helper.setText(R.id.right_arrow_iv, mContext.getResources().getString(R.string.fetal_heart_waiting_string));
                break;
            case 1:
                helper.setText(R.id.right_arrow_iv, mContext.getResources().getString(R.string.fetal_heart_check_report_string));
                break;
        }
        helper.addOnClickListener(R.id.right_arrow_iv);
    }
}
