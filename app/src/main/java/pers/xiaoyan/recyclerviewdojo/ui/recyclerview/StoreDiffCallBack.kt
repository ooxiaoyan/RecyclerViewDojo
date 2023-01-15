package pers.xiaoyan.recyclerviewdojo.ui.recyclerview

import android.os.Bundle
import androidx.recyclerview.widget.DiffUtil

// 定义了一些用来比较新老Item是否相等的契约、规则
class StoreDiffCallBack(
    private val oldStoreList: MutableList<Store>,
    private val newStoreList: MutableList<Store>
) : DiffUtil.Callback() {

    companion object {
        const val KEY_NAME = "name"
        const val KEY_OPEN_TIME = "openTime"
        const val KEY_IN_BUSINESS = "inBusiness"
        const val KEY_ADDRESS = "address"
        const val KEY_IS_FAVORITE = "isFavorite"
        const val KEY_STATUS = "status"
    }

    override fun getOldListSize(): Int = oldStoreList.size

    override fun getNewListSize(): Int = newStoreList.size

    /**
     * Called by the DiffUtil to decide whether two object represent the same Item.
     * 被DiffUtil调用，用来判断 两个对象是否是相同的Item。
     * For example, if your items have unique ids, this method should check their id equality.
     * 例如，如果你的Item有唯一的id字段，这个方法就用来判断id是否相等。
     *
     * Returns:
     * True if the two items represent the same object or false if they are different.
     */
    // 返回两个项是否相等，这里通常比较id之类的唯一值
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldStoreList.getOrNull(oldItemPosition)?.id == newStoreList.getOrNull(newItemPosition)?.id
    }

    /**
     * Called by the DiffUtil when it wants to check whether two items have the same data.
     * 被DiffUtil调用，用来检查两个item是否含有相同的数据
     * DiffUtil uses this information to detect if the contents of an item has changed.
     * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
     * DiffUtil uses this method to check equality instead of Object.equals(Object) so that you
     * can change its behavior depending on your UI. For example, if you are using DiffUtil with a
     * RecyclerView.Adapter, you should return whether the items' visual representations are the same.
     * DiffUtil 用这个方法替代equals方法去检查是否相等，所以你可以根据你的UI去改变它的返回值。
     * 例如，如果你用RecyclerView.Adapter 配合DiffUtil使用，你需要返回Item的视觉表现是否相同。
     * This method is called only if areItemsTheSame(int, int) returns true for these items.
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     *
     * Returns:
     * True if the contents of the items are the same or false if they are different.
     */
    // 返回两个项的内容是否相等，这里通常比较数据对象的各个字段是否相等
    // 比较新旧数据（主要是UI展示内容）是否相同
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldStoreList.getOrNull(oldItemPosition)
            ?.equals(newStoreList.getOrNull(newItemPosition)) ?: false
    }

    // 当areItemsTheSame返回true，areContentsTheSame返回false时，这个方法会被调用。
    // 因此只需要通过该方法告诉adapter需要更新的数据。然后在实现了DiffUtil.Callback()的类中重写该方法，传入更新了的值。
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val bundle = Bundle()
        val new = newStoreList[newItemPosition]
        val old = oldStoreList[oldItemPosition]
        if (new.name != old.name) {
            bundle.putString(KEY_NAME, new.name)
        }
        if (new.openTime != old.openTime) {
            bundle.putString(KEY_OPEN_TIME, new.openTime)
        }
        if (new.inBusiness != old.inBusiness) {
            bundle.putBoolean(KEY_IN_BUSINESS, new.inBusiness)
        }
        if (new.address != old.address) {
            bundle.putString(KEY_ADDRESS, new.address)
        }
        if (new.isFavorite != old.isFavorite) {
            bundle.putBoolean(KEY_IS_FAVORITE, new.isFavorite)
        }
        if (new.status != old.status) {
            bundle.putString(KEY_STATUS, new.status)
        }
        return if (bundle.size() == 0) null else bundle
    }
}