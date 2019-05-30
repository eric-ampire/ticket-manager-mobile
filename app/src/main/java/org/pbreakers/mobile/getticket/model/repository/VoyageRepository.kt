package org.pbreakers.mobile.getticket.model.repository

import androidx.paging.DataSource
import org.pbreakers.mobile.getticket.model.dao.VoyageDao
import org.pbreakers.mobile.getticket.model.entity.Voyage
import javax.inject.Inject

class VoyageRepository @Inject constructor(val dao: VoyageDao) {
    fun findAll(): DataSource.Factory<Int, Voyage> {
        return dao.findAll()

    }
}