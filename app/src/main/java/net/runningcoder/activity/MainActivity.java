package net.runningcoder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.adapter.MainAdapter;
import net.runningcoder.bean.WidgetItem;
import net.runningcoder.listener.OnItemClickForRecycler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BasicActivity implements OnItemClickForRecycler{

    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private List<WidgetItem> list = new ArrayList<WidgetItem>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {
        list.add(new WidgetItem(1,"ExpanableTextView","可展开收起的TextView"));
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        setTitle(R.string.title_activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.v_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MainAdapter(list,this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_main;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public void onItemClick(View v, int position) {
        WidgetItem item = list.get(position);
        if(item.id == 1){
            Intent intent = new Intent(this, ExpandTextViewActivity.class);
            intent.putExtra("title",item.name);
            startActivity(intent);
        }
    }
}
