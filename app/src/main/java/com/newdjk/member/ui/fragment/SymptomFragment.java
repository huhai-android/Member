package com.newdjk.member.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.ui.adapter.SymptomAdapter;
import com.newdjk.member.ui.entity.DiseaseDetailEntity;
import com.newdjk.member.uploadimagelib.MainConstant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by EDZ on 2018/11/2.
 */

public class SymptomFragment extends BasicFragment {

    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    Unbinder unbinder;
    private int mDiseaseid;
    private List<String> symptomItems = new ArrayList<>();
    private SymptomAdapter adapter;

    public static SymptomFragment newInstance(int mDiseaseId) {
        SymptomFragment myFragment = new SymptomFragment();
        Bundle args = new Bundle();
        args.putInt(MainConstant.DISEASEID, mDiseaseId);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.search_fragment_symptom;
    }

    @Override
    protected void initView() {
        initRecycleView();
    }

    private void initRecycleView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false);
        adapter = new SymptomAdapter(symptomItems);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mRecycleView.setAdapter(adapter);
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
                    if (data != null) {
                        symptomItems = data.getSymptomItems();
                        adapter.setNewData(symptomItems);
                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });
    }
}