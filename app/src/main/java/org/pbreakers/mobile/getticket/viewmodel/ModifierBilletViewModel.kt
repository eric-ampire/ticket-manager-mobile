package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.adapter.VoyageAdapter
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.BilletRepository
import org.pbreakers.mobile.getticket.model.repository.EtatRepository
import org.pbreakers.mobile.getticket.model.repository.UtilisateurRepository
import org.pbreakers.mobile.getticket.model.repository.VoyageRepository

class ModifierBilletViewModel : ViewModel(), KoinComponent {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    lateinit var billet: Billet


    private val _users: MutableLiveData<List<Utilisateur>> by lazy {
        MutableLiveData<List<Utilisateur>>().also {
            findAllUsers()
        }
    }

    private val _etats: MutableLiveData<List<Etat>> by lazy {
        MutableLiveData<List<Etat>>().also {
            findAllEtat()
        }
    }

    private val _voyages: MutableLiveData<List<Voyage>> by lazy {
        MutableLiveData<List<Voyage>>().also {
            findAllVoyage()
        }
    }

    val users: LiveData<List<Utilisateur>>
        get() = _users

    val etats: LiveData<List<Etat>>
        get() = _etats

    val voyages: LiveData<List<Voyage>>
        get() = _voyages


    private fun findAllUsers() {
        db.collection("users").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val allUsers = querySnapshot!!.toObjects(Utilisateur::class.java)
            _users.postValue(allUsers)
        }
    }

    private fun findAllEtat() {
        db.collection("etats").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val allItems = querySnapshot!!.toObjects(Etat::class.java)
            _etats.postValue(allItems)
        }
    }

    private fun findAllVoyage() {
        db.collection("voyages").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val allItems = querySnapshot!!.toObjects(Voyage::class.java)
            _voyages.postValue(allItems)
        }
    }

    fun updateBillet(billet: Billet): Task<Void> {
        return db.collection("billets")
            .document(billet.idBillet)
            .set(billet)
    }
}