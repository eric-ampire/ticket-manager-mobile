package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.common.BaseAdapter
import org.pbreakers.mobile.getticket.adapter.common.CustomViewHolder
import org.pbreakers.mobile.getticket.adapter.common.OnItemClickListener
import org.pbreakers.mobile.getticket.databinding.ItemVoyageBinding
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.viewmodel.HomeViewModel

class VoyageAdapter(val listener: OnItemClickListener<Voyage>, val homeViewModel: HomeViewModel)
    : BaseAdapter<Voyage>(COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVoyageBinding>(inflater, R.layout.item_voyage, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = getItem(position) ?: return
        val binding = holder.binding as ItemVoyageBinding

        homeViewModel.findLieuById(item.idDestination).observeForever {
            holder.binding.desti = it
        }

        homeViewModel.findLieuById(item.idProvenance).observeForever {
            holder.binding.prove = it
        }

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