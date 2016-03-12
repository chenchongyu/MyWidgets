package net.runningcoder.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

public class AlphaButton extends Button {

	private boolean changeAlphaOnPress = false;

	private Drawable mBgDrawable;

	private int originalAlpha = 0xff;

	public AlphaButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public AlphaButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public AlphaButton(Context context) {
		super(context);
		init();
	}

	private void init() {

		Drawable background = getBackground();

		if (background != null && !(background instanceof StateListDrawable)) {
			changeAlphaOnPress = true;
			mBgDrawable = background;
			// originalAlpha = background.getAlpha();
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				if (changeAlphaOnPress)
					mBgDrawable.setAlpha((int) (originalAlpha * (0.7f)));
				break;
			case MotionEvent.ACTION_UP:
			case MotionEvent.ACTION_CANCEL:
				if (changeAlphaOnPress)
					mBgDrawable.setAlpha(originalAlpha);
				break;
			default:
				break;
			}

			return super.onTouchEvent(event);
	}
}
