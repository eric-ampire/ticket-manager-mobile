package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.dao.TransitDao
import org.pbreakers.mobile.getticket.model.entity.Transit
import javax.inject.Inject

class TransitRepository @Inject constructor(private val dao: TransitDao)  {
    fun findAll(): LiveData<List<Transit>> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }
}
