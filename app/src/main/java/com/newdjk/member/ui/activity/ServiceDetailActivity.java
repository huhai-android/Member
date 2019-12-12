package com.newdjk.member.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.ui.adapter.ServiceContentAdapter;
import com.newdjk.member.ui.adapter.ServiceEvaluateAdapter;
import com.newdjk.member.ui.adapter.ServiceTeamAdapter;
import com.newdjk.member.ui.entity.GetContactInfoEntity;
import com.newdjk.member.ui.entity.GetReadDoctorListEntity;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.ui.entity.QueryServicePackAndDetailByPackIdEntity;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.views.RecycleViewDivider;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServiceDetailActivity extends BasicActivity {

    @BindView(R.id.buy_btn)
    Button buyBtn;
    @BindView(R.id.service_detail_picture_iv)
    ImageView serviceDetailPictureIv;

    @BindView(R.id.service_introduce_rl)
    RelativeLayout serviceIntroduceRl;
    @BindView(R.id.service_introduce_right_arrow)
    ImageView serviceIntroduceRightArrow;
    @BindView(R.id.service_introduce_ll)
    LinearLayout serviceIntroduceLl;
    @BindView(R.id.service_introduce_tv)
    TextView serviceIntroduceTv;

    @BindView(R.id.service_content_rl)
    RelativeLayout serviceContentRl;
    @BindView(R.id.service_content_right_arrow)
    ImageView serviceContentRightArrow;
    @BindView(R.id.service_content_ll)
    LinearLayout serviceContentLl;
    @BindView(R.id.service_content_recyclerview)
    RecyclerView serviceContentRecyclerview;

    @BindView(R.id.service_team_rl)
    RelativeLayout serviceTeamRl;
    @BindView(R.id.service_team_right_arrow)
    ImageView serviceTeamRightArrow;
    @BindView(R.id.service_team_ll)
    LinearLayout serviceTeamLl;
    @BindView(R.id.service_team_recyclerview)
    RecyclerView serviceTeamRecyclerview;

    @BindView(R.id.service_evaluate_recyclerview)
    RecyclerView serviceEvaluateRecyclerview;
    @BindView(R.id.pay_price_tv)
    TextView payPriceTv;

    private ServiceContentAdapter serviceContentAdapter;
    private ServiceTeamAdapter serviceTeamAdapter;
    private ServiceEvaluateAdapter serviceEvaluateAdapter;

    private HaveBindEntity.DataBean.PatientBean patientBean;
    private int OrgId;
    private int ServicePackId;
    private List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean> detailsBean;
    private int ServiceLong;
    private String EndTime;
    private double payPrice;

    private String go_path;

    private String service_package_text = "";
    @Override
    protected int initViewResId() {
        return R.layout.activity_service_detail;
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.service_detail_title_string));
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        ServicePackId = intent.getIntExtra("ServicePackId", 0);
        patientBean = (HaveBindEntity.DataBean.PatientBean) intent.getSerializableExtra("Patient");
        go_path = intent.getStringExtra("go_path");

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("ServicePackId", ServicePackId + "");
        mMyOkhttp.get().url(HttpUrl.QueryServicePackAndDetailByPackId).headers(HeadUitl.instance.getHeads()).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<QueryServicePackAndDetailByPackIdEntity>() {
            @Override
            public void onSuccess(int statusCode, final QueryServicePackAndDetailByPackIdEntity response) {
                if (response.getCode() == 0) {
                    //头部图片
                    Glide.with(ServiceDetailActivity.this).load(response.getData().getMasterPicture()).into(serviceDetailPictureIv);
                    //服务内容
                    serviceIntroduceTv.setText(response.getData().getDescription());
                    payPriceTv.setText(response.getData().getPrice() + "");

                    final List<QueryServicePackAndDetailByPackIdEntity.DataBean.DetailsBean> lists = response.getData().getDetails();
                    serviceContentAdapter = new ServiceContentAdapter(ServiceDetailActivity.this, lists);
                    serviceContentRecyclerview.setLayoutManager(new LinearLayoutManager(ServiceDetailActivity.this));
                    serviceContentRecyclerview.addItemDecoration(new RecycleViewDivider(ServiceDetailActivity.this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#999999")));
                    serviceContentRecyclerview.setAdapter(serviceContentAdapter);
                    serviceContentAdapter.setOnItemClickLitener(new ServiceContentAdapter.OnItemClickLitener() {
                        @Override
                        public void onItemClick(View view, final int position) {

                            final Dialog dialog = new Dialog(ServiceDetailActivity.this,R.style.MyDialog);
                            dialog.setContentView(R.layout.service_content_detail_dialog_layout);
                            final TextView service_title_tv = (TextView) dialog.findViewById(R.id.service_title_tv);
                            final TextView service_content_tv = (TextView) dialog.findViewById(R.id.service_content_tv);
                            Button ok_btn = (Button) dialog.findViewById(R.id.ok_btn);
                            ok_btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    service_package_text = "";
                                    dialog.cancel();
                                }
                            });
                            if (lists.get(position).getSericeItemCode().equals("1501")) {

                                Map<String, String> paramsMap = new HashMap<>();
                                paramsMap.put("orgId", response.getData().getOrgId() + "");
                                mMyOkhttp.get().url(HttpUrl.GetContactInfo).headers(HeadUitl.instance.getHeads()).params(paramsMap).tag(this).enqueue(new GsonResponseHandler<GetContactInfoEntity>() {
                                    @Override
                                    public void onSuccess(int statusCode, GetContactInfoEntity response_1501) {//(Code说明:0设备已绑定,(1未登记用户,)2未购买母胎服务包,3母胎服务包未支付,4已购服务包未绑定设备)*/
                                        if (response_1501.getCode() == 0) {
                                            service_title_tv.setText(response.getData().getDetails().get(position).getServiceItemName());
                                            service_content_tv.setText("设备名称：" + response.getData().getDetails().get(position).getServiceItemName() + "\n"
                                            + "设备押金：" + response.getData().getDetails().get(position).getTotalPrice() + "\n"
                                            + "领取地址：" + response_1501.getData().getAddress() + "\n"
                                            + "联系人：" + response_1501.getData().getName() + "\n"
                                            + "联系电话：" +  response_1501.getData().getMobile() + "\n"
                                            + "工作日时间：" + response_1501.getData().getWorkDay() + "\n"
                                            + "非工作日时间：" + response_1501.getData().getNonWorkDay()
                                            );
                                            dialog.show();
                                        }
                                    }

                                    @Override
                                    public void onFailures(int statusCode, String errorMsg) {
                                        CommonMethod.requestError(statusCode, errorMsg);
                                    }
                                });

                            } else {
                                service_title_tv.setText(response.getData().getServicePackName());
                                for (int i = 0;i < response.getData().getDetails().size();i++){
                                    if (!response.getData().getDetails().get(i).getSericeItemCode().equals("1501")) {
                                        service_package_text = service_package_text + response.getData().getDetails().get(i).getServiceItemName() + "(" + detailsBean.get(i).getNumValue() + detailsBean.get(i).getNumTypeName() + "):¥" + detailsBean.get(i).getTotalPrice() + "\n\n";
                                    }
                                }
                                service_content_tv.setText("服务套餐：" + response.getData().getServicePackName() + "\n\n" + service_package_text);
                                dialog.show();
                            }

                        }
                    });

                    List evaluate_lists = new ArrayList();
                    evaluate_lists.add("");
                    evaluate_lists.add("");
                    serviceEvaluateAdapter = new ServiceEvaluateAdapter(ServiceDetailActivity.this, evaluate_lists);
                    serviceEvaluateRecyclerview.setLayoutManager(new LinearLayoutManager(ServiceDetailActivity.this));
                    serviceEvaluateRecyclerview.addItemDecoration(new RecycleViewDivider(ServiceDetailActivity.this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#999999")));
                    serviceEvaluateRecyclerview.setAdapter(serviceEvaluateAdapter);

                    //用于生产订单接口
                    OrgId = response.getData().getOrgId();
                    ServicePackId = response.getData().getServicePackId();
                    detailsBean = response.getData().getDetails();
                    ServiceLong = response.getData().getServiceLong();
                    EndTime = response.getData().getEndTime();
                    payPrice = response.getData().getPrice();
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });

        mMyOkhttp.get().url(HttpUrl.GetReadDoctorList).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<GetReadDoctorListEntity>() {
            @Override
            public void onSuccess(int statusCode, GetReadDoctorListEntity response) {
                if (response.getCode() == 0) {
                    List<GetReadDoctorListEntity.DataBean> team_lists = response.getData();
                    serviceTeamAdapter = new ServiceTeamAdapter(ServiceDetailActivity.this, team_lists);
                    serviceTeamRecyclerview.setLayoutManager(new LinearLayoutManager(ServiceDetailActivity.this));
                    serviceTeamRecyclerview.addItemDecoration(new RecycleViewDivider(ServiceDetailActivity.this, LinearLayoutManager.HORIZONTAL, 1, Color.parseColor("#999999")));
                    serviceTeamRecyclerview.setAdapter(serviceTeamAdapter);
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

    private boolean service_introduce_boolean = true;
    private boolean service_content_boolean = true;
    private boolean servcie_team__boolean = true;

    @OnClick({R.id.buy_btn, R.id.service_introduce_rl, R.id.service_content_rl, R.id.service_team_rl})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.buy_btn:
                Intent intent = new Intent(ServiceDetailActivity.this, OrderNoteActivity.class);
                intent.putExtra("Patient", patientBean);
                intent.putExtra("OrgId", OrgId);
                intent.putExtra("ServicePackId", ServicePackId);
                intent.putExtra("detailsBean", (Serializable) detailsBean);
                intent.putExtra("ServiceLong", ServiceLong);
                intent.putExtra("EndTime", EndTime);
                intent.putExtra("payPrice", payPrice);
                intent.putExtra("go_path",go_path);
                toActivity(intent);
                //加入activity管理
                ServiceListActivity.activity_lists.add(ServiceDetailActivity.this);
                break;
            case R.id.service_introduce_rl:
                if (service_introduce_boolean) {
                    serviceIntroduceRightArrow.setBackgroundResource(R.mipmap.arrow_bottom);
                    serviceIntroduceLl.setVisibility(View.VISIBLE);
                    service_introduce_boolean = false;
                } else {
                    serviceIntroduceRightArrow.setBackgroundResource(R.mipmap.arrow_top);
                    serviceIntroduceLl.setVisibility(View.GONE);
                    service_introduce_boolean = true;
                }
                break;
            case R.id.service_content_rl:
                if (service_content_boolean) {
                    serviceContentRightArrow.setBackgroundResource(R.mipmap.arrow_bottom);
                    serviceContentLl.setVisibility(View.VISIBLE);
                    service_content_boolean = false;
                } else {
                    serviceContentRightArrow.setBackgroundResource(R.mipmap.arrow_top);
                    serviceContentLl.setVisibility(View.GONE);
                    service_content_boolean = true;
                }
                break;
            case R.id.service_team_rl:
                if (servcie_team__boolean) {
                    serviceTeamRightArrow.setBackgroundResource(R.mipmap.arrow_bottom);
                    serviceTeamLl.setVisibility(View.VISIBLE);
                    servcie_team__boolean = false;
                } else {
                    serviceTeamRightArrow.setBackgroundResource(R.mipmap.arrow_top);
                    serviceTeamLl.setVisibility(View.GONE);
                    servcie_team__boolean = true;
                }
                break;
        }
    }

}
