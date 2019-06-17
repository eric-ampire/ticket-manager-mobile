package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.repository.BilletRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import javax.inject.Inject

class MainViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var busRepository: BusRepository
    @Inject lateinit var billetRepository: BilletRepository

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

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