package net.runningcoder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import net.runningcoder.R;

/**
 * Created by ccy on 2016/3/16.
 * 根据paint的
 */
public class CircleImageView extends ImageView{
    public final static int SHAPE_CIRCLE = 1;
    public final static int SHARE_ROUND_RECT = 2;

    private Paint paint;
    private Paint circlePaint;//外圆画笔
    private int type;
    private Bitmap src;

    private int width;
    private float circleWidth;
    private int circleColor;
    private PorterDuffXfermode xfermode;
    private int paidding;
    private Rect srcRect;
    private Rect destRect;

    public CircleImageView(Context context) {
        super(context);
        parseAttrs(null);
    }



    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttrs(context.obtainStyledAttributes(attrs, R.styleable.CircleImageView));
    }
    private void parseAttrs(TypedArray a) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        if (a == null){
            type = SHAPE_CIRCLE;
        }else
            type = a.getInt(R.styleable.CircleImageView_image_type, SHAPE_CIRCLE);

        circleWidth = a.getDimension(R.styleable.CircleImageView_border_width, 0);
        circleColor = a.getColor(R.styleable.CircleImageView_border_color, Color.BLACK);
        src = ((BitmapDrawable) getDrawable()).getBitmap();
        paint = new Paint();
        paint.setAntiAlias(true);
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

        if (circleWidth > 0){
            circlePaint = new Paint();
            circlePaint.setAntiAlias(true);
            circlePaint.setStyle(Paint.Style.STROKE);
            circlePaint.setStrokeWidth(circleWidth);
            circlePaint.setColor(circleColor);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = Math.min(w,h);
        paidding = Math.min(getPaddingLeft(), getPaddingTop());
        srcRect = new Rect(0, 0, width, width);
        destRect = new Rect(paidding, paidding, width-paidding, width-paidding);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        src = Bitmap.createScaledBitmap(src, width, width, true);

        switch (type){
            case SHAPE_CIRCLE:
                drawCircleImage(canvas);
                if (circlePaint != null)
                    canvas.drawCircle(width / 2 , width / 2 , width / 2 - circleWidth/2 - paidding, circlePaint);
                break;
            case SHARE_ROUND_RECT:
                drawRectImage(canvas);
                break;
        }
    }

    private void drawCircleImage(Canvas canvas) {
        canvas.drawCircle(width / 2, width / 2, width / 2 - circleWidth - paidding, paint);

        paint.setXfermode(xfermode);

        canvas.drawBitmap(src, srcRect, destRect, paint);
        src.recycle();
    }

    private void drawRectImage(Canvas canvas) {
        RectF rectF = new RectF(destRect);
        canvas.drawRoundRect(rectF, 15, 15, paint);

        paint.setXfermode(xfermode);

        canvas.drawBitmap(src, srcRect, destRect, paint);
        src.recycle();
    }
}
