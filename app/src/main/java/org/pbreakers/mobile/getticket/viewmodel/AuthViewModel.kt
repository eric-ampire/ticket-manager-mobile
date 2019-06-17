package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Maybe
import io.reactivex.Single
import org.jetbrains.anko.design.snackbar
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.repository.UtilisateurRepository
import javax.inject.Inject

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    @Inject lateinit var repository: UtilisateurRepository

    val pseudo = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    init {
        val app = application as App
        app.appComponent.inject(this)
    }

    fun add(utilisateur: Utilisateur) {
        repository.add(utilisateur)
    }

    fun login(): Maybe<Utilisateur> {
        return repository.login(pseudo.value!!, password.value!!)
    }
}