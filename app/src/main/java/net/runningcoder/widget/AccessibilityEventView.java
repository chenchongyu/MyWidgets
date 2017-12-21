package net.runningcoder.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

/**
 * Author： chenchongyu
 * Date: 2017/12/4
 * Description:
 */

public class AccessibilityEventView extends View {
    Paint paint;
    boolean isChecked;
    private String s = "无障碍文字测试";

    public AccessibilityEventView(Context context) {
        this(context, null);
    }

    public AccessibilityEventView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AccessibilityEventView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setAccessibility();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLUE);
        paint.setTextSize(80);
    }

    private void setAccessibility() {
        setFocusableInTouchMode(true);//无障碍模式要开启focusable，如何要求不能聚焦，就设置setFocusableInTouchMode
        ViewCompat.setAccessibilityDelegate(this, new MyAccessibilityDelegate());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(s, 80, 80, paint);
    }

    public void setText(String s) {
        this.s = s;
        invalidate();
        sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED);
    }

    //如果设置了 contentDescription，这里的内容会被覆盖
    private class MyAccessibilityDelegate extends AccessibilityDelegateCompat {

        /**
         * 该方法为视图的 AccessibilityEvent 设置朗读文本提示
         * <p>
         * api14以下版本使用dispatchPopulateAccessibilityEvent()方法
         *
         * @param host
         * @param event
         */
        @Override
        public void onPopulateAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onPopulateAccessibilityEvent(host, event);
            String text = event.isChecked() ? "已选中" : "未选中";

            event.getText().add(s + text);
        }


        @Override
        public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
            super.onInitializeAccessibilityNodeInfo(host, info);
            info.setCheckable(true);
            info.setChecked(isChecked());

        }


        /**
         * 系统调用此方法来获取超出文本内容的视图状态的附加信息。
         * 如果自定义视图提供简单 TextView 或 Button 以外的交互控制,开发者应该重写该方法，
         * 并且使用该方法设置该视图的额外信息到事件中,如密码区域类型,复选 框类型或提供用户交互或反馈的状态。
         * 如果重写这个方法,开发者必须调用它父 类的实现方法，然后只修改那些父类中尚未设置的属性。
         *
         * @param host
         */
        @Override
        public void onInitializeAccessibilityEvent(View host, AccessibilityEvent event) {
            super.onInitializeAccessibilityEvent(host, event);
            event.setChecked(isChecked());
        }


        @Override
        public void sendAccessibilityEvent(View host, int eventType) {
//            if (eventType == AccessibilityEvent.TYPE_VIEW_HOVER_ENTER) {
//                isChecked = !isChecked;
//                super.sendAccessibilityEvent(host, AccessibilityEvent.TYPE_VIEW_SELECTED);
//            }

            super.sendAccessibilityEvent(host, eventType);
        }
    }

    public boolean isChecked() {
        isChecked = !isChecked;
        return isChecked;
    }
}
