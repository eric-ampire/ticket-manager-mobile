package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.adapter.BilletAdapter
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.repository.BilletRepository
import org.pbreakers.mobile.getticket.model.repository.EtatRepository

class BilletViewModel : ViewModel(), KoinComponent {

    lateinit var adapter: BilletAdapter
    private val repository: BilletRepository by inject()
    private val etatEepository: EtatRepository by inject()

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(3)
            .build()

        val data = LivePagedListBuilder(repository.findAll(), config).build()

        data.observeForever {
            adapter.submitList(it)
        }
    }

    fun findBilletByIdUser(id: Long) {
        repository.findByIdUser(id)
    }

    fun findEtatById(id: Long): LiveData<Etat> {
        return etatEepository.findById(id)
    }

    fun findAll(): LiveData<List<Billet>> {
        return repository.findAllLiveData()
    }

    fun deleteBillet(billet: Billet): Completable {
        return repository.remove(billet)
    }
}