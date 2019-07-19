package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Role
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.util.LoadingState

class BilletViewModel : ViewModel(), KoinComponent {

    private val loadingState = MutableLiveData<LoadingState>()

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    private val _tickets = MutableLiveData<List<Billet>>()
    val tickets: LiveData<List<Billet>>
        get() = _tickets

    init {

        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid

        val userRef = db.collection("users").document(currentUserId)
        userRef.addSnapshotListener { snapshot, exception ->
            if (exception != null || snapshot == null) {
                loadingState.postValue(LoadingState.error(exception?.message))
                return@addSnapshotListener
            }

            val user = snapshot.toObject(Utilisateur::class.java) ?: return@addSnapshotListener

            if (user.idRole == Role.ADMIN) {
                findAllTicket()
            } else {
                findCurrentUserTicket()
            }
        }
    }

    private fun findCurrentUserTicket() {
        val currentUserId = FirebaseAuth.getInstance().currentUser!!.uid
        val billetCol = db.collection("billets").whereEqualTo("idUtilisateur", currentUserId)

        loadingState.postValue(LoadingState.RUNNING)
        billetCol.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null || querySnapshot == null) {
                loadingState.postValue(LoadingState.error(firebaseFirestoreException?.message))
                return@addSnapshotListener
            }

            val billets = querySnapshot.toObjects(Billet::class.java)
            _tickets.postValue(billets)
            loadingState.postValue(LoadingState.LOADED)
        }
    }

    private fun findAllTicket() {
        val billetCol = db.collection("billets")
        billetCol.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null || querySnapshot == null) {
                loadingState.postValue(LoadingState.error(firebaseFirestoreException?.message))
                return@addSnapshotListener
            }

            val billets = querySnapshot.toObjects(Billet::class.java)
            _tickets.postValue(billets)
            loadingState.postValue(LoadingState.LOADED)
        }
    }

    fun getLoadingState(): LiveData<LoadingState> = loadingState

    fun deleteBillet(billet: Billet): Task<Void> {
        return db.collection("billets").document(billet.idBillet).delete()
    }
}