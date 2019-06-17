package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.model.dao.RoleDao
import org.pbreakers.mobile.getticket.model.dao.UtilisateurDao
import org.pbreakers.mobile.getticket.model.entity.Role
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.util.Session

class SplashActivity : AppCompatActivity(), KoinComponent {

    private val session: Session        by inject()
    private val userDao: UtilisateurDao by inject()
    private val roleDao: RoleDao        by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        fda()

        if (session.getCurrentUser() != null) {
            startActivity<MainActivity>()
        } else {
            startActivity<AuthActivity>()
        }

        finish()
    }

    fun fda() {
        roleDao.add(Role(Role.HOTESSE, "Admin"))
        val user = Utilisateur(1, "Eric Ampire", "ericampire", "ericampire", Role.HOTESSE)

        userDao.add(user)
        session.createSession(user)
    }
}
