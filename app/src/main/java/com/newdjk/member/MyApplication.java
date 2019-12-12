package com.newdjk.member;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.huawei.android.hms.agent.HMSAgent;
import com.liulishuo.filedownloader.FileDownloader;
import com.lxq.okhttp.MyOkHttp;
import com.lxq.okhttp.demo.DownloadMgr;
import com.lxq.okhttp.persistentcookiejar.ClearableCookieJar;
import com.lxq.okhttp.persistentcookiejar.PersistentCookieJar;
import com.lxq.okhttp.persistentcookiejar.cache.SetCookieCache;
import com.lxq.okhttp.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.lxq.okhttp.utils.OkHttpLogUtils;
import com.maning.librarycrashmonitor.MCrashMonitor;
import com.maning.librarycrashmonitor.listener.MCrashCallBack;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.greendao.gen.DaoMaster;
import com.newdjk.member.greendao.gen.DaoSession;
import com.newdjk.member.ui.entity.MonitorDataEntity;
import com.newdjk.member.uploadimagelib.MainConstant;
import com.newdjk.member.utils.BoxingGlideLoader;
import com.newdjk.member.utils.BoxingUcrop;
import com.newdjk.member.utils.DefaultMediaLoader;
import com.newdjk.member.utils.FixDefaultMediaLoader;
import com.newdjk.member.utils.MessageObservable;
import com.newdjk.member.utils.MyOkHttpContext;
import com.newdjk.member.wxapi.WXPayEntryActivity;
import com.tencent.TIMManager;
import com.tencent.TIMOfflinePushListener;
import com.tencent.TIMOfflinePushNotification;
import com.tencent.TIMOfflinePushSettings;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.ilivesdk.ILiveConstants;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.ilivesdk.adapter.CommonConstants;
import com.tencent.ilivesdk.core.ILiveLog;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.qalsdk.sdk.MsfSdkUtils;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import lib_zxing.activity.ZXingLibrary;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

//import com.tencent.livesdk.ILVLiveConfig;
//import com.tencent.livesdk.ILVLiveManager;

public class MyApplication extends Application {
    private static final String TAG = "MyApplication";
    public static final String MIPUSH_APPID ="2882303761517947258" ;
    public static final String MIPUSH_APPKEY ="5481794764258" ;
    private MyOkHttp mMyOkHttp;
    private DownloadMgr mDownloadMgr;
    private static MyApplication application;
    private static int mainTid;
    private static Context context;
    public static boolean LOG_DEBUG = true;//输出日志工具类
    public static IWXAPI mWxApi;
    public static String mRegistrationId;
    public static int accountId;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private List<MonitorDataEntity> monitorDataEntityList;
    public static BridgeWebView mBridgeWebView;
    public static String mImId;
    public static String mRegId;
    public static long busid;
    public static  boolean isChatView =false;
    public final static List<BasicActivity> mActivities = new LinkedList<BasicActivity>();

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(MyApplication.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initDate();
        openStetho();
        if (BuildConfig.IS_DEBUG) {
            MCrashMonitor.init(this, true, new MCrashCallBack() {
                @Override
                public void onCrash(File file) {
                    //可以在这里保存标识，下次再次进入把日志发送给服务器
                    Log.i(TAG, "CrashMonitor回调:" + file.getAbsolutePath());
                }
            });
            CrashReport.initCrashReport(getApplicationContext(), "a56b482cf6", false);
        } else {
            CrashReport.initCrashReport(getApplicationContext(), "a56b482cf6", false);

        }
      /*  if (!BuildConfig.DEBUG) {
            openStetho();
        }*/

        FileDownloader.setup(this);
        initOkHttp();
        initBoxing();
        registToWX();
        setDatabase();
        initIm();
        initJpush();
        if(MsfSdkUtils.isMainProcess(this)) {
            Log.d("MyApplication", "main process");

            registerPush();
        }

    }

    private void initIm() {
        ILiveSDK.getInstance().setCaptureMode(ILiveConstants.CAPTURE_MODE_SURFACETEXTURE);
        ILiveLog.setLogLevel(ILiveLog.TILVBLogLevel.DEBUG);
        ILiveSDK.getInstance().initSdk(this, BuildConfig.IM_APP_ID, BuildConfig.IM_ACCOUNT_TYPE);
        // 老用户使用IMSDK通道
        ILiveSDK.getInstance().setChannelMode(CommonConstants.E_ChannelMode.E_ChannelIMSDK);
        ILVLiveManager.getInstance().init(new ILVLiveConfig()
                .setLiveMsgListener(MessageObservable.getInstance()));
        //腾讯云初始化
        if (MsfSdkUtils.isMainProcess(this)) {    // 仅在主线程初始化

            TIMManager.getInstance().setOfflinePushListener(new TIMOfflinePushListener() {
                @Override
                public void handleNotification(TIMOfflinePushNotification notification) {
                    Log.d("MyApplication", "recv offline push");
                    // 这里的 doNotify 是 ImSDK 内置的通知栏提醒，应用也可以选择自己利用回调参数 notification 来构造自己的通知栏提醒
                    notification.doNotify(getApplicationContext(), R.mipmap.logo);
                }
            });

            TIMOfflinePushSettings settings = new TIMOfflinePushSettings();
//开启离线推送
            settings.setEnabled(true);
//设置收到 C2C 离线消息时的提示声音，这里把声音文件放到了 res/raw 文件夹下
//设置收到群离线消息时的提示声音，这里把声音文件放到了 res/raw 文件夹下
            TIMManager.getInstance().configOfflinePushSettings(settings);

        }
        TIMManager.getInstance().init(getApplicationContext());
    }

