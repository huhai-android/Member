package com.newdjk.member.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lxq.okhttp.response.GsonResponseHandler;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicFragment;
import com.newdjk.member.model.HttpUrl;
import com.newdjk.member.tools.CommonMethod;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.AddFriendActivity;
import com.newdjk.member.ui.activity.PushListActivity;
import com.newdjk.member.ui.adapter.MessageAdapter;
import com.newdjk.member.ui.entity.ConversationEntity;
import com.newdjk.member.ui.entity.CustomMessageEntity;
import com.newdjk.member.ui.entity.ImDataEntity;
import com.newdjk.member.ui.entity.JpushDataEntity;
import com.newdjk.member.ui.entity.JpushDataListEntity;
import com.newdjk.member.ui.entity.PushDataDaoEntity;
import com.newdjk.member.ui.entity.ResponseEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.HeadUitl;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.SQLiteUtils;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.VpSwipeRefreshLayout;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * 消息中心
 */
public class MessageFragment extends BasicFragment {
    Unbinder unbinder;
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
    @BindView(R.id.mServiceAvator)
    ImageView mServiceAvator;
    @BindView(R.id.mServiceUnReadTv)
    TextView mServiceUnReadTv;
    @BindView(R.id.mServiceUnReadNumberContainer)
    LinearLayout mServiceUnReadNumberContainer;
    @BindView(R.id.mServiceContainer)
    RelativeLayout mServiceContainer;
    @BindView(R.id.mServiceName)
    TextView mServiceName;
    @BindView(R.id.mServicecontent)
    TextView mServicecontent;
    @BindView(R.id.mService)
    RelativeLayout mService;
    @BindView(R.id.mSystemAvatar)
    ImageView mSystemAvatar;
    @BindView(R.id.mSystemlUnReadTv)
    TextView mSystemlUnReadTv;
    @BindView(R.id.mSystemUnReadContainer)
    LinearLayout mSystemUnReadContainer;
    @BindView(R.id.mSystemAvataLayout)
    RelativeLayout mSystemAvataLayout;
    @BindView(R.id.mSystemName)
    TextView mSystemName;
    @BindView(R.id.mSystemContent)
    TextView mSystemContent;
    @BindView(R.id.mSystemContainer)
    RelativeLayout mSystemContainer;
    @BindView(R.id.mOrderAvator)
    ImageView mOrderAvator;
    @BindView(R.id.mOrderUnReadTv)
    TextView mOrderUnReadTv;
    @BindView(R.id.mOrderUnReadNumberContainer)
    LinearLayout mOrderUnReadNumberContainer;
    @BindView(R.id.mOrderContainer)
    RelativeLayout mOrderContainer;
    @BindView(R.id.mOrderName)
    TextView mOrderName;
    @BindView(R.id.mOrdercontent)
    TextView mOrdercontent;
    @BindView(R.id.mOrder)
    RelativeLayout mOrder;
    @BindView(R.id.mPrescriptionAvatar)
    ImageView mPrescriptionAvatar;
    @BindView(R.id.mPrescriptionUnReadTv)
    TextView mPrescriptionUnReadTv;
    @BindView(R.id.mPrescriptionUnReadContainer)
    LinearLayout mPrescriptionUnReadContainer;
    @BindView(R.id.mPrescriptionAvataLayout)
    RelativeLayout mPrescriptionAvataLayout;
    @BindView(R.id.mPrescriptionName)
    TextView mPrescriptionName;
    @BindView(R.id.mPrescriptionContent)
    TextView mPrescriptionContent;
    @BindView(R.id.mPrescriptionContainer)
    RelativeLayout mPrescriptionContainer;
    @BindView(R.id.mFollowAvatar)
    ImageView mFollowAvatar;
    @BindView(R.id.mFollowUnReadTv)
    TextView mFollowUnReadTv;
    @BindView(R.id.mFollowUnReadContainer)
    LinearLayout mFollowUnReadContainer;
    @BindView(R.id.mFollowAvataLayout)
    RelativeLayout mFollowAvataLayout;
    @BindView(R.id.mFollowName)
    TextView mFollowName;
    @BindView(R.id.mFollowContent)
    TextView mFollowContent;
    @BindView(R.id.mFollowContainer)
    RelativeLayout mFollowContainer;
    @BindView(R.id.swipe_refresh_layout)
    VpSwipeRefreshLayout swipeRefreshLayout;
    private List<ImDataEntity> mConversationListData = new ArrayList<>();
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final String TAG = "MessageFragment";
    public static final String USERSIG = Contants.SIG;
    private MessageAdapter mAdapter;
    private String mId;
    private Gson mGson = new Gson();

