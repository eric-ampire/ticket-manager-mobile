package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.model.repository.BilletRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository

class MainViewModel : ViewModel(), KoinComponent {

    private val busRepository: BusRepository       by inject()
    private val billetRepository: BilletRepository by inject()

    private val countBus: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {

        }
    }

    private val countBillet: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also {
            getBilletCount()
        }
    }

    private fun getBusCount() {

    }

    private fun getBilletCount() {
    }



    fun countBillet(): LiveData<Int> = countBus
    fun countBus(): LiveData<Int> = countBillet
}