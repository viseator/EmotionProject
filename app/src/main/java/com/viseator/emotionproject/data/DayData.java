package com.viseator.emotionproject.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanhao on 17-6-3.
 */

public class DayData {
    private List<TestData> testDatas;
    public DayData(){
        testDatas=new ArrayList<TestData>();
    }

    public void putTestData(TestData testData){
        testDatas.add(testData);
    }

    public TestData getTestDatas(int i){
            return testDatas.get(i);
    }
    public int maxSize(){
        return testDatas.size();
    }
}
