package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ObservableInt
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.ItemTicketBinding
import org.pbreakers.mobile.getticket.model.entity.Billet


class BilletAdapter(private val listener: OnItemClickListener<Billet>)
    : PagedListAdapter<Billet, CustomViewHolder>(COMPARATOR) {

    val isEmptyData = ObservableInt(View.VISIBLE)

    override fun onCurrentListChanged(currentList: PagedList<Billet>?) {
        if (itemCount == 0) {
            isEmptyData.set(View.VISIBLE)
        } else {
            isEmptyData.set(View.GONE)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflate<ItemTicketBinding>(inflater, R.layout.item_ticket, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBillet = getItem(position) ?: return
        val binding = holder.binding as ItemTicketBinding

        binding.ticket = currentBillet

        binding.lytCardView.setOnClickListener {
            listener.onClick(it, currentBillet, position)
        }

        binding.btnMore.setOnClickListener {
            listener.onClickPopupButton(it, currentBillet, position)
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Billet>() {
            override fun areItemsTheSame(oldItem: Billet, newItem: Billet): Boolean = newItem.idBillet == oldItem.idBillet
            override fun areContentsTheSame(oldItem: Billet, newItem: Billet): Boolean = newItem == oldItem
        }
    }
}