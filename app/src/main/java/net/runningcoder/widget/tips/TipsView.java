package net.runningcoder.widget.tips;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import net.runningcoder.R;
import net.runningcoder.util.ViewUtils;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static net.runningcoder.widget.tips.TriangleView.Direction.DOWN;
import static net.runningcoder.widget.tips.TriangleView.Direction.LEFT;
import static net.runningcoder.widget.tips.TriangleView.Direction.RIGHT;
import static net.runningcoder.widget.tips.TriangleView.Direction.UP;

/**
 * Author： chenchongyu
 * Date: 2017/5/11
 * Description:
 */
public class TipsView {
    private final static int TRIANGLE_HEIGHT = 10;
    private final static int TRIANGLE_WIDTH = 6;
    private final static double DEFAULT_WIDTH_RATE = 0.8;
    private PopupWindow mPopupWindow;
    private TriangleView mTriangleView;
    private FrameLayout contentView;
    private ViewGroup contentBox;

    private int contentViewHeight;//popwindow内容高度
    private int contentViewWidth;//popwindow内容宽度
    private int popWindowY;//popwindow y坐标
    private int popWindowX;
    private int[] locations;
    private TextView vContent;
    private TextView vTitle;
    private ImageView vIcon;

    //可配置参数
    /**popwindow宽度和锚点宽度比例，默认为0.8*/
    private double widthRate ;
    private Context context;
    /**设置宽高，默认是WRAP_CONTENT*/
    private int width,height;
    private int radius;
    private TriangleView.Direction mDirection;
    /**箭头位置策略，目前有两种；
     * 1、默认选项，auto，自适应
     * 2、锚点策略，箭头跟随锚点位置
     */
    private TriangleView.PosStrategy mStrategy;
    private int bgColor;
    /**默认没有边框，设置颜色之后才生效*/
    private float borderWidth;

    public TipsView(Builder builder){
        this(builder.context,builder.mDirection);

        this.borderWidth = builder.borderWidth;
        this.width = builder.width;
        this.height = builder.height;
        this.radius = builder.radius;

        if (!TextUtils.isEmpty(builder.title)){
            setTitle(builder.title);
        }

        if (builder.icon != 0){
            setIcon(builder.icon);
        }

        if (builder.titleColor != 0 ){
            vTitle.setTextColor(context.getResources().getColor(builder.titleColor));
        }

        if (!TextUtils.isEmpty(builder.content)){
            setContent(builder.content);
        }

        if (builder.color != 0){
            setColor(builder.color);
        }

        if (builder.contentColor != 0){
            vContent.setTextColor(context.getResources().getColor(builder.contentColor));
        }

        if (builder.mStrategy != null )
            this.mStrategy = builder.mStrategy;

        if (builder.widthRate != 0.0){
            setWidthRate(builder.widthRate);
        }

        if (builder.borderColor != 0){
            setBorderColor(builder.borderColor);
        }

        if (builder.onClickListener != null){
            setOnClickListener(builder.onClickListener);
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
        setContentView();

    }

    private void setContentView() {
//        contentView = new LinearLayout(context);
//        contentView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT,WRAP_CONTENT));
//        contentView.setOrientation(LinearLayout.VERTICAL);

        contentView = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.layout_tips_view,null);
        contentBox = (ViewGroup) contentView.findViewById(R.id.tips_box);


        vTitle = (TextView) contentBox.findViewById(R.id.tips_title);
        vContent = (TextView) contentBox.findViewById(R.id.tips_content);
        vIcon = (ImageView) contentBox.findViewById(R.id.tips_icon);

        mPopupWindow.setContentView(contentView);

    }

    public void setIcon(int icon) {
        vIcon.setImageResource(icon);
    }

    public void setContent(String content) {
        vContent.setText(content);
    }

    public void setTitle(String title) {
        vTitle.setText(title);
    }

    public void setColor(int color){
        bgColor = context.getResources().getColor(color);
        contentBox.setBackgroundColor(bgColor);

        mTriangleView.setColor(bgColor);
    }

