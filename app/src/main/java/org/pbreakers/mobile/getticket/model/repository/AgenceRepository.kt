package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.dao.AgenceDao
import org.pbreakers.mobile.getticket.model.entity.Agence
import javax.inject.Inject

class AgenceRepository @Inject constructor(private val dao: AgenceDao) {

    fun findAll(): LiveData<List<Agence>> {
        refresh()
        return dao.findAll()
    }

    fun findById(id: Int): LiveData<Agence> {
        refresh()
        return dao.findById(id)
    }

    private fun refresh() {

    }

    fun add(agency: Agence, function: () -> Unit) {
        dao.add(agency)
        function()
    }
}