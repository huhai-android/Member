package com.newdjk.member.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.ui.entity.DiseaseDetailEntity;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.SearchDiseaseEntity;
import com.newdjk.member.uploadimagelib.MainConstant;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by EDZ on 2018/11/2.
 */

public class IntroductionFragment extends BasicFragment {

    @BindView(R.id.mName)
    TextView mName;
    @BindView(R.id.mSummary)
    TextView mSummary;
    Unbinder unbinder;
    private int mDiseaseid;

    public static IntroductionFragment newInstance(int mDiseaseId) {
        IntroductionFragment myFragment = new IntroductionFragment();
        Bundle args = new Bundle();
        args.putInt(MainConstant.DISEASEID, mDiseaseId);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.search_fragment_introduction;
    }

    @Override
    protected void initView() {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        mDiseaseid = arguments.getInt(MainConstant.DISEASEID,0);
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));

        getDepDiseaseByDiseaseId();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void getDepDiseaseByDiseaseId() {
        Map<String, String> params = new HashMap<>();
        params.put("DiseaseId", ""+mDiseaseid);
        mMyOkhttp.get().url(HttpUrl.GetDiseaseByDiseaseId).params(params).tag(this).enqueue(new GsonResponseHandler<DiseaseDetailEntity>() {
            @Override
            public void onSuccess(int statusCode, DiseaseDetailEntity entituy) {
                if (entituy.getCode() == 0) {
                    DiseaseDetailEntity.DataBean data = entituy.getData();
                    mName.setText(data.getDiseaseName());
                    mSummary.setText(data.getSummary());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });
    }
}