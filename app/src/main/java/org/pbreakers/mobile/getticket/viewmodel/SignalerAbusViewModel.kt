package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import javax.inject.Inject

class SignalerAbusViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var busRepository: BusRepository

    val bus = MutableLiveData<List<Bus>>().apply { value = arrayListOf() }


    init {
        val application = app as App
        application.appComponent.inject(this)

        busRepository.findAllLiveData().observeForever { bus.value = it }
    }
}