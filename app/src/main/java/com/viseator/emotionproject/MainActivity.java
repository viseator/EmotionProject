package com.viseator.emotionproject;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.viseator.emotionproject.adapter.FirstFragment;
import com.viseator.emotionproject.adapter.SecondFragment;
import com.viseator.emotionproject.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout mTabLayout;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private List<Fragment> fragments;
    private List<String>  titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initDate();

    }

    private void initDate(){
        firstFragment= new FirstFragment();
        secondFragment=new SecondFragment();
        fragments=new ArrayList<Fragment>();
        titles=new ArrayList<String>();
        titles.add("首页");
        titles.add("个人信息");
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        mViewPager=(ViewPager)findViewById(R.id.viewpager_main);
        mTabLayout=(TabLayout)findViewById(R.id.tablayout_main);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),fragments,titles);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
