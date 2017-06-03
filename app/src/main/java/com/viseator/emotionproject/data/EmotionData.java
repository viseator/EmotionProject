package com.viseator.emotionproject.data;

import android.util.Log;
import android.widget.ExpandableListView;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;
import com.microsoft.projectoxford.face.contract.Emotion;

import java.util.List;
import java.util.Properties;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class EmotionData {
    private static EmotionData INSTANCE = null;
    private EmotionDataEntityDao mEmotionDataEntityDao;
    private static final String TAG = "@vir EmotionData";

    public static EmotionData getInstance() {
        return INSTANCE;
    }

    public static EmotionData getInstance(EmotionDataEntityDao emotionDataEntityDao) {
        INSTANCE = new EmotionData(emotionDataEntityDao);
        return INSTANCE;
    }

    private EmotionData(EmotionDataEntityDao emotionDataEntityDao) {
        mEmotionDataEntityDao = emotionDataEntityDao;
    }


    public int count() {
        return (int) mEmotionDataEntityDao.count();
    }

    public void addEmotionData(List<RecognizeResult> results, long time) {
        if (results.size() < 1) {
            return;
        }
        Scores recognizeResult = results.get(0).scores;
        EmotionDataEntity emotionDataEntity = new EmotionDataEntity();
        emotionDataEntity.setFear(recognizeResult.fear);
        emotionDataEntity.setAnger(recognizeResult.anger);
        emotionDataEntity.setContempt(recognizeResult.contempt);
        emotionDataEntity.setDisgust(recognizeResult.disgust);
        emotionDataEntity.setHappiness(recognizeResult.happiness);
        emotionDataEntity.setSurprise(recognizeResult.surprise);
        emotionDataEntity.setNeutral(recognizeResult.neutral);
        emotionDataEntity.setSadness(recognizeResult.sadness);
        emotionDataEntity.setTime(time);
        mEmotionDataEntityDao.insert(emotionDataEntity);
    }

    public List<EmotionDataEntity> getEmotionData(long startTime, long endTime) {
        return (List<EmotionDataEntity>) mEmotionDataEntityDao.queryBuilder().where
                (EmotionDataEntityDao.Properties.Time.between(startTime, endTime));
    }

    public void removeAllData() {
        mEmotionDataEntityDao.deleteAll();
    }

}
