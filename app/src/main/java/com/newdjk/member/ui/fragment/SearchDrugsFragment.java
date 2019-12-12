package com.newdjk.member.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.activity.DugsDetailActivity;
import com.newdjk.member.ui.adapter.DugsAdapter;
import com.newdjk.member.ui.entity.DugsEntity;
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

public class SearchDrugsFragment extends BasicFragment {


    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    @BindView(R.id.mDugsRecycleView)
    RecyclerView mDugsRecycleView;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    Unbinder unbinder;
    private String mKeyWords;
    private int index = 1;
    List<DugsEntity.DataBean.ReturnDataBean> dugsData = new ArrayList<>();
    private DugsAdapter mDugsAdapter;

    public static SearchDrugsFragment newInstance(String mKeyWords) {
        SearchDrugsFragment myFragment = new SearchDrugsFragment();
        Bundle args = new Bundle();
        args.putString(MainConstant.KEYWORDS, mKeyWords);
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    protected int initViewResId() {
        return R.layout.search_fragment_drugs;
    }

    @Override
    protected void initView() {
        mDugsAdapter = new DugsAdapter(dugsData);
        mDugsRecycleView.setAdapter(mDugsAdapter);
        mDugsRecycleView.setLayoutManager(new LinearLayoutManager(getContext(), OrientationHelper.VERTICAL, false));
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
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                getDugsData(index);
            }

            @Override
            public void onRefreshing() {
                index = 1;
                getDugsData(index);
            }
        });
    }
    public void setKeyWords(String keyWords) {
        mKeyWords = keyWords;
    }
    public void refresh(){
        index = 1;
        getDugsData(index);
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
        getDugsData(index);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 找药品
     *
     * @param index
     */
    private void getDugsData(final int index) {
        Map<String, String> params = new HashMap<>();
        params.put("KeyName", mKeyWords);
        params.put("PageIndex", index + "");
        params.put("PageSize", "10");
        mMyOkhttp.post().url(HttpUrl.GetMedicationByKeyName).headers(HeadUitl.instance.getHeads()).params(params).tag(this).enqueue(new GsonResponseHandler<DugsEntity>() {
            @Override
            public void onSuccess(int statusCode, DugsEntity entituy) {
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing()) mEasylayout.refreshComplete();
                if (entituy.getCode() == 0) {
                    DugsEntity.DataBean dataBean = entituy.getData();
                    if (dataBean != null && dataBean.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        if (dataBean.getTotal() < 10) {
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                        }
                        List<DugsEntity.DataBean.ReturnDataBean> returnData = dataBean.getReturnData();
                        if (returnData.size() == 0) {
                            toast("没有更多数据");
                        }

                        if (index == 1) {
                            dugsData.clear();
                            dugsData.addAll(returnData);
                            mDugsAdapter.setNewData(dugsData);
                            mEasylayout.refreshComplete();
                        } else {
                            mEasylayout.closeLoadView();
                            mDugsAdapter.getData().addAll(returnData);
                            int postion = mDugsAdapter.getData().size();
                            mDugsAdapter.notifyDataSetChanged();
                            mDugsRecycleView.scrollToPosition(postion);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}