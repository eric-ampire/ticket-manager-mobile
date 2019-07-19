package org.pbreakers.mobile.getticket.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.model.entity.*

class DetailVoyageViewModel : ViewModel(), KoinComponent {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    lateinit var voyage: Voyage

    private val _prov = MutableLiveData<String>()
    val prov: LiveData<String>
        get() = _prov

    private val _desti = MutableLiveData<String>()
    val desti: LiveData<String>
        get() = _desti

    private val _etat = MutableLiveData<String>()
    val etat: LiveData<String>
        get() = _etat

    private val _bus = MutableLiveData<String>()
    val bus: LiveData<String>
        get() = _bus

    private val _agence = MutableLiveData<String>()
    val agence: LiveData<String>
        get() = _agence

    fun init() {
        findDestinationById(voyage.idDestination)
        findProvenanceById(voyage.idProvenance)
        findEtatById(voyage.idEtat)
        findBusById(voyage.idBus)
    }

    private fun findProvenanceById(id: String) {
        val lieuRef = db.collection("lieux").document(id)
        lieuRef.addSnapshotListener { snapshot, exception ->
            if (exception == null && snapshot?.exists() != null) {
                _prov.value = snapshot.toObject(Lieu::class.java)?.nomLieu
            }
        }
    }

    private fun findDestinationById(id: String) {
        val lieuRef = db.collection("lieux").document(id)
        lieuRef.addSnapshotListener { snapshot, exception ->
            if (exception == null && snapshot?.exists() != null) {
                _desti.value = snapshot.toObject(Lieu::class.java)?.nomLieu
            }
        }
    }

    private fun findBusById(id: String) {
        val busRef = db.collection("bus").document(id)
        busRef.addSnapshotListener { snapshot, exception ->
            if (exception == null && snapshot?.exists() != null) {
                val currentBus = snapshot.toObject(Bus::class.java) ?: return@addSnapshotListener
                val agenceRef = db.collection("agences").document(currentBus.idAgence)
                agenceRef.addSnapshotListener { snapshotAgence, exceptionAgence ->
                    if (exceptionAgence == null && snapshotAgence?.exists() != null) {
                        val currentAgence = snapshotAgence.toObject(Agence::class.java)
                        _bus.value = currentBus.nomBus
                        _agence.value = currentAgence?.nomAgence
                    }
                }
            }
        }
    }

    fun saveTicket(ticket: Billet): Task<Void> {
        // Find current user
        val billetRef = db.collection("billets").document()

        ticket.idBillet = billetRef.id

        return billetRef.set(ticket)
    }

    private fun findEtatById(id: String) {

        val etatRef = db.collection("etats").document(id)
        etatRef.addSnapshotListener { snapshot, exception ->
            if (exception == null && snapshot?.exists() != null) {
                _etat.value = snapshot.toObject(Etat::class.java)?.nomEtat
            }
        }
    }
}