package net.runningcoder.activity.recyclerview;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.adapter.helper.AdapterItemOp;
import net.runningcoder.adapter.helper.ItemTouchCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author： chenchongyu
 * Date: 2019/2/26
 * Description:
 */
public class RecyclerTestAct extends BasicActivity {
    RecyclerView mRecyclerView;
    int type;
    List<String> list = new ArrayList<>();
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        type = getIntent().getIntExtra("type", 0);
        mRecyclerView = findViewById(R.id.v_recyclerView);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        adapter = new MyAdapter(list);
        mRecyclerView.setAdapter(adapter);

        initData(type);
    }

    private void initData(int type) {
        if (type == 0) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            //关联 ItemTouchHelper
            ItemTouchCallback itemTouchCallback = new ItemTouchCallback(adapter);
            ItemTouchHelper helper = new ItemTouchHelper(itemTouchCallback);
            helper.attachToRecyclerView(mRecyclerView);

            for (int i = 0; i < 10; i++) {
                list.add("第" + i + "条数据！");
            }
        } else if (type == 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            LinearSnapHelper snapHelper = new LinearSnapHelper();
            snapHelper.attachToRecyclerView(mRecyclerView);
            list.add(R.drawable.girl + "");
            list.add(R.drawable.girl3 + "");
            list.add(R.drawable.girl5 + "");
            list.add(R.drawable.lrt + "");
            list.add(R.drawable.lrt + "");
            list.add(R.drawable.girl + "");
            list.add(R.drawable.girl3 + "");
            list.add(R.drawable.girl5 + "");
//            list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513856046715&di=00193bc5b4bd4a333fe743ab182fc56f
// &imgtype" +
//                    "=0&src=http%3A%2F%2Fimg237.ph.126.net%2F4UHQwpAaYyZ3p61VtErOeQ%3D%3D%2F1376694111092846024.jpg");
//            list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513856046715&di=95d2b2ebe486b33e48769b79c3eaa254
// &imgtype" +
//                    "=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3Dfe84214135fa828bd1239debcd1e41cd" +
//                    "%2F980e6609c93d70cf797d0f49fedcd100bba12b9b.jpg");
//            list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513856046714&di=8ef041f558e215d50ce26e136ce1396e
// &imgtype" +
//                    "=0&src=http%3A%2F%2Fp2.gexing.com%2FG1%2FM00%2F82%2F0A%2FrBACJ1V0WymgEVySAAiyPgr9wKM301_600x.jpg");
//            list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513855956982&di=77233b479766f4087f8858278bf7d3f1
// &imgtype" +
//                    "=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F016bf257b2c0930000012e7e202fdc.jpg%40900w_1l_2o_100sh.jpg");
//            list.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2133214656,3573702769&fm=27&gp=0.jpg");
        }
//        adapter.notifyDataSetChanged();
        adapter.notifyItemRangeInserted(0, list.size());
    }

    @Override
    public int getContentViewID() {
        return R.layout.activity_tvlist;
    }

    public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements AdapterItemOp {
        private List<String> list = new ArrayList<>();

        public MyAdapter(List<String> list) {
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            RecyclerView.ViewHolder viewHolder = null;
            switch (type) {
                case 0:
                    viewHolder = new TextVHolder(parent);
                    break;
                case 1:
                    viewHolder = new ImageVHolder(parent);
                    break;
            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            switch (type) {
                case 0:
                    ((TextVHolder) holder).textView.setText(list.get(position));
                    break;
                case 1:
                    ((ImageVHolder) holder).imageView.setImageResource(Integer.parseInt(list.get(position)));
//                    Glide.with(RecyclerTestAct.this)
//                            .load(list.get(position))
//                            .crossFade()
//                            .into(((ImageVHolder) holder).imageView);

            }
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        @Override
        public void onItemMove(int from, int to) {
            notifyItemMoved(from, to);
            Collections.swap(list, from, to);
        }

        @Override
        public void onItemDelete(int position) {

        }
    }

    static class TextVHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        TextVHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
            textView = itemView.findViewById(R.id.v_title);
        }
    }

    static class ImageVHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        ImageVHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_image, parent, false));
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
