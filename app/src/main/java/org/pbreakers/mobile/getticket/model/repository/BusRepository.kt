package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.pbreakers.mobile.getticket.model.dao.BusDao
import org.pbreakers.mobile.getticket.model.entity.Bus
import javax.inject.Inject

// Todo: You have to inject bus Api
class BusRepository @Inject constructor(private val dao: BusDao) {


    // Todo: You have to remove it
    fun findAll(): DataSource.Factory<Int, Bus> {
        refresh()
        return dao.findAll()
    }

    fun count(): LiveData<Int> {
        refresh()
        return dao.count()
    }

    fun findAllLiveData(): LiveData<List<Bus>> {
        refresh()
        return dao.findAllLiveData()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }

    fun add(bus: Bus, function: () -> Unit) {
        dao.add(bus)
        function()
    }

    fun remove(item: Bus): Completable {
        // Remove in remote db
        // Remove on local database on succes
        return dao.remove(item)
    }
}