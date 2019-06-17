package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.model.repository.*

class EnregViewModel : ViewModel(), KoinComponent {

    private val roleRepository: RoleRepository             by inject()
    private val agencyRepository: AgenceRepository         by inject()
    private val etatRepository: EtatRepository             by inject()
    private val transitRepository: TransitRepository       by inject()
    private val lieuRepository: LieuRepository             by inject()
    private val userRepository: UtilisateurRepository      by inject()
    private val busRepository: BusRepository               by inject()
    private val voyageRepository: VoyageRepository         by inject()
    private val pointArretRepository: PointArretRepository by inject()

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

    fun findRole(): LiveData<List<Role>> {
        return roleRepository.findAll()
    }

    fun saveAgence(agency: Agence, function: () -> Unit) {
        agencyRepository.add(agency, function)
    }

    fun saveEtat(etat: Etat, function: () -> Unit) {
        etatRepository.add(etat, function)
    }

    fun saveLieu(lieu: Lieu, function: () -> Unit) {
        lieuRepository.add(lieu, function)
    }

    fun saveRole(role: Role, function: () -> Unit) {
        roleRepository.add(role, function)
    }

    fun saveTransit(transit: Transit, function: () -> Unit) {
        transitRepository.add(transit, function)
    }

    fun saveUser(user: Utilisateur, function: () -> Unit) {
        userRepository.add(user, function)
    }

    fun saveBus(bus: Bus, function: () -> Unit) {
        busRepository.add(bus, function)
    }

    fun findAgency(): LiveData<List<Agence>> {
        return agencyRepository.findAll()
    }

    fun saveVoyage(voyage: Voyage, function: () -> Unit) {
        voyageRepository.add(voyage, function)
    }

    fun savePointArret(pointArret: PointArret, function: () -> Unit) {
        pointArretRepository.add(pointArret, function)
    }
}