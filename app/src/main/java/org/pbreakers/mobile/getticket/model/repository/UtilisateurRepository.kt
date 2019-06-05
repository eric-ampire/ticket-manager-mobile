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
        dao.add(utilisateur)
        function()
    }

    fun add(utilisateur: Utilisateur) {
        dao.add(utilisateur)
    }
}