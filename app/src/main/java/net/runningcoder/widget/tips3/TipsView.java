package net.runningcoder.widget.tips3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.runningcoder.util.ViewUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static net.runningcoder.widget.tips3.TriangleView.Direction.DOWN;
import static net.runningcoder.widget.tips3.TriangleView.Direction.LEFT;
import static net.runningcoder.widget.tips3.TriangleView.Direction.RIGHT;
import static net.runningcoder.widget.tips3.TriangleView.Direction.UP;

/**
 * Author： chenchongyu
 * Email: chenchongyu@didichuxing.com
 * Date: 2017/5/11
 * Description:
 */

public class TipsView extends LinearLayout{
    private final static int TRIANGLE_HEIGHT = 10;
    private final static int TRIANGLE_WIDTH = 5;
    private final static double DEFAULT_WIDTH_RATE = 0.8;
//    private PopupWindow mPopupWindow;
    private TriangleView mTriangleView;
    private LinearLayout contentBox;

    private int contentViewHeight;//popwindow内容高度
    private int contentViewWidth;//popwindow内容宽度
    private int popWindowY;//popwindow y坐标
    private int popWindowX;
    private int[] locations;
    private TextView vContent;
    private TextView vTitle;

    //可配置参数
    /**popwindow宽度和锚点宽度比例，默认为0.8*/
    private double widthRate ;
    private Context context;
    private View anchor;
    /**设置宽高，默认是WRAP_CONTENT*/
    private int width,height;
    private TriangleView.Direction mDirection;
    /**箭头位置策略，目前有两种；
     * 1、默认选项，auto，自适应
     * 2、锚点策略，箭头跟随锚点位置
     */
    private TriangleView.PosStrategy mStrategy;

    private int mTriangleGravity;

