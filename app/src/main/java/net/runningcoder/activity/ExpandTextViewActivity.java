package net.runningcoder.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.widget.AutoSizeTextView;

public class ExpandTextViewActivity extends BasicActivity {


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        String title = getIntent().getStringExtra("title");
//		setTitle(title);
//		setStatusBarColor(android.R.color.holo_blue_light);


        final TextView textView = findViewById(R.id.v_textview);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text = textView.getText().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(text + "+内容addcontent哈哈");
                    }
                });
            }
        });

        final AutoSizeTextView autoSizeTextView = findViewById(R.id.v_textview2);
        autoSizeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String text = autoSizeTextView.getText().toString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        autoSizeTextView.setText(text + "+内容addcontent哈哈");
                    }
                });
            }
        });
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_expand_textview;
    }
}
