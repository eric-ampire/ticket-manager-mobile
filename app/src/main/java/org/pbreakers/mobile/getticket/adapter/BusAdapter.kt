package org.pbreakers.mobile.getticket.adapter

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.xwray.groupie.ViewHolder
import org.pbreakers.mobile.eduquelib.adapter.OnItemClickListener
import org.pbreakers.mobile.getticket.model.entity.Bus

class BusAdapter(val itemClickListener: OnItemClickListener<Bus>) : PagedListAdapter<Bus, ViewHolder>(COMPARATOR)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Bus>() {
            override fun areItemsTheSame(oldItem: Bus, newItem: Bus): Boolean = newItem.idBus == oldItem.idBus
            override fun areContentsTheSame(oldItem: Bus, newItem: Bus): Boolean = newItem == oldItem
        }
    }
}