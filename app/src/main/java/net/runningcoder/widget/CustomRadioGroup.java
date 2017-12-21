package net.runningcoder.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import net.runningcoder.R;
import net.runningcoder.bean.CardFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： chenchongyu
 * Date: 2017/9/27
 * Description:
 */

public class CustomRadioGroup extends RadioGroup {
    List<CardFilter> datas = new ArrayList<>(2);

    public CustomRadioGroup(Context context) {
        super(context);
        init();
    }

    public CustomRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrientation(HORIZONTAL);
//        setBackground(R.drawable.round_rect_blue_fill_bg);
    }

    public void setDatas(List<CardFilter> list){
        datas.clear();
        datas.addAll(list);

        for (CardFilter data : datas) {
            final RadioButton radioButton = getRadioButton(data);
            this.addView(radioButton);
            if (data.selelcted == 1) {
                setDrawableLeft(radioButton);

                this.check(radioButton.getId());

            }
        }

        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                setDrawableLeft((TextView) group.findViewById(checkedId));
                int count = group.getChildCount();
                for (int i=0;i<count;i++){
                    RadioButton button = (RadioButton) group.getChildAt(i);
                    if (button.getId() != checkedId){
                        button.setCompoundDrawables(null,null,null,null);
                    }
                }
            }
        });
    }

    @NonNull
    private RadioButton getRadioButton(CardFilter data) {
        RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.item_radio,this,false);
        radioButton.setText(data.message);
        return radioButton;
    }

    private void setDrawableLeft(TextView radioButton) {
        Drawable background = getResources().getDrawable(R.drawable.icon_collapse);
        background.setBounds(0, 0, 40, 40);
        radioButton.setCompoundDrawables(background, null, null, null);
        radioButton.setCompoundDrawablePadding(20);
    }
}
