package net.runningcoder.activity.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.activity.recyclerview.assist.PinnedDecoration;
import net.runningcoder.adapter.helper.IDecorationCallback;
import net.runningcoder.bean.GroupItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： chenchongyu
 * Date: 2019/2/26
 * Description:
 */
public class RecyclerPinHeaderAct extends BasicActivity implements IDecorationCallback {
    RecyclerView mRecyclerView;
    int type;
    List<GroupItem> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mRecyclerView = findViewById(R.id.v_recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new PinnedDecoration(this, this));
//        mRecyclerView.addItemDecoration(StickyDecoration.Builder.init(this).build());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(list);
        mRecyclerView.setAdapter(adapter);

        initData(type);
    }

    private void initData(int type) {

        list.add(new GroupItem("黑龙江", "哈尔滨"));
        list.add(new GroupItem("黑龙江", "双鸭山"));
        list.add(new GroupItem("黑龙江", "佳木斯"));
        list.add(new GroupItem("黑龙江", "牡丹江"));
        list.add(new GroupItem("黑龙江", "鹤岗"));
        list.add(new GroupItem("河北", "石家庄"));
        list.add(new GroupItem("河北", "保定"));
        list.add(new GroupItem("河北", "唐山"));
        list.add(new GroupItem("河北", "张家口"));
        list.add(new GroupItem("广东", "深圳"));
        list.add(new GroupItem("广东", "广州"));
        list.add(new GroupItem("黑龙江", "哈尔滨"));
        list.add(new GroupItem("黑龙江", "双鸭山"));
        list.add(new GroupItem("黑龙江", "佳木斯"));
        list.add(new GroupItem("黑龙江", "牡丹江"));
        list.add(new GroupItem("黑龙江", "鹤岗"));
        list.add(new GroupItem("河北", "石家庄"));
        list.add(new GroupItem("河北", "保定"));
        list.add(new GroupItem("河北", "唐山"));
        list.add(new GroupItem("河北", "张家口"));
        list.add(new GroupItem("广东", "深圳"));
        list.add(new GroupItem("广东", "广州"));
        list.add(new GroupItem("黑龙江", "哈尔滨"));
        list.add(new GroupItem("黑龙江", "双鸭山"));
        list.add(new GroupItem("黑龙江", "佳木斯"));
        list.add(new GroupItem("黑龙江", "牡丹江"));
        list.add(new GroupItem("黑龙江", "鹤岗"));
        list.add(new GroupItem("河北", "石家庄"));
        list.add(new GroupItem("河北", "保定"));
        list.add(new GroupItem("河北", "唐山"));
        list.add(new GroupItem("河北", "张家口"));
        list.add(new GroupItem("广东", "深圳"));
        list.add(new GroupItem("广东", "广州"));
        list.add(new GroupItem("黑龙江", "哈尔滨"));
        list.add(new GroupItem("黑龙江", "双鸭山"));
        list.add(new GroupItem("黑龙江", "佳木斯"));
        list.add(new GroupItem("黑龙江", "牡丹江"));
        list.add(new GroupItem("黑龙江", "鹤岗"));
        list.add(new GroupItem("河北", "石家庄"));
        list.add(new GroupItem("河北", "保定"));
        list.add(new GroupItem("河北", "唐山"));
        list.add(new GroupItem("河北", "张家口"));
        list.add(new GroupItem("广东", "深圳"));
        list.add(new GroupItem("广东", "广州"));

        adapter.notifyDataSetChanged();
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_tvlist;
    }

    @Override
    public String getGroupName(int idx) {
        if (idx < 0 || idx >= list.size()) {
            return null;
        }
        return list.get(idx).groupName;
    }

    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<GroupItem> list = new ArrayList<>();

        public MyAdapter(List<GroupItem> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = new TextVHolder(parent);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((TextVHolder) holder).textView.setText(list.get(position).text);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    static class TextVHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        TextVHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
            textView = itemView.findViewById(R.id.v_title);
        }
    }

}
