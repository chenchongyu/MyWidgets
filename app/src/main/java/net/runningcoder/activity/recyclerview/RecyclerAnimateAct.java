package net.runningcoder.activity.recyclerview;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.bean.GroupItem;
import net.runningcoder.util.L;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author： chenchongyu
 * Email: chenchongyu@didichuxing.com
 * Date: 2019-08-08
 * Description:
 */
public class RecyclerAnimateAct extends BasicActivity {
    RecyclerView mRecyclerView;
    List<GroupItem> list = new ArrayList<>();
    List<GroupItem> datasource = new ArrayList<>();
    private MyAdapter adapter;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list.add((GroupItem) msg.obj);
            adapter.notifyItemRangeInserted(list.size() - 1, 1);
            addData();
        }
    };
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mRecyclerView = findViewById(R.id.v_recyclerView);
//        mRecyclerView.setItemAnimator(new SlideItemAnimator());
//        mRecyclerView.addItemDecoration(new PinnedDecoration(this, this));
//        mRecyclerView.addItemDecoration(StickyDecoration.Builder.init(this).build());
        mRecyclerView.setLayoutManager(new MyLinearLayoutManager(this));
//        mRecyclerView.setItemViewCacheSize(44);
        adapter = new MyAdapter(list);
        mRecyclerView.setAdapter(adapter);

        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initData() {
        datasource.add(new GroupItem("黑龙江", "佳木斯"));
        datasource.add(new GroupItem("黑龙江", "牡丹江"));
        datasource.add(new GroupItem("黑龙江", "鹤岗"));
        datasource.add(new GroupItem("河北", "石家庄"));
        datasource.add(new GroupItem("河北", "保定"));
        datasource.add(new GroupItem("河北", "唐山"));
        datasource.add(new GroupItem("河北", "张家口"));
        datasource.add(new GroupItem("广东", "深圳"));
        datasource.add(new GroupItem("广东", "广州"));
        datasource.add(new GroupItem("黑龙江", "哈尔滨"));
        datasource.add(new GroupItem("黑龙江", "双鸭山"));
        datasource.add(new GroupItem("黑龙江", "佳木斯"));
        datasource.add(new GroupItem("黑龙江", "牡丹江"));
        datasource.add(new GroupItem("黑龙江", "鹤岗"));
        datasource.add(new GroupItem("河北", "石家庄"));
        datasource.add(new GroupItem("河北", "保定"));
        datasource.add(new GroupItem("河北", "唐山"));
        datasource.add(new GroupItem("河北", "张家口"));
        datasource.add(new GroupItem("广东", "深圳"));
        datasource.add(new GroupItem("广东", "广州"));
        datasource.add(new GroupItem("黑龙江", "哈尔滨"));
        datasource.add(new GroupItem("黑龙江", "双鸭山"));
        datasource.add(new GroupItem("黑龙江", "佳木斯"));
        datasource.add(new GroupItem("黑龙江", "牡丹江"));
        datasource.add(new GroupItem("黑龙江", "鹤岗"));
        datasource.add(new GroupItem("河北", "石家庄"));
        datasource.add(new GroupItem("河北", "保定"));
        datasource.add(new GroupItem("河北", "唐山"));
        datasource.add(new GroupItem("河北", "张家口"));
        datasource.add(new GroupItem("广东", "深圳"));
        datasource.add(new GroupItem("广东", "广州"));
        datasource.add(new GroupItem("黑龙江", "哈尔滨"));
        datasource.add(new GroupItem("黑龙江", "双鸭山"));
        datasource.add(new GroupItem("黑龙江", "佳木斯"));
        datasource.add(new GroupItem("黑龙江", "牡丹江"));
        datasource.add(new GroupItem("黑龙江", "鹤岗"));
        datasource.add(new GroupItem("河北", "石家庄"));
        datasource.add(new GroupItem("河北", "保定"));
        datasource.add(new GroupItem("河北", "唐山"));
        datasource.add(new GroupItem("河北", "张家口"));
        datasource.add(new GroupItem("广东", "深圳"));
        datasource.add(new GroupItem("广东", "广州"));

        list.add(new GroupItem("黑龙江", "哈尔滨"));
        list.add(new GroupItem("黑龙江", "双鸭山"));

        list.addAll(datasource);
        L.i("列表个数：" + list.size());
        adapter.notifyDataSetChanged();

        mRecyclerView.smoothScrollToPosition(24);
//        adapter.notifyItemRangeInserted(0, list.size());
//        addData();
    }

    private void addData() {
        Message msg = Message.obtain();
        msg.obj = datasource.get(new Random().nextInt(datasource.size()));
        mHandler.sendMessageDelayed(msg, 1000);
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_tvlist;
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
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

            final GroupItem groupItem = list.get(position);
            ((TextVHolder) holder).textView.setText(position + " " + groupItem.groupName);
            final TextView descView = ((TextVHolder) holder).descView;
            descView.setText(groupItem.text);
            descView.setAlpha(0.0f);
            L.i(position + "==========onBindViewHolder=========");
            if (!groupItem.animateShown ) {
//                if (BtsAnimHelper.getDelayMills2(position) != -1) {
//                if (BtsAnimHelper.shouldAnim(position)) {
                startAnim(groupItem, descView, holder.getAdapterPosition());
//                }
//                }
//                else {
//                tryAnim(position, groupItem, descView);
//                }
            } else {
                L.i(position + "==========已经展示过动画=========");
                descView.setAlpha(1.0f);
                descView.setTranslationX(0);
            }
        }

        private void startAnim(final GroupItem groupItem, final TextView descView, int i) {
//            ViewUtils.measureView(descView);
            ViewCompat.setTranslationX(descView, 700);
            final ViewPropertyAnimatorCompat animation = ViewCompat.animate(descView);

            animation.translationX(0).alpha(1).setStartDelay(i * 200).setDuration(1000)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .setListener(new ViewPropertyAnimatorListener() {
                        @Override
                        public void onAnimationStart(View view) {
                            descView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationEnd(View view) {
                            groupItem.animateShown = true;
                        }

                        @Override
                        public void onAnimationCancel(View view) {

                        }
                    }).start();
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

    }

    static class TextVHolder extends RecyclerView.ViewHolder {
        TextView textView;
        TextView descView;

        TextVHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
            textView = itemView.findViewById(R.id.v_title);
            descView = itemView.findViewById(R.id.v_desc);

            setIsRecyclable(false);
        }
    }

    private static class MyLinearLayoutManager extends LinearLayoutManager {

        public MyLinearLayoutManager(Context context) {
            super(context);
        }

        @Override
        public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            super.smoothScrollToPosition(recyclerView, state, position);
            LinearSmoothScroller smoothScroller =
                    new LinearSmoothScroller(recyclerView.getContext()) {
                        @Override
                        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                            // 返回：滑过1px时经历的时间(ms)。
                            return 150f / displayMetrics.densityDpi;
                        }

                        @Override
                        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
                            return boxStart - viewStart;
                        }
                    };

            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        }
    }
}
