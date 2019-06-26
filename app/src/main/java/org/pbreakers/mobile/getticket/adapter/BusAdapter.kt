package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.google.firebase.firestore.FirebaseFirestore
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.common.BaseAdapter
import org.pbreakers.mobile.getticket.adapter.common.CustomViewHolder
import org.pbreakers.mobile.getticket.adapter.common.OnItemClickListener
import org.pbreakers.mobile.getticket.databinding.ItemBusBinding
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.viewmodel.BusViewModel

class BusAdapter(private val listener: OnItemClickListener<Bus>) : BaseAdapter<Bus>(DIFF)  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemBusBinding>(inflater, R.layout.item_bus, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBus = getItem(position) ?: return
        val binding = holder.binding as ItemBusBinding

        binding.bus = currentBus

        val db = FirebaseFirestore.getInstance()
        val agenceRef = db.collection("agences").document(currentBus.idAgence)

        agenceRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                binding.agence = it.result?.toObject(Agence::class.java) ?: return@addOnCompleteListener
            } else {
                return@addOnCompleteListener
            }
        }

        binding.itemBusLayout.setOnClickListener {
            listener.onClick(it, currentBus, position)
        }

        binding.imageButton.setOnClickListener {
            listener.onClickPopupButton(it, currentBus, position)
        }
    }

    companion object DIFF : DiffUtil.ItemCallback<Bus>() {
        override fun areItemsTheSame(oldItem: Bus, newItem: Bus): Boolean{
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Bus, newItem: Bus): Boolean {
            return oldItem.idBus == newItem.idBus
        }
    }
}