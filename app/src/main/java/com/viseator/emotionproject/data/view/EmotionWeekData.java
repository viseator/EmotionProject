package com.viseator.emotionproject.data.view;

import com.viseator.emotionproject.data.EmotionData;

import java.util.List;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class EmotionWeekData {
    public List<EmotionDayData> getEmotionDayDataList() {
        return mEmotionDayDataList;
    }

    private List<EmotionDayData> mEmotionDayDataList;

    public EmotionDayData getEmotionDayDataList(int i) {
        return mEmotionDayDataList.get(i);
    }

    public void setEmotionDayDataList(List<EmotionDayData> emotionDayDataList) {
        mEmotionDayDataList = emotionDayDataList;
    }
}
