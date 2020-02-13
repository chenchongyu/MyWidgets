package net.runningcoder.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author kevin
 * @since 2019/08/20
 * <p>
 * 两个元素自动压缩排序布局。
 */
public class BtsTwoPartsEllipsizeLayout extends ViewGroup {
    /**
     * 当小size的元素占据了总体的多少时，进行分割。如果是0.5，则是当小元素
     * 大于一半大小时，两个元素进行均分。如果是0.1，那么就是当小元素大于10%
     * 大小时，大元素占据9成，小元素就值占据这一成。
     */
    private static final float MIN_CHILD_MAX_WIDTH_PERCENT = 0.5f;
    /**
     * 目前只允许两个元素，若有其他的需求再定制修改，目前此布局用以做女性助手的action item bar
     * 适配工作，如有需求联系作者或进行自定义
     */
    private View littleChild = null;
    private View otherChild = null;

    public BtsTwoPartsEllipsizeLayout(Context context) {
        super(context);
    }

    public BtsTwoPartsEllipsizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BtsTwoPartsEllipsizeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //想要兼容MarginLayoutParams必须复写这些方法以正常运行 BEGIN
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
    //想要兼容MarginLayoutParams必须复写这些方法以正常运行 END

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() == 0 || getChildCount() != 2) {
            throw new IllegalStateException("必须包含两个View");
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int realWidth = getPaddingLeft() + getPaddingRight();
        int realHeight = getPaddingTop() + getPaddingBottom();

        //这一步假装完全测量，只为了找出较小的那一个
        if (littleChild == null) {
            measureChildren(MeasureSpec.makeMeasureSpec(1 << 30 - 1, MeasureSpec.AT_MOST),
                    MeasureSpec.makeMeasureSpec(1 << 30 - 1, MeasureSpec.AT_MOST));
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (littleChild == null || littleChild.getMeasuredWidth() > child.getMeasuredWidth()) {
                    littleChild = child;
                }
            }
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child != littleChild) {
                    otherChild = child;
                    break;
                }
            }
        }
        switch (widthMode) {
            //父布局对我有明确大小
            case MeasureSpec.EXACTLY:
            //父布局对我任意，但告诉了我最大边界，那我就成为最大边界
            case MeasureSpec.AT_MOST:
                realWidth = width;
                break;
            //如果父布局都拿不准自己多大，那自己就按照想要的来
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    realWidth += child.getMeasuredWidth();
                    realWidth += (leftMargin(child) + rightMargin(child));
                }
                break;
        }

        switch (heightMode) {
            //父布局明确自己的范围，那么明确
            case MeasureSpec.EXACTLY:
                realHeight = height;
                break;
            //父布局告诉自己随意
            case MeasureSpec.AT_MOST:
                //父布局告诉自己不确定，那么按照实际的来
            case MeasureSpec.UNSPECIFIED:
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    realHeight = Math.max(realHeight, child.getMeasuredHeight()
                            + ((MarginLayoutParams) child.getLayoutParams()).topMargin
                            + ((MarginLayoutParams) child.getLayoutParams()).bottomMargin);
                }
                break;
        }

        //构造真实的spec重新measure child

        int heightSpec = MeasureSpec.makeMeasureSpec(realHeight, MeasureSpec.AT_MOST);
        if (((float) littleChild.getMeasuredWidth()) / realWidth >= MIN_CHILD_MAX_WIDTH_PERCENT) {
            //小的那一个的真实长度已经超过了总长度的某比例，那么代表长的那个一定也是越界了，那么每个元素占一半，不偏不倚
            int halfWidth = (realWidth - getPaddingLeft() - getPaddingRight()) / 2;
            measureChild(littleChild, MeasureSpec.makeMeasureSpec(halfWidth - leftMargin(littleChild)
                    - rightMargin(littleChild), MeasureSpec.EXACTLY), heightSpec);
            measureChild(otherChild, MeasureSpec.makeMeasureSpec(halfWidth - leftMargin(otherChild)
                    - rightMargin(otherChild), MeasureSpec.EXACTLY), heightSpec);
        } else {
            measureChild(littleChild, MeasureSpec.makeMeasureSpec(littleChild.getMeasuredWidth(), MeasureSpec.AT_MOST), heightSpec);
            measureChild(otherChild, MeasureSpec.makeMeasureSpec(realWidth - getPaddingLeft() - getPaddingRight() - littleChild.getMeasuredWidth()
                            - leftMargin(littleChild)
                            - rightMargin(littleChild)
                            - leftMargin(otherChild)
                            - rightMargin(otherChild),
                    MeasureSpec.EXACTLY), heightSpec);
        }

        setMeasuredDimension(realWidth, realHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        boolean littleFirst = indexOfChild(littleChild) == 0;
        int littleLeftWithoutMargin = littleFirst ? getPaddingLeft() : getPaddingLeft() + leftMargin(otherChild)
                + rightMargin(otherChild) + otherChild.getMeasuredWidth();
        int otherLeftWithoutMargin = littleFirst ? getPaddingLeft() + leftMargin(littleChild)
                + littleChild.getMeasuredWidth() + rightMargin(littleChild) : getPaddingLeft();

        littleChild.layout(littleLeftWithoutMargin + leftMargin(littleChild), 0,
                littleLeftWithoutMargin + leftMargin(littleChild) + littleChild.getMeasuredWidth(),
                littleChild.getMeasuredHeight());
        otherChild.layout(otherLeftWithoutMargin + leftMargin(otherChild), 0,
                otherLeftWithoutMargin + leftMargin(otherChild) + otherChild.getMeasuredWidth(),
                otherChild.getMeasuredHeight());
    }

    private int leftMargin(View child) {
        return ((MarginLayoutParams) child.getLayoutParams()).leftMargin;
    }

    private int rightMargin(View child) {
        return ((MarginLayoutParams) child.getLayoutParams()).rightMargin;
    }
}
