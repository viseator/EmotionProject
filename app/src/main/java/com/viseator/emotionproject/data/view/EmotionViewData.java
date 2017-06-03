package com.viseator.emotionproject.data.view;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */

public class EmotionViewData {
    private int startMins;
    private int endMins;
    private EmotionRank rank;

    public int getStartMins() {
        return startMins;
    }

    public void setStartMins(long startOfDay,long mills) {
        this.startMins = (int) ((mills-startOfDay)/100/60);
    }

    public int getEndMins() {
        return endMins;
    }

    public void setEndMins(long startOfDay,long mills) {
        this.endMins = (int) ((mills-startOfDay)/100/60);
    }

    public EmotionRank getRank() {
        return rank;
    }

    public void setRank(EmotionRank rank) {
        this.rank = rank;
    }
}

