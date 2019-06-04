package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.dao.EtatDao
import org.pbreakers.mobile.getticket.model.dao.RoleDao
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Role
import javax.inject.Inject

class RoleRepository @Inject constructor(private val dao: RoleDao) {

    fun findAll(): LiveData<List<Role>> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }
}