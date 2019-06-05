package org.pbreakers.mobile.getticket.view.activity

import android.net.Uri
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
import org.pbreakers.mobile.getticket.app.App
import org.pbreakers.mobile.getticket.util.Session
import org.pbreakers.mobile.getticket.view.fragment.EnregFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity(), EnregFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {

    }

    @Inject
    lateinit var session: Session

    private val navController by lazy {
        Navigation.findNavController(this, R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupDrawerLayout()
        val app = application as App
        app.appComponent.inject(this)
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
        session.getCurrentUser {
            if (it != null) {
                tvUsername.text = String.format("%s", it.nomUtilisateur)
                tvRole.text = "Admin"
            }
        }
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
