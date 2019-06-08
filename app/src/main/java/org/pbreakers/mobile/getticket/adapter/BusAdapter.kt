package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.ItemBusBinding
import org.pbreakers.mobile.getticket.model.entity.Bus

class BusAdapter(private val listener: OnItemClickListener<Bus>) : RecyclerView.Adapter<CustomViewHolder>()  {

    private val data = arrayListOf<Bus>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemBusBinding>(inflater, R.layout.item_bus, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBus = data[position]
        val binding = holder.binding as ItemBusBinding

        binding.bus = currentBus
        binding.itemBusLayout.setOnClickListener {
            listener.onClick(it, currentBus, position)
        }

        binding.imageButton.setOnClickListener {
            listener.onClickPopupButton(it, currentBus, position)
        }
    }

    override fun getItemCount(): Int = data.size

    fun submitList(t: List<Bus>) {
        this.data.clear()
        this.data.addAll(t)

        notifyDataSetChanged()
    }
}