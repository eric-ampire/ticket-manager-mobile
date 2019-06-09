package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.*
import javax.inject.Inject

class DetailBilletViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var billet: Billet

    @Inject lateinit var etatRepository: EtatRepository
    @Inject lateinit var voyageRepository: VoyageRepository
    @Inject lateinit var userRepository: UtilisateurRepository

    val etat = ObservableField<String>()
    val user = ObservableField<Utilisateur>()
    val voyage = ObservableField<Voyage>()

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun init() {
        findUserById(billet.idUtilisateur)
        findEtatById(billet.idEtat)
        findVoyageById(billet.idVoyage)
    }

    private fun findVoyageById(idVoyage: Long) {
        voyageRepository.findById(idVoyage).observeForever {
            voyage.set(it)
        }
    }

    private fun findEtatById(idEtat: Long) {
        etatRepository.findById(idEtat).observeForever {
            etat.set(it.nomEtat)
        }
    }

    private fun findUserById(idUtilisateur: Long) {
        // Todo: You have to do it in the view
        userRepository.findById(idUtilisateur).observeForever {
            user.set(it)
        }
    }
}