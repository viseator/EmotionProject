package com.viseator.emotionproject.data.view;

public enum EmotionRank {
    ANGER,
    CONTEMPT,
    DISGUST,
    FEAR,
    HAPPINESS,
    NEUTRAL,
    SADNESS,
    SURPRISE;

    @Override
    public String toString() {
        switch (this) {
            case ANGER:
                return "愤怒";
            case CONTEMPT:
                return "蔑视";
            case DISGUST:
                return "恶心";
            case FEAR:
                return "恐惧";
            case HAPPINESS:
                return "快乐";
            case NEUTRAL:
                return "冷漠";
            case SADNESS:
                return "悲伤";
            case SURPRISE:
                return "惊讶";
        }
        return null;
    }
}
