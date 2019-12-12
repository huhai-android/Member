package com.newdjk.member.ui.fragment;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.listener.OnTabItemClickListener;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.BannerDetailActivity;
import com.newdjk.member.ui.activity.CityPickerActivity;
import com.newdjk.member.ui.activity.DeviceConnectActivity;
import com.newdjk.member.ui.activity.SearchActivity;
import com.newdjk.member.ui.activity.UserNoteActivity;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.adapter.FamousDoctorAdapter;
import com.newdjk.member.ui.entity.AdBannerInfo;
import com.newdjk.member.ui.entity.AppEntity;
import com.newdjk.member.ui.entity.AppLicationEntity;
import com.newdjk.member.ui.entity.CityEntity;
import com.newdjk.member.ui.entity.DoctorIdEntity;
import com.newdjk.member.ui.entity.FamousDoctorOrNurseEntity;
import com.newdjk.member.ui.entity.HaveBindEntity;
import com.newdjk.member.utils.AppUpdateUtils;
import com.newdjk.member.utils.ApplicationUtils;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.HomeUtil;
import com.newdjk.member.utils.HomeUtils;
import com.newdjk.member.utils.NetworkListener;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.GlideImageLoader;
import com.newdjk.member.views.PageIndicator;
import com.newdjk.member.views.PagerAdapter;
import com.newdjk.member.views.UpdateDialog;
import com.newdjk.member.views.ViewPagerForScrollView;
import com.newdjk.member.views.VpSwipeRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

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
import lib_zxing.activity.CaptureActivity;
import lib_zxing.activity.CodeUtils;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * 首页
 */
public class HomeFragment extends BasicFragment implements EasyPermissions.PermissionCallbacks, NetworkListener {

    private static final String TAG = "HomeFragment";
    @BindView(R.id.mBanner)
    Banner mBanner;
    //    @BindView(R.id.mOnlineConsulting)
//    LinearLayout mOnlineConsulting;
    @BindView(R.id.mVideoInterrogation)
    LinearLayout mVideoInterrogation;
    @BindView(R.id.mFamousMedicalService)
    LinearLayout mFamousMedicalService;
    @BindView(R.id.mNursingCounseling)
    LinearLayout mNursingCounseling;
    @BindView(R.id.mDistanceNursing)
    LinearLayout mDistanceNursing;
    @BindView(R.id.mSpecicalService)
    LinearLayout mSpecicalService;
    @BindView(R.id.mServicePackage)
    LinearLayout mServicePackage;
    @BindView(R.id.swipe_refresh_layout)
    VpSwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.mRecommendFamousDoctor)
    RecyclerView mRecommendFamousDoctor;
    @BindView(R.id.mRecommendFamousBurse)
    RecyclerView mRecommendFamousBurse;
    @BindView(R.id.mDoctorContainer)
    LinearLayout mDoctorContainer;
    @BindView(R.id.mNurseContainer)
    LinearLayout mNurseContainer;
    @BindView(R.id.mAdOne)
    ImageView mAdTwo;
    @BindView(R.id.mAdTwo)
    ImageView mAdOne;
    @BindView(R.id.mAdThree)
    ImageView mAdThree;
    @BindView(R.id.tv_left)
    TextView tvLeft;
    @BindView(R.id.top_right)
    ImageView topRight;
    @BindView(R.id.et_patient_msg)
    TextView etPatientMsg;
    @BindView(R.id.relat_titlebar)
    RelativeLayout relatTitlebar;
    @BindView(R.id.liear_titlebar)
    LinearLayout liearTitlebar;
    @BindView(R.id.mHotServiceContainer)
    LinearLayout mHotServiceContainer;
    @BindView(R.id.mFomusDoctor)
    ImageView mFomusDoctor;
    @BindView(R.id.mFetailHeart)
    ImageView mFetailHeart;
    Unbinder unbinder;
    @BindView(R.id.mIntroduce)
    ImageView mIntroduce;

    @BindView(R.id.lv_online)
    LinearLayout lvOnline;
    @BindView(R.id.customer_button)
    ImageView customer_button;
    @BindView(R.id.mGraphicInquiry)
    LinearLayout mGraphicInquiry;
    @BindView(R.id.mNurseRemote)
    LinearLayout mNurseRemote;
    @BindView(R.id.tabviewpager)
    ViewPagerForScrollView tabviewpager;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;
    @BindView(R.id.tabapp)
    LinearLayout tabapp;

    private FamousDoctorAdapter mFamousDoctorAdapter;
    private List<FamousDoctorOrNurseEntity.DataBean> mDoctorData;
    private List<FamousDoctorOrNurseEntity.DataBean> mNurseData;
    private FamousDoctorAdapter mFamousNurseAdapter;
    private AdBannerInfo.DataBean dataBean1;
    private AdBannerInfo.DataBean dataBean0;
    private AdBannerInfo.DataBean dataBean2;
    private String mApkUrl = "";
    private static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() / 1000 + "member.apk";
    List<AdBannerInfo.DataBean> bannerData = new ArrayList<>();
    List<AppLicationEntity> listuse = new ArrayList<>();

    /**
     * 请求CAMERA权限码
     */
    public static final int REQUEST_CAMERA_PERM = 101;
    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    private HomeUtil mHomeUtil;
    private UpdateDialog mDialog;
    private AlertDialog mDownloadDialog;
    private ProgressBar mDownloadProgress;
    private TextView mProgressText;
    private int mCurentPercent = 0;
    private PagerAdapter mtabAdapter;


    public static HomeFragment getFragment() {
        return new HomeFragment();
    }

    @Override
    protected int initViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            //恢复状态栏白色字体
            getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
