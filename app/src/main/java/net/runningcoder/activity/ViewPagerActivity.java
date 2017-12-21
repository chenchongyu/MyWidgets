package net.runningcoder.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import net.runningcoder.BasicActivity;
import net.runningcoder.R;
import net.runningcoder.adapter.MarginViewPagerAdapter;
import net.runningcoder.transformer.ZoomOut3PageTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： chenchongyu
 * Email: chenchongyu@didichuxing.com
 * Date: 2017/11/2
 * Description:
 */

public class ViewPagerActivity extends BasicActivity {
    ViewPager mMarginViewPager;
    MarginViewPagerAdapter pagerAdapter;
    List<Fragment> list = new ArrayList();

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        mMarginViewPager = $(R.id.margin_vp);
        mMarginViewPager.setPageMargin(20);
        mMarginViewPager.setPageTransformer(true, new ZoomOut3PageTransformer());
        initData();
    }

    private void initData() {
        list.add(ImageFragment.newInstance("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513856046715&di=00193bc5b4bd4a333fe743ab182fc56f&imgtype=0&src=http%3A%2F%2Fimg237.ph.126.net%2F4UHQwpAaYyZ3p61VtErOeQ%3D%3D%2F1376694111092846024.jpg", true));
        list.add(ImageFragment.newInstance("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513856046715&di=95d2b2ebe486b33e48769b79c3eaa254&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fforum%2Fw%3D580%2Fsign%3Dfe84214135fa828bd1239debcd1e41cd%2F980e6609c93d70cf797d0f49fedcd100bba12b9b.jpg", true));
        list.add(ImageFragment.newInstance("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513856046714&di=8ef041f558e215d50ce26e136ce1396e&imgtype=0&src=http%3A%2F%2Fp2.gexing.com%2FG1%2FM00%2F82%2F0A%2FrBACJ1V0WymgEVySAAiyPgr9wKM301_600x.jpg", true));
        list.add(ImageFragment.newInstance("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513855956982&di=77233b479766f4087f8858278bf7d3f1&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F016bf257b2c0930000012e7e202fdc.jpg%40900w_1l_2o_100sh.jpg", true));
        list.add(ImageFragment.newInstance("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2133214656,3573702769&fm=27&gp=0.jpg", true));
        pagerAdapter = new MarginViewPagerAdapter(getSupportFragmentManager(), list);
        mMarginViewPager.setAdapter(pagerAdapter);

    }


    @Override
    public int getContentViewID() {
        return R.layout.activity_recycleview;
    }
}
