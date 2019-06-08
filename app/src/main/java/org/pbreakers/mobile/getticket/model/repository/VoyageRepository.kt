package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import io.reactivex.Completable
import org.pbreakers.mobile.getticket.model.dao.VoyageDao
import org.pbreakers.mobile.getticket.model.entity.Voyage
import javax.inject.Inject

class VoyageRepository @Inject constructor(private val dao: VoyageDao) {

    fun findAll(): DataSource.Factory<Int, Voyage> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {

    }

    fun add(voyage: Voyage, function: () -> Unit) {
        dao.add(voyage)
        function()
    }

    fun findAllLiveData(): LiveData<List<Voyage>> {
        refresh()
        return dao.findAllLiveData()
    }

    fun remove(item: Voyage): Completable {
        return dao.remove(item)
    }
}