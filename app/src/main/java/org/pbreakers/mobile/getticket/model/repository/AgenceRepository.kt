package org.pbreakers.mobile.getticket.model.repository

import io.reactivex.Maybe
import org.pbreakers.mobile.getticket.model.dao.AgenceDao
import org.pbreakers.mobile.getticket.model.entity.Agence
import org.pbreakers.mobile.getticket.model.entity.Bus
import javax.inject.Inject

class AgenceRepository @Inject constructor(private val dao: AgenceDao) {

    fun findAll(): Maybe<List<Agence>> {
        refresh()
        return dao.findAll()
    }

    fun findById(id: Int): Maybe<Agence> {
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