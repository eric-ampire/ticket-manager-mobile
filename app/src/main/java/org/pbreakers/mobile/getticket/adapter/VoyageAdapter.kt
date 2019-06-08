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
import org.pbreakers.mobile.getticket.databinding.ItemVoyageBinding
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.entity.Voyage

class VoyageAdapter(val listener: OnItemClickListener<Voyage>) : PagedListAdapter<Voyage, CustomViewHolder>(COMPARATOR) {

    val isEmptyData = ObservableInt(View.VISIBLE)

    override fun onCurrentListChanged(currentList: PagedList<Voyage>?) {
        if (itemCount == 0) {
            isEmptyData.set(View.VISIBLE)
        } else {
            isEmptyData.set(View.GONE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVoyageBinding>(inflater, R.layout.item_voyage, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = getItem(position) ?: return
        val binding = holder.binding as ItemVoyageBinding

        holder.binding.voyage = item

        binding.root.setOnClickListener {
            listener.onClick(it, item, position)
        }

        binding.btnMore.setOnClickListener {
            listener.onClickPopupButton(it, item, position)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Voyage>() {
            override fun areItemsTheSame(oldItem: Voyage, newItem: Voyage): Boolean = newItem.idVoyage == oldItem.idVoyage
            override fun areContentsTheSame(oldItem: Voyage, newItem: Voyage): Boolean = newItem == oldItem
        }
    }
}