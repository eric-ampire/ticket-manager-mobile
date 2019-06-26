package org.pbreakers.mobile.getticket.adapter.common

import android.view.View
import androidx.databinding.ObservableInt
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.pbreakers.mobile.getticket.model.entity.Bus

abstract class BaseAdapter<T>(itemDiffCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, CustomViewHolder>(itemDiffCallback) {
    val emptyViewVisibility = ObservableInt(View.VISIBLE)

    override fun onCurrentListChanged(previousList: MutableList<T>, currentList: MutableList<T>) {
        super.onCurrentListChanged(previousList, currentList)

        if (itemCount == 0) {
            emptyViewVisibility.set(View.VISIBLE)
        } else {
            emptyViewVisibility.set(View.GONE)
        }
    }
}