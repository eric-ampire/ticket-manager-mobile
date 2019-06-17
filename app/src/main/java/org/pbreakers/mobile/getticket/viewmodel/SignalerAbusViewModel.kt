package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.BusRepository

class SignalerAbusViewModel : KoinComponent, ViewModel() {

    private val busRepository: BusRepository by inject()

    val bus = MutableLiveData<List<Bus>>().apply { value = arrayListOf() }


    init {
        busRepository.findAllLiveData().observeForever { bus.value = it }
    }
}