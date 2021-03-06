package org.pbreakers.mobile.getticket.view.fragment


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
import com.google.firebase.firestore.FirebaseFirestore
import com.kinda.alert.KAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.BilletAdapter
import org.pbreakers.mobile.getticket.adapter.common.OnItemClickListener
import org.pbreakers.mobile.getticket.databinding.FragmentBilletBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.util.LoadingState
import org.pbreakers.mobile.getticket.view.activity.MainActivity
import org.pbreakers.mobile.getticket.viewmodel.BilletViewModel


class BilletFragment : Fragment(), OnItemClickListener<Billet> {

    private lateinit var binding: FragmentBilletBinding
    private val billetViewModel by viewModel<BilletViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBilletBinding.inflate(inflater).apply {
            viewModel = billetViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        val billetAdapter = BilletAdapter(this@BilletFragment)
        binding.ticketList.adapter = billetAdapter

        billetViewModel.tickets.observe(this, Observer {
            billetAdapter.submitList(it)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as MainActivity

        billetViewModel.getLoadingState().observe(this, Observer {
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onClick(view: View, item: Billet, position: Int) {
        findNavController(view).navigate(BilletFragmentDirections.actionBilletFragmentToDetailBilletFragment(item))
    }

    override fun onClickPopupButton(view: View, item: Billet, position: Int) {
        val popupMenu = PopupMenu(context, view).apply {
            inflate(R.menu.edit_delete_context_menu)
            show()
        }

        popupMenu.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when(it.itemId) {
                R.id.menu_item_edit -> {
                    findNavController(view).navigate(BilletFragmentDirections.actionBilletFragmentToModifierBilletFragment(item))
                    true
                }

                R.id.menu_item_delete -> {
                    showDeleteTicketConfirmation(view, item)
                    true
                }

                else -> false
            }
        }
    }

    private fun showDeleteTicketConfirmation(view: View, billet: Billet) {
        val dialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE).apply {
            contentText = "Etes vous sur de vouloir supprimer"
            titleText = "Suppression"
            show()
        }

        dialog.setConfirmClickListener {
            if (it.alerType == KAlertDialog.SUCCESS_TYPE || it.alerType == KAlertDialog.ERROR_TYPE) {
                it.dismissWithAnimation()
            } else {
                billetViewModel.deleteBillet(billet)
                it.changeAlertType(KAlertDialog.SUCCESS_TYPE)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
