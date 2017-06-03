package com.viseator.emotionproject.data.view;

import com.viseator.emotionproject.data.EmotionDataEntity;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class TotalEmotionData extends EmotionDataEntity {
    public TotalEmotionData() {
        anger = 0;
        contempt = 0;
        disgust = 0;
        fear = 0;
        happiness = 0;
        neutral = 0;
        sadness = 0;
        surprise = 0;
    }

    public void add(EmotionDataEntity emotionDataEntity) {
        anger += emotionDataEntity.getAnger();
        contempt += emotionDataEntity.getContempt();
        disgust += emotionDataEntity.getDisgust();
        fear += emotionDataEntity.getFear();
        happiness += emotionDataEntity.getHappiness();
        neutral += emotionDataEntity.getNeutral();
        sadness += emotionDataEntity.getSadness();
        surprise += emotionDataEntity.getSurprise();
    }
}
