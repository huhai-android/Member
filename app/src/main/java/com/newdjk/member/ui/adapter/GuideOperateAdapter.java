package com.newdjk.member.ui.adapter;

import java.util.List;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.newdjk.member.R;

/**
 * 
 * 
 */
public class GuideOperateAdapter extends PagerAdapter {

	// 界面列表
	private List<View> views;
	private Activity activity;
	private LinearLayout llBack;
	private GuideListener mListener;

	public void setListener(GuideListener listener) {
		this.mListener = listener;
	}

	public GuideOperateAdapter(List<View> views, Activity activity) {
		this.views = views;
		this.activity = activity;
	}

	// 销毁arg1位置的界面
	@Override
	public void destroyItem(View arg0, int arg1, Object arg2) {
		((ViewPager) arg0).removeView(views.get(arg1));
	}

	@Override
	public void finishUpdate(View arg0) {
	}

	// 获得当前界面数
	@Override
	public int getCount() {
		if (views != null) {
			return views.size();
		}
		return 0;
	}

	// 初始化arg1位置的界面
	@Override
	public Object instantiateItem(View arg0, int arg1) {
		View view = views.get(arg1);
		llBack = view.findViewById(R.id.rl_back);
		llBack.setOnClickListener(v -> mListener.guideLister());
		((ViewPager) arg0).addView(view, 0);
		return views.get(arg1);
	}

	// 判断是否由对象生成界面
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return (arg0 == arg1);
	}

	@Override
	public void restoreState(Parcelable arg0, ClassLoader arg1) {
	}

	@Override
	public Parcelable saveState() {
		return null;
	}

	@Override
	public void startUpdate(View arg0) {
	}

	public interface GuideListener {
		void guideLister();
	}


}
