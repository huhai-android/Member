package com.newdjk.member.ui.adapter;

        import android.support.annotation.Nullable;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.TextView;

        import com.bumptech.glide.Glide;
        import com.chad.library.adapter.base.BaseQuickAdapter;
        import com.chad.library.adapter.base.BaseViewHolder;
        import com.newdjk.member.R;
        import com.newdjk.member.ui.entity.DugsEntity;
        import com.newdjk.member.ui.entity.SearchDoctorEntity;
        import com.newdjk.member.views.CircleImageView;
        import com.newdjk.member.views.FlowLayout;
        import com.newdjk.member.views.TagFlowLayout;

        import java.util.List;

/**
 * Created by EDZ on 2018/11/5.
 */

public class DugsAdapter extends BaseQuickAdapter<DugsEntity.DataBean.ReturnDataBean, BaseViewHolder> {


    public DugsAdapter(@Nullable List<DugsEntity.DataBean.ReturnDataBean> data) {
        super(R.layout.dugs_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DugsEntity.DataBean.ReturnDataBean item) {
        helper.setText(R.id.mDugsName,item.getName());
        helper.setText(R.id.mDugsCompany,item.getManufacturer());
    }
}
