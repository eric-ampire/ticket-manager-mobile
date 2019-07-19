package org.pbreakers.mobile.getticket.view.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation.findNavController
import com.kinda.alert.KAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_bus.view.*
import org.jetbrains.anko.design.snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.BusAdapter
import org.pbreakers.mobile.getticket.adapter.common.OnItemClickListener
import org.pbreakers.mobile.getticket.databinding.FragmentBusBinding
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.util.LoadingState
import org.pbreakers.mobile.getticket.view.activity.MainActivity
import org.pbreakers.mobile.getticket.viewmodel.BusViewModel


class BusFragment : Fragment(), OnItemClickListener<Bus>, Observer<LoadingState> {

    private val busViewModel: BusViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding by lazy {
            inflate<FragmentBusBinding>(inflater, R.layout.fragment_bus, container, false).apply {
                viewModel = busViewModel
            }
        }

        busViewModel.run {
            adapter = BusAdapter(this@BusFragment)
            loadingState.observe(this@BusFragment, this@BusFragment)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        busViewModel.init()

        view.swipeRefreshLayoutBus.setOnRefreshListener {
            busViewModel.init()
        }

        val mainActivity = activity as MainActivity

        busViewModel.getLoadingState().observe(this, Observer {
            when(it.status) {
                LoadingState.Status.ERROR, LoadingState.Status.LOADED -> {
                    mainActivity.hideProgressBar()
                }

                else -> {
                    mainActivity.showProgressBar()
                }
            }
        })
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

    override fun onClickPopupButton(view: View, item: Bus, position: Int) {
        createPopupMenu(view, item)
    }

    override fun onClick(view: View, item: Bus, position: Int) {
        findNavController(view).navigate(BusFragmentDirections.actionBusFragmentToBusDetailFragment(item))
    }

    private fun createPopupMenu(view: View, item: Bus) {
        val popupMenu = PopupMenu(view.context, view).apply {
            inflate(R.menu.edit_delete_context_menu)
        }

        popupMenu.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.menu_item_delete -> {
                    showDeleteConfirmation(view, item)
                    true
                }

                R.id.menu_item_edit -> {
                    val bundle = bundleOf("bus" to item)
                    findNavController(view).navigate(R.id.action_busFragment_to_modifierBusFragment, bundle)
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun showDeleteConfirmation(view: View, item: Bus) {
        val dialog = KAlertDialog(view.context, KAlertDialog.WARNING_TYPE).apply {
            progressHelper.barColor = Color.parseColor("#A5DC86")
            titleText = "Suppression"
            contentText = "Etes vous sur de vouloir supprimer ce bus ?"
            setCanceledOnTouchOutside(true)
        }

        dialog.setConfirmClickListener {
            // Delete item from repository
            if (dialog.alerType == KAlertDialog.SUCCESS_TYPE || dialog.alerType == KAlertDialog.ERROR_TYPE) {
                dialog.dismissWithAnimation()
            } else {
                deleteBus(dialog, item)
            }
        }

        dialog.show()
    }

    private fun deleteBus(dialog: KAlertDialog, item: Bus) {
        dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        busViewModel.removeBus(item)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    dialog.contentText = "Opération s'est derouler avec success !"
                    dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                } else {
                    dialog.contentText = it.exception?.message ?: "Unknow error !"
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            }

    }
}
