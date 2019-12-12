package com.newdjk.member.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.SearchDiseaseEntity;

import java.util.List;

/**
 * Created by EDZ on 2018/11/5.
 */

public class DiseaseAdapter extends BaseQuickAdapter<SearchDiseaseEntity.DataBean.ReturnDataBean, BaseViewHolder> {
    private int type = 0;
    private int total;

    public DiseaseAdapter(@Nullable List<SearchDiseaseEntity.DataBean.ReturnDataBean> data, int type) {
        super(R.layout.disease_layout, data);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchDiseaseEntity.DataBean.ReturnDataBean item) {
        helper.setText(R.id.mName, item.getDiseaseName());
        helper.setText(R.id.mSummary, item.getSummary());
        if (type==1) {
            helper.setVisible(R.id.mDiseaseCount,true);
            helper.setText(R.id.mDiseaseCount,"更多" + total + "" + "种疾病 >");
        }
        helper.setVisible(R.id.mtip, type == 1 ? true : false);
        helper.addOnClickListener(R.id.mDiseaseCount);
    }

    public void setTotle(int totle) {
        this.total = totle;
    }
}
