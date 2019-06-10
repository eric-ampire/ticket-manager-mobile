package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import io.reactivex.Completable
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

    fun findAll(): DataSource.Factory<Int, Billet> {
        refresh()
        return dao.findAll()
    }

    fun findAllLiveData(): LiveData<List<Billet>> {
        refresh()
        return dao.findAllLiveData()
    }

    fun add(ticket: Billet): Completable {
        // Todo: The ticket must be store online before local
        return dao.add(ticket)
    }

    fun remove(billet: Billet): Completable {
        // Todo: Delete first in remote
        return dao.remove(billet)
    }
}
