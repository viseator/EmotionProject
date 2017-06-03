package com.viseator.emotionproject;

import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.microsoft.projectoxford.face.contract.Emotion;
import com.viseator.emotionproject.service.EmotionService;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = "@vir MainActivity";

    @OnClick(R.id.test_button)
    public void ss() {
        Intent intent = new Intent(MainActivity.this, EmotionService.class);
        startService(intent);
        if (!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 0);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
