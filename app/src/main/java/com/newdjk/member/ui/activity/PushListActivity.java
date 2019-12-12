package com.newdjk.member.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.adapter.PushListAdapter;
import com.newdjk.member.ui.entity.PushDataDaoEntity;
import com.newdjk.member.utils.SQLiteUtils;
import com.newdjk.member.utils.SpUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PushListActivity extends BasicActivity {
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
    @BindView(R.id.message_recycler_view)
    RecyclerView messageRecyclerView;
    @BindView(R.id.iv_no)
    ImageView ivNo;
    @BindView(R.id.mNodataContainer)
    RelativeLayout mNodataContainer;
    private String mId;
    private Gson mGson;
    private String mAction;

    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private PushListAdapter mPushListAdapter;

    @Override
    protected int initViewResId() {
        return R.layout.push_list;
    }

    @Override
    protected void initView() {
        topTitle.setTextColor(getResources().getColor(R.color.white));

        mId = String.valueOf(SpUtils.getInt(Contants.AccountId,0));
        mGson = new Gson();
        mAction = getIntent().getStringExtra("action");

        if (mAction != null && mAction.equals("system")) {
            initBackTitle("系统消息");
        } else if (mAction != null && mAction.equals("order")) {
            initBackTitle("订单提醒");
        } else if (mAction != null && mAction.equals("service")) {
            initBackTitle("服务提醒");
        } else if (mAction != null && mAction.equals("precription")) {
            initBackTitle("处方提醒");
        } else if(mAction !=null && mAction.equals("follow")) {
            initBackTitle("随访提醒");
        }
        topLeft.setImageResource(R.drawable.head_back_white_s);
        topLeft.setVisibility(View.VISIBLE);
        List<PushDataDaoEntity> timMessageList = new ArrayList<>();
        mPushListAdapter = new PushListAdapter(timMessageList, this,mAction);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        messageRecyclerView.setAdapter(mPushListAdapter);
        messageRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        getPushLish(mAction);
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    private void getPushLish(String action) {
        List<PushDataDaoEntity> orderList = new ArrayList<>();
        List<PushDataDaoEntity> serviceList = new ArrayList<>();
        List<PushDataDaoEntity> precriptionList = new ArrayList<>();
        List<PushDataDaoEntity> followList = new ArrayList<>();
        List<PushDataDaoEntity> systemList = new ArrayList<>();
        List<PushDataDaoEntity> pushList = SQLiteUtils.getInstance().selectAllContactsByDoctorId(mId);
        for (int i = 0; i < pushList.size(); i++) {
            PushDataDaoEntity pushDataDaoEntity = pushList.get(i);
            int msgType = pushDataDaoEntity.getMsgType();
            switch (msgType) {
                case 1:
                    orderList.add(pushDataDaoEntity);
                    break;
                case 2:
                    serviceList.add(pushDataDaoEntity);
                    break;
                case 3:
                    precriptionList.add(pushDataDaoEntity);
                    break;
                case 4:
                    followList.add(pushDataDaoEntity);
                    break;
                case 5:
                    systemList.add(pushDataDaoEntity);
                    break;
            }
        }
        if (action.equals("order")) {
            mPushListAdapter.resetData(orderList);
        } else if (action.equals("service")) {
            mPushListAdapter.resetData(serviceList);
        } else if (action.equals("precription")) {
            mPushListAdapter.resetData(precriptionList);
        } else if (action.equals("follow")) {
            mPushListAdapter.resetData(followList);
        } else {
            mPushListAdapter.resetData(systemList);
        }
    }
}
