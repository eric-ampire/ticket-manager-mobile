package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.pbreakers.mobile.getticket.adapter.BusAdapter
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import org.pbreakers.mobile.getticket.util.LoadingState
import javax.inject.Inject

class BusViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject lateinit var repository: BusRepository
    @Inject lateinit var agenceRepository: AgenceRepository
    lateinit var adapter: BusAdapter

    val loadingState = MutableLiveData<LoadingState>()


    init {
        val application = app as App
        application.appComponent.inject(this)
    }

    fun init() {

        val config = PagedList.Config.Builder()
            .setPageSize(3)
            .build()

        val data = LivePagedListBuilder(repository.findAll(), config).build()

        data.observeForever {
            adapter.submitList(it)
        }
    }

    fun findAgenceById(id: Long): LiveData<Agence> {
        return agenceRepository.findById(id)
    }

    fun removeBus(item: Bus): Completable {
        return repository.remove(item)
    }
}