package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.ItemTicketBinding
import org.pbreakers.mobile.getticket.model.entity.Billet


class BilletAdapter(private val listener: OnItemClickListener<Billet>) : RecyclerView.Adapter<CustomViewHolder>() {

    private val data = arrayListOf<Billet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemTicketBinding>(inflater, R.layout.item_bus, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBillet = data[position]
        val binding = holder.binding as ItemTicketBinding

        binding.lytCardView.setOnClickListener {
            listener.onClick(it, currentBillet, position)
        }

        binding.lytCardView.setOnLongClickListener {
            listener.onLongClick(it, currentBillet, position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int = data.size

    fun submitList(t: List<Billet>) {
        this.data.clear()
        this.data.addAll(t)

        notifyDataSetChanged()
    }
}