    private List<PushDataDaoEntity> orderUnreadNumpushList = new ArrayList<>();
    private List<PushDataDaoEntity> serviceUnreadNumpushList = new ArrayList<>();
    private List<PushDataDaoEntity> prescriptionUnreadNumpushList = new ArrayList<>();
    private List<PushDataDaoEntity> followUnreadNumpushList = new ArrayList<>();
    private List<PushDataDaoEntity> sysUnreadNumpushList = new ArrayList<>();

    public static MessageFragment getFragment() {
        return new MessageFragment();
    }

    private static final int LOADING_SUCCESS = 2;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOADING_SUCCESS:
                    refreshView();
                    break;
                default:
                    break;
            }
        }
    };



    @Override
    protected int initViewResId() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {

        liearTitlebar.setBackgroundResource(R.color.theme);
        mId = String.valueOf(SpUtils.getInt(Contants.AccountId, 0));
        topTitle.setText("消息中心");
        topTitle.setTextColor(getResources().getColor(R.color.white));
        //tvRight.setText("加好友");
        // tvRight.setVisibility(View.VISIBLE);
        mAdapter = new MessageAdapter(mConversationListData);
        messageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        messageRecyclerView.setAdapter(mAdapter);
        messageRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
//        SQLiteUtils.getInstance().deleteAllImData();
        getAllPushList();
        initSwipeRefresh();
    }

    @Override
    protected void initListener() {
        mOrder.setOnClickListener(this);
        mService.setOnClickListener(this);
        mFollowContainer.setOnClickListener(this);
        mPrescriptionContainer.setOnClickListener(this);
        mSystemContainer.setOnClickListener(this);
        tvRight.setOnClickListener(this);
    }

    private void initSwipeRefresh() {
        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllPushList();
                refreshView();

            }
        });
    }


    @Override
    protected void initData() {

    }

    @Override
    protected void otherViewClick(View view) {

    }

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.UpdateConversation:
                mHandler.sendEmptyMessageDelayed(LOADING_SUCCESS, 1000);
                break;
            case MainConstant.UPDATEPUSHMESSAGELIST:
                getUnreadUnm();
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        topTitle.setText(getString(R.string.message_title));
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right:
                Intent intent = new Intent(mContext, AddFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.mOrder:
                //订单提醒
                Intent mOrderIntent = new Intent(mContext, PushListActivity.class);
                mOrderIntent.putExtra("action", "order");
                startActivity(mOrderIntent);
                break;
            case R.id.mService:
                //服务提醒
                Intent serviceIntent = new Intent(mContext, PushListActivity.class);
                serviceIntent.putExtra("action", "service");
                startActivity(serviceIntent);
                break;
            case R.id.mPrescriptionContainer:
                //处方提醒
                Intent renewalIntent = new Intent(mContext, PushListActivity.class);
                renewalIntent.putExtra("action", "precription");
                startActivity(renewalIntent);
                break;
            case R.id.mFollowContainer:
                //随访提醒
                Intent appointmentIntent = new Intent(mContext, PushListActivity.class);
                appointmentIntent.putExtra("action", "follow");
                startActivity(appointmentIntent);
                break;
            case R.id.mSystemContainer:
                //系统消息
                Intent systemIntent = new Intent(mContext, PushListActivity.class);
                systemIntent.putExtra("action", "system");
                startActivity(systemIntent);
                break;
        }
    }

    private void refreshView() {
        mConversationListData.clear();
        List<TIMConversation> conversionList = TIMManager.getInstance().getConversionList();
        Log.d(TAG,"最近会话列表1----"+conversionList.size());

        for (int i = 0; i < conversionList.size(); i++) {
            Log.d(TAG,"打印会话id=="+conversionList.get(i).getPeer()+" 类型"+conversionList.get(i).getType());
        }

        for (TIMConversation timConversation : conversionList) {
            if (timConversation.getType() == TIMConversationType.C2C || timConversation.getType() == TIMConversationType.Group) {
                String id = timConversation.getPeer();
                ConversationEntity conversationEntity = new ConversationEntity();
                List<TIMMessage> lastMsgs = timConversation.getLastMsgs(1);
                //未读消息
                long unreadMessageNum = timConversation.getUnreadMessageNum();
                conversationEntity.setUnReadNum(unreadMessageNum);
                int type = 1;
                if (timConversation.getType()== TIMConversationType.C2C ){
                    type=1;
                }else if (timConversation.getType() == TIMConversationType.Group){
                    type=2;
                }else {
                    type=0;
                }
                TIMMessage msg = lastMsgs.get(0);
                conversationEntity.setTimMessage(lastMsgs.get(0));
                TIMElem element = msg.getElement(0);
                if (element.getType() == TIMElemType.Text) {
                    TIMTextElem textElem = (TIMTextElem) element;
                    conversationEntity.setLastMsg(textElem.getText());
                } else if (element.getType() == TIMElemType.Sound) {
                    conversationEntity.setLastMsg("[语音消息]");
                } else if (element.getType() == TIMElemType.Video) {
                    conversationEntity.setLastMsg("[视频消息]");
                } else if (element.getType() == TIMElemType.Image) {
                    conversationEntity.setLastMsg("[图片消息]");
                }else if (element.getType() == TIMElemType.GroupTips){
                    conversationEntity.setLastMsg("[系统消息]");
                }
                else if (element.getType() == TIMElemType.Custom) {
                    TIMCustomElem customElem = (TIMCustomElem) msg.getElement(0);
                    String s = new String(customElem.getData());
                    CustomMessageEntity customMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                    Log.d(TAG, "自定义消息----" + s);
                    if (customMessageEntity != null) {
                        Log.d(TAG, "自定义消息pushdesc" + customMessageEntity.getPushDesc());
                        if (!TextUtils.isEmpty(customMessageEntity.getPushDesc())) {
                            conversationEntity.setLastMsg(customMessageEntity.getPushDesc());
                        } else if (!TextUtils.isEmpty(customMessageEntity.getTitle())) {
                            conversationEntity.setLastMsg(customMessageEntity.getTitle());
                        } else {
                            conversationEntity.setLastMsg("[系统消息]");
                        }
                    }
                }

                long timestamp = msg.timestamp();
                long unReadNum = conversationEntity.getUnReadNum();
                String nickName = "";
                String idfrom = "";
                String faceUrl = "";
                String lastMsg = "";
                //判断是自己发送的消息还是别人发送的消息
                String sender = msg.getSender();
                if (sender.equals(SpUtils.getString(Contants.ImId))) {
                    //是自己发消息 SpUtils.put(Contants.Name, entituy.getData().getName());
                    nickName = SpUtils.getString(Contants.Name);
                    faceUrl = SpUtils.getString(Contants.FaceUrl);
                    Log.d(TAG, "获取头像地址----" + faceUrl);
                } else {
                    //是别人发消息
                    if (msg.getSenderProfile() != null) {
                        nickName = msg.getSenderProfile().getNickName();
                        faceUrl = msg.getSenderProfile().getFaceUrl();
                        Log.d(TAG, "获取头像地址----" + faceUrl);
                    }

                }
                idfrom = id;
                lastMsg = conversationEntity.getLastMsg();

                ImDataEntity dataBean = new ImDataEntity(null, lastMsg, timestamp, idfrom, faceUrl, nickName, unReadNum,type);
                mConversationListData.add(dataBean);

            }
            Collections.sort(mConversationListData, new SortByTime());
            mAdapter.setNewData(mConversationListData);
            mAdapter.notifyDataSetChanged();
        }



//        if (doctorIdList != null && doctorIdList.size() > 0) {
//            Log.d(TAG,"最近会话列表2----"+doctorIdList.size()+"   "+doctorIdList.toString());
//
//            TIMFriendshipManager.getInstance().getUsersProfile(doctorIdList, new TIMValueCallBack<List<TIMUserProfile>>() {
//                @Override
//                public void onError(int i, String s) {
//                    toast(s);
//                }
//
//                @Override
//                public void onSuccess(List<TIMUserProfile> timUserProfiles) {
//                    mConversationListData.clear();
//                    Log.d(TAG,"最近会话列表3----"+timUserProfiles.size());
//                    for (int i = 0; i < timUserProfiles.size(); i++) {
//                        TIMUserProfile tIMUserProfile = timUserProfiles.get(i);
//                        ConversationEntity conversationEntity = new ConversationEntity();
//                        //获取会话扩展实例
//                        TIMConversation con = TIMManager.getInstance().getConversation(TIMConversationType.C2C, tIMUserProfile.getIdentifier());
//                        long unreadMessageNum = con.getUnreadMessageNum();
//                        conversationEntity.setUnReadNum(unreadMessageNum);
//                        List<TIMMessage> lastMsgs = con.getLastMsgs(1);
//                        TIMMessage msg = lastMsgs.get(0);
//                        conversationEntity.setTimMessage(lastMsgs.get(0));
//                        TIMElem element = msg.getElement(0);
//                        if (element.getType() == TIMElemType.Text) {
//                            TIMTextElem textElem = (TIMTextElem) element;
//                            conversationEntity.setLastMsg(textElem.getText());
//                        } else if (element.getType() == TIMElemType.Sound) {
//                            conversationEntity.setLastMsg("语音消息");
//                        }else if(element.getType() == TIMElemType.Image){
//                            conversationEntity.setLastMsg("图片消息");
//                        }
//
//                        long timestamp = msg.timestamp();
//                        long unReadNum = conversationEntity.getUnReadNum();
//                        String nickName = tIMUserProfile.getNickName();
//                        String id = tIMUserProfile.getIdentifier();
//                        String faceUrl = tIMUserProfile.getFaceUrl();
//                        String lastMsg = conversationEntity.getLastMsg();
//                        ImDataEntity dataBean = new ImDataEntity(null, lastMsg, timestamp, id, faceUrl, nickName, unReadNum);
//                        mConversationListData.add(dataBean);
//                    }
//                    Collections.sort(mConversationListData, new SortByTime());
//                    mAdapter.setNewData(mConversationListData);
//                    mAdapter.notifyDataSetChanged();
//                }
//            });
//        } else {
//            mAdapter.setNewData(mConversationListData);
//            mAdapter.notifyDataSetChanged();
//        }
    }

    private long getUnreadMessageNum(TIMConversation timConversation) {
        Log.i("zdp", "num=" + timConversation.getUnreadMessageNum());
        return timConversation.getUnreadMessageNum();
    }

    class SortByTime implements Comparator {
        public int compare(Object o1, Object o2) {
            ImDataEntity s1 = (ImDataEntity) o1;
            ImDataEntity s2 = (ImDataEntity) o2;
            if (s1.getLastTime() < s2.getLastTime())
                return 1;
            return -1;
        }
    }

    private void getAllPushList() {
        try {
            List<PushDataDaoEntity> list = SQLiteUtils.getInstance().selectAllContactsByDoctorId(mId);
            if (list.size() > 0) {
                Date time = list.get(0).getTime();
                String dateTime = mFormatter.format(time);
                queryPatientAppMessageByPage(mId, dateTime);
            } else {
                queryPatientAppMessageByPage(mId, null);
            }
        }catch (Exception e){

        }

    }

    private void queryPatientAppMessageByPage(final String id, final String time) {
        String accountId = String.valueOf(id);
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("AccountId", accountId);
        if (time != null) {
            requestMap.put("BeginTime", time);
        }

        mMyOkhttp.post().url(HttpUrl.QueryPatientAppMessageByPage).headers(HeadUitl.instance.getHeads()).params(requestMap).tag(this).enqueue(new GsonResponseHandler<ResponseEntity<JpushDataListEntity>>() {
            @Override
            public void onSuccess(int statusCode, ResponseEntity<JpushDataListEntity> entity) {
                //    mConsultMessageAdapter.setDatalist( entity.getData());
                swipeRefreshLayout.setRefreshing(false);
                if (entity.getCode() == 0) {
                    if (entity.getData().getReturnData() != null && entity.getData().getReturnData().size() > 0) {
                        for (int i = 0; i < entity.getData().getReturnData().size(); i++) {
                            Log.i("HomeFragment", "title=" + time + ",t=" + entity.getData().getReturnData().get(i).getTitle());
                            if (time != null && time.equals(entity.getData().getReturnData().get(i).getSendTime())) {

                            } else {
                                String extras = entity.getData().getReturnData().get(i).getExtras();
                                Log.i("HomeFragment", "extras=" + extras);
                                //  ExtrasDataEntity JpushData = mGson.fromJson(extras, ExtrasDataEntity.class);
                                String title = entity.getData().getReturnData().get(i).getTitle();
                                String content = entity.getData().getReturnData().get(i).getMessage();
                                String time = entity.getData().getReturnData().get(i).getSendTime();
                                Date dateTime = null;
                                try {
                                    dateTime = mFormatter.parse(time);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                              /*  boolean isRead;
                                if (extras != null && !extras.equals("")) {
                                    isRead = false;
                                } else {
                                    isRead = true;
                                }*/
                                int isRead = entity.getData().getReturnData().get(i).getStatus();
                                Log.i("HomeFragment", "time=" + time);
                                //  String data = mGson.toJson(extras);
                                JpushDataEntity jpushDataEntity = entity.getData().getReturnData().get(i);
                                SQLiteUtils.getInstance().addPushData(new PushDataDaoEntity(null, title, content, extras, dateTime, isRead == 0 ? false : true, id,
                                        jpushDataEntity.getMsgType(),
                                        jpushDataEntity.getModuleType(),
                                        jpushDataEntity.getId()));
                            }
                        }
                    }
                    getUnreadUnm();

                } else {
                    toast(entity.getMessage());
                }

            }

            @Override
            public void onFailures(int statusCode, String errorMsg) {
                Log.i("HomeFragment", "2222");
                swipeRefreshLayout.setRefreshing(false);
                CommonMethod.requestError(statusCode, errorMsg);
            }
        });
    }

    private void getUnreadUnm() {
        List<PushDataDaoEntity> pushList = SQLiteUtils.getInstance().selectAllContactsByDoctorId(mId);
        if (orderUnreadNumpushList != null) {
            orderUnreadNumpushList.clear();
        }
        if (serviceUnreadNumpushList != null) {
            serviceUnreadNumpushList.clear();
        }
        if (prescriptionUnreadNumpushList != null) {
            prescriptionUnreadNumpushList.clear();
        }
        if (followUnreadNumpushList != null) {
            followUnreadNumpushList.clear();
        }
        if (sysUnreadNumpushList != null) {
            sysUnreadNumpushList.clear();
        }
        int orderUnreadNum = 0; //订单提醒的数量
        int serviceUnreadNum = 0;//服务提醒的数量
        int prescriptionUnreadNum = 0;//处方提醒
        int followUnreadNum = 0;//随访提醒
        int sysUnreadNum = 0; //系统消息
        for (int i = 0; i < pushList.size(); i++) {
            PushDataDaoEntity pushDataDaoEntity = pushList.get(i);
            boolean isread = pushDataDaoEntity.isRead;
//            if (!isread) {
            int msgType = pushDataDaoEntity.getMsgType();
            Log.i(">>>>", "getUnreadUnm: " + msgType);
            switch (msgType) {
                //1：订单提醒 2：服务提醒 3：处方提醒 4 随访提醒 5 系统消息
                case 1:
//                    mOrdercontent.setText(pushDataDaoEntity.getContent());
                    orderUnreadNumpushList.add(pushDataDaoEntity);
                    if (!isread) {
                        orderUnreadNum += 1;
                    }
                    break;
                case 2:
                case 13:
//                    mServicecontent.setText(pushDataDaoEntity.getContent());
                    serviceUnreadNumpushList.add(pushDataDaoEntity);
                    if (!isread) {
                        serviceUnreadNum += 1;
                    }
                    break;
                case 3:
//                    mPrescriptionContent.setText(pushDataDaoEntity.getContent());
                    prescriptionUnreadNumpushList.add(pushDataDaoEntity);
                    if (!isread) {
                        prescriptionUnreadNum += 1;
                    }
                    break;
                case 4:
//                    mFollowContent.setText(pushDataDaoEntity.getContent());
                    followUnreadNumpushList.add(pushDataDaoEntity);
                    if (!isread) {
                        followUnreadNum += 1;
                    }
                    break;
                case 5:
//                    mSystemContent.setText(pushDataDaoEntity.getContent());
                    sysUnreadNumpushList.add(pushDataDaoEntity);
                    if (!isread) {
                        sysUnreadNum += 1;
                    }
                    break;

            }
//                }
        }
        //订单提醒的数量
        if (orderUnreadNum > 0) {
            mOrderUnReadNumberContainer.setVisibility(View.VISIBLE);
            mOrderUnReadTv.setText(String.valueOf(orderUnreadNum));
            mOrdercontent.setText(orderUnreadNumpushList.get(0).getContent());
        } else {
            mOrderUnReadNumberContainer.setVisibility(View.GONE);
        }
        //服务提醒的数量
        if (serviceUnreadNum > 0) {
            mServiceUnReadNumberContainer.setVisibility(View.VISIBLE);
            mServiceUnReadTv.setText(String.valueOf(serviceUnreadNum));
            mServicecontent.setText(serviceUnreadNumpushList.get(0).getContent());
        } else {
            mServiceUnReadNumberContainer.setVisibility(View.GONE);
        }
        //处方提醒
        if (prescriptionUnreadNum > 0) {//0
            mPrescriptionUnReadContainer.setVisibility(View.VISIBLE);
            mPrescriptionUnReadTv.setText(String.valueOf(prescriptionUnreadNum));
            mPrescriptionContent.setText(prescriptionUnreadNumpushList.get(0).getContent());
        } else {
            mPrescriptionUnReadContainer.setVisibility(View.GONE);
        }
        //随访提醒
        if (followUnreadNum > 0) {//2
            mFollowUnReadContainer.setVisibility(View.VISIBLE);
            mFollowUnReadTv.setText(String.valueOf(followUnreadNum));
            mFollowContent.setText(followUnreadNumpushList.get(0).getContent());
        } else {
            mFollowUnReadContainer.setVisibility(View.GONE);
        }
        //系统消息
        if (sysUnreadNum > 0) {
            mSystemUnReadContainer.setVisibility(View.VISIBLE);
            mSystemlUnReadTv.setText(String.valueOf(sysUnreadNum));
            mSystemContent.setText(sysUnreadNumpushList.get(0).getContent());
        } else {
            mSystemUnReadContainer.setVisibility(View.GONE);
        }
    }
}
