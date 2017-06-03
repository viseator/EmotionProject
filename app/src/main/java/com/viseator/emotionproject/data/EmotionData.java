package com.viseator.emotionproject.data;

import android.support.annotation.ArrayRes;

import com.microsoft.projectoxford.emotion.contract.RecognizeResult;
import com.microsoft.projectoxford.emotion.contract.Scores;
import com.viseator.emotionproject.data.view.EmotionDayData;
import com.viseator.emotionproject.data.view.EmotionRank;
import com.viseator.emotionproject.data.view.EmotionViewData;
import com.viseator.emotionproject.data.view.EmotionWeekData;
import com.viseator.emotionproject.data.view.TotalEmotionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class EmotionData {
    private static final long MILLS_OF_DAY = 24 * 60 * 60 * 100;
    private static final long MAX_GAP = 15 * 60 * 100;
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

    public EmotionWeekData getEmotionWeekData(long startDayTime, boolean showRecent) {
        List<EmotionDayData> dayDataList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            EmotionDayData emotionDayData = new EmotionDayData();
            emotionDayData.setEmotionDataList(generateEmotionDataOfDay
                    (startDayTime - MILLS_OF_DAY * i));
            dayDataList.add(emotionDayData);
        }
        EmotionWeekData emotionWeekData = new EmotionWeekData();
        emotionWeekData.setEmotionDayDataList(dayDataList);
        return emotionWeekData;
    }

    private List<EmotionViewData> generateEmotionDataOfDay(long startDayTime) {
        List<EmotionDataEntity> emotionDataEntities = getEmotionData(startDayTime,
                startDayTime + MILLS_OF_DAY);
        int i = 0;
        List<EmotionViewData> result = new ArrayList<>();
        while (i < emotionDataEntities.size()) {
            EmotionViewData emotionViewData = new EmotionViewData();
            emotionViewData.setStartMins(startDayTime, emotionDataEntities.get(i).getTime());
            emotionViewData.setEndMins(startDayTime, emotionDataEntities.get(i).getTime());

            TotalEmotionData totalEmotionData = new TotalEmotionData();
            totalEmotionData.add(emotionDataEntities.get(i));
            while (i + 1 < emotionDataEntities.size() && emotionDataEntities.get(i + 1).getTime()
                    - emotionDataEntities.get(i).getTime() < MAX_GAP) {
                emotionViewData.setEndMins(startDayTime, emotionDataEntities.get(++i).getTime());
                totalEmotionData.add(emotionDataEntities.get(i));
            }
            ++i;
            if (emotionViewData.getStartMins() == emotionViewData.getEndMins()) {
                continue;
            }

            double maxValue = totalEmotionData.getAnger();
            EmotionRank emotionRank = EmotionRank.ANGER;
            if (totalEmotionData.getContempt() > maxValue) {
                emotionRank = EmotionRank.CONTEMPT;
                maxValue = totalEmotionData.getContempt();
            }
            if (totalEmotionData.getDisgust() > maxValue) {
                emotionRank = EmotionRank.DISGUST;
                maxValue = totalEmotionData.getDisgust();
            }
            if (totalEmotionData.getFear() > maxValue) {
                emotionRank = EmotionRank.FEAR;
                maxValue = totalEmotionData.getFear();
            }
            if (totalEmotionData.getHappiness() > maxValue) {
                emotionRank = EmotionRank.HAPPINESS;
                maxValue = totalEmotionData.getHappiness();
            }
            if (totalEmotionData.getNeutral() > maxValue) {
                emotionRank = EmotionRank.NEUTRAL;
                maxValue = totalEmotionData.getNeutral();
            }
            if (totalEmotionData.getSurprise() > maxValue) {
                emotionRank = EmotionRank.SURPRISE;
                maxValue = totalEmotionData.getSurprise();
            }
            if (totalEmotionData.getSadness() > maxValue) {
                emotionRank = EmotionRank.SADNESS;
                maxValue = totalEmotionData.getSadness();
            }
            emotionViewData.setRank(emotionRank);
            result.add(emotionViewData);
        }
        return result;
    }
}
