package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.dao.RoleDao
import org.pbreakers.mobile.getticket.model.dao.UtilisateurDao
import org.pbreakers.mobile.getticket.model.entity.Role
import org.pbreakers.mobile.getticket.model.entity.Utilisateur
import org.pbreakers.mobile.getticket.util.Session
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject lateinit var session: Session
    @Inject lateinit var userDao: UtilisateurDao
    @Inject lateinit var roleDao: RoleDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val app = application as App
        app.appComponent.inject(this)

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
