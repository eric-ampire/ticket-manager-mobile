package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import io.reactivex.Completable
import io.reactivex.Single
import org.pbreakers.mobile.getticket.model.api.BilletApi
import org.pbreakers.mobile.getticket.model.dao.BilletDao
import org.pbreakers.mobile.getticket.model.entity.Billet

class BilletRepository(private val dao: BilletDao, private val api: BilletApi) {

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

    fun findById(id: Long): Single<Billet> {
        refresh()
        return dao.findByIdWithRx(id)
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

    fun update(billet: Billet): Completable {
        // Todo: Update remote before
        return dao.update(billet)
    }
}
