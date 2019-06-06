package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.dao.BilletDao
import javax.inject.Inject

class BilletRepository @Inject constructor(private val dao: BilletDao) {

    fun count(): LiveData<Int> {
        refresh()
        return dao.count()
    }

    private fun refresh() {

    }
}
