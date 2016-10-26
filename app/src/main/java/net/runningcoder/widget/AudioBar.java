package net.runningcoder.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/30.
 */
public class AudioBar extends View {
    private static final int RECT_COUNT = 10;
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private int offset = 5;//每个矩形直接的间距
    private int mRectWidth = 20;//每个矩形的宽度
    private int mRectHeight = 80;//每个矩形的高度

    private boolean dance = false;
    public AudioBar(Context context) {
        super(context);

        init();
    }

    public AudioBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int w = measureWidth(widthMeasureSpec);
        final int h = measureHeight(heightMeasureSpec);

        mLinearGradient = new LinearGradient(0,0,w,h, Color.YELLOW,Color.RED, Shader.TileMode.CLAMP);
        mPaint.setShader(mLinearGradient);

        setMeasuredDimension(w, h);
    }

    private int measureWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY)
            return width;
        if (mode == MeasureSpec.AT_MOST){
            return Math.min(width,(mRectWidth+offset)*RECT_COUNT);
        }
        return (mRectWidth+offset)*RECT_COUNT;
    }

    private int measureHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY)
            return height;
        if (mode == MeasureSpec.AT_MOST){
            return Math.min(height,mRectHeight);
        }
        return mRectHeight;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


    }

    private void init() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(int i=0;i<RECT_COUNT;i++){
            final float bottom = (float) (mRectHeight * Math.random());
            canvas.drawRect(mRectWidth*i+offset,bottom,mRectWidth*(i+1), mRectHeight,mPaint);
        }

        if (dance){
            postInvalidateDelayed(300);
        }
    }

    public void start(){
        dance = true;
        invalidate();
    }
    public void stop(){
        dance = false;
        invalidate();
    }

}
