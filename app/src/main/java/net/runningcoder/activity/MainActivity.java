package net.runningcoder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.adapter.MainAdapter;
import net.runningcoder.bean.WidgetItem;
import net.runningcoder.listener.OnItemClickForRecycler;
import net.runningcoder.widget.AlphaButton;
import net.runningcoder.widget.ListViewItemProgress;
import net.runningcoder.widget.PopupMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BasicActivity implements OnItemClickForRecycler{

    private RecyclerView recyclerView;
    private MainAdapter adapter;
    private ListViewItemProgress progressBar;
    private List<WidgetItem> list = new ArrayList<WidgetItem>();
    private int progree = 0;
    private PopupMenu menu;
    AlphaButton vBtn;
    int i = 0;
    boolean isAdd = true;

    private final static WidgetItem[] WIDGETS = new WidgetItem[]{
        new WidgetItem(1,"ExpanableTextView","可展开收起的TextView"),
        new WidgetItem(2,"ExpanableTextView In ListView","可展开收起的TextView"),
        new WidgetItem(3,"标签效果","标签效果"),
        new WidgetItem(4,"圆形进度条","圆形进度条")
    };

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressBar.setProgress(progree);
            progree++;
            if(progree <=100)
                handler.sendMessageDelayed(new Message(),100);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
    }

    private void initData() {

        handler.sendEmptyMessage(0);

        menu = new PopupMenu(this);
        menu.add(1,"消息").setIcon(getResources().getDrawable(android.R.drawable.ic_dialog_info));
        menu.add(2,"设置").setIcon(getResources().getDrawable(R.drawable.image_menu_option_setting));
        menu.add(3,"更多").setIcon(getResources().getDrawable(android.R.drawable.ic_menu_more));

        menu.setOnItemSelectedListener(new PopupMenu.OnItemSelectedListener() {
            @Override
            public void onItemSelected(net.runningcoder.widget.MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        Toast.makeText(MainActivity.this,"消息",Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity.this,"更多",Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
    }

    private void initView() {
        setTitle(R.string.title_activity_main);
        vBtn = $(R.id.v_btn);
        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (i == WIDGETS.length){
                    isAdd = false;
                }
                if (i == 0)
                    isAdd = true;
                if (isAdd){
                    list.add(WIDGETS[i]);
                    adapter.notifyItemInserted(i);
                    i++;
                }else {
                    adapter.notifyItemRemoved(list.size() - 1);
                    list.remove(list.size()-1);
                    i-- ;
                }


            }
        });

        progressBar = (ListViewItemProgress) findViewById(R.id.v_progress);
        recyclerView = (RecyclerView) findViewById(R.id.v_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new MainAdapter(list,this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
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
            menu.show(findViewById(R.id.action_settings));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onItemClick(View v, int position) {
        WidgetItem item = list.get(position);
        Intent intent = null;
        switch (item.id){
            case 1:
                intent = new Intent(this, ExpandTextViewActivity.class);
                break;
            case 2:
                intent = new Intent(this, TVListActivity.class);
                break;
            case 3:
                intent = new Intent(this,TagGroupViewActivity.class);
                break;
            case 4:
                intent = new Intent(this,CircleProgressViewActivity.class);
                break;
        }
        intent.putExtra("title", item.name);
        startActivity(intent);
    }
}
