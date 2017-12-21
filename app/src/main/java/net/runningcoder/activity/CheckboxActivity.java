package net.runningcoder.activity;

import android.os.Bundle;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.bean.CardFilter;
import net.runningcoder.widget.CustomRadioGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： chenchongyu
 * Email: chenchongyu@didichuxing.com
 * Date: 2017/9/27
 * Description:
 */

public class CheckboxActivity extends BasicActivity {

    CustomRadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        radioGroup = $(R.id.radio_group2);
        List<CardFilter> list = new ArrayList<>();
        CardFilter cardFilter = new CardFilter();
        cardFilter.message="全部乘客";
        cardFilter.selelcted = 0;
        list.add(cardFilter);

        CardFilter cardFilter2 = new CardFilter();
        cardFilter2.message="100%顺路乘客";
        cardFilter2.selelcted = 1;
        list.add(cardFilter2);

        radioGroup.setDatas(list);

        /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
               RadioButton radioButton = (RadioButton) radioGroup.findViewById(checkedId);
                radioButton.setButtonDrawable(R.drawable.icon_collapse);
            }
        });*/
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_checkbox;
    }
}
