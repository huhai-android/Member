package com.newdjk.member.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.FetalHeartMonitorMineActivity;
import com.newdjk.member.ui.activity.MoreFunctionActivity;
import com.newdjk.member.ui.activity.min.PersonalDataActivity;
import com.newdjk.member.ui.activity.min.SystemSettingActivity;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.ui.entity.PaintListEntity;
import com.newdjk.member.ui.entity.PatientEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.GlideMediaLoader;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.CircleImageView;
import com.newdjk.member.views.ObservableScrollView;
import com.newdjk.member.views.PageIndicator;
import com.newdjk.member.views.ViewPagerForScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 个人中心.
 */
public class MinFragment extends BasicFragment implements ObservableScrollView.ScrollViewListener {

    @BindView(R.id.mHeadContainer)
    LinearLayout mHeadContainer;
    @BindView(R.id.mOrderPic)
    LinearLayout mOrderPic;
    @BindView(R.id.mOrderVideo)
    LinearLayout mOrderVideo;
    @BindView(R.id.mOrderContinuation)
    LinearLayout mOrderContinuation;
    @BindView(R.id.mNurseConsult)
    LinearLayout mNurseConsult;
    @BindView(R.id.mNurseRemote)
    LinearLayout mNurseRemote;
    @BindView(R.id.mOrderService)
    LinearLayout mOrderService;
    @BindView(R.id.mSecond)
    LinearLayout mSecond;
    @BindView(R.id.mNurseRange)
    LinearLayout mNurseRange;
    @BindView(R.id.mOrderPre)
    LinearLayout mOrderPre;
    @BindView(R.id.mZhongyaoOrder)
    LinearLayout mZhongyaoOrder;
    @BindView(R.id.mcheckTable)
    LinearLayout mcheckTable;
    @BindView(R.id.lvVipOrder)
    LinearLayout lvVipOrder;
    @BindView(R.id.lvYuanchengOrder)
    LinearLayout lvYuanchengOrder;
    @BindView(R.id.lv_adviceOrder)
    LinearLayout lvAdviceOrder;
    @BindView(R.id.lv_faceOrder)
    LinearLayout lvFaceOrder;
    @BindView(R.id.lv_zhuankeOrder)
    LinearLayout lvZhuankeOrder;
    @BindView(R.id.mFarMenzhenOrder)
    LinearLayout mFarMenzhenOrder;
    @BindView(R.id.myDortor)
    LinearLayout myDortor;
    @BindView(R.id.myFollow)
    LinearLayout myFollow;
    @BindView(R.id.mVisitPerson)
    LinearLayout mVisitPerson;
    @BindView(R.id.myPrescription)
    LinearLayout myPrescription;
    @BindView(R.id.mFetalHeart)
    LinearLayout mFetalHeart;
    @BindView(R.id.mEvaluate)
    LinearLayout mEvaluate;
    @BindView(R.id.mFollowupPlan)
    LinearLayout mFollowupPlan;
    @BindView(R.id.mCoupons)
    LinearLayout mCoupons;
    @BindView(R.id.mAddressManager)
    LinearLayout mAddressManager;
    @BindView(R.id.mCase)
    LinearLayout mCase;
    @BindView(R.id.ll_social)
    LinearLayout llSocial;
    @BindView(R.id.mSecondDiagnosis)
    LinearLayout mSecondDiagnosis;
    @BindView(R.id.mdoctorgroup)
    LinearLayout mdoctorgroup;
    @BindView(R.id.mZhuanzhenrecode)
    LinearLayout mZhuanzhenrecode;
    @BindView(R.id.headicon)
    CircleImageView headicon;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.mSystemSetting)
    ImageView mSystemSetting;
    @BindView(R.id.patientViewpager)
    ViewPagerForScrollView patientViewpager;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;
    @BindView(R.id.scrollview)
    ObservableScrollView scrollview;
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
    @BindView(R.id.help_center)
    LinearLayout helpCenter;
    @BindView(R.id.lv_more)
    LinearLayout lvMore;
    private List<PaintListEntity.DataBean> patientlist = new ArrayList<>();
    public static String mSelfAvatarImagePath;
    private int imageHeight;
    private PagerAdapter mdapter;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public static MinFragment getFragment() {
        return new MinFragment();
    }

    @Override
    protected int initViewResId() {
        return R.layout.fragment_min;
    }

    @Override
    protected void initView() {

        mOrderVideo.setVisibility(View.VISIBLE);
//            empty.setVisibility(View.VISIBLE);

        liearTitlebar.setBackgroundResource(R.color.transparent);
        topTitle.setText("个人中心");
        topTitle.setTextColor(getResources().getColor(R.color.white));
    }

    @Override
    protected void initListener() {
        // 获取顶部图片高度后，设置滚动监听
        ViewTreeObserver vto = mHeadContainer.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mHeadContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                imageHeight = mHeadContainer.getHeight();
                scrollview.setScrollViewListener(MinFragment.this);
            }
        });

    }

    @Override
    protected void initData() {
        requestMineInfoData();
        //获取就诊人信息
        obtainPaintList();
    }

    /**
     * 请求个人中心的数据
     */
    private void requestMineInfoData() {
        String mAccountId = String.valueOf(SpUtils.getInt(Contants.AccountId, 0));
        String mUrl = HttpUrl.QuerySelfPatientByAccountId + "?AccountId=" + mAccountId;
        Log.d("url", mUrl);
        mMyOkhttp.get().url(mUrl).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<PatientEntity>() {
            @Override
            public void onSuccess(int statusCode, PatientEntity entituy) {
                PatientEntity.DataBean data = entituy.getData();
                if (data != null) {
                    tvName.setText(data.getPatientName());

                    mSelfAvatarImagePath = data.getPicturePath();
                    GlideMediaLoader.loadPhoto(mContext, headicon, mSelfAvatarImagePath);
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });
    }

    @Override
    protected void otherViewClick(View view) {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.mNurseRemote, R.id.mSecondDiagnosis,
            R.id.ll_social, R.id.myFollow, R.id.mNurseConsult,
            R.id.mNurseRange, R.id.mFetalHeart, R.id.mOrderService,
            R.id.mOrderPre, R.id.mOrderContinuation, R.id.mOrderVideo,
            R.id.mOrderPic, R.id.myPrescription, R.id.mCase, R.id.mCoupons,
            R.id.mVisitPerson, R.id.mEvaluate, R.id.headicon, R.id.mSystemSetting,
            R.id.mAddressManager, R.id.myDortor, R.id.mFollowupPlan, R.id.mSecond,
            R.id.mcheckTable, R.id.mdoctorgroup, R.id.mZhuanzhenrecode,
            R.id.mZhongyaoOrder, R.id.lvVipOrder, R.id.lvYuanchengOrder, R.id.lv_adviceOrder,
            R.id.lv_faceOrder, R.id.lv_zhuankeOrder,R.id.help_center,R.id.lv_more
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myFollow://我的关注
                Intent likeIntent = new Intent(getContext(), WebViewActivity.class);
                likeIntent.putExtra("type", WebUtil.like);
                toActivity(likeIntent);
                break;
            case R.id.mNurseConsult:
                Intent intent30 = new Intent(getContext(), WebViewActivity.class);
                intent30.putExtra("type", 30);
                toActivity(intent30);
                break;
            case R.id.mNurseRange:
                Intent intent31 = new Intent(getContext(), WebViewActivity.class);
                intent31.putExtra("type", 31);
                toActivity(intent31);
                break;
            case R.id.headicon:
                toActivity(new Intent(getContext(), PersonalDataActivity.class));
                break;
            case R.id.mSystemSetting:
                toActivity(new Intent(getContext(), SystemSettingActivity.class));
                break;
            case R.id.mAddressManager:
                Intent mAddressIntent = new Intent(getContext(), WebViewActivity.class);
                mAddressIntent.putExtra("type", 12);
                toActivity(mAddressIntent);
                break;
            case R.id.myDortor:
                Intent myDoctorIntent = new Intent(getContext(), WebViewActivity.class);
                myDoctorIntent.putExtra("type", 6);
                toActivity(myDoctorIntent);
                break;
            case R.id.mFollowupPlan:
                Intent mFollowupPlanIntent = new Intent(getContext(), WebViewActivity.class);
                mFollowupPlanIntent.putExtra("type", 10);
                toActivity(mFollowupPlanIntent);
                break;
            case R.id.mEvaluate:
                Intent mEvaluateIntent = new Intent(getContext(), WebViewActivity.class);
                mEvaluateIntent.putExtra("type", 9);
                toActivity(mEvaluateIntent);
                break;
            case R.id.mVisitPerson:
                Intent mVisitPersonIntent = new Intent(getContext(), WebViewActivity.class);
                mVisitPersonIntent.putExtra("type", 7);
                toActivity(mVisitPersonIntent);
                break;
            case R.id.mCoupons:
                Intent mCouponsIntent = new Intent(getContext(), WebViewActivity.class);
                mCouponsIntent.putExtra("type", 11);
                toActivity(mCouponsIntent);
                break;
            case R.id.mCase:
                Intent mCaseIntent = new Intent(getContext(), WebViewActivity.class);
                mCaseIntent.putExtra("type", 13);
                toActivity(mCaseIntent);
                break;
            case R.id.myPrescription:
                Intent myPrescriptionIntent = new Intent(getContext(), WebViewActivity.class);
                myPrescriptionIntent.putExtra("type", 8);
                toActivity(myPrescriptionIntent);
                break;
            case R.id.mOrderPic:
                Intent mOrderPicIntent = new Intent(getContext(), WebViewActivity.class);
                mOrderPicIntent.putExtra("type", 1);
                toActivity(mOrderPicIntent);
                break;
            case R.id.mOrderVideo:
                Intent mOrderVideoIntent = new Intent(getContext(), WebViewActivity.class);
                mOrderVideoIntent.putExtra("type", 2);
                toActivity(mOrderVideoIntent);
                break;
            case R.id.mOrderContinuation:
                Intent mOrderContinuationIntent = new Intent(getContext(), WebViewActivity.class);
                mOrderContinuationIntent.putExtra("type", 3);
                toActivity(mOrderContinuationIntent);
                break;
            case R.id.mOrderPre:
                Intent mOrderPreIntent = new Intent(getContext(), WebViewActivity.class);
                mOrderPreIntent.putExtra("type", 4);
                toActivity(mOrderPreIntent);
                break;
            case R.id.mOrderService:
                Intent mOrderServiceIntent = new Intent(getContext(), WebViewActivity.class);
                mOrderServiceIntent.putExtra("type", 5);
                toActivity(mOrderServiceIntent);
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
                                Toast.makeText(getActivity(), getResources().getString(R.string.fetal_heart_monitor_unregister_tip_string), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            Intent mFetalHeartIntent = new Intent(getContext(), FetalHeartMonitorMineActivity.class);
                            mFetalHeartIntent.putExtra("Code", response.getCode());
                            mFetalHeartIntent.putExtra("Patient", response.getData().getPatient());
                            mFetalHeartIntent.putExtra("ServicePhone", response.getData().getServicePhone());
                            toActivity(mFetalHeartIntent);
                        } else {
//                            Toast.makeText(getActivity(), getResources().getString(R.string.fetal_heart_monitor_unregister_tip_string), Toast.LENGTH_SHORT).show();
                            Toast.makeText(getActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
                break;
            case R.id.ll_social:
                Intent socialIntent = new Intent(getContext(), WebViewActivity.class);
                socialIntent.putExtra("type", WebUtil.mySocial);
                toActivity(socialIntent);
                break;
            case R.id.mSecondDiagnosis:
                Intent secondIntent = new Intent(getContext(), WebViewActivity.class);
                secondIntent.putExtra("type", WebUtil.mineSecondReportList);
                toActivity(secondIntent);
                break;

            case R.id.mSecond:
                Intent secondSuggestIntent = new Intent(getContext(), WebViewActivity.class);
                secondSuggestIntent.putExtra("type", WebUtil.mineSecondSuggest);
                toActivity(secondSuggestIntent);
                break;
            case R.id.mNurseRemote:
                Intent healthGuide = new Intent(getContext(), WebViewActivity.class);
                healthGuide.putExtra("type", 31);
                toActivity(healthGuide);
                break;

            case R.id.mcheckTable:
                Intent check = new Intent(getContext(), WebViewActivity.class);
                check.putExtra("type", WebUtil.checklist);
                toActivity(check);

                break;
            case R.id.mdoctorgroup:
                Intent doctorgroup = new Intent(getContext(), WebViewActivity.class);
                doctorgroup.putExtra("type", WebUtil.doctorgroup);
                toActivity(doctorgroup);

                break;

            case R.id.mZhuanzhenrecode:
                Intent zhuanzhen = new Intent(getContext(), WebViewActivity.class);
                zhuanzhen.putExtra("type", WebUtil.zhuanzhenrecode);
                toActivity(zhuanzhen);

                break;

            case R.id.mZhongyaoOrder:
                Intent zhongyaoList = new Intent(getContext(), WebViewActivity.class);
                zhongyaoList.putExtra("type", WebUtil.zhongyaoDingdanList);
                toActivity(zhongyaoList);

                break;

            case R.id.lvVipOrder:
                Intent viporderList = new Intent(getContext(), WebViewActivity.class);
                viporderList.putExtra("type", WebUtil.vipYuyuerecode);
                toActivity(viporderList);

                break;
            //远程门诊
            case R.id.lvYuanchengOrder:
                Intent farorderList = new Intent(getContext(), WebViewActivity.class);
                farorderList.putExtra("type", WebUtil.farOrderList);
                toActivity(farorderList);

                break;
            case R.id.lv_adviceOrder:
                Intent orderList = new Intent(getContext(), WebViewActivity.class);
                orderList.putExtra("type", WebUtil.adviceOrderList);
                toActivity(orderList);
                break;

            case R.id.lv_faceOrder:
                Intent faceorderList = new Intent(getContext(), WebViewActivity.class);
                faceorderList.putExtra("type", WebUtil.faceOrderList);
                toActivity(faceorderList);
                break;
            case R.id.lv_zhuankeOrder:
                Intent zhuankeorderList = new Intent(getContext(), WebViewActivity.class);
                zhuankeorderList.putExtra("type", WebUtil.zhuankeZhuanzhenRecode);
                toActivity(zhuankeorderList);

                break;

            case R.id.help_center:
                toast("帮助中心");
                break;

            case R.id.lv_more:
                Intent more = new Intent(getContext(), MoreFunctionActivity.class);
                toActivity(more);
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.UPDATE_PHOTO:
                requestMineInfoData();
                break;
        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        // TODO Auto-generated method stub
        // Log.i("TAG", "y--->" + y + "    height-->" + height);
        if (y <= 0) {
            liearTitlebar.setBackgroundColor(Color.argb((int) 0, 42, 105, 207));//AGB由相关工具获得，或者美工提供
        } else if (y > 0 && y <= imageHeight) {
            float scale = (float) y / imageHeight;
            float alpha = (255 * scale);
            // 只是layout背景透明(仿知乎滑动效果)
            liearTitlebar.setBackgroundColor(Color.argb((int) alpha, 42, 105, 207));
        } else {
            liearTitlebar.setBackgroundColor(Color.argb((int) 255, 42, 105, 207));
        }
    }

    private void obtainPaintList() {
        String url = HttpUrl.QueryPatientVisitByAccountId + "?AccountId=" + SpUtils.getInt(Contants.AccountId, 0);
        mMyOkhttp.get().url(url).tag(this).headers(HeadUitl.instance.getHeads()).enqueue(new GsonResponseHandler<PaintListEntity>() {
            @Override
            public void onSuccess(int statusCode, PaintListEntity response) {
                if (response.getCode() != 0) {
                    toast(response.getMessage());
                    return;
                }
                patientlist.clear();
                patientlist.addAll(response.getData());
                mdapter = new PagerAdapter(getFragmentManager());
                initviewpager();

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                mdapter = new PagerAdapter(getFragmentManager());
                initviewpager();
            }
        });
    }

    private void initviewpager() {
        patientViewpager.setAdapter(mdapter);
        patientViewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });

        patientViewpager.setCurrentItem(0, false);
        mdapter.notifyDataSetChanged();
        if (patientlist.size() + 1 == 1) {
            dotHorizontal.setVisibility(View.GONE);
        } else {
            patientViewpager.addOnPageChangeListener(new PageIndicator(getContext(), dotHorizontal, patientlist.size() + 1));

        }

    }


    private class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Log.d(TAG,"数据长度"+listuse.size());
            PatientContentFragment patientContentFragment = new PatientContentFragment();
            patientContentFragment.setdata(patientlist, position);
            return patientContentFragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            PatientContentFragment fragment = (PatientContentFragment) super.instantiateItem(container, position);

            return fragment;
        }


        @Override
        public int getCount() {
            return patientlist.size() + 1;

        }

        @Override
        public int getItemPosition(Object object) {
            //注意：默认是PagerAdapter.POSITION_UNCHANGED，不会重新加载
            return PagerAdapter.POSITION_NONE;
        }


    }
}
