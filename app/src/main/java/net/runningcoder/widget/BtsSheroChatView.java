package net.runningcoder.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.runningcoder.R;
import net.runningcoder.util.ViewUtils;

/**
 * Author： chenchongyu
 * Email: chenchongyu@didichuxing.com
 * Date: 2019-12-10
 * Description:
 */
public class BtsSheroChatView extends FrameLayout {

    private TextView textView1, textView2;
    //    private ValueAnimator exitAnim;
    private Animation enterAnim, exitAnim;

    public BtsSheroChatView(@NonNull Context context) {
        this(context, null);
    }

    public BtsSheroChatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BtsSheroChatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        textView1 = generateTextView();
        textView1.setId(android.R.id.text1);
        textView1.setVisibility(GONE);

        textView2 = generateTextView();
        textView2.setId(android.R.id.text2);
//        textView2.setVisibility(GONE);
        textView2.setAlpha(0.0f);
        addView(textView1);
        addView(textView2);

        exitAnim = new AlphaAnimation(1.0f, 0.0f);
        exitAnim.setDuration(400);
        exitAnim.setFillAfter(false);
        exitAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView2.setAlpha(0.0f);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        enterAnim = AnimationUtils.loadAnimation(getContext(), R.anim.bts_safe_scale_in);

    }

    public void setBackground(int res) {
        textView1.setBackgroundResource(res);
        textView2.setBackgroundResource(res);
    }

    /**
     * 设置文字，启动动画
     * 始终给textView1做进场动画，动画结束后隐藏textView1，把text设置到textView2并显示；
     * 切换时textView2设置退场动画，同时textView1进场；
     *
     * @param text
     */
    public void setText(final String text) {
        if (!TextUtils.isEmpty(textView2.getText())) {
            textView2.startAnimation(exitAnim);
//            textView2.setVisibility(GONE);
        } else {
            textView2.setAlpha(0.0f);
        }

        textView1.setText(text);
        textView1.setVisibility(VISIBLE);
        textView1.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        enterAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView1.setVisibility(GONE);
                textView1.setLayerType(View.LAYER_TYPE_NONE, null);

                //解决白底闪现问题
                textView1.post(new Runnable() {
                    @Override
                    public void run() {
                        textView2.setText(text);
                        textView2.setAlpha(1);
//                        textView2.setVisibility(VISIBLE);
                    }
                });

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        textView1.startAnimation(enterAnim);

    }

    protected void setWH(float animatedValue) {
        ViewGroup.LayoutParams layoutParams = textView1.getLayoutParams();
        layoutParams.width = (int) (getMeasuredWidth() * animatedValue);
        layoutParams.height = (int) (getMeasuredHeight() * animatedValue);
        textView1.setLayoutParams(layoutParams);
    }

    private TextView generateTextView() {
        TextView textView = new TextView(getContext());
        textView.setTextColor(getResources().getColor(android.R.color.white));
        textView.setTextSize(14);
        textView.setPadding(ViewUtils.dp2px(getContext(), 18f), 0, ViewUtils.dp2px(getContext(), 25), 0);
        textView.setGravity(Gravity.CENTER_VERTICAL);
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
        textView.setLayoutParams(params);
        return textView;
    }
}
