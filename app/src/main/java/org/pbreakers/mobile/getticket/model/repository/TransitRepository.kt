package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.api.TransitApi
import org.pbreakers.mobile.getticket.model.dao.TransitDao
import org.pbreakers.mobile.getticket.model.entity.Transit

class TransitRepository(private val dao: TransitDao, private val api: TransitApi)  {
    fun findAll(): LiveData<List<Transit>> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }

    fun add(transit: Transit, function: () -> Unit) {
        dao.add(transit)
        function()
    }
}
