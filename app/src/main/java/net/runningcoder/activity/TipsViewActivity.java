package net.runningcoder.activity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.widget.tips.TipsView;
import net.runningcoder.widget.tips.TriangleView;

public class TipsViewActivity extends BasicActivity implements View.OnClickListener{

	TextView btn1,btn2;
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

		btn1 = $(R.id.v_btn);
		btn2 = $(R.id.v_btn2);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		$(R.id.v_btn3).setOnClickListener(this);
		$(R.id.v_btn4).setOnClickListener(this);


	}

	@Override
	public int getContentViewID() {
		return R.layout.activity_tips_view;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()){
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

	private void showDownTips(View v) {
		new TipsView.Builder()
				.with(this)
				.color(R.color.default_reached_color)
				.title("箭头向下 build")
				.content(((TextView)v).getText().toString())
				.direction(TriangleView.Direction.DOWN)
				.build()
				.show(v);
	}

	private void showUpTips(View v) {
		new TipsView.Builder()
				.with(this)
				.color(android.R.color.holo_green_light)
				.title("箭头向上 build")
				.position(TriangleView.PosStrategy.ANCHOR_CENTER)
				.width(WindowManager.LayoutParams.MATCH_PARENT)
				.content(((TextView)v).getText().toString())
				.direction(TriangleView.Direction.UP)
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
				.color(android.R.color.holo_blue_light)
				.title("箭头向左 build")
				.width(500)
				.content(((TextView)v).getText().toString())
				.direction(TriangleView.Direction.LEFT)
				.build()
				.show(v);
	}

	private void showRightTips(View v) {
		new TipsView.Builder()
				.with(this)
				.color(R.color.default_reached_color)
				.title("箭头向右 build")
				.content(((TextView)v).getText().toString())
				.direction(TriangleView.Direction.RIGHT)
				.build()
				.show(v);
	}
}
