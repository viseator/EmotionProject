package com.viseator.emotionproject;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Author: Alexander
 * Email: yifengtang@hustunique.com
 * Since: 2017/6/4.
 */

public class AccountPswdActivity extends BaseActivity {

    @BindView(R.id.account_edit_text)
    EditText accountText;

    @BindView(R.id.pasword_edit_text)
    EditText passwordText;

    @OnClick(R.id.login_button)
    public void login() {
        if (accountText.getText().toString().isEmpty() || passwordText.getText().toString().isEmpty()) {
            Toast.makeText(this, "Please input info below", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(AccountPswdActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.account_activity;
    }

    @Override
    protected void baseInit() {
        setFullScreen();
    }

    @Override
    protected void initView() {

    }
}
