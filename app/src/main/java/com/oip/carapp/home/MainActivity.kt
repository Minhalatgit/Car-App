package com.oip.carapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityMainBinding
import com.oip.carapp.utils.Constants.BASE_URL_IMAGES
import com.oip.carapp.utils.PreferencesHandler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        setSupportActionBar(binding.toolbar)

        profileImage.setOnClickListener {
            //to navigate to profile fragment in proper flow
            bottomView.selectedItemId = R.id.moreFragment //select more fragment menu item
            navController.navigate(R.id.profileFragment)    // navigate to profile fragment
        }

        Picasso.get().load(BASE_URL_IMAGES + PreferencesHandler.getProfileImageUrl())
            .placeholder(R.drawable.profile_placeholder).into(profileImage)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        bottomView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.moreFragment -> {
                    window.statusBarColor = getColor(R.color.yellow)
                    toolbar.visibility = View.GONE
                }
                R.id.serviceBookingFragment -> {
                    window.statusBarColor = getColor(R.color.white)
                    toolbar.visibility = View.GONE
                    bottomView.visibility = View.GONE
                }
                R.id.profileFragment -> {
                    window.statusBarColor = getColor(R.color.yellow)
                    toolbar.visibility = View.GONE
                }
                R.id.notificationFragment -> {
                    window.statusBarColor = getColor(R.color.yellow)
                    toolbar.visibility = View.GONE
                }
                R.id.appointmentFragment -> {
                    window.statusBarColor = getColor(R.color.white)
                    toolbar.visibility = View.GONE
                }
                R.id.settingsFragment -> {
                    toolbar.visibility = View.GONE
                }
                else -> {
                    window.statusBarColor = getColor(R.color.white)
                    toolbar.visibility = View.VISIBLE
                    bottomView.visibility = View.VISIBLE
                }
            }
        }

    }
}