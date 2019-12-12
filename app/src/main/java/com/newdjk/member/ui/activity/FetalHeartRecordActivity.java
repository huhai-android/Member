package com.newdjk.member.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.adapter.FetalHeartRecordListAdapter;
import com.newdjk.member.ui.entity.AskCheckEntity;
import com.newdjk.member.ui.entity.GetMonitorPageEntity;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.ui.entity.SubmitAskEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.CommomDialog;
import com.newdjk.member.views.RecycleViewDivider;
import com.newdjk.member.views.YRecycleview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FetalHeartRecordActivity extends BasicActivity {


    public static List<Activity> fetalHeart_Record_lists = new ArrayList<>();

    @BindView(R.id.mRecyclerview)
    RecyclerView mRecyclerview;
    @BindView(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;


    private FetalHeartRecordListAdapter fetalHeartRecordListAdapter;
    private HaveBindEntity.DataBean.PatientBean patientBean;

    private String ServicePhone;
    private int PageSize = 10;
    private int index = 1;

    private String go_path;

    private List<GetMonitorPageEntity.DataBean.ReturnDataBean> lists = new ArrayList<>();

    @Override
    protected int initViewResId() {
        return R.layout.activity_fetal_heart_record;
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.fetal_heart_record_title_string));
        Intent intent = getIntent();
        patientBean = (HaveBindEntity.DataBean.PatientBean) intent.getSerializableExtra("Patient");
        ServicePhone = intent.getStringExtra("ServicePhone");
        go_path = intent.getStringExtra("go_path");

        fetalHeartRecordListAdapter = new FetalHeartRecordListAdapter(lists);
        mRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerview.setAdapter(fetalHeartRecordListAdapter);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        fetalHeartRecordListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecordItemClick(position);
            }
        });
        fetalHeartRecordListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                RecordItemRightArrow(position);
            }
        });
    }

    @Override
    protected void initListener() {
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                index++;
                requestHeartRecordData(index);
            }

            @Override
            public void onRefreshing() {
                index = 1;
                requestHeartRecordData(index);
            }
        });
    }

    @Override
    protected void initData() {
        index = 1;
        requestHeartRecordData(index);
    }

    private void requestHeartRecordData(int index) {
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
        paramsMap.put("PatientId", patientBean.getPatientId() + "");
        paramsMap.put("PageIndex", index + "");
        paramsMap.put("PageSize", PageSize + "");
        mMyOkhttp.post().url(HttpUrl.GetMonitorPage).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<GetMonitorPageEntity>() {
            @Override
            public void onSuccess(int statusCode, GetMonitorPageEntity response) {
                if (mEasylayout == null) return;
                if (mEasylayout.isRefreshing()) mEasylayout.refreshComplete();
                if (mEasylayout.isLoading()) mEasylayout.closeLoadView();
                if (response.getCode() == 0) {
                    GetMonitorPageEntity.DataBean data = response.getData();
                    if (data != null && data.getTotal() > 0) {
                        mNodataContainer.setVisibility(View.GONE);
                        mEasylayout.setVisibility(View.VISIBLE);
                        List<GetMonitorPageEntity.DataBean.ReturnDataBean> sublists = response.getData().getReturnData();
                        if (index != 1 && sublists != null && sublists.size() < 10) {
                            mEasylayout.setLoadMoreModel(LoadModel.NONE);
                            toast("没有更多数据了");
                        } else {
                            mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
                        }
//                        mEasylayout.setLoadMoreModel(data.getTotal() < 10 ? LoadModel.NONE : LoadModel.COMMON_MODEL);
                        if (index == 1) {
                            lists.clear();
                            lists.addAll(sublists);
                            fetalHeartRecordListAdapter.setNewData(lists);
                            mEasylayout.refreshComplete();
                        } else {
                            mEasylayout.closeLoadView();
                            //dataList.addAll(list);
                            fetalHeartRecordListAdapter.getData().addAll(sublists);
                            int postion = fetalHeartRecordListAdapter.getData().size();
                            fetalHeartRecordListAdapter.notifyDataSetChanged();
                            mRecyclerview.scrollToPosition(postion);
                        }
                    } else {
                        mNodataContainer.setVisibility(View.VISIBLE);
                        mEasylayout.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void RecordItemClick(final int position) {
        if (lists.get(position).getStatus() == -1) {
            Intent intent = new Intent(FetalHeartRecordActivity.this, ReadingReportActivity.class);
            intent.putExtra("id", lists.get(position).getId());
            intent.putExtra("status", 2);
            intent.putExtra("ServicePhone", ServicePhone);
            intent.putExtra("go_path", go_path);
            toActivity(intent);
            //加入activity管理
            FetalHeartRecordActivity.fetalHeart_Record_lists.add(FetalHeartRecordActivity.this);
        } else if (lists.get(position).getStatus() == 0) {
            Intent intent = new Intent(FetalHeartRecordActivity.this, ReadingReportActivity.class);
            intent.putExtra("id", lists.get(position).getId());
            intent.putExtra("status", 0);
            toActivity(intent);
        } else if (lists.get(position).getStatus() == 1) {
            Intent intent = new Intent(FetalHeartRecordActivity.this, ReadingReportActivity.class);
            intent.putExtra("id", lists.get(position).getId());
            intent.putExtra("status", 1);
            toActivity(intent);
        }
    }

    private void RecordItemRightArrow(final int position) {

        if (lists.get(position).getStatus() == -1) {
            if (lists.get(position).getTLong() < 20 * 60) {
                Toast.makeText(FetalHeartRecordActivity.this, getResources().getString(R.string.monitor_detail_time_low_no_ask_doctor_string), Toast.LENGTH_SHORT).show();
                return;
            }
            Map<String, String> paramsMap = new HashMap<>();
            paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
            paramsMap.put("MonitorRecordId", lists.get(position).getId() + "");
            mMyOkhttp.post().url(HttpUrl.AskCheck).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<AskCheckEntity>() {
                @Override
                public void onSuccess(int statusCode, AskCheckEntity response) {
                    if (response.getCode() == 0) {
                        new CommomDialog(FetalHeartRecordActivity.this, R.style.MyDialogStyle, getResources().getString(R.string.fetal_heart_ask_doctor_tip_string), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.cancel();
                                if (confirm) {
                                    Map<String, String> paramsMap = new HashMap<>();
                                    paramsMap.put("AccountId", String.valueOf(SpUtils.getInt(Contants.AccountId,0)));
                                    paramsMap.put("MonitorRecordId", lists.get(position).getId() + "");
                                    mMyOkhttp.post().url(HttpUrl.SubmitAsk).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<SubmitAskEntity>() {
                                        @Override
                                        public void onSuccess(int statusCode, SubmitAskEntity response) {
                                            if (response.getCode() == 0) {
                                                Intent intent = new Intent(FetalHeartRecordActivity.this, RequestResultsActivity.class);
                                                intent.putExtra("ServicePhone", ServicePhone);
                                                intent.putExtra("go_path", go_path);
                                                toActivity(intent);
                                                FetalHeartRecordActivity.this.finish();
                                            }
                                        }

                                        @Override
                                        public void onFailures(int statusCode, String errorMsg) {
                                            CommonMethod.requestError(statusCode, errorMsg);
                                        }
                                    });

                                }
                            }
                        }).show();
                    } else if (response.getCode() == 1) {
                        Toast.makeText(FetalHeartRecordActivity.this, getResources().getString(R.string.fetal_heart_service_out_date_string), Toast.LENGTH_SHORT).show();
                    } else if (response.getCode() == 2) {
                        Toast.makeText(FetalHeartRecordActivity.this, getResources().getString(R.string.fetal_heart_no_service_string), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });

        } else if (lists.get(position).getStatus() == 0) {
            Intent intent = new Intent(FetalHeartRecordActivity.this, ReadingReportActivity.class);
            intent.putExtra("id", lists.get(position).getId());
            intent.putExtra("status", 0);
            toActivity(intent);
        } else if (lists.get(position).getStatus() == 1) {
            Intent intent = new Intent(FetalHeartRecordActivity.this, ReadingReportActivity.class);
            intent.putExtra("id", lists.get(position).getId());
            intent.putExtra("status", 1);
            toActivity(intent);
        }
    }

    @Override
    protected void otherViewClick(View view) {

    }


}
