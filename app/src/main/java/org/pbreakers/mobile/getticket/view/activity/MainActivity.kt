package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_drawer.*
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.util.Session
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var session: Session

    private val navController by lazy {
        Navigation.findNavController(this, R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupDrawerLayout()
    }

    private fun setupDrawerLayout() {
        setSupportActionBar(mainToolbar)
        val toggle = object :
            ActionBarDrawerToggle(this, drawerLayout, mainToolbar, R.string.open_drawer, R.string.close_drawer) {
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
                loadUserInfo()
            }
        }

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Connect listener to navigation view
        setupWithNavController(navigationView, navController)
    }

    private fun loadUserInfo() {
        // Get current user
        session.getCurrentUser {
            if (it != null) {
                textViewUsername?.text = String.format("%s", it.nom)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
