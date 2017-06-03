package com.viseator.emotionproject.data.view;

import com.viseator.emotionproject.data.EmotionData;

import java.util.List;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class EmotionWeekData {
    private List<EmotionDayData> mEmotionDayDataList;

    public List<EmotionDayData> getEmotionDayDataList() {
        return mEmotionDayDataList;
    }

    public void setEmotionDayDataList(List<EmotionDayData> emotionDayDataList) {
        mEmotionDayDataList = emotionDayDataList;
    }
}
