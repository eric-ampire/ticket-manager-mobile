package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import org.pbreakers.mobile.getticket.model.dao.UtilisateurDao
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import javax.inject.Inject

class UtilisateurRepository @Inject constructor(private val dao: UtilisateurDao) {

    fun findByPseudoAndPassword(pseudo: String, password: String): LiveData<Utilisateur> {
        return dao.findByPseudoAndPassword(pseudo, password)
    }

    fun add(utilisateur: Utilisateur, function: () -> Unit) {
        // Todo: All user must be save in the remote db
        dao.add(utilisateur)
        function()
    }

    fun add(utilisateur: Utilisateur) {
        dao.add(utilisateur)
    }

    fun findById(idUtilisateur: Long): LiveData<Utilisateur> {
        // Todo: Find user from remote db
        return dao.findById(idUtilisateur)
    }

    fun findAll(): LiveData<List<Utilisateur>> {
        // Todo: Find in remote db
        return dao.findAll()
    }
}