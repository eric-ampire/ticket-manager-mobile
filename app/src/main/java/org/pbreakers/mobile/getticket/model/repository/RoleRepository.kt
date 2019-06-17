package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.api.RoleApi
import org.pbreakers.mobile.getticket.model.dao.EtatDao
import org.pbreakers.mobile.getticket.model.dao.RoleDao
import org.pbreakers.mobile.getticket.model.entity.Etat
import org.pbreakers.mobile.getticket.model.entity.Role
import javax.inject.Inject

class RoleRepository(private val dao: RoleDao, private val api: RoleApi) {

    fun findAll(): LiveData<List<Role>> {
        refresh()
        return dao.findAll()
    }

    private fun refresh() {
        // Todo: You have to implement this method
    }

    fun add(role: Role, function: () -> Unit) {
        dao.add(role)
        function()
    }
}