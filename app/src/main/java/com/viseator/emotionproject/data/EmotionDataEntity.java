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
    Long id;

    private Long time;

    @Generated(hash = 149199993)
    public EmotionDataEntity(Long id, Long time) {
        this.id = id;
        this.time = time;
    }

    @Generated(hash = 1448679362)
    public EmotionDataEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return this.time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


}
