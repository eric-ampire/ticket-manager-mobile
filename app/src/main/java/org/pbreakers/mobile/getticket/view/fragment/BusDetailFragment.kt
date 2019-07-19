package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentBusDetailBinding
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.viewmodel.BusDetailViewModel


class BusDetailFragment : Fragment() {

    private val currentBus: Bus? by lazy {
        BusDetailFragmentArgs.fromBundle(arguments!!).bus
    }

    private val detailBusViewModel: BusDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding by lazy {
            FragmentBusDetailBinding.inflate(inflater).apply {
                bus = currentBus
                viewModel = detailBusViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        }

        detailBusViewModel.bus = currentBus!!

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_bus_menu, menu)
    }
}
