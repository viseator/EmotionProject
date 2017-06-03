package com.viseator.emotionproject;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.view.EmotionDayData;
import com.viseator.emotionproject.data.view.EmotionViewData;
import com.viseator.emotionproject.data.view.EmotionWeekData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yanhao on 17-6-4.
 */

public class LongPressDialog extends AlertDialog {
    protected LongPressDialog(Context context) {
        super(context);
    }

    protected LongPressDialog(Context context, int theme) {
        super(context, theme);
    }

    private EmotionWeekData mEmotionWeekData;
    private long min;
    private long week;

    private static final String TAG = "@vir LongPressDialog";

    public void setMin(long min) {
        this.min = min;
    }

    public void setWeek(long week) {
        this.week = week;
    }

    @BindView(R.id.dialog_piechart)
    PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_detail);
        ButterKnife.bind(this);
        EmotionDayData emotionDayData = mEmotionWeekData.getEmotionDayDataList().get((int) (week - 1));
        List<EmotionViewData> viewDataList = emotionDayData.getEmotionViewDataList();
        EmotionViewData result = null;
        for (EmotionViewData viewData : viewDataList) {
            if (min / 3 + 7 * 60 > viewData.getStartMins() && min / 3 + 7 * 60 < viewData.getEndMins()) {
                Log.d(TAG, "found:" + String.valueOf(min));
                result = viewData;
                break;
            } else {
                Log.d(TAG, String.valueOf(min));
            }
        }
        List<Double> resultList = EmotionData.convertEntityToList(result.getTotalEmotionData());
        double total = 0;
        for (int i = 0; i < 8; i++) {
            total += resultList.get(i);
        }
        List<PieEntry> pieEntryList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (resultList.get(i) != 0) {
                PieEntry pieEntry = new PieEntry((float) (resultList.get(i) / total * 100), EmotionData
                        .getRankFromId(i).toString());
                pieEntryList.add(pieEntry);
            }
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntryList, "");
        pieDataSet.setColors(Color.parseColor("#61A5E8"),
                Color.parseColor("#EECB5F"), Color.parseColor("#9570E5"), Color.parseColor("#7ECF51"), Color.parseColor
                        ("#E3935D"), Color.parseColor("#E16757"), Color.parseColor("#605FF0"));
        pieDataSet.setValueTextSize(1);
        PieData pieData = new PieData(pieDataSet);
        pieData.setDrawValues(false);
        mPieChart.setDrawEntryLabels(true);
        mPieChart.setDescription(null);
        mPieChart.setHoleRadius(30f);
        mPieChart.setTransparentCircleRadius(0f);
        mPieChart.setEntryLabelTextSize(8);
        mPieChart.animateXY(1000, 1000);
        mPieChart.setAlpha(0.7f);
        mPieChart.getLegend().setEnabled(false);
        mPieChart.setData(pieData);
    }

    public void setEmotionWeekData(EmotionWeekData emotionWeekData) {
        mEmotionWeekData = emotionWeekData;
    }
}
