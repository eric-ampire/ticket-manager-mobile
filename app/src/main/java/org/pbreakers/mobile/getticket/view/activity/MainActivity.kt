package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_drawer.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.model.entity.Role
import org.pbreakers.mobile.getticket.util.Session
import org.pbreakers.mobile.getticket.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), KoinComponent {

    private val mainViewModel by lazy {
        ViewModelProviders.of(this).get<MainViewModel>()
    }

    private val session: Session by inject()

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

        // Change title
        navController.addOnDestinationChangedListener { _, destination, _ ->
            supportActionBar?.subtitle = destination.label
        }
    }

    private fun loadUserInfo() {
        // Get current user
        val username = session.getCurrentUser()!!

        tvUsername.text = String.format("%s", username.nomUtilisateur)
        tvRole.text = "Hotesse"

        displayBadge()
    }

    private fun displayBadge() {
        val menu = navigationView.menu
        val username = session.getCurrentUser()

        when(username!!.idRole) {

            Role.CLIENT, Role.AGENT_LOCAL -> {
                menu.findItem(R.id.scanningFragment).isVisible = false
                menu.findItem(R.id.enregFragment).isVisible = false
            }

            Role.HOTESSE -> {
                menu.findItem(R.id.enregFragment).isVisible = false
            }
        }

        // Get All badge
        val badgeBillet = menu.findItem(R.id.billetFragment).actionView.findViewById<TextView>(R.id.text)
        val badgeBus = menu.findItem(R.id.busFragment).actionView.findViewById<TextView>(R.id.text)

        mainViewModel.countBus().observe(this, Observer {
            badgeBus.text = String.format("%d bus", it)
        })

        mainViewModel.countBillet().observe(this, Observer {
            if (it == 0 || it == 1) {
                badgeBillet.text = String.format("%d billet", it)
            } else {
                badgeBillet.text = String.format("%d billets", it)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.aboutFragment -> navController.navigate(R.id.action_homeFragment_to_aboutFragment)
//            R.id.settingFragment -> navController.navigate(R.id.action_homeFragment_to_settingFragment)
//        }
        return true
    }
}
