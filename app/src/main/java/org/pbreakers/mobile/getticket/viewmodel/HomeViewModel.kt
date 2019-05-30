package org.pbreakers.mobile.getticket.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import org.pbreakers.mobile.getticket.adapter.VoyageAdapter
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.repository.VoyageRepository
import javax.inject.Inject

class HomeViewModel(val app: Application) : AndroidViewModel(app) {

    lateinit var adapter: VoyageAdapter
    @Inject lateinit var repository: VoyageRepository

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
}