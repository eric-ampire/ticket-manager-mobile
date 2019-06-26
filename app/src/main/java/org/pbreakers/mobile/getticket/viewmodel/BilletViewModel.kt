package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.adapter.BilletAdapter
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.util.LoadingState

class BilletViewModel : ViewModel(), KoinComponent {

    lateinit var adapter: BilletAdapter
    private val loadingState = MutableLiveData<LoadingState>()


    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    init {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val billetCol = db.collection("billets").whereEqualTo("idUtilisateur", currentUserId)

        loadingState.postValue(LoadingState.RUNNING)

        billetCol.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null || querySnapshot == null) {
                loadingState.postValue(LoadingState.error(firebaseFirestoreException?.message))
                return@addSnapshotListener
            }

            val billets = querySnapshot.toObjects(Billet::class.java)
            adapter.submitList(billets)
            loadingState.postValue(LoadingState.LOADED)
        }
    }

    fun findCurrentUserTicket() {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val billetCol = db.collection("billets").whereEqualTo("idUtilisateur", currentUserId)
        billetCol.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null || querySnapshot == null) return@addSnapshotListener

            val billets = querySnapshot.toObjects(Billet::class.java)
            adapter.submitList(billets)
        }
    }

    fun findAllTicket() {
        val billetCol = db.collection("billets")
        billetCol.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null || querySnapshot == null) return@addSnapshotListener

            val billets = querySnapshot.toObjects(Billet::class.java)
            adapter.submitList(billets)
        }
    }

    fun getLoadingState(): LiveData<LoadingState> = loadingState

    fun findAll(): LiveData<List<Billet>> {
        return MutableLiveData<List<Billet>>()
    }

    fun deleteBillet(billet: Billet): Task<Void> {
        return db.collection("billets").document(billet.idBillet).delete()
    }
}