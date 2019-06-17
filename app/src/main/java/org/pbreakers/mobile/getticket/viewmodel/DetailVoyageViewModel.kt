package org.pbreakers.mobile.getticket.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Maybe
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.*

class DetailVoyageViewModel : ViewModel(), KoinComponent {

    private val agencyRepository: AgenceRepository by inject()
    private val etatRepository: EtatRepository     by inject()
    private val lieuRepository: LieuRepository     by inject()
    private val busRepository: BusRepository       by inject()
    private val billetRepository: BilletRepository by inject()

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

    private fun findProvenanceById(id: Long) {
        lieuRepository.findById(id).observeForever {
            prov.set(it.nomLieu)
        }
    }

    private fun findDestinationById(id: Long) {
        lieuRepository.findById(id).observeForever {
            desti.set(it.nomLieu)
        }
    }

    private fun findBusById(id: Long) {
        busRepository.findById(id).observeForever { aBus ->
            agencyRepository.findById(aBus.idAgence).observeForever { agency ->
                bus.set(aBus.nomBus)
                agence.set(agency.nomAgence)
            }
        }
    }

    private fun findEtatById(id: Long) {
        etatRepository.findById(id).observeForever {
            etat.set(it.nomEtat)
        }
    }

    fun findEtatByName(name: String): Maybe<Etat> {
        return etatRepository.findByName(name)
    }

    fun saveBillet(ticket: Billet): Completable{
        return billetRepository.add(ticket)
    }
}