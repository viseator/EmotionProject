package com.viseator.emotionproject;

import android.content.Intent;

import butterknife.OnClick;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 2017/6/4.
 */

public class SignInOutActivity extends BaseActivity {

    @OnClick(R.id.sign_in_button)
    public void signIn(){
        Intent intent = new Intent(SignInOutActivity.this, AccountPswdActivity.class);
        startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.signin_activity;
    }

    @Override
    protected void baseInit() {
        setFullScreen();

    }

    @Override
    protected void initView() {

    }
}
