package com.newdjk.member.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.activity.DiseaseResultDetailActivity;
import com.newdjk.member.ui.activity.DugsDetailActivity;
import com.newdjk.member.ui.activity.SearchResultActivity;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.adapter.DiseaseAdapter;
import com.newdjk.member.ui.adapter.DoctorAdapter;
import com.newdjk.member.ui.adapter.DugsAdapter;
import com.newdjk.member.ui.entity.DugsEntity;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.SearchDiseaseEntity;
import com.newdjk.member.ui.entity.SearchDoctorEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.HeadUitl;

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

public class SearchAllFragment extends BasicFragment {


    @BindView(R.id.mDiseaseRecycleView)
    RecyclerView mDiseaseRecycleView;
    @BindView(R.id.mDoctorRecycleView)
    RecyclerView mDoctorRecycleView;
    @BindView(R.id.mDugsRecycleView)
    RecyclerView mDugsRecycleView;
    Unbinder unbinder;
    private String mKeyWords;
    private DiseaseAdapter mDiseaseAdapter;
    private DoctorAdapter mDoctorAdapter;
    private DugsAdapter mDugsAdapter;
    List<SearchDiseaseEntity.DataBean.ReturnDataBean> dataList = new ArrayList<>();
    private List<SearchDoctorEntity.DataBean.ReturnDataBean> mDoctorData = new ArrayList<>();
    List<DugsEntity.DataBean.ReturnDataBean> dugsData = new ArrayList<>();
    private TextView mDocotorCount;
    private TextView mDugsCount;
    private View headview;
    private View mDoctorfootview;
    private View mDugsfootview;

    public static SearchAllFragment newInstance(String mKeyWords) {
        SearchAllFragment myFragment = new SearchAllFragment();
        Bundle args = new Bundle();
        args.putString(MainConstant.KEYWORDS, mKeyWords);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.search_fragment_all;
    }

    @Override
    protected void initView() {
        initRecycleView();
    }

    private void initRecycleView() {
        mDiseaseAdapter = new DiseaseAdapter(dataList, 1);
        mDiseaseRecycleView.setAdapter(mDiseaseAdapter);
        mDiseaseRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mDiseaseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchDiseaseEntity.DataBean.ReturnDataBean dataBean = (SearchDiseaseEntity.DataBean.ReturnDataBean) adapter.getItem(position);
                Intent intent = new Intent(mContext, DiseaseResultDetailActivity.class);
                intent.putExtra(MainConstant.KEYWORDS, mKeyWords);
                if (dataBean != null) {
                    intent.putExtra(MainConstant.DISEASEID, dataBean.getDiseaseId());
                }
                startActivity(intent);
            }
        });
        mDiseaseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ((SearchResultActivity) getActivity()).changeToAnotherFragment(1);
            }
        });


        mDoctorAdapter = new DoctorAdapter(mDoctorData);
        mDoctorRecycleView.setAdapter(mDoctorAdapter);
        mDoctorRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        headview = View.inflate(mContext, R.layout.search_all_doctor_headview, null);
//        mDoctorAdapter.addHeaderView(headview);
        mDoctorfootview = View.inflate(mContext, R.layout.search_all_doctor_footview, null);
        mDocotorCount = mDoctorfootview.findViewById(R.id.mDocotorCount);
