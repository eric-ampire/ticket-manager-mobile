package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.api.AgenceApi
import org.pbreakers.mobile.getticket.model.dao.AgenceDao
import org.pbreakers.mobile.getticket.model.entity.Agence
import javax.inject.Inject

class AgenceRepository(private val dao: AgenceDao, private val api: AgenceApi) {

    fun findAll(): LiveData<List<Agence>> {
        refresh()
        return dao.findAll()
    }

    fun findById(id: Long): LiveData<Agence> {
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