package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.kinda.alert.KAlertDialog
import kotlinx.android.synthetic.main.fragment_voyage_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentVoyageDetailBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.util.*
import org.pbreakers.mobile.getticket.util.Tools.toggleSection
import org.pbreakers.mobile.getticket.viewmodel.DetailVoyageViewModel
import java.util.*
import kotlin.properties.Delegates


class DetailVoyageFragment : Fragment(), KoinComponent {

    var dialog: KAlertDialog by Delegates.notNull()

    private val currentVoyage by lazy {
        DetailVoyageFragmentArgs.fromBundle(arguments!!).voyage
    }

    private val detailVoyageViewModel: DetailVoyageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentVoyageDetailBinding.inflate(inflater).apply {
            voyage = currentVoyage
            viewModel = detailVoyageViewModel
            lifecycleOwner = this@DetailVoyageFragment
        }

        detailVoyageViewModel.run {
            voyage = currentVoyage
            init()
        }

        detailVoyageViewModel.loadingState.observe(this, Observer {
            when (it.status) {
                LoadingState.Status.RUNNING -> dialog.dialogProgress("Operation en cour...")
                LoadingState.Status.ERROR -> dialog.dialogError(it.message ?: "Erreur inconnue !")
                LoadingState.Status.LOADED -> dialog.dialogSuccess("Votre reservation s'est passer avec success !")
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
    }

    private fun initComponent() {
        // info item_section
        btnToggleInfo.setOnClickListener { toggleSection(btnToggleInfo, lytExpandInfo, nestedScrollView) }
        btnHideInfo.setOnClickListener { toggleSection(btnToggleInfo, lytExpandInfo, nestedScrollView) }

        // passenger item_section
        btnTogglePassenger.setOnClickListener { toggleSection(btnTogglePassenger, lytExpandPassenger, nestedScrollView) }

        btnReserve.setOnClickListener {
            createReservation()
        }
    }

    private fun createReservation() {
        dialog = getDialogInstance(requireContext()).apply {
            dialogWarning("Etès vous sur de vouloir reserver ?")
            show()
        }

        dialog.setConfirmClickListener {
            when {
                it.alerType == KAlertDialog.ERROR_TYPE -> {
                    dialog.dismissWithAnimation()
                }

                it.alerType == KAlertDialog.SUCCESS_TYPE -> {

                    dialog.dismissWithAnimation()
                    findNavController(btnReserve).navigate(R.id.action_detailVoyageFragment_to_homeFragment)
                }
                it.alerType == KAlertDialog.WARNING_TYPE -> {
                    checkTravelState()
                }
            }
        }
    }

    private fun checkTravelState() {
        if (currentVoyage.idEtat == Etat.ATTENTE) {
            saveTickedWithPendingState(Etat.ATTENTE)
        } else {
            dialog.dialogError("Impossible de reserver sur ce voyage !")
        }
    }

    private fun saveTickedWithPendingState(idState: String) {
        val idUser = FirebaseAuth.getInstance().currentUser!!.uid
        val ticket = Billet(
            idVoyage = currentVoyage.idVoyage,
            idEtat = idState,
            idUtilisateur = idUser,
            dateBillet = Date()
        )

        detailVoyageViewModel.saveTicket(ticket)

        dialog.setConfirmClickListener {
            if (it.alerType == KAlertDialog.SUCCESS_TYPE) {
                it.dismissWithAnimation()
            }
        }
    }
}
