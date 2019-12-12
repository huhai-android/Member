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
import com.newdjk.member.ui.activity.DiseaseResultDetailActivity;
import com.newdjk.member.ui.adapter.DiseaseAdapter;
import com.newdjk.member.ui.entity.SearchDiseaseEntity;
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

public class SearchDiseaseFragment extends BasicFragment {

    @BindView(R.id.mDiseaseRecycleView)
    RecyclerView mDiseaseRecycleView;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    Unbinder unbinder;
    private DiseaseAdapter mDiseaseAdapter;
    List<SearchDiseaseEntity.DataBean.ReturnDataBean> dataList = new ArrayList<>();
    private String mKeyWords;
    private int index =1;

    public static SearchDiseaseFragment newInstance(String mKeyWords) {
        SearchDiseaseFragment myFragment = new SearchDiseaseFragment();
        Bundle args = new Bundle();
        args.putString(MainConstant.KEYWORDS, mKeyWords);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.search_fragment_disease;
    }

    @Override
    protected void initView() {
        initRecycleView();
    }

    private void initRecycleView() {
        mDiseaseAdapter = new DiseaseAdapter(dataList,0);
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
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                getDepDiseaseForPage(index);
            }

            @Override
            public void onRefreshing() {
                index = 1;
                getDepDiseaseForPage( index);
            }
        });
    }

    public void setKeyWords(String keyWords) {
        mKeyWords = keyWords;
    }

    public void refresh(){
        index = 1;
        getDepDiseaseForPage( index);
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
        mKeyWords = arguments.getString(MainConstant.KEYWORDS);
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container, savedInstanceState));
        index = 1;
        getDepDiseaseForPage(index);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 找疾病
     */
    public void getDepDiseaseForPage(final int index) {
        Map<String, String> params = new HashMap<>();
        params.put("DepartmentId", "-1");
        params.put("DiseaseName", mKeyWords);
        params.put("PageIndex", ""+index);
        params.put("PageSize", "10");
        mMyOkhttp.post().url(HttpUrl.GetDepDiseaseForPage).params(params).tag(this).enqueue(new GsonResponseHandler<SearchDiseaseEntity>() {
            @Override
            public void onSuccess(int statusCode, SearchDiseaseEntity entituy) {
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing()) mEasylayout.refreshComplete();
                if (entituy.getCode() == 0) {
                    SearchDiseaseEntity.DataBean data = entituy.getData();
                    if (data != null && data.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        if (data.getTotal() < 10) {
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        }
                        List<SearchDiseaseEntity.DataBean.ReturnDataBean> returnData = data.getReturnData();
                        if (returnData.size() == 0)  {
                            toast("没有更多数据");
                        }

                        if (index ==1) {
                            dataList.clear();
                            dataList.addAll(returnData);
                            mDiseaseAdapter.setNewData(dataList);
                            mEasylayout.refreshComplete();
                        } else {
                            mEasylayout.closeLoadView();
                            //    dataList.addAll(list);
                            mDiseaseAdapter.getData().addAll(returnData);
                            int postion = mDiseaseAdapter.getData().size();
                            mDiseaseAdapter.notifyDataSetChanged();
                            mDiseaseRecycleView.scrollToPosition(postion);
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
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}