//        mDoctorAdapter.addFooterView(mDoctorfootview);
        mDoctorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SearchDoctorEntity.DataBean.ReturnDataBean item = (SearchDoctorEntity.DataBean.ReturnDataBean) adapter.getItem(position);
                if (item != null) {
                    Intent intent = new Intent(SearchResultActivity.activity, WebViewActivity.class);
                    intent.putExtra("type", 24);
                    intent.putExtra("doctorId", item.getDrId());
                    startActivity(intent);
                }
            }
        });

        mDugsAdapter = new DugsAdapter(dugsData);
        mDugsRecycleView.setAdapter(mDugsAdapter);
        mDugsRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
        mDugsfootview = View.inflate(mContext, R.layout.search_all_dugs_footview, null);
        mDugsCount = mDugsfootview.findViewById(R.id.mDugsCount);

        mDugsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                DugsEntity.DataBean.ReturnDataBean item = (DugsEntity.DataBean.ReturnDataBean) adapter.getItem(position);
                if (item != null) {
                    Intent intent = new Intent(mContext,DugsDetailActivity.class);
                    intent.putExtra("id",item.getId());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    protected void initListener() {
        if (mDocotorCount != null) {
            mDocotorCount.setOnClickListener(this);
        }
        if (mDugsCount != null) {
            mDugsCount.setOnClickListener(this);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.mDocotorCount:
                ((SearchResultActivity) getActivity()).changeToAnotherFragment(2);
                break;
            case R.id.mDugsCount:
                ((SearchResultActivity) getActivity()).changeToAnotherFragment(3);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        mKeyWords = arguments.getString(MainConstant.KEYWORDS);
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        getdata();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void getdata() {
        getDepDiseaseForPage();
        getDoctoreData();
        getDugsData();
    }

    /**
     * 找医生
     */
    private void getDoctoreData() {
        Map<String, String> params = new HashMap<>();
        params.put("KeyName", mKeyWords);
        params.put("DrType", "1");
        params.put("HospitalId", "-1");
        params.put("DepartmentId", "-1");
        params.put("Position", "-1");
        params.put("OrgId", "-1");
        params.put("HosGroupId", "-1");
        params.put("IsHasPack", "-1");
        params.put("HospitalLevel", "-1");
        params.put("AreaId", "0");
        params.put("PageIndex", "1");
        params.put("PageSize", "2");

        mMyOkhttp.post().url(HttpUrl.QueryDoctorInfoByPage).params(params).tag(this).enqueue(new GsonResponseHandler<SearchDoctorEntity>() {
            @Override
            public void onSuccess(int statusCode, SearchDoctorEntity entituy) {
                if (entituy.getCode() == 0) {
                    SearchDoctorEntity.DataBean dataBean = entituy.getData();
                    if (dataBean != null) {
                        mDoctorData = dataBean.getReturnData();
                        if (mDoctorData != null && mDoctorData.size() > 0) {
                            mDoctorRecycleView.setVisibility(View.VISIBLE);
                            mDoctorAdapter.setNewData(mDoctorData);
                            mDoctorAdapter.removeHeaderView(headview);
                            mDoctorAdapter.addHeaderView(headview);
                            int total = dataBean.getTotal();
                            if (total > 0) {
                                mDoctorAdapter.removeFooterView(mDoctorfootview);
                                mDoctorAdapter.addFooterView(mDoctorfootview);
                                mDocotorCount.setText("更多" + total + "" + "个医生 >");
                            }
                        } else {
                            mDoctorRecycleView.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    /**
     * 找药品
     */
    private void getDugsData() {
        Map<String, String> params = new HashMap<>();
        params.put("KeyName", mKeyWords);
        params.put("PageSize", "" + 2);
        mMyOkhttp.post().url(HttpUrl.GetMedicationByKeyName).headers(HeadUitl.instance.getHeads()).params(params).tag(this).enqueue(new GsonResponseHandler<DugsEntity>() {
            @Override
            public void onSuccess(int statusCode, DugsEntity entituy) {
                if (entituy.getCode() == 0) {
                    DugsEntity.DataBean data = entituy.getData();
                    if (data != null) {
                        dugsData = data.getReturnData();
                        if (dugsData != null && dugsData.size() > 0) {
                            mDugsRecycleView.setVisibility(View.VISIBLE);
                            mDugsAdapter.setNewData(dugsData);
                            int total = data.getTotal();
                            if (total > 0) {
                                mDugsAdapter.removeFooterView(mDugsfootview);
                                mDugsAdapter.addFooterView(mDugsfootview);
                                mDugsCount.setText("更多" + total + "" + "个药品 >");
                            }
                        } else {
                            mDugsRecycleView.setVisibility(View.GONE);
                        }

                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    /**
     * 找疾病
     */
    public void getDepDiseaseForPage() {
        Map<String, String> params = new HashMap<>();
        params.put("DepartmentId", "-1");
        params.put("DiseaseName", mKeyWords);
        params.put("PageIndex", "1");
        params.put("PageSize", "1");
        mMyOkhttp.post().url(HttpUrl.GetDepDiseaseForPage).params(params).tag(this).enqueue(new GsonResponseHandler<SearchDiseaseEntity>() {
            @Override
            public void onSuccess(int statusCode, SearchDiseaseEntity entituy) {
                if (entituy.getCode() == 0) {
                    SearchDiseaseEntity.DataBean data = entituy.getData();
                    if (data != null) {
                        dataList = data.getReturnData();
                        if (dataList != null && dataList.size() > 0) {
                            mDiseaseRecycleView.setVisibility(View.VISIBLE);
                            mDiseaseAdapter.setNewData(dataList);
                            mDiseaseAdapter.setTotle(data.getTotal());
                        } else {
                            mDiseaseRecycleView.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setKeyWords(String keyWords) {
        mKeyWords = keyWords;
    }
}
