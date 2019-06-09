package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Completable
import io.reactivex.Maybe
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.*
import javax.inject.Inject

class DetailVoyageViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var agencyRepository: AgenceRepository
    @Inject lateinit var etatRepository: EtatRepository
    @Inject lateinit var lieuRepository: LieuRepository
    @Inject lateinit var busRepository: BusRepository
    @Inject lateinit var billetRepository: BilletRepository

    lateinit var voyage: Voyage

    val prov   = ObservableField<String>()
    val desti  = ObservableField<String>()
    val etat   = ObservableField<String>()
    val bus    = ObservableField<String>()
    val agence = ObservableField<String>()

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

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