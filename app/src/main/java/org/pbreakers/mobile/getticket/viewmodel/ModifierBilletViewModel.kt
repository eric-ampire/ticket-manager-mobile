package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.*
import javax.inject.Inject

class ModifierBilletViewModel : ViewModel(), KoinComponent {

    lateinit var billet: Billet

    private val userRepository: UtilisateurRepository by inject()
    private val billetRepository: BilletRepository    by inject()
    private val etatRepository: EtatRepository        by inject()
    private val voyageRepository: VoyageRepository    by inject()

    val users = MutableLiveData<List<Utilisateur>>().apply {
        value = arrayListOf()
    }

    val etats = MutableLiveData<List<Etat>>().apply {
        value = arrayListOf()
    }

    val voyages = MutableLiveData<List<Voyage>>().apply {
        value = arrayListOf()
    }

    init {
        findAllEtat()
        findAllUsers()
        findAllVoyage()
    }

    private fun findAllUsers() {
        userRepository.findAll().observeForever {
            users.postValue(it)
        }
    }

    private fun findAllEtat() {
        etatRepository.findAll().observeForever {
            etats.postValue(it)
        }
    }

    private fun findAllVoyage() {
        voyageRepository.findAllLiveData().observeForever {
            voyages.postValue(it)
        }
    }

    fun updateBillet(billet: Billet): Completable {
        return billetRepository.update(billet)
    }
}