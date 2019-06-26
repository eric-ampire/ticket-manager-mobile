package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.adapter.BusAdapter
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import org.pbreakers.mobile.getticket.util.LoadingState

class BusViewModel : ViewModel(), KoinComponent {

    lateinit var adapter: BusAdapter

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    val loadingState = MutableLiveData<LoadingState>()
    private val agence = MutableLiveData<Agence>()

    fun init() {
        val busCol = db.collection("bus")
        loadingState.postValue(LoadingState.RUNNING)
        busCol.addSnapshotListener { querySnapshot, firebaseFirestoreException ->

            if (firebaseFirestoreException != null || querySnapshot == null) {
                loadingState.postValue(LoadingState.error(firebaseFirestoreException?.message))
                return@addSnapshotListener
            }
            val bus = querySnapshot.toObjects(Bus::class.java)
            adapter.submitList(bus)
            loadingState.postValue(LoadingState.LOADED)
        }
    }

    fun findAgenceById(id: String): LiveData<Agence> {
        val agenceRef = db.collection("agences").document(id)
        agenceRef.get().addOnCompleteListener {
            if (it.isSuccessful) {
                agence.postValue(it.result?.toObject(Agence::class.java))
            }
        }

        return agence
    }

    fun getLoadingState(): LiveData<LoadingState> = loadingState

    fun removeBus(item: Bus): Task<Void> {
        return db.collection("bus")
            .document(item.idBus)
            .delete()
    }
}