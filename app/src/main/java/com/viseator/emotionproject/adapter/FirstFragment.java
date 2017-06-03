package com.viseator.emotionproject.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.viseator.emotionproject.DetailActivity;
import com.viseator.emotionproject.R;
import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.chart.EmotionChartData;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yanhao on 17-6-3.
 */

public class FirstFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.main_linechart)
    LineChart mLineChart;
    @BindView(R.id.main_piechart)
    PieChart mPieChart;
    private EmotionData mEmotionData;

    public void setEmotionData(EmotionData emotionData) {
        mEmotionData = emotionData;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        ButterKnife.bind(this, view);
        mLineChart.setOnClickListener(this);
        initView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void initView() {
        EmotionChartData mEmotionChartData = EmotionChartData.getInstance(mEmotionData);
        PieDataSet pieDataSet = new PieDataSet(mEmotionChartData.getMainPieEntries(), "");
        pieDataSet.setColors(Color.parseColor("#61A5E8"),
                Color.parseColor("#EECB5F"), Color.parseColor("#9570E5"), Color.parseColor("#7ECF51"), Color.parseColor
                        ("#E3935D"), Color.parseColor("#E16757"), Color.parseColor("#605FF0"));
        pieDataSet.setValueTextSize(10);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);
        mPieChart.setDrawEntryLabels(true);
        mPieChart.setDescription(null);
        mPieChart.setHoleRadius(30f);
        mPieChart.setTransparentCircleRadius(0f);
        mPieChart.setEntryLabelTextSize(8);
        mPieChart.animateXY(2000, 2000);
        mPieChart.setAlpha(0.7f);
        mPieChart.getLegend().setEnabled(false);
        mPieChart.setData(pieData);


        LineDataSet lineDataSet = new LineDataSet(mEmotionChartData.getMainLineEntries(), "");
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_color);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(drawable);
        lineDataSet.enableDashedLine(10f,10f,0f);
        lineDataSet.setColor(Color.parseColor("#F2B27B"));
        lineDataSet.setDrawCircles(false);
        LineData lineData = new LineData(lineDataSet);
        lineData.setDrawValues(false);
        mLineChart.setData(lineData);
        mLineChart.setDescription(null);
        mLineChart.getLegend().setEnabled(false);
        mLineChart.animateY(2000);
        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setEnabled(false);
        YAxis yAxisRight = mLineChart.getAxisRight();
        yAxisRight.setEnabled(false);
        YAxis yAxisLeft = mLineChart.getAxisLeft();
        yAxisLeft.setEnabled(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_linechart: {
                Intent intent = new Intent(getContext(), DetailActivity.class);
                startActivity(intent);
            }
            break;
        }
    }
}
