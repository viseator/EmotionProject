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
    private float anger;
    private float contempt;
    private float disgust;
    private float fear;
    private float happiness;
    private float neutral;
    private float sadness;
    private float surprise;
    @Generated(hash = 1071886841)
    public EmotionDataEntity(long id, long time, float anger, float contempt,
            float disgust, float fear, float happiness, float neutral,
            float sadness, float surprise) {
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
    public float getAnger() {
        return this.anger;
    }
    public void setAnger(float anger) {
        this.anger = anger;
    }
    public float getContempt() {
        return this.contempt;
    }
    public void setContempt(float contempt) {
        this.contempt = contempt;
    }
    public float getDisgust() {
        return this.disgust;
    }
    public void setDisgust(float disgust) {
        this.disgust = disgust;
    }
    public float getFear() {
        return this.fear;
    }
    public void setFear(float fear) {
        this.fear = fear;
    }
    public float getHappiness() {
        return this.happiness;
    }
    public void setHappiness(float happiness) {
        this.happiness = happiness;
    }
    public float getNeutral() {
        return this.neutral;
    }
    public void setNeutral(float neutral) {
        this.neutral = neutral;
    }
    public float getSadness() {
        return this.sadness;
    }
    public void setSadness(float sadness) {
        this.sadness = sadness;
    }
    public float getSurprise() {
        return this.surprise;
    }
    public void setSurprise(float surprise) {
        this.surprise = surprise;
    }
    


}
