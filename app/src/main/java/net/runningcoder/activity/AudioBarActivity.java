package net.runningcoder.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.widget.AudioBar;

public class AudioBarActivity extends BasicActivity implements View.OnClickListener{

	AudioBar mAudioBar;
	Button btnStart,btnStop;
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

		mAudioBar = $(R.id.v_circle);
		btnStart = $(R.id.btn_start);
		btnStop = $(R.id.btn_stop);

		btnStart.setOnClickListener(this);
		btnStop.setOnClickListener(this);
//		MyHandler handler = new MyHandler(this);
//		handler.sendEmptyMessageDelayed(1,3000);
	}

	@Override
	public int getContentViewID() {
		return R.layout.activity_audio_bar;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.btn_start:
				mAudioBar.start();
				break;
			case R.id.btn_stop:
				mAudioBar.stop();
				break;
		}
	}

	/*static class MyHandler extends Handler{
		WeakReference<AudioBarActivity> activityWeakReference;
		public MyHandler(AudioBarActivity activity) {
			activityWeakReference = new WeakReference(activity);
		}

		@Override
		public void handleMessage(Message msg) {
			AudioBarActivity activity = activityWeakReference.get();
			if(activity != null){
				super.handleMessage(msg);
				switch (msg.what){
					case 1:
						activity.circleProgress.setProgress(50);
						break;
				}
			}

		}
	}*/

}