//        Aria.download(this).register();
        EventBus.getDefault().register(this);
        mHomeUtil = new HomeUtil();
        initSwipeRefresh();
        initDoctorRecycleView();
        initNurseRecycleView();
        initviewpager();
        if (checkPermission()) {
            checkUpdate();
        }
    }

    private void initviewpager() {
        listuse.clear();
        listuse.addAll(ApplicationUtils.getListuse());
        mtabAdapter = new PagerAdapter(getActivity().getSupportFragmentManager(), listuse, new OnTabItemClickListener() {
            @Override
            public void onItemChildClick(AppLicationEntity appLicationEntity) {
                tabitemclick(appLicationEntity);
            }
        });
        tabviewpager.setAdapter(mtabAdapter);
        tabviewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabviewpager.setCurrentItem(position, false);
                HomeTabFragment homeTabFragment = (HomeTabFragment) mtabAdapter.getItem(position);
                homeTabFragment.setdata(listuse, position);
            }
        });
        if (listuse.size() <= 8) {
            dotHorizontal.setVisibility(View.GONE);
        } else {
            dotHorizontal.setVisibility(View.VISIBLE);
            tabviewpager.addOnPageChangeListener(new PageIndicator(getContext(), dotHorizontal, listuse.size() / 8 + 1));

        }
        tabviewpager.setCurrentItem(0, false);
        mtabAdapter.notifyDataSetChanged();
    }


    private void initSwipeRefresh() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initData();
            }
        });
    }

    private void initNurseRecycleView() {
        mRecommendFamousBurse.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        mNurseData = new ArrayList<>();
        mFamousNurseAdapter = new FamousDoctorAdapter(mNurseData);
        mFamousNurseAdapter.setType(2);
        mRecommendFamousBurse.setAdapter(mFamousNurseAdapter);
        mFamousNurseAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FamousDoctorOrNurseEntity.DataBean dataBean = mNurseData.get(position);
                Intent mOrderVideoIntent = new Intent(getContext(), WebViewActivity.class);
                mOrderVideoIntent.putExtra("type", 25);
                mOrderVideoIntent.putExtra("nurseId", dataBean.getDrId());
                toActivity(mOrderVideoIntent);
            }
        });
    }

    private void initDoctorRecycleView() {
        mRecommendFamousDoctor.setLayoutManager(new GridLayoutManager(getActivity(), 3, LinearLayoutManager.VERTICAL, false));
        mDoctorData = new ArrayList<>();
        mFamousDoctorAdapter = new FamousDoctorAdapter(mDoctorData);
        mFamousDoctorAdapter.setType(1);
        mRecommendFamousDoctor.setAdapter(mFamousDoctorAdapter);
        mFamousDoctorAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                FamousDoctorOrNurseEntity.DataBean dataBean = mDoctorData.get(position);
                Intent mOrderVideoIntent = new Intent(getContext(), WebViewActivity.class);
                mOrderVideoIntent.putExtra("type", 14);
                mOrderVideoIntent.putExtra("id", dataBean.getDrId());
                toActivity(mOrderVideoIntent);
            }
        });
    }

    @Override
    protected void initListener() {
        tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toActivity(new Intent(getContext(), CityPickerActivity.class));
            }
        });
