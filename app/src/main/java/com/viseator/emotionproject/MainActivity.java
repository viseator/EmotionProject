package com.viseator.emotionproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.EmotionDataEntity;

public class MainActivity extends BaseActivity {

    private static final String TAG = "@vir MainActivity";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EmotionDataEntity emotionDataEntity = EmotionData.getEmotionDataEntity
                (getEmotionDataEntityDao());
        emotionDataEntity.setFear(100f);
        Log.d(TAG, String.valueOf(emotionDataEntity.getFear()));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void baseInit() {

    }

    @Override
    protected void initView() {

    }
}
