package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import android.view.View
import androidx.databinding.ObservableInt
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import org.pbreakers.mobile.getticket.adapter.BilletAdapter
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.entity.Billet
import org.pbreakers.mobile.getticket.model.repository.BilletRepository
import javax.inject.Inject

class BilletViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var adapter: BilletAdapter
    @Inject lateinit var repository: BilletRepository

    val isEmptyData = ObservableInt(View.VISIBLE)

    init {
        val application = app as App
        application.appComponent.inject(this)

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

    fun findAll(): LiveData<List<Billet>> {
        return repository.findAllLiveData()
    }
}