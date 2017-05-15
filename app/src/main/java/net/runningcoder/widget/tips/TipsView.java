package net.runningcoder.widget.tips;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.runningcoder.R;
import net.runningcoder.util.ViewUtils;

import static net.runningcoder.widget.tips.TriangleView.Direction.DOWN;
import static net.runningcoder.widget.tips.TriangleView.Direction.LEFT;
import static net.runningcoder.widget.tips.TriangleView.Direction.RIGHT;
import static net.runningcoder.widget.tips.TriangleView.Direction.UP;

/**
 * Author： chenchongyu
 * Email: chenchongyu@didichuxing.com
 * Date: 2017/5/11
 * Description:
 */

public class TipsView {
    private final static int TRIANGLE_HEIGHT = 10;
    private final static int TRIANGLE_WIDTH = 5;
    private final static double DEFAULT_WIDTH_RATE = 0.8;
    private PopupWindow mPopupWindow;
    private TriangleView mTriangleView;
    private LinearLayout contentView,contentBox;

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
    /**设置宽高，默认是WRAP_CONTENT*/
    private int width,height;
    private TriangleView.Direction mDirection;
    /**箭头位置策略，目前有两种；
     * 1、默认选项，auto，自适应
     * 2、锚点策略，箭头跟随锚点位置
     */
    private TriangleView.PosStrategy mStrategy;

    public TipsView(Builder builder){
        this(builder.context,builder.mDirection);

        if (!TextUtils.isEmpty(builder.title)){
            setTitle(builder.title);
        }

        if (!TextUtils.isEmpty(builder.content)){
            setContent(builder.content);
        }

        if (builder.mStrategy != null )
            this.mStrategy = builder.mStrategy;

        if (builder.widthRate != 0.0){
            setWidthRate(builder.widthRate);
        }

        if (builder.color != 0){
            setColor(builder.color);
        }

        if (builder.onClickListener != null){
            setOnClickListener(builder.onClickListener);
        }

        if (builder.width != 0){
            this.width = builder.width;
        }

        if (builder.height != 0){
            this.height = builder.height;
        }

    }

    private TipsView(Context context, TriangleView.Direction direction) {
        this.context = context;
        mDirection = direction;
        widthRate = DEFAULT_WIDTH_RATE;
        mStrategy = TriangleView.PosStrategy.AUTO_CENTER;
        init();
    }

    private void init() {
        mTriangleView = new TriangleView(context);
        mTriangleView.setDirection(mDirection);

        mPopupWindow = new PopupWindow(context);

//        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(null);
        setContentView(R.layout.layout_tips_view);

    }