    public void setBorderColor(int borderColor) {
        int color = context.getResources().getColor(borderColor);
        mTriangleView.setBorderColor(color);
        mTriangleView.setBorderWidth(borderWidth);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setCornerRadius(radius);
        drawable.setStroke((int) borderWidth, color);
        if (bgColor != 0)
            drawable.setColor(bgColor);
        contentBox.setBackgroundDrawable(drawable);
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

        mPopupWindow.setAnimationStyle(android.R.style.Animation_Toast);
        mPopupWindow.showAtLocation(((Activity)context).getWindow().getDecorView(), Gravity.NO_GRAVITY,popWindowX,popWindowY);

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
                setHorizontalRightLayout(anchor);
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
            return locations[0] - contentViewWidth-dp2px(TRIANGLE_WIDTH);
        }else{
            //上下方向 锚点x-10% （rate=0.8为例）
            if (width == MATCH_PARENT){
                //让整个弹窗居中
                return (int) (ViewUtils.getScreenWH(context).x*(1-widthRate)/2);
            }else
                return locations[0]+(int) (anchorWidth*(1-widthRate)/2);
        }
    }

    boolean measure = false;
    private int getYOffset(final View anchor){

        if (mDirection == DOWN){
            //当内容为多行的时候，contentView.measure测量的高度不准确，需要减去一个行高；
            if (!measure){
                vContent.post(new Runnable() {
                    @Override
                    public void run() {
                        int conviewHeight = getTextViewHeight(vTitle) + getTextViewHeight(vContent);
                        popWindowY = (int) (locations[1]-conviewHeight - dp2px(TRIANGLE_HEIGHT) -borderWidth);

                        FrameLayout.LayoutParams posParams = (FrameLayout.LayoutParams) mTriangleView.getLayoutParams();

                        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER)
                            posParams.setMargins(anchor.getLeft()+anchor.getWidth()/2,conviewHeight,0,0);
                        else{
                            posParams.gravity = Gravity.CENTER_HORIZONTAL;
                            posParams.setMargins(0, conviewHeight+dp2px(TRIANGLE_WIDTH),0,0);
                        }

                        mPopupWindow.dismiss();
                        mPopupWindow.showAtLocation(((Activity)context).getWindow().getDecorView(),
                                Gravity.NO_GRAVITY,popWindowX,popWindowY);
                    }
                });
            }
            return -anchor.getMeasuredHeight()-contentViewHeight-dp2px(TRIANGLE_HEIGHT);
        }else if (mDirection == UP){
            return locations[1]+anchor.getMeasuredHeight();
        }else{
            return locations[1]-(contentViewHeight/2- anchor.getMeasuredHeight()/2);
        }

    }

    private int getTextViewHeight(TextView textView) {
        return textView.getLineCount() * textView.getLineHeight();
    }

    /*private int getYOffset(View anchor){

        if (mDirection == DOWN){
            //当内容为多行的时候，contentView.measure测量的高度不准确，需要减去一个行高；
            L.i("  locations[1]:"+locations[1]+"  contentHeight:"+contentView.getMeasuredHeight());
            return locations[1]-contentViewHeight-dp2px(TRIANGLE_HEIGHT);
        }else if (mDirection == UP){
            return locations[1]+anchor.getMeasuredHeight();
        }else{
            return locations[1]-(contentViewHeight/2- anchor.getMeasuredHeight()/2);
        }

    }*/

    //测量、设置popwindow和内容区的宽高，测量popwindow的xy坐标
    private void measurePopWindow(View anchor) {
        if (height != 0)
            mPopupWindow.setHeight(height);
        else
            mPopupWindow.setHeight(WRAP_CONTENT);

        if (width == MATCH_PARENT){
            mPopupWindow.setWidth((int) (ViewUtils.getScreenWH(context).x * widthRate));
        }else if (width > 0)
            mPopupWindow.setWidth(width );
        else{
            mPopupWindow.setWidth(WRAP_CONTENT);

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
        FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(dp2px(TRIANGLE_HEIGHT),dp2px(TRIANGLE_WIDTH));
        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER)
            posParams.setMargins(view.getLeft()+view.getWidth()/2,0,0,0);
        else
            posParams.gravity = Gravity.CENTER_HORIZONTAL;

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentBox.getLayoutParams();
        params.setMargins(0, (int) (dp2px(TRIANGLE_WIDTH)-borderWidth),0,0);

        contentView.addView(mTriangleView,posParams);
    }

    /**
     * 箭头向下布局
     */
    private void setVerticalDownLayout(View view) {
        mDirection = DOWN;
//        contentView.setOrientation(LinearLayout.VERTICAL);
        FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(dp2px(TRIANGLE_HEIGHT),dp2px(TRIANGLE_WIDTH));

        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER)
            posParams.setMargins(view.getLeft()+view.getWidth()/2,contentBox.getMeasuredHeight(),0,0);
        else
            posParams.gravity = Gravity.CENTER_HORIZONTAL;
