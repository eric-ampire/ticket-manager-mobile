package org.pbreakers.mobile.getticket.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import io.reactivex.Maybe
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Role
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.repository.UtilisateurRepository
import org.pbreakers.mobile.getticket.util.LoadingState
import org.pbreakers.mobile.getticket.util.Session

class AuthViewModel : ViewModel(), KoinComponent {

    private val session: Session by inject()

    val pseudo = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val userFullName = MutableLiveData<String>()
    val userPassword = MutableLiveData<String>()
    val userEmail = MutableLiveData<String>()

    private val _loadingState = MutableLiveData<LoadingState>()
    val loadingState: LiveData<LoadingState>
        get() = _loadingState


    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun login() {
        _loadingState.value = LoadingState.RUNNING
        val task =  auth.signInWithEmailAndPassword(pseudo.value.toString(), password.value.toString())

        task.addOnCompleteListener {
            if (it.isSuccessful) {
                val uid = auth.currentUser!!.uid
                val userTask = db.collection("users").document(uid).get()
                userTask.addOnCompleteListener { getUserTask ->

                    if (getUserTask.isSuccessful && getUserTask.result != null && getUserTask.result!!.exists()) {
                        val currentUser = getUserTask.result?.toObject(Utilisateur::class.java)
                        session.createSession(currentUser!!)
                        _loadingState.value = LoadingState.LOADED
                    } else {
                        _loadingState.value = LoadingState.error(getUserTask.exception?.message)
                    }
                }

            } else {
                _loadingState.value = LoadingState.error(it.exception?.message)
            }
        }
    }

    fun signUp() {
        val email = userEmail.value.toString()
        val password = userPassword.value.toString()

        Log.e("ericampire", email)

        _loadingState.value = LoadingState.RUNNING
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

                saveUserData(user)

            } else {
                _loadingState.value = LoadingState.error(it.exception?.message)
            }
        }
    }

    private fun saveUserData(user: Utilisateur) {
        val task = db.collection("users").document(user.idUtilisateur).set(user)

        task.addOnSuccessListener {
            session.createSession(user)
            _loadingState.value = LoadingState.LOADED
        }

        task.addOnFailureListener {
            _loadingState.value = LoadingState.error(it.message)
        }
    }
}