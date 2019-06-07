package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.dao.BilletDao
import org.pbreakers.mobile.getticket.model.entity.Billet
import javax.inject.Inject

class BilletRepository @Inject constructor(private val dao: BilletDao) {

    fun count(): LiveData<Int> {
        refresh()
        return dao.count()
    }

    private fun refresh() {

    }

    fun findByIdUser(id: Long): LiveData<List<Billet>> {
        refresh()
        return dao.findByIdUser(id)
    }

    fun findAll(): LiveData<List<Billet>> {
        refresh()
        return dao.findAll()
    }
}
