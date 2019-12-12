package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.expandtext.ExpandCollapseListener;
import com.newdjk.member.expandtext.Section;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.entity.DugDetailCategoryBean;
import com.newdjk.member.ui.entity.DugTitleCategoryBean;
import com.newdjk.member.ui.entity.DugsDetailEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.views.ExpandableLayout;
import com.newdjk.member.views.ItemView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DugsDetailActivity extends BasicActivity {


    @BindView(R.id.top_left)
    ImageView topLeft;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_title)
    TextView topTitle;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;

    ExpandableLayout mComponent;

    @BindView(R.id.mName1)
    TextView mName1;
    @BindView(R.id.mName2)
    TextView mName2;
    @BindView(R.id.mTradeName)
    TextView mTradeName;
    @BindView(R.id.nameSpell)
    TextView nameSpell;


    private int id;
    private DugsDetailEntity.DataBean dataBean;

    @Override
    protected int initViewResId() {
        return R.layout.activity_dugs_detail;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white),0);
    }
    @Override
    protected void initView() {

        mComponent = findViewById(R.id.mComponent);

        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra("id", 0);
        }
        initTitle(getResources().getString(R.string.detail_dugs)).setLeftImage(R.mipmap.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);

        mComponent = findViewById(R.id.mComponent);
        mComponent.setRenderer(new ExpandableLayout.Renderer<DugTitleCategoryBean, DugDetailCategoryBean>() {
            @Override
            public void renderParent(View view, DugTitleCategoryBean model, boolean isExpanded, int parentPosition) {
                TextView name = view.findViewById(R.id.tvParent);
                name.setText(model.name);
                view.findViewById(R.id.arrow).setBackgroundResource(isExpanded ? R.drawable.arrow_up : R.drawable.arrow_down);
            }

            @Override
            public void renderChild(View view, DugDetailCategoryBean model, int parentPosition, int childPosition) {
                ((TextView) view.findViewById(R.id.tvChild)).setText(model.name);
            }
        });

        mComponent.setExpandListener((ExpandCollapseListener.ExpandListener<DugTitleCategoryBean>) (parentIndex, parent, view) -> {
            view.findViewById(R.id.arrow).setBackgroundResource( R.drawable.arrow_up);
        });

        mComponent.setCollapseListener((ExpandCollapseListener.CollapseListener<DugTitleCategoryBean>) (parentIndex, parent, view) -> {
            view.findViewById(R.id.arrow).setBackgroundResource( R.drawable.arrow_down);
        });
    }



    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getDugsDetailData();
    }

    @Override
    protected void otherViewClick(View view) {

    }

    public void getDugsDetailData() {
        Map<String, String> params = new HashMap<>();
        params.put("Id", id + "");
        mMyOkhttp.post().url(HttpUrl.GetMedicationFullInfo).headers(HeadUitl.instance.getHeads()).params(params).tag(this).enqueue(new GsonResponseHandler<DugsDetailEntity>() {
            @Override
            public void onSuccess(int statusCode, DugsDetailEntity entituy) {
                if (entituy.getCode() == 0) {
                    dataBean = entituy.getData();
                    mName1.setText(dataBean.getName());
                    mName2.setText(dataBean.getName());
                    mTradeName.setText(dataBean.getTradeName());
                    nameSpell.setText(dataBean.getNameSpell());
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.component),dataBean.getElement()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.function),dataBean.getFunction()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.Specification),dataBean.getSpecification()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.Usage),dataBean.getUsage()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.UntowardEffect),dataBean.getUntowardEffect()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.Contraindication),dataBean.getContraindication()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.Attention),dataBean.getAttention()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.ForPregnantLactating),dataBean.getForPregnantLactating()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.ForChildren),dataBean.getForChildren()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.ForOldPeople),dataBean.getForOldPeople()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.DrugInteraction),dataBean.getDrugInteraction()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.Pharmacodynamics),dataBean.getPharmacodynamics()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.Shape),dataBean.getShape()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.Package),dataBean.getPackage()));
                    mComponent.addSection(getComponentSection(getResources().getString(R.string.StoreUp),dataBean.getStoreUp()));

                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    public Section getComponentSection(String title, String element) {
        Section<DugTitleCategoryBean, DugDetailCategoryBean> section = new Section<>();
        DugTitleCategoryBean mDugTitleCategoryBean = new DugTitleCategoryBean(title);
        DugDetailCategoryBean mDugDetailCategoryBean1 = new DugDetailCategoryBean(element);

        section.parent = mDugTitleCategoryBean;
        section.children.add(mDugDetailCategoryBean1);
        section.expanded = true;
        return section;
    }


