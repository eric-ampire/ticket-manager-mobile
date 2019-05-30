package org.pbreakers.mobile.getticket.model.repository

import androidx.paging.DataSource
import org.pbreakers.mobile.getticket.model.dao.BusDao
import org.pbreakers.mobile.getticket.model.entity.Bus
import javax.inject.Inject

// Todo: You have to inject bus Api
class BusRepository @Inject constructor(val dao: BusDao) {


    fun findAll(): DataSource.Factory<Int, Bus> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }
}