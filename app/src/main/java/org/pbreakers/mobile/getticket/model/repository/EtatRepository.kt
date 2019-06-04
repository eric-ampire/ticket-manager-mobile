package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.dao.EtatDao
import org.pbreakers.mobile.getticket.model.entity.Etat
import javax.inject.Inject

class EtatRepository @Inject constructor(private val dao: EtatDao) {

    fun findAll(): LiveData<List<Etat>> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }
}