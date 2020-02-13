package net.runningcoder.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Author： chenchongyu
 * Email: chenchongyu@didichuxing.com
 * Date: 2017/5/12
 * Description:
 */

public class ViewUtils {
    private static Toast toast;

    public static Point getScreenWH(Context context) {
        Point point = new Point();
        WindowManager wm = ((Activity) context).getWindowManager();
        wm.getDefaultDisplay().getSize(point);

        return point;
    }

    public static void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    public static void showToast(Context context, String content) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        } else {
            toast.setText(content);
        }

        toast.show();
    }

    public static int dp2px(Context context, float dp) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
