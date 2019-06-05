package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.dao.LieuDao
import org.pbreakers.mobile.getticket.model.entity.Lieu
import javax.inject.Inject

class LieuRepository @Inject constructor(val dao: LieuDao) {
    fun findAll(): LiveData<List<Lieu>> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }

    fun add(lieu: Lieu, function: () -> Unit) {
        dao.add(lieu)
        function()
    }
}