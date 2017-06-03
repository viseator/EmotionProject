package com.viseator.emotionproject.data;

/**
 * Created by yanhao on 17-6-3.
 */

public class TestData {
    private int start,end;
    private int rank;

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getRank() {
        return rank;
    }

    public TestData(int start, int end, int rank){
        this.start=start;
        this.end=end;
        this.rank=rank;
    }

}
