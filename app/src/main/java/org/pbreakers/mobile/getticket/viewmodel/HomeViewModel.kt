package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.adapter.VoyageAdapter
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.util.LoadingState


class HomeViewModel : ViewModel(), KoinComponent {

    lateinit var adapter: VoyageAdapter

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun init() {

        loadingState.postValue(LoadingState.RUNNING)
        db.collection("voyages").also {
            it.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null && querySnapshot == null) {
                    loadingState.postValue(LoadingState.error(firebaseFirestoreException.message))
                    return@addSnapshotListener
                }
                val voyages = querySnapshot?.toObjects(Voyage::class.java)
                adapter.submitList(voyages!!)

                loadingState.postValue(LoadingState.LOADED)
            }
        }
    }

    private val loadingState = MutableLiveData<LoadingState>()

    fun getLoadingState(): LiveData<LoadingState> = loadingState

    fun deleteVoyage(item: Voyage): DocumentReference {
        return db.collection("voyages").document(item.idVoyage)
    }
}