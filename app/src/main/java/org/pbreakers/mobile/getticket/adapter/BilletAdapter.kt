package org.pbreakers.mobile.getticket.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.common.BaseAdapter
import org.pbreakers.mobile.getticket.adapter.common.CustomViewHolder
import org.pbreakers.mobile.getticket.adapter.common.OnItemClickListener
import org.pbreakers.mobile.getticket.databinding.ItemTicketBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.viewmodel.BilletViewModel


class BilletAdapter(private val listener: OnItemClickListener<Billet>) : BaseAdapter<Billet>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = inflate<ItemTicketBinding>(inflater, R.layout.item_ticket, parent, false)

        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentBillet = data[position]
        val binding = holder.binding as ItemTicketBinding

        binding.ticket = currentBillet

        val db = FirebaseFirestore.getInstance()
        val etatRef = db.collection("etats").document(currentBillet.idEtat)

        etatRef.get().addOnSuccessListener {
            val currentState = it.toObject(Etat::class.java)
            binding.etat = currentState
        }

        binding.lytCardView.setOnClickListener {
            listener.onClick(it, currentBillet, position)
        }

        binding.btnMore.setOnClickListener {
            listener.onClickPopupButton(it, currentBillet, position)
        }
    }

    fun submitList(billets: List<Billet>) {
        this.data.clear()
        this.data.addAll(billets)
        notifyDataSetChanged()
        super.updateVisibility()
    }
}