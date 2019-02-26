package net.runningcoder.activity;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;

/**
 * Author： chenchongyu
 * Date: 2017/8/11
 * Description:
 */

public class PopWindowActivity extends BasicActivity implements View.OnClickListener {

    Button btn, text;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        text = $(R.id.v_textview);

        $(R.id.first).setOnClickListener(this);
        $(R.id.second).setOnClickListener(this);
        $(R.id.third).setOnClickListener(this);
        $(R.id.v_dropdown_1).setOnClickListener(this);
        $(R.id.v_dropdown_2).setOnClickListener(this);
        Log.i("version", Build.VERSION.SDK_INT + "");
    }

    private void popSearchWindow() {
        PopupWindow popupWindow = getPopupWindow(LayoutInflater.from(this).inflate(R.layout.popwindowlayout_full, null));

        popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.NO_GRAVITY, 0, 0);

    }

    /**
     * 显示popupWindow
     */
    private void showPopwindow() {
        // 利用layoutInflater获得View
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popwindowlayout, null);

        // 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

        PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);

        view.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int height = view.getMeasuredHeight();
        window.setOutsideTouchable(true);
        //如果设置了touable为false，那么popwindow不能响应事件
        window.setTouchable(false);

        //api:21 设置是true并且必须设置背景,可响应返回键，外部可点击，后面button不会响应到事件
//        window.setFocusable(true);


        // 实例化一个ColorDrawable颜色为半透明
        //api 23以上不用设置背景
        if (Build.VERSION.SDK_INT < 23) {
            ColorDrawable dw = new ColorDrawable(0xb0000000);
            window.setBackgroundDrawable(dw);
        }

        int[] location = new int[2];
        text.getLocationOnScreen(location);
        // 设置popWindow的显示和消失动画
        window.setAnimationStyle(R.style.AnimationUpPopup);

        view.findViewById(R.id.first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopWindowActivity.this, "点击了按钮", Toast.LENGTH_SHORT).show();
            }
        });

        // 在底部显示
        window.showAtLocation(findViewById(R.id.content2), Gravity.NO_GRAVITY, location[0], location[1] - height);


        //popWindow消失监听方法
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                System.out.println("popWindow消失");
            }
        });


    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_popwindow;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                showPopwindow();
                break;
            case R.id.second:
                Log.i("onclick", "Button2 接收到了事件！");
                break;
            case R.id.third:
                popSearchWindow();
                break;
            case R.id.v_dropdown_1:
                showDropDown();
                break;
            case R.id.v_dropdown_2:
                showDropDownNoSpace();
                break;

        }
    }

    private void showDropDownNoSpace() {
        getPopupWindow().showAsDropDown($(R.id.v_dropdown_1));
    }

    private void showDropDown() {
        getPopupWindow().showAsDropDown($(R.id.first));

    }

    private PopupWindow getPopupWindow() {
        return getPopupWindow(LayoutInflater.from(this).inflate(R.layout.popwindowlayout, null));
    }

    @NonNull
    private PopupWindow getPopupWindow(View inflate) {
        PopupWindow popupWindow = new PopupWindow(inflate,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, false);

        popupWindow.setFocusable(true);
        //api 23以上不用设置背景
//        if (Build.VERSION.SDK_INT < 23) {
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        popupWindow.setBackgroundDrawable(dw);
//        }
        return popupWindow;
    }
}
