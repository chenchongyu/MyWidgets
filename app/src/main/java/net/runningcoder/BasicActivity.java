package net.runningcoder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;


public abstract class BasicActivity extends ActionBarActivity {
	public static final String ACTION_EXIT = RunningCodeApplication.getInstance().getPackageName() + ".EXIT";
	public TextView baseTitle;//返回
	public LinearLayout baseHome;
	public TextView baseSubject;
	public TextView baseActionBtn;//actionbar右上角按按钮
	protected Toolbar toolbar;

	private SystemBarTintManager tintManager;

	protected void onCreate(Bundle arg0) {
		
		super.onCreate(arg0);
		super.setContentView(R.layout.layout_base);
		setContentView(getContentViewID());
		if(showActionbar())
			baseInitActionBar();

	    IntentFilter filter = new IntentFilter();
	    filter.addAction(ACTION_EXIT);
	    registerReceiver(mExitReceiver, filter);
	    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}

	@Override
	public void setContentView(int layoutResID) {
		setContentView(View.inflate(this, layoutResID, null));
	}

	@Override
	public void setContentView(View view) {
		LinearLayout root = (LinearLayout) findViewById(R.id.v_root);
		if(root == null) return;
		root.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}

	/***
	 * 不想显示actionbar的activity需要重写此方法
	 * 返回false即可
	 * @return
	 */
	protected boolean showActionbar() {
		return true;
	}
	
	public void setTitle(int title){
		this.toolbar.setTitle(title);
	}
	public void setTitle(String title){
		this.toolbar.setTitle(title);
	}

	public abstract int getContentViewID();

	public void baseInitActionBar() {
		toolbar = (Toolbar) findViewById(R.id.toolbar);
		if (toolbar != null) {
			setSupportActionBar(toolbar);
			tintManager=new SystemBarTintManager(this);
			setStatusBarColor(R.color.colorPrimaryDark);
			tintManager.setStatusBarTintEnabled(true);
		}

	}

	protected void setActionBarColor(int color){
//		toolbar.setBackgroundColor(color);
		toolbar.setBackgroundResource(color);
	}
	protected void setStatusBarColor(int color){
		tintManager.setStatusBarTintResource(color);
	}
	
	protected void setActionImage(int drawable,boolean hideText) {
		Drawable background = getResources().getDrawable(drawable);
		background.setBounds(0, 0, background.getMinimumWidth(), background.getMinimumHeight());
		baseActionBtn.setCompoundDrawables(background, null, null, null);
		baseActionBtn.setVisibility(View.VISIBLE);
		if(hideText)
			baseActionBtn.setText("");
	}
	
	public void setDrawbleTopImage(TextView view,int drawable) {
		setDrawbleTopImage(view, drawable, Color.GRAY);
	}
	
	public void setDrawbleTopImage(TextView view,int drawable,int color) {
		Drawable background = getResources().getDrawable(drawable);
		background.setBounds(0, 0, background.getMinimumWidth(), background.getMinimumHeight());
		view.setCompoundDrawables(null, background, null, null);
		view.setVisibility(View.VISIBLE);
		view.setTextColor(color);
	}


    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	unregisterReceiver(mExitReceiver);
    }
 

	private BroadcastReceiver mExitReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
		  finish();
		}
	};
	
  public void appExit() {
	  Intent intent = new Intent();
	  intent.setAction(ACTION_EXIT);
	  sendBroadcast(intent);
  }
}
