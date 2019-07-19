package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kinda.alert.KAlertDialog
import kotlinx.android.synthetic.main.fragment_voyage_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentVoyageDetailBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.util.Tools.toggleSection
import org.pbreakers.mobile.getticket.viewmodel.DetailVoyageViewModel
import java.util.*
import kotlin.properties.Delegates


class DetailVoyageFragment : Fragment(), KoinComponent {

    var dialog: KAlertDialog by Delegates.notNull()

    // Todo: You have to use the constant
    private val currentVoyage by lazy {
        DetailVoyageFragmentArgs.fromBundle(arguments!!).voyage
    }

    private val detailVoyageViewModel: DetailVoyageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = inflate<FragmentVoyageDetailBinding>(
            inflater, R.layout.fragment_voyage_detail, container, false).apply {

            voyage = currentVoyage
            viewModel = detailVoyageViewModel
        }

        detailVoyageViewModel.run {
            voyage = currentVoyage!!
            init()
        }

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
            createReservation(it)
        }
    }

    private fun createReservation(btn: View) {
        if (currentVoyage == null) return

        dialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE).apply {
            titleText = "Confirmation"
            contentText = "Etès vous sur de vouloir continuer !"
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
                    dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
                    checkTravelState()
                }
            }
        }
    }

    private fun checkTravelState() {
        if (currentVoyage!!.idEtat == Etat.ATTENTE) {
            saveTickedWithPendingState(Etat.ATTENTE)
        } else {
            dialog.contentText = "Impossible de reserver sur ce voyage !"
            dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
        }
    }

    private fun saveTickedWithPendingState(idState: String) {
        val idUser = FirebaseAuth.getInstance().currentUser!!.uid
        val ticket = Billet(
            idVoyage = currentVoyage!!.idVoyage,
            idEtat = idState,
            idUtilisateur = idUser,
            dateBillet = Date()
        )

        detailVoyageViewModel.saveTicket(ticket).addOnSuccessListener {
            dialog.contentText = "Votre reservation s'est passer avec success !"
            dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)

        }.addOnFailureListener {

            dialog.contentText = it.message ?: "Erreur inconnue !"
            dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
        }

        dialog.setConfirmClickListener {
            if (it.alerType == KAlertDialog.SUCCESS_TYPE) {
                it.dismissWithAnimation()
            }
        }
    }
}
