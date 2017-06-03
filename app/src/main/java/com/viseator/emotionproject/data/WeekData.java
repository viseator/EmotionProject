package com.viseator.emotionproject.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanhao on 17-6-3.
 */

public class WeekData {
    private List<DayData> dayDatas;
    public WeekData(){
        dayDatas=new ArrayList<DayData>();
    }

    public void putDayData(DayData dayData){
        dayDatas.add(dayData);
    }

    public DayData getDayDatas(int i){
        if(i>=dayDatas.size())
            return null;
        else return dayDatas.get(i);
    }
}
