package org.pbreakers.mobile.getticket.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.adapter.VoyageAdapter
import org.pbreakers.mobile.getticket.model.entity.Lieu
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.LieuRepository
import org.pbreakers.mobile.getticket.model.repository.VoyageRepository


class HomeViewModel : ViewModel(), KoinComponent {

    lateinit var adapter: VoyageAdapter
    private val repository: VoyageRepository   by inject()
    private val lieuRepository: LieuRepository by inject()

    fun findLieuById(id: Long): LiveData<Lieu> {
        return lieuRepository.findById(id)
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

    fun deleteVoyage(item: Voyage): Completable {
        return repository.remove(item)
    }
}