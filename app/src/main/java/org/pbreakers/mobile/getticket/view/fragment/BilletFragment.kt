package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentBilletBinding
import org.pbreakers.mobile.getticket.viewmodel.BilletViewModel


class BilletFragment : Fragment() {

    private lateinit var binding: FragmentBilletBinding
    private val billetViewModel by lazy {
        ViewModelProviders.of(this).get<BilletViewModel>()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate<FragmentBilletBinding>(inflater, R.layout.fragment_billet, container, false).apply {
            viewModel = billetViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
