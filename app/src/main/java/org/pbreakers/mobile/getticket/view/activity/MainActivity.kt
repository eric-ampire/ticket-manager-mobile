package org.pbreakers.mobile.getticket.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.*
import com.google.firebase.auth.FirebaseAuth
import com.kinda.alert.KAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header_drawer.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.KoinComponent
import org.pbreakers.mobile.getticket.R
import org.pbreakers.mobile.getticket.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), KoinComponent {

    private val mainViewModel by viewModel<MainViewModel>()

    private val navController by lazy {
        findNavController(this, R.id.navHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        setupDrawerLayout()
    }

    private fun setupDrawerLayout() {

        loadUserInfo()

        // Connect listener to navigation view
        setupWithNavController(navigationView, navController)
        setupActionBarWithNavController(this, navController, drawerLayout)
    }

    private fun initToolbar() {
        setSupportActionBar(mainToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(navController, drawerLayout)
    }

    private fun loadUserInfo() {
        // Get current user
        mainViewModel.getCurrentUser().observe(this, Observer {
            tvUsername.text = String.format("%s", it.nomUtilisateur)
        })

        mainViewModel.getCurrentUserRole().observe(this, Observer {
            tvRole.text = String.format("%s", it.nomRole)
        })

        displayBadge()
    }

    private fun displayBadge() {
        val menu = navigationView.menu

        // Get All badge
        val badgeBillet = menu.findItem(R.id.billetFragment).actionView.findViewById<TextView>(R.id.text)
        val badgeBus = menu.findItem(R.id.busFragment).actionView.findViewById<TextView>(R.id.text)
        val badgeVoyage = menu.findItem(R.id.homeFragment).actionView.findViewById<TextView>(R.id.text)

        mainViewModel.countBus().observe(this, Observer {
            badgeBus.text = String.format("%d bus", it)
        })

        mainViewModel.countVoyage().observe(this, Observer {
            badgeVoyage.text = String.format("%d voy.", it)
        })

        mainViewModel.countBillet().observe(this, Observer {
            if (it == 0 || it == 1) {
                badgeBillet.text = String.format("%d billet", it)
            } else {
                badgeBillet.text = String.format("%d billets", it)
            }
        })
    }

    fun showProgressBar() {
        mainProgressBar.isVisible = true
    }

    fun hideProgressBar() {
        mainProgressBar.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                val dialog = KAlertDialog(this, KAlertDialog.WARNING_TYPE).apply {
                    titleText = "Deconnexion"
                    confirmText = "Deconnecter"
                    contentText = "Etes vous sur de vouloir vous deconnectez ?"
                    show()
                }

                dialog.setConfirmClickListener {
                    if (it.alerType == KAlertDialog.WARNING_TYPE) {
                        it.changeAlertType(KAlertDialog.SUCCESS_TYPE)
                    }

                    if (it.alerType == KAlertDialog.SUCCESS_TYPE) {
                        FirebaseAuth.getInstance().signOut()
                        finish()

                        startActivity<AuthActivity>()
                    }
                }
            }

            else -> onNavDestinationSelected(item, navController)
        }
        return true
    }
}
