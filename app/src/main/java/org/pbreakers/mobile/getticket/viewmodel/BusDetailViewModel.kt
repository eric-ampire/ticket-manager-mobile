package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import kotlin.properties.Delegates

class BusDetailViewModel : ViewModel(), KoinComponent {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    var bus: Bus by Delegates.notNull()
    val agences: LiveData<Agence>
        get() = _agences

    private val _agences by lazy {
        MutableLiveData<Agence>().also {
            findAgenceById(bus.idAgence)
        }
    }

    private fun findAgenceById(idAgence: String) {
        val docRef = db.collection("agences").document(idAgence)
        docRef.addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null || documentSnapshot == null) return@addSnapshotListener

            val mAgence = documentSnapshot.toObject(Agence::class.java)
            _agences.postValue(mAgence)
        }
    }
}