package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.ViewHolder
import org.pbreakers.mobile.eduquelib.adapter.OnItemClickListener
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.ItemBusBinding
import org.pbreakers.mobile.getticket.databinding.ItemVoyageBinding
import org.pbreakers.mobile.getticket.model.entity.Voyage

class VoyageAdapter(val listener: OnItemClickListener<Voyage>) : PagedListAdapter<Voyage, CustomViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVoyageBinding>(inflater, R.layout.item_voyage, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = getItem(position) ?: return
        val binding = holder.binding as ItemVoyageBinding

        //holder.binding.bus = currentBus
        binding.root.setOnClickListener {
            listener.onClick(it, item, position)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Voyage>() {
            override fun areItemsTheSame(oldItem: Voyage, newItem: Voyage): Boolean = newItem.idVoyage == oldItem.idVoyage
            override fun areContentsTheSame(oldItem: Voyage, newItem: Voyage): Boolean = newItem == oldItem
        }
    }
}