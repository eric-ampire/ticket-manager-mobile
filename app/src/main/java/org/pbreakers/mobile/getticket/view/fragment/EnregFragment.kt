package org.pbreakers.mobile.getticket.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.fragment_enreg.*
import kotlinx.android.synthetic.main.fragment_enreg.view.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentEnregBinding
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.util.*
import org.pbreakers.mobile.getticket.util.Tools.toggleSection
import org.pbreakers.mobile.getticket.viewmodel.EnregViewModel
import java.util.*
import java.util.Observer as Observer1


class EnregFragment : Fragment() {

    lateinit var binding: FragmentEnregBinding

    private val enregViewModel: EnregViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = inflate<FragmentEnregBinding>(inflater, R.layout.fragment_enreg, container, false).apply {
            lifecycleOwner = this@EnregFragment
            viewModel = enregViewModel
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAgencyComponent(view)       // Agency
        initEtatComponent(view)         // Etat
        initLieuComponent(view)         // Lieu
        initRoleComponent(view)         // Role
        initTransitComponent(view)      // Transit
        initUserComponent(view)         // User
        initBusComponent(view)          // Bus
        initVoyageComponent(view)       // Voyage
        initPointArretComponent(view)   // Point Arret
    }

    private fun initPointArretComponent(view: View) {
        view.btnTogglePointArret.setOnClickListener {
            toggleSection(it, view.lytExpandPointArret, nestedScrollView)
        }

        view.btnHidePointArret.setOnClickListener {
            toggleSection(view.btnTogglePointArret, view.lytExpandPointArret, nestedScrollView)
        }

        view.btnEnregPointArret.setOnClickListener {
            when {
                binding.spinnerLieuPointArret.itemIsNotSelected("Ajouter une un lieu") -> return@setOnClickListener
                binding.spinnerVoyagePointArret.itemIsNotSelected("Ajouter un voyage") -> return@setOnClickListener
            }

            val lieu = spinnerLieuPointArret.selectedItem as Lieu
            val voyage = spinnerVoyagePointArret.selectedItem as Voyage

            val pointArret = PointArret(
                idPointArret = System.nanoTime(),
                idVoyage = voyage.idVoyage,
                idLieu = lieu.idLieu
            )

            enregViewModel.savePointArret(pointArret) {
                toggleSection(view.btnTogglePointArret, view.lytExpandPointArret, nestedScrollView)
                context?.toast("Success")
            }
        }
    }

