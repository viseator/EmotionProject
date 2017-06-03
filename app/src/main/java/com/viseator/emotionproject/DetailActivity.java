package com.viseator.emotionproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.util.Log;
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
    private int dNum;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmotionData emotionData = EmotionData.getInstance(getEmotionDataEntityDao());
        weekData= emotionData.getEmotionWeekData(0,false);
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
            public void OnItemSelect(float y, int num) {
                dY=y;
                dNum=num;
            }
        });
        detailView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Toast.makeText(DetailActivity.this,String.valueOf(dNum),Toast.LENGTH_SHORT).show();
                LongPressDialog dialog=new LongPressDialog(DetailActivity.this,R.style.Dialog);
                Window window=dialog.getWindow();
                WindowManager.LayoutParams layoutParams=window.getAttributes();
                layoutParams.x=30;
                int nH=detailView.getMyHeight();
                float iH=(float) (3*1440)/ dY;
                layoutParams.y=(int)((float)nH/iH);
                window.setAttributes(layoutParams);
                dialog.show();
                Log.w("dY；",String.valueOf(dY));
                return false;
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
