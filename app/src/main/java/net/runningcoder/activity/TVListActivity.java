package net.runningcoder.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.adapter.TVListAdapter;
import net.runningcoder.listener.OnItemClickForRecycler;

import java.util.ArrayList;
import java.util.List;

public class TVListActivity extends BasicActivity {

    private RecyclerView recyclerView;
    private TVListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<String> list = new ArrayList<>(5);
        list.add("在很多时候xml里面的布局并不能满足我们的需求。这时候就需要用代码进行动态布局，前些天在对RelativeLayout 进行动态布局时遇到了些问题，现在解决了，分享下。");
        list.add("人生自古谁无死，留取丹心照汗青");
        list.add("我们经常会遇到让控件或是view实现叠加的效果,一般...不如RelativeLayout。");
        list.add("private RecyclerView recyclerView;在很多时候xml里面的布局并不能满足我们的需求。这时候就需要用代码进行动态布局，前些天在对RelativeLayout 进行动态布局时遇到了些问题，现在解决了，分享下。");

        recyclerView = (RecyclerView) findViewById(R.id.v_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new TVListAdapter(list, new OnItemClickForRecycler() {
            @Override
            public void onItemClick(View v, int position) {
//                L.i(list.get(position));
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_tvlist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tv_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
