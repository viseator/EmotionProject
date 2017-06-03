package com.viseator.emotionproject.data.view;

import com.viseator.emotionproject.data.EmotionData;

import java.util.List;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class EmotionDayData {
    private List<EmotionViewData> mEmotionViewDataList;

    public List<EmotionViewData> getEmotionViewDataList() {
        return mEmotionViewDataList;
    }

    public void setEmotionViewDataList(List<EmotionViewData> emotionDataList) {
        mEmotionViewDataList = emotionDataList;
    }
}
