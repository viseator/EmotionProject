package com.viseator.emotionproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
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
    private int height=wm.getDefaultDisplay().getHeight();
    private float x=distance/8;
    private OnItemSelectListener listener;
    float xPress=0,yPress=0;
    public int getMyHeight(){
        return height;
    }

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
                            paint.setColor(Color.parseColor("#FFCD38"));
                            break;
                        case CONTEMPT:
                            paint.setColor(Color.parseColor("#FFFE9F"));
                            break;
                        case DISGUST:
                            paint.setColor(Color.parseColor("#FFDD67"));
                            break;
                        case SADNESS:
                            paint.setColor(Color.parseColor("#4A4A4A"));
                            break;
                        case FEAR:
                            paint.setColor(Color.parseColor("#FCA180"));
                            break;
                        case NEUTRAL:
                            paint.setColor(Color.parseColor("#F3F3F3"));
                            break;
                        case SURPRISE:
                            paint.setColor(Color.parseColor("#FFD480"));
                            break;
                        case HAPPINESS:
                            paint.setColor(Color.parseColor("#F56262"));
                            break;
                    }
                    canvas.drawRect(distance/8+a*x+distance/32,start*3,distance/8+a*x+x-distance/32,end*3,paint);
                }
            }

        }
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(2);
        for(int i=0;i<=1440*3;i++){
            if((i%45)==0) {
                if(i%180==0) {
                    canvas.drawLine(0, i, distance / 25, i, paint);
                    paint.setTextSize(20);
                    canvas.drawText(String.valueOf(i/180)+":00",distance/25,i,paint);
                }
                else canvas.drawLine(0, i, distance / 50, i, paint);
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width=MeasureSpec.getSize(widthMeasureSpec);
        int height=1440*3;
        setMeasuredDimension(width,height);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                xPress=event.getX();
                yPress=event.getY();
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
