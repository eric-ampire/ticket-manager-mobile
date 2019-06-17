package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import io.reactivex.Maybe
import io.reactivex.Single
import org.pbreakers.mobile.getticket.model.api.EtatApi
import org.pbreakers.mobile.getticket.model.dao.EtatDao
import org.pbreakers.mobile.getticket.model.entity.Etat
import javax.inject.Inject

class EtatRepository(private val dao: EtatDao, private val api: EtatApi) {

    fun findAll(): LiveData<List<Etat>> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }

    fun add(etat: Etat, function: () -> Unit) {
        dao.add(etat)
        function()
    }

    fun findById(id: Long): LiveData<Etat> {
        refresh()
        return dao.findById(id)
    }

    fun findByName(name: String): Maybe<Etat> {
        // Todo: You have to find the state in the remote db
        return dao.findEtatName(name)
    }
}