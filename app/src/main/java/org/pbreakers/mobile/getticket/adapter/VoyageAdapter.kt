package org.pbreakers.mobile.getticket.adapter

import androidx.recyclerview.widget.DiffUtil
import org.pbreakers.mobile.getticket.model.entity.Voyage

class VoyageAdapter {





    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Voyage>() {
            override fun areItemsTheSame(oldItem: Voyage, newItem: Voyage): Boolean = newItem.id == oldItem.id
            override fun areContentsTheSame(oldItem: Voyage, newItem: Voyage): Boolean = newItem === oldItem
        }
    }
}