package com.newdjk.member.ui.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.newdjk.member.R;
import com.newdjk.member.ui.entity.QueryServicePackPageEntity;
import com.newdjk.member.ui.entity.ServicePackageEntity;

import java.util.List;

public class ServiceListAdapter extends BaseQuickAdapter<ServicePackageEntity.DataBean.ReturnDataBean,BaseViewHolder> {

    public ServiceListAdapter(@Nullable List<ServicePackageEntity.DataBean.ReturnDataBean> data) {
        super(R.layout.service_list_item_layout,data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServicePackageEntity.DataBean.ReturnDataBean item) {

        helper.setText(R.id.service_pack_name_tv,item.getServicePackName());
        ImageView view = helper.getView(R.id.service_pack_icon_iv);
        Glide.with(mContext)
                .load(item.getMasterPicture())
                .centerCrop()
                .dontAnimate()//防止设置placeholder导致第一次不显示网络图片,只显示默认图片的问题
                .error(R.mipmap.ic_service_default)
                .placeholder(R.mipmap.ic_service_default)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(view);
        helper.setText(R.id.OrgName_tv,item.getOrgName());
        helper.setText(R.id.fit_people_tv,item.getFitPeople() == null ? "服务人群：孕妇" : "服务人群：" + item.getFitPeople());
        helper.setText(R.id.service_pack_price_tv,item.getPrice()+"元");
        TextView tvDeposit = helper.getView(R.id.tv_deposit);
        if (item.getDeposit() > 0) {
            tvDeposit.setVisibility(View.VISIBLE);
            String deposit;
            if (String.valueOf(item.getDeposit()).endsWith(".00")) {
                deposit = String.valueOf(item.getDeposit()).replace(".00", "");
            } else if (String.valueOf(item.getDeposit()).endsWith(".0")) {
                deposit = String.valueOf(item.getDeposit()).replace(".0", "");
            } else {
                deposit = String.valueOf(item.getDeposit());
            }
            tvDeposit.setText("(含押金" + deposit + "元)");
        } else {
            tvDeposit.setVisibility(View.GONE);
        }
    }
}
