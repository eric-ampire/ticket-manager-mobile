package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableInt
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.common.BaseAdapter
import org.pbreakers.mobile.getticket.adapter.common.CustomViewHolder
import org.pbreakers.mobile.getticket.adapter.common.OnItemClickListener
import org.pbreakers.mobile.getticket.databinding.ItemVoyageBinding
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.entity.Lieu
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.viewmodel.HomeViewModel

class VoyageAdapter(val listener: OnItemClickListener<Voyage>) : BaseAdapter<Voyage>(DIFF) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ItemVoyageBinding>(inflater, R.layout.item_voyage, parent, false)

        return CustomViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val item = getItem(position) ?: return
        val binding = holder.binding as ItemVoyageBinding

        val db = FirebaseFirestore.getInstance()
        val destRef = db.collection("lieux").document(item.idDestination)
        val provRef = db.collection("lieux").document(item.idProvenance)

        destRef.get().addOnSuccessListener {
            holder.binding.desti = it.toObject(Lieu::class.java)
        }

        provRef.get().addOnSuccessListener {
            holder.binding.prove = it.toObject(Lieu::class.java)
        }

        holder.binding.voyage = item

        binding.root.setOnClickListener {
            listener.onClick(it, item, position)
        }

        binding.btnMore.setOnClickListener {
            listener.onClickPopupButton(it, item, position)
        }
    }

    companion object DIFF : DiffUtil.ItemCallback<Voyage>() {
        override fun areItemsTheSame(oldItem: Voyage, newItem: Voyage): Boolean{
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Voyage, newItem: Voyage): Boolean {
            return oldItem.idVoyage == newItem.idVoyage
        }
    }
}