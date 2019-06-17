package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository

class ModifierBusViewModel : ViewModel(), KoinComponent {

    lateinit var bus: Bus

    val agences = MutableLiveData<List<Agence>>().apply {
        value = arrayListOf()
    }

    private val agenceRepository: AgenceRepository by inject()
    private val busRepository: BusRepository       by inject()

    init {
        findAllAgence()
    }

    fun init() {
        findAllAgence()
    }

    fun modifierBus(bus: Bus): Completable {
        return busRepository.update(bus)
    }

    private fun findAllAgence() {
        agenceRepository.findAll().observeForever {
            agences.postValue(it)
        }
    }
}