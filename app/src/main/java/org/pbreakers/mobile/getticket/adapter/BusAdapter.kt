package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.ItemBusBinding
import org.pbreakers.mobile.getticket.model.entity.Bus

class BusAdapter(private val listener: OnItemClickListener<Bus>) : PagedListAdapter<Bus, CustomViewHolder>(COMPARATOR)  {

    val isEmptyData = ObservableInt(View.VISIBLE)

    override fun onCurrentListChanged(currentList: PagedList<Bus>?) {
        if (itemCount == 0) {
            isEmptyData.set(View.VISIBLE)
        } else {
            isEmptyData.set(View.GONE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemBusBinding>(inflater, R.layout.item_bus, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBus = getItem(position) ?: return
        val binding = holder.binding as ItemBusBinding

        binding.bus = currentBus
        binding.itemBusLayout.setOnClickListener {
            listener.onClick(it, currentBus, position)
        }

        binding.imageButton.setOnClickListener {
            listener.onClickPopupButton(it, currentBus, position)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Bus>() {
            override fun areItemsTheSame(oldItem: Bus, newItem: Bus): Boolean = newItem.idBus == oldItem.idBus
            override fun areContentsTheSame(oldItem: Bus, newItem: Bus): Boolean = newItem == oldItem
        }
    }
}