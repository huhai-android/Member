package com.newdjk.member.ui.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioFormat;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.FileProvider;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.huhai.videorecode.CameraActivity;
import com.huhai.videorecode.IUIKitCallBack;
import com.huhai.videorecode.JCameraView;
import com.huhai.videorecode.TUIKitConstants;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.camera.MessageInfo;
import com.newdjk.member.camera.MessageInfoUtil;
import com.newdjk.member.listener.OnTabItemClickListener;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.service.MyService;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.IM.RoomActivity;
import com.newdjk.member.ui.activity.min.GroupChatActivity;
import com.newdjk.member.ui.activity.min.WebViewActivity;
import com.newdjk.member.ui.adapter.ChatAdapter;
import com.newdjk.member.ui.entity.AppLicationEntity;
import com.newdjk.member.ui.entity.ChatHistoryDataEntity;
import com.newdjk.member.ui.entity.ChatHistoryEntity;
import com.newdjk.member.ui.entity.ConsultMessageEntity;
import com.newdjk.member.ui.entity.CustomMessageEntity;
import com.newdjk.member.ui.entity.DoctorEntity;
import com.newdjk.member.ui.entity.Entity;
import com.newdjk.member.ui.entity.HangUpTipEntity;
import com.newdjk.member.ui.entity.ImageInfoArrayEntity;
import com.newdjk.member.ui.entity.InquiryRecordListDataEntity;
import com.newdjk.member.ui.entity.MsgBodyEntity;
import com.newdjk.member.ui.entity.MsgContentEntity;
import com.newdjk.member.ui.entity.OnlineRenewalDataEntity;
import com.newdjk.member.ui.entity.RejectCallTip;
import com.newdjk.member.ui.entity.ResponseEntity;
import com.newdjk.member.ui.entity.UpdateViewEntity;
import com.newdjk.member.ui.fragment.TabChatFragment;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.ApplicationUtils;
import com.newdjk.member.utils.ChatViewUtil;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.LogOutUtil;
import com.newdjk.member.utils.LogUtils;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.MyTIMMessage;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.TimeUtil;
import com.newdjk.member.utils.WebUtil;
import com.newdjk.member.views.LoadDialog;
import com.newdjk.member.views.PageIndicator;
import com.tencent.TIMCallBack;
import com.tencent.TIMConnListener;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElemType;
import com.tencent.TIMImageElem;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageLocator;
import com.tencent.TIMMessageOfflinePushSettings;
import com.tencent.TIMMessageRevokedListener;
import com.tencent.TIMOfflinePushToken;
import com.tencent.TIMSoundElem;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserStatusListener;
import com.tencent.TIMValueCallBack;
import com.tencent.callsdk.ILVCallConfig;
import com.tencent.callsdk.ILVCallListener;
import com.tencent.callsdk.ILVCallManager;
import com.tencent.callsdk.ILVCallNotification;
import com.tencent.callsdk.ILVCallNotificationListener;
import com.tencent.callsdk.ILVIncomingListener;
import com.tencent.callsdk.ILVIncomingNotification;
import com.tencent.ilivesdk.ILiveCallBack;
import com.tencent.ilivesdk.ILiveFunc;
import com.tencent.ilivesdk.core.ILiveLoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import omrecorder.AudioChunk;
import omrecorder.AudioRecordConfig;
import omrecorder.OmRecorder;
import omrecorder.PullTransport;
import omrecorder.PullableSource;
import omrecorder.Recorder;

public class ChatActivity extends BasicActivity implements ILVIncomingListener, ILVCallListener, ILVCallNotificationListener, TIMMessageRevokedListener {


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
    @BindView(R.id.reject_tip)
    TextView rejectTip;
    @BindView(R.id.count_time)
    TextView countTime;
    @BindView(R.id.count_time_border)
    LinearLayout countTimeBorder;
    @BindView(R.id.accept_layout)
    LinearLayout acceptLayout;
    @BindView(R.id.btn_voice)
    ImageButton btnVoice;
    @BindView(R.id.btn_keyboard)
    ImageButton btnKeyboard;
    @BindView(R.id.voice_panel)
    TextView voicePanel;
    @BindView(R.id.input)
    EditText input;
    @BindView(R.id.btnEmoticon)
    ImageButton btnEmoticon;
    @BindView(R.id.text_panel)
    LinearLayout textPanel;
    @BindView(R.id.btn_add)
    ImageButton btnAdd;
    @BindView(R.id.btn_send)
    ImageButton btnSend;
    @BindView(R.id.input_1)
    LinearLayout input1;
    @BindView(R.id.btn_image)
    LinearLayout btnImage;
    @BindView(R.id.add_your_resume)
    LinearLayout addYourResume;
    @BindView(R.id.the_chat_more_continuation)
    LinearLayout theChatMoreContinuation;
    @BindView(R.id.end_of_the_interrogation)
    LinearLayout endOfTheInterrogation;
    @BindView(R.id.doctor_first_page)
    LinearLayout doctorFirstPage;
    @BindView(R.id.video_interrogation)
    LinearLayout videoInterrogation;
    @BindView(R.id.morePanel)
    LinearLayout morePanel;
    @BindView(R.id.input_layout)
    RelativeLayout inputLayout;
    @BindView(R.id.input_border)
    LinearLayout inputBorder;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.status)
    TextView status;
    @BindView(R.id.accept_time)
    TextView acceptTime;
    @BindView(R.id.accept_tip)
    RelativeLayout acceptTip;
    @BindView(R.id.chat_recycler_view)
    RecyclerView chatRecyclerView;
    @BindView(R.id.voice_sending)
    ImageView voiceSending;
    @BindView(R.id.cancle_voice_sending)
    TextView cancleVoiceSending;
    @BindView(R.id.mEndIcon)
    ImageView mEndIcon;
    @BindView(R.id.emoticonPanel)
    LinearLayout emoticonPanel;
    @BindView(R.id.easylayout)
    EasyRefreshLayout easylayout;
    @BindView(R.id.btn_alb)
    LinearLayout btn_alb;
    @BindView(R.id.ll_over_bottom_menu)
    LinearLayout llOverBottomMenu;
    @BindView(R.id.tv_online_image)
    TextView tvOnlineImage;
    @BindView(R.id.tv_online_video)
    TextView tvOnlineVideo;
    @BindView(R.id.tv_online_continuation)
    TextView tvOnlineContinuation;
    @BindView(R.id.tv_service_package)
    TextView tvServicePackage;

    @BindView(R.id.iv_picture_text)
    ImageView ivPictureText;
    @BindView(R.id.ll_picture_text)
    LinearLayout llPictureText;
    @BindView(R.id.iv_video)
    ImageView ivVideo;
    @BindView(R.id.ll_video)
    LinearLayout llVideo;
    @BindView(R.id.iv_online_continue)
    ImageView ivOnlineContinue;
    @BindView(R.id.ll_online_continue)
    LinearLayout llOnlineContinue;
    @BindView(R.id.iv_service)
    ImageView ivService;
    @BindView(R.id.ll_service)
    LinearLayout llService;
    @BindView(R.id.iv_online_huli)
    ImageView ivOnlineHuli;
    @BindView(R.id.tv_online_huli)
    TextView tvOnlineHuli;
    @BindView(R.id.ll_online_huli)
    LinearLayout llOnlineHuli;
    @BindView(R.id.iv_online_yuancheng)
    ImageView ivOnlineYuancheng;
    @BindView(R.id.tv_online_yuancheng)
    TextView tvOnlineYuancheng;
    @BindView(R.id.ll_online_yuancheng)
    LinearLayout llOnlineYuancheng;
    @BindView(R.id.video_recode)
    LinearLayout videoRecode;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.dot_horizontal)
    LinearLayout dotHorizontal;


    //    @BindView(R.id.activity_chat)
//    RelativeLayout activityChat;
    private boolean mIsRecording;
    private List<MyTIMMessage> mTimMessages;
    private int mLastY;
    private TIMConversation mConversation;
    private String mIdentifier;
    private MediaRecorder mRecorder;
    private String mSoundPath;
    private long mStartRecordeTime;
    private final static int REQ_PERMISSION_CODE = 0x100;
    private ChatAdapter mAdapter;
    private final int FLING_MIN_DISTANCE = 200;// 手指滑动距离
    private boolean mIsCancle = false;
    private final int REQUEST_CODE_TAKE_PICTURE = 1;
    private final int IMAGE_REQUEST_CODE = 2;
    private final int RENEWALREQUESTCODE = 3;
    private final int UPLOADRESUME_CODE = 4;
    private final int MISSION_CODE = 5;
    private final int QUICK_REPLY_CODE = 6;
    public static final int CASE_CODE = 7;
    private String mPicturePath;
    public AnimationDrawable mAnimationDrawable;
    private LinearLayout callView, loginView, llDstNums;
    private Dialog mIncomingDlg;
    private int mCurIncomingId;
    private InquiryRecordListDataEntity mInquiryRecordListDataEntity;
    private String mAction;
    private long mAcceptTime;
    private long mNowTime;
    private CountDownTimer mTimer;
    private long remainingTime;
    //问诊的有效时间24小时
    private final long VALID_TIME = 86400000;
    private OnlineRenewalDataEntity mOnlineRenewalDataEntity;
    private ConsultMessageEntity mConsultMessageEntity;
    private int mMedicalTempId = 1;
    private Gson mGson = new Gson();
    private String mLeftAvatorImagePath;
    private int mStatus = 0;
    private String validTime;
    private TIMMessage mTIMMessage = new TIMMessage();
    private int mType;
    private int mDoctorId;
    private boolean mIsServerReflashComplete = false;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private boolean mIsImsdkReflashComplete = false;
    private int mMsgTimestampStart;
    private int patientId;
    private String mDoctorName;
    private CountDownTimer mOutTimeTimer;
    private boolean mIsTimeout = false;
    private Recorder audioRecord;
    private boolean isRecord = false;
    private List<DoctorEntity.DataBean.DrServiceItemsBean> mDoctorItem;
    private int mDoctorType;
    private TIMMessageOfflinePushSettings.AndroidSettings mAndroidSettings;
    private TIMMessageOfflinePushSettings mSettings;
    private String mName = "病人回复消息:";
    private PagerAdapter mbuttonAdapter;
    List<AppLicationEntity> listuse = new ArrayList<>();

    @Override
    protected int initViewResId() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initView() {

        Fresco.initialize(this);
        mAction = getIntent().getStringExtra("action");
        easylayout.setLoadMoreModel(LoadModel.NONE);
        mTimMessages = new ArrayList<>();
        mStatus = getIntent().getIntExtra("status", 8);
        mDoctorId = getIntent().getIntExtra("drId", 0);
        if (mStatus == 0) {
            acceptTip.setVisibility(View.GONE);
            inputBorder.setVisibility(View.GONE);
            acceptLayout.setVisibility(View.GONE);
            tvRight.setVisibility(View.GONE);
            checkDoctorInformation();
            ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_OVER, mDoctorType);
            //   receptionFunctions.setVisibility(View.GONE);
        } else {
            getCurrentTime();

        }

        chatRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        hideInput();
                        morePanel.setVisibility(View.GONE);
                        break;
                }
                return false;
            }
        });
        input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    morePanel.setVisibility(View.GONE);
                }
            }
        });

        voiceSending.setImageResource(R.drawable.voice_tip);
        mAnimationDrawable = (AnimationDrawable) voiceSending.getDrawable();
        input.addTextChangedListener(new TextWatcher() {
                                         @Override
                                         public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                         }

                                         @Override
                                         public void onTextChanged(CharSequence s, int start, int before, int count) {

                                         }

                                         @Override
                                         public void afterTextChanged(Editable s) {
                                             if (input.getText() != null && !input.getText().toString().trim().equals("")) {
                                                 btnAdd.setVisibility(View.GONE);
                                                 btnSend.setVisibility(View.VISIBLE);
                                             } else {
                                                 btnAdd.setVisibility(View.VISIBLE);
                                                 btnSend.setVisibility(View.GONE);
                                             }
                                         }
                                     }
        );
        mOutTimeTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                mIsTimeout = true;
                voicePanel.setText(getString(R.string.chat_press_talk));
                voiceSending.setVisibility(View.GONE);
                mAnimationDrawable.selectDrawable(0);
                mAnimationDrawable.stop();
                sendSoundMessage(true);
            }
        };
        voicePanel.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        voicePanel.setText(getString(R.string.chat_release_send));
                        mLastY = (int) event.getY();
                        voiceSending.setVisibility(View.VISIBLE);
                        mAnimationDrawable.start();
                        mOutTimeTimer.start();
                        startRecordAndFile();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int Y = (int) event.getY();
                        if (!mIsTimeout) {
                            if (upMove(Y)) {
                                mIsCancle = true;
                                voicePanel.setText(getString(R.string.chat_release_cancel));
                                cancleVoiceSending.setVisibility(View.VISIBLE);
                                voiceSending.setVisibility(View.GONE);
                            } else {
                                mIsCancle = false;
                                voiceSending.setVisibility(View.VISIBLE);
                                cancleVoiceSending.setVisibility(View.GONE);
                                voicePanel.setText(getString(R.string.chat_release_send));
                            }
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        if (!mIsTimeout) {
                            mOutTimeTimer.cancel();
                            voicePanel.setText(getString(R.string.chat_press_talk));
                            voiceSending.setVisibility(View.GONE);
                            mAnimationDrawable.selectDrawable(0);
                            mAnimationDrawable.stop();
                            if (mIsCancle) {
                                cancleVoiceSending.setVisibility(View.GONE);
                                sendSoundMessage(false);
                            } else {
                                sendSoundMessage(true);
                            }
                        }
                        mIsTimeout = false;
                        break;
                }
                return true;
            }
        });

        mIdentifier = getIntent().getStringExtra(Contants.FRIEND_IDENTIFIER);
        mDoctorName = getIntent().getStringExtra(Contants.FRIEND_NAME);
        initToolbar();
        initviewpager();
        MyApplication.mImId = mIdentifier;
        Log.i("mIdentifier===", "mIdentifier=" + mIdentifier);
