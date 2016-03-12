package net.runningcoder.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import net.runningcoder.R;


public class ListViewItemProgress extends ProgressBar {

//	private static final int DEFAULT_COLOR_UNREACHED_COLOR = 0x7f0a0000;
	private static final int DEFAULT_COLOR_REACHED_COLOR = 0xD7E3EB;
	private static final int DEFAULT_TEXT_SIZE = 20;//sp
	private static final int DEFAULT_ALPHA = 100;//sp

	private int reachedColor = 0;
	private int colorAlpha = 100;
	private int textSize;
	private int textColor;
	private boolean showNum = false;

	private Paint mPaint;//进度条画笔
	private Paint mTextPaint;//文字画笔
	public ListViewItemProgress(Context context) {
		this(context,null);
	}
	public ListViewItemProgress(Context context, AttributeSet attrs) {
		this(context,attrs,0);
	}

	public ListViewItemProgress(Context context, AttributeSet attrs,
								int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.item_progress_bar_attr, defStyleAttr, 0);
		
		//Color.parseColor("#D7E3EB")  R.color.default_reached_color
		reachedColor = a.getColor(R.styleable.item_progress_bar_attr_progressReachedColor,
				context.getResources().getColor(R.color.default_reached_color));
		colorAlpha = a.getInt(R.styleable.item_progress_bar_attr_reachedColorAlpha, DEFAULT_ALPHA);
		
		//getDimensionPixelSize 方法会自动把值转成dp或sp
		textSize = a.getDimensionPixelSize(R.styleable.item_progress_bar_attr_progressTextSize, DEFAULT_TEXT_SIZE);
		textColor = a.getColor(R.styleable.item_progress_bar_attr_progressTextColor,
				context.getResources().getColor(android.R.color.black));
		showNum = a.getBoolean(R.styleable.item_progress_bar_attr_showNum,showNum);

		mPaint = new Paint();
		mPaint.setColor(reachedColor);
		mPaint.setStyle(Style.FILL);//设置为实心
		mPaint.setAlpha(colorAlpha);
		
		mTextPaint = new Paint();
		mTextPaint.setColor(textColor);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		canvas.save();
		//画笔平移到指定paddingLeft， getHeight() / 2位置，注意以后坐标都为以此为0，0
		canvas.translate(getPaddingLeft(), getHeight() / 2);

		//当前进度和总值的比例
		float radio = getProgress() * 1.0f / getMax();
		//已到达的宽度
		float progressPosX = (int) (getWidth() * radio);

		// 绘制已到达的进度
//		float endX = progressPosX - mTextOffset / 2;
		if (progressPosX > 0)
		{
			String text = (getProgress()*100)/getMax()+"%";

			mPaint.setStrokeWidth(1);
			canvas.drawRect(0, -getHeight(), progressPosX, getHeight(), mPaint);
			if(showNum){
				//拿到字体的宽度和高度
				float textHeight = (mPaint.descent() + mPaint.ascent()) / 2;//.ascent：是baseline之上至字符最高处的距离;descent：是baseline之下至字符最低处的距离

				mTextPaint.setTextSize(textSize);
				canvas.drawText(text, progressPosX-(textSize*3), -textHeight, mTextPaint);
			}

		}

		canvas.restore();
	}

	public int getTextSize() {
		return textSize;
	}
	public void setTextSize(int textSize) {
		this.textSize = textSize;
	}



}
