package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ObservableField
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentBusDetailBinding
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.viewmodel.BusDetailViewModel


class BusDetailFragment : Fragment() {

    private val currentBus: Bus? by lazy {
        arguments?.getParcelable<Bus>("bus")
    }

    private val detailBusViewModel by lazy {
        ViewModelProviders.of(this).get<BusDetailViewModel>()
    }

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
            inflate<FragmentBusDetailBinding>(inflater, R.layout.fragment_bus_detail, container, false).apply {
                this.bus = currentBus
                this.viewModel = detailBusViewModel
                lifecycleOwner = viewLifecycleOwner
            }
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_bus_menu, menu)
    }
}
