package com.viseator.emotionproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.viseator.emotionproject.data.DayData;
import com.viseator.emotionproject.data.TestData;
import com.viseator.emotionproject.data.WeekData;

import java.text.AttributedCharacterIterator;

/**
 * Created by yanhao on 17-6-3.
 */

public class DetailView extends View {
    private WeekData weekData;
    private Paint paint;
    private WindowManager wm=(WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
    private int distance=wm.getDefaultDisplay().getWidth();
    private float x=distance/8;
    private OnItemSelectListener listener;


    public DetailView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        paint=new Paint();
    }

    public void setWeekData(WeekData weekData){
        this.weekData=weekData;
    }

    public interface OnItemSelectListener{
        void OnItemSelect(float y,int num);
    }

    public void setItemSelectListener(OnItemSelectListener listener){
        this.listener=listener;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int a=0;
        for(;a<7;a++){
            DayData dayData=weekData.getDayDatas(a);
            if(dayData!=null){
                int max=dayData.maxSize();
                for(int b=0;b<max;b++){
                    TestData testData=dayData.getTestDatas(b);
                    int start=testData.getStart();
                    int end=testData.getEnd();
                    int rank=testData.getRank();
                    switch (rank){
                        case 0:
                            paint.setColor(Color.GREEN);
                            break;
                        case 1:
                            paint.setColor(Color.BLACK);
                            break;
                        case 2:
                            paint.setColor(Color.BLUE);
                            break;
                        case 3:
                            paint.setColor(Color.LTGRAY);
                            break;
                        case 4:
                            paint.setColor(Color.CYAN);
                            break;
                        case 5:
                            paint.setColor(Color.DKGRAY);
                            break;
                        case 6:
                            paint.setColor(Color.MAGENTA);
                            break;
                        case 7:
                            paint.setColor(Color.RED);
                            break;
                    }
                    canvas.drawRect(distance/8+a*x,start,distance/8+a*x+x,end,paint);
                }
            }

        }
        /*
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        for(int i=0;i<=1440;i++){
            if((i%30)==0)

        }
        canvas.drawLine(0,50,distance/9,50,paint);
        canvas.drawLine(0,60,distance/9,60,paint);
        */
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                float xPress=event.getX();
                float yPress=event.getY();
                int result=dispatchX(xPress);
                listener.OnItemSelect(yPress,result);
                break;
        }
        return super.onTouchEvent(event);
    }

    private int dispatchX(float xPress){
        int a=1;
        for(;a<=7;a++){
            if(xPress>a*x&&xPress<(a+1)*x)
                return a;
        }
        return -1;
    }
}
