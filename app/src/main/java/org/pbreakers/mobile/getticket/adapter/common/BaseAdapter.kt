package org.pbreakers.mobile.getticket.adapter.common

import android.view.View
import androidx.databinding.ObservableInt
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil

abstract class BaseAdapter<T>(diff: DiffUtil.ItemCallback<T>) : PagedListAdapter<T, CustomViewHolder>(diff) {
    val emptyViewVisibility = ObservableInt(View.VISIBLE)

    override fun onCurrentListChanged(currentList: PagedList<T>?) {
        if (itemCount == 0) {
            emptyViewVisibility.set(View.VISIBLE)
        } else {
            emptyViewVisibility.set(View.GONE)
        }
    }
}