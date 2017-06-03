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
        for (int i = 0; i < 20; i++) {
            List<RecognizeResult> recognizeResultList = new ArrayList<>();
            RecognizeResult recognizeResult = new RecognizeResult();
            recognizeResult.scores = new Scores();
            Scores scores = recognizeResult.scores;
            scores.anger = Math.random();
            scores.disgust = Math.random();
            scores.fear = Math.random();
            scores.happiness = Math.random();
            scores.neutral = Math.random();
            scores.surprise = Math.random();
            scores.contempt = Math.random();
            scores.sadness = Math.random();
            recognizeResultList.add(recognizeResult);

            emotionData.addEmotionData(recognizeResultList, (long) (EmotionData.MAX_GAP
                    * i + EmotionData.MAX_GAP * Math.random()));
        }
//        EmotionWeekData  weekData = emotionData.getEmotionWeekData(0, true);

        EmotionDataEntity emotionDataEntity = emotionData.getLastData();
        Log.d(TAG, String.valueOf(emotionData));

    }
}
