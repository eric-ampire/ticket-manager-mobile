package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.startActivity
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.util.Session
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var session: Session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val app = application as App
        app.appComponent.inject(this)

        session.getCurrentUser {
            if (it == null) {
                startActivity<AuthActivity>()
            } else {
                startActivity<MainActivity>()
            }

            finish()
        }
    }
}
