package com.newdjk.member.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.member.R;

import java.util.List;

/**
 * Created by EDZ on 2018/11/7.
 */

public class SymptomAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public SymptomAdapter(@Nullable List<String> data) {
        super(R.layout.symptom,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.mName,item);
    }
}
