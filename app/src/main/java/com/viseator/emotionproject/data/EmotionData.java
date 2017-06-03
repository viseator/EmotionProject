package com.viseator.emotionproject.data;

import android.util.Log;

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
    public static final long MILLS_OF_DAY = 24 * 60 * 60 * 100;
    public static final long MAX_GAP = 15 * 60 * 100;
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
            Log.d(TAG, "add data size :" + String.valueOf(results.size()));
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
        return mEmotionDataEntityDao.queryBuilder().where
                (EmotionDataEntityDao.Properties.Time.between(startTime, endTime)).list();
    }


    public void removeAllData() {
        mEmotionDataEntityDao.deleteAll();
    }

    public EmotionDataEntity getLastData() {
        return mEmotionDataEntityDao.queryBuilder().list().get(count() - 1);
    }

    public EmotionWeekData getEmotionWeekData(long startDayTime, boolean showRecent) {
        // TODO: 6/3/17 add not show recent
        List<EmotionDayData> dayDataList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            EmotionDayData emotionDayData = new EmotionDayData();
            List<EmotionViewData> emotionViewDataList = generateEmotionDataOfDay
                    (startDayTime - MILLS_OF_DAY * i);
            emotionDayData.setEmotionViewDataList(emotionViewDataList);

            int[] rankSum = new int[]{0, 0, 0, 0, 0, 0, 0, 0};

            for (EmotionViewData viewData : emotionViewDataList) {
                switch (viewData.getRank()) {
                    case ANGER:
                        rankSum[0] += viewData.getEndMins() - viewData.getStartMins();
                        break;
                    case CONTEMPT:
                        rankSum[1] += viewData.getEndMins() - viewData.getStartMins();
                        break;
                    case DISGUST:
                        rankSum[2] += viewData.getEndMins() - viewData.getStartMins();
                        break;
                    case FEAR:
                        rankSum[3] += viewData.getEndMins() - viewData.getStartMins();
                        break;
                    case HAPPINESS:
                        rankSum[4] += viewData.getEndMins() - viewData.getStartMins();
                        break;
                    case NEUTRAL:
                        rankSum[5] += viewData.getEndMins() - viewData.getStartMins();
                        break;
                    case SADNESS:
                        rankSum[6] += viewData.getEndMins() - viewData.getStartMins();
                        break;
                    case SURPRISE:
                        rankSum[7] += viewData.getEndMins() - viewData.getStartMins();
                        break;
                }
            }
            int max = rankSum[0];
            EmotionRank rankMax = EmotionRank.ANGER;
            for (int j = 1; j < 8; j++) {
                if (rankSum[j] > max) {
                    max = rankSum[j];
                    rankMax = getRankFromId(j);
                }
            }
            emotionDayData.setMostOfDay(rankMax);
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
            if (emotionViewData.getStartMins() == emotionViewData.getEndMins() || emotionViewData
                    .getEndMins() - emotionViewData.getStartMins() < 2 * MAX_GAP) {
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
            }
            emotionViewData.setRank(emotionRank);
            emotionViewData.setTotalEmotionData(totalEmotionData);
            result.add(emotionViewData);
        }
        return result;
    }

    public static List<Double> convertEntityToList(EmotionDataEntity entity) {
        List<Double> result = new ArrayList<>();
        result.add(entity.getAnger() > 0.1 ? entity.getAnger() : 0);
        result.add(entity.getContempt() > 0.1 ? entity.getContempt() : 0);
        result.add(entity.getDisgust() > 0.1 ? entity.getDisgust() : 0);
        result.add(entity.getFear() > 0.1 ? entity.getFear() : 0);
        result.add(entity.getHappiness() > 0.1 ? entity.getHappiness() : 0);
        result.add(entity.getNeutral() > 0.1 ? entity.getNeutral() : 0);
        result.add(entity.getSadness() > 0.1 ? entity.getSadness() : 0);
        result.add(entity.getSurprise() > 0.1 ? entity.getSurprise() : 0);
        return result;
    }

    public static EmotionRank getRankFromId(int id) {
        switch (id) {
            case 0:
                return EmotionRank.ANGER;
            case 1:
                return EmotionRank.CONTEMPT;
            case 2:
                return EmotionRank.DISGUST;
            case 3:
                return EmotionRank.FEAR;
            case 4:
                return EmotionRank.HAPPINESS;
            case 5:
                return EmotionRank.NEUTRAL;
            case 6:
                return EmotionRank.SADNESS;
            case 7:
                return EmotionRank.SURPRISE;
        }
        return null;
    }

    public static int getIdFromRank(EmotionRank rank) {
        switch (rank) {
            case ANGER:
                return 0;
            case CONTEMPT:
                return 1;
            case DISGUST:
                return 2;
            case FEAR:
                return 3;
            case HAPPINESS:
                return 4;
            case NEUTRAL:
                return 5;
            case SADNESS:
                return 6;
            case SURPRISE:
                return 7;
        }

        return -1;
    }
}
