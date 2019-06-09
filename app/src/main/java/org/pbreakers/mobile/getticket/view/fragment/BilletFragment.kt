package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import org.jetbrains.anko.design.snackbar

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.BilletAdapter
import org.pbreakers.mobile.getticket.adapter.OnItemClickListener
import org.pbreakers.mobile.getticket.databinding.FragmentBilletBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.viewmodel.BilletViewModel


class BilletFragment : Fragment(), OnItemClickListener<Billet> {

    private lateinit var binding: FragmentBilletBinding
    private val billetViewModel by lazy {
        ViewModelProviders.of(this).get<BilletViewModel>().apply {
            adapter = BilletAdapter(this@BilletFragment)
        }
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

    override fun onClick(view: View, item: Billet, position: Int) {
        val bundle = bundleOf("billet" to item)
        findNavController(view).navigate(R.id.action_billetFragment_to_detailBilletFragment, bundle)
    }

    override fun onClickPopupButton(view: View, item: Billet, position: Int) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
