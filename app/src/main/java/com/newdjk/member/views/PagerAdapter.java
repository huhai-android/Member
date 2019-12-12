package com.newdjk.member.views;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.newdjk.member.listener.OnTabItemClickListener;
import com.newdjk.member.ui.entity.AppLicationEntity;
import com.newdjk.member.ui.fragment.HomeTabFragment;

import java.util.ArrayList;
import java.util.List;

/*
 *  @项目名：  BdMember
 *  @包名：    com.newdjk.bdmember.ui.adapter
 *  @文件名:   PagerAdapter
 *  @创建者:   huhai
 *  @创建时间:  2019/7/22 10:33
 *  @描述：
 */
public class PagerAdapter extends FragmentPagerAdapter {
    private OnTabItemClickListener listener;
    private List<AppLicationEntity> listuse=new ArrayList<>();

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public PagerAdapter(FragmentManager supportFragmentManager, List<AppLicationEntity> listall, OnTabItemClickListener listener) {
        super(supportFragmentManager);
        listuse.addAll(listall);
        this.listener = listener;
    }

    @Override
    public Fragment getItem(int position) {
        // Log.d(TAG,"数据长度"+listuse.size());
        HomeTabFragment homeTabFragment = new HomeTabFragment();
        homeTabFragment.setdata(listuse, position);
        homeTabFragment.setonclickListener(new OnTabItemClickListener() {
            @Override
            public void onItemChildClick(AppLicationEntity appLicationEntity) {
                // tabitemclick(appLicationEntity);
                listener.onItemChildClick(appLicationEntity);
            }
        });
        return homeTabFragment;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        HomeTabFragment fragment = (HomeTabFragment) super.instantiateItem(container, position);
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
