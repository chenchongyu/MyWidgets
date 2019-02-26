package net.runningcoder.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.runningcoder.R;

/**
 * Author： chenchongyu
 * Date: 2018/1/15
 * Description: 文字内容切换的时候，有切换动画的"textview"
 */

public class TranslateTextView extends FrameLayout {
    private String currentContent;
    private TextView textView1;
    private TextView textView2;
    private Animation animFirst;
    private Animation animSecond;

    public TranslateTextView(@NonNull Context context) {
        this(context, null);
    }

    public TranslateTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TranslateTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        textView1 = getTextView();
        textView2 = getTextView();

        //先add textview2，后add 1，让1在上面
        addView(textView2);
        addView(textView1);

        animFirst = AnimationUtils.loadAnimation(getContext(), R.anim.bts_raw_home_dynamic_first);
        animSecond = AnimationUtils.loadAnimation(getContext(), R.anim.bts_raw_home_dynamic_second);
        animSecond.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView1.setText(currentContent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @NonNull
    private TextView getTextView() {
        TextView textView = new TextView(getContext());
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(18);
        return textView;
    }

    public void setText(String text) {
        currentContent = text;
        if (TextUtils.isEmpty(currentContent)) {
            textView1.setText(text);
        } else {
            textView2.setText(text);
            textView1.startAnimation(animFirst);
            textView2.startAnimation(animSecond);

        }
    }

}
