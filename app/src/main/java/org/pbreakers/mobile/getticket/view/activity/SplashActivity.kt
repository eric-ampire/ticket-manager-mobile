package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.jetbrains.anko.startActivity
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.R

class SplashActivity : AppCompatActivity(), KoinComponent {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity<MainActivity>()
        } else {
            startActivity<AuthActivity>()
        }

        finish()
    }
}
