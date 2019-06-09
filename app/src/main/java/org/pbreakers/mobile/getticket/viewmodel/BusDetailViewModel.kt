package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import javax.inject.Inject

class BusDetailViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var bus: Bus

    private val _agences  = MutableLiveData<Agence>()
    val agences: LiveData<Agence>
        get() = _agences


    @Inject lateinit var repository: AgenceRepository

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun init() {
        findAgenceById(bus.idAgence)
    }

    private fun findAgenceById(id: Long) {
        repository.findById(id).observeForever {
            _agences.postValue(it)
        }
    }
}