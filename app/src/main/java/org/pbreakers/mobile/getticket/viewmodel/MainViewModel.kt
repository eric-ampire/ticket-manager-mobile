package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.model.entity.*

class MainViewModel : ViewModel(), KoinComponent {
    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    private val currentUser: MutableLiveData<Utilisateur> by lazy {
        MutableLiveData<Utilisateur>().also {
            setUserData()
        }
    }

    private val countBus: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            setBusCount()
        }
    }

    private val countBillet = MutableLiveData<Int>()

    private val countVoyage: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            setVoyagetCount()
        }
    }


    private val userRole = MutableLiveData<Role>()

    private fun setUserRole(idRole: String) {
        val userRoleRef = db.collection("roles").document(idRole)

        userRoleRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val role = querySnapshot!!.toObject(Role::class.java) ?: return@addSnapshotListener
            userRole.postValue(role)

            if (role.idRole == Role.ADMIN) {
                setAllBilletCount()
            } else {
                setBilletCount()
            }
        }
    }

    private fun setAllBilletCount() {
        val billetRef = db.collection("billets")

        billetRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val count = querySnapshot!!.toObjects(Billet::class.java).size
            countBillet.postValue(count)
        }
    }

    private fun setVoyagetCount() {
        db.collection("voyages").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val count = querySnapshot!!.toObjects(Voyage::class.java).size
            countVoyage.postValue(count)
        }
    }


    private fun setUserData() {
        val idUser = FirebaseAuth.getInstance().currentUser!!.uid
        val userRef = db.collection("users").document(idUser)

        userRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val user = querySnapshot!!.toObject(Utilisateur::class.java) ?: return@addSnapshotListener
            currentUser.postValue(user)

            setUserRole(user.idRole)
        }
    }

    private fun setBusCount() {
        db.collection("bus").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val count = querySnapshot!!.toObjects(Bus::class.java).size
            countBus.postValue(count)
        }
    }

    private fun setBilletCount() {
        val idUser = FirebaseAuth.getInstance().currentUser!!.uid
        val billetRef = db.collection("billets").whereEqualTo("idUtilisateur", idUser)

        billetRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            if (firebaseFirestoreException != null && querySnapshot == null) return@addSnapshotListener

            val count = querySnapshot!!.toObjects(Billet::class.java).size
            countBillet.postValue(count)
        }
    }



    fun countBillet(): LiveData<Int> = countBillet
    fun countBus(): LiveData<Int> = countBus
    fun getCurrentUser(): LiveData<Utilisateur> = currentUser
    fun getCurrentUserRole(): LiveData<Role> = userRole
    fun countVoyage(): LiveData<Int> = countVoyage
}