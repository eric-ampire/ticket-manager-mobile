package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.model.repository.*
import javax.inject.Inject

class ModifierVoyageViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var roleRepository: RoleRepository
    @Inject lateinit var agencyRepository: AgenceRepository
    @Inject lateinit var etatRepository: EtatRepository
    @Inject lateinit var transitRepository: TransitRepository
    @Inject lateinit var lieuRepository: LieuRepository
    @Inject lateinit var userRepository: UtilisateurRepository
    @Inject lateinit var busRepository: BusRepository
    @Inject lateinit var voyageRepository: VoyageRepository
    @Inject lateinit var pointArretRepository: PointArretRepository

    val role     = MutableLiveData<List<Role>>().apply { value = arrayListOf() }
    val agences  = MutableLiveData<List<Agence>>().apply { value = arrayListOf() }
    val bus      = MutableLiveData<List<Bus>>().apply { value = arrayListOf() }
    val etats    = MutableLiveData<List<Etat>>().apply { value = arrayListOf() }
    val transits = MutableLiveData<List<Transit>>().apply { value = arrayListOf() }
    val lieu     = MutableLiveData<List<Lieu>>().apply { value = arrayListOf() }
    val voyages  = MutableLiveData<List<Voyage>>().apply { value = arrayListOf() }

    init {
        val application = app as App
        application.appComponent.inject(this)

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