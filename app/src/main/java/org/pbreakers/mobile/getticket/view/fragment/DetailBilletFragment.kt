package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProviders
import org.koin.androidx.viewmodel.ext.android.viewModel

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentDetailBilletBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.viewmodel.DetailBilletViewModel


class DetailBilletFragment : Fragment() {

    private val currentBillet by lazy {
        arguments?.getParcelable<Billet>("billet")
    }

    private val detailBilletViewModel: DetailBilletViewModel by viewModel()

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

        detailBilletViewModel.run {
            billet = currentBillet!!
            init()
        }

        return binding.root
    }
}
