package net.runningcoder.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Author： chenchongyu
 * Date: 2018/12/2
 * Description:
 */
public class AutoSizeTextView extends TextView {
    float currentSize;

    public AutoSizeTextView(Context context) {
        super(context);
    }

    public AutoSizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        currentSize = getTextSize();
        resizeText(text);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        resizeText(text);
    }

    private void resizeText(CharSequence text) {
        int availableWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
        int textWidth = (int) getTextWidth(text);
        while (availableWidth > 0 && currentSize > 0 && textWidth > availableWidth) {
            currentSize -= 1;
            setTextSize(currentSize);
            textWidth = (int) getTextWidth(text);
        }
    }

    private float getTextWidth(CharSequence text) {
        return getPaint().measureText(text.toString());
    }
}
