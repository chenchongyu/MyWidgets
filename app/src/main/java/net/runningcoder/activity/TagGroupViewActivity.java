package net.runningcoder.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.util.L;
import net.runningcoder.widget.TagGroupView;

public class TagGroupViewActivity extends BasicActivity implements View.OnClickListener{
	private final static String[] DATAS = {"PHP","Ruby","C","攻城狮","C++","程序猿","设计湿","产品狗","JSP","Android","Java","iOS","Javascript","HTML","Oracle","MySql","Redis","Moungodb","Jquery","bootstrap","我们都是IT人"};
	TagGroupView tagGroup;
	Button btn;
	int index = 0;
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

		tagGroup = (TagGroupView) findViewById(R.id.v_tagview);
		btn = (Button) findViewById(R.id.v_btn);
		btn.setOnClickListener(this);

	}

	@Override
	public int getContentViewID() {
		return R.layout.activity_taggroup;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.v_btn:
				addTag();
				break;
			default:break;
		}}

	private void addTag() {
		L.i(index+"/"+DATAS.length);
		if(index == DATAS.length)
			index = 0;
		tagGroup.addTag(DATAS[index]);
		index++;
	}
}
