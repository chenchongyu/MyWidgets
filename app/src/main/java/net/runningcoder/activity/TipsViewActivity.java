package net.runningcoder.activity;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.DynamicDrawableSpan;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.widget.tips.TipsView;
import net.runningcoder.widget.tips.TriangleView;

public class TipsViewActivity extends BasicActivity implements View.OnClickListener {

    TextView btn1, btn2;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        String title = getIntent().getStringExtra("title");
        setTitle(title);
        setActionBarColor(android.R.color.holo_orange_dark);
        setStatusBarColor(android.R.color.holo_orange_light);

        btn1 = $(R.id.v_btn);
        btn2 = $(R.id.v_btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        $(R.id.v_btn3).setOnClickListener(this);
        $(R.id.v_btn4).setOnClickListener(this);

        final TextView textView = findViewById(R.id.v_btn);

        String from = "北京市昌平区回龙观新龙城25 ";
        int pos = from.length();
        //预留一个空格位
        SpannableStringBuilder builder = new SpannableStringBuilder(from+" ");
        DynamicDrawableSpan drawableSpan2 = new DynamicDrawableSpan(
                DynamicDrawableSpan.ALIGN_BASELINE) {
            @Override
            public Drawable getDrawable() {
                Drawable d = getResources().getDrawable(R.drawable.bts_list_right_arrow);
                d.setBounds(0, 0, 50, 50);
                return d;
            }
        };

        builder.setSpan(drawableSpan2, pos, pos + 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        builder.append(" 北京是昌平区回龙观流星花园");
        textView.setText(builder);

    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_tips_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.v_btn:
                showUpTips(v);
                break;
            case R.id.v_btn2:
                showDownTips(v);
                break;
            case R.id.v_btn3:
                showLeftTips(v);
                break;
            case R.id.v_btn4:
                showRightTips(v);
                break;
        }
    }

    private void showUpTips(View v) {
        new TipsView.Builder()
                .with(this)
                .color(android.R.color.holo_green_light)
                .setTitleColor(android.R.color.holo_red_light)
                .setContentColor(android.R.color.holo_purple)
                .borderColor(android.R.color.holo_red_dark)
                .borderWidth(2f)
                .radius(5)
                .title("箭头向上 build")
                .icon(android.R.drawable.stat_notify_error)
                .position(TriangleView.PosStrategy.ANCHOR_CENTER)
                .width(WindowManager.LayoutParams.MATCH_PARENT)
                .rate(0.9)
                .content(((TextView) v).getText().toString())
                .direction(TriangleView.Direction.UP)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);

        new TipsView.Builder()
                .with(this)
//				.position(TriangleView.PosStrategy.ANCHOR_CENTER)
//				.width(WindowManager.LayoutParams.MATCH_PARENT)
                .borderColor(android.R.color.holo_red_dark)
                .borderWidth(2f)
                .radius(5)
                .icon(android.R.drawable.ic_dialog_info)
                .color(R.color.default_reached_color)
                .title("箭头向下 build")
                .content("测试内容，，，，，")
                .direction(TriangleView.Direction.DOWN)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);
    }

    private void showDownTips(View v) {
        new TipsView.Builder()
                .with(this)
//				.position(TriangleView.PosStrategy.ANCHOR_CENTER)
//				.width(WindowManager.LayoutParams.MATCH_PARENT)
                .borderColor(android.R.color.holo_red_dark)
                .borderWidth(2f)
                .radius(5)
                .icon(android.R.drawable.ic_dialog_info)
                .color(R.color.default_reached_color)
                .title("箭头向下 build")
                .content(((TextView) v).getText().toString())
                .direction(TriangleView.Direction.DOWN)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);
    }

    private void showLeftTips(View v) {
        new TipsView.Builder()
                .with(this)
                .borderColor(android.R.color.holo_red_dark)
                .borderWidth(2f)
                .radius(5)
                .color(android.R.color.holo_blue_light)
                .title("箭头向左 build")
//				.width(500)
                .content(((TextView) v).getText().toString())
                .direction(TriangleView.Direction.LEFT)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);
    }

    private void showRightTips(View v) {
        new TipsView.Builder()
                .with(this)
                .borderColor(android.R.color.holo_red_dark)
                .borderWidth(2f)
                .radius(5)
                .color(R.color.default_reached_color)
                .title("箭头向右 build")
                .content(((TextView) v).getText().toString())
                .direction(TriangleView.Direction.RIGHT)
                .setOnClickListener(new TipsView.OnTipClickListener() {
                    @Override
                    public void onClick(TipsView view) {
                        view.dismiss();
                    }
                })
                .build()
                .show(v);
    }
}
