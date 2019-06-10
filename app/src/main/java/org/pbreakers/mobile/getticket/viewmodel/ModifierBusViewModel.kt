package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import javax.inject.Inject

class ModifierBusViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var bus: Bus

    val agences = MutableLiveData<List<Agence>>().apply {
        value = arrayListOf()
    }

    @Inject lateinit var agenceRepository: AgenceRepository
    @Inject lateinit var busRepository: BusRepository

    init {
        val application = app as App
        application.appComponent.inject(this)
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