    public TipsView(Builder builder){
        super(builder.context);

        this.context = builder.context;
        mDirection = builder.mDirection;
        widthRate = DEFAULT_WIDTH_RATE;
        mStrategy = TriangleView.PosStrategy.AUTO_CENTER;

        if (builder.width != 0){
            this.width = builder.width;
        }else{
            this.width = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        if (builder.height != 0){
            this.height = builder.height;
        }else {
            this.height = WindowManager.LayoutParams.WRAP_CONTENT;
        }

        if (builder.anchor != null){
            this.anchor = builder.anchor;
        }

        if (builder.mStrategy != null )
            this.mStrategy = builder.mStrategy;

        if (builder.widthRate != 0.0){
            setWidthRate(builder.widthRate);
        }

        initView();


        if (!TextUtils.isEmpty(builder.title)){
            setTitle(builder.title);
        }

        if (!TextUtils.isEmpty(builder.content)){
            setContent(builder.content);
        }


        if (builder.color != 0){
            setColor(builder.color);
        }

        if (builder.onClickListener != null){
            setOnClickListener(builder.onClickListener);
        }

    }

    private void initView() {
        if (mDirection == UP || mDirection == DOWN){
            setOrientation(VERTICAL);
            mTriangleGravity = Gravity.CENTER_HORIZONTAL;
        } else{
            setOrientation(HORIZONTAL);
            mTriangleGravity = Gravity.CENTER_VERTICAL;
        }

        if (mDirection == UP || mDirection == LEFT){
            setTriangleView();
            setContentView();
        }else {
            setContentView();
            setTriangleView();
        }


    }


    private void setTriangleView() {
        mTriangleView = new TriangleView(context);
        mTriangleView.setDirection(mDirection);

        LinearLayout.LayoutParams posParams = new LinearLayout.LayoutParams(dp2px(TRIANGLE_HEIGHT),dp2px(TRIANGLE_WIDTH));
        if (mStrategy != TriangleView.PosStrategy.AUTO_CENTER)
            posParams.setMargins(anchor.getLeft()+anchor.getWidth()/2,0,0,0);
        else{
            posParams.gravity = mTriangleGravity;
        }


        mTriangleView.setLayoutParams(posParams);
        addView(mTriangleView);

    }

    private void setContentView() {
        contentBox = new LinearLayout(context);
        contentBox.setOrientation(VERTICAL);

        if (width != MATCH_PARENT)
            contentBox.setLayoutParams(new LayoutParams(WRAP_CONTENT,WRAP_CONTENT));
        else
            contentBox.setLayoutParams(new LayoutParams(MATCH_PARENT,WRAP_CONTENT));


        vTitle = new TextView(context);
        vTitle.setLayoutParams(new LayoutParams(WRAP_CONTENT,WRAP_CONTENT));
//        vTitle.setVisibility(GONE);

        vContent = new TextView(context);
        vContent.setLayoutParams(new LayoutParams(WRAP_CONTENT,WRAP_CONTENT));
//        vContent.setVisibility(GONE);

        contentBox.addView(vTitle);
        contentBox.addView(vContent);

        addView(contentBox);

        setBackgroundColor(Color.YELLOW);
    }

    public void setContent(String content) {
        vContent.setText(content);
        vContent.setVisibility(View.VISIBLE);
    }

    public void setTitle(String title) {
        vTitle.setText(title);
        vTitle.setVisibility(View.VISIBLE);
    }

    public void setColor(int color){
        contentBox.setBackgroundColor(context.getResources().getColor(color));
        mTriangleView.setColor(context.getResources().getColor(color));
    }

    public void setWidthRate(double rate){
        this.widthRate = rate;
    }

    public void setOnClickListener(final OnTipClickListener onClickListener){
        this.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(TipsView.this);
            }
        });
    }

    public void show(View anchor){
        setVisibility(VISIBLE);
        ViewGroup root = (ViewGroup) ((Activity)context).getWindow().getDecorView();
        root.addView(this);
    }


    public void dismiss(){
        setVisibility(GONE);
    }

    private int getXOffset(View anchor) {
        int anchorWidth = anchor.getMeasuredWidth();
        if (mDirection == LEFT){
            Point point = ViewUtils.getScreenWH(context);
            if (point.x - anchorWidth < contentViewHeight){
                //如果pop和anchor的宽度超过屏幕，则设置pop的宽度为point.x - anchorwidth-三角号宽度
//                mPopupWindow.setWidth(point.x - anchorWidth-dp2px(TRIANGLE_HEIGHT));
            }
            return locations[0]+ anchorWidth;
        }else if (mDirection == RIGHT){
            return (int) (anchor.getX() - contentViewWidth-dp2px(TRIANGLE_HEIGHT));
        }else{
            //上下方向 锚点x-10% （rate=0.8为例）
            return (int) (locations[0]+anchorWidth*(1-widthRate)/2);
        }
    }

    private int getYOffset(View anchor){

        if (mDirection == DOWN){
            return locations[1]-contentViewHeight-dp2px(TRIANGLE_WIDTH);
        }else if (mDirection == UP){
            return locations[1]+anchor.getMeasuredHeight();
        }else{
            return locations[1]-(contentViewHeight/2- anchor.getMeasuredHeight()/2);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        locations = new int[2];
        anchor.getLocationInWindow(locations);

        if (width != MATCH_PARENT){
            contentViewWidth = (int) (anchor.getMeasuredWidth() * widthRate);
            width = contentViewWidth;
        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        height = contentBox.getMeasuredHeight()+dp2px(TRIANGLE_HEIGHT);

//        if (width == ViewGroup.LayoutParams.WRAP_CONTENT)
//            width = contentBox.getMeasuredWidth();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(width, height);

        contentViewHeight = getMeasuredHeight();
        contentViewWidth = getMeasuredWidth();

        int marginX = width == MATCH_PARENT?0:getXOffset(anchor);
        int marginY = getYOffset(anchor);

        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width,height);
        layoutParams.setMargins(marginX,marginY,0,0);

        this.setLayoutParams(layoutParams);


    }

    public static class Builder{
        private double widthRate;
        private Context context;
        private TriangleView.Direction mDirection;
        private OnTipClickListener onClickListener;
        private TriangleView.PosStrategy mStrategy;
        private String title,content;
        private int color;
        private int width,height;
        private View anchor;

        public Builder with(Context context){
            this.context = context;
            return this;
        }

        public Builder anchor(View anchor){
            this.anchor = anchor;
            return this;
        }

        public Builder position(TriangleView.PosStrategy mStrategy){
            this.mStrategy = mStrategy;
            return this;
        }

        public Builder rate(double rate){
            this.widthRate = rate;
            return this;
        }

        public Builder title(String title){
            this.title = title;
            return this;
        }

        public Builder content(String content){
            this.content = content;
            return this;
        }

        public Builder color(int color){
            this.color = color;
            return this;
        }

        public Builder width(int width){
            this.width = width;
            return this;
        }

        public Builder height(int height){
            this.height = height;
            return this;
        }

        public Builder direction(TriangleView.Direction direction){
            this.mDirection= direction;
            return this;
        }

        public Builder setOnClickListener(OnTipClickListener onClickListener){
            this.onClickListener = onClickListener;
            return this;
        }

        public TipsView build(){
            return new TipsView(this);
        }
    }


    public interface OnTipClickListener{
        void onClick(TipsView view);
    }

    private int dp2px(float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }



}
