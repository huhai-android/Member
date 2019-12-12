package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.ui.fragment.MyFragmentAdapter;
import com.newdjk.member.ui.fragment.SearchAllFragment;
import com.newdjk.member.ui.fragment.SearchDiseaseFragment;
import com.newdjk.member.ui.fragment.SearchDoctorFragment;
import com.newdjk.member.ui.fragment.SearchDrugsFragment;
import com.newdjk.member.uploadimagelib.MainConstant;

import java.util.ArrayList;

import butterknife.BindView;

public class SearchResultActivity extends BasicActivity {


    @BindView(R.id.mEditText)
    EditText mEditText;
    @BindView(R.id.top_left)
    ImageView mLeft;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;

    private String mKeyWords;
    private ArrayList<Fragment> mList;
    private SearchAllFragment mSearchAllFragment;
    private SearchDiseaseFragment mSearchDiseaseFragment;
    private SearchDoctorFragment mSearchDoctorFragment;
    private SearchDrugsFragment mSearchDrugsFragment;
    private MyFragmentAdapter adapter;
    private String tabTitles[] = new String[]{"全部", "疾病", "医生", "药品"};

    @Override
    protected int initViewResId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
    }


    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            mKeyWords = intent.getStringExtra(MainConstant.KEYWORDS);
            if (!TextUtils.isEmpty(mKeyWords)) {
                mEditText.setHint(mKeyWords);
            }
        }

        mList = new ArrayList<>();
        mSearchAllFragment = SearchAllFragment.newInstance(mKeyWords);
        mList.add(mSearchAllFragment);
        mSearchDiseaseFragment = SearchDiseaseFragment.newInstance(mKeyWords);
        mList.add(mSearchDiseaseFragment);
        mSearchDoctorFragment = SearchDoctorFragment.newInstance(mKeyWords);
        mList.add(mSearchDoctorFragment);
        mSearchDrugsFragment = SearchDrugsFragment.newInstance(mKeyWords);
        mList.add(mSearchDrugsFragment);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        adapter = new MyFragmentAdapter(supportFragmentManager, mList);
        mViewPager.setAdapter(adapter);
        mViewPager.setOffscreenPageLimit(3);
        mTab.setupWithViewPager(mViewPager);
        for (int i = 0; i < adapter.getCount(); i++) {
            TabLayout.Tab tab1 = mTab.getTabAt(i);//获得每一个tab
            tab1.setCustomView(R.layout.custom_tab);//给每一个tab设置view
            TextView title = (TextView) tab1.getCustomView().findViewById(R.id.tab_title);
            title.setText(tabTitles[i]);//设置tab上的文字
        }


        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //搜索
                    String content = mEditText.getText().toString();
                    if (TextUtils.isEmpty(content)) {
                        toast("请输入关键词,然后进行搜索");
                        return true;
                    }
                    mSearchAllFragment.setKeyWords(content);
                    mSearchAllFragment.getdata();
                    mSearchDiseaseFragment.setKeyWords(content);
                    mSearchDiseaseFragment.refresh();
                    mSearchDoctorFragment.setKeyWords(content);
                    mSearchDoctorFragment.refresh();
                    mSearchDrugsFragment.setKeyWords(content);
                    mSearchDrugsFragment.refresh();
                }
                return false;
            }
        });


    }

    @Override
    protected void initListener() {
        mLeft.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                finish();
                break;
        }
    }


    public void changeToAnotherFragment(int index) {
        mViewPager.setCurrentItem(index, true);

    }


}
