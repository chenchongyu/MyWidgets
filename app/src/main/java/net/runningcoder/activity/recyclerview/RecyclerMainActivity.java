package net.runningcoder.activity.recyclerview;

import android.content.Intent;
import android.view.View;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;

/**
 * Author： chenchongyu
 * Date: 2019/2/26
 * Description:
 */
public class RecyclerMainActivity extends BasicActivity {

    @Override
    public int getContentViewID() {
        return R.layout.activity_recycler_main;
    }

    public void onDragClick(View view) {
        Intent intent = new Intent(this, RecyclerTestAct.class);
        intent.putExtra("type", 0);
        startActivity(intent);
    }

    public void onViewPagerClick(View view) {
        Intent intent = new Intent(this, RecyclerTestAct.class);
        intent.putExtra("type", 1);
        startActivity(intent);
    }

    public void onPinClick(View view){
        Intent intent = new Intent(this, RecyclerPinHeaderAct.class);
        startActivity(intent);
    }

    public void onAnimateClick(View view){
        Intent intent = new Intent(this, RecyclerAnimateAct.class);
        startActivity(intent);
    }

}
