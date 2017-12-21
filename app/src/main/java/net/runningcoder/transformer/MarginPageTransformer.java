package net.runningcoder.transformer;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Author： chenchongyu
 * Date: 2017/12/21
 * Description:
 * 当前选中的 item 的 position 永远是 0 ，被选中 item 的前一个为 -1，被选中 item 的后一个为 1。
 * 前后 item position 为 -1 和 1 的前提是你没有给 ViewPager 设置 pageMargin。
 如果你设置了 pageMargin，前后 item 的 position 需要分别加上（或减去，前减后加）一个偏移量
 （偏移量的计算方式为 pageMargin / pageWidth）。
  */

public class MarginPageTransformer implements ViewPager.PageTransformer {
    final float SCALE_MAX = 0.8f;
    final float ALPHA_MAX = 0.5f;

    @Override
    public void transformPage(View page, float position) {
        //我们给不同状态的页面设置不同的效果
        //通过position的值来分辨页面所处于的状态

        float scale = (position < 0)
                ? ((1 - SCALE_MAX) * position + 1)
                : ((SCALE_MAX - 1) * position + 1);
        float alpha = (position < 0)
                ? ((1 - ALPHA_MAX) * position + 1)
                : ((ALPHA_MAX - 1) * position + 1);
        if (position < 0) {
            page.setPivotX(page.getWidth());
            page.setPivotY(page.getHeight()/2);
        } else {
            page.setPivotX(0);
            page.setPivotY(page.getHeight()/2);
        }
        page.setScaleX(scale);
        page.setScaleY(scale);
        page.setAlpha(Math.abs(alpha));
    }
}
