package com.viseator.emotionproject.data;

import android.util.Log;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class EmotionData {
    private static EmotionDataEntity INSTANCE = null;
    private static final String TAG = "@vir EmotionData";

    public static EmotionDataEntity getEmotionDataEntity(EmotionDataEntityDao emotionDataEntityDao) {
        if (INSTANCE == null) {
            if (emotionDataEntityDao.count() == 0) {
                Log.d(TAG, String.valueOf("create database"));
                INSTANCE = create(emotionDataEntityDao);
            } else {
                Log.e(TAG, String.valueOf("INSTANCE FAILURE"));
            }
        }
        return INSTANCE;
    }

    private static EmotionDataEntity create(EmotionDataEntityDao emotionDataEntityDao) {
        EmotionDataEntity emotionDataEntity = new EmotionDataEntity();
        emotionDataEntityDao.insert(emotionDataEntity);
        return emotionDataEntity;
    }
}
