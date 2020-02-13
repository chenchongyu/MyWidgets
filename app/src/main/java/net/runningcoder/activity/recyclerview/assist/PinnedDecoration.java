package net.runningcoder.activity.recyclerview.assist;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import net.runningcoder.R;
import net.runningcoder.adapter.helper.IDecorationCallback;

/**
 * Author： chenchongyu
 * Date: 2019-07-19
 * Description:
 */
public class PinnedDecoration extends RecyclerView.ItemDecoration {
    private IDecorationCallback callback;
    private Paint textPaint;
    private Paint groupPaint;
    private int groupHeight;

    public PinnedDecoration(Context context, IDecorationCallback callback) {
        this.callback = callback;
        Resources resources = context.getResources();
        groupPaint = new Paint();
        groupPaint.setColor(Color.BLUE);

        textPaint = new TextPaint();
        textPaint.setTypeface(Typeface.DEFAULT_BOLD);
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(80);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.LEFT);

        groupHeight = resources.getDimensionPixelSize(R.dimen.group_height);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        if (pos == 0) {
            outRect.top = groupHeight;
        }

        if (!TextUtils.equals(callback.getGroupName(pos), callback.getGroupName(pos - 1))) {
            outRect.top = groupHeight;
        }
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        final int itemCount = state.getItemCount(); //获取所有条目
        final int childCount = parent.getChildCount(); //获取可见条目数
        final int left = parent.getLeft() + parent.getPaddingLeft();
        final int right = parent.getRight() - parent.getPaddingRight();
        String groupName = null;
        String preGroupName;
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            int pos = parent.getChildAdapterPosition(view);
            preGroupName = groupName;
            groupName = callback.getGroupName(pos);
            if (groupName == null || TextUtils.equals(preGroupName, groupName)) {
                continue;
            }

            //所画矩形的bottom在第一个view的top之上；
            int viewBottom = Math.max(groupHeight, (view.getTop() + parent.getPaddingTop()));

//            if (pos + 1 < itemCount) {
//                //获取下个GroupName
//                String nextGroupName = callback.getGroupName(pos + 1);
//                //下一组的第一个View接近头部
//                int bottom = view.getBottom();
//                if (!groupName.equals(nextGroupName) && viewBottom < bottom) {
//                    viewBottom = bottom;
//                }
//            }

            c.drawRect(left, viewBottom - groupHeight, right, viewBottom, groupPaint);
            //文字竖直居中显示
            Paint.FontMetrics fm = textPaint.getFontMetrics();
            float baseLine = viewBottom - (groupHeight - (fm.bottom - fm.top)) / 2 - fm.bottom;
            c.drawText(groupName, left, baseLine, textPaint);

        }

    }
}
