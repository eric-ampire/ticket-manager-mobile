package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
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
    val isEmptyData = ObservableInt(View.VISIBLE)


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
                    updateVisibility()
                    loadingState.postValue(LoadingState.LOADED)
                }

                override fun onComplete() {
                    updateVisibility()
                    loadingState.postValue(LoadingState.LOADED)
                }

                override fun onSubscribe(d: Disposable) {
                    loadingState.postValue(LoadingState.RUNNING)
                }

                override fun onError(e: Throwable) {
                    updateVisibility()
                    loadingState.postValue(LoadingState.error(e.message))
                }
            })

    }

    fun updateVisibility() {
        if (busAdapter.itemCount == 0) {
            isEmptyData.set(View.VISIBLE)
        } else {
            isEmptyData.set(View.GONE)
        }
    }

    fun removeBus(item: Bus): Completable {
        return repository.remove(item)
    }
}