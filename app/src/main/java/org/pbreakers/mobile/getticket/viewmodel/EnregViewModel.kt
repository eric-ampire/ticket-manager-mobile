package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.model.repository.*
import javax.inject.Inject

class EnregViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var roleRepository: RoleRepository
    @Inject lateinit var agencyRepository: AgenceRepository
    @Inject lateinit var etatRepository: EtatRepository
    @Inject lateinit var transitRepository: TransitRepository
    @Inject lateinit var lieuRepository: LieuRepository
    @Inject lateinit var userRepository: UtilisateurRepository
    @Inject lateinit var busRepository: BusRepository

    val role = arrayListOf<Role>()

    init {
        val application = app as App
        application.appComponent.inject(this)

        roleRepository.findAll().observeForever {
            role.clear()
            role.addAll(it)
        }
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
}