package net.runningcoder.bean;

import android.support.annotation.Nullable;

/**
 * Author： chenchongyu
 * Date: 2019-07-19
 * Description:
 */
public class GroupItem {
    @Nullable
    public String groupName;
    public String text;

    public boolean animateShown;

    public GroupItem(String groupName, String text) {
        this.groupName = groupName;
        this.text = text;
    }
}
