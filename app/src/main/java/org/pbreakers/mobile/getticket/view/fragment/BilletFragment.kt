package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation.findNavController
import com.kinda.alert.KAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.BilletAdapter
import org.pbreakers.mobile.getticket.adapter.common.OnItemClickListener
import org.pbreakers.mobile.getticket.databinding.FragmentBilletBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.viewmodel.BilletViewModel


class BilletFragment : Fragment(), OnItemClickListener<Billet> {

    private lateinit var binding: FragmentBilletBinding
    private val billetViewModel by lazy {
        ViewModelProviders.of(this).get<BilletViewModel>().apply {
            adapter = BilletAdapter(this@BilletFragment, this)
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
        val popupMenu = PopupMenu(context, view).apply {
            inflate(R.menu.edit_delete_context_menu)
            show()
        }

        popupMenu.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when(it.itemId) {
                R.id.menu_item_edit -> {
                    val bundle = bundleOf("billet" to item)
                    findNavController(view).navigate(R.id.action_billetFragment_to_modifierBilletFragment, bundle)
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
                deleteBillet(view, billet, dialog)
            }
        }
    }

    private fun deleteBillet(view: View, billet: Billet, dialog: KAlertDialog) {

        billetViewModel.deleteBillet(billet)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    dialog.contentText = ""
                    dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                }

                override fun onSubscribe(d: Disposable) {
                    dialog.contentText = "Suppression en cour"
                    dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
                }

                override fun onError(e: Throwable) {
                    dialog.contentText = e.message
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
