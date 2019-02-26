package net.runningcoder.activity;

import android.os.Bundle;
import android.os.Handler;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.widget.TranslateTextView;

/**
 * Author： chenchongyu
 * Date: 2018/1/15
 * Description:
 */

public class TranslateTextViewActivity extends BasicActivity {
    TranslateTextView translateTextView;
    int i = 0;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);

        translateTextView = $(R.id.translate_view);
        translateTextView.setText("更换内容："+i);
        startLoop();
    }

    private void startLoop() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                translateTextView.setText("更换内容："+i);
                i++;
                startLoop();
            }
        },1500);
    }


    @Override
    public int getContentViewID() {
        return R.layout.activity_translate_text_view;
    }
}
