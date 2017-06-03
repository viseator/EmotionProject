package com.viseator.emotionproject.test;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;
import com.microsoft.projectoxford.face.contract.Emotion;
import com.viseator.emotionproject.data.EmotionData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class Test {
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

            emotionData.addEmotionData(recognizeResultList, (long) (EmotionData.MILLS_OF_DAY
                    * i + EmotionData.MILLS_OF_DAY * Math.random()));
        }


    }
}
