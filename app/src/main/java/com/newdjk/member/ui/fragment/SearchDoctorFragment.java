package com.newdjk.member.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.activity.SearchResultActivity;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.adapter.DoctorAdapter;
import com.newdjk.member.ui.entity.SearchDoctorEntity;
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

public class SearchDoctorFragment extends BasicFragment {

    @BindView(R.id.mDoctorRecycleView)
    RecyclerView mDoctorRecycleView;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;

    private String mKeyWords;
    private Unbinder unbinder;
    private List<SearchDoctorEntity.DataBean.ReturnDataBean> mDoctorData = new ArrayList<>();
    private DoctorAdapter mDoctorAdapter;
    private int index;

    public static SearchDoctorFragment newInstance(String mKeyWords) {
        SearchDoctorFragment myFragment = new SearchDoctorFragment();
        Bundle args = new Bundle();
        args.putString(MainConstant.KEYWORDS, mKeyWords);
        myFragment.setArguments(args);
        return myFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        mKeyWords = arguments.getString(MainConstant.KEYWORDS);
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        index = 1;
        getDoctoreData(index);
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected int initViewResId() {
        return R.layout.search_fragment_doctor;
    }

    @Override
    protected void initView() {
        initRecycleView();
    }

    private void initRecycleView() {
        mDoctorAdapter = new DoctorAdapter(mDoctorData);
        mDoctorRecycleView.setAdapter(mDoctorAdapter);
        mDoctorRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
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
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                getDoctoreData(index);
            }

            @Override
            public void onRefreshing() {
                index = 1;
                getDoctoreData( index);
            }
        });
    }
    public void setKeyWords(String keyWords) {
        mKeyWords = keyWords;
    }
    public void refresh(){
        index = 1;
        getDoctoreData( index);
    }

    @Override
    protected void initData() {

    }


    /**
     * 找医生
     */
    private void getDoctoreData(final int index) {
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
        params.put("PageIndex", index + "");
        params.put("PageSize", "10");

        mMyOkhttp.post().url(HttpUrl.QueryDoctorInfoByPage).params(params).tag(this).enqueue(new GsonResponseHandler<SearchDoctorEntity>() {
            @Override
            public void onSuccess(int statusCode, SearchDoctorEntity entituy) {
                if (mEasylayout == null) {
                    return;
                }
                if (mEasylayout.isRefreshing()) {
                    mEasylayout.refreshComplete();
                }
                if (entituy.getCode() == 0) {
                    SearchDoctorEntity.DataBean dataBean = entituy.getData();
                    if (dataBean != null && dataBean.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        if (dataBean.getTotal() < 10) {
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        }

                        List<SearchDoctorEntity.DataBean.ReturnDataBean> returnData = dataBean.getReturnData();
                        if (returnData.size() == 0) {
                            toast("没有更多数据");
                        }

                        if (index == 1) {
                            mDoctorData.clear();
                            mDoctorData.addAll(returnData);
                            mDoctorAdapter.setNewData(mDoctorData);
                            mEasylayout.refreshComplete();
                        } else {
                            mEasylayout.closeLoadView();
                            mDoctorAdapter.getData().addAll(returnData);
                            int postion = mDoctorAdapter.getData().size();
                            mDoctorAdapter.notifyDataSetChanged();
                            mDoctorRecycleView.scrollToPosition(postion);
                        }
                    } else {
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                } else {
                    mNodataContainer.setVisibility(View.VISIBLE);
                    mEasylayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {

    }
}