package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import kotlin.properties.Delegates

class ModifierBusViewModel : ViewModel(), KoinComponent {

    var bus: Bus by Delegates.notNull()
    val agences: LiveData<List<Agence>>
        get() = _agences

    private val _agences: MutableLiveData<List<Agence>> by lazy {
        MutableLiveData<List<Agence>>().also {
            findAllAgence()
        }
    }

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun modifierBus(bus: Bus): Task<Void> {
        return db.collection("bus")
            .document(bus.idBus)
            .set(bus)
    }

    private fun findAllAgence() {
        val agenceCol = db.collection("agences")
        agenceCol.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val mAgences = querySnapshot!!.toObjects(Agence::class.java)
            _agences.postValue(mAgences)
        }
    }
}