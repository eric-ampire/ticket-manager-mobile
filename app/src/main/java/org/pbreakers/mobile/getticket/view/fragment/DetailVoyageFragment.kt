package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.kinda.alert.KAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_voyage_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.databinding.FragmentVoyageDetailBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.util.Session
import org.pbreakers.mobile.getticket.util.Tools.toggleSection
import org.pbreakers.mobile.getticket.viewmodel.DetailVoyageViewModel
import java.util.*
import kotlin.properties.Delegates


class DetailVoyageFragment : Fragment(), KoinComponent {

    var dialog: KAlertDialog by Delegates.notNull()

    // Todo: You have to use the constant
    private val currentVoyage by lazy {
        arguments?.getParcelable<Voyage>("voyage")
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
                    findNavController(btn).navigate(R.id.action_detailVoyageFragment_to_homeFragment)
                }
                it.alerType == KAlertDialog.WARNING_TYPE -> {
                    dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
                    findEtatInfo(btn)
                }
            }
        }
    }

    private fun findEtatInfo(btn: View) {
        val db = FirebaseFirestore.getInstance()
        val etatRef = db.collection("etats").document(currentVoyage!!.idEtat)

        // Todo: Tu doit verifier si le voyage et en cour
        etatRef.get().addOnCompleteListener {
            if (it.isSuccessful && it.result != null) {
                val etat = it.result!!.toObject(Etat::class.java) ?: return@addOnCompleteListener
                getUserInfo(etat, btn)
            } else {
                dialog.contentText = it.exception?.message ?: "Erreur inconnue"
                dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
            }
        }
    }

    private fun getUserInfo(etat: Etat, btn: View) {

        // Find current user
        val idUser = FirebaseAuth.getInstance().currentUser!!.uid
        val db = FirebaseFirestore.getInstance()
        val billetRef = db.collection("billets").document()

        val ticket = Billet(
            idBillet = billetRef.id,
            idVoyage = currentVoyage!!.idVoyage,
            idEtat = etat.idEtat,
            idUtilisateur = idUser,
            dateBillet = Date()
        )

        saveTicket(ticket, billetRef)
    }

    private fun saveTicket(ticket: Billet, billetRef: DocumentReference) {

        dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)

        billetRef.set(ticket).addOnCompleteListener {
            if (it.isSuccessful) {
                dialog.contentText = "Votre reservation s'est passer avec success !"
                dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)
            } else {
                dialog.contentText = it.exception?.message ?: "Erreur inconnue !"
                dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
            }
        }

        dialog.setConfirmClickListener {
            if (it.alerType == KAlertDialog.SUCCESS_TYPE) {
                it.dismissWithAnimation()
            }
        }
    }
}
