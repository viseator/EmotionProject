package com.viseator.emotionproject.data.chart;

import android.util.Log;

import com.github.mikephil.charting.data.PieEntry;
import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.EmotionDataEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class EmotionChartData {
    private static final String TAG = "@vir EmotionChartData";
    private EmotionData mEmotionData;
    private static EmotionChartData INSTANCE = null;

    private EmotionChartData(EmotionData emotionData) {
        mEmotionData = emotionData;
    }

    public static EmotionChartData getInstance() {
        return INSTANCE;
    }

    public static EmotionChartData getInstance(EmotionData emotionData) {
        if (INSTANCE == null) {
            INSTANCE = new EmotionChartData(emotionData);
        }
        return INSTANCE;
    }

    public List<PieEntry> getMainPieEntries() {
        EmotionDataEntity emotionDataEntity = mEmotionData.getLastData();
        List<Double> listData = EmotionData.convertEntityToList(emotionDataEntity);
        double total = 0;
        for (int i = 0; i < 8; i++) {
            total += listData.get(i);
        }
        List<PieEntry> pieEntryList = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            if (listData.get(i) != 0) {
                PieEntry pieEntry = new PieEntry((float) (listData.get(i) / total * 100), EmotionData
                        .getRankFromId(i).toString());
                pieEntryList.add(pieEntry);
            }
        }
        Log.d(TAG, String.valueOf(pieEntryList.size()));
        return pieEntryList;
    }
}
