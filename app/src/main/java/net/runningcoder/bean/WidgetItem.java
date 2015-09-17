package net.runningcoder.bean;

/**
 * Created by Administrator on 2015/9/16.
 */
public class WidgetItem {
    public int id;
    public String name;
    public String desc;

    public WidgetItem() {}

    public WidgetItem(int id, String name,String desc) {
        this.desc = desc;
        this.id = id;
        this.name = name;
    }
}
