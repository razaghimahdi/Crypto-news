package com.apadanah.crypto_news.presentation.util.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.recyclerview.widget.*



abstract class BaseGenericListAdapter<T : Any>(
    @IdRes val layoutId: Int,
) : ListAdapter<T, BaseViewHolder>(BaseItemCallback<T>()) {

   private val TAG="AppDebug BaseGenericListAdapter"

    override fun getItemViewType(position: Int) = layoutId

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(
            viewType, parent, false
        )
        return BaseViewHolder(root as ViewGroup)
    }

    var list: List<T>
        get() = currentList
        set(value) = submitList(value)




    override fun getItemCount() = list.size

}


class BaseViewHolder(container: ViewGroup) : RecyclerView.ViewHolder(container)

class BaseItemCallback<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem.toString() == newItem.toString()

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
}