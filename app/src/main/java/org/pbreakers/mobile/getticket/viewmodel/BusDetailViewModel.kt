package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.widget.ArrayAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import javax.inject.Inject

class BusDetailViewModel(val app: Application) : AndroidViewModel(app) {

    val agency = ObservableField<String>()
    lateinit var agencyAdapter: ArrayAdapter<Agence>

    @Inject lateinit var repository: AgenceRepository

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun init() {
        repository.findAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<List<Agence>> {
                override fun onSuccess(t: List<Agence>) {
                }

                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                }
            })

    }
}