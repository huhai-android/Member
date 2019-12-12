package com.newdjk.member.utils;

/*
 *  @项目名：  flowchat 
 *  @包名：    com.livestar.flowchat.util
 *  @文件名:   FragmentFactory
 *  @创建者:   huhai
 *  @创建时间:  2018/3/2 11:13
 *  @描述：
 */

import android.support.v4.app.Fragment;

import com.newdjk.member.ui.fragment.FunctionFragment;
import com.newdjk.member.ui.fragment.HomeFragment;
import com.newdjk.member.ui.fragment.MessageFragment;
import com.newdjk.member.ui.fragment.MinFragment;

import java.util.HashMap;
import java.util.Map;

public class FragmentFactory {

    private Map<Integer, Fragment> mFragments = new HashMap<Integer, Fragment>();


    public Fragment createFragment(int position)
    {
        Fragment fragment = null;
        fragment = mFragments.get(position);  //在集合中取出来Fragment
        if(fragment == null)   //如果在集合中没有取出来，需要重新创建
        {
            if(position == 0)
            {
                fragment = HomeFragment.getFragment();

            }
            else if(position == 1)
            {
                fragment = FunctionFragment.getFragment();
            }
            else if(position == 2)
            {
                fragment = MessageFragment.getFragment();
            }
            else if(position == 3)
            {
                fragment = MinFragment.getFragment();
            }

            if(fragment != null)
            {
                mFragments.put(position, fragment);
            }
        }
        return fragment;

    }
}
