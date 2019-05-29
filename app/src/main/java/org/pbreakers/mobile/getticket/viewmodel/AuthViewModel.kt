package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jetbrains.anko.design.snackbar
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.repository.UtilisateurRepository
import javax.inject.Inject

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    @Inject lateinit var repository: UtilisateurRepository

    val pseudo = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val progressBarVisibility = ObservableInt(View.GONE)

    init {
        val app = application as App
        app.appComponent.inject(this)
    }

    fun findByPseudoAndPassword(pseudo: String, password: String): LiveData<Utilisateur> {
        return repository.findByPseudoAndPassword(pseudo, password)
    }

    fun add(utilisateur: Utilisateur) {
        repository.add(utilisateur)
    }

    fun createAccount(View: View) {

    }

    fun login(view: View) {
        if (pseudo.value == null) {
            view.snackbar("Pseudo invalide !")
            return
        }

        if (password.value == null) {
            view.snackbar("Mot de passe invalide")
            return
        }

        progressBarVisibility.set(View.VISIBLE)
        repository.findByPseudoAndPassword(pseudo.value!!, password.value!!).observeForever {

        }
    }
}