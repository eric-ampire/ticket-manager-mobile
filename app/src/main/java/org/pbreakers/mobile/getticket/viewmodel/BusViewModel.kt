package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import org.pbreakers.mobile.getticket.adapter.BusAdapter
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import org.pbreakers.mobile.getticket.util.LoadingState
import javax.inject.Inject

class BusViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var repository: BusRepository
    lateinit var busAdapter: BusAdapter
    val loadingState = MutableLiveData<LoadingState>()

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun init() {

        repository.findAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<List<Bus>> {
                override fun onSuccess(t: List<Bus>) {
                    busAdapter.submitList(t)
                    loadingState.postValue(LoadingState.LOADED)
                }

                override fun onComplete() {
                    loadingState.postValue(LoadingState.LOADED)
                }

                override fun onSubscribe(d: Disposable) {
                    loadingState.postValue(LoadingState.RUNNING)
                }

                override fun onError(e: Throwable) {
                    loadingState.postValue(LoadingState.error(e.message))
                }
            })

    }
}