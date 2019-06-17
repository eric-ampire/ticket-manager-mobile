package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository

class BusDetailViewModel : ViewModel(), KoinComponent {

    lateinit var bus: Bus

    private val _agences  = MutableLiveData<Agence>()
    val agences: LiveData<Agence>
        get() = _agences


    private val repository: AgenceRepository by inject()

    fun init() {
        findAgenceById(bus.idAgence)
    }

    private fun findAgenceById(id: Long) {
        repository.findById(id).observeForever {
            _agences.postValue(it)
        }
    }
}