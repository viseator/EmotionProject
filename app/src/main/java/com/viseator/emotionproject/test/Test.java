package com.viseator.emotionproject.test;

import android.util.Log;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.viseator.emotionproject.data.EmotionData;
import com.viseator.emotionproject.data.EmotionDataEntity;
import com.viseator.emotionproject.data.view.EmotionWeekData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class Test {
    private static final String TAG = "@vir Test";

    public static void test(EmotionData emotionData) {
        long time = (long) (System.currentTimeMillis() - EmotionData.MILLS_OF_DAY * 7);
        while ((time += Math.random() * EmotionData.MAX_GAP * 1.2) < System.currentTimeMillis()) {
            List<RecognizeResult> recognizeResultList = new ArrayList<>();
            RecognizeResult recognizeResult = new RecognizeResult();
            recognizeResult.scores = new Scores();
            Scores scores = recognizeResult.scores;
            scores.anger = Math.random() < 0.125 ? 0.8 + Math.random() * 0.2 : Math.random() * 0.1;
            scores.disgust = Math.random() < 0.125 ? 0.8 + Math.random() * 0.2 : Math.random() * 0.1;
            scores.fear = Math.random() < 0.125 ? 0.8 + Math.random() * 0.2 : Math.random() * 0.1;
            scores.happiness = Math.random() < 0.125 ? 0.8 + Math.random() * 0.2 : Math.random() * 0.1;
            scores.neutral = Math.random() < 0.125 ? 0.8 + Math.random() * 0.2 : Math.random() * 0.1;
            scores.surprise = Math.random() < 0.125 ? 0.8 + Math.random() * 0.2 : Math.random() * 0.1;
            scores.contempt = Math.random() < 0.125 ? 0.8 + Math.random() * 0.2 : Math.random() * 0.1;
            scores.sadness = Math.random() < 0.125 ? 0.8 + Math.random() * 0.2 : Math.random() * 0.1;
            recognizeResultList.add(recognizeResult);
            emotionData.addEmotionData(recognizeResultList, time);
        }

//        EmotionWeekData weekData = emotionData.getEmotionWeekData(0, true);

//        EmotionDataEntity emotionDataEntity = emotionData.getLastData();
        Log.d(TAG, String.valueOf(emotionData));

    }
}
