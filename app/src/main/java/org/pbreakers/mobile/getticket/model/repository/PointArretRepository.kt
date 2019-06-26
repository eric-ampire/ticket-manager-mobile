package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.api.PointArretApi
import org.pbreakers.mobile.getticket.model.dao.PointArretDao
import org.pbreakers.mobile.getticket.model.entity.PointArret

class PointArretRepository(private val dao: PointArretDao, private val api: PointArretApi) {
    fun findAll(): LiveData<List<PointArret>> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }

    fun add(pointArret: PointArret, function: () -> Unit) {
        dao.add(pointArret)
        function()
    }
}
