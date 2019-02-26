package net.runningcoder.adapter.helper;

/**
 * Author： chenchongyu
 * Date: 2019/2/26
 * Description:
 */
public interface AdapterItemOp {
    /**
     * 交换条目
     *
     * @param from 起点
     * @param to 终点
     */
    void onItemMove(int from, int to);

    /**
     * 删除条目
     *
     * @param position 位置
     */
    void onItemDelete(int position);
}
