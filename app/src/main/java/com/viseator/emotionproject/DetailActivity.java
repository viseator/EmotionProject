package com.viseator.emotionproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.viseator.emotionproject.data.DayData;
import com.viseator.emotionproject.data.TestData;
import com.viseator.emotionproject.data.WeekData;

/**
 * Created by yanhao on 17-6-3.
 */

public class DetailActivity extends AppCompatActivity{
    private WeekData weekData;
    private DetailView detailView;
    private TabLayout tabLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        weekData=new WeekData();
        TestData testData=new TestData(30,100,1);
        TestData testData1=new TestData(150,270,2);
        TestData testData2=new TestData(300,550,4);
        DayData dayData=new DayData();
        dayData.putTestData(testData);
        dayData.putTestData(testData1);
        dayData.putTestData(testData2);
        weekData.putDayData(dayData);
        TestData testData3=new TestData(80,200,5);
        TestData testData4=new TestData(250,400,3);
        TestData testData5=new TestData(450,700,6);
        DayData dayData1=new DayData();
        dayData1.putTestData(testData3);
        dayData1.putTestData(testData4);
        dayData1.putTestData(testData5);
        weekData.putDayData(dayData1);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        initData();
    }

    private void initData(){
        tabLayout=(TabLayout) findViewById(R.id.detail_tablayout);
        detailView=(DetailView) findViewById(R.id.detail_view);
        detailView.setWeekData(weekData);
        detailView.setItemSelectListener(new DetailView.OnItemSelectListener(){

            @Override
            public void OnItemSelect(float y, int num) {
                Toast.makeText(DetailActivity.this,String.valueOf(num),Toast.LENGTH_SHORT).show();
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("周一"),false);
        tabLayout.addTab(tabLayout.newTab().setText("周二"),false);
        tabLayout.addTab(tabLayout.newTab().setText("周三"),false);
        tabLayout.addTab(tabLayout.newTab().setText("周四"),false);
        tabLayout.addTab(tabLayout.newTab().setText("周五"),false);
        tabLayout.addTab(tabLayout.newTab().setText("周六"),false);
        tabLayout.addTab(tabLayout.newTab().setText("周日"),false);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }


}
