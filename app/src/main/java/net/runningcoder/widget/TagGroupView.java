package net.runningcoder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.runningcoder.R;
import net.runningcoder.util.L;

/**
 * TODO: document your custom view class.
 */
public class TagGroupView extends ViewGroup {
    private String mTitle;
    private int mTextColor = Color.RED;
    private int mBackgroundColor = Color.WHITE;

    private float mTextSize = 18;
    private boolean mIsReadonly = true;


    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private int horizontalSpacing = 5;//每个标签的横向间距
    private int verticalSpacing = 5;//每个标签的纵向间距



    public TagGroupView(Context context) {
        super(context);
        init(null, 0);
    }

    public TagGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public TagGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.TagGroupView, defStyle, 0);

        mTextColor = a.getColor(R.styleable.TagGroupView_tagTextColor,mTextColor);
        mBackgroundColor = a.getColor(R.styleable.TagGroupView_tagBackground,mBackgroundColor);

        mTextSize = a.getDimension(R.styleable.TagGroupView_tagTextSize, mTextSize);
        mIsReadonly = a.getBoolean(R.styleable.TagGroupView_readonly, mIsReadonly);

        horizontalSpacing = (int) a.getDimension(R.styleable.TagGroupView_tagHorizontalSpacing, horizontalSpacing);
        verticalSpacing = (int) a.getDimension(R.styleable.TagGroupView_tagVerticalSpacing, verticalSpacing);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        L.i("--------------onMeasure-------------");
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int row = 1;
        int height = 0;
        int width = 0;
        int rowWidth = 0;//根据行宽度与widthSize进行比较，判断是一行还是多行
        int rowHeight = 0;
        //group高度=每一行的高度+padding，宽度= row==1?每个子view的宽度:widthSize
        final int childCount = getChildCount();

        for(int i = 0;i<childCount;i++){
            View child = getChildAt(i);
            final int childHeight = child.getMeasuredHeight();
            final int childWidth = child.getMeasuredWidth()+horizontalSpacing;

            rowWidth += childWidth;
            L.i("childWidth:"+childWidth+"  rowWidth:"+rowWidth);
            if((rowWidth+ getPaddingLeft()+getPaddingRight()) >widthSize){
                rowWidth = childWidth;
                width = widthSize;//超过1行了  宽度始终为最大宽度
                row++;

            }else {
                rowHeight = Math.max(childHeight,rowHeight);
            }

        }

        height +=(rowHeight+verticalSpacing)*(row)+ getPaddingBottom()+getPaddingTop();
        width += getPaddingLeft()+getPaddingRight();

        height = (heightMode == MeasureSpec.EXACTLY) ? heightSize : height;
        width = (widthMode == MeasureSpec.EXACTLY) ? widthSize : width;
        L.i("TagGroupView  onMeasure width-height:" + width + ":"+height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        L.i("---------onSizeChanged--------------"+w+":"+h+":"+oldw+":"+oldh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //计算子view的位置，第一个子view的left和top和parent相同
        int parentWidth = r-l-getPaddingRight();//实际宽度

        int childLeft = getPaddingLeft();
        int childTop = getPaddingTop();

        final int count = getChildCount();

        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            if((childLeft + width) > parentWidth){
                //另起一行
                childLeft = getPaddingLeft();
                childTop += height + verticalSpacing;
            }
            L.i("TagGroupView  onLayout:"+childLeft+":"+childTop+":"+(childLeft+width)+":"+(childTop+height));
            child.layout(childLeft, childTop, childLeft + width, childTop + height);
            childLeft+=width+horizontalSpacing;
        }

    }

    public void addTag(String tag){
        TextView textView = new TextView(getContext());
        textView.setText(tag);
        textView.setTextColor(mTextColor);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize);
        textView.setBackgroundColor(mBackgroundColor);

        addView(textView);
        L.i("tag:"+tag+"->"+mTextSize);
//        invalidate();
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setmTitle(String exampleString) {
        mTitle = exampleString;
        
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mTextColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setmTextColor(int exampleColor) {
        mTextColor = exampleColor;
        
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mTextSize;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setTextSize(float exampleDimension) {
        mTextSize = exampleDimension;
        
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}
