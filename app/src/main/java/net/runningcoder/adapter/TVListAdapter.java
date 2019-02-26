package net.runningcoder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.runningcoder.R;
import net.runningcoder.adapter.helper.AdapterItemOp;
import net.runningcoder.listener.OnItemClickForRecycler;
import net.runningcoder.widget.ExpandableTextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class TVListAdapter extends RecyclerView.Adapter<TVListAdapter.ViewHolder> implements AdapterItemOp {

    private List<String> list;
    public OnItemClickForRecycler listener;

    public TVListAdapter(List<String> list, OnItemClickForRecycler listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public TVListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tvlist, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String item = list.get(position);
        holder.mTextView.setText(item);
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


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ExpandableTextView mTextView;

        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mTextView = (ExpandableTextView) v.findViewById(R.id.v_textview);
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                listener.onItemClick(v, getPosition());
            }

        }
    }

}
