package org.pbreakers.mobile.getticket.model.repository

import androidx.paging.DataSource
import org.pbreakers.mobile.getticket.model.dao.VoyageDao
import org.pbreakers.mobile.getticket.model.entity.Voyage
import javax.inject.Inject

class VoyageRepository @Inject constructor(private val dao: VoyageDao) {

    fun findAll(): DataSource.Factory<Int, Voyage> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {

    }
}