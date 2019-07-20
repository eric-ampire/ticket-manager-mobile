package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.model.entity.Voyage

class DetailBilletViewModel(var billet: Billet) : ViewModel(), KoinComponent {

    private val db by lazy { FirebaseFirestore.getInstance() }

    private val _user by lazy {
        MutableLiveData<Utilisateur>().also {
            findUserById(billet.idUtilisateur)
        }
    }

    private val _etat by lazy {
        MutableLiveData<String>().also {
            findEtatById(billet.idEtat)
        }
    }

    private val _voyage by lazy {
        MutableLiveData<Voyage>().also {
            findVoyageById(billet.idVoyage)
        }
    }

    val etat: LiveData<String>
        get() = _etat

    val user: LiveData<Utilisateur>
        get() = _user

    val voyage: LiveData<Voyage>
        get() = _voyage



    private fun findVoyageById(idVoyage: String) {

        val voyageRef = db.collection("voyages").document(idVoyage)
        voyageRef.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _voyage.postValue(snapshot.toObject(Voyage::class.java))
        }
    }

    private fun findEtatById(idEtat: String) {

        val etatRef = db.collection("etats").document(idEtat)
        etatRef.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _etat.postValue(snapshot.toObject(Etat::class.java)?.nomEtat)
        }
    }

    private fun findUserById(idUtilisateur: String) {

        val userRef = db.collection("users").document(idUtilisateur)
        userRef.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) return@addSnapshotListener
            _user.postValue(snapshot.toObject(Utilisateur::class.java))
        }
    }
}