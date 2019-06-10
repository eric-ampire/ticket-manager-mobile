package org.pbreakers.mobile.getticket.view.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import com.kinda.alert.KAlertDialog
import io.reactivex.CompletableObserver
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_voyage_detail.*
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.databinding.FragmentVoyageDetailBinding
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.util.Session
import org.pbreakers.mobile.getticket.util.Tools.toggleSection
import org.pbreakers.mobile.getticket.viewmodel.DetailVoyageViewModel
import java.util.*
import javax.inject.Inject


class DetailVoyageFragment : Fragment() {

    @Inject lateinit var session: Session

    // Todo: You have to use the constant
    private val currentVoyage by lazy {
        arguments?.getParcelable<Voyage>("voyage")
    }

    private val detailVoyageViewModel by lazy {
        ViewModelProviders.of(this).get(DetailVoyageViewModel::class.java).apply {
            voyage = currentVoyage!!
            init()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val app = activity?.application as App
        app.appComponent.inject(this)
    }

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

        val dialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE).apply {
            titleText = "Confirmation"
            contentText = "Etès vous sur de vouloir continuer !"
            showCancelButton(true)
        }

        dialog.setConfirmClickListener {
            when (it.alerType) {
                KAlertDialog.ERROR_TYPE   -> dialog.dismiss()
                KAlertDialog.SUCCESS_TYPE -> dialog.dismiss()

                else -> {
                    findEtatInfo(dialog, btn)
                }
            }
        }

        dialog.show()
    }

    private fun findEtatInfo(dialog: KAlertDialog, btn: View) {
        detailVoyageViewModel.findEtatByName("En attente")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<Etat> {
                override fun onSuccess(t: Etat) {
                    getUserInfo(dialog, t, btn)
                }

                override fun onComplete() {
                    dialog.contentText = "Une erreur s'est produit lors de l'opération"
                    dialog.changeAlertType(KAlertDialog.WARNING_TYPE)
                }

                override fun onSubscribe(d: Disposable) {
                    dialog.titleText = "Loading"
                    dialog.contentText = "Operation en cour de traitement..."
                    dialog.changeAlertType(KAlertDialog.PROGRESS_TYPE)
                }

                override fun onError(e: Throwable) {
                    dialog.contentText = e.message
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            })

    }

    private fun getUserInfo(dialog: KAlertDialog, etat: Etat, btn: View) {

        // Find current user
        session.getCurrentUser { user ->

            if (user == null) {
                dialog.contentText = "Impossible de trouver un utilisateur !"
                dialog.changeAlertType(KAlertDialog.ERROR_TYPE)

            } else {

                val ticket = Billet(
                    idBillet = System.nanoTime(),
                    idVoyage = currentVoyage!!.idVoyage,
                    idEtat = etat.idEtat,
                    idUtilisateur = user.idUtilisateur,
                    dateBillet = Date()
                )

                saveTicket(dialog, ticket, btn)
            }
        }
    }

    private fun saveTicket(dialog: KAlertDialog, ticket: Billet, btn: View) {
        detailVoyageViewModel.saveBillet(ticket)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : CompletableObserver {
                override fun onComplete() {
                    dialog.contentText = "Votre reservation s'est passer avec success !"
                    dialog.changeAlertType(KAlertDialog.SUCCESS_TYPE)

                    findNavController(btn).navigate(R.id.action_detailVoyageFragment_to_homeFragment)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    dialog.contentText = e.message
                    dialog.changeAlertType(KAlertDialog.ERROR_TYPE)
                }
            })

    }
}
