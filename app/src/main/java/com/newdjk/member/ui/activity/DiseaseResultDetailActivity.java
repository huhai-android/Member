package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.ui.fragment.IntroductionFragment;
import com.newdjk.member.ui.fragment.MyFragmentAdapter;
import com.newdjk.member.ui.fragment.SearchDoctorFragment;
import com.newdjk.member.ui.fragment.SearchDrugsFragment;
import com.newdjk.member.ui.fragment.SymptomFragment;
import com.newdjk.member.uploadimagelib.MainConstant;

import java.util.ArrayList;

import butterknife.BindView;

public class DiseaseResultDetailActivity extends BasicActivity {

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
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;


    private String mKeyWords;
    private ArrayList<Fragment> mList;
    private String tabTitles[] = new String[]{"简介", "症状","医生","药品"};
    private MyFragmentAdapter adapter;
    private IntroductionFragment mIntroductionFragment;
    private SymptomFragment mSymptomFragment;
    private SearchDoctorFragment mSearchDoctorFragment;
    private SearchDrugsFragment mSearchDrugsFragment;
    private int mDiseaseId;

    @Override
    protected int initViewResId() {
        return R.layout.activity_disease_result_detail;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white),0);
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            mKeyWords = intent.getStringExtra(MainConstant.KEYWORDS);
            mDiseaseId = intent.getIntExtra(MainConstant.DISEASEID,0);

            if (!TextUtils.isEmpty(mKeyWords)){
                initTitle(mKeyWords).setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                }).setTitleBgRes(R.color.white);
            }
        }

        mList = new ArrayList<>();
        mIntroductionFragment = IntroductionFragment.newInstance(mDiseaseId);
        mList.add(mIntroductionFragment);
        mSymptomFragment = SymptomFragment.newInstance(mDiseaseId);
        mList.add(mSymptomFragment);
        mSearchDoctorFragment = SearchDoctorFragment.newInstance(mKeyWords);
        mList.add(mSearchDoctorFragment);
        mSearchDrugsFragment = SearchDrugsFragment.newInstance(mKeyWords);
        mList.add(mSearchDrugsFragment);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        adapter = new MyFragmentAdapter(supportFragmentManager, mList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(4);
        mTab.setupWithViewPager(mViewPager);
        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab1 = mTab.getTabAt(i);//获得每一个tab
            tab1.setCustomView(R.layout.custom_tab);//给每一个tab设置view
            TextView title = (TextView) tab1.getCustomView().findViewById(R.id.tab_title);
            title.setText(tabTitles[i]);//设置tab上的文字
        }
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }
}
