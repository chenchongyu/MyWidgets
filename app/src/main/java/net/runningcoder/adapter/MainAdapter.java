package net.runningcoder.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.runningcoder.R;
import net.runningcoder.bean.WidgetItem;
import net.runningcoder.listener.OnItemClickForRecycler;
import net.runningcoder.util.L;

import java.util.List;

/**
 * Created by Administrator on 2015/9/16.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder>{

    private List<WidgetItem> list;
    public OnItemClickForRecycler listener;
    public MainAdapter(List<WidgetItem> list,OnItemClickForRecycler listener){
        L.i("list size:"+list.size());
        this.list = list;
        this.listener = listener;
    }
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        WidgetItem item = list.get(position);
        holder.mTextView.setText(item.name);
        holder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            v.setOnClickListener(this);
            mTextView = (TextView) v.findViewById(R.id.v_title);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v,getPosition());

        }
    }

}