//        topRight.setOnClickListener(this);

        //在线客服
        customer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("type", WebUtil.onlinecustmer);
                intent.putExtra("Name", SpUtils.getString(Contants.Name));
                intent.putExtra("AccountId", SpUtils.getInt(Contants.AccountId, -1));
                intent.putExtra("Sex", SpUtils.getInt(Contants.Sex, -1));
                intent.putExtra("Mobile", SpUtils.getString(Contants.Mobile));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {
        initBannerData();
        initHotServiceData();
        initDoctoreData();
        initNurseData();
    }

    /**
     * 获取首页的banner数据
     */
    private void initBannerData() {
        String bannerUrl = HttpUrl.QueryAdBannerInfo + "?classId=" + 3 + "";
        mMyOkhttp.get().url(bannerUrl).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<AdBannerInfo>() {
            @Override
            public void onSuccess(int statusCode, AdBannerInfo entituy) {
                bannerData = entituy.getData();
                if (bannerData == null || bannerData.size() == 0) {
                    return;
                }


                List<String> images = new ArrayList<>();
                for (AdBannerInfo.DataBean datum : bannerData) {
                    images.add(datum.getContent());
                }
                try {
                    dealBannerData(images);
                }catch (Exception e){

                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });

    }

    private void dealBannerData(List<String> images) {
        if (images == null || images.size() == 0) {
            return;
        }
        //设置banner样式
//                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(images);
        //设置banner动画效果
        mBanner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        //mBanner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        mBanner.isAutoPlay(true);
        //设置轮播时间
        mBanner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        mBanner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用

        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Log.d("position", position + "===========");
                AdBannerInfo.DataBean dataBean = bannerData.get(position);

                //内部链接
                if (!TextUtils.isEmpty(bannerData.get(position).getContentLink())){
                    Intent urlIntent = new Intent(getContext(), WebViewActivity.class);
                    urlIntent.putExtra("type", WebUtil.innerUrl);
                    urlIntent.putExtra("url", dataBean.getContentLink());
                    toActivity(urlIntent);
                //外部链接
                }else {
                    Intent intent = new Intent(getContext(), BannerDetailActivity.class);
                    intent.putExtra("banner", bannerData.get(position).getLinkContent());
                    intent.putExtra("bannerInfo", bannerData.get(position));
                    startActivity(intent);

                }

//                String s = images.get(position);
            }
        });
        mBanner.start();
    }

    /**
     * 获取热门服务数据
     */
    private void initHotServiceData() {
        String bannerUrl = HttpUrl.QueryAdBannerInfo + "?classId=" + 5 + "";
        mMyOkhttp.get().url(bannerUrl).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<AdBannerInfo>() {
            @Override
            public void onSuccess(int statusCode, AdBannerInfo entituy) {
                List<AdBannerInfo.DataBean> data = entituy.getData();
                handHotServiceData(data);
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
            }
        });

    }

    /**
     * 热门服务数据的处理
     *
     * @param data
     */
    private void handHotServiceData(List<AdBannerInfo.DataBean> data) {
        if (data != null && data.size() == 3) {
            dataBean0 = data.get(0);
            dataBean1 = data.get(1);
            dataBean2 = data.get(2);
            Glide.with(getActivity()).load(dataBean0.getContent()).into(mAdOne);
            Glide.with(getActivity()).load(dataBean1.getContent()).into(mAdTwo);
            Glide.with(getActivity()).load(dataBean2.getContent()).into(mAdThree);
        } else {
            return;
        }

    }

    /**
     * 点击banner跳转到制定的网页
     *
     * @param contentLink
     */
    private void toWeb(String contentLink) {
        Intent intent = new Intent(Intent.ACTION_VIEW);    //为Intent设置Action属性
        intent.setData(Uri.parse(contentLink)); //为Intent设置DATA属性
        startActivity(intent);
    }

    private void initDoctoreData() {
        String path = HttpUrl.QueryDoctorInfoForHot + "?HosGroupId=" + 0 + "&OrgId=" + 0 + "&DrType=" + 1;
        mMyOkhttp.get().url(path).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<FamousDoctorOrNurseEntity>() {
            @Override
            public void onSuccess(int statusCode, FamousDoctorOrNurseEntity entituy) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                List<FamousDoctorOrNurseEntity.DataBean> data = entituy.getData();
                if (data == null || data.size() == 0) {
                    mDoctorContainer.setVisibility(View.GONE);
                } else {
                    mDoctorContainer.setVisibility(View.VISIBLE);
                    if (data.size() > 6) {
                        data = data.subList(0, 6);
                    }
                    mDoctorData = data;
                    mFamousDoctorAdapter.setNewData(mDoctorData);
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void initNurseData() {
        String path = HttpUrl.QueryDoctorInfoForHot + "?HosGroupId=" + 0 + "&OrgId=" + 0 + "&DrType=" + 2 + "";
        mMyOkhttp.get().url(path).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<FamousDoctorOrNurseEntity>() {
            @Override
            public void onSuccess(int statusCode, FamousDoctorOrNurseEntity entituy) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                List<FamousDoctorOrNurseEntity.DataBean> dataBeanList = entituy.getData();
                if (dataBeanList == null || dataBeanList.size() == 0) {
                    mNurseContainer.setVisibility(View.GONE);
                } else {
                    mNurseContainer.setVisibility(View.VISIBLE);
                    if (dataBeanList.size() > 6) {
                        dataBeanList = dataBeanList.subList(0, 6);
                    }
                    mNurseData = dataBeanList;
                    mFamousNurseAdapter.setNewData(mNurseData);
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                CommonMethod.requestError(statusCode, errorMsg);

            }
        });
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @OnClick({R.id.mNurseRemote, R.id.mIntroduce, R.id.top_right, R.id.mFomusDoctor, R.id.mFetailHeart, R.id.et_patient_msg, R.id.mAdOne, R.id.mAdTwo, R.id.mAdThree, R.id.mNurseContainer, R.id.mDoctorContainer, R.id.mGraphicInquiry, R.id.mVideoInterrogation, R.id.mFamousMedicalService, R.id.mNursingCounseling, R.id.mDistanceNursing, R.id.mServicePackage, R.id.mSpecicalService, R.id.lv_online})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mFetailHeart:
                int id = SpUtils.getInt(Contants.Id, 0);
                int accountId = SpUtils.getInt(Contants.AccountId, 0);
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("accountId", accountId + "");
                paramsMap.put("patientId", id + "");
                mMyOkhttp.get().url(HttpUrl.HaveBind).params(paramsMap).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<HaveBindEntity>() {
                    @Override
                    public void onSuccess(int statusCode, HaveBindEntity response) {//(Code说明:0设备已绑定,(1未登记用户,)2未购买母胎服务包,3母胎服务包未支付,4已购服务包未绑定设备)*/
                        Intent mDoctorIntent10 = null;
                        if (response.getCode() == 0) {//服务包，设备，病人信息
                            mDoctorIntent10 = new Intent(getContext(), DeviceConnectActivity.class);
                            mDoctorIntent10.putExtra("Code", response.getCode());
                            if (response.getData() != null) {
                                //用于上传数据
                                mDoctorIntent10.putExtra("DeviceNo", response.getData().getDeviceNo());
                                mDoctorIntent10.putExtra("Patient", response.getData().getPatient());
                                //用于问医之后的
                                mDoctorIntent10.putExtra("ServicePhone", response.getData().getServicePhone());
                            }

                        } else if (response.getCode() == 1) {
                            mDoctorIntent10 = new Intent(getContext(), UserNoteActivity.class);
                        } else if (response.getCode() == 2 || response.getCode() == 5) {//人绑定，服务包没有，设备没有    5表示的是最近有绑定 只是到期了
                            mDoctorIntent10 = new Intent(getContext(), DeviceConnectActivity.class);
                            mDoctorIntent10.putExtra("Code", response.getCode());
                            if (response.getData() != null) {
                                mDoctorIntent10.putExtra("Patient", response.getData().getPatient());
                            }

                        } else if (response.getCode() == 3) {//人绑定，设备没有，服务包没有支付
                            mDoctorIntent10 = new Intent(getContext(), DeviceConnectActivity.class);
                            mDoctorIntent10.putExtra("Code", response.getCode());
                            if (response.getData() != null) {
                                mDoctorIntent10.putExtra("Patient", response.getData().getPatient());
                                mDoctorIntent10.putExtra("ServicePackId", response.getData().getServicePackId());
                            }
                        } else if (response.getCode() == 4) {//人绑定，设备没有，服务有*/
                            mDoctorIntent10 = new Intent(getContext(), DeviceConnectActivity.class);
                            mDoctorIntent10.putExtra("Code", response.getCode());
                            if (response.getData() != null) {
                                mDoctorIntent10.putExtra("Patient", response.getData().getPatient());
                                mDoctorIntent10.putExtra("ServicePhone", response.getData().getServicePhone());
                            }
                        }
                        if (response.getCode() != 1) {
                            boolean user_first = SpUtils.getBoolean("user_first_boolean", false);
                            mDoctorIntent10.putExtra("user_first", user_first);
                        }
                        toActivity(mDoctorIntent10);
                    }

                    @Override
                    public void onFailures(int statusCode, String errorMsg) {
                        CommonMethod.requestError(statusCode, errorMsg);
                    }
                });
                break;
            case R.id.mFomusDoctor:
                //名医续方
                Intent mDoctorIntent0 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent0.putExtra("type", 15);
                mDoctorIntent0.putExtra("code", 1103);
                toActivity(mDoctorIntent0);
                break;
            case R.id.et_patient_msg:
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
                break;
//            case R.id.mOnlineConsulting:
//                //名医续方
//                Intent mDoctorIntent1 = new Intent(getContext(), WebViewActivity.class);
//                mDoctorIntent1.putExtra("type", 15);
//                mDoctorIntent1.putExtra("code", 1103);
//                toActivity(mDoctorIntent1);
//                break;
            case R.id.mGraphicInquiry:
                Intent mDoctorIntent2 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent2.putExtra("type", 15);
                mDoctorIntent2.putExtra("code", 1101);
                toActivity(mDoctorIntent2);

                break;
            case R.id.mVideoInterrogation:
                Intent mDoctorIntent3 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent3.putExtra("type", 15);
                mDoctorIntent3.putExtra("code", 1102);
                toActivity(mDoctorIntent3);
                break;

//                Intent mDoctorIntent8 = new Intent(getContext(), WebViewActivity.class);
//                mDoctorIntent8.putExtra("type", 17);
//                toActivity(mDoctorIntent8);

//                break;
            case R.id.mNursingCounseling:
                Intent mDoctorIntent6 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent6.putExtra("type", 16);
                mDoctorIntent6.putExtra("code", 1201);
                toActivity(mDoctorIntent6);
                break;
            case R.id.mDistanceNursing:
                Intent intent8 = new Intent(getActivity(), WebViewActivity.class);
                intent8.putExtra("type", WebUtil.serviceType);
                intent8.putExtra("index", 3);
                startActivity(intent8);
//                Intent mDoctorIntent7 = new Intent(getContext(), WebViewActivity.class);
//                mDoctorIntent7.putExtra("type", 16);
//                mDoctorIntent7.putExtra("code", 1202);
//                toActivity(mDoctorIntent7);
                break;
            case R.id.mServicePackage:
                Intent intent7 = new Intent(getActivity(), WebViewActivity.class);
                intent7.putExtra("type", WebUtil.serviceType);
                intent7.putExtra("index", 2);
                startActivity(intent7);
//                Intent mDoctorIntent9 = new Intent(getContext(), WebViewActivity.class);
//                mDoctorIntent9.putExtra("type", 18);
//                toActivity(mDoctorIntent9);
                break;
            case R.id.mSpecicalService:

                break;
            case R.id.mDoctorContainer:
                //点击推荐医生的更多
                Intent mDoctorIntent4 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent4.putExtra("type", 15);
                toActivity(mDoctorIntent4);
                break;
            case R.id.mNurseContainer:
                //点击推荐护士的更多
                Intent mDoctorIntent5 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent5.putExtra("type", 16);
                toActivity(mDoctorIntent5);
                break;
            case R.id.mAdOne:
                //如果contentLink 内容为空的话就不能跳转
                if (TextUtils.isEmpty(dataBean0.getContentLink())) {
                    return;
                }
                toWeb(dataBean0.getContentLink());
                break;
            case R.id.mAdTwo:
                //如果contentLink 内容为空的话就不能跳转
                if (TextUtils.isEmpty(dataBean1.getContentLink())) {
                    return;
                }
                toWeb(dataBean1.getContentLink());
                break;
            case R.id.mAdThree:
                //如果contentLink 内容为空的话就不能跳转
                if (TextUtils.isEmpty(dataBean2.getContentLink())) {
                    return;
                }
                toWeb(dataBean2.getContentLink());
                break;
            case R.id.top_right:
                cameraTask();
                break;
            case R.id.mFamousMedicalService:
            case R.id.mIntroduce:
                Intent webIntent = new Intent(getContext(), WebViewActivity.class);
                webIntent.putExtra("type", WebUtil.introduction);
                startActivity(webIntent);
                break;
            case R.id.lv_online:
                Intent intent2 = new Intent(getContext(), WebViewActivity.class);
                intent2.putExtra("type", 15);
                intent2.putExtra("code", 1103);
                toActivity(intent2);
                break;
            case R.id.mNurseRemote:
                Intent intent3 = new Intent(getContext(), WebViewActivity.class);
                intent3.putExtra("type", 16);
                intent3.putExtra("code", 1202);
                toActivity(intent3);
                break;
        }
    }

    public void cameraTask() {
        if (EasyPermissions.hasPermissions(getActivity(), Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            onClick();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "需要获取拍照权限", REQUEST_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    private boolean checkPermission() {

        List<String> permissions = new ArrayList<>();
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        int len = permissions.size();
        if (len != 0) {
            String[] per = new String[len];
            for (int i = 0; i < len; i++) {
                per[i] = permissions.get(i);
            }
            requestPermissions(
                    per,
                    REQUEST_CODE);
            return false;
        }
        return true;

    }


    /**
     * 按钮点击事件处理逻辑
     *
     * @param
     */
    private void onClick() {
        Intent intent = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE);
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
//        Aria.download(this).unRegister();
        mDialog = null;
        mDownloadDialog = null;
        unbinder.unbind();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cityEvent(CityEntity.DataBean bean) {
        if (bean != null) {
            tvLeft.setText(bean.getAreaName());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions 权限处理请求结果
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    //同意授权
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERM:
                onClick();
                break;
        }

    }

    //拒绝授权
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode) {
            case REQUEST_CAMERA_PERM:
                toast("使用该功能,需要获取相机权限");
                break;
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    if (!TextUtils.isEmpty(result)) {
                        Log.d("onActivityResult", result);
                        int accountId = MyApplication.getAccountId();
                        Map<String, String> params = new HashMap<>();
                        params.put("QRCodeUrl", result);
                        params.put("AccountId", accountId + "");
                        mMyOkhttp.get().url(HttpUrl.QueryDoctorIdByQRCodeUrl).params(params).headers(HeadUitl.instance.getHeads()).tag(this).enqueue(new GsonResponseHandler<DoctorIdEntity>() {
                            @Override
                            public void onSuccess(int statusCode, DoctorIdEntity entituy) {
                                DoctorIdEntity.DataBean mData = entituy.getData();
                                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                                intent.putExtra("type", 29);
                                intent.putExtra("doctorId", mData.getDrId());
                                intent.putExtra("doctorName", mData.getDrName());
                                intent.putExtra("doctorType", mData.getDrType());
                                startActivity(intent);
                            }

                            @Override
                            public void onFailures(int statusCode, String errorMsg) {
                                Log.d("errMsg", errorMsg);
                            }
                        });

                    }
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    //下载版本更新

    private void checkUpdate() {
        HomeUtils.INSTANCE.checkVersion(new HomeUtils.UpdateListener() {
            @Override
            public void success(final AppEntity entity) {
                AppUpdateUtils.mAppLicationUtils = null;
                AppUpdateUtils.getInstance().update(entity, getContext(), getActivity());

            }


            @Override
            public void failed(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);

            }
        });
    }


    @Override
    public void netWorkStatus(int status) {

    }

    private void tabitemclick(AppLicationEntity appLicationEntity) {
        int type = appLicationEntity.getAppID();
        Log.d(TAG, "点击item" + type);
        switch (type) {
            case 1:
                Intent mDoctorIntent2 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent2.putExtra("type", 15);
                mDoctorIntent2.putExtra("code", 1101);
                toActivity(mDoctorIntent2);
                break;
            case 2:
                Intent mDoctorIntent3 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent3.putExtra("type", 15);
                mDoctorIntent3.putExtra("code", 1102);
                toActivity(mDoctorIntent3);
                break;
            case 3:
                Intent intent2 = new Intent(getContext(), WebViewActivity.class);
                intent2.putExtra("type", 15);
                intent2.putExtra("code", 1103);
                toActivity(intent2);
                break;
            case 4:
                Intent intentmianzhenyuyue = new Intent(getContext(), WebViewActivity.class);
                intentmianzhenyuyue.putExtra("type", WebUtil.mianzhenyuyue);
                toActivity(intentmianzhenyuyue);

                break;
            case 5:
                Intent intentZhuanke = new Intent(getContext(), WebViewActivity.class);
                intentZhuanke.putExtra("type", WebUtil.zhuankeyilianti);
                toActivity(intentZhuanke);

                break;
            case 6:
                Intent intentYisheng = new Intent(getContext(), WebViewActivity.class);
                intentYisheng.putExtra("type", WebUtil.yishengjituan);
                toActivity(intentYisheng);
                break;
            case 7:
                Intent intentZhensuo = new Intent(getContext(), WebViewActivity.class);
                intentZhensuo.putExtra("type", WebUtil.zhensuo);
                toActivity(intentZhensuo);
                break;
            case 8:
                Intent jiankangzixun = new Intent(getContext(), WebViewActivity.class);
                jiankangzixun.putExtra("type", 27);
                toActivity(jiankangzixun);
                break;
            case 9:
                Intent webIntent = new Intent(getContext(), WebViewActivity.class);
                webIntent.putExtra("type", WebUtil.introduction);
                startActivity(webIntent);
                break;
            case 10:
                Intent mDoctorIntent6 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent6.putExtra("type", 16);
                mDoctorIntent6.putExtra("code", 1201);
                toActivity(mDoctorIntent6);
                break;
            case 11://远程护理
                Intent intent3 = new Intent(getContext(), WebViewActivity.class);
                intent3.putExtra("type", 16);
                intent3.putExtra("code", 1202);
                toActivity(intent3);
                break;
            case 12://VIP预约
                Intent vipYuyueIntent = new Intent(getContext(), WebViewActivity.class);
                vipYuyueIntent.putExtra("type", WebUtil.vipYuyue);
                startActivity(vipYuyueIntent);
                break;
            case 13://医生服务包
                Intent intent8 = new Intent(getActivity(), WebViewActivity.class);
                intent8.putExtra("type", WebUtil.serviceType);
                intent8.putExtra("index", 3);
                startActivity(intent8);
                break;
            case 14://机构服务包
                Intent intent7 = new Intent(getActivity(), WebViewActivity.class);
                intent7.putExtra("type", WebUtil.serviceType);
                intent7.putExtra("index", 2);
                startActivity(intent7);
                break;
        }

    }



}
