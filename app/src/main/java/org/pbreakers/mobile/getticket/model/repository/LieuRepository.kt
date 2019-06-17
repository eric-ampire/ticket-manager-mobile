package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.api.LieuApi
import org.pbreakers.mobile.getticket.model.dao.LieuDao
import org.pbreakers.mobile.getticket.model.entity.Lieu

class LieuRepository(private val dao: LieuDao, private val api: LieuApi) {
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

    fun findById(id: Long): LiveData<Lieu> {
        refresh()
        return dao.findById(id)
    }
}
