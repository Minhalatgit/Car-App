package com.oip.carapp.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.oip.carapp.R
import com.oip.carapp.authentication.views.AuthenticationActivity
import com.oip.carapp.databinding.ActivityMainBinding
import com.oip.carapp.utils.PreferencesHandler
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationIcon: ImageView
    lateinit var navController: NavController

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
        navigationIcon = binding.navigationIcon

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        // enable up button
        setupActionBarWithNavController(this, navController)
        // connect menu items with fragments
        setupWithNavController(binding.navView, navController)

        // Toolbar, hamburger and back button setup for all fragment
        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                controller.graph.startDestination -> {
                    supportActionBar?.apply {
                        setDisplayShowHomeEnabled(false)
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                        this.title = ""
                    }
                    openDrawerListener()
                }
                R.id.serviceFragment -> {
                    supportActionBar?.apply {
                        setHomeAsUpIndicator(R.drawable.back_icon)
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                        setDisplayHomeAsUpEnabled(true)
                        this.title = ""
                    }
                }
                R.id.chooseCarFragment -> {
                    supportActionBar?.apply {
                        setHomeAsUpIndicator(R.drawable.back_icon)
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.dark
                            )
                        )
                        setDisplayHomeAsUpEnabled(true)
                        this.title = ""
                    }
                }
                R.id.paymentFragment -> {
                    supportActionBar?.apply {
                        setHomeAsUpIndicator(R.drawable.back_icon)
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                        setDisplayHomeAsUpEnabled(true)
                        this.title = ""
                    }
                }
                R.id.walletFragment -> {
                    supportActionBar?.apply {
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.yellow
                            )
                        )
                        setDisplayHomeAsUpEnabled(false)
                        this.title = ""
                    }
                }
                R.id.historyFragment -> {
                    supportActionBar?.apply {
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                        setDisplayHomeAsUpEnabled(false)
                        this.title = ""
                    }
                }
                R.id.notificationFragment -> {
                    supportActionBar?.apply {
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                        setDisplayHomeAsUpEnabled(false)
                        this.title = ""
                    }
                }
                R.id.inviteFriendFragment -> {
                    supportActionBar?.apply {
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                        setDisplayHomeAsUpEnabled(false)
                        this.title = ""
                    }
                }
                R.id.settingsFragment -> {
                    supportActionBar?.apply {
                        setBackgroundDrawable(
                            ContextCompat.getDrawable(
                                this@MainActivity,
                                R.color.white
                            )
                        )
                        setDisplayHomeAsUpEnabled(false)
                        this.title = ""
                    }
                }
                R.id.accountFragment -> {
                    supportActionBar?.apply {
                        this.title = ""
                    }
                }
            }

            // Navigation Drawer lock/unlock
            when (destination.id) {
                controller.graph.startDestination -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                R.id.walletFragment -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                R.id.historyFragment -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                R.id.notificationFragment -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                R.id.inviteFriendFragment -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                R.id.settingsFragment -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                else -> drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

        // Navigation for logout
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.logout -> {
                    PreferencesHandler.setIsLogin(false)
                    val intent =
                        Intent(this, AuthenticationActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                }
            }
            NavigationUI.onNavDestinationSelected(it, navController)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun openDrawerListener() {
        navigationIcon.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}