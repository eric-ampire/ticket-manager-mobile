package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.BusRepository

class SignalerAbusViewModel : KoinComponent, ViewModel() {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    val bus = MutableLiveData<List<Bus>>().apply {
        value = arrayListOf()
    }

    init {
        db.collection("bus").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) {
                return@addSnapshotListener
            }

            val allBus = querySnapshot?.toObjects(Bus::class.java)
            bus.postValue(allBus)
        }
    }

    private fun findBus() {

    }
}