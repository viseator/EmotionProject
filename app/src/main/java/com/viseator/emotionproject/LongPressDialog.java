package com.viseator.emotionproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by yanhao on 17-6-4.
 */

public class LongPressDialog extends AlertDialog {
    protected LongPressDialog(Context context) {
        super(context);
    }
    protected LongPressDialog(Context context,int theme){
        super(context,theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail);
    }
}
