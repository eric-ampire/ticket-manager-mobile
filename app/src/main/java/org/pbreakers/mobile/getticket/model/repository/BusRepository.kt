package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import io.reactivex.Completable
import org.pbreakers.mobile.getticket.model.api.BusApi
import org.pbreakers.mobile.getticket.model.dao.BusDao
import org.pbreakers.mobile.getticket.model.entity.Bus

// Todo: You have to inject bus Api
class BusRepository(private val dao: BusDao, private val api: BusApi) {


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

    fun findById(id: Long): LiveData<Bus> {
        refresh()
        return dao.findById(id)
    }

    fun update(bus: Bus): Completable {
        // Todo: Update online
        return dao.update(bus)
    }
}