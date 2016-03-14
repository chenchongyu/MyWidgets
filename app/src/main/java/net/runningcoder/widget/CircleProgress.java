package net.runningcoder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import net.runningcoder.R;
import net.runningcoder.util.L;


/**
 * Created by Administrator on 2016/3/12.
 */
public class CircleProgress extends View {

    //paints
    private Paint outerCirclePaint = new Paint();
    private Paint textPaint = new Paint();
    private Paint arcPaint = new Paint();//圆弧画笔
    private Paint rectPaint = new Paint();//矩形画笔

    //colors
    private static int DEFAULT_COLOR = Color.parseColor("#b3daee");
    private int circleColor = Color.parseColor("#b3daee");
    private int textColor = Color.GREEN;
    private int arcColor = Color.BLUE;
    //datas
    private int circleRadius=100;//内圆半径
    private int arcWidth = 15;//圆弧宽度
    private int arcLong = 30;//圆弧长度
    private String loadintText = "loading...";
    private int textSize = 30;
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
        parseAttrs(context.obtainStyledAttributes(attrs, R.styleable.CircleProgress));
    }

    private void parseAttrs(TypedArray a) {
        circleColor = a.getColor(R.styleable.CircleProgress_circleColor,DEFAULT_COLOR);
        if (a.hasValue(R.styleable.CircleProgress_loadingTextColor))
            L.i("获取到textcorlor");
        textColor = a.getColor(R.styleable.CircleProgress_loadingTextColor,Color.BLACK);
        arcColor = a.getColor(R.styleable.CircleProgress_arcColor,Color.BLUE);

        textSize = (int)a.getDimension(R.styleable.CircleProgress_loadingTextSize,20);
        arcWidth = (int)a.getDimension(R.styleable.CircleProgress_arc_width,15);
        circleRadius = a.getInt(R.styleable.CircleProgress_radius,100);
        arcLong = a.getInt(R.styleable.CircleProgress_arc_long, 30);
        speed = a.getInt(R.styleable.CircleProgress_speed, 2);

        if (a.hasValue(R.styleable.CircleProgress_loading_text))
            loadintText = a.getString(R.styleable.CircleProgress_loading_text);
        a.recycle();

        initPaint();
    }


    private void initPaint() {
        outerCirclePaint.setColor(circleColor);
        outerCirclePaint.setAntiAlias(true);
        outerCirclePaint.setStyle(Paint.Style.STROKE);
        outerCirclePaint.setStrokeWidth(arcWidth);

        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(textSize);
        textPaint.setStyle(Paint.Style.STROKE);

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
//        L.i(progress+"------------onDraw------------");
        int centerX = circleRadius + arcWidth + getPaddingLeft();
        int centerY = circleRadius + arcWidth + getPaddingTop();
//        int arcRadius = arcWidth/2;
        canvas.drawCircle(centerX, centerY, circleRadius, outerCirclePaint);
//        canvas.drawCircle(centerX, centerY, circleRadius, innerCirclePaint);

        RectF rectF = new RectF(getPaddingLeft()+ arcWidth,getPaddingTop()+ arcWidth,
                getPaddingLeft()+2*circleRadius+arcWidth ,getPaddingTop()+2*circleRadius+arcWidth);
//        canvas.drawRect(rectF,rectPaint);
        if (isLoading){
//          画固定长度的圆弧，不断更新圆弧位置
            canvas.drawArc(rectF, -90+startAngle,arcLong,false,arcPaint);
            float offset = textPaint.measureText(loadintText)/2;
            canvas.drawText(loadintText,centerX-offset,centerY,textPaint);
        }else{
            canvas.drawArc(rectF, -90,startAngle,false,arcPaint);//从-90度（12点位置）开始不断累积圆弧

            float offset = textPaint.measureText(progress + "%") / 2;
            canvas.drawText(progress + "%", centerX-offset,centerY,textPaint);
        }

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
            delay = isLoading?0:0;
            if (isLoading || destProgress>progress){
                sendEmptyMessageDelayed(msg.what, delay);
            }

//            super.handleMessage(msg);
        }
    };
}
