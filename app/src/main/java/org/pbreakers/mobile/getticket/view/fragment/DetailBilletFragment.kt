package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProviders

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentDetailBilletBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.viewmodel.DetailBilletViewModel


class DetailBilletFragment : Fragment() {

    private val currentBillet by lazy {
        arguments?.getParcelable<Billet>("billet")
    }

    private val detailBilletViewModel by lazy {
        ViewModelProviders.of(this).get(DetailBilletViewModel::class.java).apply {
            billet = currentBillet!!
            init()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding by lazy {
            inflate<FragmentDetailBilletBinding>(inflater, R.layout.fragment_detail_billet, container, false).apply {
                viewModel = detailBilletViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        }

        return binding.root
    }
}
