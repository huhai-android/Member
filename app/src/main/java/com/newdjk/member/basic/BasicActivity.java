package com.newdjk.member.basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.jaeger.library.StatusBarUtil;
import com.lxq.okhttp.MyOkHttp;
import com.newdjk.member.MyApplication;
import com.newdjk.member.R;
import com.newdjk.member.utils.LogUtils;
import com.newdjk.member.utils.ToastUtil;
import com.newdjk.member.views.LoadDialog;
import com.newdjk.member.views.TitleBuilder;

import org.greenrobot.eventbus.EventBus;

import java.util.LinkedList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018-3-12.
 */

public abstract class BasicActivity<T> extends FragmentActivity implements View.OnClickListener {

    // 管理activity
    public final static List<BasicActivity> mActivities = new LinkedList<BasicActivity>();
    public static BasicActivity activity;
    protected Context mContext;
    protected boolean isBlack = true;
    protected TitleBuilder titleBuilder;
    protected MyOkHttp mMyOkhttp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        synchronized (mActivities) {
            mActivities.add(this);
        }
        setContentView(initViewResId());
        setStatusBar();
        ButterKnife.bind(this);
        mContext = this;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
        changeStatusBarTextColor(true);
        titleBuilder = new TitleBuilder(this);
        mMyOkhttp = MyApplication.getInstance().getMyOkHttp();
        initView();
        initListener();
        initData();
        getcurrentTime();


    }

    private void getcurrentTime() {


    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary),0);
    }

    protected void setStatusBar(int barColor) {
        StatusBarUtil.setColor(this, barColor,0);
    }

    protected void changeStatusBarTextColor(boolean isBlack) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (isBlack) {
                //设置状态栏黑色字体
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }else {
                //恢复状态栏白色字体
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE|View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            }
        }
    }

    /**
     * 在setConvetView之前
     */
    public void init() {
    }

    /**
     * 初始化界面
     */
    protected Activity initDefault() {
        return null;
    }

    /**
     * 初始化界面
     */
    protected abstract int initViewResId();

    /**
     * 初始化界面
     */
    protected abstract void initView();

    /**
     * 初始化界面
     */
    protected abstract void initListener();

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 点击事件
     *
     * @param view
     */
    protected abstract void otherViewClick(View view);

    /**
     * 点击的事件的统一的处理
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                otherViewClick(view);
                break;
        }
    }
    /**
     * @param str 日志的处理
     */
    protected void logE(String str) {
        LogUtils.e("" + this.getClass().getSimpleName(), str);
    }
    /**
     * @param str 显示一个内容为str的toast
     */
    protected void toast(String str) {
        ToastUtil.setToast(str);
    }

    //标题栏 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * 左侧没有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initTitle(String title) {
        return titleBuilder.setTitleText(title);
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitleBg(String title) {
        return titleBuilder.setTitleBgRes(R.color.tm).setTitleText(title)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }
    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitleBgRes(int color,String title) {
        return titleBuilder.setTitleBgRes(color).setTitleText(title)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected TitleBuilder initBackTitle(String title) {
        return titleBuilder
                .setTitleText(title)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
    }

    protected TitleBuilder initBackTitleLeftImg(String title, View.OnClickListener leftOnCLick){
        return titleBuilder
                .setTitleText(title)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(leftOnCLick);
    }

    protected TitleBuilder initBackTitleLeftImg(int color,String title, View.OnClickListener leftOnCLick){
        return titleBuilder
                .setTitleText(title)
                .setTitleBgRes(color)
                .setLeftImage(R.drawable.head_back_selectot)
                .setLeftOnClickListener(leftOnCLick);
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param txt 右边文字
     */
    protected TitleBuilder initTitleRightStr(String txt, View.OnClickListener rightOnCLick) {
        return titleBuilder
                .setRightText(txt)
                .setLeftOnClickListener(rightOnCLick);
    }

    /**
     * 左侧有返回键的标题栏
     * <p>如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param ResId 右边图片
     */
    protected TitleBuilder initTitleRightImg(int ResId, View.OnClickListener rightOnCLick) {
        return titleBuilder
                .setRightImage(ResId)
                .setLeftOnClickListener(rightOnCLick);
    }
    //标题栏 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //添加右边文件，点击右边文字事件
    protected TitleBuilder initTitleRightStrClick(String txt, View.OnClickListener rightOnCLick) {
        return titleBuilder
                .setRightText(txt)
                .setRightOnClickListener(rightOnCLick);
    }


    //启动新Activity方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**打开新的Activity，向左滑入效果
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }
    /**打开新的Activity
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }
    /**打开新的Activity，向左滑入效果
     * @param intent
     * @param requestCode
     */
    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }
    /**打开新的Activity
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
                if (intent == null) {
                    logE( "toActivity  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
    }
    //启动新Activity方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //手机返回键和菜单键实现同点击标题栏左右按钮效果<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    /**
     * 退出时之前的界面进入动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int enterAnim = R.anim.fade;
    /**
     * 退出时该界面动画,可在finish();前通过改变它的值来改变动画效果
     */
    protected int exitAnim = R.anim.right_push_out;
    @Override
    public void onBackPressed() {
        finish();
        LoadDialog.clear();
        super.onBackPressed();
    }
    @Override
    public void finish() {
        super.finish();//必须写在最前才能显示自定义动画
        if (enterAnim > 0 && exitAnim > 0) {
            try {
                overridePendingTransition(enterAnim, exitAnim);
            } catch (Exception e) {
                logE( "finish overridePendingTransition(enterAnim, exitAnim);" +
                        " >> catch (Exception e) {  " + e.getMessage());
            }
        }
        LoadDialog.clear();
    }
    /**
     * 退出
     */
    protected void killAll() {
        List<BasicActivity> copy;
        synchronized (mActivities) {
            copy = new LinkedList<BasicActivity>(mActivities);
        }
        for (BasicActivity activity : copy) {
            activity.finish();
        }
     //   android.os.Process.killProcess(android.os.Process.myPid());
    }
    //手机返回键和菜单键实现同点击标题栏左右按钮效果>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>



    @Override
    protected void onDestroy() {
        super.onDestroy();
        synchronized (mActivities) {
            mActivities.remove(this);
        }
        LoadDialog.clear();
        EventBus.getDefault().unregister(this);//
    }

    @Override
    protected void onPause() {
        super.onPause();
        activity = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        activity = this;
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }


    /**
     * 加载效果
     *
     * @param bool
     */
    public void loading(boolean bool) {
        if (bool) {
            LoadDialog.show(this);
        } else {
            LoadDialog.clear();
        }
    }

    /**
     * 加载效果
     *
     * @param bool
     */
    protected void loading(boolean bool, String content) {
        if (bool) {
            LoadDialog.show(this, content);
        } else {
            LoadDialog.clear();
        }
    }


    public void noAnimFinish() {
        super.finish();
    }
}
