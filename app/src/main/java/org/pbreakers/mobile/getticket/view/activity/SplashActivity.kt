package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.Util
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.model.dao.*
import org.pbreakers.mobile.getticket.model.entity.*
import org.pbreakers.mobile.getticket.util.Session
import java.util.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var session: Session

    @Inject
    lateinit var userDao: UtilisateurDao

    @Inject
    lateinit var roleDao: RoleDao

    @Inject lateinit var agenceDao: AgenceDao
    @Inject lateinit var busDao: BusDao
    @Inject lateinit var voyage: VoyageDao
    @Inject lateinit var lieuDao: LieuDao
    @Inject lateinit var etatDao: EtatDao
    @Inject lateinit var transitDao: TransitDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val app = application as App
        app.appComponent.inject(this)

        fda()

        session.getCurrentUser {
            if (it == null) {
                startActivity<AuthActivity>()
            } else {
                startActivity<MainActivity>()
            }

            finish()
        }
    }

    fun fda() {
        roleDao.add(Role(1, "Admin"))
        userDao.add(Utilisateur(1, "Eric Ampire", "ericampire", "ericampire", 1))
    }
}
