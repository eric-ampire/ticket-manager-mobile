package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.model.repository.*
import javax.inject.Inject

class ModifierVoyageViewModel : ViewModel(), KoinComponent {

    private val roleRepository: RoleRepository             by inject()
    private val agencyRepository: AgenceRepository         by inject()
    private val etatRepository: EtatRepository             by inject()
    private val transitRepository: TransitRepository       by inject()
    private val lieuRepository: LieuRepository             by inject()
    private val busRepository: BusRepository               by inject()
    private val voyageRepository: VoyageRepository         by inject()

    val role     = MutableLiveData<List<Role>>().apply { value = arrayListOf() }
    val agences  = MutableLiveData<List<Agence>>().apply { value = arrayListOf() }
    val bus      = MutableLiveData<List<Bus>>().apply { value = arrayListOf() }
    val etats    = MutableLiveData<List<Etat>>().apply { value = arrayListOf() }
    val transits = MutableLiveData<List<Transit>>().apply { value = arrayListOf() }
    val lieu     = MutableLiveData<List<Lieu>>().apply { value = arrayListOf() }
    val voyages  = MutableLiveData<List<Voyage>>().apply { value = arrayListOf() }

    init {
        roleRepository.findAll().observeForever   { role.value = it }
        agencyRepository.findAll().observeForever { agences.value = it }
        busRepository.findAllLiveData().observeForever { bus.value = it }
        etatRepository.findAll().observeForever { etats.value = it }
        transitRepository.findAll().observeForever { transits.value = it }
        lieuRepository.findAll().observeForever { lieu.value = it }
        voyageRepository.findAllLiveData().observeForever { voyages.value = it }
    }

    fun update(voyage: Voyage): Completable {
        return voyageRepository.update(voyage)
    }
}