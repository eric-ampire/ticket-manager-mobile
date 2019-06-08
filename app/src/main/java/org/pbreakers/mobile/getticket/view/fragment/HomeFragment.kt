package org.pbreakers.mobile.getticket.view.fragment


import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.kinda.alert.KAlertDialog
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.pbreakers.mobile.getticket.adapter.OnItemClickListener
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.VoyageAdapter
import org.pbreakers.mobile.getticket.databinding.FragmentHomeBinding
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.viewmodel.HomeViewModel


class HomeFragment : Fragment(), OnItemClickListener<Voyage> {


    private val homeViewModel by lazy {
        ViewModelProviders.of(this).get<HomeViewModel>().apply {
            this.adapter = VoyageAdapter(this@HomeFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false).apply {
            this.viewModel = homeViewModel
        }

        homeViewModel.init()

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

    override fun onClick(view: View, item: Voyage, position: Int) {
        showVoyageDetailFragment(item, view)
    }

    private fun showVoyageDetailFragment(item: Voyage, view: View) {
        // Todo: Using constant
        val bundle = bundleOf("voyage" to item)
        findNavController(view).navigate(R.id.action_homeFragment_to_detailVoyageFragment, bundle)
    }

    private fun createPopupMenu(view: View, item: Voyage) {
        val popupMenu = PopupMenu(view.context, view).apply {
            inflate(R.menu.edit_delete_context_menu)
        }

        popupMenu.setOnMenuItemClickListener {

            when (it.itemId) {
                R.id.menu_item_delete -> {
                    val dialog = KAlertDialog(view.context, KAlertDialog.WARNING_TYPE).apply {
                        progressHelper.barColor = Color.parseColor("#A5DC86")
                        titleText = "Suppression"
                        contentText = "Etes vous sur de vouloir supprimer ?"
                        setCanceledOnTouchOutside(true)
                    }

                    dialog.setConfirmClickListener {

                        if (dialog.alerType == KAlertDialog.SUCCESS_TYPE || dialog.alerType == KAlertDialog.ERROR_TYPE) {
                            dialog.dismissWithAnimation()
                        } else {
                            deleteVoyage(dialog, item)
                        }
                    }

                    dialog.show()
                    true
                }

                R.id.menu_item_edit -> {
                    val bundle = bundleOf("voyage" to item)
                    findNavController(view).navigate(R.id.action_homeFragment_to_modifierVoyageFragment, bundle)
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun deleteVoyage(dialog: KAlertDialog, item: Voyage) {

        homeViewModel.deleteVoyage(item)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                }

                override fun onSubscribe(d: Disposable) {
                    dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
                }

                override fun onError(e: Throwable) {
                    dialog.contentText = e.message
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            })

    }


    override fun onClickPopupButton(view: View, item: Voyage, position: Int) {
        createPopupMenu(view, item)
    }
}
