package net.runningcoder.activity;

import android.os.Bundle;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;

public class ExpandTextViewActivity extends BasicActivity {


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		String title = getIntent().getStringExtra("title");
		setTitle(title);
		setStatusBarColor(android.R.color.holo_blue_light);
	}

	@Override
	public int getContentViewID() {
		return R.layout.activity_expand_textview;
	}
}
