package net.runningcoder.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import net.runningcoder.util.L;


/**
 * Created by Administrator on 2016/3/12.
 */
public class CircleProgress extends View {

    //paints
    private Paint outerCirclePaint = new Paint();
    private Paint innerCirclePaint = new Paint();
    private Paint arcPaint = new Paint();//圆弧画笔
    private Paint rectPaint = new Paint();//圆弧画笔

    //colors
    private int outerColor = Color.BLACK;
    private int innerColor = Color.BLACK;
    private int arcColor = Color.BLUE;
    //datas
    private int circleRadius=100;//内圆半径
    private int arcWidth = 15;//圆弧宽度
    private int arcLong = 30;//圆弧长度

    private int destProgress;
    private int progress = 0;
    private int speed = 2;
    private int max = 100;

    private boolean isLoading = true;

    public CircleProgress(Context context) {
        super(context);

        initPaint();
    }

    public CircleProgress(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    private void initPaint() {
        outerCirclePaint.setColor(outerColor);
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setStyle(Paint.Style.STROKE);

        innerCirclePaint.setColor(innerColor);
        innerCirclePaint.setAntiAlias(true);
        innerCirclePaint.setStyle(Paint.Style.STROKE);

        arcPaint.setColor(arcColor);
        arcPaint.setAntiAlias(true);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(arcWidth);

        rectPaint.setColor(Color.GREEN);
        rectPaint.setAntiAlias(true);
        rectPaint.setStyle(Paint.Style.STROKE);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float startAngle = progress* 360 / 100 ;
        L.i(progress+"------------onDraw------------"+startAngle);
        int centerX = circleRadius + arcWidth + getPaddingLeft();
        int centerY = circleRadius + arcWidth + getPaddingTop();
        int arcRadius = arcWidth/2;
        canvas.drawCircle(centerX, centerY, circleRadius + arcWidth, outerCirclePaint);
        canvas.drawCircle(centerX, centerY, circleRadius, innerCirclePaint);

        RectF rectF = new RectF(getPaddingLeft()+arcRadius,getPaddingTop()+arcRadius,
                getPaddingLeft()+2*(circleRadius+arcWidth)-arcRadius,getPaddingTop()+2*(circleRadius+arcWidth)-arcRadius);
//        canvas.drawRect(rectF,rectPaint);
        if (isLoading){
            //画固定长度的圆弧，不断更新圆弧位置
            canvas.drawArc(rectF, -90+startAngle,arcLong,false,arcPaint);
        }else
            canvas.drawArc(rectF, -90,startAngle,false,arcPaint);//从-90度（12点位置）开始不断累积圆弧
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
        validateHandler.sendEmptyMessage(1);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        validateHandler.removeMessages(1);
        isLoading = false;

        progress = progress > max?max:progress;
        this.progress = 0;
        this.destProgress = progress;
        validateHandler.sendEmptyMessage(0);
    }


    private long delay = 80;
    private Handler validateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            invalidate();
            progress+=speed;
            delay = isLoading?0:delay;
            if (isLoading || destProgress>progress){
                sendEmptyMessageDelayed(msg.what, delay);
            }

//            super.handleMessage(msg);
        }
    };
}
