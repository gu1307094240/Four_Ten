package com.example.gu.four_ten.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gu.four_ten.R;
import com.example.gu.four_ten.adapter.MyViewPagerAdapter;
import com.example.gu.four_ten.sub_fragments.Text_Sub_Fragment;
import java.util.ArrayList;

/**
 * Created by 31452 on 2016/6/19.
 */

/**
 * 使用Fragment嵌套Fragment的模式实现界面滑动
 * 主Activity下有五个一级Fragment，这是其中之一，Text_Fragment
 */
public class Text_Fragment extends Fragment{

    private ViewPager viewPager;
    private ArrayList<Fragment> fragments;
    private Text_Sub_Fragment fragment0;
    private Text_Sub_Fragment fragment1;
    private Text_Sub_Fragment fragment2;
    private Text_Sub_Fragment fragment3;
    private Text_Sub_Fragment fragment4;
    private Text_Sub_Fragment fragment5;
    private Text_Sub_Fragment fragment6;
    private Text_Sub_Fragment fragment7;
    private Text_Sub_Fragment fragment8;
    private Text_Sub_Fragment fragment9;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nulllayout,container,false);
        initViewPager(view);
        return view;
    }

    /**
     * 添加十个二级fragment页面
     * 通过传不同的参数获得显示不同数据的页面
     * @param parentView
     */
    private void initViewPager(View parentView){
        viewPager = (ViewPager)parentView.findViewById(R.id.viewPager_text);
        fragments = new ArrayList<Fragment>();
        fragment0 = newInstance(0);
        fragment1 = newInstance(1);
        fragment2 = newInstance(2);
        fragment3 = newInstance(3);
        fragment4 = newInstance(4);
        fragment5 = newInstance(5);
        fragment6 = newInstance(6);
        fragment7 = newInstance(7);
        fragment8 = newInstance(8);
        fragment9 = newInstance(9);

        fragments.add(fragment0);
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);
        fragments.add(fragment6);
        fragments.add(fragment7);
        fragments.add(fragment8);
        fragments.add(fragment9);
        viewPager.setAdapter(new MyViewPagerAdapter(getChildFragmentManager(), fragments));
        viewPager.setCurrentItem(0);
    }

    /**
     * 静态工厂方法需要一个int型的值来初始化fragment的参数，
     * 然后返回新的fragment到调用者
     */
    public static Text_Sub_Fragment newInstance(int num) {

        Bundle args = new Bundle();

        Text_Sub_Fragment fragment = new Text_Sub_Fragment();
        args.putInt("num",num);
        fragment.setArguments(args);
        return fragment;
    }

}