    private fun initVoyageComponent(view: View) {

        view.btnToggleVoyage.setOnClickListener {
            toggleSection(it, view.lytExpandVoyage, nestedScrollView)
        }

        view.btnHideVoyage.setOnClickListener {
            toggleSection(view.btnToggleVoyage, view.lytExpandVoyage, nestedScrollView)
        }

        view.btnEnregVoyage.setOnClickListener {

            if (voyageInputInvalid()) return@setOnClickListener

            val bus = spinnerBusVoyage.selectedItem as Bus
            val destination = spinnerDestiVoyage.selectedItem as Lieu
            val provenance = spinnerProvVoyage.selectedItem as Lieu
            val transit = spinnerTransitVoyage.selectedItem as Transit
            val etat = spinnerEtatVoyage.selectedItem as Etat

            val voyage = Voyage(
                idVoyage = System.nanoTime(),
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
            saveVoyage(voyage, view)
        }
    }

    private fun saveVoyage(voyage: Voyage, view: View) {
        enregViewModel.saveVoyage(voyage) {

            cleanText(
                edtRefVoyage,
                edtHeureDepartVoyage,
                edtDateDepartVoyage,
                edtHeureArriveVoyage,
                edtDateDestiVoyage,
                edtPrixVoyage
            )

            toggleSection(view.btnToggleVoyage, view.lytExpandVoyage, nestedScrollView)
            context?.toast("Success")
        }
    }

    private fun voyageInputInvalid(): Boolean {
        return when {
            edtRefVoyage.isInvalidInput(getString(R.string.input_empty)) -> return true

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

            binding.spinnerProvVoyage.selectedItem == null -> {
                context?.toast("Ajouter un lieu")
                true
            }

            binding.spinnerDestiVoyage.selectedItem == null -> {
                context?.toast("Ajouter une un lieu")
                true
            }

            binding.spinnerTransitVoyage.selectedItem == null -> {
                context?.toast("Ajouter transit")
                true
            }

            binding.spinnerEtatVoyage.selectedItem == null -> {
                context?.toast("Ajouter un etat")
                true
            }

            binding.spinnerBusVoyage.selectedItem == null -> {
                context?.toast("Ajouter un bus")
                true
            }

            edtHeureDepartVoyage.isInvalidInput(getString(R.string.input_empty)) -> return true
            edtDateDepartVoyage.isInvalidInput(getString(R.string.input_empty)) -> return true
            edtHeureArriveVoyage.isInvalidInput(getString(R.string.input_empty)) -> return true
            edtDateDestiVoyage.isInvalidInput(getString(R.string.input_empty)) -> return true
            edtPrixVoyage.isInvalidInput(getString(R.string.input_empty)) -> return true

            else -> false
        }
    }

    private fun initBusComponent(view: View) {
        view.btnToggleBus.setOnClickListener {
            toggleSection(it, view.lytExpandBus, nestedScrollView)
        }

        view.btnHideBus.setOnClickListener {
            toggleSection(view.btnToggleBus, view.lytExpandBus, nestedScrollView)
        }

        view.btnEnregBus.setOnClickListener {

            when {
                edtNomBus.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                edtRangerBus.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                edtSiegeBus.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                binding.spinnerAgencyBus.selectedItem == null -> {
                    context?.toast("Ajouter une agence")
                    return@setOnClickListener
                }
            }

            val agency = binding.spinnerAgencyBus.selectedItem as Agence
            val bus = Bus(
                System.nanoTime(),
                edtNomBus.text.toString().trim(),
                edtRangerBus.text.toString().trim().toInt(),
                edtSiegeBus.text.toString().trim().toInt(),
                agency.idAgence
            )

            // Saving
            enregViewModel.saveBus(bus) {
                cleanText(edtNomBus, edtRangerBus, edtSiegeBus)

                toggleSection(view.btnToggleBus, view.lytExpandBus, nestedScrollView)
                context?.toast("Success")
            }
        }
    }

    private fun initUserComponent(view: View) {
        view.btnToggleUser.setOnClickListener {
            toggleSection(it, view.lytExpandUser, nestedScrollView)
        }

        view.btnHideUser.setOnClickListener {
            toggleSection(view.btnToggleUser, view.lytExpandUser, nestedScrollView)
        }

        view.btnEnregUser.setOnClickListener {

            when {
                view.edtNomUser.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                view.edtPseudoUser.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                view.edtPasswordUser.isInvalidInput(getString(R.string.input_empty)) -> return@setOnClickListener
                view.edtPasswordConfirmUser.text.toString() != view.edtPasswordUser.text.toString() -> {
                    view.edtPasswordUser.error = getString(R.string.password_wrong)
                    return@setOnClickListener
                }

                binding.edtRoleUser.selectedItem == null -> {
                    context?.toast("Ajouter un role")
                    return@setOnClickListener
                }
            }

            val role = binding.edtRoleUser.selectedItem as Role

            val user = Utilisateur(
                System.nanoTime(),
                edtNomUser.text.toString().trim(),
                edtPseudoUser.text.toString().trim(),
                edtPasswordUser.text.toString().trim(),
                role.idRole
            )

            // Todo: Show progress bar
            enregViewModel.saveUser(user) {
                cleanText(edtNomUser, edtPseudoUser, edtPasswordUser, edtPasswordConfirmUser)

                toggleSection(view.btnToggleUser, view.lytExpandUser, nestedScrollView)
                context?.toast("Success")
            }

            view.snackbar("Success")
            toggleSection(view.btnToggleUser, view.lytExpandUser, nestedScrollView)
        }
    }

    private fun dialogDatePickerLight(view: EditText) {
        val currentCalender = Calendar.getInstance()

        val datePicker = DatePickerDialog.newInstance(
            { _, year, monthOfYear, dayOfMonth ->
                val calendar = Calendar.getInstance().apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, monthOfYear)
                    set(Calendar.DAY_OF_MONTH, dayOfMonth)
                }

                val date = calendar.timeInMillis
                view.setText(Tools.getFormattedDateShort(date))
            },

            currentCalender.get(Calendar.YEAR),
            currentCalender.get(Calendar.MONTH),
            currentCalender.get(Calendar.DAY_OF_MONTH)
        )

