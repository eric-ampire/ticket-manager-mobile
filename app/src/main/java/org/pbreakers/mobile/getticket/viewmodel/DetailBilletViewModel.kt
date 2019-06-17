package org.pbreakers.mobile.getticket.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.EtatRepository
import org.pbreakers.mobile.getticket.model.repository.UtilisateurRepository
import org.pbreakers.mobile.getticket.model.repository.VoyageRepository

class DetailBilletViewModel : ViewModel(), KoinComponent {

    lateinit var billet: Billet

    private val etatRepository: EtatRepository        by inject()
    private val voyageRepository: VoyageRepository    by inject()
    private val userRepository: UtilisateurRepository by inject()


    val etat = ObservableField<String>()
    val user = ObservableField<Utilisateur>()
    val voyage = ObservableField<Voyage>()

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