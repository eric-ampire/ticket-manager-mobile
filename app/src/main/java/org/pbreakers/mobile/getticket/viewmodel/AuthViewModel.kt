package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Maybe
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.repository.UtilisateurRepository

class AuthViewModel : ViewModel(), KoinComponent {

    private val repository: UtilisateurRepository by inject()

    val pseudo = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun add(utilisateur: Utilisateur) {
        repository.add(utilisateur)
    }

    fun login(): Maybe<Utilisateur> {
        return repository.login(pseudo.value!!, password.value!!)
    }
}