    private void initJpush() {
        mRegistrationId = JPushInterface.getRegistrationID(this);
        Log.d(TAG, "mRegistrationId1=" + mRegistrationId);
        JPushInterface.setDebugMode(true);
    }

    private void initBoxing() {
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
        ((DefaultMediaLoader) DefaultMediaLoader.getInstance()).init(new FixDefaultMediaLoader());
    }

    private void initOkHttp() {
        MyOkHttpContext.setContext(this);
        OkHttpLogUtils.isDebug = BuildConfig.IS_DEBUG;
        //持久化存储cookie
        ClearableCookieJar cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(getApplicationContext()));
        //log拦截器
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        //自定义OkHttp
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                .cookieJar(cookieJar)       //设置开启cookie
                .addInterceptor(logging)            //设置开启log
                .addNetworkInterceptor(new StethoInterceptor()) //添加抓包工具
                .build();
        mMyOkHttp = new MyOkHttp(okHttpClient);

        mDownloadMgr = (DownloadMgr) new DownloadMgr.Builder()
                .myOkHttp(mMyOkHttp)
                .maxDownloadIngNum(5)       //设置最大同时下载数量（不设置默认5）
                .saveProgressBytes(50 * 1204)   //设置每50kb触发一次saveProgress保存进度 （不能在onProgress每次都保存 过于频繁） 不设置默认50kb
                .build();

        mDownloadMgr.resumeTasks();     //恢复本地所有未完成的任务
    }

    private void openStetho() {
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build());
    }

    private void initDate() {
        application = this;
        context = this;
        mainTid = android.os.Process.myTid();
        // 初始化极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        ZXingLibrary.initDisplayOpinion(this);
    }

    /**
     * 注册微信
     */
    private void registToWX() {
        mWxApi = WXPayEntryActivity.initWeiXin(this, MainConstant.WEIXIN_APP_ID);
    }


    public static Context getContext() {
        return context;
    }

    public static Context getApplication() {
        return application;
    }

    public static int getMainTid() {
        return mainTid;
    }


    public static synchronized MyApplication getInstance() {
        return application;
    }

    public MyOkHttp getMyOkHttp() {
        return mMyOkHttp;
    }

    public DownloadMgr getDownloadMgr() {
        return mDownloadMgr;
    }

    public static int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     *      * 设置greenDao    
     */
    private void setDatabase() {
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。      
        //   // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO 已经帮你做了。       
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。       
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。       
        try {
            mHelper = new DaoMaster.DevOpenHelper(this, "Jpush-DB", null);
            db = mHelper.getWritableDatabase();
            // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。       
            mDaoMaster = new DaoMaster(db);
            mDaoSession = mDaoMaster.newSession();
        }catch (Exception e){

        }

    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

    public SQLiteDatabase getDb() {
        return db;
    }


    public List<MonitorDataEntity> getMonitorDataEntityList() {
        return monitorDataEntityList;
    }

    public void setMonitorDataEntityList(List<MonitorDataEntity> monitorDataEntityList) {
        this.monitorDataEntityList = monitorDataEntityList;
    }

    public void clearMonitorDataEntityList() {
        if (monitorDataEntityList != null) {
            monitorDataEntityList.clear();
            monitorDataEntityList = null;
        }

    }

    public void registerPush(){
        String vendor = Build.MANUFACTURER;
        if(vendor.toLowerCase(Locale.ENGLISH).contains("xiaomi")) {
            //注册小米推送服务
            if (BuildConfig.IS_DEBUG){
                busid=5525;
            }else {
                busid=5589;
            }

            MiPushClient.registerPush(this, MIPUSH_APPID, MIPUSH_APPKEY);
        }else if(vendor.toLowerCase(Locale.ENGLISH).contains("huawei")) {
            //请求华为推送设备 token
            if (BuildConfig.IS_DEBUG){
                busid=5531;
            }else {
                busid=5590;
            }

            HMSAgent.init(this);
        }
    }


    // 遍历所有Activity并finish
    public static void exit() {
        if (mActivities != null && mActivities.size() > 0) {

            for (Activity activity : mActivities) {
                activity.finish();
            }
        }

    }

    /**
     * 移除活动
     *
     * @param activity
     */
    public static void remove(Activity activity) {
        if (mActivities != null) {
            mActivities.remove(activity);
        }
    }
}