//    @OnClick({R.id.mComponent, R.id.mFunction, R.id.mSpecification, R.id.mUsage, R.id.mUntowardEffect, R.id.mContraindication, R.id.mAttention, R.id.mForPregnantLactating, R.id.mForChildren, R.id.mForOldPeople, R.id.mDrugInteraction, R.id.mPharmacodynamics, R.id.mShape, R.id.mPackage, R.id.mStoreUp})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.mComponent:
//                Intent intent0 = new Intent(this, DugsDescriptionActivity.class);
//                intent0.putExtra("title", getResources().getString(R.string.component));
//                intent0.putExtra("content", dataBean.getElement());
//                startActivity(intent0);
//                break;
//            case R.id.mFunction:
//                Intent intent6 = new Intent(this, DugsDescriptionActivity.class);
//                intent6.putExtra("title", getResources().getString(R.string.function));
//                intent6.putExtra("content", dataBean.getFunction());
//                startActivity(intent6);
//                break;
//            case R.id.mSpecification:
//                Intent intent1 = new Intent(this, DugsDescriptionActivity.class);
//                intent1.putExtra("title", getResources().getString(R.string.Specification));
//                intent1.putExtra("content", dataBean.getSpecification());
//                startActivity(intent1);
//                break;
//            case R.id.mUsage:
//                Intent intent2 = new Intent(this, DugsDescriptionActivity.class);
//                intent2.putExtra("title", getResources().getString(R.string.Usage));
//                intent2.putExtra("content", dataBean.getUsage());
//                startActivity(intent2);
//                break;
//            case R.id.mUntowardEffect:
//                Intent intent3 = new Intent(this, DugsDescriptionActivity.class);
//                intent3.putExtra("title", getResources().getString(R.string.UntowardEffect));
//                intent3.putExtra("content", dataBean.getUntowardEffect());
//                startActivity(intent3);
//                break;
//            case R.id.mContraindication:
//                Intent intent4 = new Intent(this, DugsDescriptionActivity.class);
//                intent4.putExtra("title", getResources().getString(R.string.Contraindication));
//                intent4.putExtra("content", dataBean.getContraindication());
//                startActivity(intent4);
//                break;
//            case R.id.mAttention:
//                Intent intent5 = new Intent(this, DugsDescriptionActivity.class);
//                intent5.putExtra("title", getResources().getString(R.string.Attention));
//                intent5.putExtra("content", dataBean.getAttention());
//                startActivity(intent5);
//                break;
//            case R.id.mForPregnantLactating:
//                Intent intent7 = new Intent(this, DugsDescriptionActivity.class);
//                intent7.putExtra("title", getResources().getString(R.string.ForPregnantLactating));
//                intent7.putExtra("content", dataBean.getForPregnantLactating());
//                startActivity(intent7);
//                break;
//            case R.id.mForChildren:
//                Intent intent8 = new Intent(this, DugsDescriptionActivity.class);
//                intent8.putExtra("title", getResources().getString(R.string.ForChildren));
//                intent8.putExtra("content", dataBean.getForChildren());
//                startActivity(intent8);
//                break;
//            case R.id.mForOldPeople:
//                Intent intent9 = new Intent(this, DugsDescriptionActivity.class);
//                intent9.putExtra("title", getResources().getString(R.string.ForOldPeople));
//                intent9.putExtra("content", dataBean.getForOldPeople());
//                startActivity(intent9);
//                break;
//            case R.id.mDrugInteraction:
//                Intent intent10 = new Intent(this, DugsDescriptionActivity.class);
//                intent10.putExtra("title", getResources().getString(R.string.DrugInteraction));
//                intent10.putExtra("content", dataBean.getDrugInteraction());
//                startActivity(intent10);
//                break;
//            case R.id.mPharmacodynamics:
//                Intent intent11 = new Intent(this, DugsDescriptionActivity.class);
//                intent11.putExtra("title", getResources().getString(R.string.Pharmacodynamics));
//                intent11.putExtra("content", dataBean.getPharmacodynamics());
//                startActivity(intent11);
//                break;
//            case R.id.mShape:
//                Intent intent12 = new Intent(this, DugsDescriptionActivity.class);
//                intent12.putExtra("title", getResources().getString(R.string.Shape));
//                intent12.putExtra("content", dataBean.getShape());
//                startActivity(intent12);
//                break;
//            case R.id.mPackage:
//                Intent intent13 = new Intent(this, DugsDescriptionActivity.class);
//                intent13.putExtra("title", getResources().getString(R.string.Package));
//                intent13.putExtra("content", dataBean.getPackage());
//                startActivity(intent13);
//                break;
//            case R.id.mStoreUp:
//                Intent intent14 = new Intent(this, DugsDescriptionActivity.class);
//                intent14.putExtra("title", getResources().getString(R.string.StoreUp));
//                intent14.putExtra("content", dataBean.getStoreUp());
//                startActivity(intent14);
//                break;
//        }
//    }
}
