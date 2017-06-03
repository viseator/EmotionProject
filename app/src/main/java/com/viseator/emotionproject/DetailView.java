package com.viseator.emotionproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.viseator.emotionproject.data.view.EmotionDayData;
import com.viseator.emotionproject.data.view.EmotionRank;
import com.viseator.emotionproject.data.view.EmotionViewData;
import com.viseator.emotionproject.data.view.EmotionWeekData;

/**
 * Created by yanhao on 17-6-3.
 */

public class DetailView extends View {
    private EmotionWeekData weekData;
    private Paint paint;
    private WindowManager wm=(WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
    private int distance=wm.getDefaultDisplay().getWidth();
    private float x=distance/8;
    private OnItemSelectListener listener;


    public DetailView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
        paint=new Paint();
    }

    public void setWeekData(EmotionWeekData weekData){
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
            EmotionDayData dayData=weekData.getEmotionDayDataList(a);
            if(dayData.maxSizeOfEmotionViewData()!=0){
                int max=dayData.maxSizeOfEmotionViewData();
                for(int b=0;b<max;b++){
                    EmotionViewData viewData=dayData.getEmotionViewDataList(b);
                    int start=viewData.getStartMins();
                    int end=viewData.getEndMins();
                    EmotionRank rank= viewData.getRank();
                    switch (rank){
                        case ANGER:
                            paint.setColor(Color.GREEN);
                            break;
                        case CONTEMPT:
                            paint.setColor(Color.BLACK);
                            break;
                        case DISGUST:
                            paint.setColor(Color.BLUE);
                            break;
                        case SADNESS:
                            paint.setColor(Color.LTGRAY);
                            break;
                        case FEAR:
                            paint.setColor(Color.CYAN);
                            break;
                        case NEUTRAL:
                            paint.setColor(Color.DKGRAY);
                            break;
                        case SURPRISE:
                            paint.setColor(Color.MAGENTA);
                            break;
                        case HAPPINESS:
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
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=1440;
        setMeasuredDimension(width,height);
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
