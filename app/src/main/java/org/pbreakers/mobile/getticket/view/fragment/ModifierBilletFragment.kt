package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.kinda.alert.KAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_modifier_billet.*
import org.koin.androidx.viewmodel.ext.android.viewModel

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentModifierBilletBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.util.getDateFromString
import org.pbreakers.mobile.getticket.util.itemIsNotSelected
import org.pbreakers.mobile.getticket.viewmodel.ModifierBilletViewModel
import java.util.*
import kotlin.properties.Delegates


class ModifierBilletFragment : Fragment() {

    private val currentBillet by lazy {
        arguments?.getParcelable<Billet>("billet")
    }

    private var dialog: KAlertDialog by Delegates.notNull()
    private val modifierBilletViewModel by viewModel<ModifierBilletViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = inflate<FragmentModifierBilletBinding>(inflater, R.layout.fragment_modifier_billet, container, false).apply {
            viewModel = modifierBilletViewModel
            lifecycleOwner = viewLifecycleOwner
        }

        modifierBilletViewModel.run {
            billet = currentBillet!!
        }

        binding.fabSaveBilletModification.setOnClickListener {
            when {
                spinnerUser.itemIsNotSelected("Selectionner le beneficiaire") -> return@setOnClickListener
                spinnerEtat.itemIsNotSelected("Selectionnez un etat")         -> return@setOnClickListener
                spinnerVoyage.itemIsNotSelected("Selectionnez voyage")         -> return@setOnClickListener
            }

            showConfirmationDialog(it)
        }

        return binding.root
    }

    private fun showConfirmationDialog(view: View) {
        dialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE).apply {
            titleText = "Modification"
            contentText = "Etes vous sur de vouloir continuer ?"
            show()
        }

        dialog.setConfirmClickListener {
            if (it.alerType == KAlertDialog.ERROR_TYPE || it.alerType == KAlertDialog.SUCCESS_TYPE) {
                it.dismissWithAnimation()
            } else {
                updateTicket(view)
            }
        }
    }

    private fun updateTicket(view: View) {
        val user = spinnerUser.selectedItem as Utilisateur
        val etat = spinnerEtat.selectedItem as Etat
        val voyage = spinnerVoyage.selectedItem as Voyage

        val billet = Billet(
            idBillet = currentBillet!!.idBillet,
            idUtilisateur = user.idUtilisateur,
            idEtat = etat.idEtat,
            dateBillet = Date(),
            idVoyage = voyage.idVoyage
        )

        // Loading
        dialog.contentText = "Modification..."
        dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)

        modifierBilletViewModel.updateBillet(billet)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    dialog.contentText = "Modification success"
                    dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)

                    Navigation.findNavController(view).navigate(R.id.action_modifierBilletFragment_to_billetFragment)
                } else {
                    dialog.contentText = it.exception?.message ?: "Erreur inconnue"
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            }

    }
}
