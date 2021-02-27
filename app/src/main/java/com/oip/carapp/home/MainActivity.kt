package com.oip.carapp.home

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationIcon: ImageView
    lateinit var navController: NavController

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
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setActionbarItems(
        actionBarColor: Int,
        navigationIcon: Int,
        displayNavigateIcon: Boolean
    ) {
        supportActionBar?.apply {
            setIcon(navigationIcon)
//            setDisplayShowHomeEnabled(displayNavigateIcon)
//            setDisplayHomeAsUpEnabled(displayNavigateIcon)
            setBackgroundDrawable(ContextCompat.getDrawable(this@MainActivity, actionBarColor))
            this.title = ""
        }
    }

    private fun openDrawerListener() {
        navigationIcon.setOnClickListener {
            drawerLayout.openDrawer(Gravity.LEFT)
        }
    }
}