//            posParams.setMargins(contentViewWidth/2,contentBox.getMeasuredHeight(),0,0);

        contentView.addView(mTriangleView,posParams);

    }

    /**
     * 箭头向左布局
     */
    private void setHorizontalLeftLayout(View view) {
        mDirection = LEFT;

//        contentView.setOrientation(LinearLayout.HORIZONTAL);
        FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(dp2px(TRIANGLE_WIDTH),dp2px(TRIANGLE_HEIGHT));
        //箭头Y坐标 = view的y+（view的高度-箭头高度）/2
        //marginTop = 箭头Y-popwindow y

        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER){
            int top = locations[1]+(view.getMeasuredHeight()-dp2px(TRIANGLE_HEIGHT)) / 2;
            posParams.setMargins(0, top-popWindowY,0,0);
        }
        else
            posParams.gravity = Gravity.CENTER_VERTICAL;

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) contentBox.getLayoutParams();
        params.setMargins((int) (dp2px(TRIANGLE_WIDTH)-borderWidth), 0,0,0);

        contentView.addView(mTriangleView,posParams);
    }

    /**
     * 箭头向右布局
     */
    private void setHorizontalRightLayout(View view) {

        mDirection = RIGHT;

        FrameLayout.LayoutParams posParams = new FrameLayout.LayoutParams(dp2px(TRIANGLE_WIDTH),dp2px(TRIANGLE_HEIGHT));

        if (mStrategy == TriangleView.PosStrategy.ANCHOR_CENTER){
            int top = locations[1]+(view.getMeasuredHeight()-dp2px(TRIANGLE_HEIGHT)) / 2;
            posParams.setMargins(0, top-popWindowY,0,0);
        } else{
            posParams.gravity = Gravity.CENTER_VERTICAL;
            posParams.setMargins((int) (contentViewWidth-borderWidth),0,0,0);
        }

        contentView.addView(mTriangleView,posParams);
    }


    public static class Builder{
        private double widthRate;
        private Context context;
        private TriangleView.Direction mDirection;
        private OnTipClickListener onClickListener;
        private TriangleView.PosStrategy mStrategy;
        private String title,content;
        private int color,titleColor,contentColor,icon,borderColor;
        private int width,height;
        private float borderWidth = 1f;
        public int radius;

        public Builder with(Context context){
            this.context = context;
            return this;
        }

        public Builder position(TriangleView.PosStrategy mStrategy){
            this.mStrategy = mStrategy;
            return this;
        }

        public Builder borderWidth(float width){
            this.borderWidth = width;
            return this;
        }
        public Builder rate(double rate){
            this.widthRate = rate;
            return this;
        }

        public Builder radius(int radius){
            this.radius = radius;
            return this;
        }

        public Builder icon(int icon){
            this.icon = icon;
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

        public Builder borderColor(int color){
            this.borderColor = color;
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

        public Builder setTitleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Builder setContentColor(int contentColor) {
            this.contentColor = contentColor;
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
