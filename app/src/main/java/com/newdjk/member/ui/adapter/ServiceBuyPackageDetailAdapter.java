package com.newdjk.member.ui.adapter;

import android.content.Context;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;

import java.util.List;

public class ServiceBuyPackageDetailAdapter extends BaseQuickAdapter<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean, BaseViewHolder> {
    private Context mContext;


    public ServiceBuyPackageDetailAdapter(@Nullable List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean> data, Context mContext) {
        super(R.layout.layout_list_service_package_detail, data);
        this.mContext = mContext;

    }

    @Override
    protected void convert(BaseViewHolder helper, QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean item) {
        helper.setText(R.id.tv_name, item.getServiceItemName());
        StringBuffer sb = new StringBuffer();
        if (item.getServiceItemName().contains("押金") || item.getServiceItemName().contains("租金")) {
            sb.append(item.getEachPrice());
            sb.append("元");
        } else {

            if (item.getNumTypeName() == null) {
                sb.append("按需");
            } else if (item.getNumTypeName().contains("需")) {
                sb.append("按需");
            } else {
                if (item.getNumType() == 1) {
                    sb.append(item.getNumValue());
                    sb.append("次");
                } else if (item.getNumType() == 2) {
                    sb.append(item.getNumValue());
                    sb.append("期");
                } else if (item.getNumType() == 3) {
                    sb.append(item.getNumValue());
                    sb.append("台");
                } else if (item.getNumType() == 4) {
                    sb.append(item.getNumValue());
                    sb.append("人");
                } else if (item.getNumType() == 5) {
                    sb.append(item.getNumValue());
                    sb.append("疗程");
                } else if (item.getNumType() == 8) {
                    sb.append("不限次");
                }
            }


          /*  sb.append(item.getEachPrice());
            sb.append("元");
            sb.append("/");

            sb.append("*");*/

        }
        helper.setText(R.id.tv_des, sb.toString());
    }
}
