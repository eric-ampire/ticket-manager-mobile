package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Maybe
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.repository.UtilisateurRepository

class AuthViewModel : ViewModel(), KoinComponent {

    val pseudo = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    fun login(): Task<AuthResult> {
        val auth = FirebaseAuth.getInstance()
        return auth.signInWithEmailAndPassword(pseudo.value.toString(), password.value.toString())
    }
}