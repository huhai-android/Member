package com.newdjk.member.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.WebUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreFunctionActivity extends BasicActivity {


    @BindView(R.id.myDortor)
    LinearLayout myDortor;
    @BindView(R.id.myFollow)
    LinearLayout myFollow;
    @BindView(R.id.mVisitPerson)
    LinearLayout mVisitPerson;
    @BindView(R.id.mEvaluate)
    LinearLayout mEvaluate;
    @BindView(R.id.mAddressManager)
    LinearLayout mAddressManager;
    @BindView(R.id.help_center)
    LinearLayout helpCenter;
    @BindView(R.id.mCoupons)
    LinearLayout mCoupons;
    @BindView(R.id.mFetalHeart)
    LinearLayout mFetalHeart;
    @BindView(R.id.ll_social)
    LinearLayout llSocial;
    @BindView(R.id.mdoctorgroup)
    LinearLayout mdoctorgroup;
    @BindView(R.id.mZhuanzhenrecode)
    LinearLayout mZhuanzhenrecode;
    @BindView(R.id.mFollowupPlan)
    LinearLayout mFollowupPlan;
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


    @Override
    protected int initViewResId() {
        return R.layout.activity_morefunction;
    }

    @Override
    protected void initView() {
        initTitle("更多").setLeftImage(R.drawable.head_back_n).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        }).setTitleBgRes(R.color.white);
    }

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.white), 0);
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


    @OnClick({R.id.myDortor, R.id.myFollow, R.id.mVisitPerson, R.id.mEvaluate, R.id.mAddressManager, R.id.help_center, R.id.mCoupons, R.id.mFetalHeart, R.id.ll_social, R.id.mdoctorgroup, R.id.mZhuanzhenrecode, R.id.mFollowupPlan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myDortor:
                Intent myDoctorIntent = new Intent(this, WebViewActivity.class);
                myDoctorIntent.putExtra("type", 6);
                toActivity(myDoctorIntent);
                break;
            case R.id.myFollow:
                Intent likeIntent = new Intent(this, WebViewActivity.class);
                likeIntent.putExtra("type", WebUtil.like);
                toActivity(likeIntent);
                break;
            case R.id.mVisitPerson:
                Intent mVisitPersonIntent = new Intent(this, WebViewActivity.class);
                mVisitPersonIntent.putExtra("type", 7);
                toActivity(mVisitPersonIntent);
                break;
            case R.id.mEvaluate:
                Intent mEvaluateIntent = new Intent(this, WebViewActivity.class);
                mEvaluateIntent.putExtra("type", 9);
                toActivity(mEvaluateIntent);
                break;
            case R.id.mAddressManager:
                Intent mAddressIntent = new Intent(this, WebViewActivity.class);
                mAddressIntent.putExtra("type", 12);
                toActivity(mAddressIntent);
                break;
            case R.id.help_center:
                toast("帮助中心");
                break;
            case R.id.mCoupons:
                Intent mCouponsIntent = new Intent(this, WebViewActivity.class);
                mCouponsIntent.putExtra("type", 11);
                toActivity(mCouponsIntent);
                break;
            case R.id.mFetalHeart:
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("accountId", String.valueOf(SpUtils.getInt(Contants.AccountId, 0)));
                paramsMap.put("patientId", String.valueOf(SpUtils.getInt(Contants.Id, 0)));
                mMyOkhttp.get().url(HttpUrl.HaveBind).headers(HeadUitl.instance.getHeads()).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<HaveBindEntity>() {
                    @Override
                    public void onSuccess(int statusCode, HaveBindEntity response) {//(Code说明:0设备已绑定,(1未登记用户,)2未购买母胎服务包,3母胎服务包未支付,4已购服务包未绑定设备)*/

                        if (response.getCode() == 0 || response.getCode() == 4 || response.getCode() == 5) {
                            if (response.getData() == null) {
                                Toast.makeText(MoreFunctionActivity.this, getResources().getString(R.string.fetal_heart_monitor_unregister_tip_string), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent mFetalHeartIntent = new Intent(MoreFunctionActivity.this, FetalHeartMonitorMineActivity.class);
                            mFetalHeartIntent.putExtra("Code", response.getCode());
                            mFetalHeartIntent.putExtra("Patient", response.getData().getPatient());
                            mFetalHeartIntent.putExtra("ServicePhone", response.getData().getServicePhone());
                            toActivity(mFetalHeartIntent);
                        } else {
//                            Toast.makeText(getActivity(), getResources().getString(R.string.fetal_heart_monitor_unregister_tip_string), Toast.LENGTH_SHORT).show();
                            Toast.makeText(MoreFunctionActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
                break;
            case R.id.ll_social:
                Intent socialIntent = new Intent(this, WebViewActivity.class);
                socialIntent.putExtra("type", WebUtil.mySocial);
                toActivity(socialIntent);
                break;
            case R.id.mdoctorgroup:
                Intent doctorgroup = new Intent(this, WebViewActivity.class);
                doctorgroup.putExtra("type", WebUtil.doctorgroup);
                toActivity(doctorgroup);
                break;
            case R.id.mZhuanzhenrecode:
                Intent zhuanzhen = new Intent(this, WebViewActivity.class);
                zhuanzhen.putExtra("type", WebUtil.zhuanzhenrecode);
                toActivity(zhuanzhen);
                break;
            case R.id.mFollowupPlan:
                Intent mFollowupPlanIntent = new Intent(this, WebViewActivity.class);
                mFollowupPlanIntent.putExtra("type", 10);
                toActivity(mFollowupPlanIntent);
                break;
        }
    }


}