    private void setContentView(int layout) {
        contentView = (LinearLayout) LayoutInflater.from(context).inflate(layout,null);
        contentBox = (LinearLayout) contentView.findViewById(R.id.tips_box);

        vTitle = (TextView) contentView.findViewById(R.id.tips_title);
        vContent = (TextView) contentView.findViewById(R.id.tips_content);

        mPopupWindow.setContentView(contentView);

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
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClick(TipsView.this);
            }
        });
    }

    public void show(View anchor){
        measurePopWindow(anchor);
        assemble(anchor);

        mPopupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY,popWindowX,popWindowY);

    }


    public void dismiss(){
        mPopupWindow.dismiss();
    }

    private void assemble(View anchor) {

        switch (mDirection){
            case UP:
                setVerticalUpLayout(anchor);
                break;
            case DOWN:
                setVerticalDownLayout(anchor);
                break;
            case LEFT:
                setHorizontalLeftLayout(anchor);
                break;
            case RIGHT:
                setHorizontalRightLayout();
                break;
        }
    }

    private int getXOffset(View anchor) {
        int anchorWidth = anchor.getMeasuredWidth();
        if (mDirection == LEFT){
            Point point = ViewUtils.getScreenWH(context);
            if (point.x - anchorWidth < contentViewHeight){
                //如果pop和anchor的宽度超过屏幕，则设置pop的宽度为point.x - anchorwidth-三角号宽度
                mPopupWindow.setWidth(point.x - anchorWidth-dp2px(TRIANGLE_HEIGHT));
            }
            return locations[0]+ anchorWidth;
        }else if (mDirection == RIGHT){
            return (int) (anchor.getX() - contentViewWidth-dp2px(TRIANGLE_HEIGHT));
        }else{
            //上下方向 锚点x-10% （rate=0.8为例）
            return (int) (anchor.getX()+anchorWidth*(1-widthRate)/2);
        }
    }

    private int getYOffset(View anchor){

        if (mDirection == DOWN){
            //当内容为多行的时候，contentView.measure测量的高度不准确，需要减去一个行高；
            int lineHeight = 0 ;
            if (vTitle.getVisibility() == View.VISIBLE){
                lineHeight = vTitle.getLineHeight();
            }else
                lineHeight = vContent.getLineHeight();
            return locations[1]-contentViewHeight-dp2px(TRIANGLE_WIDTH)-lineHeight;
        }else if (mDirection == UP){
            return locations[1]+anchor.getMeasuredHeight();
        }else{
            return locations[1]-(contentViewHeight/2- anchor.getMeasuredHeight()/2);
        }

    }

    //测量、设置popwindow和内容区的宽高，测量popwindow的xy坐标
    private void measurePopWindow(View anchor) {
        if (height != 0)
            mPopupWindow.setHeight(height);
        else
            mPopupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);

        if (width != 0)
            mPopupWindow.setWidth(width);
        else{
            mPopupWindow.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);

            if (mDirection == UP || mDirection == DOWN)
                mPopupWindow.setWidth((int) (anchor.getWidth()*widthRate));
        }


        locations = new int[2];
        anchor.getLocationInWindow(locations);

        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);

        contentView.measure(spec,spec);
        //计算popwindow的高度
        contentViewHeight = contentView.getMeasuredHeight();
        contentViewWidth = contentView.getMeasuredWidth();

        popWindowY = getYOffset(anchor);
        popWindowX = getXOffset(anchor);
    }


    /**
     * 箭头向上布局
     */
    private void setVerticalUpLayout(View view) {
        mDirection = UP;

        LinearLayout.LayoutParams posParams = new LinearLayout.LayoutParams(dp2px(TRIANGLE_HEIGHT),dp2px(TRIANGLE_WIDTH));
        if (mStrategy != TriangleView.PosStrategy.AUTO_CENTER)
            posParams.setMargins(view.getLeft()+view.getWidth()/2,0,0,0);
        else
            contentView.setGravity(Gravity.CENTER_HORIZONTAL);

        mTriangleView.setLayoutParams(posParams);
        contentView.addView(mTriangleView,0);

    }

    /**
     * 箭头向下布局
     */
    private void setVerticalDownLayout(View view) {
        mDirection = DOWN;

        LinearLayout.LayoutParams posParams = new LinearLayout.LayoutParams(dp2px(TRIANGLE_HEIGHT),dp2px(TRIANGLE_WIDTH));
        mTriangleView.setLayoutParams(posParams);
//        posParams.setMargins(view.getLeft()+view.getWidth()/2,0,0,0);
        contentView.addView(mTriangleView,1);
        contentView.setGravity(Gravity.CENTER_HORIZONTAL);

    }

    /**
     * 箭头向左布局
     */
    private void setHorizontalLeftLayout(View view) {
        mDirection = LEFT;

        contentView.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams posParams = new LinearLayout.LayoutParams(dp2px(TRIANGLE_WIDTH),dp2px(TRIANGLE_HEIGHT));
        //箭头Y坐标 = view的y+（view的高度-箭头高度）/2
        //marginTop = 箭头Y-popwindow y
//        int top = locations[1]+(view.getMeasuredHeight()-dp2px(TRIANGLE_HEIGHT)) / 2;
//        posParams.setMargins(0, top-popWindowY,0,0);
        mTriangleView.setLayoutParams(posParams);

        contentView.addView(mTriangleView,0);
        contentView.setGravity(Gravity.CENTER_VERTICAL);
    }

    /**
     * 箭头向右布局
     */
    private void setHorizontalRightLayout() {
        mDirection = RIGHT;

        contentView.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams posParams = new LinearLayout.LayoutParams(dp2px(TRIANGLE_WIDTH),dp2px(TRIANGLE_HEIGHT));
        //箭头Y坐标 = view的y+（view的高度-箭头高度）/2
        //marginTop = 箭头Y-popwindow y
//        int top = locations[1]+(view.getMeasuredHeight()-dp2px(TRIANGLE_HEIGHT)) / 2;
//        posParams.setMargins(0, top-popWindowY,0,0);
        mTriangleView.setLayoutParams(posParams);

        contentView.addView(mTriangleView,1);
        contentView.setGravity(Gravity.CENTER_VERTICAL);
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

        public Builder with(Context context){
            this.context = context;
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
