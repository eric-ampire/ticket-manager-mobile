package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.internal.Util
import org.jetbrains.anko.startActivity
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
        //

        // Adding agency
        agenceDao.add(Agence(1, "Mulykap"))
        agenceDao.add(Agence(2, "Transkat"))
        agenceDao.add(Agence(3, "Takwa"))
        agenceDao.add(Agence(4, "Vejor"))


        // Adding Bus
        busDao.add(Bus(1, "MLKP 1", 2, 23, 1))
        busDao.add(Bus(2, "Rosa 240", 1, 23, 2))
        busDao.add(Bus(3, "MLKP 10", 3, 23, 1))
        busDao.add(Bus(4, "Takwa 10", 2, 23, 3))
        busDao.add(Bus(5, "Vejor 1", 2, 23, 4))
        busDao.add(Bus(6, "MLKP 10", 3, 23, 1))
        busDao.add(Bus(7, "Takwa 10", 2, 23, 3))
        busDao.add(Bus(8, "Vejor 1", 2, 23, 4))
        busDao.add(Bus(9, "MLKP 10", 3, 23, 1))
        busDao.add(Bus(10, "Takwa 10", 2, 23, 3))
        busDao.add(Bus(11, "Vejor 1", 2, 23, 4))
        busDao.add(Bus(12, "MLKP 10", 3, 23, 1))
        busDao.add(Bus(13, "Takwa 10", 2, 23, 3))
        busDao.add(Bus(14, "Vejor 1", 2, 23, 4))
        busDao.add(Bus(15, "MLKP 10", 3, 23, 1))
        busDao.add(Bus(16, "Takwa 10", 2, 23, 3))
        busDao.add(Bus(17, "Vejor 1", 2, 23, 4))
        busDao.add(Bus(18, "MLKP 10", 3, 23, 1))
        busDao.add(Bus(19, "Takwa 10", 2, 23, 3))
        busDao.add(Bus(20, "Vejor 1", 2, 23, 4))
        busDao.add(Bus(21, "MLKP 10", 3, 23, 1))
        busDao.add(Bus(22, "Takwa 10", 2, 23, 3))
        busDao.add(Bus(23, "Vejor 1", 2, 23, 4))
        busDao.add(Bus(24, "MLKP 10", 3, 23, 1))
        busDao.add(Bus(25, "Takwa 10", 2, 23, 3))
        busDao.add(Bus(26, "Vejor 1", 2, 23, 4))

        // Add Role
        roleDao.add(Role(1, "admin"))
        roleDao.add(Role(2, "hotesse"))
        roleDao.add(Role(3, "agent local"))
        roleDao.add(Role(4, "agent local"))

        // Add Transit
        transitDao.add(Transit(1, "Likasi - Kolwezi"))
        transitDao.add(Transit(2, "Kolwezi - Fungurume"))

        // Add Etat
        etatDao.add(Etat(1, "Occupe"))
        etatDao.add(Etat(2, "En cour"))
        etatDao.add(Etat(3, "Au repos"))

        // Add Lieu
        lieuDao.add(Lieu(1, "Likasi"))
        lieuDao.add(Lieu(3, "Lubumbashi"))
        lieuDao.add(Lieu(2, "Likasi"))


        // Add voyage
        voyage.add(Voyage(1, "Ref 1", 1, 2, 1, Date(), Date(), Date(), Date(), 1, 21000.0, 1))
        voyage.add(Voyage(2, "Ref 3", 2, 1, 3, Date(), Date(), Date(), Date(), 2, 20000.0, 2))
        voyage.add(Voyage(3, "Ref 1", 3, 1, 2, Date(), Date(), Date(), Date(), 1, 51000.0, 1))


        userDao.add(Utilisateur(1, "Eric Ampire", "ericampire", "ericampire", 1))
    }
}
