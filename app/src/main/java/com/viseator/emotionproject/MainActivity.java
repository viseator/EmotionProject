package com.viseator.emotionproject;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.viseator.emotionproject.adapter.FirstFragment;
import com.viseator.emotionproject.adapter.SecondFragment;
import com.viseator.emotionproject.adapter.ViewPagerAdapter;
import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.chart.EmotionChartData;
import com.viseator.emotionproject.service.EmotionService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private EmotionData mEmotionData;
    private EmotionChartData mEmotionChartData;
    private static final String TAG = "@vir MainActivity";

    private Intent serviceIntent;
    private EmotionService.ServiceBinder binder = null;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            binder = (EmotionService.ServiceBinder)iBinder;
            Log.d(TAG, "onServiceConnected: done");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: miss");
        }
    };

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
    }


    private ViewPager mViewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout mTabLayout;
    private FirstFragment firstFragment;
    private SecondFragment secondFragment;
    private List<android.support.v4.app.Fragment> fragments;
    private List<String> titles;
    @BindView(R.id.main_piechart)
    PieChart mPieChart;

    @OnClick(R.id.test_button)
    public void test() {
        serviceIntent = new Intent(MainActivity.this, EmotionService.class);
        startService(serviceIntent);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (binder == null);
                EmotionService service = (EmotionService) binder.getService();
            }
        }).start();

    }

    @OnClick(R.id.cancel_button)
    public void cancel() {
        EmotionService service = (EmotionService) binder.getService();
        service.stopWork();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDate();
//        Test.test(emotionData);
//        Log.d(TAG, EmotionRank.ANGER.toString());

        if (!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            requestCameraPermission();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void baseInit() {
//        mEmotionData = EmotionData.getInstance(getEmotionDataEntityDao());

//        mEmotionChartData = EmotionChartData.getInstance(mEmotionData);

    }

    @Override
    protected void initView() {
//        mPieChart.setData(new PieData(new PieDataSet(mEmotionChartData.getMainPieEntries(),
//                "test")));
    }

    private void initDate() {
        firstFragment = new FirstFragment();
        secondFragment = new SecondFragment();
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
