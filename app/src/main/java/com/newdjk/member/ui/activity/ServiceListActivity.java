package com.newdjk.member.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.adapter.ServiceListAdapter;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.ui.entity.QueryServicePackPageEntity;
import com.newdjk.member.ui.entity.ServicePackageEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.RecycleViewDivider;
import com.newdjk.member.views.YRecycleview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class ServiceListActivity extends BasicActivity {
    public static List<Activity> activity_lists = new ArrayList<Activity>();

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;

    private ServiceListAdapter serviceListAdapter;
    private HaveBindEntity.DataBean.PatientBean patientBean;
    private int PageSize = 10;

    private String go_path;
    private int index = 1;

    private List<ServicePackageEntity.DataBean.ReturnDataBean> dataList = new ArrayList<>();
    private ServiceListAdapter mServiceListAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.activity_service_list;
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.service_list_title_string));
        mServiceListAdapter = new ServiceListAdapter(dataList);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(mServiceListAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mServiceListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (dataList.get(position).getServiceClassId() == 8) {//胎心服务包
                    Intent intent = new Intent(ServiceListActivity.this, WebViewActivity.class);
                    intent.putExtra("type", WebUtil.fetalHeart);
                    intent.putExtra("id", dataList.get(position).getServicePackId());
                    intent.putExtra("Patient", patientBean);
                    toActivity(intent);
                } else {//不是胎心服务包
                    Intent intent = new Intent(ServiceListActivity.this, WebViewActivity.class);
                    intent.putExtra("type", WebUtil.servicePackageDetail);
                    intent.putExtra("id", dataList.get(position).getServicePackId());
                    intent.putExtra("Patient", patientBean);
                    toActivity(intent);
                }
                activity_lists.add(ServiceListActivity.this);
            }
        });
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                getServicePackListData(index);
            }

            @Override
            public void onRefreshing() {
                index = 1;
                getServicePackListData(index);
            }
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        patientBean = (HaveBindEntity.DataBean.PatientBean) intent.getSerializableExtra("Patient");
        go_path = intent.getStringExtra("go_path");
        getServicePackListData(index);

    }

    private void getServicePackListData(int index) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("ServiceClassId", "8");
//        paramsMap.put("OrgId", SpUtils.getString(Contants.OrgId));
        paramsMap.put("PageIndex", index + "");
        paramsMap.put("PageSize", PageSize + "");
        mMyOkhttp.post().url(HttpUrl.QueryOrgServicePackByPage).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<ServicePackageEntity>() {
            @Override
            public void onSuccess(int statusCode, ServicePackageEntity response) {
                if (response.getCode() == 0) {
                    if (mEasylayout == null) return;
                    if (mEasylayout.isRefreshing()) mEasylayout.refreshComplete();
                    if (mEasylayout.isLoading()) mEasylayout.closeLoadView();
                    if (response.getCode() == 0) {
                        ServicePackageEntity.DataBean data = response.getData();
                        if (data != null && data.getTotal() > 0) {
                            mNodataContainer.setVisibility(View.GONE);
                            mEasylayout.setVisibility(View.VISIBLE);
                            List<ServicePackageEntity.DataBean.ReturnDataBean> returnData = data.getReturnData();
                            if (index != 1 && returnData != null && returnData.size() < 10) {
                                mEasylayout.setLoadMoreModel(LoadModel.NONE);
                                toast("没有更多数据了");
                            } else {
                                mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                            }
                            if (index == 1) {
                                dataList.clear();
                                dataList.addAll(returnData);
                                mServiceListAdapter.setNewData(dataList);
                                mEasylayout.refreshComplete();
                            } else {
                                mEasylayout.closeLoadView();
                                //dataList.addAll(list);
                                mServiceListAdapter.getData().addAll(returnData);
                                int postion = mServiceListAdapter.getData().size();
                                mServiceListAdapter.notifyDataSetChanged();
                                mRecyclerview.scrollToPosition(postion);
                            }
                        } else {
                            mNodataContainer.setVisibility(View.VISIBLE);
                            mEasylayout.setVisibility(View.GONE);
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


    @Override
    protected void otherViewClick(View view) {

    }

}
