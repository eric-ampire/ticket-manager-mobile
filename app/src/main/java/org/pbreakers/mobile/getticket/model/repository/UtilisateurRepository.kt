package org.pbreakers.mobile.getticket.model.repository

import androidx.lifecycle.LiveData
import io.reactivex.Maybe
import org.pbreakers.mobile.getticket.model.api.UserApi
import org.pbreakers.mobile.getticket.model.dao.UtilisateurDao
import org.pbreakers.mobile.getticket.model.entity.Utilisateur

class UtilisateurRepository(private val dao: UtilisateurDao, private val api: UserApi) {

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

    fun login(username: String, password: String): Maybe<Utilisateur> {
        return dao.fite()
    }
}