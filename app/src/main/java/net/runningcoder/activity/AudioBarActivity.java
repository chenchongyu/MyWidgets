package net.runningcoder.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.bean.GroupItem;
import net.runningcoder.util.L;
import net.runningcoder.widget.AudioBar;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class AudioBarActivity extends BasicActivity implements View.OnClickListener {

    AudioBar mAudioBar;
    Button btnStart, btnStop;

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
        switch (v.getId()) {
            case R.id.btn_start:
                mAudioBar.start();
                break;
            case R.id.btn_stop:
                mAudioBar.stop();
                break;
        }

        testAnnotation();
    }

    private void testAnnotation() {
        Class<GroupItem> aClass = GroupItem.class;
        Field[] fields = aClass.getFields();
        List<String> errorFields = new ArrayList<>();
        for (Field field : fields) {
            //todo 调用每个object类的field
            if (notNullable(field)) {
                L.d(aClass.getName() + "->" + field.getName(), " have no Nullable ");
                errorFields.add(field.getName());
            }
        }
    }

    private boolean notNullable(Field field) {
        return !Modifier.isTransient(field.getModifiers())
                && !isNotObj(field.getType()) && null == field.getAnnotation(Nullable.class);
    }

    private boolean isNotObj(Class typeClz) {
        return typeClz.equals(Integer.class) || typeClz.equals(int.class)
                || typeClz.equals(Long.class) || typeClz.equals(long.class)
                || typeClz.equals(Float.class) || typeClz.equals(float.class)
                || typeClz.equals(Double.class) || typeClz.equals(double.class)
                || typeClz.equals(Boolean.class) || typeClz.equals(boolean.class);
    }

}
