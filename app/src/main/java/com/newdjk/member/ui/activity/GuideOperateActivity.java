package com.newdjk.member.ui.activity;

import android.app.Dialog;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.newdjk.member.R;
import com.newdjk.member.basic.BasicActivity;
import com.newdjk.member.ui.adapter.GuideOperateAdapter;
import com.newdjk.member.utils.SpUtils;
import com.newdjk.member.views.CommomDialog;

import java.util.ArrayList;
import java.util.List;

public class GuideOperateActivity extends BasicActivity implements ViewPager.OnPageChangeListener,GuideOperateAdapter.GuideListener {

    private ViewPager vp;
    private GuideOperateAdapter vpAdapter;
    private List<View> views;

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected int initViewResId() {
        return R.layout.activity_guide_operate;
    }

    @Override
    protected void initView() {
        initBackTitleBgRes(R.color.white, getResources().getString(R.string.device_connect_title_string));
        // 初始化页面
        initViews();

        // 初始化底部小点
        initDots();
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

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);

        views = new ArrayList<View>();
        // 初始化引导图片列表
        views.add(inflater.inflate(R.layout.activity_guide_operate_1, null));
        views.add(inflater.inflate(R.layout.activity_guide_operate_2, null));
        views.add(inflater.inflate(R.layout.activity_guide_operate_3, null));
        views.add(inflater.inflate(R.layout.activity_guide_operate_4, null));
        views.add(inflater.inflate(R.layout.activity_guide_operate_5, null));

        // 初始化Adapter
        vpAdapter = new GuideOperateAdapter(views, this);
        vpAdapter.setListener(this);


        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        // 绑定回调
        vp.setOnPageChangeListener(this);
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[views.size()];

        // 循环取得小点图片
        for (int i = 0; i < views.size(); i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(true);// 都设为灰色
        }

        currentIndex = 0;
        dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
    }

    private void setCurrentDot(int position) {
      /*  if (position < 0 || position > views.size() - 1
                || currentIndex == position) {
            return;
        }*/

        dots[position].setEnabled(false);
        dots[currentIndex].setEnabled(true);

        currentIndex = position;
    }

    // 当滑动状态改变时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    // 当当前页面被滑动时调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
    }

    // 当新的页面被选中时调用
    @Override
    public void onPageSelected(int arg0) {
        // 设置底部小点选中状态
        setCurrentDot(arg0);
    }


    @Override
    public void guideLister() {
        SpUtils.put("user_first_boolean", true);
        finish();
    }
}
