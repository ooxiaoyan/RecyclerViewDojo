package pers.xiaoyan.recyclerviewdojo.ui.recyclerview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pers.xiaoyan.recyclerviewdojo.R
import pers.xiaoyan.recyclerviewdojo.databinding.ItemStoreBinding
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.StoreDiffCallBack.Companion.KEY_ADDRESS
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.StoreDiffCallBack.Companion.KEY_IN_BUSINESS
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.StoreDiffCallBack.Companion.KEY_IS_FAVORITE
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.StoreDiffCallBack.Companion.KEY_NAME
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.StoreDiffCallBack.Companion.KEY_OPEN_TIME
import pers.xiaoyan.recyclerviewdojo.ui.recyclerview.StoreDiffCallBack.Companion.KEY_STATUS

class StoreAdapter : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    var data: MutableList<Store> = mutableListOf()
        set(value) {
            // 第一个参数是DiffUtil.Callback对象
            // 第二个参数代表是否检测Item的移动，改为false算法效率更高，按需设置，我们这里是true
            val diffResult = DiffUtil.calculateDiff(StoreDiffCallBack(data, value), true)
            diffResult.dispatchUpdatesTo(this@StoreAdapter)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemStoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        data.getOrNull(position)?.let {
            holder.bind(it)
        }
    }

    // 重写Adapter中的onBindViewHolder方法，获取getChangePayload的返回值
    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val payload = payloads[0] as Bundle
            data.getOrNull(position)
            payload.keySet().forEach {
                when (it) {
                    KEY_NAME -> {
                        holder.binding.tvTitle.text = payload.getString(it)
                    }
                    KEY_OPEN_TIME -> {
                        holder.binding.tvOpenTime.text = payload.getString(it)
                    }
                    KEY_IN_BUSINESS -> {
                        holder.binding.tvStatusTag.apply {
                            val inBusiness = payload.getBoolean(it)
                            background = ContextCompat.getDrawable(
                                holder.binding.tvStatusTag.context,
                                if (inBusiness) R.drawable.bg_status_tag_normal else R.drawable.bg_status_tag_disable
                            )
                            setTextColor(
                                resources.getColor(if (inBusiness) R.color.white else R.color.tertiary_color)
                            )
                        }
                    }
                    KEY_ADDRESS -> {
                        holder.binding.tvAddress.text = payload.getString(it)
                    }
                    KEY_IS_FAVORITE -> {
                        holder.binding.ivFavorite.isSelected = payload.getBoolean(it)
                    }
                    KEY_STATUS -> {
                        holder.binding.tvStatusTag.text = payload.getString(it)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(
        val binding: ItemStoreBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store) {
            binding.store = store
            binding.ivFavorite.isSelected = store.isFavorite
            binding.executePendingBindings()
        }
    }
}