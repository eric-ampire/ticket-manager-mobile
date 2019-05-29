package org.pbreakers.mobile.getticket.util

import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.dao.UtilisateurDao
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import javax.inject.Inject


class Session(val application: App) {

    @Inject
    lateinit var dao: UtilisateurDao

    fun getCurrentUser(onSuccess: (Utilisateur?) -> Unit) {

        application.appComponent.inject(this)

        dao.findAll().observeForever {
            onSuccess(it.firstOrNull())
        }
    }
}