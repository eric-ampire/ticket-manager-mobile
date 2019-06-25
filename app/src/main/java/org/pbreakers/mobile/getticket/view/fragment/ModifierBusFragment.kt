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
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_modifier_bus.*
import org.koin.androidx.viewmodel.ext.android.viewModel

import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentModifierBusBinding
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.util.isInvalidInput
import org.pbreakers.mobile.getticket.util.itemIsNotSelected
import org.pbreakers.mobile.getticket.viewmodel.ModifierBusViewModel


class ModifierBusFragment : Fragment() {

    private lateinit var binding: FragmentModifierBusBinding
    private val currentBus by lazy {
        arguments?.getParcelable<Bus>("bus")
    }

    private val modifierBusViewModel by viewModel<ModifierBusViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = inflate<FragmentModifierBusBinding>(inflater, R.layout.fragment_modifier_bus, container, false).apply {
            viewModel = modifierBusViewModel
            lifecycleOwner = viewLifecycleOwner

            fabSaveBusModification.setOnClickListener {
                processModification(it)
            }
        }

        modifierBusViewModel.run {
            bus = currentBus!!
        }

        return binding.root
    }

    private fun processModification(view: View) {
        when {
            edtNomBus.isInvalidInput(getString(R.string.input_empty))       -> return
            edtNombreRanger.isInvalidInput(getString(R.string.input_empty)) -> return
            edtNombreSiege.isInvalidInput(getString(R.string.input_empty))  -> return
            spinnerAgencyBus.itemIsNotSelected("Selectionner l'agence") -> return
        }

        val dialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE).apply {
            titleText = "Confirmation"
            contentText = "Etès vous sur de vouloir continuer !"
        }

        dialog.setConfirmClickListener {
            if (it.alerType == KAlertDialog.SUCCESS_TYPE || it.alerType == KAlertDialog.ERROR_TYPE) {
                it.dismissWithAnimation()

            } else {
                updateBus(dialog, view)
            }
        }

        dialog.show()
    }

    private fun updateBus(dialog: KAlertDialog, view: View) {
        val agency = spinnerAgencyBus.selectedItem as Agence
        val newBus = Bus(
            idBus = currentBus!!.idBus,
            nomBus = edtNomBus.text.toString().trim(),
            nombreRange = edtNombreRanger.text.toString().trim().toInt(),
            nombreSiege = edtNombreSiege.text.toString().trim().toInt(),
            idAgence = agency.idAgence
        )

        dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
        modifierBusViewModel.modifierBus(newBus)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                    Navigation.findNavController(view).navigate(R.id.action_modifierBusFragment_to_busFragment)
                } else {
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                    dialog.contentText = it.exception?.message ?: "Unkwnon error !"
                }
            }

    }
}
