package org.pbreakers.mobile.getticket.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Completable
import io.reactivex.Maybe
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.model.repository.*

class DetailVoyageViewModel : ViewModel(), KoinComponent {

    private val db by lazy {
        FirebaseFirestore.getInstance()
    }

    lateinit var voyage: Voyage

    val prov   = ObservableField<String>()
    val desti  = ObservableField<String>()
    val etat   = ObservableField<String>()
    val bus    = ObservableField<String>()
    val agence = ObservableField<String>()

    fun init() {
        findDestinationById(voyage.idDestination)
        findProvenanceById(voyage.idProvenance)
        findEtatById(voyage.idEtat)
        findBusById(voyage.idBus)
    }

    private fun findProvenanceById(id: String) {
        val lieuRef = db.collection("lieux").document(id)
        lieuRef.get().addOnSuccessListener {
            val currentLieu = it.toObject(Lieu::class.java) ?: return@addOnSuccessListener
            prov.set(currentLieu.nomLieu)
        }.addOnFailureListener {

        }
    }

    private fun findDestinationById(id: String) {
        val lieuRef = db.collection("lieux").document(id)
        lieuRef.get().addOnSuccessListener {
            val currentLieu = it.toObject(Lieu::class.java) ?: return@addOnSuccessListener
            desti.set(currentLieu.nomLieu)
        }.addOnFailureListener {

        }
    }

    private fun findBusById(id: String) {
        // Todo: run transaction
        val busRef = db.collection("bus").document(id)
        busRef.get().addOnSuccessListener {
            val currentBus = it.toObject(Bus::class.java) ?: return@addOnSuccessListener

            val agenceRef = db.collection("agences").document(currentBus.idAgence)
            agenceRef.get().addOnSuccessListener { doc ->
                val currentAgence = doc.toObject(Agence::class.java)

                if (currentAgence != null) {
                    bus.set(currentBus.nomBus)
                    agence.set(currentAgence.nomAgence)
                }
            }
        }
    }

    private fun findEtatById(id: String) {

        val etatRef = db.collection("etats").document(id)
        etatRef.get().addOnSuccessListener {
            val currentEtat = it.toObject(Etat::class.java) ?: return@addOnSuccessListener
            etat.set(currentEtat.nomEtat)
        }.addOnFailureListener {

        }
    }
}