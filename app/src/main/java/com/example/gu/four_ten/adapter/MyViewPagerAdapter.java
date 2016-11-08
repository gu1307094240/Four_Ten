package com.example.gu.four_ten.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;

/**
 * Created by 31452 on 2016/6/20.
 */

/**
 * viewPager适配器，控制页面左右滑动
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;


    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyViewPagerAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    /**
     * 获得当前页面
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    /**
     * 获得页面数量
     * @return
     */
    @Override
    public int getCount() {
        return fragments.size();
    }

}
