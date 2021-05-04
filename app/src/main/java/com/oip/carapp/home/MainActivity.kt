package com.oip.carapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityMainBinding

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

        //nav controller for bottom view
//        val navController = findNavController(R.id.navHostFragment)
//
//        // Setting Navigation Controller with the BottomNavigationView
//        bottomView.setupWithNavController(navController)
//
//        // Setting Up ActionBar with Navigation Controller
//        setupActionBarWithNavController(this, navController)

        setSupportActionBar(binding.toolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

    }

}