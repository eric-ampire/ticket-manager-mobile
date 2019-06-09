package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import io.reactivex.Completable
import org.pbreakers.mobile.getticket.adapter.VoyageAdapter
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Lieu
import org.pbreakers.mobile.getticket.model.entity.Voyage
import org.pbreakers.mobile.getticket.model.repository.LieuRepository
import org.pbreakers.mobile.getticket.model.repository.VoyageRepository
import javax.inject.Inject

class HomeViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var adapter: VoyageAdapter
    @Inject lateinit var repository: VoyageRepository
    @Inject lateinit var lieuRepository: LieuRepository

    val isEmptyData = ObservableInt(View.VISIBLE)

    init {
        val application = app as App
        application.appComponent.inject(this)
    }

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