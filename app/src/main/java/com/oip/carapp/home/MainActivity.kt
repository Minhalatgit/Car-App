package com.oip.carapp.home

import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController( // to enable up button and hamburger for top destination
            this,
            navController,
            drawerLayout
        )
        NavigationUI.setupWithNavController( // to connect menu items and fragments
            binding.navView,
            navController
        )

        navController.addOnDestinationChangedListener { controller, destination, _ ->
            when (destination.id) {
                controller.graph.startDestination -> setActionbarItems(
                    R.drawable.hamburger_icon,
                    R.color.white,
                    ""
                )
                R.id.serviceFragment -> setActionbarItems(
                    R.drawable.back_icon,
                    R.color.white,
                    ""
                )
                R.id.chooseCarFragment -> setActionbarItems(
                    R.drawable.back_icon,
                    R.color.dark,
                    ""
                )
                R.id.paymentFragment -> setActionbarItems(
                    R.drawable.back_icon,
                    R.color.white,
                    ""
                )
            }

            if (destination.id == controller.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

    private fun setActionbarItems(icon: Int, color: Int, title: String) {
        supportActionBar?.apply {
            setHomeAsUpIndicator(icon)
            setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        this@MainActivity,
                        color
                    )
                )
            )
            this.title = title
        }
    }
}