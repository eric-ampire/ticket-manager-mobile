package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import org.pbreakers.mobile.getticket.model.repository.EtatRepository
import org.pbreakers.mobile.getticket.model.repository.LieuRepository
import javax.inject.Inject

class DetailVoyageViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var agencyRepository: AgenceRepository
    @Inject lateinit var etatRepository: EtatRepository
    @Inject lateinit var lieuRepository: LieuRepository
    @Inject lateinit var busRepository: BusRepository

    lateinit var voyage: Voyage

    val prov   = ObservableField<String>()
    val desti  = ObservableField<String>()
    val etat   = ObservableField<String>()
    val bus    = ObservableField<Bus>()
    val agence = ObservableField<Agence>()

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
                bus.set(aBus)
                agence.set(agency)
            }
        }
    }

    private fun findEtatById(id: Long) {
        etatRepository.findById(id).observeForever {
            etat.set(it.nomEtat)
        }
    }
}