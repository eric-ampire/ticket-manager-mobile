package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.repository.BilletRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository

class MainViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var busRepository: BusRepository
    lateinit var billetRepository: BilletRepository

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun countBillet(): LiveData<Int> {
        return billetRepository.count()
    }

    fun countBus(): LiveData<Int> {
        return busRepository.count()
    }
}