package net.runningcoder.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.widget.CircleProgress;

import java.lang.ref.WeakReference;

public class CircleProgressViewActivity extends BasicActivity{

	CircleProgress circleProgress;
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

		circleProgress = $(R.id.v_circle);
		circleProgress.setIsLoading(true);

		MyHandler handler = new MyHandler(this);
		handler.sendEmptyMessageDelayed(1,3000);
	}

	@Override
	public int getContentViewID() {
		return R.layout.activity_circle_progress;
	}

	static class MyHandler extends Handler{
		WeakReference<CircleProgressViewActivity> activityWeakReference;
		public MyHandler(CircleProgressViewActivity activity) {
			activityWeakReference = new WeakReference(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			CircleProgressViewActivity activity = activityWeakReference.get();
			if(activity != null){
				super.handleMessage(msg);
				switch (msg.what){
					case 1:
						activity.circleProgress.setProgress(50);
						break;
				}
			}

		}
	}

}
