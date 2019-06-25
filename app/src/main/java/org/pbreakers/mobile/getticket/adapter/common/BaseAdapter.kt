package org.pbreakers.mobile.getticket.adapter.common

import android.view.View
import androidx.databinding.ObservableInt
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import org.pbreakers.mobile.getticket.model.entity.Bus

abstract class BaseAdapter<T> : RecyclerView.Adapter<CustomViewHolder>() {
    val emptyViewVisibility = ObservableInt(View.VISIBLE)

    protected val data = arrayListOf<T>()

    override fun getItemCount(): Int = data.size

    fun updateVisibility() {
        if (itemCount == 0) {
            emptyViewVisibility.set(View.VISIBLE)
        } else {
            emptyViewVisibility.set(View.GONE)
        }
    }
}