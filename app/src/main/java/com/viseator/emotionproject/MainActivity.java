package com.viseator.emotionproject;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.viseator.emotionproject.adapter.FirstFragment;
import com.viseator.emotionproject.adapter.SecondFragment;
import com.viseator.emotionproject.adapter.ViewPagerAdapter;
import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.chart.EmotionChartData;
import com.viseator.emotionproject.service.EmotionService;
import com.viseator.emotionproject.test.Test;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @OnClick(R.id.commit_buttom)
    public void commit() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (binder != null) {
                    EmotionService service = (EmotionService) binder.getService();
                    service.startWork();
                }
            }
        }).start();
    }

    private EmotionData mEmotionData;
    private EmotionChartData mEmotionChartData;
    private static final String TAG = "@vir MainActivity";

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.arg1 == 1) {
                firstFragment.refreshPieChart();
            }
        }
    };


    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission
                .CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
    }

//    public interface OnDataReceivedListener{
//        void onReceive();
//    }

    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout mTabLayout;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private List<android.support.v4.app.Fragment> fragments;
    private List<String> titles;

//    private OnDataReceivedListener mDataReceivedListener = new OnDataReceivedListener() {
//        @Override
//        public void onReceive() {
//            Log.d(TAG, String.valueOf("OK"));
//            firstFragment.refreshPieChart();
//        }
//    };

    private EmotionService.ServiceBinder binder = null;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (EmotionService.ServiceBinder) iBinder;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDate();
//        Log.d(TAG, EmotionRank.ANGER.toString());

        if (!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            requestCameraPermission();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                serviceStart();
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        unbindService(connection);
        super.onDestroy();

    }

    private void serviceStart() {
        Intent intent = new Intent(MainActivity.this, EmotionService.class);
        bindService(intent, connection, BIND_AUTO_CREATE);
        while(binder == null);
        binder.setHandler(mHandler);
        EmotionService service = (EmotionService) binder.getService();
        service.startWork();
    }

    @Override
    protected int getLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void baseInit() {
        mEmotionData = EmotionData.getInstance(getEmotionDataEntityDao());

        mEmotionChartData = EmotionChartData.getInstance(mEmotionData);

//        mEmotionData.removeAllData();
//        Test.test(mEmotionData);

    }

    @Override
    protected void initView() {

    }

    private void initDate() {
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
        firstFragment.setEmotionData(mEmotionData);
        fragments = new ArrayList<>();
        titles = new ArrayList<>();
        titles.add("首页");
        titles.add("个人信息");
        fragments.add(firstFragment);
        fragments.add(secondFragment);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_main);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout_main);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, titles);
        mViewPager.setAdapter(viewPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
