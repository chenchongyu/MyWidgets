package net.runningcoder.widget.tips;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author： chenchongyu
 * Email: chenchongyu@didichuxing.com
 * Date: 2017/5/17
 * Description:
 */

public class TipsTextView extends TextView {

    private static final String TAG = "TextViewHeightPlus";
    private int actualHeight=0;


    public int getActualHeight() {
        return actualHeight;
    }

    public TipsTextView(Context context) {
        super(context);
    }

    public TipsTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TipsTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        actualHeight=0;
        actualHeight=getLineCount()*getLineHeight();
    }
}
