package com.newdjk.member.service;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.broadcast.NotificationClickReceiver;
import com.newdjk.member.tools.Contants;
import com.newdjk.member.ui.activity.DialogActivity;
import com.newdjk.member.ui.activity.MainActivity;
import com.newdjk.member.ui.entity.AppEntity;
import com.newdjk.member.ui.entity.CustomMessageEntity;
import com.newdjk.member.ui.entity.RejectCallTip;
import com.newdjk.member.ui.entity.UpdateEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.AppUtils;
import com.newdjk.member.utils.GroupButtonDialog;
import com.newdjk.member.utils.InstallApkUtil;
import com.newdjk.member.utils.MessageEvent;
import com.newdjk.member.utils.NetWorkUtil;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.utils.TimeUtil;
import com.newdjk.member.utils.ToastUtil;
import com.tencent.TIMConversation;
import com.tencent.TIMConversationType;
import com.tencent.TIMCustomElem;
import com.tencent.TIMElem;
import com.tencent.TIMElemType;
import com.tencent.TIMFriendshipManager;
import com.tencent.TIMManager;
import com.tencent.TIMMessage;
import com.tencent.TIMMessageListener;
import com.tencent.TIMSNSSystemElem;
import com.tencent.TIMTextElem;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;
import com.tencent.qalsdk.util.ALog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service implements TIMMessageListener {
    private static final String TAG = "MyService";
    private Ringtone mRingtone;
    private Gson mGson = new Gson();
    private TIMConversation mConversation;
    private AlertDialog mIncomingDlg;
    public static String serviceTime;
    protected com.lxq.okhttp.MyOkHttp mMyOkhttp;
    private String mContent;
    private static final String PUSH_CHANNEL_ID = "PUSH_NOTIFY_ID";
    private static final String PUSH_CHANNEL_NAME = "PUSH_NOTIFY_NAME";
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;
    String PRIMARY_CHANNEL = "my_channel_01";
    private Notification mNotification;
    private int channelID = 1;
    private String title = "芸医生";

    public MyService() {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mRingtone = RingtoneManager.getRingtone(MyApplication.getContext(), notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
        EventBus.getDefault().register(this);
//        GetAllRecordForDoctor();
        TIMManager.getInstance().addMessageListener(this);
        initNotification();
    }

    @Override
    public boolean onNewMessages(List<TIMMessage> list) {
        boolean needToNotify = false;
        String imId = null;
        long unreadNum = 0;
        long timeStamp = 0;
        MessageEvent messageEvent = new MessageEvent();
        //messageEvent.setmType(MainConstant.UpdateMessageListEntity);
        messageEvent.setmType(MainConstant.UpdateChatActivity);
        messageEvent.setList(list);
        EventBus.getDefault().post(messageEvent);
        for (TIMMessage timMessage : list) {
            if (timMessage.getConversation().getType().equals(TIMConversationType.C2C)||timMessage.getConversation().getType().equals(TIMConversationType.Group)) {
                //只要有一条c2c消息，就响铃通知用户
                imId = timMessage.getConversation().getPeer();

                imId = timMessage.getConversation().getPeer();
                if (timMessage.getConversation().getType().equals(TIMConversationType.C2C)){
                    if ( MyApplication.mImId != null &&  MyApplication.mImId.equals(imId) || timMessage.isSelf()) {
                        return false;
                    }
                }

                mConversation = timMessage.getConversation();
                unreadNum = getUnreadMessageNum(mConversation);
                List<TIMMessage> lastMsgs = mConversation.getLastMsgs(1);
                TIMMessage msg = lastMsgs.get(0);
                TIMElem element = msg.getElement(0);
                if (element.getType() == TIMElemType.Text) {
                    TIMTextElem textElem = (TIMTextElem) element;
                    mContent = textElem.getText();
                } else if (element.getType() == TIMElemType.Sound) {
                    mContent = "[语音消息]";
                } else if (element.getType() == TIMElemType.Custom) {
                    mContent = "[回复一条新消息]";
                } else if (element.getType() == TIMElemType.Image) {
                    mContent = "[图片消息]";
                }else if (element.getType() == TIMElemType.GroupTips){
                    MessageEvent messageEvent2 = new MessageEvent();
//        messageEvent.setmType(MainConstant.UpdateMessageListEntity);
                    messageEvent2.setmType(MainConstant.ChangeGroupName);
                    messageEvent2.setList(list);
                    EventBus.getDefault().post(messageEvent2);
                    mContent = "[系统消息]";
                }

                if (!MyApplication.isChatView) {
                    Log.d(TAG, element.toString());
                    getNewMessage(messageEvent, list, imId);
                }


                timeStamp = msg.timestamp();
                if (timMessage.getElement(0).getType() == TIMElemType.Custom) {
                    Log.i("zdp", "tipsTHFGH");
                    TIMCustomElem customElem = (TIMCustomElem) timMessage.getElement(0);
                    String s = new String(customElem.getData());
                    Log.i("MyService", "s=" + s);
                    CustomMessageEntity CustomMessageEntity = mGson.fromJson(s, CustomMessageEntity.class);
                    CustomMessageEntity.ExtDataBean extraData = CustomMessageEntity.getExtData();
                    if (extraData != null) {
                        int type = extraData.getType();
                        if (type == 38) {
                            //   EventBus.getDefault().post(new UpdateViewEntity(null));
//                            GetAllRecordForDoctor();
                            MessageEvent messageEvent1 = new MessageEvent();
                            messageEvent1.setmType(MainConstant.UpdateConversation);
                            messageEvent1.setList(null);
                            EventBus.getDefault().post(messageEvent1);
//                            EventBus.getDefault().post(new UpdateImMessageEntity(null));
                            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                            vibrator.vibrate(new long[]{0, 200, 200, 200}, -1);
                            mRingtone.play();
                            return false;
                        }
//                        else if (type == 36) {
//                           CustomMessageEntity.ExtDataBean.DataBean dataBean = extraData.getData();
//                            if (dataBean != null) {
//                                //    int recordId = dataBean.getRecordId();
//                                int serviceType = dataBean.getRecordId();
//                                List<AllRecordForDoctorEntity> allRecordForDoctorList = new ArrayList<>();
//                                switch (serviceType) {
//                                    case 1:
//                                        allRecordForDoctorList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1101", imId);
//                                        break;
//                                    case 2:
//                                        allRecordForDoctorList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1102", imId);
//                                        break;
//                                    case 3:
//                                        allRecordForDoctorList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1103", imId);
//                                        break;
//
//                                }
//                                if (allRecordForDoctorList.size() > 0) {
//                                    AllRecordForDoctorEntity AllRecordForDoctorEntity = allRecordForDoctorList.get(0);
//                                    SQLiteUtils.getInstance().deleteImData(AllRecordForDoctorEntity);
//                                }
//                                EventBus.getDefault().post(new UpdateImMessageEntity(null));
//                                return false;
//                            }
//                        }
                        switch (type) {
                            case 129:
                                int callId = extraData.getData().getAVRoomID();
                                String sender = extraData.getData().getTargets();
                                String userSig = extraData.getData().getUserSig();
                                //newIncomingCall(callId, sender, userSig);
                                Intent intent = new Intent(MyService.this, DialogActivity.class);
                                intent.putExtra("sender", sender);
                                intent.putExtra("callId", callId);
                                intent.putExtra("identifier", imId);
                                intent.putExtra("userSig", userSig);
                                intent.putExtra("action", "dialogActivity");
                                startActivity(intent);
                                break;
                            case 131:
                                EventBus.getDefault().post(new RejectCallTip(true));

                                break;
                            case 134:
                                EventBus.getDefault().post(new RejectCallTip(true));

                                break;
                            case 133:
                                EventBus.getDefault().post(new RejectCallTip(true));

                                break;
                            case 130:

                                break;

                        }
                    }
                }
                needToNotify = true;
            } else if (timMessage.getConversation().getType().equals(TIMConversationType.Group)) {
                needToNotify = true;
                //如果有系统通知，刷新好友列表
                      /*  ContactModel contactModel = new ContactModel();
                        contactModel.getFriendList();*/
            } else if (timMessage.getElement(0) instanceof TIMSNSSystemElem) {
                      /*  ContactModel contactModel = new ContactModel();
                        contactModel.getFriendList();*/
                needToNotify = true;
            }
        }
        if (!needToNotify) {
            return false;
        }
        MessageEvent messageEvent1 = new MessageEvent();
//        messageEvent.setmType(MainConstant.UpdateMessageListEntity);
        messageEvent1.setmType(MainConstant.UpdateConversation);
        messageEvent1.setList(list);
        EventBus.getDefault().post(messageEvent1);
//        String serviceCode = null;
//        List<AllRecordForDoctorEntity> videoList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1102", imId);
//        AllRecordForDoctorEntity allRecordForDoctorEntity = null;
//        if (videoList.size() > 0) {
//            allRecordForDoctorEntity = videoList.get(0);
//            serviceCode = "1102";
//
//        } else {
//            List<AllRecordForDoctorEntity> pictureList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1101", imId);
//            if (pictureList.size() > 0) {
//                allRecordForDoctorEntity = pictureList.get(0);
//                serviceCode = "1102";
//            } else {
//                List<AllRecordForDoctorEntity> renewalList = SQLiteUtils.getInstance().selectImDataByServiceCodeAndId("1103", imId);
//                if (renewalList.size() > 0) {
//                    allRecordForDoctorEntity = renewalList.get(0);
//                    serviceCode = "1102";
//                }
//            }
//
//        }
//        if (allRecordForDoctorEntity != null) {
//            allRecordForDoctorEntity.setUnReadNum(unreadNum);
//            allRecordForDoctorEntity.setTimeStamp(timeStamp);
//            SQLiteUtils.getInstance().updateImData(allRecordForDoctorEntity);
//        }
//        EventBus.getDefault().post(new UpdateImMessageEntity(serviceCode));
//        List<String> doctorIdList = new ArrayList<>();
//        doctorIdList.add(imId);
//        TIMFriendshipManager.getInstance().getUsersProfile(doctorIdList, new TIMValueCallBack<List<TIMUserProfile>>(){
//                    @Override
//                    public void onError(int i, String s) {
//
//                    }
//
//                    @Override
//                    public void onSuccess(List<TIMUserProfile> timUserProfiles) {
//                        if ( timUserProfiles.size()>0) {
//                            TIMUserProfile tIMUserProfile = timUserProfiles.get(0);
//                            String name = tIMUserProfile.getNickName();
//                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                                NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_ID, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
//                                channel.enableLights(true);
//                                channel.enableVibration(true);
//                                if (mNotificationManager != null) {
//                                    mNotificationManager.createNotificationChannel(channel);
//                                }
//                            }
//
//                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(),"default");
//                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
//                            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                            PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
//                            mBuilder.setContentTitle(name)//设置通知栏标题
//                                    .setContentText(mContent)
//                                    .setChannelId(PUSH_CHANNEL_ID)
//                                    .setContentIntent(intent) //设置通知栏单击意图
//                                    /*.setNumber(++pushNum) //设置通知集合的数量
//                                    .setTicker(senderStr+":"+contentStr) //通知首次出现在通知栏，带上升动画效果的*/
//                                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
//                                    .setDefaults(Notification.DEFAULT_ALL)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合
//                                    .setSmallIcon(R.mipmap.ic_logo);//设置通知小 ICON
//                            Notification notify = mBuilder.build();
//                            notify.flags |= Notification.FLAG_AUTO_CANCEL;
//                            mNotificationManager.notify(1110, notify);
//                        }
//                    }
//                });
    /*    Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{0, 200, 200, 200}, -1);
        mRingtone.play();*/
        return true;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        TIMManager.getInstance().removeMessageListener(this);
        super.onDestroy();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(MessageEvent event) {
        switch (event.getmType()) {
            case MainConstant.CANCELVIDEO:
                int action = event.getAction();
                String identifier = event.getIdentifity();
                long time = event.time;
                sendVideoMessage(action, identifier, time);
                break;

        }

    }

    /*  public void newIncomingCall(final int callId, String sender, final String userSig) {
          Log.i("dd", "callId=" + callId);
          if (null != mIncomingDlg) {  // 关闭遗留来电对话框
              mIncomingDlg.dismiss();
          }
          //  mCurIncomingId = callId;
          mIncomingDlg = new AlertDialog.Builder(getApplicationContext())
                  .setTitle("来电提醒 ")
                  .setMessage("New Call From " + sender)
                  .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                      @Override
                      public void onClick(DialogInterface dialog, int which) {
                          mIncomingDlg.dismiss();
                          sendVideoMessage(130);
                          //acceptCall(callId, notification.getSponsorId(), callType);
                          Intent roomIntent = new Intent(MyService.this, RoomActivity.class);
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
                          //   int ret = ILVCallManager.getInstance().rejectCall(mCurIncomingId);
                      }
                  })
                  .create();
          mIncomingDlg.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
          mIncomingDlg.setCanceledOnTouchOutside(false);
          mIncomingDlg.show();
      }*/
    public void sendVideoMessage(int type, String identify, long time) {
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
        sendCustomMessage(json, "", identify);
    }

    private void sendCustomMessage(String data, String desc, String identify) {
        TIMConversation conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, identify);
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
//发送消息
        conversation.sendMessage(timMessage, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                //updateRecyclerView();
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                //  updateRecyclerView();
            }
        });
    }


    private long getUnreadMessageNum(TIMConversation timConversation) {
        Log.i("zdp", "num=" + timConversation.getUnreadMessageNum());

        return timConversation.getUnreadMessageNum();
    }

    private void getNewMessage(MessageEvent messageEvent, List<TIMMessage> list, String imId) {
        MessageEvent messageEvent1 = new MessageEvent();
//        messageEvent.setmType(MainConstant.UpdateMessageListEntity);
        messageEvent.setmType(MainConstant.UpdateConversation);
        messageEvent.setList(list);
        EventBus.getDefault().post(messageEvent);
        List<String> doctorIdList = new ArrayList<>();
        doctorIdList.add(imId);
        TIMFriendshipManager.getInstance().getUsersProfile(doctorIdList, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                if (timUserProfiles.size() > 0) {
                    TIMUserProfile tIMUserProfile = timUserProfiles.get(0);
                    String name = tIMUserProfile.getNickName();
                    showNotification(tIMUserProfile);
                }
            }
        });
    }

    //初始化通知
    private void showNotification(TIMUserProfile tIMUserProfile) {
        notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配8.0,自行查看8.0的通知，主要是NotificationChannel
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                    "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(chan1);
            builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL);
        } else {
            builder = new NotificationCompat.Builder(this, null);
        }
        builder.setContentText(mContent)//notification的一些设置，具体的可以去官网查看
                .setContentTitle(tIMUserProfile.getNickName()) //设置通知标题
                .setTicker(mContent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo))
                .setSmallIcon(R.mipmap.ic_logo);
        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
        builder.setContentIntent(intent);
        // 如果存在，启动安装流程
        mNotification = builder.build();
        notificationManager.notify(channelID, mNotification);


    }


    private String mApkUrl = "";
    public static boolean isDownload = false;
    private static final String DOWNLOAD_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + System.currentTimeMillis() + "member.apk";
    private int mCurentPercent = 0;
    private Notification notification;
    private GroupButtonDialog mDialog;


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void downloadApp(AppEntity entity) {
        Log.d("Myservice", "收到下载app消息提示");

        final String apkUrl = entity.getData().getAppPath();
        mApkUrl = apkUrl;
        int apkCode = Integer.parseInt(entity.getData().getAppVersion());
        int versionCode = AppUtils.getAppVersionCode(this);
        ToastUtil.setToast("正在下载中，在顶部状态栏中可以查看进度！");
        startDownload();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(UpdateEntity updateEntity) {
        Log.d("Myservice", "收到安装app消息");
        if (isDownload) {
            ToastUtil.setToast("app正在更新中，请下载完成后点击进行安装");
            return;
        }
        InstallApkUtil installApkUtil = new InstallApkUtil();
        installApkUtil.installApk(MyService.this, DOWNLOAD_PATH);

    }

    private void startDownload() {

        FileDownloader.getImpl().create(mApkUrl).setWifiRequired(false).setPath(DOWNLOAD_PATH).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                isDownload = true;
                int p = (int) ((double) soFarBytes / (double) totalBytes * 100);
                Log.d(TAG, "正在下载" + soFarBytes + "   总数" + totalBytes);

                mCurentPercent = p;
                builder.setProgress(100, p, false);
                builder.setContentText("下载进度:" + p + "%");
                builder.setContentTitle("正在更新...");
                notification = builder.build();
                notificationManager.notify(channelID, notification);

            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {


            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.d(TAG, "下载完成");
                isDownload = false;
                if (builder != null) {

                    mCurentPercent = 100;
                    builder.setProgress(100, 100, false);
                    builder.setContentText("下载完成:" + 100 + "%");
                    builder.setContentTitle("下载完成，点击进行安装");
                    notification = builder.build();
                    notificationManager.notify(channelID, notification);
                }

                InstallApkUtil installApkUtil = new InstallApkUtil();
                installApkUtil.installApk(MyService.this, DOWNLOAD_PATH);

            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {

            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.d(TAG, "下载失败");
                isDownload = false;
                if (builder != null) {
                    builder.setContentTitle("下载失败");
                    notification = builder.build();
                    notificationManager.notify(channelID, notification);
                }

                if (mDialog == null) {
                    mDialog = new GroupButtonDialog(MyService.this);
                }
                mDialog.show("下载失败", "下载失败，是否重新下载？", new GroupButtonDialog.DialogListener() {
                    @Override
                    public void cancel() {
                        mDialog = null;
                    }

                    @Override
                    public void confirm() {
                                   /* UpdateManage updateManage = new UpdateManage(getContext(), apkUrl);
                                    updateManage.showDownloadDialog();*/
                        mDialog = null;
                        if (TextUtils.isEmpty(mApkUrl)) {
                            return;
                        }
                        Log.d(TAG, "重新下载");
                        if (NetWorkUtil.isNetworkAvailable(getApplicationContext())) {
                            ALog.w(TAG, "恢复任务失败，网络未连接");
                            ToastUtil.setToast("网络连接失败，请检查网络");
                            return;
                        }
                        initNotification();
                    }
                });
            }

            @Override
            protected void warn(BaseDownloadTask task) {


            }
        }).start();
    }

    //初始化通知
    private void initNotification() {
        notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {//适配8.0,自行查看8.0的通知，主要是NotificationChannel
            NotificationChannel chan1 = new NotificationChannel(PRIMARY_CHANNEL,
                    "Primary Channel", NotificationManager.IMPORTANCE_DEFAULT);
            chan1.setLightColor(Color.GREEN);
            chan1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            notificationManager.createNotificationChannel(chan1);
            builder = new NotificationCompat.Builder(this, PRIMARY_CHANNEL);
        } else {
            builder = new NotificationCompat.Builder(this, null);
        }
        builder.setContentText("下载进度:" + "0%")//notification的一些设置，具体的可以去官网查看
                .setContentTitle("正在更新...") //设置通知标题
                .setTicker("正在下载")
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setOnlyAlertOnce(true)
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_logo))
                .setSmallIcon(R.mipmap.ic_logo);
        builder.setProgress(100, 0, false);//显示下载进度
        Intent intent = new Intent(this, NotificationClickReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        // 如果存在，启动安装流程
        mNotification = builder.build();
    }


}
