package com.viseator.emotionproject.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by viseator on 6/3/17.
 * Wu Di
 * Email: viseator@gmail.com
 */
@Entity
public class EmotionDataEntity {
    @Id
    long id;

    private long time;
    protected double anger;
    protected double contempt;
    protected double disgust;
    protected double fear;
    protected double happiness;
    protected double neutral;
    protected double sadness;
    protected double surprise;
    @Generated(hash = 1905874642)
    public EmotionDataEntity(long id, long time, double anger, double contempt,
            double disgust, double fear, double happiness, double neutral,
            double sadness, double surprise) {
        this.id = id;
        this.time = time;
        this.anger = anger;
        this.contempt = contempt;
        this.disgust = disgust;
        this.fear = fear;
        this.happiness = happiness;
        this.neutral = neutral;
        this.sadness = sadness;
        this.surprise = surprise;
    }
    @Generated(hash = 1448679362)
    public EmotionDataEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public double getAnger() {
        return this.anger;
    }
    public void setAnger(double anger) {
        this.anger = anger;
    }
    public double getContempt() {
        return this.contempt;
    }
    public void setContempt(double contempt) {
        this.contempt = contempt;
    }
    public double getDisgust() {
        return this.disgust;
    }
    public void setDisgust(double disgust) {
        this.disgust = disgust;
    }
    public double getFear() {
        return this.fear;
    }
    public void setFear(double fear) {
        this.fear = fear;
    }
    public double getHappiness() {
        return this.happiness;
    }
    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }
    public double getNeutral() {
        return this.neutral;
    }
    public void setNeutral(double neutral) {
        this.neutral = neutral;
    }
    public double getSadness() {
        return this.sadness;
    }
    public void setSadness(double sadness) {
        this.sadness = sadness;
    }
    public double getSurprise() {
        return this.surprise;
    }
    public void setSurprise(double surprise) {
        this.surprise = surprise;
    }
}