//        TIMManager.getInstance().addMessageListener(this);
        mConversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, mIdentifier);
        mConversation.setReadMessage();
        mAdapter = new ChatAdapter(mTimMessages, mDoctorId, patientId, this, mLeftAvatorImagePath, mConversation);
        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        chatRecyclerView.setAdapter(mAdapter);


        mTIMMessage = new TIMMessage();
        initMessage(mTIMMessage);
        //设置当前消息的离线推送配置
        mSettings = new TIMMessageOfflinePushSettings();
        mSettings.setEnabled(true);

//设置离线推送扩展信息
        JSONObject object = new JSONObject();
        try {
            object.put("level", 15);
            object.put("task", "TASK15");
            mSettings.setExt(object.toString().getBytes("utf-8"));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //设置在 Android 设备上收到消息时的离线配置
        mAndroidSettings = new TIMMessageOfflinePushSettings.AndroidSettings();
//ImSDK 2.5.3 之前的构造方式
//TIMMessageOfflinePushSettings.AndroidSettings androidSettings = settings.new AndroidSettings();
        mAndroidSettings.setTitle(mName);
//推送自定义通知栏消息，接收方收到消息后单击通知栏消息会给应用回调（针对小米、华为离线推送）
        mAndroidSettings.setNotifyMode(TIMMessageOfflinePushSettings.NotifyMode.Normal);
//设置 Android 设备收到消息时的提示音，声音文件需要放置到 raw 文件夹
        // mAndroidSettings.setSound(Uri.parse("android.resource://" + getPackageName() + "/" +R.raw.weixin2));
        //  getMessage(new TIMMessage());

        TIMManager.getInstance().setMessageRevokedListener(this);
    }
    private void initviewpager() {
        listuse.clear();

        listuse.addAll(ApplicationUtils.getListChat());

        mbuttonAdapter = new PagerAdapter(getSupportFragmentManager());

        viewpager.setAdapter(mbuttonAdapter);
        viewpager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                viewpager.setCurrentItem(position, false);
                TabChatFragment homeTabFragment = (TabChatFragment) mbuttonAdapter.getItem(position);
                homeTabFragment.setdata(listuse, position);
            }
        });
        if (listuse.size() <= 8) {
            dotHorizontal.setVisibility(View.GONE);
        } else {
            dotHorizontal.setVisibility(View.VISIBLE);
            viewpager.addOnPageChangeListener(new PageIndicator(getContext(), dotHorizontal, listuse.size() / 8 + 1));

        }
        viewpager.setCurrentItem(0, false);
        mbuttonAdapter.notifyDataSetChanged();
    }

    private class PagerAdapter extends FragmentPagerAdapter {
        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // Log.d(TAG,"数据长度"+listuse.size());
            TabChatFragment homeTabFragment = new TabChatFragment();
            homeTabFragment.setdata(listuse, position);
            homeTabFragment.setonclickListener(new OnTabItemClickListener() {
                @Override
                public void onItemChildClick(AppLicationEntity appLicationEntity) {
                    tabitemclick(appLicationEntity);
                }
            });
            return homeTabFragment;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TabChatFragment fragment = (TabChatFragment) super.instantiateItem(container, position);
            fragment.setdata(listuse, position);
            return fragment;
        }


        @Override
        public int getCount() {
            if (listuse.size() <= 8) {
                return 1;
            } else if (listuse.size() > 8 && listuse.size() <= 16) {
                return 2;
            } else {
                return 3;
            }
        }

        @Override
        public int getItemPosition(Object object) {
            //注意：默认是PagerAdapter.POSITION_UNCHANGED，不会重新加载
            return PagerAdapter.POSITION_NONE;
        }

    }

    public void getCurrentTime() {
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.get().url(HttpUrl.getCurrentTime).headers(headMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity response) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = response.getData().toString();
                try {
                    Date d = format.parse(time);
                    mNowTime = d.getTime();

                    //根据问诊类容初始化相关工作
                    if (mAction != null && mAction.equals("videoInterrogation")) {
                        mInquiryRecordListDataEntity = (InquiryRecordListDataEntity) getIntent().getSerializableExtra("inquiryRecordListDataEntity");
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setmType(MainConstant.UpdateConversation);
                        EventBus.getDefault().post(messageEvent);

                        if (mInquiryRecordListDataEntity != null) {
                            mDoctorId = mInquiryRecordListDataEntity.getDoctorId();
                            mDoctorType = mInquiryRecordListDataEntity.getType();
                            patientId = mInquiryRecordListDataEntity.getPatientId();
                            mDoctorName = mInquiryRecordListDataEntity.getDoctorName();
                            mLeftAvatorImagePath = mInquiryRecordListDataEntity.getDoctorHeadImgUrl();
                            mType = mInquiryRecordListDataEntity.getType();
                            int type = mInquiryRecordListDataEntity.getStatus();
                            if (type == 0) {
                                acceptLayout.setVisibility(View.GONE);
                                inputBorder.setVisibility(View.GONE);
                                acceptTip.setVisibility(View.VISIBLE);
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                                String date = "";
                                if (!TextUtils.isEmpty(mInquiryRecordListDataEntity.getReExaminationDate())) {
                                    date = mInquiryRecordListDataEntity.getReExaminationDate().substring(0, mInquiryRecordListDataEntity.getReExaminationDate().indexOf(" "));

                                }
                                String startTime = mInquiryRecordListDataEntity.getReExaminationStartTime();
                                String endTime = mInquiryRecordListDataEntity.getReExaminationEndTime();
                                String dateTime = date + " " + startTime;
                                mAcceptTime = date2TimeStamp(dateTime, "yyyy-MM-dd HH:mm:ss");
                                remainingTime = mAcceptTime - mNowTime;
                                countTimeBorder.setVisibility(View.GONE);
                                /*  if (remainingTime > 0) {
                                 *//* countTimeBorder.setVisibility(View.VISIBLE);
                                    mTimer = new CountDownTimer(remainingTime, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            String time = TimeUtil.formatTime(millisUntilFinished);
                                            countTime.setText(time);
                                        }

                                        @Override
                                        public void onFinish() {
                                            acceptTip.setBackgroundColor(getResources().getColor(R.color.background));

                                            inputBorder.setVisibility(View.GONE);

                                        }
                                    }.start();*//*
                                } else {
                                    acceptLayout.setVisibility(View.GONE);
                                    inputBorder.setVisibility(View.VISIBLE);
                    *//*    if (VALID_TIME + remainingTime > 0) {

                        } else {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");
                            inputBorder.setVisibility(View.GONE);
                            acceptLayout.setVisibility(View.GONE);
                        }*//*
                                }*/
                                status.setText("待问诊");
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_PLAYING, mDoctorType);
                                mEndIcon.setBackgroundResource(R.mipmap.ic_wen_zhen_over);
                                endOfTheInterrogation.setEnabled(false);
                                acceptTime.setText("预约时间： " + date + " " + startTime + "-" + endTime);
                            } else if (type == 1) {
                                String startTime = mInquiryRecordListDataEntity.getStartTime();
                                mAcceptTime = date2TimeStamp(startTime, "yyyy-MM-dd HH:mm:ss");
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                                status.setText("接诊中");
                                mEndIcon.setBackgroundResource(R.mipmap.img_end);
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_PLAYING, mDoctorType);
                                endOfTheInterrogation.setEnabled(true);
                                inputBorder.setVisibility(View.VISIBLE);
                                acceptLayout.setVisibility(View.GONE);
                                mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊剩余时间：" + time);

                                    }

                                    @Override
                                    public void onFinish() {
                                        // updateInquiryRecordStatus(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        acceptTime.setText("问诊剩余时间：00：00");
                                        status.setText("已结束");
                         /*   acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            acceptTime.setVisibility(View.GONE);
                            inputBorder.setVisibility(View.GONE);
                            acceptLayout.setVisibility(View.GONE);*/
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }
                          /*  inputBorder.setVisibility(View.GONE);
                            acceptLayout.setVisibility(View.VISIBLE);
                            countTimeBorder.setVisibility(View.GONE);
                            acceptBorder.setVisibility(View.GONE);
                            rejectTip.setVisibility(View.VISIBLE);
                            acceptTip.setVisibility(View.GONE);*/
                                    }
                                }.start();
                                //  acceptTime.setText(date + " " + startTime + "-" + endTime);
                            } else if (type == 2 || type == 4 || type == 5) {
                                // mAcceptTime = date2TimeStamp(startTime, "yyyy-MM-dd HH:mm:ss");
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                status.setText("已结束");
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_OVER, mDoctorType);
                                inputBorder.setVisibility(View.GONE);
                                acceptLayout.setVisibility(View.GONE);
                            }
                        }

                    } else if (mAction != null && mAction.equals("onlineRenewal")) {
                        mOnlineRenewalDataEntity = (OnlineRenewalDataEntity) getIntent().getSerializableExtra("onlineRenewalDataEntity");

                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setmType(MainConstant.UpdateConversation);
                        EventBus.getDefault().post(messageEvent);
                        if (mOnlineRenewalDataEntity != null) {
                            mDoctorId = mOnlineRenewalDataEntity.getDoctorId();
                            patientId = mOnlineRenewalDataEntity.getPatientId();
                            if (mInquiryRecordListDataEntity != null) {
                                mDoctorType = mInquiryRecordListDataEntity.getType();
                            }
                            mType = 1;
                            mLeftAvatorImagePath = mOnlineRenewalDataEntity.getDoctorHeadImgUrl();
                            //   acceptValidTime.setVisibility(View.GONE);
                            int type = mOnlineRenewalDataEntity.getStatus();
                            if (type == 0) {
                                status.setText("待接诊");
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                                acceptLayout.setVisibility(View.GONE);
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_PLAYING, mDoctorType);
                                inputBorder.setVisibility(View.VISIBLE);
                                mEndIcon.setBackgroundResource(R.mipmap.ic_wen_zhen_over);
                                endOfTheInterrogation.setEnabled(false);
                            } else if (type == 1) {
                                inputBorder.setVisibility(View.VISIBLE);
                                acceptLayout.setVisibility(View.GONE);
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_PLAYING, mDoctorType);
                                status.setText("问诊中");
                                mEndIcon.setBackgroundResource(R.mipmap.img_end);
                                endOfTheInterrogation.setEnabled(true);
                                try {
                                    validTime = mOnlineRenewalDataEntity.getDealWithTime();
                                    mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                                    mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            String time = TimeUtil.formatTime(millisUntilFinished);
                                            acceptTime.setText("问诊时间还剩：" + time);
                                        }

                                        @Override
                                        public void onFinish() {
                                            // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                            acceptTime.setText("问诊时间还剩：00：00");
                               /* status.setText("已结束");
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                acceptTime.setVisibility(View.GONE);
                                inputBorder.setVisibility(View.GONE);
                                acceptLayout.setVisibility(View.GONE);
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));*/
                                            if (mTimer != null) {
                                                mTimer.cancel();
                                            }
                                        }
                                    }.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            } else {
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                status.setText("已结束");
                                inputBorder.setVisibility(View.GONE);
                                acceptLayout.setVisibility(View.GONE);
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_OVER, mDoctorType);
                            }
                        }
                    } else if (mAction != null && mAction.equals("pictureConsult")) {
                        mConsultMessageEntity = (ConsultMessageEntity) getIntent().getSerializableExtra("consultMessageEntity");
                        MessageEvent messageEvent = new MessageEvent();
                        messageEvent.setmType(MainConstant.UpdateConversation);
                        EventBus.getDefault().post(messageEvent);

                        if (mConsultMessageEntity != null) {
                            mDoctorId = mConsultMessageEntity.getDoctorId();
                            patientId = mConsultMessageEntity.getPatientId();
                            mType = mConsultMessageEntity.getType();
                            mLeftAvatorImagePath = mConsultMessageEntity.getDoctorHeadImgUrl();
                            // acceptValidTime.setVisibility(View.GONE);
                            int type = mConsultMessageEntity.getStatus();
                            if (type == 0) {
                                status.setText("待接诊");
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.huang));
                                acceptLayout.setVisibility(View.GONE);
                                inputBorder.setVisibility(View.VISIBLE);
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_PLAYING, mDoctorType);
                                mEndIcon.setBackgroundResource(R.mipmap.ic_wen_zhen_over);
                                endOfTheInterrogation.setEnabled(false);
                            } else if (type == 1) {
                                inputBorder.setVisibility(View.VISIBLE);
                                acceptLayout.setVisibility(View.GONE);
                                status.setText("问诊中");
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_PLAYING, mDoctorType);
                                mEndIcon.setBackgroundResource(R.mipmap.img_end);
                                endOfTheInterrogation.setEnabled(true);
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                                validTime = mConsultMessageEntity.getDealWithTime();
                                try {
                                    mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                                    mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            String time = TimeUtil.formatTime(millisUntilFinished);
                                            acceptTime.setText("问诊时间还剩：" + time);
                                        }

                                        @Override
                                        public void onFinish() {
                                            // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                              /*  status.setText("已结束");
                                inputBorder.setVisibility(View.GONE);
                                acceptTime.setVisibility(View.GONE);*/
                                            acceptTime.setText("问诊时间还剩：00：00");
                                            if (mTimer != null) {
                                                mTimer.cancel();
                                            }
                                        /*inputBorder.setVisibility(View.GONE);
                                        acceptLayout.setVisibility(View.VISIBLE);
                                        countTimeBorder.setVisibility(View.GONE);
                                        acceptBorder.setVisibility(View.GONE);
                                        rejectTip.setVisibility(View.VISIBLE);
                                        acceptTip.setVisibility(View.GONE);*/
                                        }
                                    }.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                inputBorder.setVisibility(View.GONE);
                                acceptLayout.setVisibility(View.GONE);
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_OVER, mDoctorType);
                                status.setText("已结束");

                            }
                        }
                    }

                    if (mAdapter != null) {
                        mAdapter.setLeftImagePath(mLeftAvatorImagePath);
                    }
                    checkDoctorInformation();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {

            }
        });
    }

    private void checkDoctorInformation() {
        Log.d("chat", "2222医生id" + String.valueOf(mDoctorId));
        loading(true);
        HashMap<String, String> params = new HashMap<>();
        params.put("DrId", mDoctorId + "");
        params.put("PatientId", patientId + "");
        mMyOkhttp.get().headers(HeadUitl.instance.getHeads()).url(HttpUrl.QueryDoctorInfoByDrId).params(params).tag(this).enqueue(new GsonResponseHandler<DoctorEntity>() {
            @Override
            public void onSuccess(int statusCode, DoctorEntity response) {
                LoadDialog.clear();
                if (response.getCode() == 0) {
                    if (response.getData() != null) {
                        mDoctorItem = response.getData().getDrServiceItems();
                        mDoctorType = response.getData().getDrType();
                        LogUtils.d("ChatViewUtil", "医生类型-----" + mDoctorType + "");
                        setStatus();
                    }
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void setStatus() {
        if (mDoctorItem == null) {
            return;
        }
        if (mDoctorType == 2) {

            llOnlineContinue.setVisibility(View.GONE);
            llPictureText.setVisibility(View.GONE);
            llVideo.setVisibility(View.GONE);
            llOnlineYuancheng.setVisibility(View.VISIBLE);
            llOnlineHuli.setVisibility(View.VISIBLE);
            for (int i = 0; i < mDoctorItem.size(); i++) {
                if (mDoctorItem.get(i).getSericeItemCode().equals("1101")) {//图文
                    llPictureText.setClickable(true);
                    ivPictureText.setImageResource(R.drawable.home_icon_2);
                    llPictureText.setOnClickListener(this);
                } else if (mDoctorItem.get(i).getSericeItemCode().equals("1103")) {//续方
                    llOnlineContinue.setClickable(true);
                    ivOnlineContinue.setImageResource(R.drawable.home_icon_7);
                    llOnlineContinue.setOnClickListener(this);
                } else if (mDoctorItem.get(i).getSericeItemCode().equals("1201")) {///护理咨询
                    llOnlineHuli.setClickable(true);
                    ivOnlineHuli.setImageResource(R.drawable.home_icon_5);
                    llOnlineHuli.setOnClickListener(this);

                } else if (mDoctorItem.get(i).getSericeItemCode().equals("1202")) {//远程护理
                    llOnlineYuancheng.setClickable(true);
                    ivOnlineYuancheng.setImageResource(R.mipmap.kangfuzhidao);
                    llOnlineYuancheng.setOnClickListener(this);
                }
            }
        } else {
            llOnlineHuli.setVisibility(View.GONE);
            llOnlineYuancheng.setVisibility(View.GONE);
            for (int i = 0; i < mDoctorItem.size(); i++) {
                if (mDoctorItem.get(i).getSericeItemCode().equals("1101")) {//图文
                    llPictureText.setClickable(true);
                    ivPictureText.setImageResource(R.drawable.home_icon_2);
                    llPictureText.setOnClickListener(this);
                } else if (mDoctorItem.get(i).getSericeItemCode().equals("1102")) {//视频
                    llVideo.setClickable(true);
                    ivVideo.setImageResource(R.drawable.home_icon_3);
                    llVideo.setOnClickListener(this);
                } else if (mDoctorItem.get(i).getSericeItemCode().equals("1103")) {//续方
                    llOnlineContinue.setClickable(true);
                    ivOnlineContinue.setImageResource(R.drawable.home_icon_7);
                    llOnlineContinue.setOnClickListener(this);
                }
            }
        }


    }

    public void startRecordAndFile() {
        //判断是否有外部存储设备sdcard
        if (audioRecord == null) {
            creatAudioRecord();
        }

        mStartRecordeTime = System.currentTimeMillis();
        audioRecord.startRecording();
        // 让录制状态为true
        isRecord = true;
        // 开启音频文件写入线程
        //    new Thread(new AudioRecordThread()).start();
    }

    public void stopRecordAndFile() {
        try {
            audioRecord.stopRecording();
            audioRecord = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void creatAudioRecord() {
        audioRecord = OmRecorder.wav(
                new PullTransport.Default(mic(), new PullTransport.OnAudioChunkPulledListener() {
                    @Override
                    public void onAudioChunkPulled(AudioChunk audioChunk) {
                        animateVoice((float) (audioChunk.maxAmplitude() / 200.0));
                    }
                }), file());

    }

    private PullableSource mic() {
        return new PullableSource.Default(
                new AudioRecordConfig.Default(
                        MediaRecorder.AudioSource.MIC, AudioFormat.ENCODING_PCM_16BIT,
                        AudioFormat.CHANNEL_IN_MONO, 16000
                )
        );
    }


    @NonNull
    private File file() {

        File file = new File(Environment.getExternalStorageDirectory(), "huhai.wav");
        mSoundPath = file.getPath();
        return file;
    }

    private void animateVoice(final float maxPeak) {
        ImageView recordButton = new ImageView(ChatActivity.this);
        recordButton.animate().scaleX(1 + maxPeak).scaleY(1 + maxPeak).setDuration(10).start();
    }

    @Override
    protected void initListener() {
        easylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {

                getMessage(mTIMMessage);
            }

            @Override
            public void onRefreshing() {

                getMessage(mTIMMessage);
            }
        });
        theChatMoreContinuation.setOnClickListener(this);
        endOfTheInterrogation.setOnClickListener(this);
        addYourResume.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        doctorFirstPage.setOnClickListener(this);
        btnVoice.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        btnKeyboard.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnImage.setOnClickListener(this);
        videoInterrogation.setOnClickListener(this);
        addYourResume.setOnClickListener(this);
        endOfTheInterrogation.setOnClickListener(this);
        btn_alb.setOnClickListener(this);
        tvOnlineContinuation.setOnClickListener(this);
        tvOnlineImage.setOnClickListener(this);
        tvServicePackage.setOnClickListener(this);
        tvOnlineVideo.setOnClickListener(this);
        llService.setOnClickListener(this);
        videoRecode.setOnClickListener(this);


        chatRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom) {
                    chatRecyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            if (mAdapter.getItemCount() > 0) {
                                chatRecyclerView.smoothScrollToPosition(mAdapter.getItemCount() - 1);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.top_left:
                finish();
                break;
            case R.id.btn_send:
                String message = input.getText().toString();
                if (!TextUtils.isEmpty(message)) {
                    sendTextMessage(message);
                    input.setText("");
                    btnAdd.setVisibility(View.VISIBLE);
                    btnSend.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_voice:
                hideInput();
                btnVoice.setVisibility(View.GONE);
                textPanel.setVisibility(View.GONE);
                btnKeyboard.setVisibility(View.VISIBLE);
                morePanel.setVisibility(View.GONE);
                voicePanel.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_keyboard:
                btnVoice.setVisibility(View.VISIBLE);
                textPanel.setVisibility(View.VISIBLE);
                btnKeyboard.setVisibility(View.GONE);
                voicePanel.setVisibility(View.GONE);
                break;
            case R.id.btn_add:
                if (morePanel.getVisibility() == View.GONE) {
                    hideInput();

                    morePanel.setVisibility(View.VISIBLE);
                } else {
                    if (textPanel.getVisibility() == View.VISIBLE) {
                        showInput();
                        morePanel.setVisibility(View.VISIBLE);
                    }
                    morePanel.setVisibility(View.GONE);
                }
                break;
            case R.id.the_chat_more_continuation:
                Intent renewalRequsetIntent = new Intent(ChatActivity.this, WebViewActivity.class);
                renewalRequsetIntent.putExtra("type", 19);
                renewalRequsetIntent.putExtra("doctorId", mDoctorId);
                startActivityForResult(renewalRequsetIntent, RENEWALREQUESTCODE);
                break;
            case R.id.add_your_resume:
                Intent addResumeIntent = new Intent(ChatActivity.this, WebViewActivity.class);
                addResumeIntent.putExtra("type", 20);
                startActivityForResult(addResumeIntent, UPLOADRESUME_CODE);
                break;
            case R.id.doctor_first_page:
                Intent doctorFirstPageIntent = new Intent(ChatActivity.this, WebViewActivity.class);
                if (mType == 1) {
                    doctorFirstPageIntent.putExtra("type", 24);
                    doctorFirstPageIntent.putExtra("doctorId", mDoctorId);
                } else if (mType == 2) {
                    doctorFirstPageIntent.putExtra("type", 25);
                    doctorFirstPageIntent.putExtra("nurseId", mDoctorId);
                }
                startActivity(doctorFirstPageIntent);
                break;
            case R.id.video_interrogation:
                int callId = ILiveFunc.generateAVCallRoomID();
                CustomMessageEntity customMessageEntity = new CustomMessageEntity();
                //   customMessageEntity.setTitle(SpUtils.getString(Contants.Name)+"医生提醒您填写一下病历，这对于您的病情很有帮助");
                customMessageEntity.setIsSystem(false);
                customMessageEntity.setContent(null);
                CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
                CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
                extData.setType(129);
                data.setAVRoomID(callId);
                data.setTargets(SpUtils.getString(Contants.Name));
                extData.setData(data);
                customMessageEntity.setExtData(extData);
                String json = new Gson().toJson(customMessageEntity);
                sendCustomMessage(json, "");
                mTimer = new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {


                    }

                    @Override
                    public void onFinish() {
                        Log.i("ChatActivity", "222222");
                        CustomMessageEntity customMessageEntity = new CustomMessageEntity();
                        //   customMessageEntity.setTitle(SpUtils.getString(Contants.Name)+"医生提醒您填写一下病历，这对于您的病情很有帮助");
                        customMessageEntity.setIsSystem(false);
                        customMessageEntity.setContent(null);
                        CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
                        CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
                        extData.setType(132);
                        data.setTargets(SpUtils.getString(Contants.Name));
                        extData.setData(data);
                        customMessageEntity.setExtData(extData);
                        String json = new Gson().toJson(customMessageEntity);
                        sendCustomMessage(json, "");
                        EventBus.getDefault().post(new RejectCallTip(true));
                    }
                }.start();
                Intent roomIntent = new Intent(ChatActivity.this, RoomActivity.class);
                roomIntent.putExtra("callId", callId);
                startActivity(roomIntent);
                break;
          /*  case R.id.btn_photo:
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String f = System.currentTimeMillis() + ".jpg";
                mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
                File file = new File(mPicturePath);
                file.getParentFile().mkdirs();
                Log.i("zdp", mPicturePath);
                Uri uri = FileProvider.getUriForFile(this, "com.newdjk.member.provider", file);
                //添加权限
                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE);
                break; */
            case R.id.btn_alb:
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
                break;
            case R.id.btn_image:
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String f = System.currentTimeMillis() + ".jpg";
                mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
                File file = new File(mPicturePath);
                file.getParentFile().mkdirs();
                Log.i("zdp", mPicturePath);
                Uri uri = FileProvider.getUriForFile(this, "com.newdjk.member.file.provider", file);
                //添加权限
                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE);
                break;
            case R.id.end_of_the_interrogation:
                alertDialog("end");
                break;
            case R.id.ll_picture_text://图文问诊
                Intent imageIntent = new Intent(this, WebViewActivity.class);
                imageIntent.putExtra("type", WebUtil.doctorPicture);
                imageIntent.putExtra("code", 1101);
                imageIntent.putExtra("id", mDoctorId);
                toActivity(imageIntent);

                break;
            case R.id.ll_video://视频问诊
                Intent intentVideo = new Intent(this, WebViewActivity.class);
                intentVideo.putExtra("type", WebUtil.doctorVideo);
                intentVideo.putExtra("code", 1102);
                intentVideo.putExtra("id", mDoctorId);
                toActivity(intentVideo);

                break;
            case R.id.ll_online_continue://在线续方
                Intent intentContinuation = new Intent(this, WebViewActivity.class);
                intentContinuation.putExtra("type", WebUtil.doctorContinuation);
                intentContinuation.putExtra("code", 1103);
                intentContinuation.putExtra("id", mDoctorId);
                toActivity(intentContinuation);

                break;
            case R.id.ll_service:
                Intent intentServicePackage = new Intent(this, WebViewActivity.class);
                intentServicePackage.putExtra("type", WebUtil.doctorServicePackage);
                intentServicePackage.putExtra("id", mDoctorId);
                intentServicePackage.putExtra("code", mDoctorName);
                toActivity(intentServicePackage);

                break;

            case R.id.ll_online_huli:
                Intent mDoctorIntent6 = new Intent(getContext(), WebViewActivity.class);
                mDoctorIntent6.putExtra("type", 16);
                mDoctorIntent6.putExtra("code", 1201);
                toActivity(mDoctorIntent6);
                break;

            case R.id.ll_online_yuancheng:
                Intent intent3 = new Intent(getContext(), WebViewActivity.class);
                intent3.putExtra("type", 16);
                intent3.putExtra("code", 1202);
                toActivity(intent3);
                break;

            case R.id.video_recode:
                startVideoRecord();
                break;

        }
    }


    protected static final int CAPTURE = 1;
    protected static final int AUDIO_RECORD = 2;
    protected static final int VIDEO_RECORD = 3;
    protected static final int SEND_PHOTO = 4;
    protected static final int SEND_FILE = 5;
    private AlertDialog mPermissionDialog;

    protected void startVideoRecord() {
        if (!checkPermission(VIDEO_RECORD)) {
            return;
        }
        Intent captureIntent = new Intent(getContext(), CameraActivity.class);
        captureIntent.putExtra(TUIKitConstants.CAMERA_TYPE, JCameraView.BUTTON_STATE_ONLY_RECORDER);
        CameraActivity.mCallBack = new IUIKitCallBack() {
            @Override
            public void onSuccess(Object data) {
                Intent videoData = (Intent) data;
                String imgPath = videoData.getStringExtra(TUIKitConstants.CAMERA_IMAGE_PATH);
                String videoPath = videoData.getStringExtra(TUIKitConstants.CAMERA_VIDEO_PATH);
                int imgWidth = videoData.getIntExtra(TUIKitConstants.IMAGE_WIDTH, 0);
                int imgHeight = videoData.getIntExtra(TUIKitConstants.IMAGE_HEIGHT, 0);
                long duration = videoData.getLongExtra(TUIKitConstants.VIDEO_TIME, 0);
                MessageInfo msg = MessageInfoUtil.buildVideoMessage(imgPath, videoPath, imgWidth, imgHeight, duration);

                sendShopVideoMessage(msg);

                hideInput();
            }

            @Override
            public void onError(String module, int errCode, String errMsg) {

            }
        };
        getContext().startActivity(captureIntent);
    }

    private void sendShopVideoMessage(final MessageInfo msg) {
        //发送消息
        MyTIMMessage myTIMMessage = new MyTIMMessage();
        myTIMMessage.setTimMessage(msg.getTIMMessage());
        getAdapterData().add(0, myTIMMessage);
        updateRecyclerView();

        mConversation.sendMessage(msg.getTIMMessage(), new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.d("视频", "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.i("视频", "success");
                MessageEvent messageEvent1 = new MessageEvent();
//        messageEvent.setmType(MainConstant.UpdateMessageListEntity);
                messageEvent1.setmType(MainConstant.UpdateConversation);
                EventBus.getDefault().post(messageEvent1);
                updateRecyclerView();
            }
        });
    }

    protected boolean checkPermission(Context context, String permission) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int result = ActivityCompat.checkSelfPermission(context, permission);
            if (PackageManager.PERMISSION_GRANTED != result) {
                //2.没有权限
                showPermissionDialog();
                flag = false;
            }
        }
        return flag;
    }

    private void showPermissionDialog() {
        if (mPermissionDialog == null) {
            mPermissionDialog = new AlertDialog.Builder(this)
                    .setMessage("使用该功能，需要开启权限，鉴于您禁用相关权限，请手动设置开启权限")
                    .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cancelPermissionDialog();
                            Uri packageURI = Uri.parse("package:" + ChatActivity.this.getPackageName());
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                            ChatActivity.this.startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //关闭页面或者做其他操作
                            cancelPermissionDialog();
                        }
                    })
                    .create();
        }
        mPermissionDialog.show();
    }

    private void cancelPermissionDialog() {
        mPermissionDialog.cancel();
    }

    protected boolean checkPermission(int type) {
        if (!checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            return false;
        }
        if (!checkPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            return false;
        }
        if (type == SEND_FILE || type == SEND_PHOTO) {
            return true;
        } else if (type == CAPTURE) {
            return checkPermission(this, Manifest.permission.CAMERA);
        } else if (type == AUDIO_RECORD) {
            return checkPermission(this, Manifest.permission.RECORD_AUDIO);
        } else if (type == VIDEO_RECORD) {
            return checkPermission(this, Manifest.permission.CAMERA)
                    && checkPermission(this, Manifest.permission.RECORD_AUDIO);
        }
        return true;
    }


    private void alertDialog(final String action) {
        final Dialog dialog = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.alert_dialog, null);
        dialog.setContentView(view);
        Button sure = view.findViewById(R.id.btn_positive_custom_dialog);
        Button cancel = view.findViewById(R.id.btn_negative_custom_dialog);
        TextView titleText = view.findViewById(R.id.tv_title_custom_dialog);
        TextView contentText = view.findViewById(R.id.tv_message_custom_dialog);
        if (action.equals("end")) {
            titleText.setText("结束问诊提醒");
            contentText.setText("你确定要结束问诊吗");
        }
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (action.equals("end")) {
                    if (mAction != null && mAction.equals("videoInterrogation")) {
                        updateInquiryRecordStatus(2, String.valueOf(SpUtils.getInt(Contants.Id, 0)), 5, mInquiryRecordListDataEntity.getId(), null);
                    } else if (mAction != null && mAction.equals("onlineRenewal")) {
                        updatePrescriptionApplyStatus(2, String.valueOf(SpUtils.getInt(Contants.Id, 0)), 5, mOnlineRenewalDataEntity.getId(), null);
                    } else if (mAction != null && mAction.equals("pictureConsult")) {
                        updateConsultRecordStatus(2, String.valueOf(SpUtils.getInt(Contants.Id, 0)), 5, mConsultMessageEntity.getId(), null);
                    }
                }
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    //变更续方业务状态
    private void updatePrescriptionApplyStatus(int operatorRole, String operatorId, final int recodeStatus, int id, String remark) {
        Map<String, String> map = new HashMap<>();
        map.put("OperatorRole", String.valueOf(operatorRole));
        map.put("OperatorId", String.valueOf(operatorId));
        map.put("Status", String.valueOf(recodeStatus));
        map.put("Id", String.valueOf(id));
        if (remark != null) {
            map.put("Remark", remark);
        }
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.UpdatePrescriptionApplyStatus).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        if (recodeStatus == 1) {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTime.setVisibility(View.VISIBLE);
                            status.setText("问诊中");
                            try {

                                mTimer = new CountDownTimer(VALID_TIME, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊时间还剩" + time);

                                    }

                                    @Override
                                    public void onFinish() {
                                        // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        status.setText("已结束");
                                        inputBorder.setVisibility(View.GONE);
                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }

                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (recodeStatus == 2 || recodeStatus == 5) {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            inputBorder.setVisibility(View.GONE);
                            acceptLayout.setVisibility(View.GONE);
                            status.setText("已结束");
                            acceptTime.setVisibility(View.GONE);
                            if (mTimer != null) {
                                mTimer.cancel();
                            }

                        }
//                        EventBus.getDefault().post(new UpdateImMessageEntity(null));
                    } else {
                        toast("接诊失败");
                    }
                    //  mVideoInterrogationAdapter.setDatalist( entity.getData());
                } else {
                    toast(entity.getMessage());
                }
            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    //变更图文问诊状态
    private void updateConsultRecordStatus(int operatorRole, String operatorId, final int recordStatus, int id, String remark) {
        Map<String, String> map = new HashMap<>();
        map.put("OperatorRole", String.valueOf(operatorRole));
        map.put("OperatorId", operatorId);
        map.put("Status", String.valueOf(recordStatus));
        map.put("Id", String.valueOf(id));
        if (remark != null) {
            map.put("Remark", remark);
        }
        loading(true);
        Log.i("ChatActivity", "operatorId=" + operatorId + ",id=" + id);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.UpdateConsultRecordStatus).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        //  EventBus.getDefault().post(new UpdateConsultViewEntity1(true));
                        if (recordStatus == 1) {

                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTime.setVisibility(View.VISIBLE);
                            status.setText("问诊中");
                            try {
                                mTimer = new CountDownTimer(VALID_TIME, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊时间还剩" + time);

                                    }

                                    @Override
                                    public void onFinish() {
                                        // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        status.setText("已结束");
                                        inputBorder.setVisibility(View.GONE);
                                        acceptTime.setVisibility(View.GONE);
                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }

                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (recordStatus == 2 || recordStatus == 5) {
                            inputBorder.setVisibility(View.GONE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            status.setText("已结束");
                            acceptTime.setVisibility(View.GONE);
                            if (mTimer != null) {
                                mTimer.cancel();
                            }

                        }
//                        EventBus.getDefault().post(new UpdateImMessageEntity(null));
                    } else {
                        toast("操作失败");
                    }
                } else {
                    Log.i("ChatActivity", "error=" + entity.getCode() + ",errormessage=" + entity.getMessage());
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    //视频业务状态变更
    private void updateInquiryRecordStatus(int operatorRole, String operatorId, final int recodeStatus, int id, String remark) {
        Map<String, String> map = new HashMap<>();
        map.put("OperatorRole", String.valueOf(operatorRole));
        map.put("OperatorId", String.valueOf(operatorId));
        map.put("Status", String.valueOf(recodeStatus));
        map.put("Id", String.valueOf(id));
        if (remark != null) {
            map.put("Remark", remark);
        }
        loading(true);
        Map<String, String> headMap = new HashMap<>();
        headMap.put("Authorization", SpUtils.getString(Contants.Token));
        mMyOkhttp.post().url(HttpUrl.UpdateInquiryRecordStatus).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<Entity>() {
            @Override
            public void onSuccess(int statusCode, Entity entity) {
                LoadDialog.clear();
                if (entity.getCode() == 0) {
                    boolean result = (boolean) entity.getData();
                    if (result) {
                        if (recodeStatus == 1) {
                            inputBorder.setVisibility(View.VISIBLE);
                            acceptLayout.setVisibility(View.GONE);
                            acceptTime.setVisibility(View.VISIBLE);
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                            mNowTime = System.currentTimeMillis();
                            status.setText("问诊中");
                            try {
                                validTime = mOnlineRenewalDataEntity.getDealWithTime();
                                mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                                mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        String time = TimeUtil.formatTime(millisUntilFinished);
                                        acceptTime.setText("问诊时间还剩" + time);

                                    }

                                    @Override
                                    public void onFinish() {
                                        // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                        status.setText("已结束");
                                        inputBorder.setVisibility(View.GONE);
                                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                        acceptTime.setVisibility(View.GONE);
                                        if (mTimer != null) {
                                            mTimer.cancel();
                                        }

                                    }
                                }.start();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else if (recodeStatus == 2 || recodeStatus == 5) {
                            acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                            inputBorder.setVisibility(View.GONE);
                            acceptLayout.setVisibility(View.GONE);
                            status.setText("已结束");
                            acceptTime.setVisibility(View.GONE);
                            if (mTimer != null) {
                                mTimer.cancel();
                            }

                        }
                        EventBus.getDefault().post(new UpdateViewEntity(null));
                    } else {
                        toast("接诊失败");
                    }
                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // StatusBarConfig.assistActivity(this);
        checkPermission();
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ILVCallManager.getInstance().init(new ILVCallConfig()
                .setNotificationListener(this)
                .setAutoBusy(true));

        TIMManager.getInstance().setConnectionListener(new TIMConnListener() {
            @Override
            public void onConnected() {
                Log.e("zdp", "[DEV]onConnected->enter");
            }

            @Override
            public void onDisconnected(int i, String s) {
                Log.e("zdp", "[DEV]onDisconnected->enter: " + i + ", " + s);
            }

            @Override
            public void onWifiNeedAuth(String s) {
                Log.e("zdp", "[DEV]onWifiNeedAuth->enter:" + s);
            }
        });
        // 设置通话回调
        ILVCallManager.getInstance().addIncomingListener(this);
        ILVCallManager.getInstance().addCallListener(this);

        MyApplication.isChatView = true;
    }

    private void initToolbar() {
//        mName = SpUtils.getString(Contants.Name);
//        if (!TextUtils.isEmpty(mName)) {
//            initBackTitle(mName);
//        } else {
//            initBackTitle(getIntent().getStringExtra(Contants.FRIEND_NAME)).setTitleTextColor(getResources().getColor(R.color.white));
//        }

        // initBackTitle(getIntent().getStringExtra(Contants.FRIEND_NAME)).setTitleTextColor(getResources().getColor(R.color.white));
        topTitle.setText(mDoctorName);
        topTitle.setTextColor(getResources().getColor(R.color.white));
        topLeft.setVisibility(View.VISIBLE);
        topLeft.setImageResource(R.drawable.head_back_white_s);
        topLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public List<MyTIMMessage> getAdapterData() {
        return mTimMessages;
    }

    /**
     * 隐藏输入框
     */
    private void hideInput() {
        input.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
    }

    /**
     * 显示输入框
     */
    private void showInput() {
        input.setFocusable(true);
        input.setFocusableInTouchMode(true);
        input.requestFocus();
        morePanel.setVisibility(View.GONE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(input, 0);
    }

    /**
     * 录音失败
     */
    public void recordFail() {
        if (mIsRecording) {
            mIsRecording = false;
            // voiceSending.setText(getText(R.string.chat_cancel_send));
            voiceSending.setVisibility(View.GONE);
            if (mAnimationDrawable != null) {
                mAnimationDrawable.selectDrawable(0);
                mAnimationDrawable.stop();
            }
            voicePanel.setText(getText(R.string.chat_press_talk));
            sendSoundMessage(false);
        }
    }

//    @Override
//    public boolean onNewMessages(List<TIMMessage> list) {
//        List<TIMMessage> chatList = new ArrayList<>();
//        for (TIMMessage timMessage : list) {
//            if (mIdentifier.equals(timMessage.getConversation().getPeer())) {
//                chatList.add(timMessage);
//            }
//            if (timMessage.getElement(0).getType() == TIMElemType.Custom) {
//                Log.i("zdp", "tipsTHFGH");
//                TIMCustomElem customElem = (TIMCustomElem) timMessage.getElement(0);
//                String s = new String(customElem.getData());
//                CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
//                CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
//                if (extraData != null) {
//                    int type = extraData.getType();
//                    if (!timMessage.isSelf()) {
//                        if (type > 100) {
//                            switch (type) {
//                                case 129:
//                                    int callId = extraData.getData().getAVRoomID();
//                                    String sender = extraData.getData().getTargets();
//                                    String userSig = extraData.getData().getUserSig();
//                                    newIncomingCall(callId, sender, userSig);
//                                    break;
//                                case 131:
//                                    EventBus.getDefault().post(new RejectCallTip(true));
//                                    if (null != mIncomingDlg) {  // 关闭遗留来电对话框
//                                        mIncomingDlg.dismiss();
//                                    }
//                                    break;
//                                case 134:
//                                    EventBus.getDefault().post(new RejectCallTip(true));
//                                    if (mTimer != null) {
//                                        Log.i("ChatActivity","stay here");
//                                        mTimer.cancel();
//                                    }
//                                    if (null != mIncomingDlg) {  // 关闭遗留来电对话框
//                                        mIncomingDlg.dismiss();
//                                    }
//                                    break;
//                                case 133:
//                                    EventBus.getDefault().post(new RejectCallTip(true));
//                                    if (mTimer != null) {
//                                        Log.i("ChatActivity","stay here");
//                                        mTimer.cancel();
//                                    }
//                                    break;
//                                case 130:
//                                    if (mTimer != null) {
//                                        Log.i("ChatActivity","stay here");
//                                        mTimer.cancel();
//                                    }
//                                    break;
//
//                            }
//                        }
//                    }
//                    if (type == 36 ) {
//                        MessageEvent messageEvent = new MessageEvent();
//                        messageEvent.setmType(MainConstant.UpdateConversation);
//                        EventBus.getDefault().post(messageEvent);
////                        EventBus.getDefault().post(new UpdateViewEntity(null));
//                        inputBorder.setVisibility(View.GONE);
//                        acceptLayout.setVisibility(View.GONE);
//                        acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
//                        status.setText("已结束");
//                        acceptTime.setVisibility(View.GONE);
//                        if (mTimer != null) {
//                            mTimer.cancel();
//                        }
//                    }
//
//                }
//
//            }
//        }
//
//        if (chatList.size() == 0) {
//            return false;
//        }
//        getAdapterData().addAll(0, chatList);
//        updateRecyclerView();
//        return true;
//    }


    public void newIncomingCall(final int callId, String sender, final String userSig) {
        mIncomingDlg = new Dialog(this, R.style.ActionSheetDialogStyle);//dialog样式
        View view = View.inflate(this, R.layout.alert_dialog, null);
        mIncomingDlg.setContentView(view);
        Button sure = view.findViewById(R.id.btn_positive_custom_dialog);
        Button cancel = view.findViewById(R.id.btn_negative_custom_dialog);
        TextView titleText = view.findViewById(R.id.tv_title_custom_dialog);
        TextView contentText = view.findViewById(R.id.tv_message_custom_dialog);
        titleText.setText("视频邀请");
        contentText.setText("来自" + sender + "的视频邀请");
        sure.setText("接受");
        cancel.setText("拒绝");
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVideoMessage(130, -1);
                //acceptCall(callId, notification.getSponsorId(), callType);
                Intent roomIntent = new Intent(ChatActivity.this, RoomActivity.class);
                roomIntent.putExtra("callId", callId);
                roomIntent.putExtra("target", "other");
                roomIntent.putExtra("userSig", userSig);
                startActivity(roomIntent);

                mIncomingDlg.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVideoMessage(133, -1);
                mIncomingDlg.dismiss();
            }
        });
        mIncomingDlg.show();
        Log.i("dd", "callId=" + callId);
     /*   if (null != mIncomingDlg) {  // 关闭遗留来电对话框
            mIncomingDlg.dismiss();
        }
        //  mCurIncomingId = callId;
        mIncomingDlg = new AlertDialog.Builder(this)
                .setTitle("来电提醒 ")
                .setMessage("New Call From " + sender)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mIncomingDlg.dismiss();
                        sendVideoMessage(130);
                        //acceptCall(callId, notification.getSponsorId(), callType);
                        Intent roomIntent = new Intent(ChatActivity.this, RoomActivity.class);
                        roomIntent.putExtra("callId", callId);
                        roomIntent.putExtra("target", "other");
                        roomIntent.putExtra("userSig", userSig);
                        startActivity(roomIntent);
                    }
                })
                .setNegativeButton("Reject", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendVideoMessage(133);
                        mIncomingDlg.dismiss();
                        //   int ret = ILVCallManager.getInstance().rejectCall(mCurIncomingId);
                    }
                })
                .create();
        mIncomingDlg.setCanceledOnTouchOutside(false);
        mIncomingDlg.show();*/
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    public void initMessage(TIMMessage timMessage) {

        Log.d("chat", "1111医生id===" + String.valueOf(mDoctorId));
        mConversation.getLocalMessage(20, timMessage, new TIMValueCallBack<List<TIMMessage>>() {
            @Override
            public void onError(int i, String s) {
                if (easylayout == null) return;
                if (easylayout.isRefreshing()) easylayout.refreshComplete();
                Log.i("ChatActivity", "error=" + s + "  " + i);
            }

            @Override
            public void onSuccess(final List<TIMMessage> timMessages) {
               /* TIMTextElem textElem = (TIMTextElem) timMessages.get(0).getElement(0);
                Log.i("zdp","id="+ textElem.getText());*/
                //  mTimMessages.clear();
                if (easylayout == null) {
                    return;
                }
                if (easylayout.isRefreshing()) {
                    easylayout.refreshComplete();
                }
                if (timMessages.size() == 0) {
                    mIsImsdkReflashComplete = true;
                }
                List<MyTIMMessage> list = new ArrayList<>();
                for (int i = 0; i < timMessages.size(); i++) {
                    MyTIMMessage myTIMMessage = new MyTIMMessage();
                    myTIMMessage.setTimMessage(timMessages.get(i));
                    list.add(myTIMMessage);

                }
                getAdapterData().addAll(list);
                if (list.size() == 0) {
                    Log.d("ChatActivity", "本地没数据直接刷新后台的消息");
                    mIsImsdkReflashComplete = true;
                    mMsgTimestampStart = (int) (System.currentTimeMillis() / 1000);
                    if (mDoctorId == 0) {
                        mDoctorId = getIntent().getIntExtra("drId", 0);
                    }
                    GetChatHistoryByPage(String.valueOf(mDoctorId), String.valueOf(SpUtils.getInt(Contants.AccountId, 0)), mMsgTimestampStart, 20, true, 0);
                } else if (list.size() < 20) {
                    Log.d("ChatActivity", "本地有数据" + list.size() + " 不够20，拉取后台消息");
                    //  getMessage(mTIMMessage);
                    mIsImsdkReflashComplete = true;
                    mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getTimMessage().timestamp();
                    int pageSize = 20 - list.size();
                    if (mDoctorId == 0) {
                        mDoctorId = getIntent().getIntExtra("drId", 0);
                    } else {
                        GetChatHistoryByPage(String.valueOf(mDoctorId), String.valueOf(SpUtils.getInt(Contants.AccountId, 0)), mMsgTimestampStart, pageSize, true, 0);
                    }
                } else {
                    updateRecyclerView();
                }
            }
        });
    }

    /**
     * 获取单聊的聊天信息
     *
     * @param timMessage
     */
    public void getMessage(TIMMessage timMessage) {
        if (mIsImsdkReflashComplete) {
            if (easylayout == null) return;
            if (easylayout.isRefreshing()) easylayout.refreshComplete();
            if (mTimMessages.size() > 0) {
                boolean isLocalMessage = mTimMessages.get(mTimMessages.size() - 1).isLocalMessage();
                if (isLocalMessage) {
                    mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getMsgTimestamp();
                } else {
                    mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getTimMessage().timestamp();
                }
            }
            if (mDoctorId == 0) {
                mDoctorId = getIntent().getIntExtra("drId", 0);
            }
            GetChatHistoryByPage(String.valueOf(mDoctorId), String.valueOf(SpUtils.getInt(Contants.AccountId, 0)), mMsgTimestampStart, 20, false, 0);
            //   getLocalDatabaseMessage(SpUtils.getInt(Contants.Id, 0),mAccountId,mMsgTimestampStart);
        } else {
            mConversation.getLocalMessage(20, timMessage, new TIMValueCallBack<List<TIMMessage>>() {
                @Override
                public void onError(int i, String s) {
                    if (easylayout == null) return;
                    if (easylayout.isRefreshing()) easylayout.refreshComplete();
                }

                @Override
                public void onSuccess(final List<TIMMessage> timMessages) {
               /* TIMTextElem textElem = (TIMTextElem) timMessages.get(0).getElement(0);
                Log.i("zdp","id="+ textElem.getText());*/
                    //  mTimMessages.clear();
                    if (easylayout == null) {
                        return;
                    }
                    if (easylayout.isRefreshing()) {
                        easylayout.refreshComplete();
                    }
                    if (timMessages.size() == 0) {
                        mIsImsdkReflashComplete = true;
                        mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getTimMessage().timestamp();
                        if (mDoctorId == 0) {
                            mDoctorId = getIntent().getIntExtra("drId", 0);
                        }
                        GetChatHistoryByPage(String.valueOf(mDoctorId), String.valueOf(SpUtils.getInt(Contants.AccountId, 0)), mMsgTimestampStart, 20, false, 0);
                        //       GetChatHistoryByPage(String.valueOf(SpUtils.getInt(Contants.Id, 0)), String.valueOf(mAccountId), mMsgTimestampStart);
                        //  getLocalDatabaseMessage(SpUtils.getInt(Contants.Id, 0),mAccountId,mMsgTimestampStart);
                    } else if (timMessages.size() < 20) {
                        mIsImsdkReflashComplete = true;
                        List<MyTIMMessage> list = new ArrayList<>();
                        for (int i = 0; i < timMessages.size(); i++) {
                            MyTIMMessage myTIMMessage = new MyTIMMessage();
                            myTIMMessage.setTimMessage(timMessages.get(i));
                            list.add(myTIMMessage);

                        }
                        getAdapterData().addAll(list);
                        int pageSize = 20 - timMessages.size();
                        mMsgTimestampStart = (int) mTimMessages.get(mTimMessages.size() - 1).getTimMessage().timestamp();
                        if (mDoctorId == 0) {
                            mDoctorId = getIntent().getIntExtra("drId", 0);
                        }
                        GetChatHistoryByPage(String.valueOf(mDoctorId), String.valueOf(SpUtils.getInt(Contants.AccountId, 0)), mMsgTimestampStart, pageSize, false, timMessages.size());
                    } else {
                        List<MyTIMMessage> list = new ArrayList<>();
                        for (int i = 0; i < timMessages.size(); i++) {
                            MyTIMMessage myTIMMessage = new MyTIMMessage();
                            myTIMMessage.setTimMessage(timMessages.get(i));
                            list.add(myTIMMessage);

                        }
                        getAdapterData().addAll(list);
                        mTIMMessage = mTimMessages.get(mTimMessages.size() - 1).getTimMessage();
                        mAdapter.notifyDataSetChanged();
                        if (list.size() > 0) {
                            LinearLayoutManager mLayoutManager = (LinearLayoutManager) chatRecyclerView.getLayoutManager();
                            mLayoutManager.scrollToPositionWithOffset(list.size(), 0);
                        }
                    }
                    //  updateRecyclerView();
                }
            });
        }
    }

    /**
     * 发送图片消息
     *
     * @param path
     */
    public void sendPictureMessage(String path) {
        if (new File(path).exists()) {
            TIMMessage timMessage = new TIMMessage();
            //添加图片
            TIMImageElem elem = new TIMImageElem();
            elem.setPath(path);
            //将 Elem 添加到消息
            if (timMessage.addElement(elem) != 0) {
                Log.d("zdp", "addElement failed");
                return;
            }
            MyTIMMessage myTIMMessage = new MyTIMMessage();
            myTIMMessage.setTimMessage(timMessage);
            getAdapterData().add(0, myTIMMessage);
            mSettings.setDescr("[图片信息]");
            mSettings.setAndroidSettings(mAndroidSettings);
            timMessage.setOfflinePushSettings(mSettings);
            //发送消息
            mConversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    //错误码 code 和错误描述 desc，可用于定位请求失败原因
                    //错误码 code 列表请参见错误码表
                    updateRecyclerView();
                    Log.d("zdp", "send message failed. code: " + code + " errmsg: " + desc);
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    updateRecyclerView();
                    Log.e("zdp", "SendMsg ok");
                }
            });
            updateRecyclerView();
        }
    }


    /**
     * 发送文本消息
     *
     * @param input
     */
    public void sendTextMessage(String input) {
        TIMMessage timMessage = new TIMMessage();
        TIMTextElem timTextElem = new TIMTextElem();
        timTextElem.setText(input);
        timMessage.addElement(timTextElem);
        MyTIMMessage myTIMMessage = new MyTIMMessage();
        myTIMMessage.setTimMessage(timMessage);
        getAdapterData().add(0, myTIMMessage);
        updateRecyclerView();


        mSettings.setDescr(input);
        mSettings.setAndroidSettings(mAndroidSettings);
        timMessage.setOfflinePushSettings(mSettings);

        mConversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {
            @Override
            public void onError(int i, String s) {
                Log.d("chat", "发送消息失败" + i + "   " + s);
                updateRecyclerView();
            }

            @Override
            public void onSuccess(TIMMessage timMessage) {
                updateRecyclerView();
            }
        });
    }

    /**
     * 更新消息列表
     */
    public void updateRecyclerView() {
        if (mAdapter == null) {
            mAdapter = new ChatAdapter(mTimMessages, mDoctorId, patientId, this, mLeftAvatorImagePath, mConversation);
            chatRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            chatRecyclerView.setAdapter(mAdapter);
        } else {
            mConversation.setReadMessage();
            mAdapter.notifyDataSetChanged();
        }
        if (mAdapter.getItemCount() > 0) {
            mTIMMessage = mTimMessages.get(mTimMessages.size() - 1).getTimMessage();
            chatRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
        }

    }

    /**
     * 录音
     */
    public void recordSound() {
        try {
            mRecorder = new MediaRecorder();
            mRecorder.reset();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mSoundPath = MyApplication.getContext().getFilesDir() + File.separator + mIdentifier +
                    "_" + TimeUtil.getTimeStr(SystemClock.currentThreadTimeMillis() / 1000);
            mRecorder.setOutputFile(mSoundPath);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();

            mRecorder.start();
            mStartRecordeTime = System.currentTimeMillis();
        } catch (IOException e) {
            recordFail();
        }
    }

    /**
     * 发送语音消息
     *
     * @param needToSend
     */
    public void sendSoundMessage(boolean needToSend) {
        stopRecordAndFile();
        long duration = (System.currentTimeMillis() - mStartRecordeTime) / 1000;
        if (needToSend) {
            if (duration < 1) {
                Toast.makeText(MyApplication.getContext(), "录音时间过短", Toast.LENGTH_SHORT).show();
                return;
            }
            TIMMessage timMessage = new TIMMessage();
            TIMSoundElem soundElem = new TIMSoundElem();
            soundElem.setPath(mSoundPath);
            soundElem.setDuration(duration);
            if (timMessage.addElement(soundElem) != 0) {
                Toast.makeText(MyApplication.getContext(), "发送失败", Toast.LENGTH_SHORT).show();
                return;
            }
            MyTIMMessage myTIMMessage = new MyTIMMessage();
            myTIMMessage.setTimMessage(timMessage);
            getAdapterData().add(0, myTIMMessage);
            mSettings.setDescr("[语音信息]");
            mSettings.setAndroidSettings(mAndroidSettings);
            timMessage.setOfflinePushSettings(mSettings);
            mConversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {//发送消息回调
                @Override
                public void onError(int code, String desc) {//发送消息失败
                    updateRecyclerView();
                }

                @Override
                public void onSuccess(TIMMessage msg) {//发送消息成功
                    updateRecyclerView();
                }
            });
            updateRecyclerView();
        } else {
            File file = new File(mSoundPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    @Override
    protected void onDestroy() {
        ILVCallManager.getInstance().removeIncomingListener(this);
        MyApplication.mImId = null;
        ILVCallManager.getInstance().removeCallListener(this);
        if (mRecorder != null) {
            mRecorder.release();
        }
        if (mTimer != null) {
            mTimer.cancel();
        }
        MyApplication.isChatView = false;
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQ_PERMISSION_CODE:
                for (int ret : grantResults) {
                    if (PackageManager.PERMISSION_GRANTED != ret) {
                        Toast.makeText(this, "用户没有允许需要的权限，使用可能会受到限制！", Toast.LENGTH_SHORT).show();
                        //  addLogMessage("用户没有允许需要的权限，使用可能会受到限制！");
                    }
                }
                break;
            default:
                break;
        }
    }

    private Context getContext() {
        return this;
    }

    /**
     * 检查权限
     *
     * @return
     */
    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)) {
                permissions.add(Manifest.permission.READ_PHONE_STATE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(ChatActivity.this,
                        (String[]) permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    private boolean upMove(int y) {
        if ((mLastY - y) > FLING_MIN_DISTANCE) {
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TAKE_PICTURE:
                if (resultCode == RESULT_OK) {
                    Log.i("zdp", mPicturePath);
                    sendPictureMessage(mPicturePath);
                }
                break;
            case IMAGE_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri selectedImage = data.getData(); //获取系统返回的照片的Uri
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);//从系统表中查询指定Uri对应的照片
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String path = cursor.getString(columnIndex);  //获取照片路径
                        sendPictureMessage(path);
                        cursor.close();
                    } catch (Exception e) {
                        // TODO Auto-generatedcatch block
                        e.printStackTrace();
                    }
                }
                break;
            case RENEWALREQUESTCODE:
                if (resultCode == RESULT_OK) {
                    String renewalMessage = data.getExtras().getString("msgContent");
                    sendCustomMessage(renewalMessage, "customer");
                }
                break;
            case UPLOADRESUME_CODE:
                if (resultCode == RESULT_OK) {
                    String renewalMessage = data.getExtras().getString("msgContent");
                    sendCustomMessage(renewalMessage, "customer");
                }
                break;
            case CASE_CODE:
                if (resultCode == RESULT_OK) {
                    String renewalMessage = data.getExtras().getString("msgContent");
                    sendCustomMessage(renewalMessage, "customer");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private void sendCustomMessage(String data, String desc) {
        //构造一条消息
        TIMMessage timMessage = new TIMMessage();
        //向 TIMMessage 中添加自定义内容
        TIMCustomElem elem = new TIMCustomElem();
        elem.setData(data.getBytes());      //自定义 byte[]
        elem.setDesc(desc); //自定义描述信息
        //将 elem 添加到消息
        if (timMessage.addElement(elem) != 0) {
            Log.d("zdp", "addElement failed");
            return;
        }
       /* TIMMessage timMessage = new TIMMessage();
        TIMTextElem timTextElem = new TIMTextElem();
        timTextElem.setText(input);*/
        //  timMessage.addElement(elem);
        MyTIMMessage myTIMMessage = new MyTIMMessage();
        myTIMMessage.setTimMessage(timMessage);
        getAdapterData().add(0, myTIMMessage);
        mSettings.setDescr(desc);
        mSettings.setAndroidSettings(mAndroidSettings);
        timMessage.setOfflinePushSettings(mSettings);
        //发送消息
        mConversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                updateRecyclerView();
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                updateRecyclerView();
            }
        });
        updateRecyclerView();

    }

    @Override
    public void onCallEstablish(int callId) {

    }

    @Override
    public void onCallEnd(int callId, int endResult, String endInfo) {

    }

    @Override
    public void onException(int iExceptionId, int errCode, String errMsg) {

    }

    @Override
    public void onRecvNotification(int callid, ILVCallNotification notification) {

    }

    @Override
    public void onNewIncomingCall(int callId, int callType, ILVIncomingNotification notification) {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(HangUpTipEntity HangUpTipEntity) {

        int action = HangUpTipEntity.action;
        long time = HangUpTipEntity.time;
        sendVideoMessage(action, time);
        if (mTimer != null) {
            Log.i("ChatActivity", "stay here");
            mTimer.cancel();
        }
    }

    /**
     * 消息处理
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.UpdateChatActivity:
                List<TIMMessage> list = event.getList();
                List<MyTIMMessage> chatList = new ArrayList<>();
                for (TIMMessage timMessage : list) {
                    if (mIdentifier.equals(timMessage.getConversation().getPeer())) {
                        MyTIMMessage myTIMMessage = new MyTIMMessage();
                        myTIMMessage.setTimMessage(timMessage);
                        chatList.add(myTIMMessage);
                    }
                    if (timMessage.getElement(0).getType() == TIMElemType.Custom) {
                        Log.i("zdp", "tipsTHFGH");
                        TIMCustomElem customElem = (TIMCustomElem) timMessage.getElement(0);
                        String s = new String(customElem.getData());
                        CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                        CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
                        if (extraData != null) {
                            int type = extraData.getType();
                            if (!timMessage.isSelf()) {
                                if (type > 100) {
                                    switch (type) {
                                        case 129:
                                            int callId = extraData.getData().getAVRoomID();
                                            String sender = extraData.getData().getTargets();
                                            String userSig = extraData.getData().getUserSig();
                                            //  newIncomingCall(callId, sender, userSig);
                                            Intent intent = new Intent(ChatActivity.this, DialogActivity.class);
                                            intent.putExtra("sender", sender);
                                            intent.putExtra("callId", callId);
                                            intent.putExtra("identifier", mIdentifier);
                                            intent.putExtra("userSig", userSig);
                                            intent.putExtra("action", "chatActivity");
                                            startActivity(intent);
                                            break;
                                        case 131:
                                            EventBus.getDefault().post(new RejectCallTip(true));
                                            if (null != mIncomingDlg) {  // 关闭遗留来电对话框
                                                mIncomingDlg.dismiss();
                                            }
                                            break;
                                        case 134:
                                            EventBus.getDefault().post(new RejectCallTip(true));
                                            if (mTimer != null) {
                                                Log.i("ChatActivity", "stay here");
                                                mTimer.cancel();
                                            }
                                            if (null != mIncomingDlg) {  // 关闭遗留来电对话框
                                                mIncomingDlg.dismiss();
                                            }
                                            break;
                                        case 133:
                                            EventBus.getDefault().post(new RejectCallTip(true));
                                            if (mTimer != null) {
                                                Log.i("ChatActivity", "stay here");
                                                mTimer.cancel();
                                            }
                                            break;
                                        case 130:
                                            if (mTimer != null) {
                                                Log.i("ChatActivity", "stay here");
                                                mTimer.cancel();
                                            }
                                            break;

                                    }
                                }
                            }
                            if (type == 36) {
                                checkDoctorInformation();
                                MessageEvent messageEvent = new MessageEvent();
                                messageEvent.setmType(MainConstant.UpdateConversation);
                                EventBus.getDefault().post(messageEvent);
//                        EventBus.getDefault().post(new UpdateViewEntity(null));
                                inputBorder.setVisibility(View.GONE);
                                acceptLayout.setVisibility(View.GONE);
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
                                status.setText("已结束");
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_OVER, mDoctorType);
                                mEndIcon.setBackgroundResource(R.mipmap.ic_wen_zhen_over);
                                acceptTime.setVisibility(View.GONE);
                                if (mTimer != null) {
                                    mTimer.cancel();
                                }
                            } else if (type == 35) {
                                MessageEvent messageEvent = new MessageEvent();
                                messageEvent.setmType(MainConstant.UpdateConversation);
                                EventBus.getDefault().post(messageEvent);
                                status.setText("问诊中");
                                acceptTime.setVisibility(View.VISIBLE);
                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_PLAYING, mDoctorType);
                                endOfTheInterrogation.setEnabled(true);
                                mEndIcon.setBackgroundResource(R.mipmap.img_end);
                                acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
                                inputBorder.setVisibility(View.VISIBLE);
                                //validTime = mConsultMessageEntity.getDealWithTime();
                                try {
                                    mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
                                    mTimer = new CountDownTimer(VALID_TIME, 1000) {
                                        @Override
                                        public void onTick(long millisUntilFinished) {
                                            String time = TimeUtil.formatTime(millisUntilFinished);
                                            acceptTime.setText("问诊时间还剩" + time);
                                        }

                                        @Override
                                        public void onFinish() {
                                            // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
                                            status.setText("已结束");
                                            inputBorder.setVisibility(View.GONE);
                                            acceptTime.setVisibility(View.GONE);
                                            if (mTimer != null) {
                                                mTimer.cancel();
                                            }
                                        /*inputBorder.setVisibility(View.GONE);
                                        acceptLayout.setVisibility(View.VISIBLE);
                                        countTimeBorder.setVisibility(View.GONE);
                                        acceptBorder.setVisibility(View.GONE);
                                        rejectTip.setVisibility(View.VISIBLE);
                                        acceptTip.setVisibility(View.GONE);*/
                                        }
                                    }.start();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }

                        }

                    }
                }
                if (chatList != null && chatList.size() > 0) {
                    getAdapterData().addAll(0, chatList);
                    updateRecyclerView();
                }
                break;
        }

    }


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void userEventBus(MessageEvent event) {
//        switch (event.getmType()) {
//            case MainConstant.UpdateChatActivity:
//                List<TIMMessage> list = event.getList();
//                List<MyTIMMessage> chatList = new ArrayList<>();
//                for (TIMMessage timMessage : list) {
//                    if (mIdentifier.equals(timMessage.getConversation().getPeer())) {
//                        MyTIMMessage myTIMMessage = new MyTIMMessage();
//                        myTIMMessage.setTimMessage(timMessage);
//                        chatList.add(myTIMMessage);
//                    }
//                    if (timMessage.getElement(0).getType() == TIMElemType.Custom) {
//                        Log.i("zdp", "tipsTHFGH");
//                        TIMCustomElem customElem = (TIMCustomElem) timMessage.getElement(0);
//                        String s = new String(customElem.getData());
//                        CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
//                        CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
//                        if (extraData != null) {
//                            int type = extraData.getType();
//                            if (!timMessage.isSelf()) {
//                                if (type > 100) {
//                                    switch (type) {
//                                        case 129:
//                                            int callId = extraData.getData().getAVRoomID();
//                                            String sender = extraData.getData().getTargets();
//                                            String userSig = extraData.getData().getUserSig();
//                                            newIncomingCall(callId, sender, userSig);
//                                            break;
//                                        case 131:
//                                            EventBus.getDefault().post(new RejectCallTip(true));
//                                            if (null != mIncomingDlg) {  // 关闭遗留来电对话框
//                                                mIncomingDlg.dismiss();
//                                            }
//                                            break;
//                                        case 134:
//                                            EventBus.getDefault().post(new RejectCallTip(true));
//                                            if (mTimer != null) {
//                                                Log.i("ChatActivity", "stay here");
//                                                mTimer.cancel();
//                                            }
//                                            if (null != mIncomingDlg) {  // 关闭遗留来电对话框
//                                                mIncomingDlg.dismiss();
//                                            }
//                                            break;
//                                        case 133:
//                                            EventBus.getDefault().post(new RejectCallTip(true));
//                                            if (mTimer != null) {
//                                                Log.i("ChatActivity", "stay here");
//                                                mTimer.cancel();
//                                            }
//                                            break;
//                                        case 130:
//                                            if (mTimer != null) {
//                                                Log.i("ChatActivity", "stay here");
//                                                mTimer.cancel();
//                                            }
//                                            break;
//
//                                    }
//                                }
//                            }
//                            if (type == 36) {
//                                checkDoctorInformation();
//                                MessageEvent messageEvent = new MessageEvent();
//                                messageEvent.setmType(MainConstant.UpdateConversation);
//                                EventBus.getDefault().post(messageEvent);
////                        EventBus.getDefault().post(new UpdateViewEntity(null));
//                                inputBorder.setVisibility(View.GONE);
//                                acceptLayout.setVisibility(View.GONE);
//                                acceptTip.setBackgroundColor(getResources().getColor(R.color.gray));
//                                status.setText("已结束");
//                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_OVER);
//                                acceptTime.setVisibility(View.GONE);
//                                if (mTimer != null) {
//                                    mTimer.cancel();
//                                }
//                            } else if (type == 35) {
//                                status.setText("接诊中");
//                                acceptTime.setVisibility(View.VISIBLE);
//                                ChatViewUtil.instance.setChatRvSize(llOverBottomMenu, easylayout, ChatViewUtil.instance.TYPE_PLAYING);
//                                endOfTheInterrogation.setEnabled(true);
//                                mEndIcon.setBackgroundResource(R.drawable.img_end);
//                                acceptTip.setBackgroundColor(getResources().getColor(R.color.blue));
//                                validTime = mConsultMessageEntity.getDealWithTime();
//                                Log.d("","");
//                                try {
//                                    mAcceptTime = date2TimeStamp(validTime, "yyyy-MM-dd HH:mm:ss");
//                                    mTimer = new CountDownTimer(VALID_TIME - mNowTime + mAcceptTime, 1000) {
//                                        @Override
//                                        public void onTick(long millisUntilFinished) {
//                                            String time = TimeUtil.formatTime(millisUntilFinished);
//                                            acceptTime.setText("问诊时间还剩" + time);
//                                        }
//
//                                        @Override
//                                        public void onFinish() {
//                                            // update(2, SpUtils.getInt(Contants.Id, 0), 5, mInquiryRecordListDataEntity.getId());
//                                            status.setText("已结束");
//                                            inputBorder.setVisibility(View.GONE);
//                                            acceptTime.setVisibility(View.GONE);
//                                            if (mTimer != null) {
//                                                mTimer.cancel();
//                                            }
//                                        /*inputBorder.setVisibility(View.GONE);
//                                        acceptLayout.setVisibility(View.VISIBLE);
//                                        countTimeBorder.setVisibility(View.GONE);
//                                        acceptBorder.setVisibility(View.GONE);
//                                        rejectTip.setVisibility(View.VISIBLE);
//                                        acceptTip.setVisibility(View.GONE);*/
//                                        }
//                                    }.start();
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                }
//
//
//                            }
//
//                        }
//
//                    }
//                }
//                if (chatList.size() > 0) {
//                    getAdapterData().addAll(0, chatList);
//                    updateRecyclerView();
//                }
//                break;
//        }
//    }


    public void sendVideoMessage(int type, long time) {
        CustomMessageEntity customMessageEntity = new CustomMessageEntity();
        String textTime;
        if (time != -1) {
            textTime = TimeUtil.getCountTime(time / 1000);
        } else {
            textTime = "";
        }
        //   customMessageEntity.setTitle(SpUtils.getString(Contants.Name)+"医生提醒您填写一下病历，这对于您的病情很有帮助");
        customMessageEntity.setIsSystem(false);
        customMessageEntity.setContent(null);
        CustomMessageEntity.ExtDataBean extData = new CustomMessageEntity.ExtDataBean();
        CustomMessageEntity.ExtDataBean.DataBean data = new CustomMessageEntity.ExtDataBean.DataBean();
        extData.setType(type);
        data.setTargets(SpUtils.getString(Contants.Name));
        data.setTime(textTime);
        extData.setData(data);

        customMessageEntity.setExtData(extData);
        String json = new Gson().toJson(customMessageEntity);
        sendCustomMessage(json, "");
    }

    private void GetChatHistoryByPage(String doctorId, String accountId, int msgTimestampStart, int pageSize, final boolean isFirstData, final int imListSize) {
        if (mIsServerReflashComplete) {
            toast("没有更多数据");
        } else {
            Log.i("ChatActivity", "doctorId=" + doctorId + ",accountId=" + accountId + ",msgTimestampStart=" + msgTimestampStart);
            Map<String, String> map = new HashMap<>();
            map.put("MsgTimestampStart", String.valueOf(msgTimestampStart));
            map.put("AccountId", accountId);
            map.put("DoctorId", doctorId);
            map.put("PageSize", String.valueOf(pageSize));
            loading(true);
            Map<String, String> headMap = new HashMap<>();
            headMap.put("Authorization", SpUtils.getString(Contants.Token));
            mMyOkhttp.post().url(HttpUrl.GetChatHistoryByPage).headers(headMap).params(map).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<ChatHistoryDataEntity>>() {
                @Override
                public void onSuccess(int statusCode, ResponseEntity<ChatHistoryDataEntity> entity) {
                    LoadDialog.clear();
                    if (entity.getCode() == 0) {
                        ChatHistoryDataEntity chatHistoryDataEntity = entity.getData();
                        List<ChatHistoryEntity> list = chatHistoryDataEntity.getReturnData();
                        if (list.size() == 0) {
                            if (!isFirstData) {
                                toast("没有更多数据");
                            }
                            mIsServerReflashComplete = true;

                        }
                        for (int i = 0; i < list.size(); i++) {
                            long id = list.get(i).getId();
                            String msgBody = list.get(i).getMsgBody();
                            Type jsonType = new TypeToken<List<MsgBodyEntity>>() {
                            }.getType();
                            try {
                                List<MsgBodyEntity> msgBodyList = mGson.fromJson(msgBody, jsonType);
                                if (msgBodyList != null) {
                                    String MsgType = msgBodyList.get(0).getMsgType();
                                    MsgContentEntity msgContentEntity = msgBodyList.get(0).getMsgContent();
                                    if (MsgType != null) {
                                        if (MsgType.equals("TIMTextElem")) {
                                            TIMMessage timMessage = new TIMMessage();
                                            TIMTextElem timTextElem = new TIMTextElem();
                                            timTextElem.setText(msgContentEntity.getText());
                                            timMessage.addElement(timTextElem);
                                            MyTIMMessage myTIMMessage = new MyTIMMessage();
                                            myTIMMessage.setTimMessage(timMessage);
                                            myTIMMessage.setLocalMessage(true);
                                            myTIMMessage.setMsgTimestamp(list.get(i).getMsgTimestamp());
                                            myTIMMessage.setSendTarget(list.get(i).getFrom_Account());
                                            getAdapterData().add(mAdapter.getItemCount(), myTIMMessage);

                                        } else if (MsgType.equals("TIMCustomElem")) {
                                            TIMMessage timMessage = new TIMMessage();
                                            TIMCustomElem elem = new TIMCustomElem();
                                            elem.setData(msgContentEntity.getData().getBytes());      //自定义 byte[]
                                            elem.setDesc(msgContentEntity.getDesc()); //自定义描述信息
                                            if (timMessage.addElement(elem) != 0) {
                                                Log.d("zdp", "addElement failed");
                                                return;
                                            }

                                            //  timMessage.addElement(elem);
                                            MyTIMMessage myTIMMessage = new MyTIMMessage();
                                            myTIMMessage.setTimMessage(timMessage);
                                            myTIMMessage.setLocalMessage(true);
                                            myTIMMessage.setMsgTimestamp(list.get(i).getMsgTimestamp());
                                            myTIMMessage.setSendTarget(list.get(i).getFrom_Account());
                                            getAdapterData().add(mAdapter.getItemCount(), myTIMMessage);
                                            //  updateRecyclerView();
                                        } else if (MsgType.equals("TIMSoundElem")) {
                                            String url = msgContentEntity.getFileUrl();
                                            String uuid = msgContentEntity.getUUID();
                                            if (url != null && uuid != null) {
                                                String content = mGson.toJson(msgContentEntity);
                                                TIMMessage timMessage = new TIMMessage();
                                                TIMCustomElem elem = new TIMCustomElem();
                                                elem.setData(content.getBytes());      //自定义 byte[]
                                                elem.setDesc("TIMSoundElem"); //自定义描述信息
                                                if (timMessage.addElement(elem) != 0) {
                                                    Log.d("zdp", "addElement failed");
                                                    return;
                                                }

                                                //  timMessage.addElement(elem);
                                                MyTIMMessage myTIMMessage = new MyTIMMessage();
                                                myTIMMessage.setTimMessage(timMessage);
                                                myTIMMessage.setLocalMessage(true);
                                                myTIMMessage.setMsgTimestamp(list.get(i).getMsgTimestamp());
                                                myTIMMessage.setSendTarget(list.get(i).getFrom_Account());
                                                getAdapterData().add(mAdapter.getItemCount(), myTIMMessage);
                                            }
                                            //  downFile(msgContentEntity.getFileUrl(), msgContentEntity.getUUID());
                                        } else if (MsgType.equals("TIMImageElem")) {
                                            List<ImageInfoArrayEntity> imageInfoArray = msgContentEntity.getImageInfoArray();
                                            String content = mGson.toJson(imageInfoArray);
                                            Log.i("ChatActivity", "content=" + content);
                                            TIMMessage timMessage = new TIMMessage();
                                            TIMCustomElem elem = new TIMCustomElem();
                                            elem.setData(content.getBytes());      //自定义 byte[]
                                            elem.setDesc("TIMImageElem"); //自定义描述信息
                                            if (timMessage.addElement(elem) != 0) {
                                                Log.d("zdp", "addElement failed");
                                                return;
                                            }

                                            //  timMessage.addElement(elem);
                                            MyTIMMessage myTIMMessage = new MyTIMMessage();
                                            myTIMMessage.setTimMessage(timMessage);
                                            myTIMMessage.setLocalMessage(true);
                                            myTIMMessage.setMsgTimestamp(list.get(i).getMsgTimestamp());
                                            myTIMMessage.setSendTarget(list.get(i).getFrom_Account());
                                            getAdapterData().add(mAdapter.getItemCount(), myTIMMessage);
                                            //  downFile(msgContentEntity.getFileUrl(), msgContentEntity.getUUID());
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                Log.d("haha", msgBody.toString());
                            }
                           /* List<ChatHistoryEntity> list1 = SQLiteUtils.getInstance().selectHistoryDataById(id);
                            if (list1.size() == 0) {
                                SQLiteUtils.getInstance().addHistoryData(list.get(i));
                            }*/
                        }
                        mAdapter.notifyDataSetChanged();
                        if (!isFirstData) {
                            LinearLayoutManager mLayoutManager = (LinearLayoutManager) chatRecyclerView.getLayoutManager();
                            mLayoutManager.scrollToPositionWithOffset(list.size() + imListSize, 0);
                        } else if (mAdapter.getItemCount() > 0) {
                            mTIMMessage = mTimMessages.get(mTimMessages.size() - 1).getTimMessage();
                            chatRecyclerView.scrollToPosition(mAdapter.getItemCount() - 1);
                        }
                        //          List<ChatHistoryEntity> list2 = SQLiteUtils.getInstance().selectHistoryData();
                        //          Log.i("ChatActivity", "list2=" + list2.size());
                    } else {
                        toast(entity.getMessage());
                    }

                }

                @Override
                public void onFailures(int statusCode, String errorMsg) {
                    CommonMethod.requestError(statusCode, errorMsg);
                }
            });
        }
    }

    @Override
    public void onMessageRevoked(TIMMessageLocator timMessageLocator) {
        for (int i = 0; i < mTimMessages.size(); i++) {
            MyTIMMessage myTIMMessage = mTimMessages.get(i);
            TIMMessage timMessage = myTIMMessage.getTimMessage();
            if (timMessage.checkEquals(timMessageLocator)) {
                myTIMMessage.setRevoke(true);
                mAdapter.notifyDataSetChanged();
                break;
            }
        }
    }


    private void loginSDK(final String id, String userSig) {
        ILiveLoginManager.getInstance().iLiveLogin(id, userSig, new ILiveCallBack() {
            @Override
            public void onSuccess(Object data) {

                //登录成功后，上报证书 ID 及设备 token
                TIMOfflinePushToken param = new TIMOfflinePushToken();
                param.setToken(MyApplication.mRegId);
                param.setBussid(MyApplication.busid);
                TIMManager.getInstance().setOfflinePushToken(param, new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        Log.i("MainActivity", "errorCode=" + i + ",s=" + s);
                    }

                    @Override
                    public void onSuccess() {
                        Log.i("MainActivity", "success");
                    }
                });


                //设置用户状态变更监听器，在回调中进行相应的处理
                TIMManager.getInstance().setUserStatusListener(new TIMUserStatusListener() {
                    @Override
                    public void onForceOffline() {
                        //  EventBus.getDefault().post(new NoticeLoginEntity(true));
                        Toast.makeText(ChatActivity.this, "当前账号在其它设备登录，请重新登录", Toast.LENGTH_SHORT).show();
                        MessageEvent event = new MessageEvent();
                        event.setmType(MainConstant.LOGOUTLOGIN);
                        EventBus.getDefault().post(event);
                    }

                    @Override
                    public void onUserSigExpired() {
                        MessageEvent event = new MessageEvent();
                        event.setmType(MainConstant.LOGOUTLOGIN);
                        EventBus.getDefault().post(event);
                        Toast.makeText(ChatActivity.this, "当前账号凭证已过期，请 重新登录", Toast.LENGTH_SHORT).show();
                    }
                });
                SpUtils.put(Contants.ImId, id);
                MessageEvent messageEvent = new MessageEvent();
                messageEvent.setmType(MainConstant.UpdateConversation);
                EventBus.getDefault().post(messageEvent);
                startService(new Intent(ChatActivity.this, MyService.class));

            }

            @Override
            public void onError(String module, int errCode, String errMsg) {
                if (errCode == 6208) {
                    loginSDK(id, userSig);
                } else {
                    LogOutUtil.getInstance().loginOut(ChatActivity.this, false);
                    Toast.makeText(mContext, "Login failed:" + module + "|" + errCode + "|" + errMsg, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void tabitemclick(AppLicationEntity appLicationEntity) {
        String desc = appLicationEntity.getAppDesc();
        Log.d("tabitemclick", "点击" + desc);
        switch (desc){
            case "图片":
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
                break;
            case "拍摄":
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                String f = System.currentTimeMillis() + ".jpg";
                mPicturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + f;
                File file = new File(mPicturePath);
                file.getParentFile().mkdirs();
                Log.i("zdp", mPicturePath);
                Uri uri = FileProvider.getUriForFile(this, "com.newdjk.member.file.provider", file);
                //添加权限
                openCameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(openCameraIntent, REQUEST_CODE_TAKE_PICTURE);
                break;
            case "医生主页":
                Intent doctorFirstPageIntent = new Intent(ChatActivity.this, WebViewActivity.class);
                if (mType == 1) {
                    doctorFirstPageIntent.putExtra("type", 24);
                    doctorFirstPageIntent.putExtra("doctorId", mDoctorId);
                } else if (mType == 2) {
                    doctorFirstPageIntent.putExtra("type", 25);
                    doctorFirstPageIntent.putExtra("nurseId", mDoctorId);
                }
                startActivity(doctorFirstPageIntent);
                break;
            case "拍摄视频":
                startVideoRecord();
                break;
            case "结束问诊":
                alertDialog("end");
                break;

            case "续方申请":

                Intent renewalRequsetIntent = new Intent(ChatActivity.this, WebViewActivity.class);
                renewalRequsetIntent.putExtra("type", 19);
                renewalRequsetIntent.putExtra("doctorId", mDoctorId);
                startActivityForResult(renewalRequsetIntent, RENEWALREQUESTCODE);
                break;
            case "补充病历":
                Intent addResumeIntent = new Intent(ChatActivity.this, WebViewActivity.class);
                addResumeIntent.putExtra("type", 20);
                startActivityForResult(addResumeIntent, UPLOADRESUME_CODE);
                break;
        }


    }
}
