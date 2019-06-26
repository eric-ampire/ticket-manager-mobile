package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Single
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.repository.BilletRepository

class ScannerViewModel : ViewModel(), KoinComponent {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    fun findBilletById(idBillet: String): Task<DocumentSnapshot> {
        return db.collection("billets").document(idBillet).get()
    }

    fun updateBillet(billet: Billet): Completable {
        // Todo: Implementation
        TODO()
    }
}