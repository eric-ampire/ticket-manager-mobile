package org.pbreakers.mobile.getticket.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Maybe
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Role
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.repository.UtilisateurRepository

class AuthViewModel : ViewModel(), KoinComponent {

    val pseudo = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val userFullName = MutableLiveData<String>()
    val userPassword = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()


    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun login(): Task<AuthResult> {
        return auth.signInWithEmailAndPassword(pseudo.value.toString(), password.value.toString())
    }

    fun signUp(onSuccess: () -> Unit, onError: (errorMessage: String) -> Unit) {
        val email = userEmail.value.toString()
        val password = userPassword.value.toString()

        Log.e("ericampire", email)

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val userId = auth.currentUser!!.uid
                val user = Utilisateur(
                    idUtilisateur = userId,
                    idRole = Role.CLIENT,
                    nomUtilisateur = userFullName.value.toString(),
                    pseudoUtilisateur = email,
                    passwordUtilisateur = password
                )

                saveUserData(user, onError, onSuccess)

            } else {
                onError(it.exception?.message ?: "Erreur inconnue")
            }
        }
    }

    private fun saveUserData(user: Utilisateur, onError: (errorMessage: String) -> Unit, onSuccess: () -> Unit) {
        val task = db.collection("users").document(user.idUtilisateur).set(user)

        task.addOnSuccessListener {
            onSuccess()
        }

        task.addOnFailureListener {
            onError(it.message ?: "Erreur inconnue !")
        }
    }
}