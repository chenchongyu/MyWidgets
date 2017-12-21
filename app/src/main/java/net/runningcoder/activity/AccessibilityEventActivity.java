package net.runningcoder.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.View;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.util.L;

import java.util.Map;
import java.util.Set;

/**
 * Author： chenchongyu
 * Date: 2017/12/4
 * Description:
 */

public class AccessibilityEventActivity extends BasicActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

    }

    public void onClick(View v) {
//        ((AccessibilityEventView)$(R.id.v_view)).setText("修改后的内容--");

        SharedPreferences sharedPreferences = getSharedPreferences("test", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("aaa", "aaaa");
        editor.putString("bbb", "bbb");
        editor.putString("ccc", "ccc");
        editor.commit();

        Map<String, ?> allKeys = sharedPreferences.getAll();
        L.i(allKeys.keySet() + "");
        Set<? extends Map.Entry<String, ?>> entries = allKeys.entrySet();
        for (Map.Entry<String, ?> entry : entries) {
            if (entry.getKey().equals("aaa") || entry.getKey().equals("bbb")) {
                editor.remove(entry.getKey());
            }
        }
        editor.apply();
        L.i("删除后：" + sharedPreferences.getAll().keySet());
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_accessibility_test;
    }
}
