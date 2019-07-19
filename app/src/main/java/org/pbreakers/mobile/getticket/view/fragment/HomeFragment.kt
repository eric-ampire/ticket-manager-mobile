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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.pbreakers.mobile.getticket.adapter.common.OnItemClickListener
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.adapter.VoyageAdapter
import org.pbreakers.mobile.getticket.databinding.FragmentHomeBinding
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.util.LoadingState
import org.pbreakers.mobile.getticket.view.activity.MainActivity
import org.pbreakers.mobile.getticket.viewmodel.HomeViewModel


class HomeFragment : Fragment(), OnItemClickListener<Voyage> {


    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentHomeBinding>(inflater, R.layout.fragment_home, container, false).apply {
            this.viewModel = homeViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        homeViewModel.run {
            this.adapter = VoyageAdapter(this@HomeFragment)
            init()
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

    override fun onClick(view: View, item: Voyage, position: Int) {
        showVoyageDetailFragment(item, view)
    }

    private fun showVoyageDetailFragment(item: Voyage, view: View) {
        findNavController(view)
            .navigate(HomeFragmentDirections.actionHomeFragmentToDetailVoyageFragment(item))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mainActivity = activity as MainActivity

        homeViewModel.getLoadingState().observe(this, Observer {
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
                    findNavController(view)
                        .navigate(HomeFragmentDirections.actionHomeFragmentToModifierVoyageFragment(item))
                    true
                }

                else -> false
            }
        }

        popupMenu.show()
    }

    private fun deleteVoyage(dialog: KAlertDialog, item: Voyage) {

        val deleteTask = homeViewModel.deleteVoyage(item).delete()

        dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        deleteTask.addOnCompleteListener {
            if (it.isSuccessful) {
                dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
            } else {
                dialog.contentText = it.exception?.message ?: "Erreur inconnue !"
                dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
            }
        }
    }


    override fun onClickPopupButton(view: View, item: Voyage, position: Int) {
        createPopupMenu(view, item)
    }
}
