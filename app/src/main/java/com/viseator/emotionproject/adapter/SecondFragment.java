package com.viseator.emotionproject.adapter;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.viseator.emotionproject.R;

/**
 * Created by yanhao on 17-6-3.
 */

public class SecondFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.second_fragment,container,false);
        return view;
    }
}
