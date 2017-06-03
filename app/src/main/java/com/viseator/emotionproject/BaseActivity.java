package com.viseator.emotionproject;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.microsoft.projectoxford.emotion.EmotionServiceClient;
import com.viseator.emotionproject.data.DaoSession;
import com.viseator.emotionproject.data.EmotionDataEntityDao;

import butterknife.ButterKnife;

/**
 * Created by viseator on 5/27/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        baseInit();
        initView();
    }

    protected void setFullScreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    protected abstract int getLayout();

    protected abstract void baseInit();

    protected abstract void initView();

    protected EmotionDataEntityDao getEmotionDataEntityDao() {
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        return daoSession.getEmotionDataEntityDao();
    }

}

