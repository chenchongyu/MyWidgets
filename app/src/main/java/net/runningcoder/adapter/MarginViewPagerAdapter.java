package net.runningcoder.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author： chenchongyu
 * Date: 2017/12/21
 * Description:
 */

public class MarginViewPagerAdapter extends FragmentStatePagerAdapter {
    private static float MIN_SCALE = 0.85f;

    private static float MIN_ALPHA = 0.5f;

    private List<Fragment> fragments = new ArrayList<>();
    public MarginViewPagerAdapter(FragmentManager fm,List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = fragments.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
