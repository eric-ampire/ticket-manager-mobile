package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentBusBinding
import org.pbreakers.mobile.getticket.viewmodel.BusViewModel


class BusFragment : Fragment() {

    private val busViewModel by lazy {
        ViewModelProviders.of(this).get<BusViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentBusBinding>(inflater, R.layout.fragment_bus, container, false)
        binding.viewModel = busViewModel

        busViewModel.init()

        return binding.root
    }
}
