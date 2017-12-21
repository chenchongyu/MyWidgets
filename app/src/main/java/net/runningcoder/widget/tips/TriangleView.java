
/**
 * Copyright (C) 2014 Itai Hanski
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package net.runningcoder.widget.tips;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;


/**
 * Copy from TriangleView create by Itai Hanski.
 * @version 1.0
 * @author SpringXu
 */
public class TriangleView extends View {

    private static final Direction DEFAULT_DIRECTION = Direction.LEFT;
    private static final int DEFAULT_COLOR = 0xff757575;

    private Paint mPaint,mPathPaint;
    private Path mTrianglePath,mTriangleArea;
    private Direction mDirection;
    private int mColor,mPathColor;
    private float borderWidth=1;

    public TriangleView(Context context) {
        this(context, null);
    }

    public TriangleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TriangleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mDirection = DEFAULT_DIRECTION;
        mColor = DEFAULT_COLOR;
        mPathColor = DEFAULT_COLOR;


        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mPaint.setAlpha(204);

        mPathPaint = new Paint();
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setStrokeWidth(borderWidth);
        mPathPaint.setColor(mPathColor);
        mPathPaint.setAntiAlias(true);
    }

    /**
     * Set the color of the triangle.
     * @param color the color of the triangle.
     */
    public void setColor(int color) {
        if (mColor != color) {
            mColor = color;
            if (mPaint != null) {
                mPaint.setColor(color);
            }
            mTrianglePath = null;
            invalidate();
        }
    }

    public void setBorderColor(int color) {
        if (mPathColor != color) {
            mPathColor = color;
            if (mPathPaint != null) {
                mPathPaint.setColor(color);
            }
            mTrianglePath = null;
            invalidate();
        }
    }

    public void setBorderWidth(float width){
        this.borderWidth = width;
        mPathPaint.setStrokeWidth(width);

        invalidate();
    }

    public void setDirection(Direction direction) {
        if (direction != mDirection) {
            mDirection = direction;
            mTrianglePath = null;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(getTriangleArea(), mPaint);
        canvas.drawPath(getTrianglePath2(), mPathPaint);
    }

    private Path getTriangleArea() {
        if (mTriangleArea == null) {
            mTriangleArea = new Path();
            int width = getWidth();
            int height = getHeight();
            Point p1, p2, p3;
            switch (mDirection) {
                case LEFT:
                    p1 = new Point(width, 0);
                    p2 = new Point(width, height);
                    p3 = new Point(0, height / 2);
                    break;
                case UP:
                    p1 = new Point(0, height);
                    p2 = new Point(width, height);
                    p3 = new Point(width / 2, 0);
                    break;
                case RIGHT:
                    p1 = new Point(0, 0);
                    p2 = new Point(0, height);
                    p3 = new Point(width, height / 2);
                    break;
                case DOWN:
                default:
                    p1 = new Point(0, 0);
                    p2 = new Point(width, 0);
                    p3 = new Point(width / 2, height);
            }
            mTriangleArea.moveTo(p1.x, p1.y);
            mTriangleArea.lineTo(p2.x, p2.y);
            mTriangleArea.lineTo(p3.x, p3.y);
        }
        return mTriangleArea;
    }

    private Path getTrianglePath2() {
        if (mTrianglePath == null){
            mTrianglePath = new Path();
            int width = getWidth();
            int height = getHeight();
            Point p1, p2, p3;
            switch (mDirection) {
                case LEFT:
                    p1 = new Point(width, 0);
                    p2 = new Point(width, height);
                    p3 = new Point(0, height / 2);
                    break;
                case UP:
                    p1 = new Point(0, height);
                    p2 = new Point(width, height);
                    p3 = new Point(width / 2, 0);
                    break;
                case RIGHT:
                    p1 = new Point(0, 0);
                    p2 = new Point(0, height);
                    p3 = new Point(width, height / 2);
                    break;
                case DOWN:
                default:
                    p1 = new Point(0, 0);
                    p2 = new Point(width, 0);
                    p3 = new Point(width / 2, height);
            }
//            mTrianglePath.moveTo(p1.x, p1.y);
            mTrianglePath.moveTo(p2.x, p2.y);
            mTrianglePath.lineTo(p3.x, p3.y);
            mTrianglePath.lineTo(p1.x, p1.y);
        }

        return mTrianglePath;
    }

    public enum Direction {
        LEFT,
        UP,
        RIGHT,
        DOWN
    }

    public enum PosStrategy{
        AUTO_CENTER,
        ANCHOR_CENTER
    }
}
