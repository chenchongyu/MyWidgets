package net.runningcoder.activity;

import android.os.Bundle;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.widget.ScratchCardView;

public class ScratchCardActivity extends BasicActivity {

	ScratchCardView mScratchCardView;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		initView();
	}

	private void initView() {
		String title = getIntent().getStringExtra("title");
		setTitle(title);
		setActionBarColor(android.R.color.holo_orange_dark);
		setStatusBarColor(android.R.color.holo_orange_light);

		mScratchCardView = $(R.id.v_circle);


	}

	@Override
	public int getContentViewID() {
		return R.layout.activity_scratch;
	}


}