        //set dark light
        datePicker.run {
            isThemeDark = false
            accentColor = resources.getColor(R.color.colorPrimary)
            minDate = currentCalender
            show(activity?.fragmentManager, "tag")
        }
    }

    private fun initAgencyComponent(view: View) {
        view.btnToggleAgency.setOnClickListener {
            toggleSection(it, view.lytExpandAgency, nestedScrollView)
        }

        view.btnHideAgency.setOnClickListener {
            toggleSection(view.btnToggleAgency, view.lytExpandAgency, nestedScrollView)
        }

        view.btnEnregAgency.setOnClickListener {
            if (view.edtNomAgency.text.isBlank()) {
                view.edtNomAgency.error = getString(R.string.input_empty)
            } else {
                val agency = Agence(System.nanoTime(), edtNomAgency.text.toString().trim())
                enregViewModel.saveAgence(agency) {
                    view.edtNomAgency.text.clear()
                    toggleSection(view.btnToggleAgency, view.lytExpandAgency, nestedScrollView)
                    context?.toast("Success")
                }
            }
        }
    }

    private fun initEtatComponent(view: View) {
        view.btnToggleEtat.setOnClickListener {
            toggleSection(it, view.lytExpandEtat, nestedScrollView)
        }

        view.btnHideEtat.setOnClickListener {
            toggleSection(view.btnToggleEtat, view.lytExpandEtat, nestedScrollView)
        }

        view.btnEnregEtat.setOnClickListener {
            if (view.edtNomEtat.text.isBlank()) {
                view.edtNomEtat.error = getString(R.string.input_empty)
            } else {
                val etat = Etat(System.nanoTime(), edtNomEtat.text.toString().trim())
                enregViewModel.saveEtat(etat) {
                    view.edtNomEtat.text.clear()
                    context?.toast("Success")
                    toggleSection(view.btnToggleEtat, view.lytExpandEtat, nestedScrollView)
                }
            }
        }
    }

    private fun initLieuComponent(view: View) {
        view.btnToggleLieu.setOnClickListener {
            toggleSection(it, view.lytExpandLieu, nestedScrollView)
        }

        view.btnHideLieu.setOnClickListener {
            toggleSection(view.btnToggleLieu, view.lytExpandLieu, nestedScrollView)
        }

        view.btnEnregLieu.setOnClickListener {
            if (view.edtNomLieu.text.isBlank()) {
                view.edtNomLieu.error = getString(R.string.input_empty)
            } else {
                val lieu = Lieu(System.nanoTime(), edtNomLieu.text.toString().trim())
                enregViewModel.saveLieu(lieu) {
                    view.edtNomLieu.text.clear()
                    toggleSection(view.btnToggleLieu, view.lytExpandLieu, nestedScrollView)
                    context?.toast("Success")
                }
            }
        }
    }

    private fun initRoleComponent(view: View) {
        view.btnToggleRole.setOnClickListener {
            toggleSection(it, view.lytExpandRole, nestedScrollView)
        }

        view.btnHideRole.setOnClickListener {
            toggleSection(view.btnToggleRole, view.lytExpandRole, nestedScrollView)
        }

        view.btnEnregRole.setOnClickListener {
            if (view.edtNomRole.text.isBlank()) {
                view.edtNomRole.error = getString(R.string.input_empty)
            } else {
                val role = Role(System.nanoTime(), edtNomRole.text.toString().trim())
                enregViewModel.saveRole(role) {
                    view.edtNomRole.text.clear()
                    toggleSection(view.btnToggleRole, view.lytExpandRole, nestedScrollView)
                    context?.toast("Success")
                }
            }
        }
    }

    private fun initTransitComponent(view: View) {
        view.btnToggleTransit.setOnClickListener {
            toggleSection(it, view.lytExpandTransit, nestedScrollView)
        }

        view.btnHideTransit.setOnClickListener {
            toggleSection(view.btnToggleTransit, view.lytExpandTransit, nestedScrollView)
        }

        view.btnEnregTransit.setOnClickListener {
            if (view.edtNomTransit.text.isBlank()) {
                view.edtNomTransit.error = getString(R.string.input_empty)
            } else {
                val transit = Transit(System.nanoTime(), edtNomTransit.text.toString().trim())
                enregViewModel.saveTransit(transit) {
                    view.edtNomTransit.text.clear()
                    toggleSection(view.btnToggleTransit, view.lytExpandTransit, nestedScrollView)
                    context?.toast("Success")
                }
            }
        }
    }
}
