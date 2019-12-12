package com.newdjk.member.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.CustomMessageEntity;

import java.util.List;

public class CustomMessageAdapter extends BaseQuickAdapter< CustomMessageEntity.ContentBean, BaseViewHolder> {

    public CustomMessageAdapter(@Nullable List<CustomMessageEntity.ContentBean> data) {
        super( R.layout.list_item,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, CustomMessageEntity.ContentBean item) {
        CustomMessageEntity.ContentBean.ContentElemBean contentElem = item.getContentElem();
        if (contentElem != null) {
            helper.setText(R.id.service_item_name,contentElem.getText());
            String num = contentElem.getDetail();
            if (num != null) {
                helper.setText(R.id.number,num);
            }
        }
    }

}
