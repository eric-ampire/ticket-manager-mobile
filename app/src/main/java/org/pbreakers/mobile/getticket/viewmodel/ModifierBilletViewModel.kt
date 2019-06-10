package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.repository.*
import javax.inject.Inject

class ModifierBilletViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var billet: Billet

    @Inject lateinit var userRepository: UtilisateurRepository
    @Inject lateinit var billetRepository: BilletRepository
    @Inject lateinit var etatRepository: EtatRepository

    val users = MutableLiveData<List<Utilisateur>>().apply {
        value = arrayListOf()
    }

    val etats = MutableLiveData<List<Etat>>().apply {
        value = arrayListOf()
    }

    init {
        val application = app as App
        application.appComponent.inject(this)

        findAllEtat()
        findAllUsers()
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

    fun updateBillet(billet: Billet): Completable {
        return billetRepository.update(billet)
    }
}