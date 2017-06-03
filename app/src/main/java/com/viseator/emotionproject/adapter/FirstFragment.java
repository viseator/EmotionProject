package com.viseator.emotionproject.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.viseator.emotionproject.DetailActivity;
import com.viseator.emotionproject.R;
import butterknife.ButterKnife;

/**
 * Created by yanhao on 17-6-3.
 */

public class FirstFragment extends Fragment implements View.OnClickListener{
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.first_fragment,container,false);
        ButterKnife.bind(this,view);
        button=(Button)view.findViewById(R.id.button_fragment);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_fragment:{
                Intent intent=new Intent(getContext(), DetailActivity.class);
                startActivity(intent);
            } break;
        }
    }
}
