package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.adapter.BusAdapter
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import org.pbreakers.mobile.getticket.model.repository.AgenceRepository
import org.pbreakers.mobile.getticket.model.repository.BusRepository
import org.pbreakers.mobile.getticket.util.LoadingState

class BusViewModel : ViewModel(), KoinComponent {

    private val repository: BusRepository by inject()
    private val agenceRepository: AgenceRepository by inject()
    lateinit var adapter: BusAdapter

    val loadingState = MutableLiveData<LoadingState>()

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