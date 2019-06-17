package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.kinda.alert.KAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_modifier_voyage.*
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentModifierVoyageBinding
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.util.*
import org.pbreakers.mobile.getticket.viewmodel.ModifierVoyageViewModel


class ModifierVoyageFragment : Fragment() {

    private lateinit var binding: FragmentModifierVoyageBinding
    private val currentVoyage by lazy {
        arguments?.getParcelable<Voyage>("voyage")
    }

    private val modifierVoyageViewModel by viewModel<ModifierVoyageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = inflate<FragmentModifierVoyageBinding>(inflater, R.layout.fragment_modifier_voyage, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            voyage = currentVoyage
            viewModel = modifierVoyageViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fabSaveVoyageModification.setOnClickListener { fab ->
            initUpdateVoyage(fab)
        }
    }

    private fun initUpdateVoyage(fab: View) {

        if (voyageInputInvalid() || currentVoyage == null) return

        val bus = spinnerBusVoyage.selectedItem as Bus
        val destination = spinnerDestiVoyage.selectedItem as Lieu
        val provenance = spinnerProvVoyage.selectedItem as Lieu
        val transit = spinnerTransitVoyage.selectedItem as Transit
        val etat = spinnerEtatVoyage.selectedItem as Etat

        val voyage = Voyage(
            idVoyage = currentVoyage!!.idVoyage,
            referenceVoyage = edtRefVoyage.text.toString().trim(),
            idBus = bus.idBus,
            idProvenance = provenance.idLieu,
            idDestination = destination.idLieu,
            idTransit = transit.idTransit,
            idEtat = etat.idEtat,
            prixVoyage = edtPrixVoyage.text.toString().trim().toDouble(),
            dateDepart = edtDateDepartVoyage.text.toString().trim().getDateFromString()!!,
            dateDestination = edtDateDestiVoyage.text.toString().trim().getDateFromString()!!,
            heureDepart = edtHeureDepartVoyage.text.toString().trim().getTimeFromString()!!,
            heureDestination = edtHeureArriveVoyage.text.toString().trim().getTimeFromString()!!
        )

        // Saving
        showProgressDialog(voyage, fab)

    }

    private fun showProgressDialog(voyage: Voyage, fab: View) {
        val dialog = KAlertDialog(context, KAlertDialog.PROGRESS_TYPE).apply {
            titleText = "Modification"
            contentText = "Mise a jour en cour d'execution !"
        }

        processUpdate(voyage, dialog)

        dialog.setConfirmClickListener {

            if (dialog.alerType == KAlertDialog.SUCCESS_TYPE) {
                dialog.dismissWithAnimation()
                findNavController(fab).navigate(R.id.action_modifierVoyageFragment_to_homeFragment)
            }

            if (dialog.alerType == KAlertDialog.ERROR_TYPE) {
                dialog.dismissWithAnimation()
            }
        }
    }

    private fun processUpdate(voyage: Voyage, dialog: KAlertDialog) {
        modifierVoyageViewModel.update(voyage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    cleanText(
                        edtRefVoyage,
                        edtHeureDepartVoyage,
                        edtDateDepartVoyage,
                        edtHeureArriveVoyage,
                        edtDateDestiVoyage,
                        edtPrixVoyage
                    )

                    dialog.contentText = "La mise a jour a été effectuée avec succes !"
                    dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                }

                override fun onSubscribe(d: Disposable) {
                    dialog.show()
                }

                override fun onError(e: Throwable) {
                    dialog.contentText = e.message
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            })

    }


    private fun voyageInputInvalid(): Boolean {
        return when {
            edtRefVoyage.isInvalidInput(getString(R.string.input_empty)) -> true

            edtDateDepartVoyage.text.toString().trim().getDateFromString() == null -> {
                edtDateDepartVoyage.error = "Date Invalide"
                true
            }

            edtHeureDepartVoyage.text.toString().trim().getTimeFromString() == null -> {
                edtHeureDepartVoyage.error = "Heure Invalide"
                true
            }

            edtDateDestiVoyage.text.toString().trim().getDateFromString() == null -> {
                edtDateDestiVoyage.error = "Date invalide"
                true
            }

            edtHeureArriveVoyage.text.toString().trim().getTimeFromString() == null -> {
                edtHeureArriveVoyage.error = "Heure Invalide"
                true
            }

            binding.spinnerProvVoyage.itemIsNotSelected("Ajouter un lieu")   -> true
            binding.spinnerDestiVoyage.itemIsNotSelected("Ajouter un lieu")  -> true
            binding.spinnerTransitVoyage.itemIsNotSelected("Ajoute Transit") -> true
            binding.spinnerEtatVoyage.itemIsNotSelected("Ajouter un etat")   -> true
            binding.spinnerBusVoyage.itemIsNotSelected("Ajouter un Bus")     -> true

            edtHeureDepartVoyage.isInvalidInput(getString(R.string.input_empty))  -> true
            edtDateDepartVoyage.isInvalidInput(getString(R.string.input_empty))   -> true
            edtHeureArriveVoyage.isInvalidInput(getString(R.string.input_empty))  -> true
            edtDateDestiVoyage.isInvalidInput(getString(R.string.input_empty))    -> true
            edtPrixVoyage.isInvalidInput(getString(R.string.input_empty))          -> true

            else -> false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
}
