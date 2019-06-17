package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.model.repository.*
import java.util.*
import javax.inject.Inject

class EnregViewModel(val app: Application) : AndroidViewModel(app) {

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