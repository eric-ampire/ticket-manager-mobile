package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation.findNavController
import kotlinx.android.synthetic.main.fragment_bus.view.*
import org.jetbrains.anko.design.snackbar
import org.pbreakers.mobile.eduquelib.adapter.OnItemClickListener
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.BusAdapter
import org.pbreakers.mobile.getticket.databinding.FragmentBusBinding
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.util.LoadingState
import org.pbreakers.mobile.getticket.viewmodel.BusViewModel


class BusFragment : Fragment(), OnItemClickListener<Bus>, Observer<LoadingState> {

    private val busViewModel by lazy {
        ViewModelProviders.of(this).get<BusViewModel>().apply {
            busAdapter = BusAdapter(this@BusFragment)
            loadingState.observe(this@BusFragment, this@BusFragment)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding by lazy {
            inflate<FragmentBusBinding>(inflater, R.layout.fragment_bus, container, false).apply {
                viewModel = busViewModel
            }
        }

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hasOptionsMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewBus(view)

        busViewModel.init()

        view.swipeRefreshLayoutBus.setOnRefreshListener {
            busViewModel.init()
        }
    }

    override fun onChanged(loadingState: LoadingState) {

        when(loadingState.status) {
            LoadingState.Status.LOADED -> {
                view?.snackbar("Loaded")
                view?.swipeRefreshLayoutBus?.isRefreshing = false
            }

            LoadingState.Status.ERROR -> {
                view?.snackbar("Erreur : ${loadingState.message}")
                view?.swipeRefreshLayoutBus?.isRefreshing = false
            }

            LoadingState.Status.RUNNING -> {
                view?.swipeRefreshLayoutBus?.isRefreshing = true
            }
        }
    }

    override fun onClick(view: View, item: Bus, position: Int) {

        val bundle = bundleOf("bus" to item)
        findNavController(view).navigate(R.id.action_busFragment_to_busDetailFragment, bundle)
    }

    private fun addNewBus(view: View) {
//
//        view.fabAddBus.setOnClickListener {
//            findNavController(it).navigate(R.id.action_busFragment_to_busDetailFragment)
//        }
    }
}
