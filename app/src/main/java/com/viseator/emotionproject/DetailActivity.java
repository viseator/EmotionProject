package com.viseator.emotionproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.util.EventLog;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.view.EmotionWeekData;

/**
 * Created by yanhao on 17-6-3.
 */

public class DetailActivity extends BaseActivity{
    private EmotionWeekData weekData;
    private DetailView detailView;
    private TabLayout tabLayout;
    private float  dY;
    private float mY;
    private int dNum;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmotionData emotionData = EmotionData.getInstance(getEmotionDataEntityDao());
        weekData= emotionData.getEmotionWeekData(System.currentTimeMillis(),false);
        initData();
    }

    @Override
    protected int getLayout() {
        return R.layout.detail_activity;
    }

    @Override
    protected void baseInit() {

    }

    @Override
    protected void initView() {

    }

    private void initData(){
        tabLayout=(TabLayout) findViewById(R.id.detail_tablayout);
        detailView=(DetailView) findViewById(R.id.detail_view);
        detailView.setWeekData(weekData);
        detailView.setItemSelectListener(new DetailView.OnItemSelectListener(){

            @Override
            public void OnItemSelect(float y, int num,float YP) {
                dY=y;
                dNum=num;
                mY=YP;
            }
        });
        detailView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(DetailActivity.this,String.valueOf(dNum),Toast.LENGTH_SHORT).show();
                LongPressDialog dialog=new LongPressDialog(DetailActivity.this,R.style.Dialog);
                Window window=dialog.getWindow();
                WindowManager.LayoutParams layoutParams=window.getAttributes();
                layoutParams.x=(int)((dNum-4)*(detailView.getDistance()));
                layoutParams.y= (int) (mY-500);
                window.setAttributes(layoutParams);
                dialog.show();
                Log.w("mY；",String.valueOf(mY));
                return false;
            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("MON"),false);
        tabLayout.addTab(tabLayout.newTab().setText("TUE"),false);
        tabLayout.addTab(tabLayout.newTab().setText("WED"),false);
        tabLayout.addTab(tabLayout.newTab().setText("THU"),false);
        tabLayout.addTab(tabLayout.newTab().setText("FRI"),false);
        tabLayout.addTab(tabLayout.newTab().setText("SAT"),false);
        tabLayout.addTab(tabLayout.newTab().setText("SUN"),false);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

    }


}
