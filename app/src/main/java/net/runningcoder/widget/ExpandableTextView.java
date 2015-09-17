package net.runningcoder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.runningcoder.R;
import net.runningcoder.util.L;

/**
 * TODO: document your custom view class.
 */
public class ExpandableTextView extends LinearLayout implements View.OnClickListener {
    private final int MODEL_EXPAND = 1;
    private final int MODEL_COLLAPSE = 0;


    private String text; // TODO: use a default from R.string...
    private int mTextColor = Color.RED; // TODO: use a default from R.color...
    private float mTextSize = 0; // TODO: use a default from R.dimen...
    private int lines = 3;
    private int mCurrentModel = MODEL_EXPAND;
    private int mStartHeight;
    private int mEndHeight;
    private Drawable mExpandIcon;
    private Drawable mCollapseIcon;
    private TextPaint mTextPaint;
    private TextView tv;
    private ImageView img;
    ExpandCollapseAnimation animation = null;

    public ExpandableTextView(Context context) {
        super(context);
        init(null, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ExpandableTextView, defStyle, 0);

        text = a.getString(
                R.styleable.ExpandableTextView_text);
        lines = a.getInteger(R.styleable.ExpandableTextView_lines,3);
        mTextColor = a.getColor(
                R.styleable.ExpandableTextView_textColor,
                mTextColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mTextSize = a.getDimension(
                R.styleable.ExpandableTextView_textSize,
                mTextSize);

        if (a.hasValue(R.styleable.ExpandableTextView_expandDrawable)) {
            mExpandIcon = a.getDrawable(
                    R.styleable.ExpandableTextView_expandDrawable);
            mExpandIcon.setCallback(this);
        }
        if (a.hasValue(R.styleable.ExpandableTextView_collapseDrawable)) {
            mCollapseIcon = a.getDrawable(
                    R.styleable.ExpandableTextView_collapseDrawable);
            mCollapseIcon.setCallback(this);
        }

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        setOrientation(LinearLayout.VERTICAL);
        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initViews();
    }

    private void initViews() {
        tv = new TextView(getContext());
        tv.setText(text);
        tv.setLines(lines);
        this.addView(tv);
        L.i("getLineCount ->" + tv.getLineCount());

        img = new ImageView(getContext());
        if(mCurrentModel == MODEL_EXPAND){
            img.setImageDrawable(mExpandIcon);
        }else {
            img.setImageDrawable(mCollapseIcon);
        }

        img.setOnClickListener(this);
        addView(img);

    }


    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//        mTextHeight = fontMetrics.bottom;
    }

    @Override
    public void onClick(View v) {

        if(mCurrentModel == MODEL_EXPAND){
            animation = new ExpandCollapseAnimation(tv,MODEL_EXPAND);
            img.setImageDrawable(mCollapseIcon);
            mCurrentModel = MODEL_COLLAPSE;
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });

        }else {
            animation = new ExpandCollapseAnimation(tv,MODEL_COLLAPSE);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mCurrentModel = MODEL_EXPAND;
                    img.setImageDrawable(mExpandIcon);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            tv.setLines(lines);
        }
        tv.startAnimation(animation);
        tv.requestLayout();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(mCurrentModel == MODEL_COLLAPSE){
        }else {
//            mStartHeight =  tv.getCompoundPaddingBottom() + tv.getCompoundPaddingTop()
//                    + tv.getLineHeight()*lines;
//            int height = tv.getHeight();
            mStartHeight = tv.getHeight();
            mEndHeight = tv.getLayout().getLineTop(tv.getLineCount())+tv.getCompoundPaddingBottom()
                    +tv.getCompoundPaddingTop();
        }
        L.i("mCurrentModel:"+mCurrentModel+" onMeasure  mStartHeight:"+mStartHeight);

    }



    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setText(String exampleString) {
        text = exampleString;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setTextColor(int exampleColor) {
        mTextColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setTextSize(float exampleDimension) {
        mTextSize = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }


    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setmExpandIcon(Drawable exampleDrawable) {
        mExpandIcon = exampleDrawable;
    }

    public class ExpandCollapseAnimation extends Animation {
        private TextView mAnimatedView;

        private int mType;
        private LinearLayout.LayoutParams mLayoutParams;

        /**
         * Initializes expand collapse animation, has two types, collapse (1) and expand (0).
         * @param view The view to animate
         * @param type The type of animation: 0 will expand from gone and 0 size to visible and layout size defined in xml.
         * 1 will collapse view and set to gone
         */
        public ExpandCollapseAnimation(TextView view, int type) {

            mAnimatedView = view;
//            mEndHeight = tv.getLayout().getLineTop(tv.getLineCount())+tv.getCompoundPaddingBottom()
//                    +tv.getCompoundPaddingTop();//mAnimatedView.getMeasuredHeight();
//            mStartHeight = mAnimatedView.getLineHeight()*lines+tv.getCompoundPaddingBottom()
//                    +tv.getCompoundPaddingTop();

            mLayoutParams = ((LinearLayout.LayoutParams) view.getLayoutParams());
            mType = type;

            view.setVisibility(View.VISIBLE);
            setDuration(300);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            L.d("mStartHeight :"+mStartHeight+"  mEndHeight :"+mEndHeight);
            super.applyTransformation(interpolatedTime, t);
            L.d("interpolatedTime->" + interpolatedTime+" mType:"+mType);
            if(mType == MODEL_EXPAND) {
                mLayoutParams.height = (int) (mStartHeight+(mEndHeight-mStartHeight)*interpolatedTime);
            } else {
                mLayoutParams.height = (int) (mEndHeight-(mEndHeight-mStartHeight)*interpolatedTime);
            }
            L.d("anim height " + mLayoutParams.height);
            mAnimatedView.requestLayout();
        }
    }

}
