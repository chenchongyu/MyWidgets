package net.runningcoder.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import net.runningcoder.R;

/**
 * Created by Administrator on 2016/10/10.
 */
public class ScratchCardView extends View {

    private int mForgroundColor = Color.GRAY;
    private Bitmap mBackBitmap,mForBitmap;
    private Paint mPaint;
    private Canvas mCanvas;
    private Path mPath;
    private int paintWidth=50;

    public ScratchCardView(Context context) {
        this(context,null);
    }

    public ScratchCardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBackBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lrt);
        mForBitmap = Bitmap.createBitmap(mBackBitmap.getWidth(),mBackBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(paintWidth);
//        mPaint.setAlpha(0);
//        mPaint.setDither(true);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT) );

        mPath = new Path();

        mCanvas = new Canvas(mForBitmap);
        mCanvas.drawColor(mForgroundColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas.drawPath(mPath,mPaint);
        canvas.drawBitmap(mBackBitmap,0,0,null);
        canvas.drawBitmap(mForBitmap,0,0,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(),event.getY());
                invalidate();
                break;
        }
        invalidate();
        return true;
    }
}
